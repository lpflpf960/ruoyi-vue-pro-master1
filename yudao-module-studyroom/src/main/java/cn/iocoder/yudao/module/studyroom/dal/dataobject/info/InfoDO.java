package cn.iocoder.yudao.module.studyroom.dal.dataobject.info;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 自习室信息管理 DO
 */
@TableName("sr_info")
@KeySequence("sr_info_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfoDO extends BaseDO {

    /**
     * 编号
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 分类：
     * 1-公告通知 2-失物招领 3-日常安排 4-活动资讯 5-规则说明 6-营业变更 7-其他
     */
    private Integer category;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 封面图
     */
    private String coverUrl;

    /**
     * 正文内容
     */
    private String content;

    /**
     * 门店ID，为空表示全局
     */
    private Long roomId;

    /**
     * 门店名称快照
     */
    private String roomName;

    /**
     * 状态：0草稿 1已发布 2已下架
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否置顶
     */
    private Boolean sticky;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 浏览次数
     */
    private Integer browseCount;

    /**
     * 租户编号
     */
    private Long tenantId;
}