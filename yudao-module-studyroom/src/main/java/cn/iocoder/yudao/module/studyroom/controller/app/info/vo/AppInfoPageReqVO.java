package cn.iocoder.yudao.module.studyroom.controller.app.info.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Schema(description = "用户 App - 自习室信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class AppInfoPageReqVO extends PageParam {

    @Schema(description = "分类")
    private Integer category;

    @Schema(description = "关键词")
    private String keyword;

    @Schema(description = "门店ID，为空表示不按门店筛选")
    private Long roomId;
}