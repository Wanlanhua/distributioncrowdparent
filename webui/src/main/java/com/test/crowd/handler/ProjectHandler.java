package com.test.crowd.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.test.crowd.api.MemberManagerRemoteService;
import com.test.crowd.api.ProjectOperationRemoteService;
import com.test.crowd.entity.ResultEntity;
import com.test.crowd.entity.po.MemberLaunchInfoPO;
import com.test.crowd.entity.po.ProjectPO;
import com.test.crowd.entity.vo.MemberConfirmInfoVO;
import com.test.crowd.entity.vo.MemberSignSuccessVO;
import com.test.crowd.entity.vo.ProjectVO;
import com.test.crowd.entity.vo.ReturnVO;
import com.test.crowd.util.CrowdConstant;
import com.test.crowd.util.CrowdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class ProjectHandler {
	
	@Autowired
	private ProjectOperationRemoteService projectOperationRemoteService;
	
	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	
	@Value(value="${oss.project.parent.folder}")
	private String ossProjectParentFolder;

	@Value(value="${oss.endpoint}")
	private String endpoint;

	@Value(value="${oss.accessKeyId}")
	private String accessKeyId;

	@Value(value="${oss.accessKeySecret}")
	private String accessKeySecret;

	@Value(value="${oss.bucketName}")
	private String bucketName;

	@Value(value="${oss.bucket.domain}")
	private String bucketDomain;


	// 删除支持的订单
	@RequestMapping("/project/to/delete/by/apply")
	public String DeleteProjectapply(HttpSession session,Model model) {
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		projectOperationRemoteService.deleteProjectInfoApply(memberSignSuccessVO.getToken());
		return "minecrowdfunding2";
	}
	//查找支持的项目
	@RequestMapping("/project/to/find/by/apply")
	public String findAProjectapply(HttpSession session,Model model) {
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		ResultEntity<ProjectVO> projectInfo = projectOperationRemoteService.findProjectInfoApply(memberSignSuccessVO.getToken());
		model.addAttribute("projectName",projectInfo.getData().getProjectName());
		model.addAttribute("money",projectInfo.getData().getMoney());
		model.addAttribute("day",projectInfo.getData().getDay());
		model.addAttribute("Createdate",projectInfo.getData().getCreatedate());
		model.addAttribute("Supportmoney",projectInfo.getData().getReturnVOList().get(0).getSupportmoney());
		model.addAttribute("Count",projectInfo.getData().getReturnVOList().get(0).getCount());
		model.addAttribute("Freight",projectInfo.getData().getReturnVOList().get(0).getFreight());
		return "minecrowdfunding2";
	}

	//确认配送信息
	@RequestMapping("/project/to/save/project/Receiving")
	@ResponseBody
	public String saveProjectReceiving(HttpSession session,String ReceiveName,String ReceiveTel,String ReceiveAddress,String ReceiveMark) {
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		String s = projectOperationRemoteService.saveProjectReceivingManager(memberSignSuccessVO.getToken(), ReceiveName, ReceiveTel, ReceiveAddress, ReceiveMark);
		return s;
	}

	//查找项目信息并返回
	@RequestMapping("/project/to/find/project")
	public String findAProject(HttpSession session,Model model) {
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		ResultEntity<ProjectPO> projectInfo = projectOperationRemoteService.findProjectInfo(memberSignSuccessVO.getToken());
		ProjectPO projectPO = projectInfo.getData();
		model.addAttribute("projectName",projectPO.getProjectName());
		model.addAttribute("money",projectPO.getMoney());
		model.addAttribute("day",projectPO.getDay());
		return "minecrowdfunding";
	}

	//查找项目、发起人信息、回报情况
	@RequestMapping("/project/to/find/project/whole")
	public String findWholeProject(HttpSession session,Model model) {
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		ResultEntity<ProjectVO> wholeEntity = projectOperationRemoteService.findProjectInfoWhole(memberSignSuccessVO.getToken());
		ProjectVO projectinfoWhole = wholeEntity.getData();
		model.addAttribute("projectinfoWhole",projectinfoWhole);
		return "project";
	}

	// 删除项目
	@ResponseBody
	@RequestMapping("/project/to/delete/project")
	public String DeleteProject(HttpSession session,Model model) {
		MemberSignSuccessVO memberSignSuccessVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
		projectOperationRemoteService.deleteProjectInfo(memberSignSuccessVO.getToken());
		return "SUCCESS";
	}

	//将redis中的所有数据存储到数据库中 跳转到我的页面
	@RequestMapping("/project/to/save/project/whole")
	public String tosaveProjectwhole(HttpSession session,Model model) {
		ProjectVO project = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
		ResultEntity<ProjectVO> Entity = projectOperationRemoteService.saveWholeProject(project.getMemberSignToken(), project.getProjectTempToken());
		ProjectVO pro = (ProjectVO) Entity.getData();
		model.addAttribute("projectName",pro.getProjectName());
		model.addAttribute("money",pro.getMoney());
		model.addAttribute("day",pro.getDay());
		model.addAttribute("freight",pro.getReturnVOList().get(0).getFreight());
		return "minecrowdfunding";
	}
	//上传确认信息 跳转到第四页
	@ResponseBody
	@RequestMapping("/project/to/save/project/confirminfo")
	public String tosaveProjectconfirminfo(MemberConfirmInfoVO memberConfirmInfoVO, HttpSession session) {
		ProjectVO project = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
		memberConfirmInfoVO.setMemberSignToken(project.getMemberSignToken());
		memberConfirmInfoVO.setProjectTempToken(project.getProjectTempToken());
		projectOperationRemoteService.saveConfirmInfomation(memberConfirmInfoVO);
		return "SUCCESS";
	}

	//上传回报信息 跳转到第三页
	@ResponseBody
	@RequestMapping("/project/to/save/project/returninfo")
	public String tosaveProjectreturninfo(ReturnVO returnVO, HttpSession session) {
		ProjectVO project = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
		returnVO.setMemberSignToken(project.getMemberSignToken());
		returnVO.setProjectTempToken(project.getProjectTempToken());
		projectOperationRemoteService.saveReturnInfromation(returnVO);
		return "SUCCESS";
	}

	//保存项目信息
	@ResponseBody
	@RequestMapping("/project/to/save/project/information.html")
	public String tosaveProjectinfomation(ProjectVO projectVOFront, HttpSession session) {
		ProjectVO project = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);
		projectVOFront.setMemberSignToken(project.getMemberSignToken());
		projectVOFront.setProjectTempToken(project.getProjectTempToken());
		projectOperationRemoteService.saveProjectInformation(projectVOFront);
		return "SUCCESS";
	}

	//初始化个空项目
	@RequestMapping("/project/to/create/project/page")
	public String toCreateProjectPage(HttpSession session, Model model) {

		// 1.获取当前登录的用户
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

		// 2.检查signVO为null
		if(signVO == null) {
			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_DENIED);
			return "member-login";
		}

		// 3.获取token数据
		String token = signVO.getToken();

		// 4.根据token查询MemberLaunchInfo信息
		ResultEntity<MemberLaunchInfoPO> resultEntity = memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(token);

		// 5.检查结果
		String result = resultEntity.getResult();
		if(ResultEntity.FAILED.equals(result)) {
			throw new RuntimeException(resultEntity.getMessage());
		}

		// 6.获取查询结果
		MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();

		// 7.存入模型
		model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);

		return "project-create";
	}

	//同意协议
	@RequestMapping("/project/agree/protocol")
	public String agreeProtocol(HttpSession session, Model model) {

		// 1.登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

		if(signVO == null) {

			model.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_DENIED);

			return "member-login";
		}

		// 2.从当前signVO对象中获取token
		String token = signVO.getToken();

		// 3.调用远程方法初始化Project
		ResultEntity<ProjectVO> resultEntity = projectOperationRemoteService.initCreation(token);

		String result = resultEntity.getResult();

		if(ResultEntity.FAILED.equals(result)) {
			throw new RuntimeException(resultEntity.getMessage());
		}

		// ※补充操作：将初始化项目的信息存入Session
		ProjectVO projectVO = resultEntity.getData();
		session.setAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT, projectVO);

		return "redirect:/project/to/create/project/page";
	}


	//上传详细图片
	@ResponseBody
	@RequestMapping("/project/upload/detail/picture")
	public ResultEntity<String> uploadDetailPicture(
				HttpSession session,
				@RequestParam("detailPicture") List<MultipartFile> detailPictureList
			) throws IOException {

		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

		if(signVO == null) {

			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}

		// 遍历用户上传的文件
		if(!CrowdUtils.collectionEffectiveCheck(detailPictureList)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}

		List<String> detailPicturePathList = new ArrayList<>();

		for (MultipartFile detailPicture : detailPictureList) {
			boolean empty = detailPicture.isEmpty();

			if(empty) {
				continue ;
			}

			InputStream inputStream = detailPicture.getInputStream();

			String originalFileName = detailPicture.getOriginalFilename();

			String fileName = CrowdUtils.generateFileName(originalFileName);

			String folderName = CrowdUtils.generateFolderNameByDate(ossProjectParentFolder);

			CrowdUtils.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);

			String detailPicturePath = bucketDomain + "/" + folderName + "/" + fileName;

			detailPicturePathList.add(detailPicturePath);
		}

		// 获取保存头图所需要的相关信息
		String memberSignToken = signVO.getToken();

		ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);

		String projectTempToken = projectVO.getProjectTempToken();

		return projectOperationRemoteService.saveDetailPicturePathList(memberSignToken, projectTempToken, detailPicturePathList);

	}


	//上传头图
	@ResponseBody
	@RequestMapping("/project/upload/head/picture")
	public ResultEntity<String> uploadHeadPicture(
			HttpSession session,
			@RequestParam("headPicture") MultipartFile headPicture) throws IOException {

		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);

		if(signVO == null) {

			return ResultEntity.failed(CrowdConstant.MESSAGE_ACCESS_DENIED);
		}

		// 排除上传文件为空的情况
		if(headPicture.isEmpty()) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}

		// 准备工作
		String originalFileName = headPicture.getOriginalFilename();

		String fileName = CrowdUtils.generateFileName(originalFileName);

		String folderName = CrowdUtils.generateFolderNameByDate(ossProjectParentFolder);

		InputStream inputStream = headPicture.getInputStream();

		// 执行上传
		CrowdUtils.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);

		// 拼接headerPicturePath
		String headerPicturePath = bucketDomain + "/" + folderName + "/" + fileName;

		// 获取保存头图所需要的相关信息
		String memberSignToken = signVO.getToken();

		ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_INITED_PROJECT);

		String projectTempToken = projectVO.getProjectTempToken();

		// 保存头图相关信息
		return projectOperationRemoteService.saveHeadPicturePath(memberSignToken, projectTempToken, headerPicturePath);
	}




}
