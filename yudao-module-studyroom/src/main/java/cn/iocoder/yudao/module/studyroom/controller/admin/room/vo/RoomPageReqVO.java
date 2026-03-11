package cn.iocoder.yudao.module.studyroom.controller.admin.room.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自习室分页 Request VO")
@Data
public class RoomPageReqVO extends PageParam {

    @Schema(description = "自习室名字", example = "噜噜自习室")
    private String roomName;

    @Schema(description = "自习室地址")
    private String roomAddr;

    @Schema(description = "自习室联系电话")
    private String phone;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}