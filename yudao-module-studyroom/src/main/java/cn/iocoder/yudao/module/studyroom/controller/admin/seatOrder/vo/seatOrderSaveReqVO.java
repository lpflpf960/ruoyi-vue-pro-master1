package cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 座位订单新增/修改 Request VO")
@Data
public class seatOrderSaveReqVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1772")
    private Long id;

    @Schema(description = "支付编号", example = "30408")
    private Long payOrderId;

    @Schema(description = "用户ID", example = "17551")
    private Long userId;

    @Schema(description = "用户名称", example = "王五")
    private String userName;

    @Schema(description = "自习室ID", example = "14880")
    private Long roomId;

    @Schema(description = "自习室名称", example = "芋艿")
    private String roomName;

    @Schema(description = "座位ID", example = "7347")
    private Long seatId;

    @Schema(description = "座位区域：0经济区 1舒适区")
    private Integer seatArea;

    @Schema(description = "座位号")
    private Integer seatNum;

    @Schema(description = "座位单价（元/小时）", example = "20")
    private Integer seatPrice;

    @Schema(description = "座位类型：0普通 1独享 2双人 3共享", example = "1")
    private Integer seatType;

    @Schema(description = "预约描述", example = "随便")
    private String description;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "小时数")
    private Double hours;

    @Schema(description = "订单总价（分）")
    private Integer total;

    @Schema(description = "支付状态：0-未支付 1-已支付 2-已退款")
    private Integer payStatus;

    @Schema(description = "订单状态：0-待支付 10-已支付 20-使用中 30-已完成 40-已取消 50-支付超时关闭 60-已退款")
    private Integer status;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "租户编号", example = "18319")
    private Long tenantId;

    @Schema(description = "支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付渠道编码")
    private String payChannelCode;

    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消原因")
    private String cancelReason;

    @Schema(description = "关闭时间")
    private LocalDateTime closeTime;

    @Schema(description = "关闭原因")
    private String closeReason;

}