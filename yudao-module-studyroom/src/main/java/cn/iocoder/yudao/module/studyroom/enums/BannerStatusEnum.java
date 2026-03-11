package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BannerStatusEnum {

    ENABLE(0, "开启"),
    DISABLE(1, "关闭");

    private final Integer status;
    private final String name;

}