package cn.iocoder.yudao.module.studyroom.controller.admin.info.vo;

import cn.idev.excel.annotation.ExcelIgnoreUnannotated;
import cn.idev.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 自习室信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class InfoRespVO {

    @Schema(description = "编号")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "标题")
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "分类")
    @ExcelProperty("分类")
    private Integer category;

    @Schema(description = "分类名称")
    @ExcelProperty("分类名称")
    private String categoryName;

    @Schema(description = "摘要")
    @ExcelProperty("摘要")
    private String summary;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "正文内容")
    private String content;

    @Schema(description = "门店ID")
    @ExcelProperty("门店ID")
    private Long roomId;

    @Schema(description = "门店名称")
    @ExcelProperty("门店名称")
    private String roomName;

    @Schema(description = "状态")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "状态名称")
    @ExcelProperty("状态名称")
    private String statusName;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "是否置顶")
    @ExcelProperty("是否置顶")
    private Boolean sticky;

    @Schema(description = "发布时间")
    @ExcelProperty("发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "浏览次数")
    @ExcelProperty("浏览次数")
    private Integer browseCount;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;
}