package cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "管理后台 - 自习室轮播图分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class StudyroomBannerPageReqVO extends PageParam {

    @Schema(description = "标题", example = "活动")
    private String title;

    @Schema(description = "状态 0=开启 1=关闭", example = "0")
    private Integer status;

    @Schema(description = "门店编号", example = "1")
    private Long roomId;

}