package cn.iocoder.yudao.module.family.pojo.dal;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("family")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FamilyDO extends BaseDO {

    private Long id;

    private String familyNo;

    private String familyHeadNumber;

    private String familyAddress;

}
