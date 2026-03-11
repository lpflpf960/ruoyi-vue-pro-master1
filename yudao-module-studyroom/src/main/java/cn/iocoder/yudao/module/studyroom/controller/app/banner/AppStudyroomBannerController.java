package cn.iocoder.yudao.module.studyroom.controller.app.banner;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.studyroom.controller.app.banner.vo.AppStudyroomBannerRespVO;
import cn.iocoder.yudao.module.studyroom.service.banner.StudyroomBannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 自习室轮播图")
@RestController
@RequestMapping("/app-api/studyroom/banner")
@Validated
public class AppStudyroomBannerController {

    @Resource
    private StudyroomBannerService studyroomBannerService;

    @GetMapping("/list")
    @Operation(summary = "获得首页轮播图列表")
    @PermitAll
    public CommonResult<List<AppStudyroomBannerRespVO>> getBannerList(
            @RequestParam(value = "roomId", required = false) Long roomId) {
        return success(studyroomBannerService.getAppBannerList(roomId));
    }

}