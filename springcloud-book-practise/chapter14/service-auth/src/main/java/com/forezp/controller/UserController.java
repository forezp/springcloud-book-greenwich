package com.forezp.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

/**
 * 此类暴露Remote Token Services 接口
 * 本案例采用RemoteTokenServices 这种方式对Token 进行验证。如果其他资源服务需要验
 * 证Token ，则需要远程调用本授权服务暴露的验证Token 的API 接口
 *
 * 调用此接口验证token之前要先获取token，获取token的方法：
 *	方法1：
 *	curl service-hi:l23456@localhost:5OOO/uaa/oauth/token -d grant_type=password -d username=fzp -d password=123456
 *  返回：
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public Principal getUser(Principal principal) {
		return principal;
	}


}
