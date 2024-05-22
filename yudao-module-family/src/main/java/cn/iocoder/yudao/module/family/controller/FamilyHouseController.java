package cn.iocoder.yudao.module.family.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyHouseDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyHousePageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyHouseSaveVO;
import cn.iocoder.yudao.module.family.service.FamilyHouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 户房屋")
@RestController
@RequestMapping("/admin-api/family-house")
public class FamilyHouseController {

    @Autowired
    private FamilyHouseService familyHouseService;

    @GetMapping("/page")
    @Operation(summary = "获得户房屋列表分页")
    public CommonResult<PageResult<FamilyHouseDO>> getFamilyHousePage(@Valid FamilyHousePageReqVO pageReqVO) {
        PageResult<FamilyHouseDO> pageResult = familyHouseService.getFamilyHousePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/info")
    @Operation(summary = "获得单个房屋")
    public CommonResult<FamilyHouseDO> getInfo(@RequestParam("id") String id) {
        FamilyHouseDO info = familyHouseService.getInfo(id);
        return success(info);
    }

    @PostMapping("/add")
    @Operation(summary = "添加户房屋")
    public CommonResult<Boolean> addHouse(@Valid @RequestBody FamilyHouseSaveVO vo) {
        return success(familyHouseService.addHouse(vo));
    }

    @PostMapping("/update")
    @Operation(summary = "更新户成员")
    public CommonResult<Boolean> updateHouse(@Valid @RequestBody FamilyHouseSaveVO vo) {
        return success(familyHouseService.updateHouse(vo));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除户房屋")
    public CommonResult<Boolean> deleteHouse(@RequestParam("id") Long id) {
        return success(familyHouseService.deleteHouse(id));
    }
}
