package cn.iocoder.yudao.module.studyroom.dal.mysql.info;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.info.vo.AppInfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.info.InfoDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;

/**
 * 自习室信息管理 Mapper
 */
@Mapper
public interface InfoMapper extends BaseMapperX<InfoDO> {

    /**
     * Admin 端分页查询
     */
    default PageResult<InfoDO> selectPage(InfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<InfoDO>()
                .likeIfPresent(InfoDO::getTitle, reqVO.getTitle())
                .eqIfPresent(InfoDO::getCategory, reqVO.getCategory())
                .eqIfPresent(InfoDO::getStatus, reqVO.getStatus())
                .eqIfPresent(InfoDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(InfoDO::getSticky, reqVO.getSticky())
                .betweenIfPresent(InfoDO::getPublishTime, reqVO.getPublishTime())
                .betweenIfPresent(InfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(InfoDO::getSticky)
                .orderByDesc(InfoDO::getSort)
                .orderByDesc(InfoDO::getPublishTime)
                .orderByDesc(InfoDO::getId));
    }

    /**
     * App 端分页查询：
     * 只查已发布，且发布时间 <= 当前时间
     * roomId 为空时查全部已发布
     * roomId 不为空时查 全局 + 当前门店
     */
    default PageResult<InfoDO> selectAppPage(AppInfoPageReqVO reqVO, LocalDateTime now) {
        LambdaQueryWrapperX<InfoDO> wrapper = new LambdaQueryWrapperX<InfoDO>();

        // 只查询已发布
        wrapper.eq(InfoDO::getStatus, 1);

        // 只查询已到发布时间的数据
        wrapper.le(InfoDO::getPublishTime, now);

        // 关键词
        wrapper.likeIfPresent(InfoDO::getTitle, reqVO.getKeyword());

        // 分类
        wrapper.eqIfPresent(InfoDO::getCategory, reqVO.getCategory());

        // 门店条件：全局 + 当前门店
        if (reqVO.getRoomId() != null) {
            wrapper.and(q -> q.isNull(InfoDO::getRoomId)
                    .or()
                    .eq(InfoDO::getRoomId, reqVO.getRoomId()));
        }

        // 排序
        wrapper.orderByDesc(InfoDO::getSticky)
                .orderByDesc(InfoDO::getSort)
                .orderByDesc(InfoDO::getPublishTime)
                .orderByDesc(InfoDO::getId);

        return selectPage(reqVO, wrapper);
    }

    /**
     * 浏览次数 +1
     */
    @Update("UPDATE sr_info SET browse_count = browse_count + 1, update_time = NOW() WHERE id = #{id} AND deleted = 0")
    int incrementBrowseCount(@Param("id") Long id);
}