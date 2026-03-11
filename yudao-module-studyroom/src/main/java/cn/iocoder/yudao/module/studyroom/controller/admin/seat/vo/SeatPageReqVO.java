package cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 座位分页 Request VO")
@Data
public class SeatPageReqVO extends PageParam {

    @Schema(description = "自习室ID", example = "14464")
    private Long roomId;

    @Schema(description = "座位号")
    private Integer seatNum;

    @Schema(description = "座位区域 0经济区 1舒适区")
    private Boolean seatArea;

    @Schema(description = "座位类型", example = "独享")
    private Integer seatType;

    @Schema(description = "租户编号", example = "29794")
    private Long tenantId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}