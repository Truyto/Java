# 💪 健身科普平台

一个基于 Vue 3 + Spring Boot 的健身科普平台，提供健身知识科普、动作指导、用户社区交流等功能。

## 🛠️ 技术栈

### 前端技术
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.x | 渐进式 JavaScript 框架 |
| Vite | 3.x | 下一代前端构建工具 |
| Axios | 1.x | HTTP 客户端 |
| Vue Router | 4.x | Vue 路由管理 |

### 后端技术
| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.7.18 | Java 后端框架 |
| Spring Data JPA | 2.7.x | 数据访问层 |
| Spring Security | 5.7.x | 安全框架 |
| JWT | - | 身份认证 |
| MySQL | 8.0 | 关系型数据库 |
| Lombok | 1.18.x | 简化代码 |

## ✨ 功能特性

### 1. 用户系统
- **登录/注册**：支持用户注册和登录，密码加密存储
- **用户信息**：显示用户名、健身格言、健身初衷
- **等级系统**：用户拥有健身等级（Lv.1-Lv.10）和经验值
- **等级称号**：根据等级显示不同称号（健身小白→宇宙最强）

### 2. 健身科普
- **科普列表**：展示健身科普文章卡片
- **科普详情**：支持查看更多科普内容
- **分类展示**：按分类展示不同类型的科普知识

### 3. 动作指导
- **动作列表**：展示各种健身动作卡片
- **动作分类**：按部位分类（腿部、核心、胸部等）
- **动作难度**：标记入门/进阶等难度等级

### 4. 想法球（互动社区）
- **动态展示**：用户健身初衷以浮动球形式展示
- **实时刷新**：每30秒刷新一批用户的健身初衷
- **用户高亮**：用户自己的想法球会高亮显示

### 5. 页面交互
- **滚动导航**：滚动时自动高亮当前板块
- **暗黑模式**：支持亮色/暗色主题切换
- **平滑滚动**：点击导航平滑滚动到对应板块

## 📁 项目结构

```
GymIntroductionNetcopy/
├── backend/                    # 后端代码
│   ├── src/main/java/com/example/gym/
│   │   ├── config/            # 配置类
│   │   │   ├── DataInitializer.java   # 数据初始化
│   │   │   ├── SecurityConfig.java    # 安全配置
│   │   │   └── WebConfig.java         # Web配置
│   │   ├── controller/        # 控制器
│   │   │   ├── UserController.java
│   │   │   ├── ScienceController.java
│   │   │   └── ActionController.java
│   │   ├── service/           # 服务层
│   │   │   ├── UserService.java
│   │   │   ├── ScienceService.java
│   │   │   ├── ActionService.java
│   │   │   └── JwtService.java
│   │   ├── repository/        # 数据访问层
│   │   ├── entity/            # 实体类
│   │   ├── dto/               # 数据传输对象
│   │   └── GymApplication.java
│   ├── src/main/resources/
│   │   └── application.yml    # 应用配置
│   └── pom.xml                # Maven配置
├── src/                       # 前端代码
│   ├── views/                 # 页面组件
│   │   ├── Home.vue           # 首页
│   │   ├── login.vue          # 登录页
│   │   ├── ScienceList.vue    # 科普列表
│   │   └── ActionList.vue     # 动作列表
│   ├── api/                   # API接口
│   ├── router/                # 路由配置
│   ├── utils/                 # 工具函数
│   ├── App.vue                # 根组件
│   └── main.js                # 入口文件
├── public/                    # 静态资源
├── dist/                      # 构建产物
├── vite.config.js             # Vite配置
└── package.json               # 依赖配置
```

## 🚀 快速开始

### 环境要求
- JDK 1.8+
- MySQL 8.0+
- Node.js 16+

### 后端启动

1. **配置数据库**
   ```sql
   CREATE DATABASE gym_db DEFAULT CHARACTER SET utf8mb4;
   ```

2. **修改配置**
   修改 `backend/src/main/resources/application.yml`：
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/gym_db?useSSL=false&serverTimezone=Asia/Shanghai
       username: your_username
       password: your_password
   ```

3. **启动后端**
   ```bash
   cd backend
   mvn spring-boot:run
   ```

### 前端启动

1. **安装依赖**
   ```bash
   npm install
   ```

2. **启动开发服务器**
   ```bash
   npm run dev
   ```

3. **访问地址**
   ```
   http://localhost:5173
   ```

## 🔌 API 接口

### 用户接口
| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/user/login` | 用户登录 |
| POST | `/api/user/register` | 用户注册 |
| GET | `/api/user/info` | 获取用户信息 |
| GET | `/api/user/fit-reasons` | 获取随机健身初衷 |

