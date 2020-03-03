package com.test.crowd.service.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.test.crowd.entity.po.*;
import com.test.crowd.entity.vo.MemberConfirmInfoVO;
import com.test.crowd.entity.vo.MemberLauchInfoVO;
import com.test.crowd.entity.vo.ProjectVO;
import com.test.crowd.entity.vo.ReturnVO;
import com.test.crowd.mapper.*;
import com.test.crowd.service.api.ProjectService;
import com.test.crowd.util.CrowdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private MemberConfirmInfoPOMapper  memberConfirmInfoPOMapper;
	
	@Autowired
	private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;

	@Autowired
	private MemberPOMapper MemberPOMapper;
	
	@Autowired
	private ProjectItemPicPOMapper  projectItemPicPOMapper;
	
	@Autowired
	private ProjectPOMapper   projectPOMapper;
	
	@Autowired
	private ReturnPOMapper  returnPOMapper;
	
	@Autowired
	private TagPOMapper   tagPOMapper;
	
	@Autowired
	private TypePOMapper   typePOMapper;

	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void saveProject(ProjectVO projectVO, String memberId) {

		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
		DateFormat df2 = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
		String datetime=df.format(new Date())+""+df2.format(new Date());
		// 1.保存ProjectPO
		ProjectPO projectPO = new ProjectPO();
		BeanUtils.copyProperties(projectVO, projectPO);
		
		projectPO.setMemberid(Integer.parseInt(memberId));
		projectPO.setCreatedate(datetime);
		projectPOMapper.insert(projectPO);
		
		// 2.获取保存ProjectPO后得到的自增主键
		// 在ProjectPOMapper.xml文件中insert方法对应的标签中设置useGeneratedKeys="true" keyProperty="id"
		Integer projectId = projectPO.getId();
		

		// 5.保存detailPicturePathList
		// ①从VO对象中获取detailPicturePathList
		List<String> detailPicturePathList = projectVO.getDetailPicturePathList();
		if(CrowdUtils.collectionEffectiveCheck(detailPicturePathList)) {
			
			// ②创建一个空List集合，用来存储ProjectItemPicPO对象
			List<ProjectItemPicPO> projectItemPicPOList = new ArrayList<>();
			
			// ③遍历detailPicturePathList
			for (String detailPath : detailPicturePathList) {
				
				// ④创建projectItemPicPO对象
				ProjectItemPicPO projectItemPicPO = new ProjectItemPicPO(null, projectId, detailPath);
				
				projectItemPicPOList.add(projectItemPicPO);
			}
			
			// ⑤根据projectItemPicPOList执行批量保存
			projectItemPicPOMapper.insertBatch(projectItemPicPOList);
		}
		
		
		// 6.保存MemberLaunchInfoPO
		MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();

		if(memberLauchInfoVO != null) {

			// ※※※※※※※※※※※补充功能※※※※※※※※※※※
			// 将旧的用户发起人信息删除
			MemberLaunchInfoPOExample example = new MemberLaunchInfoPOExample();
			example.createCriteria().andMemberidEqualTo(Integer.parseInt(memberId));
			memberLaunchInfoPOMapper.deleteByExample(example);
			// ※※※※※※※※※※※※※※※※※※※※※※※※※※
			MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
			BeanUtils.copyProperties(memberLauchInfoVO, memberLaunchInfoPO);

			memberLaunchInfoPO.setMemberid(Integer.parseInt(memberId));

			memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);
		}
		
		// 7.根据ReturnVO的List保存ReturnPO
		List<ReturnVO> returnVOList = projectVO.getReturnVOList();
		
		if(CrowdUtils.collectionEffectiveCheck(returnVOList)) {
			
			List<ReturnPO> returnPOList = new ArrayList<>();
			
			for (ReturnVO returnVO : returnVOList) {
				
				ReturnPO returnPO = new ReturnPO();
				
				BeanUtils.copyProperties(returnVO, returnPO);
				
				returnPO.setProjectid(projectId);
				
				returnPOList.add(returnPO);
			}
			
			returnPOMapper.insertBatch(returnPOList);
			
		}
		
		// 8.保存MemberConfirmInfoPO
		MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
		
		if(memberConfirmInfoVO != null) {
			MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO(null, Integer.parseInt(memberId), memberConfirmInfoVO.getPaynum(), memberConfirmInfoVO.getCardnum());
			memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);
			
		}
		
	}

	@Override
	public void deleteproject(Integer id) {
		ProjectPOExample example=new ProjectPOExample();
		example.createCriteria().andMemberidEqualTo(id);
		projectPOMapper.deleteByExample(example);
	}

	@Override
	public ProjectPO findProject(Integer id) {

		ProjectPOExample example=new ProjectPOExample();
		example.createCriteria().andMemberidEqualTo(id);
		List<ProjectPO> projectPOS = projectPOMapper.selectByExample(example);
		ProjectPO projectPO=null;
		if(projectPOS.size()>0)
		{
			projectPO=projectPOS.get(0);
		}
		else {
			projectPO=new ProjectPO();
		}

		return projectPO;
	}

	@Override
	public ProjectVO findProjectwhole(Integer id) {
		//项目信息
		ProjectPOExample projectExample=new ProjectPOExample();
		projectExample.createCriteria().andMemberidEqualTo(id);
		List<ProjectPO> projectPOS = projectPOMapper.selectByExample(projectExample);
		ProjectPO projectPO=new ProjectPO();
		if(projectPOS.size()>0)
		{
			projectPO=projectPOS.get(0);
		}
		//project的id
		Integer projectId=projectPO.getId();
		//发起人信息
		MemberLaunchInfoPOExample launchinfoExample=new MemberLaunchInfoPOExample();
		launchinfoExample.createCriteria().andMemberidEqualTo(id);
		List<MemberLaunchInfoPO> memberLaunchInfoPOS = memberLaunchInfoPOMapper.selectByExample(launchinfoExample);
		MemberLauchInfoVO MemberLauchInfoVO=new MemberLauchInfoVO();
		if(memberLaunchInfoPOS.size()>0)
		{
			MemberLaunchInfoPO memberLaunchInfoPO = memberLaunchInfoPOS.get(0);
			BeanUtils.copyProperties(memberLaunchInfoPO, MemberLauchInfoVO);
		}
		//回报信息
		ReturnPOExample returnExample=new ReturnPOExample();
		returnExample.createCriteria().andProjectidEqualTo(projectId);
		List<ReturnPO> returnPOS = returnPOMapper.selectByExample(returnExample);
		ReturnVO ReturnVO=new ReturnVO();
		if(returnPOS.size()>0)
		{
			 ReturnPO returnPO=returnPOS.get(0);
			BeanUtils.copyProperties(returnPO, ReturnVO);
		}
		List<ReturnVO> listReturnVo=new ArrayList<>();
		listReturnVo.add(ReturnVO);
		//项目详情的图片
		List<Integer> projectIds=new ArrayList<>();
		projectIds.add(projectId);
		ProjectItemPicPOExample itempicExample=new ProjectItemPicPOExample();
		itempicExample.createCriteria().andProjectidIn(projectIds);
		List<ProjectItemPicPO> projectItemPicPOS = projectItemPicPOMapper.selectByExample(itempicExample);
		ProjectItemPicPO projectItemPicPO=new ProjectItemPicPO();
		if(projectItemPicPOS.size()>0)
		{
			projectItemPicPO=projectItemPicPOS.get(0);
		}
		List<String> listItempic=new ArrayList<>();
		listItempic.add(projectItemPicPO.getItemPicPath());
		//合并
		ProjectVO projectVO=new ProjectVO();
		projectVO.setProjectName(projectPO.getProjectName());
		projectVO.setProjectDescription(projectPO.getProjectDescription());
		projectVO.setMoney(projectPO.getMoney());
		projectVO.setDay(projectPO.getDay());
		projectVO.setHeaderPicturePath(projectPO.getHeaderPicturePath());
		projectVO.setMemberLauchInfoVO(MemberLauchInfoVO);
		projectVO.setReturnVOList(listReturnVo);
		projectVO.setDetailPicturePathList(listItempic);
		projectVO.setSupportmoney(projectPO.getSupportmoney());
		projectVO.setFollower(projectPO.getFollower());
		return projectVO;
	}

	@Override
	public String saveReceiving(Integer id, String receiveName, String receiveTel, String receiveAddress, String receiveMark) {
		MemberPO record=new MemberPO();
		record.setId(id);
		record.setUsername(receiveName);
		record.setEmail(receiveTel);
		record.setRealname(receiveAddress);
		record.setCardnum(receiveMark);
		int i = MemberPOMapper.updateByPrimaryKeySelective(record);
		if(i>0)

		{
			return "success";
		}
		return "false";
	}

	@Override
	public String applyProject(Integer id, Integer total_amount,String subject) {
		//查出po
		ProjectPOExample example=new ProjectPOExample();
		example.createCriteria().andProjectNameEqualTo(subject);
		List<ProjectPO> projectPOS = projectPOMapper.selectByExample(example);
		ProjectPO projectPO=projectPOS.get(0);
		if(projectPO.getSupportmoney()==null)
		{
			projectPO.setSupportmoney((long) 0);
		}
		projectPO.setSupportmoney(projectPO.getSupportmoney()+total_amount);
		if(projectPO.getFollower()==null)
		{
			projectPO.setFollower(0);
		}
		projectPO.setFollower(projectPO.getFollower()+1);
		int i = projectPOMapper.updateByExampleSelective(projectPO, example);
		//把项目id放入member
		MemberPO memberPO = MemberPOMapper.selectByPrimaryKey(id);
		memberPO.setApplyid(projectPO.getId()+"");
		MemberPOMapper.updateByPrimaryKeySelective(memberPO);
		if(i>0)
		{
			return "success";
		}
		return "false";
	}

	//查看支持的项目
	@Override
	public ProjectVO findprojectByApply(Integer id) {
		MemberPO memberPO = MemberPOMapper.selectByPrimaryKey(id);
		ProjectPO projectPO=null;
		ProjectVO projectVO=new ProjectVO();
		if(memberPO.getApplyid()==null || memberPO.getApplyid()=="")
		{
			projectPO=new ProjectPO();
		}else {
			 projectPO = projectPOMapper.selectByPrimaryKey(Integer.parseInt(memberPO.getApplyid()));
			 if(projectPO==null)
			 {
			 	return new ProjectVO();
			 }else {
				 //回报情况
				 ReturnPOExample returnExample=new ReturnPOExample();
				 returnExample.createCriteria().andProjectidEqualTo(projectPO.getId());
				 List<ReturnPO> returnPOS = returnPOMapper.selectByExample(returnExample);
				 ReturnVO ReturnVO=new ReturnVO();
				 if(returnPOS.size()>0)
				 {
					 ReturnPO returnPO=returnPOS.get(0);
					 BeanUtils.copyProperties(returnPO, ReturnVO);
				 }
				 List<ReturnVO> listReturnVo=new ArrayList<>();
				 listReturnVo.add(ReturnVO);
				 //
				 projectVO.setProjectName(projectPO.getProjectName());
				 projectVO.setMoney(projectPO.getMoney());
				 projectVO.setDay(projectPO.getDay());
				 projectVO.setCreatedate(projectPO.getCreatedate());
				 projectVO.setReturnVOList(listReturnVo);
			 }

		}

		return projectVO;
	}

	//删除支持的订单
	@Override
	public void deleteprojectByApply(Integer id) {
		MemberPO record=new MemberPO();
		record.setId(id);
		record.setApplyid("");
		int i = MemberPOMapper.updateByPrimaryKeySelective(record);
	}


}
