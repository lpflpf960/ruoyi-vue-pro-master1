package cn.iocoder.yudao.module.studyroom.service.feedback;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackProcessReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.feedback.FeedbackDO;

public interface FeedbackService {

    Long createFeedback(AppFeedbackCreateReqVO createReqVO);

    FeedbackDO getFeedback(Long id);

    FeedbackDO getAppFeedback(Long id);

    PageResult<FeedbackDO> getAppFeedbackPage(AppFeedbackPageReqVO pageReqVO);

    PageResult<FeedbackDO> getFeedbackPage(FeedbackPageReqVO pageReqVO);

    void processFeedback(FeedbackProcessReqVO reqVO);

    void deleteFeedback(Long id);
}