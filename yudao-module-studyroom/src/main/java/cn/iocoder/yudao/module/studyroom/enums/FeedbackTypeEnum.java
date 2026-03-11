package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自习室反馈类型枚举
 */
@Getter
@AllArgsConstructor
public enum FeedbackTypeEnum {

    SUGGESTION(1, "功能建议"),
    BUG(2, "Bug反馈"),
    STORE(3, "门店问题"),
    ORDER(4, "预约问题"),
    PAY(5, "支付问题"),
    COMPLAINT(6, "投诉建议"),
    OTHER(7, "其他");

    private final Integer type;
    private final String name;

    public static String getNameByType(Integer type) {
        if (type == null) {
            return "未知类型";
        }
        for (FeedbackTypeEnum item : values()) {
            if (item.getType().equals(type)) {
                return item.getName();
            }
        }
        return "未知类型";
    }
}