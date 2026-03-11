package cn.iocoder.yudao.module.studyroom.service.feedback;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackProcessReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.studyroom.dal.mysql.feedback.FeedbackMapper;
import cn.iocoder.yudao.module.studyroom.enums.FeedbackStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUser;

@Service
@Validated
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;
    @Resource
    private MemberUserMapper memberUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createFeedback(AppFeedbackCreateReqVO createReqVO) {
        Long userId = getLoginUserId();
        Long tenantId = TenantContextHolder.getTenantId();

        MemberUserDO user = memberUserMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        String userName = ObjectUtil.isNotEmpty(user.getNickname()) ? user.getNickname()
                : ObjectUtil.isNotEmpty(user.getName()) ? user.getName()
                : ObjectUtil.isNotEmpty(user.getMobile()) ? user.getMobile()
                : String.valueOf(userId);

        FeedbackDO feedback = FeedbackDO.builder()
                .userId(userId)
                .userName(userName)
                .mobile(user.getMobile())
                .type(createReqVO.getType())
                .title(createReqVO.getTitle())
                .content(createReqVO.getContent())
                .images(createReqVO.getImages())
                .contactInfo(createReqVO.getContactInfo())
                .status(FeedbackStatusEnum.PENDING.getStatus())
                .tenantId(tenantId)
                .build();

        feedbackMapper.insert(feedback);
        return feedback.getId();
    }

    @Override
    public FeedbackDO getFeedback(Long id) {
        return feedbackMapper.selectById(id);
    }

    @Override
    public FeedbackDO getAppFeedback(Long id) {
        FeedbackDO feedback = feedbackMapper.selectById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        Long userId = getLoginUserId();
        if (!ObjectUtil.equal(feedback.getUserId(), userId)) {
            throw new RuntimeException("无权查看该反馈");
        }
        return feedback;
    }

    @Override
    public PageResult<FeedbackDO> getAppFeedbackPage(AppFeedbackPageReqVO pageReqVO) {
        Long userId = getLoginUserId();
        return feedbackMapper.selectPageByUserId(pageReqVO, userId);
    }

    @Override
    public PageResult<FeedbackDO> getFeedbackPage(FeedbackPageReqVO pageReqVO) {
        return feedbackMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void processFeedback(FeedbackProcessReqVO reqVO) {
        FeedbackDO feedback = feedbackMapper.selectById(reqVO.getId());
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }

        FeedbackDO updateObj = FeedbackDO.builder()
                .id(reqVO.getId())
                .status(reqVO.getStatus())
                .replyContent(reqVO.getReplyContent())
                .replyTime(LocalDateTime.now())
                .replyUserId(getLoginUserId())
                .build();

        feedbackMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFeedback(Long id) {
        FeedbackDO feedback = feedbackMapper.selectById(id);
        if (feedback == null) {
            throw new RuntimeException("反馈不存在");
        }
        feedbackMapper.deleteById(id);
    }
}