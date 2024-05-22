package cn.iocoder.yudao.module.family.service;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyMemberDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyMemberPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyMemberSaveVO;

import java.util.List;

public interface FamilyMemberService {

    PageResult<FamilyMemberDO> getFamilyMemberPage(FamilyMemberPageReqVO pageReqVO);

    List<FamilyMemberDO>  getFamilyMemberList(String familyNo);

    FamilyMemberDO getInfo(String id);

    CommonResult<String> addMember(FamilyMemberSaveVO vo);

    CommonResult<String> updateMember(FamilyMemberSaveVO vo);

    CommonResult<String> deleteMember(String idNumber);
}
