package cn.iocoder.yudao.module.studyroom.service.seat;

import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.*;
import cn.iocoder.yudao.module.studyroom.controller.app.seat.vo.AppSeatRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seat.SeatDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 座位 Service 接口
 *
 * @author LCoCo.
 */
public interface SeatService {

    /**
     * 创建座位
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSeat(@Valid SeatSaveReqVO createReqVO);

    /**
     * 更新座位
     *
     * @param updateReqVO 更新信息
     */
    void updateSeat(@Valid SeatSaveReqVO updateReqVO);

    /**
     * 删除座位
     *
     * @param id 编号
     */
    void deleteSeat(Long id);

    /**
    * 批量删除座位
    *
    * @param ids 编号
    */
    void deleteSeatListByIds(List<Long> ids);

    /**
     * 获得座位
     *
     * @param id 编号
     * @return 座位
     */
    SeatDO getSeat(Long id);

    /**
     * 获得座位分页
     *
     * @param pageReqVO 分页查询
     * @return 座位分页
     */
    PageResult<SeatDO> getSeatPage(SeatPageReqVO pageReqVO);


    List<SeatDO> getSeatList(Long roomId);

    /**
     * 按预约时间段查询座位列表，并附带 reserved 状态
     *
     * @param roomId     门店ID
     * @param startTime  预约开始时间，格式 yyyy-MM-dd HH:mm:ss
     * @param orderTotal 预约时长（小时，可支持 0.5）
     * @return 座位列表（含 reserved）
     */
    List<AppSeatRespVO> getSeatListWithReserved(Long roomId, String startTime, Double orderTotal) throws UnsupportedEncodingException;
}