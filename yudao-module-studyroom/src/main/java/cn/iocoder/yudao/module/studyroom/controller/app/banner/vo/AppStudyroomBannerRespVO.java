package cn.iocoder.yudao.module.studyroom.controller.app.banner.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "用户 App - 自习室轮播图 Response VO")
@Data
public class AppStudyroomBannerRespVO {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "图片地址")
    private String imageUrl;

    @Schema(description = "跳转类型")
    private Integer jumpType;

    @Schema(description = "跳转地址")
    private String jumpUrl;

}