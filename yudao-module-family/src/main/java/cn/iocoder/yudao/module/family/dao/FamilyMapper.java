package cn.iocoder.yudao.module.family.dao;

import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FamilyMapper extends BaseMapperX<FamilyDO> {

    List<FamilyPageRespVO> getFamilyTablePage(FamilyPageReqVO pageReqVO);

    Long countTotal(FamilyPageReqVO pageReqVO);

    FamilyPageRespVO getOne(@Param("id") Long id);
}
