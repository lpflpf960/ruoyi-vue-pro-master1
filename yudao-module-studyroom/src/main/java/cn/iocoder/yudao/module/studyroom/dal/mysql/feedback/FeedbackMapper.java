package cn.iocoder.yudao.module.studyroom.dal.mysql.feedback;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.feedback.FeedbackDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自习室意见反馈 Mapper
 */
@Mapper
public interface FeedbackMapper extends BaseMapperX<FeedbackDO> {

    /**
     * App 端：查询当前登录用户自己的反馈分页
     */
    default PageResult<FeedbackDO> selectPageByUserId(AppFeedbackPageReqVO reqVO, Long userId) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackDO>()
                .eq(FeedbackDO::getUserId, userId)
                .orderByDesc(FeedbackDO::getId));
    }

    /**
     * Admin 端：反馈分页查询
     */
    default PageResult<FeedbackDO> selectPage(FeedbackPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedbackDO>()
                .eqIfPresent(FeedbackDO::getUserId, reqVO.getUserId())
                .likeIfPresent(FeedbackDO::getUserName, reqVO.getUserName())
                .eqIfPresent(FeedbackDO::getType, reqVO.getType())
                .eqIfPresent(FeedbackDO::getStatus, reqVO.getStatus())
                .likeIfPresent(FeedbackDO::getTitle, reqVO.getTitle())
                .betweenIfPresent(FeedbackDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(FeedbackDO::getId));
    }

}