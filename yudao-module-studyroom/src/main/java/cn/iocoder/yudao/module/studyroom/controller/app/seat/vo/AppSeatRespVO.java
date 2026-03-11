package cn.iocoder.yudao.module.studyroom.controller.app.seat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户 App - 座位 Response VO")
public class AppSeatRespVO {

    @Schema(description = "座位主键 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long seatId;

    @Schema(description = "门店/自习室 ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1001")
    private Long roomId;

    @Schema(description = "座位号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8")
    private Integer seatNum;

    @Schema(description = "座位区域：true=舒适区，false=经济区", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean seatArea;

    @Schema(description = "座位类型：1=独享，2=双人，3=共享", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer seatType;

    @Schema(description = "座位价格（元/小时）", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer seatPrice;

    @Schema(description = "当前时间段是否已被预约：true=已预约，false=可预约", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean reserved;
}