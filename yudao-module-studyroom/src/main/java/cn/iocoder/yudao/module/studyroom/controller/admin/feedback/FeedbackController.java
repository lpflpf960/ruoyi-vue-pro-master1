package cn.iocoder.yudao.module.studyroom.controller.admin.feedback;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackProcessReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.feedback.vo.FeedbackRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.feedback.FeedbackDO;
import cn.iocoder.yudao.module.studyroom.enums.FeedbackStatusEnum;
import cn.iocoder.yudao.module.studyroom.enums.FeedbackTypeEnum;
import cn.iocoder.yudao.module.studyroom.service.feedback.FeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 自习室意见反馈")
@RestController
@RequestMapping("/studyroom/feedback")
@Validated
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @GetMapping("/get")
    @Operation(summary = "获得反馈详情")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:feedback:query')")
    public CommonResult<FeedbackRespVO> getFeedback(@RequestParam("id") Long id) {
        return success(convert(feedbackService.getFeedback(id)));
    }

    @GetMapping("/page")
    @Operation(summary = "获得反馈分页")
    @PreAuthorize("@ss.hasPermission('studyroom:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> getFeedbackPage(@Valid FeedbackPageReqVO pageReqVO) {
        PageResult<FeedbackDO> pageResult = feedbackService.getFeedbackPage(pageReqVO);
        List<FeedbackRespVO> list = pageResult.getList().stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return success(new PageResult<>(list, pageResult.getTotal()));
    }

    @PutMapping("/process")
    @Operation(summary = "处理反馈")
    @PreAuthorize("@ss.hasPermission('studyroom:feedback:process')")
    public CommonResult<Boolean> processFeedback(@Valid @RequestBody FeedbackProcessReqVO reqVO) {
        feedbackService.processFeedback(reqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除反馈")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:feedback:delete')")
    public CommonResult<Boolean> deleteFeedback(@RequestParam("id") Long id) {
        feedbackService.deleteFeedback(id);
        return success(true);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出反馈 Excel")
    @PreAuthorize("@ss.hasPermission('studyroom:feedback:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportFeedbackExcel(@Valid FeedbackPageReqVO pageReqVO,
                                    HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<FeedbackDO> list = feedbackService.getFeedbackPage(pageReqVO).getList();
        List<FeedbackRespVO> respList = list.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        ExcelUtils.write(response, "自习室意见反馈.xls", "数据", FeedbackRespVO.class, respList);
    }

    private FeedbackRespVO convert(FeedbackDO feedback) {
        if (feedback == null) {
            return null;
        }
        FeedbackRespVO respVO = BeanUtils.toBean(feedback, FeedbackRespVO.class);
        respVO.setTypeName(FeedbackTypeEnum.getNameByType(feedback.getType()));
        respVO.setStatusName(FeedbackStatusEnum.getNameByStatus(feedback.getStatus()));
        return respVO;
    }
}