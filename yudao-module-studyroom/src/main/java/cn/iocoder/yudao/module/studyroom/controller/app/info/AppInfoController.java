package cn.iocoder.yudao.module.studyroom.controller.app.info;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.studyroom.controller.app.info.vo.AppInfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.info.vo.AppInfoRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.info.InfoDO;
import cn.iocoder.yudao.module.studyroom.enums.InfoCategoryEnum;
import cn.iocoder.yudao.module.studyroom.service.info.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 自习室信息管理")
@RestController
@RequestMapping("/studyroom/info")
@Validated
public class AppInfoController {

    @Resource
    private InfoService infoService;

    @GetMapping("/get")
    @Operation(summary = "获得信息详情")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<AppInfoRespVO> getInfo(@RequestParam("id") Long id,
                                               @RequestParam(value = "roomId", required = false) Long roomId) {
        return success(convert(infoService.getAppInfo(id, roomId)));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信息分页")
    public CommonResult<PageResult<AppInfoRespVO>> getInfoPage(@Valid AppInfoPageReqVO pageReqVO) {
        PageResult<InfoDO> pageResult = infoService.getAppInfoPage(pageReqVO);
        List<AppInfoRespVO> list = pageResult.getList().stream().map(this::convert).collect(Collectors.toList());
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    private AppInfoRespVO convert(InfoDO info) {
        if (info == null) {
            return null;
        }
        AppInfoRespVO respVO = BeanUtils.toBean(info, AppInfoRespVO.class);
        respVO.setCategoryName(InfoCategoryEnum.getNameByCategory(info.getCategory()));
        return respVO;
    }
}