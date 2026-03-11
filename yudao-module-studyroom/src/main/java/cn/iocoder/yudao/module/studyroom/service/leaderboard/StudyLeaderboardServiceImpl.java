package cn.iocoder.yudao.module.studyroom.service.leaderboard;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppMyStudyRankRespVO;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppStudyLeaderboardReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.leaderboard.vo.AppStudyLeaderboardRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.leaderboard.StudyLeaderboardDO;
import cn.iocoder.yudao.module.studyroom.dal.mysql.seatOrder.SeatOrderMapper;
import cn.iocoder.yudao.module.studyroom.enums.StudyLeaderboardTypeEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Service
@Validated
public class StudyLeaderboardServiceImpl implements StudyLeaderboardService {

    @Resource
    private SeatOrderMapper seatOrderMapper;
    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    public List<AppStudyLeaderboardRespVO> getStudyDurationLeaderboard(AppStudyLeaderboardReqVO reqVO) {
        String type = reqVO.getType();
        Integer limit = ObjectUtil.defaultIfNull(reqVO.getLimit(), 20);
        if (!StudyLeaderboardTypeEnum.isValid(type)) {
            throw new IllegalArgumentException("不支持的榜单类型");
        }

        Long currentUserId = getLoginUserId();
        List<StudyLeaderboardDO> records;

        if (StudyLeaderboardTypeEnum.ALL.getType().equalsIgnoreCase(type)) {
            records = seatOrderMapper.selectStudyLeaderboardAll(limit);
        } else {
            LocalDateTime[] range = buildTimeRange(type);
            records = seatOrderMapper.selectStudyLeaderboard(range[0], range[1], limit);
        }

        List<AppStudyLeaderboardRespVO> result = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            StudyLeaderboardDO item = records.get(i);
            AppStudyLeaderboardRespVO vo = new AppStudyLeaderboardRespVO();
            vo.setRank(i + 1);
            vo.setUserId(item.getUserId());
            vo.setUserName(item.getUserName());
            vo.setAvatar(item.getAvatar());
            vo.setStudyMinutes(item.getStudyMinutes());
            vo.setStudyHoursText(formatHours(item.getStudyMinutes()));
            vo.setFinishedOrderCount(item.getFinishedOrderCount());
            vo.setMe(ObjectUtil.equal(item.getUserId(), currentUserId));
            result.add(vo);
        }
        return result;
    }

    @Override
    public AppMyStudyRankRespVO getMyStudyRank(AppStudyLeaderboardReqVO reqVO) {
        String type = reqVO.getType();
        if (!StudyLeaderboardTypeEnum.isValid(type)) {
            throw new IllegalArgumentException("不支持的榜单类型");
        }

        Long userId = getLoginUserId();
        MemberUserDO user = memberUserMapper.selectById(userId);

        Long myMinutes;
        Long finishedOrderCount;
        Integer myRank;

        if (StudyLeaderboardTypeEnum.ALL.getType().equalsIgnoreCase(type)) {
            myMinutes = ObjectUtil.defaultIfNull(seatOrderMapper.selectUserStudyMinutesAll(userId), 0L);
            finishedOrderCount = ObjectUtil.defaultIfNull(seatOrderMapper.selectUserFinishedOrderCountAll(userId), 0L);
            myRank = myMinutes > 0 ? seatOrderMapper.selectUserRankAll(userId, myMinutes) : null;
        } else {
            LocalDateTime[] range = buildTimeRange(type);
            myMinutes = ObjectUtil.defaultIfNull(seatOrderMapper.selectUserStudyMinutes(userId, range[0], range[1]), 0L);
            finishedOrderCount = ObjectUtil.defaultIfNull(seatOrderMapper.selectUserFinishedOrderCount(userId, range[0], range[1]), 0L);
            myRank = myMinutes > 0 ? seatOrderMapper.selectUserRank(userId, myMinutes, range[0], range[1]) : null;
        }

        AppMyStudyRankRespVO respVO = new AppMyStudyRankRespVO();
        respVO.setUserId(userId);
        respVO.setUserName(user != null && user.getNickname() != null && !user.getNickname().trim().isEmpty()
                ? user.getNickname() : "我");
        respVO.setAvatar(user != null ? user.getAvatar() : null);
        respVO.setStudyMinutes(myMinutes);
        respVO.setStudyHoursText(formatHours(myMinutes));
        respVO.setFinishedOrderCount(finishedOrderCount);
        respVO.setRank(myRank);
        return respVO;
    }

    private LocalDateTime[] buildTimeRange(String type) {
        LocalDate today = LocalDate.now();
        LocalDateTime startTime;
        LocalDateTime endTime;

        switch (type.toUpperCase()) {
            case "TODAY":
                startTime = today.atStartOfDay();
                endTime = today.plusDays(1).atStartOfDay();
                break;
            case "WEEK":
                LocalDate weekStart = today.with(DayOfWeek.MONDAY);
                startTime = weekStart.atStartOfDay();
                endTime = weekStart.plusDays(7).atStartOfDay();
                break;
            case "MONTH":
                LocalDate monthStart = today.withDayOfMonth(1);
                startTime = monthStart.atStartOfDay();
                endTime = monthStart.plusMonths(1).atStartOfDay();
                break;
            default:
                throw new IllegalArgumentException("不支持的榜单类型");
        }
        return new LocalDateTime[]{startTime, endTime};
    }

    private String formatHours(Long studyMinutes) {
        if (studyMinutes == null || studyMinutes <= 0) {
            return "0小时";
        }
        BigDecimal hours = BigDecimal.valueOf(studyMinutes)
                .divide(BigDecimal.valueOf(60), 1, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        return hours.toPlainString() + "小时";
    }
}