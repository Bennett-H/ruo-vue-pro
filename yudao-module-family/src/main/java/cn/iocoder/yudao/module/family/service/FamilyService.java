package cn.iocoder.yudao.module.family.service;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageRespVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilySaveVO;

public interface FamilyService {

    PageResult<FamilyPageRespVO> getFamilyTablePage(FamilyPageReqVO pageReqVO);

    FamilyPageRespVO getInfo(Long id);

    CommonResult<String> addFamily(FamilySaveVO vo);

    CommonResult<String> updateFamily(FamilySaveVO vo);

    boolean deleteFamily(String familyNo);
}
