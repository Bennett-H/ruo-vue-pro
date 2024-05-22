package cn.iocoder.yudao.module.family.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FamilySaveVO {

    private Long id;

    @NotEmpty(message = "户编号不能为空")
    private String familyNo;

    private String familyAddress;

}
