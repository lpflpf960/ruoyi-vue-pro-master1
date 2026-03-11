package cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 App - 自习室座位订单 Response VO")
@Data
public class AppSeatOrderRespVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "支付单编号", example = "2048")
    private Long payOrderId;

    @Schema(description = "支付状态：0-未支付 1-已支付 2-已退款", example = "1")
    private Integer payStatus;

    @Schema(description = "支付状态名称", example = "已支付")
    private String payStatusName;

    @Schema(description = "订单状态：0-待支付 10-已支付 20-使用中 30-已完成 40-已取消 50-支付超时关闭 60-已退款", example = "10")
    private Integer status;

    @Schema(description = "订单状态名称", example = "已支付")
    private String statusName;

    @Schema(description = "订单总价，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer total;

    @Schema(description = "自习室名称", example = "中关村店")
    private String roomName;

    @Schema(description = "座位号", example = "10")
    private Integer seatNum;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "支付渠道编码", example = "wx_pub")
    private String payChannelCode;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "座位类型：0普通 1独享 2双人 3共享")
    private Integer seatType;

    @Schema(description = "座位区域：0经济区 1舒适区")
    private Integer seatArea;

    @Schema(description = "座位单价（元/小时）")
    private Integer seatPrice;

    @Schema(description = "预约订单描述")
    private String description;

    @Schema(description = "预约时长（小时）")
    private Double hours;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "关闭原因")
    private String closeReason;

}