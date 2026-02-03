# reuse-technology

技术复用程序 - Java 基础设施库

## 项目简介

reuse-technology 是一个技术复用项目，采用 Spring Boot Starter 模式封装常用功能，旨在减少重复代码，提高开发效率。

## 技术选型

- **Java**: 21
- **Spring Boot**: 3.5.6
- **构建工具**: Maven

## 模块概览

### reuse-dependencies
依赖管理模块

统一管理所有自定义模块的版本，作为 BOM 使用。

### common-data-structures
通用数据结构模块

提供项目开发所需的基础数据结构和通用定义。

**主要功能**:
- 基础实体类（时间实体、业务实体）
- 统一异常体系
- 通用枚举定义
- 统一响应结果封装
- 分页支持
- Bean 转换工具
- 自动数据赋值（AOP）

### foundational-capability-starter
工具类复用模块

封装开发中常用的工具功能。

**主要功能**:
- ID 生成器
- 用户名生成（拼音转换）
- 滑动验证码
- 文件处理
- JSON 处理
- 树形结构转换

### security-crypto-starter
加解密模块

提供国密算法和通用加密能力。

**主要功能**:
- SM2 非对称加密
- SM4 对称加密
- SM3 摘要算法
- PBKDF2 密码加密

### sliding-verification-starter
滑动验证码模块（开发中）

完整的滑动验证码解决方案。

## 快速开始

### 添加依赖管理

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>cn.yang</groupId>
            <artifactId>reuse-dependencies</artifactId>
            <version>1.3.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 引入模块

```xml
<dependency>
    <groupId>cn.yang</groupId>
    <artifactId>common-data-structures</artifactId>
</dependency>
```

## 设计特点

- **模块化**: 每个模块职责单一，可按需引入
- **即插即用**: Spring Boot Starter 自动配置
- **国密支持**: 支持 SM2/SM3/SM4 国密算法
- **统一规范**: 统一的异常处理和响应封装

## 版本历史

| 版本 | 说明 |
|------|------|
| 1.3.0 | 更新分页接口，新增文件上传、树形工具、滑动验证码 |
| 1.2.0 | 新增加密解密模块 |
| 1.1.0 | 新增通用数据结构和工具类模块 |