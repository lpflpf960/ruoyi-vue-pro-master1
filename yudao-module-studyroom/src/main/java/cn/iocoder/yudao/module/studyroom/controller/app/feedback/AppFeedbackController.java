package cn.iocoder.yudao.module.studyroom.controller.app.feedback;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackCreateReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo.AppFeedbackRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.studyroom.enums.FeedbackStatusEnum;
import cn.iocoder.yudao.module.studyroom.enums.FeedbackTypeEnum;
import cn.iocoder.yudao.module.studyroom.service.feedback.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 App - 自习室意见反馈")
@RestController
@RequestMapping("/studyroom/feedback")
@Validated
public class AppFeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @PostMapping("/create")
    @Operation(summary = "创建意见反馈")
    public CommonResult<Long> createFeedback(@Valid @RequestBody AppFeedbackCreateReqVO createReqVO) {
        return success(feedbackService.createFeedback(createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "获得反馈详情")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<AppFeedbackRespVO> getFeedback(@RequestParam("id") Long id) {
        return success(convert(feedbackService.getAppFeedback(id)));
    }

    @GetMapping("/page")
    @Operation(summary = "获得我的反馈分页")
    public CommonResult<PageResult<AppFeedbackRespVO>> getFeedbackPage(@Valid AppFeedbackPageReqVO pageReqVO) {
        PageResult<FeedbackDO> pageResult = feedbackService.getAppFeedbackPage(pageReqVO);
        List<AppFeedbackRespVO> list = pageResult.getList().stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    private AppFeedbackRespVO convert(FeedbackDO feedback) {
        if (feedback == null) {
            return null;
        }
        AppFeedbackRespVO respVO = BeanUtils.toBean(feedback, AppFeedbackRespVO.class);
        respVO.setTypeName(FeedbackTypeEnum.getNameByType(feedback.getType()));
        respVO.setStatusName(FeedbackStatusEnum.getNameByStatus(feedback.getStatus()));
        return respVO;
    }
}