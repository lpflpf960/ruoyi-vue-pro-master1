package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自习室反馈处理状态枚举
 */
@Getter
@AllArgsConstructor
public enum FeedbackStatusEnum {

    PENDING(0, "待处理"),
    PROCESSING(1, "处理中"),
    PROCESSED(2, "已处理"),
    CLOSED(3, "已关闭");

    private final Integer status;
    private final String name;

    public static String getNameByStatus(Integer status) {
        if (status == null) {
            return "未知状态";
        }
        for (FeedbackStatusEnum item : values()) {
            if (item.getStatus().equals(status)) {
                return item.getName();
            }
        }
        return "未知状态";
    }
}