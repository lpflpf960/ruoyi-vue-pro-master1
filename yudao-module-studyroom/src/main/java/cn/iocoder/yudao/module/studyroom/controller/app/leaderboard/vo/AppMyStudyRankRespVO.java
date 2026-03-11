package cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户 App - 我的学习排名 Response VO")
public class AppMyStudyRankRespVO {

    @Schema(description = "我的排名，未上榜时返回 null", example = "12")
    private Integer rank;

    @Schema(description = "用户ID", example = "10001")
    private Long userId;

    @Schema(description = "用户名", example = "我")
    private String userName;

    @Schema(description = "头像", example = "https://xxx/avatar.png")
    private String avatar;

    @Schema(description = "学习总分钟数", example = "800")
    private Long studyMinutes;

    @Schema(description = "学习时长展示", example = "13.3小时")
    private String studyHoursText;

    @Schema(description = "已完成学习次数", example = "5")
    private Long finishedOrderCount;

}