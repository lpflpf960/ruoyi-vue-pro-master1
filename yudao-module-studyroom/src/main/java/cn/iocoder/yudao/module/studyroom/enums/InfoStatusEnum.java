package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自习室信息状态枚举
 */
@Getter
@AllArgsConstructor
public enum InfoStatusEnum {

    DRAFT(0, "草稿"),
    PUBLISHED(1, "已发布"),
    OFFLINE(2, "已下架");

    private final Integer status;
    private final String name;

    public static String getNameByStatus(Integer status) {
        if (status == null) {
            return "未知状态";
        }
        for (InfoStatusEnum item : values()) {
            if (item.getStatus().equals(status)) {
                return item.getName();
            }
        }
        return "未知状态";
    }
}