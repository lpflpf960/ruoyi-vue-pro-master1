package cn.iocoder.yudao.module.studyroom.dal.mysql.room;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.room.RoomDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.studyroom.controller.admin.room.vo.*;

import java.util.List;

/**
 * 自习室 Mapper
 *
 * @author Lcoco
 */
@Mapper
public interface RoomMapper extends BaseMapperX<RoomDO> {

    default PageResult<RoomDO> selectPage(RoomPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RoomDO>()
                .likeIfPresent(RoomDO::getRoomName, reqVO.getRoomName())
                .eqIfPresent(RoomDO::getRoomAddr, reqVO.getRoomAddr())
                .eqIfPresent(RoomDO::getPhone, reqVO.getPhone())
                .betweenIfPresent(RoomDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RoomDO::getRoomId));
    }

    @Override
    default List<RoomDO> selectList() {
        return BaseMapperX.super.selectList();
    }
}