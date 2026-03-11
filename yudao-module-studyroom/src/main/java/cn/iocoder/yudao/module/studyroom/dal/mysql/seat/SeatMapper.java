package cn.iocoder.yudao.module.studyroom.dal.mysql.seat;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seat.SeatDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.*;

/**
 * 座位 Mapper
 *
 * @author LCoCo.
 */
@Mapper
public interface SeatMapper extends BaseMapperX<SeatDO> {

    default PageResult<SeatDO> selectPage(SeatPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SeatDO>()
                .eqIfPresent(SeatDO::getRoomId, reqVO.getRoomId())
                .eqIfPresent(SeatDO::getSeatNum, reqVO.getSeatNum())
                .eqIfPresent(SeatDO::getSeatArea, reqVO.getSeatArea())
                .eqIfPresent(SeatDO::getSeatType, reqVO.getSeatType())
                .betweenIfPresent(SeatDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(SeatDO::getRoomId));
    }

    default List<SeatDO> selectList(Long roomId) {
        // 正确用法：只传递LambdaQueryWrapperX对象，BaseMapperX会自动拼接条件
        return selectList(new LambdaQueryWrapperX<SeatDO>()
                .eqIfPresent(SeatDO::getRoomId, roomId) // 匹配roomId
                .eq(SeatDO::getDeleted, false) // 显式过滤已删除数据（如果BaseDO已处理可省略）
                .orderByAsc(SeatDO::getSeatNum));
    }



}