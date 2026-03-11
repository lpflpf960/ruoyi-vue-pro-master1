package cn.iocoder.yudao.module.studyroom.dal.mysql.seatOrder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo.SeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.leaderboard.StudyLeaderboardDO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder.SeatOrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 自习室座位订单 Mapper
 */
@Mapper
public interface SeatOrderMapper extends BaseMapperX<SeatOrderDO> {

    /**
     * 乐观锁更新支付状态
     */
    int updateByIdAndPayStatus(@Param("id") Long id,
                               @Param("expectStatus") Integer expectStatus,
                               @Param("updateObj") SeatOrderDO updateObj);

    /**
     * 查询指定时间段内，当前门店中已经被占用的座位ID
     */
    List<Long> selectReservedSeatIds(@Param("roomId") Long roomId,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);

    /**
     * 查询某个具体座位在某个时间段内是否冲突
     */
    Long selectConflictCount(@Param("roomId") Long roomId,
                             @Param("seatId") Long seatId,
                             @Param("startTime") LocalDateTime startTime,
                             @Param("endTime") LocalDateTime endTime);

    /**
     * 已支付 -> 使用中
     */
    int updatePaidToUsing(@Param("now") LocalDateTime now);

    /**
     * 使用中 -> 已完成
     */
    int updateUsingToFinished(@Param("now") LocalDateTime now);

    /**
     * 已支付 -> 已完成（容错）
     */
    int updatePaidToFinishedDirectly(@Param("now") LocalDateTime now);

    /**
     * App 端：查询当前登录用户自己的订单分页
     */
    default PageResult<SeatOrderDO> selectPageByUserId(AppSeatOrderPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SeatOrderDO>()
                .eq(SeatOrderDO::getUserId, userId)
                .orderByDesc(SeatOrderDO::getId));
    }

    /**
     * Admin 端：订单分页查询
     */
    default PageResult<SeatOrderDO> selectPage(SeatOrderPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SeatOrderDO>()
                .eqIfPresent(SeatOrderDO::getPayOrderId, reqVO.getPayOrderId())
                .eqIfPresent(SeatOrderDO::getUserId, reqVO.getUserId())
                .likeIfPresent(SeatOrderDO::getUserName, reqVO.getUserName())
                .eqIfPresent(SeatOrderDO::getRoomId, reqVO.getRoomId())
                .likeIfPresent(SeatOrderDO::getRoomName, reqVO.getRoomName())
                .eqIfPresent(SeatOrderDO::getSeatId, reqVO.getSeatId())
                .eqIfPresent(SeatOrderDO::getSeatArea, reqVO.getSeatArea())
                .eqIfPresent(SeatOrderDO::getSeatNum, reqVO.getSeatNum())
                .eqIfPresent(SeatOrderDO::getSeatPrice, reqVO.getSeatPrice())
                .eqIfPresent(SeatOrderDO::getSeatType, reqVO.getSeatType())
                .likeIfPresent(SeatOrderDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(SeatOrderDO::getStartTime, reqVO.getStartTime())
                .betweenIfPresent(SeatOrderDO::getEndTime, reqVO.getEndTime())
                .eqIfPresent(SeatOrderDO::getHours, reqVO.getHours())
                .eqIfPresent(SeatOrderDO::getTotal, reqVO.getTotal())
                .eqIfPresent(SeatOrderDO::getPayStatus, reqVO.getPayStatus())
                .eqIfPresent(SeatOrderDO::getStatus, reqVO.getStatus())
                .likeIfPresent(SeatOrderDO::getCreator, reqVO.getCreator())
                .betweenIfPresent(SeatOrderDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(SeatOrderDO::getUpdater, reqVO.getUpdater())
                .betweenIfPresent(SeatOrderDO::getUpdateTime, reqVO.getUpdateTime())
                .eqIfPresent(SeatOrderDO::getTenantId, reqVO.getTenantId())
                .betweenIfPresent(SeatOrderDO::getPayTime, reqVO.getPayTime())
                .likeIfPresent(SeatOrderDO::getPayChannelCode, reqVO.getPayChannelCode())
                .betweenIfPresent(SeatOrderDO::getCancelTime, reqVO.getCancelTime())
                .betweenIfPresent(SeatOrderDO::getCloseTime, reqVO.getCloseTime())
                .orderByDesc(SeatOrderDO::getId));
    }

    /**
     * 指定时间范围排行榜（只统计已完成订单）
     */
    List<StudyLeaderboardDO> selectStudyLeaderboard(@Param("startTime") LocalDateTime startTime,
                                                    @Param("endTime") LocalDateTime endTime,
                                                    @Param("limit") Integer limit);

    /**
     * 总榜（只统计已完成订单）
     */
    List<StudyLeaderboardDO> selectStudyLeaderboardAll(@Param("limit") Integer limit);

    /**
     * 查询当前用户指定周期学习分钟数
     */
    Long selectUserStudyMinutes(@Param("userId") Long userId,
                                @Param("startTime") LocalDateTime startTime,
                                @Param("endTime") LocalDateTime endTime);

    /**
     * 查询当前用户总榜分钟数
     */
    Long selectUserStudyMinutesAll(@Param("userId") Long userId);

    /**
     * 查询当前用户指定周期完成次数
     */
    Long selectUserFinishedOrderCount(@Param("userId") Long userId,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);

    /**
     * 查询当前用户总榜完成次数
     */
    Long selectUserFinishedOrderCountAll(@Param("userId") Long userId);

    /**
     * 指定周期用户排名
     */
    Integer selectUserRank(@Param("userId") Long userId,
                           @Param("myMinutes") Long myMinutes,
                           @Param("startTime") LocalDateTime startTime,
                           @Param("endTime") LocalDateTime endTime);

    /**
     * 总榜用户排名
     */
    Integer selectUserRankAll(@Param("userId") Long userId,
                              @Param("myMinutes") Long myMinutes);

}