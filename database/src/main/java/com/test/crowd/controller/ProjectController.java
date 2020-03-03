package com.test.crowd.controller;

import com.test.crowd.entity.ResultEntity;
import com.test.crowd.entity.po.ProjectPO;
import com.test.crowd.entity.vo.ProjectVO;
import com.test.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value="/save/project/remote/{memberId}",method = RequestMethod.POST)
	public ResultEntity<String> saveProjectRemote(
			@RequestBody ProjectVO projectVO,
			@PathVariable("memberId") String memberId) {
		
		try {
			projectService.saveProject(projectVO, memberId);
			
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
	}

	@RequestMapping("/retrieve/project/delete")
	void deleteProjectRemote(@RequestParam("id") Integer id)
	{
		projectService.deleteproject(id);
	}
	@RequestMapping("/retrieve/project/find")
	ResultEntity<ProjectPO> findProjectRemote(@RequestParam("id") Integer id)
	{
		ProjectPO projectPO = projectService.findProject(id);
		ResultEntity r=new ResultEntity();
		r.setData(projectPO);
		return r;
	}
	@RequestMapping("/retrieve/project/find/whole")
	ResultEntity<ProjectVO> findwholeProjectRemote(@RequestParam("id") Integer id)
	{
		ProjectVO projectwhole = projectService.findProjectwhole(id);
		ResultEntity<ProjectVO> result=new ResultEntity<>();
		result.setData(projectwhole);
		return result;
	}

	@RequestMapping("/retrieve/save/project/Receiving")
	public String saveProjectReceivingRetrieve(@RequestParam("id") Integer id,@RequestParam("ReceiveName")String ReceiveName,@RequestParam("ReceiveTel")String ReceiveTel,@RequestParam("ReceiveAddress")String ReceiveAddress,@RequestParam("ReceiveMark")String ReceiveMark)
	{
		String s = projectService.saveReceiving(id, ReceiveName, ReceiveTel, ReceiveAddress, ReceiveMark);
		return s;
	}
	@RequestMapping("/retrieve/apply/project")
	public  String applywholeProjectRemote(@RequestParam("id") Integer id,@RequestParam("total_amount") Integer  total_amount,@RequestParam("subject") String subject)
	{
		String result = projectService.applyProject(id,total_amount,subject);
		return result;
	}

	@RequestMapping("/retrieve/project/find/by/aply")
	ResultEntity<ProjectVO> findProjectApplyRemote(@RequestParam("id") Integer id)
	{
		ProjectVO ProjectVO = projectService.findprojectByApply(id);
		ResultEntity<ProjectVO> resultEntity=new ResultEntity<>();
		resultEntity.setData(ProjectVO);
		return resultEntity;
	}

	@RequestMapping("/retrieve/project/delete/by/apply")
	void deleteProjectApplyRemote(@RequestParam("id") Integer id)
	{
		projectService.deleteprojectByApply(id);
	}


}
