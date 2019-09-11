使用Spring Security：
1. 首先引入Spring Security 相关的依赖，然后写一个配置类，该配置类继承了WebSecurityConfigurerAdapter，并在该配置类
上引入@EnableWebSecurity 注解开启WebSecurity，注入configureGlobal方法，再configureGlobal方法中需要配置AuthenticationManagerBu ilder,AuthenticationManagerBuilder 配置了读取用户的认证信息的方式，可以从内存中读取，也可以从数据库中读取，或者用其他的方式。
	a. 内存读取参考示例SecurityConfiguration1.java 和 SecurityConfiguration2.java
	b. 数据库读取参考SecurityConfiguration4.java  要现在mysql数据库中新建spring security数据库，然后导入resources/scheme.sql初始化数据


2. 需要配置HttpSecurity, HttpSecurity 配置了请求的认证规则，即哪些URI 请求需要认证、哪些不需要，以及需要拥有什么权限
才能访问。最后，如果需要开启方法级别的安全配置，需要通过在配置类上@EnableGlobalMethodSecurity注解开启，方法级别上的
安全控制支持secureEnabled 、jsr250Enabled 和prePostEnabled 这3种方式，用的最多的是prePostEnabled 。其中， prePostEnabled 包括PreAuthorize 和PostAuthorize两种形式， 一般只用到PreAuthorize 这种方式。
	在方法上写权限注解的两种方法：
       a. @PreAuthorize("hasRole('ADMIN')")
       b. @PreAuthorize("hasAuthority('ROLE_ADMIN')")  两种写法相等
       一个方法多个权限点的写法：
       a. @PreAuthorize("hasAnyRole('ADMIN','USER')")
       b. @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
       
	开启方法级别的安全配置参考示例SecurityConfiguration3.java 与 BlogController类的delete方法上的preAuthorize注解

3. 章节详细介绍阅读深入理解SpringCloud与微服务的第13章节 Spring Boot Security详解。