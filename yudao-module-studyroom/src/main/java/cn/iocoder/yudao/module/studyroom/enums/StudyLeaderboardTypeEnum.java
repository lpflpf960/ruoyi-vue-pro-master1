package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudyLeaderboardTypeEnum {

    TODAY("TODAY", "今日榜"),
    WEEK("WEEK", "本周榜"),
    MONTH("MONTH", "本月榜"),
    ALL("ALL", "总榜");

    private final String type;
    private final String name;

    public static boolean isValid(String type) {
        if (type == null) {
            return false;
        }
        for (StudyLeaderboardTypeEnum value : values()) {
            if (value.getType().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}