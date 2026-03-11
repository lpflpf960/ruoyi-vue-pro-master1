package cn.iocoder.yudao.module.studyroom.dal.dataobject.banner;

import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

@TableName("sr_banner")
@KeySequence("sr_banner_seq")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyroomBannerDO extends BaseDO {

    @TableId
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 跳转类型 1=无跳转 2=站内页面 3=外部链接
     */
    private Integer jumpType;

    /**
     * 跳转地址
     */
    private String jumpUrl;

    /**
     * 门店编号，空表示全部门店
     */
    private Long roomId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态 0=开启 1=关闭
     */
    private Integer status;

    /**
     * 生效开始时间
     */
    private LocalDateTime startTime;

    /**
     * 生效结束时间
     */
    private LocalDateTime endTime;

    /**
     * 备注
     */
    private String remark;

}