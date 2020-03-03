package com.test.crowd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMVCConfig implements WebMvcConfigurer {
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		// 相当于：<mvc:view-controller path="/to/login/page.html" view-name="member-login" />
		// 跳转到登录页面
		String urlPath = "/member/to/login/page.html";
		String viewName = "member-login";
		registry.addViewController(urlPath).setViewName(viewName);
		// 跳转到个人中心页面
		urlPath = "/member/to/member/center/page.html";
		viewName = "member-center";
		registry.addViewController(urlPath).setViewName(viewName);
		// 跳转到同意协议页面
		urlPath = "/project/to/agree/page";
		viewName = "project-1-start";
		registry.addViewController(urlPath).setViewName(viewName);
		//注册
		urlPath = "/member/to/reg.html";
		viewName = "reg";
		registry.addViewController(urlPath).setViewName(viewName);
		//众筹页面
		urlPath = "/member/to/Mycrowd.html";
		viewName = "minecrowdfunding";
		registry.addViewController(urlPath).setViewName(viewName);

		urlPath = "/project/to/step2.html";
		viewName = "start-step-2";
		registry.addViewController(urlPath).setViewName(viewName);

		urlPath = "/project/to/step3.html";
		viewName = "start-step-3";
		registry.addViewController(urlPath).setViewName(viewName);

		urlPath = "/project/to/step4.html";
		viewName = "start-step-4";
		registry.addViewController(urlPath).setViewName(viewName);

		urlPath = "/project/to/pay1.html";
		viewName = "pay-step-1";
		registry.addViewController(urlPath).setViewName(viewName);

		urlPath = "/project/to/pay2.html";
		viewName = "pay-step-2";
		registry.addViewController(urlPath).setViewName(viewName);

	}

}
