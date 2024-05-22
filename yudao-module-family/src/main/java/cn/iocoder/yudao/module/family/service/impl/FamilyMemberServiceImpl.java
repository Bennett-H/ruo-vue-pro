package cn.iocoder.yudao.module.family.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.constants.RelationshipConstants;
import cn.iocoder.yudao.module.family.dao.FamilyHouseMapper;
import cn.iocoder.yudao.module.family.dao.FamilyMapper;
import cn.iocoder.yudao.module.family.dao.FamilyMemberMapper;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyDO;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyHouseDO;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyMemberDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyMemberPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyMemberSaveVO;
import cn.iocoder.yudao.module.family.service.FamilyMemberService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private FamilyMemberMapper familyMemberMapper;

    @Autowired
    private FamilyHouseMapper familyHouseMapper;

    @Override
    public PageResult<FamilyMemberDO> getFamilyMemberPage(FamilyMemberPageReqVO pageReqVO) {
        PageResult<FamilyMemberDO> pageResult = familyMemberMapper.selectPage(pageReqVO,
                Wrappers.<FamilyMemberDO>lambdaQuery().eq(FamilyMemberDO::getFamilyNo, pageReqVO.getFamilyNo()));
        return pageResult;
    }

    @Override
    public List<FamilyMemberDO> getFamilyMemberList(String familyNo) {
        return familyMemberMapper.selectList(FamilyMemberDO::getFamilyNo, familyNo);
    }

    @Override
    public FamilyMemberDO getInfo(String id) {
        return familyMemberMapper.selectById(id);
    }

    @Override
    public CommonResult<String> addMember(FamilyMemberSaveVO vo) {
        CommonResult<String> result = new CommonResult<>();
        if (!IdcardUtil.isValidCard(vo.getIdNumber())) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("请输入正确身份证格式");
            return result;
        }
        String idNumber = vo.getIdNumber();
        if (!validateVO(vo)) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("身份证已重复");
            return result;
        }
        DateTime birthDate = idNumberToBirthDate(idNumber);
        FamilyMemberDO familyMemberDO = FamilyMemberDO.builder().familyNo(vo.getFamilyNo()).relationship(vo.getRelationship())
                .idNumber(idNumber).name(vo.getName()).birthDate(birthDate).build();
        // 是户主
        if (RelationshipConstants.HOUSE_HEAD.equals(vo.getRelationship())) {
            familyMapper.update(Wrappers.<FamilyDO>lambdaUpdate()
                    .eq(FamilyDO::getFamilyNo, vo.getFamilyNo())
                    .set(FamilyDO::getFamilyHeadNumber, vo.getIdNumber()));
            familyMemberMapper.update(Wrappers.<FamilyMemberDO>lambdaUpdate()
                    .eq(FamilyMemberDO::getFamilyNo, vo.getFamilyNo())
                    .set(FamilyMemberDO::getRelationship, RelationshipConstants.NAN));
        }
        familyMemberMapper.insert(familyMemberDO);
        return CommonResult.success("");
    }

    private boolean validateVO(FamilyMemberSaveVO vo) {
        LambdaQueryWrapper<FamilyMemberDO> wrapper = Wrappers.<FamilyMemberDO>lambdaQuery()
                .eq(FamilyMemberDO::getIdNumber, vo.getIdNumber())
                .ne(FamilyMemberDO::getId, vo.getId());
        List<FamilyMemberDO> list = familyMemberMapper.selectList(wrapper);
        return CollectionUtil.isEmpty(list);
    }

    @Override
    public CommonResult<String> updateMember(FamilyMemberSaveVO vo) {
        CommonResult<String> result = new CommonResult<>();
        if (!IdcardUtil.isValidCard(vo.getIdNumber())) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("请输入正确身份证格式");
            return result;
        }
        if (!validateVO(vo)) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("身份证已重复");
            return result;
        }
        // 是户主
        if (RelationshipConstants.HOUSE_HEAD.equals(vo.getRelationship())) {
            familyMapper.update(Wrappers.<FamilyDO>lambdaUpdate()
                    .eq(FamilyDO::getFamilyNo, vo.getFamilyNo())
                    .set(FamilyDO::getFamilyHeadNumber, vo.getIdNumber()));
            familyMemberMapper.update(Wrappers.<FamilyMemberDO>lambdaUpdate()
                    .eq(FamilyMemberDO::getFamilyNo, vo.getFamilyNo())
                    .set(FamilyMemberDO::getRelationship, RelationshipConstants.NAN));
        }
        FamilyMemberDO familyMemberDO = familyMemberMapper.selectById(vo.getId());
        // 从户主 改为非户主
        if (RelationshipConstants.HOUSE_HEAD.equals(familyMemberDO.getRelationship()) && !RelationshipConstants.HOUSE_HEAD.equals(vo.getRelationship())) {
            familyMapper.update(Wrappers.<FamilyDO>lambdaUpdate()
                    .eq(FamilyDO::getFamilyNo, familyMemberDO.getFamilyNo())
                    .set(FamilyDO::getFamilyHeadNumber, ""));
        }
        String idNumber = vo.getIdNumber();
        DateTime birthDate = idNumberToBirthDate(idNumber);
        familyMemberMapper.update(Wrappers.<FamilyMemberDO>lambdaUpdate()
                .eq(FamilyMemberDO::getId, vo.getId())
                .set(FamilyMemberDO::getRelationship, vo.getRelationship())
                .set(FamilyMemberDO::getName, vo.getName())
                .set(FamilyMemberDO::getIdNumber, vo.getIdNumber())
                .set(FamilyMemberDO::getBirthDate, birthDate));
        familyHouseMapper.update(Wrappers.<FamilyHouseDO>lambdaUpdate()
                .eq(FamilyHouseDO::getIdNumber, familyMemberDO.getIdNumber())
                .set(FamilyHouseDO::getIdNumber, vo.getIdNumber())
                .set(FamilyHouseDO::getName, vo.getName()));
        return CommonResult.success("");
    }

    @Override
    public CommonResult<String> deleteMember(String idNumber) {
        CommonResult<String> result = new CommonResult<>();
        if(!canDelete(idNumber)) {
            result.setCode(GlobalErrorCodeConstants.BAD_REQUEST.getCode());
            result.setMsg("请先删除指标信息");
            return result;
        }
        FamilyMemberDO familyMemberDO = familyMemberMapper.selectOne(FamilyMemberDO::getIdNumber, idNumber);
        // 是户主
        if (RelationshipConstants.HOUSE_HEAD.equals(familyMemberDO.getRelationship())) {
            familyMapper.update(Wrappers.<FamilyDO>lambdaUpdate()
                    .eq(FamilyDO::getFamilyNo, familyMemberDO.getFamilyNo())
                    .set(FamilyDO::getFamilyHeadNumber, ""));
        }
        familyMemberMapper.delete(FamilyMemberDO::getIdNumber, idNumber);
        return CommonResult.success("");
    }

    private DateTime idNumberToBirthDate(String idNumber) {
        return DateUtil.parse(idNumber.substring(6, 14), "yyyyMMdd");
    }

    private boolean canDelete(String idNumber) {
        List<FamilyHouseDO> list = familyHouseMapper.selectList(FamilyHouseDO::getIdNumber, idNumber);
        return CollectionUtil.isEmpty(list);
    }
}
