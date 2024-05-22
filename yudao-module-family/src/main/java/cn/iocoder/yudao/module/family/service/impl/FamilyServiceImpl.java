package cn.iocoder.yudao.module.family.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.dao.FamilyHouseMapper;
import cn.iocoder.yudao.module.family.dao.FamilyMapper;
import cn.iocoder.yudao.module.family.dao.FamilyMemberMapper;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyDO;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyHouseDO;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyMemberDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageRespVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilySaveVO;
import cn.iocoder.yudao.module.family.service.FamilyService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private FamilyMemberMapper familyMemberMapper;

    @Autowired
    private FamilyHouseMapper familyHouseMapper;

    @Override
    public PageResult<FamilyPageRespVO> getFamilyTablePage(FamilyPageReqVO pageReqVO) {
        pageReqVO.setPageNo((pageReqVO.getPageNo() - 1) * pageReqVO.getPageSize());
        List<FamilyPageRespVO> list = familyMapper.getFamilyTablePage(pageReqVO);
        PageResult<FamilyPageRespVO> result = new PageResult<>();
        result.setList(list);
        result.setTotal(familyMapper.countTotal(pageReqVO));
        return result;
    }

    @Override
    public FamilyPageRespVO getInfo(Long id) {
        return familyMapper.getOne(id);
    }

    @Override
    public CommonResult<String> addFamily(FamilySaveVO vo) {
        CommonResult<String> result = new CommonResult<>();
        List<FamilyDO> familyDOS = familyMapper.selectList(FamilyDO::getFamilyNo, vo.getFamilyNo());
        if (CollectionUtil.isNotEmpty(familyDOS)) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("户编号已存在");
            return result;
        }
        FamilyDO familyDO = FamilyDO.builder().familyNo(vo.getFamilyNo()).familyAddress(vo.getFamilyAddress()).build();
        familyMapper.insert(familyDO);
        return CommonResult.success("");
    }

    @Override
    public CommonResult<String> updateFamily(FamilySaveVO vo) {
        CommonResult<String> result = new CommonResult<>();
        List<FamilyDO> familyDOS = familyMapper.selectList(Wrappers.<FamilyDO>lambdaQuery().eq(FamilyDO::getFamilyNo, vo.getFamilyNo()).ne(FamilyDO::getId, vo.getId()));
        if (CollectionUtil.isNotEmpty(familyDOS)) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("户编号已存在");
            return result;
        }
        FamilyDO familyDO = FamilyDO.builder().id(vo.getId()).familyNo(vo.getFamilyNo()).familyAddress(vo.getFamilyAddress()).build();
        familyMapper.updateById(familyDO);
        return CommonResult.success("");
    }

    @Override
    public boolean deleteFamily(String familyNo) {
        familyMapper.delete(FamilyDO::getFamilyNo, familyNo);
        familyHouseMapper.delete(FamilyHouseDO::getFamilyNo, familyNo);
        familyMemberMapper.delete(FamilyMemberDO::getFamilyNo, familyNo);
        return true;
    }
}
