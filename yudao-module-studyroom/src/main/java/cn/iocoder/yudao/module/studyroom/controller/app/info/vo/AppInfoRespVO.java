package cn.iocoder.yudao.module.studyroom.controller.app.info.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户 App - 自习室信息 Response VO")
@Data
public class AppInfoRespVO {

    @Schema(description = "编号")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "分类")
    private Integer category;

    @Schema(description = "分类名称")
    private String categoryName;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "正文内容")
    private String content;

    @Schema(description = "门店ID")
    private Long roomId;

    @Schema(description = "门店名称")
    private String roomName;

    @Schema(description = "是否置顶")
    private Boolean sticky;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "浏览次数")
    private Integer browseCount;
}