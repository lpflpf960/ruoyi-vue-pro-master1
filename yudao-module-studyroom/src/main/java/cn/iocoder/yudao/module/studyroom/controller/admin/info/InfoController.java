package cn.iocoder.yudao.module.studyroom.controller.admin.info;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoRespVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoSaveReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.info.InfoDO;
import cn.iocoder.yudao.module.studyroom.enums.InfoCategoryEnum;
import cn.iocoder.yudao.module.studyroom.enums.InfoStatusEnum;
import cn.iocoder.yudao.module.studyroom.service.info.InfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 自习室信息管理")
@RestController
@RequestMapping("/studyroom/info")
@Validated
public class InfoController {

    @Resource
    private InfoService infoService;

    @PostMapping("/create")
    @Operation(summary = "新增信息")
    @PreAuthorize("@ss.hasPermission('studyroom:info:create')")
    public CommonResult<Long> createInfo(@Valid @RequestBody InfoSaveReqVO createReqVO) {
        return success(infoService.createInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "修改信息")
    @PreAuthorize("@ss.hasPermission('studyroom:info:update')")
    public CommonResult<Boolean> updateInfo(@Valid @RequestBody InfoSaveReqVO updateReqVO) {
        infoService.updateInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:info:delete')")
    public CommonResult<Boolean> deleteInfo(@RequestParam("id") Long id) {
        infoService.deleteInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得信息详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:info:query')")
    public CommonResult<InfoRespVO> getInfo(@RequestParam("id") Long id) {
        return success(convert(infoService.getInfo(id)));
    }

    @GetMapping("/page")
    @Operation(summary = "获得信息分页")
    @PreAuthorize("@ss.hasPermission('studyroom:info:query')")
    public CommonResult<PageResult<InfoRespVO>> getInfoPage(@Valid InfoPageReqVO pageReqVO) {
        PageResult<InfoDO> pageResult = infoService.getInfoPage(pageReqVO);
        List<InfoRespVO> list = pageResult.getList().stream().map(this::convert).collect(Collectors.toList());
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    @PutMapping("/publish")
    @Operation(summary = "发布信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:info:publish')")
    public CommonResult<Boolean> publishInfo(@RequestParam("id") Long id) {
        infoService.publishInfo(id);
        return success(true);
    }

    @PutMapping("/offline")
    @Operation(summary = "下架信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:info:offline')")
    public CommonResult<Boolean> offlineInfo(@RequestParam("id") Long id) {
        infoService.offlineInfo(id);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出信息 Excel")
    @PreAuthorize("@ss.hasPermission('studyroom:info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportInfoExcel(@Valid InfoPageReqVO pageReqVO, HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<InfoDO> list = infoService.getInfoPage(pageReqVO).getList();
        List<InfoRespVO> respList = list.stream().map(this::convert).collect(Collectors.toList());
        ExcelUtils.write(response, "自习室信息管理.xls", "数据", InfoRespVO.class, respList);
    }

    private InfoRespVO convert(InfoDO info) {
        if (info == null) {
            return null;
        }
        InfoRespVO respVO = BeanUtils.toBean(info, InfoRespVO.class);
        respVO.setCategoryName(InfoCategoryEnum.getNameByCategory(info.getCategory()));
        respVO.setStatusName(InfoStatusEnum.getNameByStatus(info.getStatus()));
        return respVO;
    }
}