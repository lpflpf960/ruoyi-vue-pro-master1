package cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 自习室意见反馈 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FeedbackRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "用户ID", example = "1024")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "用户名称", example = "张三")
    @ExcelProperty("用户名称")
    private String userName;

    @Schema(description = "手机号", example = "13800000000")
    @ExcelProperty("手机号")
    private String mobile;

    @Schema(description = "反馈类型", example = "1")
    @ExcelProperty("反馈类型")
    private Integer type;

    @Schema(description = "反馈类型名称", example = "功能建议")
    @ExcelProperty("反馈类型名称")
    private String typeName;

    @Schema(description = "反馈标题")
    @ExcelProperty("反馈标题")
    private String title;

    @Schema(description = "反馈内容")
    @ExcelProperty("反馈内容")
    private String content;

    @Schema(description = "图片 JSON")
    private String images;

    @Schema(description = "联系方式")
    @ExcelProperty("联系方式")
    private String contactInfo;

    @Schema(description = "处理状态", example = "0")
    @ExcelProperty("处理状态")
    private Integer status;

    @Schema(description = "处理状态名称", example = "待处理")
    @ExcelProperty("处理状态名称")
    private String statusName;

    @Schema(description = "处理回复")
    @ExcelProperty("处理回复")
    private String replyContent;

    @Schema(description = "处理时间")
    @ExcelProperty("处理时间")
    private LocalDateTime replyTime;

    @Schema(description = "处理人ID")
    @ExcelProperty("处理人ID")
    private Long replyUserId;

    @Schema(description = "提交时间")
    @ExcelProperty("提交时间")
    private LocalDateTime createTime;
}