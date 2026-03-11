package cn.iocoder.yudao.module.studyroom.dal.dataobject.room;

import lombok.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import lombok.experimental.SuperBuilder;

/**
 * 自习室 DO
 *
 * @author Lcoco
 */
@TableName("sr_room")
@KeySequence("sr_room_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDO extends BaseDO {

    /**
     * 自习室ID
     */
    @TableId
    private Long roomId;
    /**
     * 自习室名字
     */
    private String roomName;
    /**
     * 自习室地址
     */
    private String roomAddr;
    /**
     * 自习室联系电话
     */
    private String phone;
    /**
     * 自习室租户名称
     */
    private String tenantName;
    /**
     * 自习室租户编号
     */
    private Long tenantId;
    /**
     * 自习室开店时间
     */
    private LocalTime startTime;
    /**
     * 自习室关店时间
     */
    private LocalTime endTime;



}