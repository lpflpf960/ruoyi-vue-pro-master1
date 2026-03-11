package cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "用户 App - 自习室意见反馈创建 Request VO")
@Data
public class AppFeedbackCreateReqVO {

    @Schema(description = "反馈类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "反馈类型不能为空")
    private Integer type;

    @Schema(description = "反馈标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "反馈标题不能为空")
    private String title;

    @Schema(description = "反馈内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "反馈内容不能为空")
    private String content;

    /**
     * 图片 JSON 字符串
     * 示例：["url1","url2"]
     */
    @Schema(description = "图片 JSON 字符串")
    private String images;

    @Schema(description = "联系方式")
    private String contactInfo;
}