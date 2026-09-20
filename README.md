# B站莫奈取色 (BiliMonet)

一款专为 **哔哩哔哩 (tv.danmaku.bili)** 打造的轻量级 Xposed/LSPosed 莫奈取色模块。
在**背景与图标严格保持 B 站官方原色**的前提下，将文字与高亮色系全面接入 Android 系统 **Monet 动态取色**引擎，壁纸一换，B 站配色跟着走。

---<img width="7546" height="8192" alt="IMG_20260920_111349" src="https://github.com/user-attachments/assets/8399f471-42ac-4662-8c38-5b183d39d1b1" />


## ✨ 核心特性

### 1. 🎨 官方底色，零妥协
- **绝不染蓝**：页面大背景、顶栏、搜索框、卡片底色 100% 保持官方原生（白天纯白，夜间纯黑）。
- **图标与 Tab 官方原色**：右上角游戏中心 / 私信图标、未选中 Tab 与官方浅白一致，夜间清晰可见、不隐形。

### 2. 🌈 文字与高亮 Monet 化
- 选中 Tab、高亮文字、链接色、粉色系点缀按原版 KSU 莫奈映射表动态取色（`system_accent / neutral` 全系）。
- **B 站应用内夜间模式独立识别**：不依赖系统深色开关，根据当前主题背景亮度 + AppCompat/MagicSakura 状态判定，白天夜间各取其色。

### 3. ⚡ 现代架构与极致纯粹
- **双 Hook 引擎兼容**：同时兼容现代 Modern Hook（`io.github.libxposed:api`）与传统 Xposed API（`de.robv.android.xposed`）。
- **精准作用域**：仅作用于 `tv.danmaku.bili`，零常驻后台、零电量消耗。
- **静默无日志**：release 构建无任何日志输出，不写 LSPosed 日志。
- **不碰发布链路**：底栏发布按钮、评论发布按钮、创作中心发布按钮保持官方原样。

---

## 📱 系统要求与兼容性

- **系统版本**：Android 8.0 (API 26) ~ Android 14+
- **框架支持**：LSPosed / LSPatch（Xposed API 兼容环境）
- **目标应用**：哔哩哔哩 (tv.danmaku.bili) 官方正式版

---

## 🚀 安装与使用

1. 在 Magisk / KernelSU / APatch 环境下安装好 **LSPosed**。
2. 安装 **B站莫奈取色** APK（从旧版本直接覆盖安装即可，无需卸载）。
3. 打开 LSPosed 管理器，启用本模块，并确认勾选作用域为 **哔哩哔哩**。
4. 强行停止一次哔哩哔哩，重新打开即可生效。
5. 换一张壁纸，重启 B 站，配色即跟随系统 Monet 变化。

---

## 📄 免责声明

本模块仅供技术交流与个人学习使用。
