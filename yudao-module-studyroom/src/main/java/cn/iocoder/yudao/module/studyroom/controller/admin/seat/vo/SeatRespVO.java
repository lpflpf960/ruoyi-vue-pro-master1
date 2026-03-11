package cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import cn.idev.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 座位 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SeatRespVO {

    @Schema(description = "座位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "6995")
    @ExcelProperty("座位ID")
    private Long seatId;

    @Schema(description = "自习室ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14464")
    @ExcelProperty("自习室ID")
    private Long roomId;

    @Schema(description = "座位号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("座位号")
    private Integer seatNum;

    @Schema(description = "座位区域 0经济区 1舒适区", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "座位区域 0经济区 1舒适区", converter = DictConvert.class)
    @DictFormat("area_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Boolean seatArea;

    @Schema(description = "座位类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "单人")
    @ExcelProperty("座位类型")
    private String seatType;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29794")
    @ExcelProperty("租户编号")
    private Long tenantId;

    @Schema(description = "座位单价", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @ExcelProperty("座位单价")
    private Integer seatPrice;

}