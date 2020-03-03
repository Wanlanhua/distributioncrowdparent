package com.test.crowd.api;

import java.util.List;

import com.test.crowd.entity.ResultEntity;
import com.test.crowd.entity.po.ProjectPO;
import com.test.crowd.entity.vo.MemberConfirmInfoVO;
import com.test.crowd.entity.vo.ProjectVO;
import com.test.crowd.entity.vo.ReturnVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient("project-manager")
public interface ProjectOperationRemoteService {
    //支持项目
    @RequestMapping("/project/manager/apply/project")
    public String applyProjectInfo(@RequestParam("token") String token,@RequestParam("total_amount") Integer  total_amount,@RequestParam("subject") String subject);
    //确认配送信息
    @RequestMapping("/project/manager/save/project/Receiving")
    public String saveProjectReceivingManager(@RequestParam("token") String token,@RequestParam("ReceiveName")String ReceiveName,@RequestParam("ReceiveTel")String ReceiveTel,@RequestParam("ReceiveAddress")String ReceiveAddress,@RequestParam("ReceiveMark")String ReceiveMark);
    //查找项目信息
    @RequestMapping("/project/manager/find/project")
    public ResultEntity<ProjectPO> findProjectInfo(@RequestParam("token") String token);
    //查找支持的项目
    @RequestMapping("/project/manager/find/project/apply")
    public ResultEntity<ProjectVO> findProjectInfoApply(@RequestParam("token") String token);
    //删除支持的订单
    @RequestMapping("/project/manager/delete/project/apply")
    public void deleteProjectInfoApply(@RequestParam("token") String token);
    //删除项目
    @RequestMapping("/project/manager/delete/project")
    public void deleteProjectInfo(@RequestParam("token") String token);

    //查找项目、发起人信息、回报情况
    @RequestMapping("/project/manager/find/project/whole")
    public ResultEntity<ProjectVO> findProjectInfoWhole(@RequestParam("token") String token);

    //存整个项目信息
    @RequestMapping("/project/manager/save/whole/project")
    public ResultEntity<ProjectVO> saveWholeProject(
            @RequestParam("memberSignToken") String memberSignToken,
            @RequestParam("projectTempToken") String projectTempToken);

    //存确认信息
    @RequestMapping("/project/manager/save/confirm/infomation")
    public ResultEntity<String> saveConfirmInfomation(
            @RequestBody MemberConfirmInfoVO memberConfirmInfoVO);

    //存回报信息
    @RequestMapping("/project/manager/save/return/infromation")
    public ResultEntity<String> saveReturnInfromation(
            @RequestBody ReturnVO returnVO);

    //存项目信息
    @RequestMapping("/project/manager/save/project/information")
    public ResultEntity<String> saveProjectInformation(
            @RequestBody ProjectVO projectVOFront);

    //存详细图片
    @RequestMapping("/project/manager/save/detail/picture/path/list")
    public ResultEntity<String> saveDetailPicturePathList(@RequestParam("memberSignToken") String memberSignToken,
                                                          @RequestParam("projectTempToken") String projectTempToken,
                                                          @RequestParam("detailPicturePathList") List<String> detailPicturePathList);

    //存头图
    @RequestMapping("/project/manager/save/head/picture/path")
    public ResultEntity<String> saveHeadPicturePath(@RequestParam("memberSignToken") String memberSignToken,
                                                    @RequestParam("projectTempToken") String projectTempToken,
                                                    @RequestParam("headerPicturePath") String headerPicturePath);

    //初始化项目
    @RequestMapping("/project/manager/initCreation")
    public ResultEntity<ProjectVO> initCreation(
            @RequestParam("memberSignToken") String memberSignToken);
}
