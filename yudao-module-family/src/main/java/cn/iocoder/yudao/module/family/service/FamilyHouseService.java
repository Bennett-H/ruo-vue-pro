package cn.iocoder.yudao.module.family.service;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyHouseDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyHousePageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyHouseSaveVO;

public interface FamilyHouseService {

    PageResult<FamilyHouseDO> getFamilyHousePage(FamilyHousePageReqVO pageReqVO);

    FamilyHouseDO getInfo(String id);

    boolean addHouse(FamilyHouseSaveVO vo);

    boolean updateHouse(FamilyHouseSaveVO vo);

    boolean deleteHouse(Long id);
}
