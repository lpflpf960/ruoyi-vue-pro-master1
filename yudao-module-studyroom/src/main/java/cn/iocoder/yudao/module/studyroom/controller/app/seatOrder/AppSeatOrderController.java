package cn.iocoder.yudao.module.studyroom.controller.app.seatOrder;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderCreateReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder.SeatOrderDO;
import cn.iocoder.yudao.module.studyroom.enums.SeatOrderPayStatusEnum;
import cn.iocoder.yudao.module.studyroom.enums.SeatOrderStatusEnum;
import cn.iocoder.yudao.module.studyroom.service.seatorder.SeatOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 自习室座位订单")
@RestController
@RequestMapping("/studyroom/seat-order")
@Validated
public class AppSeatOrderController {

    @Resource
    private SeatOrderService seatOrderService;

    @PostMapping("/create")
    @Operation(summary = "创建座位订单")
    public CommonResult<Long> createSeatOrder(@Valid @RequestBody AppSeatOrderCreateReqVO createReqVO) {
        return success(seatOrderService.createSeatOrder(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得座位订单")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<AppSeatOrderRespVO> getSeatOrder(@RequestParam("id") Long id) {
        SeatOrderDO order = seatOrderService.getAppSeatOrder(id);
        return success(convert(order));
    }

    @PostMapping("/update-paid")
    @Operation(summary = "支付成功回调")
    @PermitAll
    public CommonResult<Boolean> updateSeatOrderPaid(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        seatOrderService.updateSeatOrderPaid(
                Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }

    @GetMapping("/page")
    @Operation(summary = "获得座位订单分页")
    public CommonResult<PageResult<AppSeatOrderRespVO>> getSeatOrderPage(@Valid AppSeatOrderPageReqVO pageReqVO) {
        PageResult<SeatOrderDO> pageResult = seatOrderService.getSeatOrderPage(pageReqVO);
        List<AppSeatOrderRespVO> list = pageResult.getList().stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    private AppSeatOrderRespVO convert(SeatOrderDO order) {
        if (order == null) {
            return null;
        }
        AppSeatOrderRespVO respVO = BeanUtils.toBean(order, AppSeatOrderRespVO.class);
        respVO.setStatusName(SeatOrderStatusEnum.getNameByStatus(order.getStatus()));
        respVO.setPayStatusName(SeatOrderPayStatusEnum.getNameByStatus(order.getPayStatus()));
        return respVO;
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消座位订单")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> cancelSeatOrder(@RequestParam("id") Long id) {
        seatOrderService.cancelSeatOrder(id);
        return success(true);
    }

}