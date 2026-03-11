package cn.iocoder.yudao.module.studyroom.controller.admin.room.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 自习室新增/修改 Request VO")
@Data
public class RoomSaveReqVO {

    @Schema(description = "自习室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1604")
    private Long roomId;

    @Schema(description = "自习室名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "自习室名字不能为空")
    private String roomName;

    @Schema(description = "自习室地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "自习室地址不能为空")
    private String roomAddr;

    @Schema(description = "自习室联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "自习室联系电话不能为空")
    private String phone;

    @Schema(description = "自习室开店时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "自习室开店时间不能为空")
    private LocalTime startTime;

    @Schema(description = "自习室关店时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "自习室关店时间不能为空")
    private LocalTime endTime;

}