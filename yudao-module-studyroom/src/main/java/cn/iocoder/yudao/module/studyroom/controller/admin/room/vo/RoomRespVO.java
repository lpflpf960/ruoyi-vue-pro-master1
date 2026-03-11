package cn.iocoder.yudao.module.studyroom.controller.admin.room.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 自习室 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RoomRespVO {

    @Schema(description = "自习室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1604")
    @ExcelProperty("自习室ID")
    private Long roomId;

    @Schema(description = "自习室名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("自习室名字")
    private String roomName;

    @Schema(description = "自习室地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("自习室地址")
    private String roomAddr;

    @Schema(description = "自习室联系电话", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("自习室联系电话")
    private String phone;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "3466")
    @ExcelProperty("租户编号")
    private Long tenantId;

    @Schema(description = "租户名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "噜噜自习室")
    @ExcelProperty("租户名称")
    private String tenantName;

    @Schema(description = "自习室开店时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开店时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8") // 核心：强制序列化为HH:mm:ss
    private LocalTime startTime;

    @Schema(description = "自习室关店时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("关店时间")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8") // 核心：强制序列化为HH:mm:ss
    private LocalTime endTime;

}