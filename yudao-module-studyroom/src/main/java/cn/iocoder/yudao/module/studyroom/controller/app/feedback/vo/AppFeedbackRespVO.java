package cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 App - 自习室意见反馈 Response VO")
@Data
public class AppFeedbackRespVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "反馈类型", example = "1")
    private Integer type;

    @Schema(description = "反馈类型名称", example = "功能建议")
    private String typeName;

    @Schema(description = "反馈标题")
    private String title;

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "图片 JSON 字符串")
    private String images;

    @Schema(description = "联系方式")
    private String contactInfo;

    @Schema(description = "处理状态", example = "0")
    private Integer status;

    @Schema(description = "处理状态名称", example = "待处理")
    private String statusName;

    @Schema(description = "处理回复")
    private String replyContent;

    @Schema(description = "处理时间")
    private LocalDateTime replyTime;

    @Schema(description = "提交时间")
    private LocalDateTime createTime;
}