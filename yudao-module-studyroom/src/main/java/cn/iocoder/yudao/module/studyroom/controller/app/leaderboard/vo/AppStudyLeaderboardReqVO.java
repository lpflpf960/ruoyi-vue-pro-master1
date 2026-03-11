package cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Schema(description = "用户 App - 学习时长排行榜 Request VO")
public class AppStudyLeaderboardReqVO {

    @Schema(description = "榜单类型：TODAY/WEEK/MONTH/ALL", requiredMode = Schema.RequiredMode.REQUIRED, example = "MONTH")
    @NotBlank(message = "榜单类型不能为空")
    private String type;

    @Schema(description = "返回条数", example = "20")
    @Min(value = 1, message = "返回条数至少为 1")
    @Max(value = 100, message = "返回条数最多为 100")
    private Integer limit = 20;

}