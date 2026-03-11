package cn.iocoder.yudao.module.studyroom.service.room;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.studyroom.controller.admin.room.vo.*;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder.SeatOrderDO;

/**
 * 自习室 Service 接口
 *
 * @author Lcoco
 */
public interface RoomService {

    /**
     * 创建自习室
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createRoom(@Valid RoomSaveReqVO createReqVO);

    /**
     * 更新自习室
     *
     * @param updateReqVO 更新信息
     */
    void updateRoom(@Valid RoomSaveReqVO updateReqVO);

    /**
     * 删除自习室
     *
     * @param id 编号
     */
    void deleteRoom(Long id);

    /**
    * 批量删除自习室
    *
    * @param ids 编号
    */
    void deleteRoomListByIds(List<Long> ids);

    /**
     * 获得自习室
     *
     * @param id 编号
     * @return 自习室
     */
    RoomDO getRoom(Long id);

    /**
     * 获得自习室分页
     *
     * @param pageReqVO 分页查询
     * @return 自习室分页
     */
    PageResult<RoomDO> getRoomPage(RoomPageReqVO pageReqVO);

    //用户端 - 获取自习室列表
    List<RoomDO> getRoomList();


}