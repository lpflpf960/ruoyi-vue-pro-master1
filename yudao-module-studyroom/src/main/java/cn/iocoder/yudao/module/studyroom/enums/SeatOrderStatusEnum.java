package cn.iocoder.yudao.module.studyroom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SeatOrderStatusEnum {

    UNPAID(0, "待支付"),
    PAID(10, "已支付"),
    USING(20, "使用中"),
    FINISHED(30, "已完成"),
    CANCELED(40, "已取消"),
    CLOSED(50, "支付超时关闭"),
    REFUNDED(60, "已退款");

    private final Integer status;
    private final String name;

    public static String getNameByStatus(Integer status) {
        for (SeatOrderStatusEnum item : values()) {
            if (item.status.equals(status)) {
                return item.name;
            }
        }
        return "未知状态";
    }
}