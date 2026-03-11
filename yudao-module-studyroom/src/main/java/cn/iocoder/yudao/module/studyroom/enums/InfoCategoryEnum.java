package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自习室信息分类枚举
 */
@Getter
@AllArgsConstructor
public enum InfoCategoryEnum {

    NOTICE(1, "公告通知"),
    LOST_FOUND(2, "失物招领"),
    DAILY_ARRANGEMENT(3, "日常安排"),
    ACTIVITY(4, "活动资讯"),
    RULE(5, "规则说明"),
    BUSINESS_CHANGE(6, "营业变更"),
    OTHER(7, "其他");

    private final Integer category;
    private final String name;

    public static String getNameByCategory(Integer category) {
        if (category == null) {
            return "未知分类";
        }
        for (InfoCategoryEnum item : values()) {
            if (item.getCategory().equals(category)) {
                return item.getName();
            }
        }
        return "未知分类";
    }
}