package cn.iocoder.yudao.module.studyroom.dal.dataobject.seat;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 座位 DO
 *
 * @author LCoCo.
 */
@TableName("sr_seat")
@KeySequence("sr_seat_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatDO extends BaseDO {

    /**
     * 座位ID
     */
    @TableId
    private Long seatId;
    /**
     * 自习室ID
     */
    private Long roomId;
    /**
     * 座位号
     */
    private Integer seatNum;
    /**
     * 座位区域 0经济区 1舒适区
     *
     * 枚举 {@link TODO area_status 对应的类}
     */
    private Boolean seatArea;
    /**
     * 自习室名称
     */
    private String roomName;
    /**
     * 座位类型
     */
    private Integer seatType;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 座位单价
     */
    private Integer seatPrice;



}