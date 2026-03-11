package cn.iocoder.yudao.module.studyroom.service.leaderboard;

import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppMyStudyRankRespVO;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppStudyLeaderboardReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppStudyLeaderboardRespVO;

import java.util.List;

public interface StudyLeaderboardService {

    /**
     * 获取学习时长排行榜
     */
    List<AppStudyLeaderboardRespVO> getStudyDurationLeaderboard(AppStudyLeaderboardReqVO reqVO);

    /**
     * 获取我的学习排名
     */
    AppMyStudyRankRespVO getMyStudyRank(AppStudyLeaderboardReqVO reqVO);

}