# 工资查询系统

面向员工工资及津贴查询 Web 系统。管理员通过上传 Excel 文件导入工资/津贴数据，员工登录后可查询本人的工资单与津贴单。

---

## 功能列表

- 员工账号登录（Session 鉴权，超时自动拦截）
- 工资单查询：按年份/月份筛选，分页展示
- 津贴单查询：按年份/月份筛选，分页展示
- 管理员上传 `.xls` Excel 文件批量导入工资/津贴数据
- 重复上传自动覆盖（同员工同月旧记录先删后写）

---

## Quick Start

**前置条件**：JDK 1.7+、MySQL 5.x、Tomcat 7/8、Eclipse EE（含 WTP 插件）

1. **建库建表**
   ```sql
   CREATE DATABASE salarys DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
   ```
   建表结构见 [项目设计文档 → 数据库设计](docs/design.md#数据库设计)。

2. **修改数据库连接**（`src/applicationContext.xml`）
   ```xml
   <property name="url" value="jdbc:mysql://127.0.0.1:3306/salarys?useUnicode=true&amp;characterEncoding=utf8" />
   <property name="username" value="root" />
   <property name="password" value="你的密码" />
   ```

3. **导入项目**：Eclipse → File → Import → Existing Projects into Workspace

4. **部署**：Servers 视图 → 右键 Tomcat → Add and Remove → 加入 `salarys`

5. **启动**：运行 Tomcat，访问 `http://localhost:8080/salarys/pages/login.html`

---

## 项目设计

技术栈、架构设计、目录结构、数据库设计、Excel 格式要求、接口说明等详见 **[docs/design.md](docs/design.md)**。

---

## License

[MIT](LICENSE)
