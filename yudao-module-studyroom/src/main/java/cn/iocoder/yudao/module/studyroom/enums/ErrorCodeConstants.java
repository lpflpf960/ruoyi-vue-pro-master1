package cn.iocoder.yudao.module.studyroom.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

// ========== 自习室  ==========
public interface ErrorCodeConstants {
    ErrorCode ROOM_NOT_EXISTS = new ErrorCode(1-024-000-001, "自习室不存在");
    ErrorCode SEAT_RECORD_NOT_EXISTS = new ErrorCode(1-024-000-002, "座位记录不存在");
    ErrorCode ROOM_SEAT_NOT_EXISTS = new ErrorCode(1-024-000-003, "自习室座位不存在");
    ErrorCode SEAT_NOT_EXISTS = new ErrorCode(1-024-000-004, "座位不存在");
    ErrorCode SEAT_ORDER_NOT_EXISTS = new ErrorCode(1-024-000-005, "订单不存在");

    // ========== 座位订单模块 1-010-001-000 ==========
    ErrorCode SEAT_ORDER_NOT_FOUND = new ErrorCode(1-024-000-006, "座位订单不存在");
    ErrorCode SEAT_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID = new ErrorCode(1-024-000-007, "座位订单更新支付状态失败，订单不是【未支付】状态");
    ErrorCode SEAT_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR = new ErrorCode(1-024-000-010, "座位订单更新支付状态失败，支付单编号不匹配");

    // ========== 座位业务模块 1-010-002-000 ==========
    ErrorCode SEAT_NOT_FOUND = new ErrorCode(1-024-000-011, "座位不存在");
    ErrorCode SEAT_ALREADY_BOOKED = new ErrorCode(1-024-000-012, "该时间段座位已被预约");
    ErrorCode SEAT_BOOKING_TIME_INVALID = new ErrorCode(1-024-000-013, "预约时间不合法");
}
