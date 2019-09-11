1. ResourceServerConfigurer是资源服务类Configuration + EnableResourceServer注解开启 Resource Server功能
   EnableGlobalMethodSecurity开启方法级别的安全保护configure方法通过ant表达式配置哪些请求需要验证，哪些请求不需要验证

2. OAuth2ClientConfig类，OAuth2客户端,用来访问被0Auth2 保护的资源。配置0Auth2 Client ，简单来说， 需要配置3 个选项：
         一是配置受保护的资源的信息， 即ClientCredentialsResourceDetails ；
         二是配置一个过滤器， 存储当前请求和上下文；
         三是在Request 域内创建AccessTokenRequest 类型的Bean。
   在0Auth2ClientConfig 类上;加上@Enable0Auth2Client 注解， 开启0Auth2 Client 的功能：
   并配置了一个ClientCredentialsResourceDetails 类型的Bean ， 该Bean 是通过读取配置文件中前缀为security.oauth2.client
   的配置来获取Bean 的配置属性的； 注入一个Oauth2FeignRequestInterceptor类型过滤器的Bean
   最后注入了一个用于向Uaa 服务请求的0Auth2RestTemplate 类型的Bean

3. UserController类提供一个注册API，访问此服务不需要认证

4. HiController有3个服务，/hi服务不需要认证权限，只要头部的token正确就可以访问；/hello方法需要头部Token正确且具有ROLD_ADMIN权限才能访问；
   /getPrinciple获取当前Token用户信息。

5. 用Curl命令演示整个授权与访问资源流程：
    1. 访问service-hi注册用户 chenyan：
       curl -d "username=chenyan&password=123456" http://localhost:8762/user/registry
    2. 访问service-auth为新用户chenyan获取Token服务
       curl -i -X POST -d "username=chenyan&password=123456&grant_type=password&client_id=service-hi&client_secret=123456" http://localhost:5000/uaa/oauth/token
    3. 访问service-hi的/hi服务
       curl -l -H "Authorization:Bearer eb69eff5-e87d-43f7-a2eb-0eeaabbe5bc6" -X GET "http://localhost:8762/hi"
       访问成功，输出：hi :,i am from port:8762
    4. 访问service-hi需要ROLE_ADMIN权限节点的hello接口
       curl -l -H "Authorization:Bearer eb69eff5-e87d-43f7-a2eb-0eeaabbe5bc6" -X GET "http://localhost:8762/hello"
       第一次访问提示访问失败没有权限：{"error"："access_denied"，"error_description":"不允许访问"}

       为新用户插入ROLE_ADMIN权限, INSERT INTO  user_role VALUES (4,2); 再执行：
       curl -l -H "Authorization:Bearer eb69eff5-e87d-43f7-a2eb-0eeaabbe5bc6" -X GET "http://localhost:8762/hello"
       访问成功，输出 hello

     这种方式 每次请求都需要资源服务内部远程调度auth-service 服务来验证Token 的正确性，以及该Token 对应的用户所具有的权限， 额外多了一次内部请求。

6. 用postman命令演示整个授权与访问资源流程：
    1. 访问service-hi注册用户 plum：
            a.在postman中使用post协议访问，地址为http://localhost:8762/user/registry
            b.参数选择body中的x-www-form-urlencoded类型
            c.输入2个 key-value 键值对如下：
                username        plum
                password        123456
            d. 返回参数：
                {
                    "id": 8,
                    "username": "plum",
                    "password": "$2a$10$GQ.XPZT.sTLbpVKUhuPRaeYw.mn2UvSs7HPhOmaKMNQlR3LygfsgK",
                    "authorities": null,
                    "enabled": true,
                    "accountNonExpired": true,
                    "accountNonLocked": true,
                    "credentialsNonExpired": true
                }

    2. 访问service-auth为新用户chenyan获取Token服务
            a.在postman中使用post协议访问，地址为http://localhost:5000/uaa/oauth/token
            b.参数选择body中的x-www-form-urlencoded类型
            c.输入5个 key-value 键值对如下：
                client_id       service-hi
                client_secret   123456
                grant_type      password
                username        plum
                password        123456
            d. 返回参数：
                {
                    "access_token": "01eb1368-aade-4e7e-8401-8969c204130c",
                    "token_type": "bearer",
                    "refresh_token": "906914e3-55c2-4f72-a7ab-3dc19869db87",
                    "expires_in": 43199,
                    "scope": "server"
                }

    3. 访问service-hi的/hi服务
            a.在postman中使用get协议访问，地址为http://localhost:8762/hi
            b.Headers面板中输入1个 key-value 键值对如下：
                Authorization   Bearer 01eb1368-aade-4e7e-8401-8969c204130c
            c. 输出：
                hi :,i am from port:8762

    4. 访问service-hi需要ROLE_ADMIN权限节点的hello接口
            a.在postman中使用get协议访问，地址为http://localhost:8762/hello
            b.Headers面板中输入1个 key-value 键值对如下：
               Authorization   Bearer 01eb1368-aade-4e7e-8401-8969c204130c
            c. 输出：
               {
                   "error": "access_denied",
                   "error_description": "不允许访问"
               }

       为新用户插入ROLE_ADMIN权限, INSERT INTO  user_role VALUES (5,2); 用户ID会变，查看spring_security_auth库的user表，再访问hello服务成功。

    这种方式 每次请求都需要资源服务内部远程调度auth-service 服务来验证Token 的正确性，以及该Token 对应的用户所具有的权限， 额外多了一次内部请求。