package com.test.crowd.service.api;


import com.test.crowd.entity.po.MemberLaunchInfoPO;
import com.test.crowd.entity.po.MemberPO;

public interface MemberService {

	int getLoginAcctCount(String loginAcct);

	void saveMemberPO(MemberPO memberPO);

    MemberPO getMemberByLoginAcct(String loginAcct);

	MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId);
}
