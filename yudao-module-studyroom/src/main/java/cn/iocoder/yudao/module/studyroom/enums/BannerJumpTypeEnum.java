package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BannerJumpTypeEnum {

    NONE(1, "无跳转"),
    PAGE(2, "站内页面"),
    LINK(3, "外部链接");

    private final Integer type;
    private final String name;

}