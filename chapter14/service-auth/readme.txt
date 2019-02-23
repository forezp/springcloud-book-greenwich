1. ServiceAuthApplication此类加上 EnableResourceServer 注解，开启Resource Server 。因为此程序本身需要对外暴露获取Token 的API 接口和验证Token 的API 接口，
    所以该程序也是一个资源服务。EnableDiscoveryClient 是集成服务注册与发现中心 Consul组件使用的

2. WebSecurityConfig类，因为项目service-auth 需要对外暴露检查Token 的API 接口，所以auth-service 也是一个资源服务，需要在工程中引入Spring Security，
   并做相关的配置，对auth-service 资源进行保护
        1. configure方法中的HttpSecurity配置此项目所有请求都需要安全认证
        2. configure方法中的AuthenticationManagerBuilder 中配置了验证的用户信息源和密码加密的策略
        3. 配置了验证管理的Bean 为AuthenticationManager

3. OAuth2AuthorizationConfig类的Configuration + EnableAuthorizationServer注解，开启服务授权服务功能作为授权服务需要配置3 个选项，分别为:
        1. ClientDetailsServiceConfigurer 详细说明见此类中的configure(ClientDetailsServiceConfigurer clients) 方法
        2. AuthorizationServerEndpointsConfigurer 详细说明见此类中的configure(AuthorizationServerEndpointsConfigurer endpoints)方法
        3. AuthorizationServerSecurityConfigurer 详细说明见此类中的configure(AuthorizationServerEndpointsConfigurer endpoints) 方法

4. 使用postman获取token的方式：  Oauth 2.0定义了四种授权方式： 1. 授权码模式 2.简化模式 3. 密码模式 4. 客户端模式
    第一种方式： 客户端模式
            1.在postman中使用post协议访问，地址为http://localhost:5000/uaa/oauth/token
            2.参数选择body中的x-www-form-urlencoded类型
            3.输入3个 key-value 键值对如下：
                client_id       service-hi
                client_secret   123456
                grant_type      client_credentials
            4. 返回参数：
                {
                    "access_token": "2052176a-c221-41e6-a34a-e26e048905e0",
                    "token_type": "bearer",
                    "expires_in": 43199,
                    "scope": "server"
                }

            5. 也可以用curl 命令行模式访问: 打开curl命令行： 输入：


    第二种方式： 密码模式
                1.在postman中使用post协议访问，地址为http://localhost:5000/uaa/oauth/token
                2.参数选择body中的x-www-form-urlencoded类型
                3.输入5个 key-value 键值对如下：
                    client_id       service-hi
                    client_secret   123456
                    grant_type      password
                    username        fzp
                    password        123456
                4. 返回参数：
                    {
                        "access_token": "eb156d88-227c-4594-81c8-381aa7c6bbd8",
                        "token_type": "bearer",
                        "refresh_token": "be7ddde5-cde5-4120-a7b9-0f3769ba313e",
                        "expires_in": 43104,
                        "scope": "server"
                    }

                5. 也可以用curl 命令行模式访问: 打开curl命令行： 输入：
                    curl -i -X POST -d "username=fzp&password=123456&grant_type=password&client_id=service-hi&client_secret=123456" http://localhost:5000/uaa/oauth/token
                    返回步骤4中对应的JSON参数

5. 笔记： 解决Spring Security OAuth在访问/oauth/token时候报401 authentication is required
   回答： /uas/oauth/token 这个如果配置支持allowFormAuthenticationForClients的，且url中有client_id和client_secret的会走ClientCredentialsTokenEndpointFilter来保护
         如果没有支持allowFormAuthenticationForClients或者有支持但是url中没有client_id和client_secret的，走basic认证保护