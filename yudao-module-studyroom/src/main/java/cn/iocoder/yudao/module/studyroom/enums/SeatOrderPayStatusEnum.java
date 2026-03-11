package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatOrderPayStatusEnum {

    UNPAID(0, "未支付"),
    PAID(1, "已支付"),
    REFUNDED(2, "已退款");

    private final Integer status;
    private final String name;

    public static String getNameByStatus(Integer status) {
        if (status == null) {
            return "未知状态";
        }
        for (SeatOrderPayStatusEnum value : values()) {
            if (value.getStatus().equals(status)) {
                return value.getName();
            }
        }
        return "未知状态";
    }
}