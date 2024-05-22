package cn.iocoder.yudao.module.family.pojo.vo;

import lombok.Data;

@Data
public class FamilyPageRespVO {

    private Long id;

    private String familyNo;

    private String familyHeadName;

    private String familyHeadNumber;

    private String familyAddress;

    private Integer familyTotalNum;

    private Double familyTotalArea;

    private Integer familyTotalHouseNum;

}
