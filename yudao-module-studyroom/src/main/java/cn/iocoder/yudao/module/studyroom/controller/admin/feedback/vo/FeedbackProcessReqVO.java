package cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 自习室意见反馈处理 Request VO")
@Data
public class FeedbackProcessReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "编号不能为空")
    private Long id;

    @Schema(description = "处理状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "处理状态不能为空")
    private Integer status;

    @Schema(description = "处理回复", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "处理回复不能为空")
    private String replyContent;
}