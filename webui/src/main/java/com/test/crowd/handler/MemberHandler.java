package com.test.crowd.handler;

import javax.servlet.http.HttpSession;

import com.test.crowd.api.MemberManagerRemoteService;
import com.test.crowd.api.ProjectOperationRemoteService;
import com.test.crowd.entity.ResultEntity;
import com.test.crowd.entity.vo.MemberSignSuccessVO;
import com.test.crowd.entity.vo.MemberVO;
import com.test.crowd.entity.vo.ProjectVO;
import com.test.crowd.util.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class MemberHandler {

	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	@Autowired
	private ProjectOperationRemoteService projectOperationRemoteService;
	//退出
	@RequestMapping("/member/logout.html")
	public String logout(HttpSession session) {

		// 1.从现有Session中获取已登录的Member
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

		// 2.如果memberSignSuccessVO为null，则已经退出，不必继续执行
		if(memberSignSuccessVO  == null) {
			return "redirect:/";
		}

		// 3.获取token值
		String token = memberSignSuccessVO.getToken();

		// 4.调用远程方法删除Redis中存储的token
		ResultEntity<String> resultEntity = memberManagerRemoteService.logout(token);

		// 5.如果调用远程方法失败，抛出异常
		String result = resultEntity.getResult();

		if(ResultEntity.FAILED.equals(result)) {
			throw new RuntimeException(resultEntity.getMessage());
		}

		// 6.释放当前Session
		session.invalidate();

		return "redirect:/index.html";
	}
	//登陆
	@RequestMapping("/member/do/login.html")
	public String doLogin(MemberVO memberVO, Model model, HttpSession session) {

		String loginAcct = memberVO.getLoginacct();
		String userPswd = memberVO.getUserpswd();
		// 调用远程方法执行登录操作
		ResultEntity<MemberSignSuccessVO> resultEntity = memberManagerRemoteService.login(loginAcct, userPswd);
		// 检查远程方法调用结果
		String result = resultEntity.getResult();
		if(ResultEntity.FAILED.equals(result)) {

			String message = resultEntity.getMessage();

			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, message);

			return "member-login";
		}
		// 如果登录成功，则获取MemberSignSuccessVO对象
		MemberSignSuccessVO memberSignSuccessVO = resultEntity.getData();
		// 将MemberSignSuccessVO对象存入Session域
		session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, memberSignSuccessVO);
		session.setAttribute("loginIN",memberSignSuccessVO.getUsername());
		return "member-center";
	}

	//发送验证码
	@RequestMapping("/member/do/sendCode.html")
	public String SendCode(String phoneNum,Model model,String loginacct,String userpswd)
	{
		memberManagerRemoteService.sendCode(phoneNum);
		model.addAttribute("phoneNum",phoneNum);
		model.addAttribute("loginacct",loginacct);
		model.addAttribute("userpswd",userpswd);
		return "redirect:/member/to/reg.html";
	}
	//注册
	@RequestMapping("/member/do/reg.html")
	public String reg(MemberVO memberVO)
	{
		memberManagerRemoteService.register(memberVO);
		return "redirect:/member/to/login/page.html";
	}



}
