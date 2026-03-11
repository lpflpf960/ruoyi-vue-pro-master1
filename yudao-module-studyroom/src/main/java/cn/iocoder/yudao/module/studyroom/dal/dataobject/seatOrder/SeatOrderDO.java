package cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 自习室座位订单 DO
 */
@TableName("sr_order")
@KeySequence("sr_order_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatOrderDO extends BaseDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称快照
     */
    private String userName;

    /**
     * 自习室ID
     */
    private Long roomId;

    /**
     * 自习室名称快照
     */
    private String roomName;

    /**
     * 座位ID
     */
    private Long seatId;

    /**
     * 座位类型：0普通 1独享 2双人 3共享
     */
    private Integer seatType;

    /**
     * 座位号
     */
    private Integer seatNum;

    /**
     * 座位区域：0经济区 1舒适区
     */
    private Integer seatArea;

    /**
     * 座位单价（当前按 元/小时 存储）
     */
    private Integer seatPrice;

    /**
     * 预约描述
     */
    private String description;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 预约时长（小时）
     */
    private Double hours;

    /**
     * 订单总价（分）
     */
    private Integer total;

    /**
     * 支付单号
     */
    private Long payOrderId;

    /**
     * 支付状态：true-已支付 false-未支付
     */
    private Integer payStatus;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 支付渠道编码
     */
    private String payChannelCode;

    /**
     * 租户编号
     */
    private Long tenantId;

    /**
     * 订单状态：
     * 0-待支付 10-已支付 20-使用中 30-已完成 40-已取消 50-支付超时关闭 60-已退款
     */
    private Integer status;

    /**
     * 取消时间
     */
    private LocalDateTime cancelTime;

    /**
     * 取消原因
     */
    private String cancelReason;

    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;

    /**
     * 关闭原因
     */
    private String closeReason;

}