package com.test.crowd.api;

import com.test.crowd.entity.po.MemberLaunchInfoPO;
import com.test.crowd.entity.po.MemberPO;
import com.test.crowd.entity.ResultEntity;
import com.test.crowd.entity.po.ProjectPO;
import com.test.crowd.entity.vo.ProjectVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {

	@RequestMapping("/retrieve/member/launch/info/po")
	ResultEntity<MemberLaunchInfoPO> retrieveMemberLaunchInfoPO(@RequestParam("memberId") String memberId);

	@RequestMapping("/retrieve/loign/acct/count")
	ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct);

	@RequestMapping(value="/save/member/remote",method = RequestMethod.POST)
	ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);

	@RequestMapping("/retrieve/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);

	@RequestMapping(value="/save/project/remote/{memberId}",method = RequestMethod.POST)
	ResultEntity<String> saveProjectRemote(
			@RequestBody ProjectVO projectVO,
			@PathVariable("memberId") String memberId);

	@RequestMapping("/retrieve/project/delete")
	void deleteProjectRemote(@RequestParam("id") Integer id);
	@RequestMapping("/retrieve/project/find")
	ResultEntity<ProjectPO> findProjectRemote(@RequestParam("id") Integer id);
	@RequestMapping("/retrieve/project/find/whole")
	ResultEntity<ProjectVO> findwholeProjectRemote(@RequestParam("id") Integer id);
	@RequestMapping("/retrieve/save/project/Receiving")
	public String saveProjectReceivingRetrieve(@RequestParam("id") Integer id,@RequestParam("ReceiveName")String ReceiveName,@RequestParam("ReceiveTel")String ReceiveTel,@RequestParam("ReceiveAddress")String ReceiveAddress,@RequestParam("ReceiveMark")String ReceiveMark);
	@RequestMapping("/retrieve/apply/project")
    String applywholeProjectRemote(@RequestParam("id") Integer id,@RequestParam("total_amount") Integer  total_amount,@RequestParam("subject") String subject);

	@RequestMapping("/retrieve/project/delete/by/apply")
	void deleteProjectApplyRemote(@RequestParam("id") Integer id);
	@RequestMapping("/retrieve/project/find/by/aply")
	ResultEntity<ProjectVO> findProjectApplyRemote(@RequestParam("id") Integer id);
}
