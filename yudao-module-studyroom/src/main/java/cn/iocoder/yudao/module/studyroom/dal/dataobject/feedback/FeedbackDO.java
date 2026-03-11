package cn.iocoder.yudao.module.studyroom.dal.dataobject.feedback;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 自习室意见反馈 DO
 */
@TableName("sr_feedback")
@KeySequence("sr_feedback_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDO extends BaseDO {

    /**
     * 编号
     */
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
     * 手机号快照
     */
    private String mobile;

    /**
     * 反馈类型：
     * 1-功能建议 2-Bug反馈 3-门店问题 4-预约问题 5-支付问题 6-投诉建议 7-其他
     */
    private Integer type;

    /**
     * 反馈标题
     */
    private String title;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 图片 JSON 字符串
     * 示例：["url1","url2"]
     */
    private String images;

    /**
     * 联系方式
     */
    private String contactInfo;

    /**
     * 处理状态：
     * 0-待处理 1-处理中 2-已处理 3-已关闭
     */
    private Integer status;

    /**
     * 处理回复
     */
    private String replyContent;

    /**
     * 处理时间
     */
    private LocalDateTime replyTime;

    /**
     * 处理人 ID
     */
    private Long replyUserId;

    /**
     * 租户编号
     */
    private Long tenantId;
}