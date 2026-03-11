package cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 座位订单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SeatOrderRespVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1772")
    @ExcelProperty("主键")
    private Long id;

    @Schema(description = "支付编号", example = "30408")
    @ExcelProperty("支付编号")
    private Long payOrderId;

    @Schema(description = "用户ID", example = "17551")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "用户名称", example = "王五")
    @ExcelProperty("用户名称")
    private String userName;

    @Schema(description = "自习室ID", example = "14880")
    @ExcelProperty("自习室ID")
    private Long roomId;

    @Schema(description = "自习室名称", example = "芋艿")
    @ExcelProperty("自习室名称")
    private String roomName;

    @Schema(description = "座位ID", example = "7347")
    @ExcelProperty("座位ID")
    private Long seatId;

    @Schema(description = "座位区域：0经济区 1舒适区")
    @ExcelProperty("座位区域")
    private Integer seatArea;

    @Schema(description = "座位号")
    @ExcelProperty("座位号")
    private Integer seatNum;

    @Schema(description = "座位单价（元/小时）", example = "20")
    @ExcelProperty("座位单价(元/小时)")
    private Integer seatPrice;

    @Schema(description = "座位类型：0普通 1独享 2双人 3共享", example = "1")
    @ExcelProperty("座位类型")
    private Integer seatType;

    @Schema(description = "预约描述", example = "随便")
    @ExcelProperty("预约描述")
    private String description;

    @Schema(description = "开始时间")
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @Schema(description = "小时数")
    @ExcelProperty("小时数")
    private Double hours;

    @Schema(description = "订单总价（分）")
    @ExcelProperty("订单总价(分)")
    private Integer total;

    @Schema(description = "支付状态：0-未支付 1-已支付 2-已退款")
    @ExcelProperty(value = "付款状态", converter = DictConvert.class)
    @DictFormat("pay_status")
    private Integer payStatus;

    @Schema(description = "支付状态名称", example = "已支付")
    @ExcelProperty("付款状态名称")
    private String payStatusName;

    @Schema(description = "订单状态：0-待支付 10-已支付 20-使用中 30-已完成 40-已取消 50-支付超时关闭 60-已退款")
    @ExcelProperty("订单状态")
    private Integer status;

    @Schema(description = "订单状态名称", example = "已取消")
    @ExcelProperty("订单状态名称")
    private String statusName;

    @Schema(description = "创建者")
    @ExcelProperty("创建者")
    private String creator;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    @ExcelProperty("更新者")
    private String updater;

    @Schema(description = "更新时间")
    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "租户编号", example = "18319")
    @ExcelProperty("租户编号")
    private Long tenantId;

    @Schema(description = "支付时间")
    @ExcelProperty("支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付渠道编码")
    @ExcelProperty("支付渠道编码")
    private String payChannelCode;

    @Schema(description = "取消时间")
    @ExcelProperty("取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因")
    @ExcelProperty("取消原因")
    private String cancelReason;

    @Schema(description = "关闭时间")
    @ExcelProperty("关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "关闭原因")
    @ExcelProperty("关闭原因")
    private String closeReason;

}