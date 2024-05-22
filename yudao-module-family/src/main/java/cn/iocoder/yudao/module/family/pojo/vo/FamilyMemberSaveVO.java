package cn.iocoder.yudao.module.family.pojo.vo;

import lombok.Data;

@Data
public class FamilyMemberSaveVO {

    private Long id;

    private String familyNo;

    private String name;

    private Integer relationship;

    private String idNumber;

}
