package cn.iocoder.yudao.module.family.service.impl;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.dao.FamilyHouseMapper;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyHouseDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyHousePageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyHouseSaveVO;
import cn.iocoder.yudao.module.family.service.FamilyHouseService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyHouseServiceImpl implements FamilyHouseService {

    @Autowired
    private FamilyHouseMapper familyHouseMapper;

    @Override
    public PageResult<FamilyHouseDO> getFamilyHousePage(FamilyHousePageReqVO pageReqVO) {
        PageResult<FamilyHouseDO> pageResult = familyHouseMapper.selectPage(pageReqVO,
                Wrappers.<FamilyHouseDO>lambdaQuery().eq(FamilyHouseDO::getFamilyNo, pageReqVO.getFamilyNo()));
        return pageResult;
    }

    @Override
    public FamilyHouseDO getInfo(String id) {
        return familyHouseMapper.selectById(id);
    }

    @Override
    public boolean addHouse(FamilyHouseSaveVO vo) {
        FamilyHouseDO familyHouseDO = FamilyHouseDO.builder().houseArea(vo.getHouseArea()).familyNo(vo.getFamilyNo())
                .region(vo.getRegion()).name(vo.getName()).idNumber(vo.getIdNumber()).build();
        familyHouseMapper.insert(familyHouseDO);
        return true;
    }

    @Override
    public boolean updateHouse(FamilyHouseSaveVO vo) {
        familyHouseMapper.update(Wrappers.<FamilyHouseDO>lambdaUpdate()
                .eq(FamilyHouseDO::getId, vo.getId())
                .set(FamilyHouseDO::getRegion, vo.getRegion())
                .set(FamilyHouseDO::getName, vo.getName())
                .set(FamilyHouseDO::getIdNumber, vo.getIdNumber())
                .set(FamilyHouseDO::getHouseArea, vo.getHouseArea()));
        return true;
    }

    @Override
    public boolean deleteHouse(Long id) {
        familyHouseMapper.delete(FamilyHouseDO::getId, id);
        return true;
    }
}
