# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A Java-based salary management web application. Administrators upload `.xls` Excel files to import salary and subsidy records; employees query their own records. Built with Spring MVC + Hibernate 4 + MySQL.

## Build & Deploy

This is an **Eclipse Dynamic Web Project** with no Maven or Gradle. There are no build scripts — it is built and deployed via Eclipse IDE with a Tomcat server plugin.

- Import into Eclipse as an existing project
- Configure a Tomcat server in Eclipse and add the project to it
- Run/debug via the Eclipse Servers view
- The app is served at `http://localhost:8080/salarys/`

## Configuration

Database connection is hardcoded in `src/applicationContext.xml`:
- URL: `jdbc:mysql://127.0.0.1:3306/salarys`
- Default credentials: `root` / `passw0rd`
- `hbm2ddl.auto` is set to `none` — schema must be created manually

## Architecture

The stack is: `Controller (Action) → Service → DAO → Hibernate → MySQL`

**Spring config split across three files:**
- `src/applicationContext.xml` — DataSource, SessionFactory, transaction manager, DAO and Service beans
- `src/springmvc-servlet.xml` — component scan, static resource mappings, interceptors, multipart resolver
- `src/spring-mvc.xml` — root config that imports both of the above

**Layers:**
- `com.action` — Spring MVC `@Controller` classes (`SalaryAction`, `UserAction`, `UploadAction`). URL pattern is `/<name>.do?<method>` (e.g., `salaryAction.do?querySalary`).
- `com.service` — Service interfaces + impls; wired manually in `applicationContext.xml` (not component-scanned).
- `com.dao` — DAO interfaces + impls extending `BaseDAOImpl<T>` which wraps Hibernate `Session`. Beans declared in `applicationContext.xml`.
- `com.dao.beans` — JPA/Hibernate `@Entity` classes (`Salary`, `SalarySubsidy`, `User`) scanned from `applicationContext.xml`.
- `com.basic` — Generic `BaseDAOImpl<T>` with HQL/Criteria helpers; `PojoMapper` for detaching entities; `SpringFactory` for programmatic bean lookup.
- `com.filter` — `AuthInterceptor` (session guard) and `EncodingFilter` registered as Spring MVC interceptors in `springmvc-servlet.xml`.
- `com.constants.Globals` — Column name arrays (`SALARY_COLUMES`, `SUBSIDY_COLUMES`) that define the **exact order** fields must appear in uploaded Excel files.
- `com.util.ExcelDeal` / `TestReflet` — Excel parsing (jxl library, `.xls` only — `.xlsx` is not supported) and reflection-based bean population.

**Excel upload flow:**
1. `UploadAction.upload()` saves the file under `WebContent/excels/<date>/`
2. `readExcel()` parses it with jxl using GBK encoding
3. Rows are mapped to `Salary` or `SalarySubsidy` via `TestReflet.setBeanValue()` using column index order defined in `Globals.SALARY_COLUMES` / `Globals.SUBSIDY_COLUMES`
4. Existing records for the same (name, year, month) are deleted before inserting new ones

**Authentication:**
- `AuthInterceptor` checks `HttpSession` for a `"user"` attribute (key `Globals.USER_SESSION`)
- Unauthenticated requests return `{"invalidSession": true}` JSON
- Four URLs are whitelisted (login, upload, querySalary, querySubsidy) — see `springmvc-servlet.xml`

**Frontend:**
- Static HTML pages in `WebContent/pages/` (`login.html`, `salary.html`, `subsidy.html`)
- jQuery 1.9 + `jquery.form.js` for AJAX form submission; `jquery.validate.js` for client-side validation
- `WebContent/js/context.js` holds the base URL context path

## Key Constraints

- Excel files **must be `.xls`** format (jxl cannot parse `.xlsx`)
- Excel column order must exactly match `Globals.SALARY_COLUMES` or `Globals.SUBSIDY_COLUMES` arrays — adding or reordering columns requires updating both the array and the `@Entity` class
- Upload path uses Windows-style backslashes (`\\`) in `UploadAction` — will break on Linux/Mac without adjustment
- The `EncodingFilter` and `LoginFilter` in `web.xml` are commented out; encoding is handled by the Spring interceptor instead
