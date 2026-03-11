package cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo.SeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo.SeatOrderRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder.SeatOrderDO;
import cn.iocoder.yudao.module.studyroom.enums.SeatOrderPayStatusEnum;
import cn.iocoder.yudao.module.studyroom.enums.SeatOrderStatusEnum;
import cn.iocoder.yudao.module.studyroom.service.seatorder.SeatOrderService;
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

@Tag(name = "管理后台 - 座位订单")
@RestController
@RequestMapping("/studyroom/seat-order")
@Validated
public class SeatOrderController {

    @Resource
    private SeatOrderService seatOrderService;

    @GetMapping("/get")
    @Operation(summary = "获得座位订单详情")
    @Parameter(name = "id", description = "订单编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('studyroom:seat-order:query')")
    public CommonResult<SeatOrderRespVO> getSeatOrder(@RequestParam("id") Long id) {
        SeatOrderDO seatOrder = seatOrderService.getSeatOrder(id);
        return success(convert(seatOrder));
    }

    @GetMapping("/page")
    @Operation(summary = "获得座位订单分页")
    @PreAuthorize("@ss.hasPermission('studyroom:seat-order:query')")
    public CommonResult<PageResult<SeatOrderRespVO>> getSeatOrderPage(@Valid SeatOrderPageReqVO pageReqVO) {
        PageResult<SeatOrderDO> pageResult = seatOrderService.getSeatOrderPageForAdmin(pageReqVO);
        List<SeatOrderRespVO> list = pageResult.getList().stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除座位订单（仅允许删除待支付/已取消/支付超时关闭订单）")
    @Parameter(name = "id", description = "订单编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:seat-order:delete')")
    public CommonResult<Boolean> deleteSeatOrder(@RequestParam("id") Long id) {
        seatOrderService.deleteSeatOrder(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Operation(summary = "批量删除座位订单（仅允许删除待支付/已取消/支付超时关闭订单）")
    @Parameter(name = "ids", description = "订单编号列表", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:seat-order:delete')")
    public CommonResult<Boolean> deleteSeatOrderList(@RequestParam("ids") List<Long> ids) {
        seatOrderService.deleteSeatOrderListByIds(ids);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出座位订单 Excel")
    @PreAuthorize("@ss.hasPermission('studyroom:seat-order:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSeatOrderExcel(@Valid SeatOrderPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SeatOrderDO> list = seatOrderService.getSeatOrderPageForAdmin(pageReqVO).getList();
        List<SeatOrderRespVO> respList = list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        ExcelUtils.write(response, "座位订单.xls", "数据", SeatOrderRespVO.class, respList);
    }

    private SeatOrderRespVO convert(SeatOrderDO seatOrder) {
        if (seatOrder == null) {
            return null;
        }
        SeatOrderRespVO respVO = BeanUtils.toBean(seatOrder, SeatOrderRespVO.class);
        respVO.setStatusName(SeatOrderStatusEnum.getNameByStatus(seatOrder.getStatus()));
        respVO.setPayStatusName(SeatOrderPayStatusEnum.getNameByStatus(seatOrder.getPayStatus()));
        return respVO;
    }

}