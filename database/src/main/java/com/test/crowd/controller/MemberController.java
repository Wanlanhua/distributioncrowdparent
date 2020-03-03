package com.test.crowd.controller;

import com.test.crowd.entity.po.MemberLaunchInfoPO;
import com.test.crowd.entity.po.MemberPO;
import com.test.crowd.entity.ResultEntity;
import com.test.crowd.service.api.MemberService;
import com.test.crowd.util.CrowdConstant;
import com.test.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MemberController {

	@Autowired
	private MemberService memberService;
	@RequestMapping("/retrieve/member/launch/info/po")
	public ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoPO(@RequestParam("memberId") String memberId) {

		MemberLaunchInfoPO memberLaunchInfoPO = memberService.getMemberLaunchInfoPO(memberId);

		return ResultEntity.successWithData(memberLaunchInfoPO);
	}



	@RequestMapping("/retrieve/member/by/login/acct")
	public ResultEntity<MemberPO> retrieveMemberByLoginAcct(
			@RequestParam("loginAcct") String loginAcct) {

		try {
			MemberPO memberPO = memberService.getMemberByLoginAcct(loginAcct);

			return ResultEntity.successWithData(memberPO);
		} catch (Exception e) {
			e.printStackTrace();

			return ResultEntity.failed(e.getMessage());
		}
	}


	@RequestMapping("/retrieve/loign/acct/count")
	public ResultEntity<Integer> retrieveLoignAcctCount(
			@RequestParam("loginAcct") String loginAcct) {

		if(!CrowdUtils.strEffectiveCheck(loginAcct)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGINACCT_INVALID);
		}

		try {
			int count = memberService.getLoginAcctCount(loginAcct);

			return ResultEntity.successWithData(count);

		} catch (Exception e) {
			e.printStackTrace();

			return ResultEntity.failed(e.getMessage());
		}
	}

	@RequestMapping(value = "/save/member/remote",method = RequestMethod.POST)
	public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {

		try {
			// 执行保存
			memberService.saveMemberPO(memberPO);
		} catch (Exception e) {
			e.printStackTrace();

			return ResultEntity.failed(e.getMessage());
		}

		return ResultEntity.successNoData();

	}

}
