package cn.iocoder.yudao.module.studyroom.service.seatorder;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo.SeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderCreateReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder.SeatOrderDO;

import java.util.List;

public interface SeatOrderService {

    Long createSeatOrder(AppSeatOrderCreateReqVO createReqVO);

    SeatOrderDO getSeatOrder(Long id);

    SeatOrderDO getAppSeatOrder(Long id);

    void updateSeatOrderPaid(Long id, Long payOrderId);

    PageResult<SeatOrderDO> getSeatOrderPage(AppSeatOrderPageReqVO pageReqVO);

    PageResult<SeatOrderDO> getSeatOrderPageForAdmin(SeatOrderPageReqVO pageReqVO);

    void deleteSeatOrder(Long id);

    void deleteSeatOrderListByIds(List<Long> ids);

    void cancelSeatOrder(Long id);

    void closeExpiredUnpaidOrders();

    /**
     * 刷新已支付订单生命周期：
     * 10-已支付 -> 20-使用中 -> 30-已完成
     */
    void refreshPaidOrderLifecycle();
}