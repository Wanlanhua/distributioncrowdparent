package com.test.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PortalHandler {
	
	@RequestMapping("/index.html")
	public String showPortalPage() {
		
		// 加载真实数据，到页面上显示
		
		return "portal-page";
	}

}
