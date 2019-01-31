package com.forezp.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 自定义过滤器实现很简单：
 * 只需要继承ZuulFilter ，井实现ZuulFilter 中的抽象方法，包括filterType()
 * 和filterOrder（），以及IZuulFilter 的shouldFilter（）和run（）的两个方法
 */
@Component
public class MyFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

    /**
     * filterType()即过滤器的类型，有4 种类型，分别是“ pre ＇’“ post ”“ routing ”和“ error ”
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * filterOrder（）是过滤顺序，它为一个Int 类型的值，值越小，越早执行该过滤器
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * shouldFilter（）表示该过滤器是否过滤逻辑，如果为true ，则执行run（）方法：如果为false ，则不执行run （）方法
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * run （）方法写具体的过滤的逻辑
     * 在本例中，检查请求的参数中是否传了token 这个参数，如果没有传，则请求不被路由到具体的服务实例，直接返回响应，状态码为401
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Object accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}
            return null;
        }
        log.info("ok");
        return null;
    }
}