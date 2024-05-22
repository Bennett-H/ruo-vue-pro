package cn.iocoder.yudao.module.family.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyPageRespVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilySaveVO;
import cn.iocoder.yudao.module.family.service.FamilyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 户")
@RestController
@RequestMapping("/admin-api/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @GetMapping("/page")
    @Operation(summary = "获得户列表分页")
    public CommonResult<PageResult<FamilyPageRespVO>> getFamilyTablePage(@Valid FamilyPageReqVO pageReqVO) {
        PageResult<FamilyPageRespVO> pageResult = familyService.getFamilyTablePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/info")
    @Operation(summary = "获得单个户信息")
    public CommonResult<FamilyPageRespVO> getFamilyInfo(@RequestParam("id") Long id) {
        FamilyPageRespVO info = familyService.getInfo(id);
        return success(info);
    }

    @PostMapping("/add")
    @Operation(summary = "添加户")
    public CommonResult<String> addFamily(@Valid @RequestBody FamilySaveVO vo) {
        return familyService.addFamily(vo);
    }

    @PostMapping("/update")
    @Operation(summary = "更新户")
    public CommonResult<String> updateFamily(@Valid @RequestBody FamilySaveVO vo) {
        return familyService.updateFamily(vo);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除户")
    public CommonResult<Boolean> deleteFamily(@RequestParam("familyNo") String familyNo) {
        return success(familyService.deleteFamily(familyNo));
    }
}
