package cn.iocoder.yudao.module.studyroom.controller.app.leaderboard;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppMyStudyRankRespVO;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppStudyLeaderboardReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppStudyLeaderboardRespVO;
import cn.iocoder.yudao.module.studyroom.service.leaderboard.StudyLeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 学习时长排行榜")
@RestController
@RequestMapping("/studyroom/leaderboard")
@Validated
public class AppStudyLeaderboardController {

    @Resource
    private StudyLeaderboardService studyLeaderboardService;

    @GetMapping("/study-duration")
    @Operation(summary = "获取学习时长排行榜")
    public CommonResult<List<AppStudyLeaderboardRespVO>> getStudyDurationLeaderboard(@Valid AppStudyLeaderboardReqVO reqVO) {
        return success(studyLeaderboardService.getStudyDurationLeaderboard(reqVO));
    }

    @GetMapping("/my-rank")
    @Operation(summary = "获取我的学习排名")
    public CommonResult<AppMyStudyRankRespVO> getMyStudyRank(@Valid AppStudyLeaderboardReqVO reqVO) {
        return success(studyLeaderboardService.getMyStudyRank(reqVO));
    }
}