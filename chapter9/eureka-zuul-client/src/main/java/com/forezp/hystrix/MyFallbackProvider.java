package com.forezp.hystrix;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
class MyFallbackProvider  implements FallbackProvider {
    /**
     * getRoute方法，用于指定熔断功能应用于哪些路由的服务
     * 可以指定具体的服务名称，比如eureka-client
     * 也可以指定*，代表此熔断器应用到所有的服务上
     * @return
     */
    @Override
    public String getRoute() {
        return "eureka-client";
//        return "*";
    }

    /**
     * fallbackResponse()方法为进入熔断功能时执行的逻辑。
     * @param var1
     * @param var2
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String var1, Throwable var2) {
        return new ClientHttpResponse() {
            /**
             * 网关向api服务请求是失败了，但是消费者客户端向网关发起的请求是OK的，
             * 不应该把api的404,500等问题抛给客户端
             * 网关和api服务集群对于客户端来说是黑盒子
             */
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 微服务出现宕机后，客户端再请求时候就会返回 fallback 等字样的字符串提示；
             * 但对于复杂一点的微服务，我们这里就得好好琢磨该怎么友好提示给用户了；
             * 如果请求用户服务失败，返回什么信息给消费者客户端
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                //返回前端的内容
                return new ByteArrayInputStream("oooops!error, i'm the fallback.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                //和body中的内容编码一致，否则容易乱码
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
