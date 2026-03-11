package cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户 App - 学习时长排行榜 Response VO")
public class AppStudyLeaderboardRespVO {

    @Schema(description = "排名", example = "1")
    private Integer rank;

    @Schema(description = "用户ID", example = "10001")
    private Long userId;

    @Schema(description = "用户名", example = "张三")
    private String userName;

    @Schema(description = "头像", example = "https://xxx/avatar.png")
    private String avatar;

    @Schema(description = "学习总分钟数", example = "1260")
    private Long studyMinutes;

    @Schema(description = "学习时长展示", example = "21小时")
    private String studyHoursText;

    @Schema(description = "已完成学习次数", example = "8")
    private Long finishedOrderCount;

    @Schema(description = "是否当前用户", example = "true")
    private Boolean me;

}