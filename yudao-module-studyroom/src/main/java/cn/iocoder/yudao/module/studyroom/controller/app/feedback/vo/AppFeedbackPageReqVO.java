package cn.iocoder.yudao.module.studyroom.controller.app.feedback.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "用户 App - 自习室意见反馈分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AppFeedbackPageReqVO extends PageParam {
}