package cn.iocoder.yudao.module.studyroom.dal.dataobject.leaderboard;

import lombok.Data;

@Data
public class StudyLeaderboardDO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 学习总分钟数
     */
    private Long studyMinutes;

    /**
     * 已完成学习次数
     */
    private Long finishedOrderCount;

}