### 科普接口
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/science` | 获取科普列表 |
| GET | `/api/science/{id}` | 获取科普详情 |

### 动作接口
| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/action` | 获取动作列表 |
| GET | `/api/action/{id}` | 获取动作详情 |

## 🔒 认证机制

系统使用 JWT 进行身份认证：

1. 用户登录成功后，服务端返回 JWT Token
2. 前端将 Token 存储在 localStorage 中
3. 后续请求在请求头中携带 `Authorization: Bearer {token}`
4. 服务端验证 Token 有效性

## 🎨 核心功能实现

### 1. 想法球动画
```javascript
// Canvas 绘制浮动球
class Ball {
  constructor(x, y, text, color, isUserBall) {
    this.x = x;
    this.y = y;
    this.text = text;
    this.color = color;
    this.isUserBall = isUserBall; // 用户自己的球高亮标记
    // ...
  }
  
  draw(ctx) {
    // 绘制球体
    // 用户球添加金色边框和发光效果
    if (this.isUserBall) {
      ctx.shadowColor = '#FFD700';
      ctx.shadowBlur = 20;
    }
    // ...
  }
}
```

### 2. 滚动导航高亮
```javascript
const checkActiveNav = () => {
  const navKeys = ['home', 'science', 'action', 'communicate', 'mine'];
  for (let i = navKeys.length - 1; i >= 0; i--) {
    const sectionEl = document.getElementById(navKeys[i]);
    if (!sectionEl) continue;
    const rect = sectionEl.getBoundingClientRect();
    // 板块进入视口时高亮对应导航
    if (rect.top < window.innerHeight - 100 && rect.bottom > 100) {
      currentNav.value = navKeys[i];
      break;
    }
  }
};
```

### 3. 用户等级系统
```java
// 用户实体
@Entity
public class User {
    private Integer level = 1;    // 等级
    private Integer exp = 0;      // 经验值
    // ...
}

// 等级称号映射
private static final String[] LEVEL_TITLES = {
    "健身小白", "健身新手", "健身爱好者", "健身达人", 
    "健身教练", "金牌教练", "健身宗师", "健身传奇", 
    "健身之神", "宇宙最强"
};
```

## ⚠️ 遇到的难题及解决方案

### 1. 图片路径问题
**问题**：动作卡片和科普卡片的图片无法正确加载

**解决方案**：
- 统一图片存储路径为 `public/images/`
- 使用绝对路径加载图片
- 添加图片加载失败时的默认图片处理

```javascript
const handleImageError = (event) => {
  event.target.style.backgroundImage = 'url(/public/images/default.png)';
};
```

### 2. 用户信息显示失效
**问题**：登录后用户卡片无法正常显示

**解决方案**：
- 修复用户卡片的 CSS 动画样式
- 将 `v-if` 改为动态类名控制显示状态
- 确保用户信息正确从 localStorage 读取

```html
<!-- 修改前 -->
<div v-if="showUserCard" class="user-card">

<!-- 修改后 -->
<div class="user-card" :class="{show: showUserCard}">
```

### 3. 导航高亮失效
**问题**：滚动页面时导航栏无法正确高亮当前板块

**解决方案**：
- 发现页面存在多个相同 id 的元素
- 移除重复的 id，确保每个板块只有一个唯一 id
- 调整滚动判断条件，优化高亮触发时机

### 4. 暗黑模式状态丢失
**问题**：切换暗黑模式后，刷新页面恢复为亮色模式

**解决方案**：
- 在 localStorage 中保存主题状态
- 页面加载时读取保存的主题状态
- 同步更新 DOM 类名

```javascript
// 切换主题时保存
localStorage.setItem('isDark', isDark.value ? 'true' : 'false');

// 页面加载时恢复
const savedIsDark = localStorage.getItem('isDark') === 'true';
if (savedIsDark) {
  isDark.value = true;
  document.documentElement.classList.add('dark-mode');
}
```

### 5. 用户等级显示固定
**问题**：所有用户等级都显示为 Lv.3

**解决方案**：
- 在 User 实体中添加 level 和 exp 字段
- 登录接口返回用户等级信息
- 前端动态渲染等级和称号

## 📝 测试账号

| 用户名 | 密码 | 等级 | 称号 |
|--------|------|------|------|
| admin | 123456 | Lv.10 | 宇宙最强 |
| test | 123456 | Lv.5 | 健身教练 |
| fitness1 | 123456 | Lv.3 | 健身爱好者 |
| fitness6 | 123456 | Lv.1 | 健身小白 |

## 📄 许可证

MIT License

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

---

*努力不是为了感动谁，而是为了不辜负自己* 💪
