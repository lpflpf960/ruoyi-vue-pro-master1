package cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "用户 App - 自习室座位订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AppSeatOrderPageReqVO extends PageParam {
}