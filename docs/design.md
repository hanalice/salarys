# 项目设计文档

---

## 目录

- [技术栈](#技术栈)
- [架构设计](#架构设计)
- [目录结构](#目录结构)
- [数据库设计](#数据库设计)
- [Excel 格式要求](#excel-格式要求)
- [接口说明](#接口说明)
- [注意事项](#注意事项)

---

## 技术栈

| 层次 | 技术 |
|------|------|
| MVC 框架 | Spring MVC 3.x |
| ORM | Hibernate 4 |
| 数据库 | MySQL 5.x |
| 连接池 | Apache Commons DBCP |
| Excel 解析 | jxl（仅支持 `.xls` 格式） |
| JSON 序列化 | fastjson 1.1.42 |
| 前端 | jQuery 1.9 + jquery.form + jquery.validate |
| 构建/部署 | Eclipse Dynamic Web Project + Tomcat |

---

## 架构设计

```
浏览器（HTML + jQuery）
    ↓ HTTP
DispatcherServlet（Spring MVC）
    ↓
拦截器链：EncodingFilter → AuthInterceptor（登录校验）
    ↓
Controller（Action 层）
    ↓
Service 层（业务逻辑）
    ↓
DAO 层（Hibernate）
    ↓
MySQL 数据库
```

### 各层说明

- **Action（`com.action`）**：Spring `@Controller`，URL 规则为 `/<name>.do?<method>`，例如 `salaryAction.do?querySalary`。
- **Service（`com.service`）**：接口 + 实现分离，手动在 `applicationContext.xml` 中声明 Bean，不通过组件扫描注入。
- **DAO（`com.dao`）**：接口 + 实现继承泛型 `BaseDAOImpl<T>`，提供 HQL / Criteria 查询封装。Bean 在 `applicationContext.xml` 中声明。
- **实体（`com.dao.beans`）**：JPA/Hibernate `@Entity` 注解类，由 `applicationContext.xml` 的 `packagesToScan` 自动扫描。
- **拦截器（`com.filter`）**：`AuthInterceptor` 检查 Session，未登录时返回 `{"invalidSession": true}`；`EncodingFilter` 处理编码。两者均注册为 Spring MVC Interceptor。
- **工具（`com.util`）**：`ExcelDeal`/`UploadAction` 负责 Excel 解析；`TestReflet` 通过反射将 Excel 列值写入 Bean 字段；`StringUtil` 提供常用字符串/日期处理。

### Spring 配置文件

| 文件 | 作用 |
|------|------|
| `src/spring-mvc.xml` | 根配置，`import` 下面两个文件 |
| `src/applicationContext.xml` | DataSource、SessionFactory、事务、DAO/Service Bean |
| `src/springmvc-servlet.xml` | 组件扫描、静态资源映射、拦截器、文件上传解析器 |

---

## 目录结构

```
salarys/
├── src/
│   ├── applicationContext.xml       # 数据源 / Hibernate / 事务配置
│   ├── spring-mvc.xml               # Spring MVC 根配置
│   ├── springmvc-servlet.xml        # MVC 拦截器、静态资源、上传配置
│   └── com/
│       ├── action/                  # Controller 层
│       │   ├── BaseAction.java      # 公共请求/响应工具方法
│       │   ├── UserAction.java      # 登录接口
│       │   ├── SalaryAction.java    # 工资/津贴查询接口
│       │   └── UploadAction.java    # Excel 上传与解析
│       ├── service/
│       │   ├── interfaces/          # Service 接口
│       │   ├── impl/                # Service 实现
│       │   └── beans/               # 分页查询值对象（PageVBean、SearchVBean）
│       ├── dao/
│       │   ├── interfaces/          # DAO 接口
│       │   ├── impl/                # DAO 实现
│       │   └── beans/               # 数据库实体（Salary、SalarySubsidy、User）
│       ├── basic/                   # 通用 DAO 基类（BaseDAOImpl）
│       ├── filter/                  # 拦截器（AuthInterceptor、EncodingFilter）
│       ├── constants/Globals.java   # 全局常量 + Excel 列名定义
│       └── util/                    # 工具类（StringUtil、ExcelDeal、TestReflet 等）
└── WebContent/
    ├── WEB-INF/
    │   ├── web.xml                  # Servlet 配置
    │   └── lib/                     # 依赖 JAR
    ├── pages/
    │   ├── login.html               # 登录页
    │   ├── salary.html              # 工资查询页
    │   └── subsidy.html             # 津贴查询页
    ├── js/                          # jQuery 及业务 JS
    ├── css/                         # 样式表
    └── images/                      # 图片资源
```

---

## 数据库设计

数据库名：`salarys`，字符集：`UTF-8`。需手动建表（`hbm2ddl.auto=none`）。

**`user`** — 登录用户

| 字段 | 类型 | 说明 |
|------|------|------|
| id | varchar(32) | UUID 主键 |
| name | varchar | 姓名 |
| account | varchar | 登录账号 |
| password | varchar | 密码（初始为存折号后6位） |

**`salary`** — 工资单

| 字段 | 说明 |
|------|------|
| id | UUID 主键 |
| name / year / month | 姓名、年份、月份（联合唯一业务键） |
| gangwei / xinji / jiaotong / liangyou / one_child / poison / jiaxiang | 各类收入项 |
| increase / annual | 增发项 |
| yanglao / gongjijin / yiliao / shiye / personal_income_tax / gonghui / kouxiang | 各类扣款项 |
| decrease / total / remark | 减发合计、实发合计、备注 |

**`salary_subsidy`** — 津贴单

| 字段 | 说明 |
|------|------|
| id | UUID 主键 |
| name / year / month | 姓名、年份、月份 |
| gangwei / gongzuoliang / jiaxiang1 / jiaxiang2 / holiday | 收入项 |
| increase / personal_income_tax / kouxiang1 / kouxiang2 | 扣款项 |
| decrease / total / remark | 减发合计、实发合计、备注 |

---

## Excel 格式要求

- 文件格式：**`.xls`**（不支持 `.xlsx`）
- 文件编码：**GBK**
- 第一行为表头（自动跳过），从第二行起为数据行
- 列顺序与 `Globals.java` 中的数组严格绑定，**不可增删或调换**

**工资单列顺序**（22列）：

`姓名 → 年 → 月 → 岗位 → 薪绩 → 交通 → 独生子女 → 粮油 → 有毒有害 → 家乡 → 增发 → 年资 → 养老 → 公积金 → 医疗 → 失业 → 个人所得税 → 工会 → 扣项 → 减发 → 实发合计 → 备注`

**津贴单列顺序**（15列）：

`姓名 → 年 → 月 → 岗位 → 工作量 → 家乡1 → 家乡2 → 节假日 → 增发 → 个人所得税 → 扣项1 → 扣项2 → 减发 → 实发合计 → 备注`

---

## 接口说明

| URL | 方法 | 说明 |
|-----|------|------|
| `userAction.do?queryUser` | POST | 登录，参数：`account`、`password` |
| `salaryAction.do?querySalary` | POST | 工资分页查询，需登录 |
| `salaryAction.do?querySubsidy` | POST | 津贴分页查询，需登录 |
| `uploadAction.do?commonUpload` | POST | Excel 上传，参数：`salaryType`（`salary`/`subsidy`） |

未登录时受保护接口返回 `{"invalidSession": true}`，前端应跳转至登录页。

---

## 注意事项

- `UploadAction.java` 中上传路径使用 Windows 路径分隔符（`\\`），**部署在 Linux 时需改为 `/`**
- `web.xml` 中的 `LoginFilter` 和 `EncodingFilter` 已注释掉，编码与鉴权统一由 Spring MVC 拦截器处理
- 新增 Excel 字段需同步修改：`Globals.java` 数组、对应 `@Entity` 类、数据库表
