package com.test.crowd.service.api;

import com.test.crowd.entity.po.ProjectPO;
import com.test.crowd.entity.vo.ProjectVO;

public interface ProjectService {

    void saveProject(ProjectVO projectVO, String memberId);

    void deleteproject(Integer id);

    ProjectPO findProject(Integer id);

    ProjectVO findProjectwhole(Integer id);

    String saveReceiving(Integer id, String receiveName, String receiveTel, String receiveAddress, String receiveMark);

    String applyProject(Integer id,Integer  total_amount,String subject);

    ProjectVO findprojectByApply(Integer id);

    void deleteprojectByApply(Integer id);
}
