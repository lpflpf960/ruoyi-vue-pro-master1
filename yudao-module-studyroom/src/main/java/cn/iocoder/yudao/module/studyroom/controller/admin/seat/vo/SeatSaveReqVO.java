package cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 座位新增/修改 Request VO")
@Data
public class SeatSaveReqVO {

    @Schema(description = "座位ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "6995")
    private Long seatId;

    @Schema(description = "自习室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14464")
    @NotNull(message = "自习室ID不能为空")
    private Long roomId;

    @Schema(description = "座位号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "座位号不能为空")
    private Integer seatNum;

    @Schema(description = "座位区域 0经济区 1舒适区", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "座位区域不能为空") // 移除多余描述，只保留核心提示
    private Integer seatArea; // 明确是Integer类型（0/1）

    @Schema(description = "座位类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "座位类型不能为空") // 关键修复：Integer用@NotNull，而非@NotEmpty
    private Integer seatType;

    @Schema(description = "自习室名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "噜噜自习室")
    @NotBlank(message = "自习室名称不能为空") // 字符串用@NotBlank更严谨
    private String roomName;

    @Schema(description = "座位单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "座位价格不能为空") // 字符串用@NotBlank更严谨
    private Integer seatPrice;

    // 移除tenantId：VO中无该字段，前端也无需传递
}