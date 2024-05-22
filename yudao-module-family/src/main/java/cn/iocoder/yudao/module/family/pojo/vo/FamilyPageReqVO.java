package cn.iocoder.yudao.module.family.pojo.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import lombok.Data;

@Data
public class FamilyPageReqVO extends PageParam {

    private String familyHeadName;

    private String familyMemberName;

    private Integer familyTotalNumsStart;

    private Integer familyTotalNumsEnd;

    private Double houseTotalAreasStart;

    private Double houseTotalAreasEnd;

}
