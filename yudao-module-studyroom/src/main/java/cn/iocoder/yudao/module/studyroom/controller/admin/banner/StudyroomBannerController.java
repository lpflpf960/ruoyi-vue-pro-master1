package cn.iocoder.yudao.module.studyroom.controller.admin.banner;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerRespVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerSaveReqVO;
import cn.iocoder.yudao.module.studyroom.convert.banner.StudyroomBannerConvert;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.banner.StudyroomBannerDO;
import cn.iocoder.yudao.module.studyroom.service.banner.StudyroomBannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 自习室轮播图")
@RestController
@RequestMapping("/studyroom/banner")
@Validated
public class StudyroomBannerController {

    @Resource
    private StudyroomBannerService studyroomBannerService;

    @PostMapping("/create")
    @Operation(summary = "创建轮播图")
    @PreAuthorize("@ss.hasPermission('studyroom:banner:create')")
    public CommonResult<Long> createBanner(@Valid @RequestBody StudyroomBannerSaveReqVO createReqVO) {
        return success(studyroomBannerService.createBanner(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新轮播图")
    @PreAuthorize("@ss.hasPermission('studyroom:banner:update')")
    public CommonResult<Boolean> updateBanner(@Valid @RequestBody StudyroomBannerSaveReqVO updateReqVO) {
        studyroomBannerService.updateBanner(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除轮播图")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:banner:delete')")
    public CommonResult<Boolean> deleteBanner(@RequestParam("id") Long id) {
        studyroomBannerService.deleteBanner(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得轮播图")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:banner:query')")
    public CommonResult<StudyroomBannerRespVO> getBanner(@RequestParam("id") Long id) {
        StudyroomBannerDO banner = studyroomBannerService.getBanner(id);
        return success(StudyroomBannerConvert.INSTANCE.convert(banner));
    }

    @GetMapping("/page")
    @Operation(summary = "获得轮播图分页")
    @PreAuthorize("@ss.hasPermission('studyroom:banner:query')")
    public CommonResult<PageResult<StudyroomBannerRespVO>> getBannerPage(@Valid StudyroomBannerPageReqVO pageReqVO) {
        PageResult<StudyroomBannerDO> pageResult = studyroomBannerService.getBannerPage(pageReqVO);
        return success(StudyroomBannerConvert.INSTANCE.convertPage(pageResult));
    }

}