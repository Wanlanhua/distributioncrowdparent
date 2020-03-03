package com.test.crowd.api;

import com.test.crowd.entity.ResultEntity;
import com.test.crowd.entity.po.MemberLaunchInfoPO;
import com.test.crowd.entity.vo.MemberSignSuccessVO;
import com.test.crowd.entity.vo.MemberVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("member-manager")
public interface MemberManagerRemoteService {
	@RequestMapping("/retrieve/member/launch/info/by/member/token")
	public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoByMemberToken(@RequestParam("token") String token);
	@RequestMapping("/member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token);
	
	@RequestMapping("/member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(
			@RequestParam("loginAcct") String loginAcct, 
			@RequestParam("userPswd") String userPswd);

	@RequestMapping("/member/manager/register")
	public ResultEntity<String> register(@RequestBody MemberVO memberVO);
	
	@RequestMapping("/member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum);
}
