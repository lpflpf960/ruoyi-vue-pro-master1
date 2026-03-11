package cn.iocoder.yudao.module.studyroom.dal.mysql.banner;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.banner.StudyroomBannerDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface StudyroomBannerMapper extends BaseMapperX<StudyroomBannerDO> {

    default PageResult<StudyroomBannerDO> selectPage(StudyroomBannerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<StudyroomBannerDO>()
                .likeIfPresent(StudyroomBannerDO::getTitle, reqVO.getTitle())
                .eqIfPresent(StudyroomBannerDO::getStatus, reqVO.getStatus())
                .eqIfPresent(StudyroomBannerDO::getRoomId, reqVO.getRoomId())
                .orderByAsc(StudyroomBannerDO::getSort)
                .orderByDesc(StudyroomBannerDO::getId));
    }

    default List<StudyroomBannerDO> selectAppList(Long roomId) {
        LocalDateTime now = LocalDateTime.now();

        LambdaQueryWrapper<StudyroomBannerDO> wrapper = new LambdaQueryWrapper<StudyroomBannerDO>()
                .eq(StudyroomBannerDO::getStatus, 0)
                .and(w -> w.isNull(StudyroomBannerDO::getRoomId)
                        .or(roomId != null, x -> x.eq(StudyroomBannerDO::getRoomId, roomId)))
                .and(w -> w.isNull(StudyroomBannerDO::getStartTime)
                        .or()
                        .le(StudyroomBannerDO::getStartTime, now))
                .and(w -> w.isNull(StudyroomBannerDO::getEndTime)
                        .or()
                        .ge(StudyroomBannerDO::getEndTime, now))
                .orderByAsc(StudyroomBannerDO::getSort)
                .orderByDesc(StudyroomBannerDO::getId);

        return selectList(wrapper);
    }

}