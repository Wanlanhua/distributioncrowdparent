package com.test.crowd.service.impl;

import com.test.crowd.entity.po.MemberLaunchInfoPO;
import com.test.crowd.entity.po.MemberLaunchInfoPOExample;
import com.test.crowd.entity.po.MemberPO;
import com.test.crowd.entity.po.MemberPOExample;
import com.test.crowd.mapper.MemberLaunchInfoPOMapper;
import com.test.crowd.mapper.MemberPOMapper;
import com.test.crowd.service.api.MemberService;
import com.test.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberPOMapper memberPOMapper;
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
	@Override
	public int getLoginAcctCount(String loginAcct) {
		
		MemberPOExample example = new MemberPOExample();
		
		example.createCriteria().andLoginacctEqualTo(loginAcct);
		
		return memberPOMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void saveMemberPO(MemberPO memberPO) {
		memberPOMapper.insert(memberPO);
	}

	@Override
	public MemberPO getMemberByLoginAcct(String loginAcct) {
		MemberPOExample example = new MemberPOExample();

		example.createCriteria().andLoginacctEqualTo(loginAcct);

		List<MemberPO> list = memberPOMapper.selectByExample(example);

		if(CrowdUtils.collectionEffectiveCheck(list)) {
			return list.get(0);
		}

		return null;
	}

	@Override
	public MemberLaunchInfoPO getMemberLaunchInfoPO(String memberId) {

		MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();

		example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));

		List<MemberLaunchInfoPO> list = memberLaunchInfoPOMapper.selectByExample(example);

		if(CrowdUtils.collectionEffectiveCheck(list)) {
			return list.get(0);
		}else {
			return null;
		}

	}

}
