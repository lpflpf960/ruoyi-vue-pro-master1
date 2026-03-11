package cn.iocoder.yudao.module.studyroom.controller.admin.seat;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.*;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seat.SeatDO;
import cn.iocoder.yudao.module.studyroom.service.seat.SeatService;

@Tag(name = "管理后台 - 座位")
@RestController
@RequestMapping("/studyroom/seat")
@Validated
public class SeatController {

    @Resource
    private SeatService seatService;

    @PostMapping("/create")
    @Operation(summary = "创建座位")
    @PreAuthorize("@ss.hasPermission('studyroom:seat:create')")
    public CommonResult<Long> createSeat(@Valid @RequestBody SeatSaveReqVO createReqVO) {
        return success(seatService.createSeat(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新座位")
    @PreAuthorize("@ss.hasPermission('studyroom:seat:update')")
    public CommonResult<Boolean> updateSeat(@Valid @RequestBody SeatSaveReqVO updateReqVO) {
        seatService.updateSeat(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除座位")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:seat:delete')")
    public CommonResult<Boolean> deleteSeat(@RequestParam("id") Long id) {
        seatService.deleteSeat(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除座位")
                @PreAuthorize("@ss.hasPermission('studyroom:seat:delete')")
    public CommonResult<Boolean> deleteSeatList(@RequestParam("ids") List<Long> ids) {
        seatService.deleteSeatListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得座位")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('studyroom:seat:query')")
    public CommonResult<SeatRespVO> getSeat(@RequestParam("id") Long id) {
        SeatDO seat = seatService.getSeat(id);
        return success(BeanUtils.toBean(seat, SeatRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得座位分页")
    @PreAuthorize("@ss.hasPermission('studyroom:seat:query')")
    public CommonResult<PageResult<SeatRespVO>> getSeatPage(@Valid SeatPageReqVO pageReqVO) {
        PageResult<SeatDO> pageResult = seatService.getSeatPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SeatRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出座位 Excel")
    @PreAuthorize("@ss.hasPermission('studyroom:seat:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSeatExcel(@Valid SeatPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SeatDO> list = seatService.getSeatPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "座位.xls", "数据", SeatRespVO.class,
                        BeanUtils.toBean(list, SeatRespVO.class));
    }


}