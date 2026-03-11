package cn.iocoder.yudao.module.studyroom.controller.admin.info.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 自习室信息新增/修改 Request VO")
@Data
public class InfoSaveReqVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "标题不能为空")
    private String title;

    @Schema(description = "分类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "分类不能为空")
    private Integer category;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "正文内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "正文内容不能为空")
    private String content;

    @Schema(description = "门店ID，为空表示全局")
    private Long roomId;

    @Schema(description = "状态：0草稿 1已发布 2已下架")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否置顶")
    private Boolean sticky;

    @Schema(description = "发布时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime publishTime;
}