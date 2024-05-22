package cn.iocoder.yudao.module.family.controller;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.family.pojo.dal.FamilyMemberDO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyMemberPageReqVO;
import cn.iocoder.yudao.module.family.pojo.vo.FamilyMemberSaveVO;
import cn.iocoder.yudao.module.family.service.FamilyMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 户成员")
@RestController
@RequestMapping("/admin-api/family-member")
public class FamilyMemberController {

    @Autowired
    private FamilyMemberService familyMemberService;

    @GetMapping("/page")
    @Operation(summary = "获得户列表分页")
    public CommonResult<PageResult<FamilyMemberDO>> getFamilyMemberPage(@Valid FamilyMemberPageReqVO pageReqVO) {
        PageResult<FamilyMemberDO> pageResult = familyMemberService.getFamilyMemberPage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/list")
    @Operation(summary = "获得房屋成员列表")
    public CommonResult<List<FamilyMemberDO>> getFamilyMemberList(@RequestParam("familyNo") String familyNo) {
        List<FamilyMemberDO> list = familyMemberService.getFamilyMemberList(familyNo);
        return success(list);
    }

    @GetMapping("/info")
    @Operation(summary = "获得单个成员信息")
    public CommonResult<FamilyMemberDO> getInfo(@RequestParam("id") String id) {
        FamilyMemberDO info = familyMemberService.getInfo(id);
        return success(info);
    }

    @PostMapping("/add")
    @Operation(summary = "添加户成员")
    public CommonResult<String> addMember(@Valid @RequestBody FamilyMemberSaveVO vo) {
        return familyMemberService.addMember(vo);
    }

    @PostMapping("/update")
    @Operation(summary = "更新户成员")
    public CommonResult<String> updateMember(@Valid @RequestBody FamilyMemberSaveVO vo) {
        return familyMemberService.updateMember(vo);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除户成员")
    public CommonResult<String> deleteMember(@RequestParam("idNumber") String idNumber) {
        return familyMemberService.deleteMember(idNumber);
    }
}
