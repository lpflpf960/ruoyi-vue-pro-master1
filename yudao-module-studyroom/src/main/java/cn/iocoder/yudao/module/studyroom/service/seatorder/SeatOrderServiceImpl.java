package cn.iocoder.yudao.module.studyroom.service.seatorder;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils;
import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.member.dal.dataobject.user.MemberUserDO;
import cn.iocoder.yudao.module.member.dal.mysql.user.MemberUserMapper;
import cn.iocoder.yudao.module.pay.api.order.PayOrderApi;
import cn.iocoder.yudao.module.pay.api.order.dto.PayOrderCreateReqDTO;
import cn.iocoder.yudao.module.pay.api.order.dto.PayOrderRespDTO;
import cn.iocoder.yudao.module.pay.enums.ErrorCodeConstants;
import cn.iocoder.yudao.module.pay.enums.order.PayOrderStatusEnum;
import cn.iocoder.yudao.module.studyroom.controller.admin.seatOrder.vo.SeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderCreateReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seatOrder.vo.AppSeatOrderPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seat.SeatDO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seatOrder.SeatOrderDO;
import cn.iocoder.yudao.module.studyroom.dal.mysql.seatOrder.SeatOrderMapper;
import cn.iocoder.yudao.module.studyroom.enums.SeatOrderPayStatusEnum;
import cn.iocoder.yudao.module.studyroom.enums.SeatOrderStatusEnum;
import cn.iocoder.yudao.module.studyroom.service.seat.SeatService;
import cn.iocoder.yudao.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.iocoder.yudao.module.trade.service.delivery.DeliveryPickUpStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static cn.hutool.core.util.ObjectUtil.notEqual;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.studyroom.enums.ErrorCodeConstants.SEAT_BOOKING_TIME_INVALID;
import static cn.iocoder.yudao.module.studyroom.enums.ErrorCodeConstants.SEAT_ORDER_NOT_FOUND;
import static cn.iocoder.yudao.module.studyroom.enums.ErrorCodeConstants.SEAT_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR;
import static cn.iocoder.yudao.module.studyroom.enums.ErrorCodeConstants.SEAT_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID;

@Service
@Validated
@Slf4j
public class SeatOrderServiceImpl implements SeatOrderService {

    /**
     * 对应 pay_app.app_key
     */
    private static final String PAY_APP_KEY = "studyroom";

    @Resource
    private SeatOrderMapper seatOrderMapper;
    @Resource
    private PayOrderApi payOrderApi;
    @Resource
    private MemberUserMapper memberUserMapper;
    @Resource
    private DeliveryPickUpStoreService deliveryPickUpStoreService;
    @Resource
    private SeatService seatService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSeatOrder(AppSeatOrderCreateReqVO createReqVO) {
        Long userId = getLoginUserId();
        Long tenantId = TenantContextHolder.getTenantId();
        log.debug("[createSeatOrder] current tenantId = {}", tenantId);

        long minutes = Duration.between(createReqVO.getStartTime(), createReqVO.getEndTime()).toMinutes();
        if (minutes <= 0) {
            log.error("[createSeatOrder][预约时长不合法 - 分钟数: {}，startTime: {}, endTime: {}]",
                    minutes, createReqVO.getStartTime(), createReqVO.getEndTime());
            throw exception(SEAT_BOOKING_TIME_INVALID);
        }

        if (minutes < 30 || minutes > 12 * 60) {
            log.error("[createSeatOrder][预约时长超出允许范围 - 分钟数: {}]", minutes);
            throw new RuntimeException("预约时长需在30分钟到12小时之间");
        }

        LocalDateTime now = LocalDateTime.now();
        if (createReqVO.getStartTime().isBefore(now)) {
            log.error("[createSeatOrder][预约开始时间不能早于当前时间 - 当前时间: {}, 预约时间: {}]",
                    now, createReqVO.getStartTime());
            throw exception(SEAT_BOOKING_TIME_INVALID);
        }

        if (createReqVO.getStartTime().isAfter(now.plusDays(7))) {
            log.error("[createSeatOrder][预约开始时间超出允许范围 - 当前时间: {}, 预约时间: {}]",
                    now, createReqVO.getStartTime());
            throw new RuntimeException("仅支持预约未来7天内的座位");
        }

        MemberUserDO user = memberUserMapper.selectById(userId);
        if (user == null) {
            log.error("[createSeatOrder][用户不存在 userId={}]", userId);
            throw new RuntimeException("用户不存在");
        }

        DeliveryPickUpStoreDO room = deliveryPickUpStoreService.getDeliveryPickUpStore(createReqVO.getRoomId());
        if (room == null) {
            log.error("[createSeatOrder][门店不存在 roomId={}]", createReqVO.getRoomId());
            throw new RuntimeException("自习室不存在");
        }

        SeatDO seat = seatService.getSeat(createReqVO.getSeatId());
        if (seat == null) {
            log.error("[createSeatOrder][座位不存在 seatId={}]", createReqVO.getSeatId());
            throw new RuntimeException("座位不存在");
        }

        if (!ObjectUtil.equal(seat.getRoomId(), createReqVO.getRoomId())) {
            log.error("[createSeatOrder][座位与门店不匹配 roomId={}, seat.roomId={}, seatId={}]",
                    createReqVO.getRoomId(), seat.getRoomId(), seat.getSeatId());
            throw new RuntimeException("座位和自习室不匹配");
        }

        Long conflictCount = seatOrderMapper.selectConflictCount(
                createReqVO.getRoomId(),
                createReqVO.getSeatId(),
                createReqVO.getStartTime(),
                createReqVO.getEndTime()
        );
        if (conflictCount != null && conflictCount > 0) {
            log.error("[createSeatOrder][座位已被预约 roomId={}, seatId={}, startTime={}, endTime={}]",
                    createReqVO.getRoomId(), createReqVO.getSeatId(),
                    createReqVO.getStartTime(), createReqVO.getEndTime());
            throw new RuntimeException("该座位当前时段已被预约");
        }

        Integer pricePerHour = ObjectUtil.defaultIfNull(seat.getSeatPrice(), 0);
        double totalYuan = pricePerHour * (minutes / 60.0);
        int totalPrice = (int) Math.round(totalYuan * 100);

        String userName = ObjectUtil.isNotEmpty(user.getNickname()) ? user.getNickname()
                : ObjectUtil.isNotEmpty(user.getName()) ? user.getName()
                : ObjectUtil.isNotEmpty(user.getMobile()) ? user.getMobile()
                : String.valueOf(userId);

        String roomName = room.getName();

        Integer seatArea = Boolean.TRUE.equals(seat.getSeatArea()) ? 1 : 0;
        String seatAreaText = seatArea == 1 ? "舒适区" : "经济区";

        Integer seatType = ObjectUtil.defaultIfNull(seat.getSeatType(), 0);
        String seatTypeText;
        switch (seatType) {
            case 1:
                seatTypeText = "独享";
                break;
            case 2:
                seatTypeText = "双人";
                break;
            case 3:
                seatTypeText = "共享";
                break;
            default:
                seatTypeText = "普通";
                break;
        }

        double hours = minutes / 60.0;
        String hoursText = (hours % 1 == 0)
                ? String.format("%.0f小时", hours)
                : String.format("%.1f小时", hours);

        String description = String.format("预约订单：%s - %s %s %s号座，%s - %s，共%s",
                roomName,
                seatAreaText,
                seatTypeText,
                ObjectUtil.defaultIfNull(seat.getSeatNum(), 0),
                createReqVO.getStartTime().toLocalTime(),
                createReqVO.getEndTime().toLocalTime(),
                hoursText);

        SeatOrderDO order = SeatOrderDO.builder()
                .userId(userId)
                .userName(userName)
                .roomId(room.getId())
                .roomName(roomName)
                .seatId(seat.getSeatId())
                .seatType(seatType)
                .seatNum(seat.getSeatNum())
                .seatArea(seatArea)
                .seatPrice(seat.getSeatPrice())
                .description(description)
                .startTime(createReqVO.getStartTime())
                .endTime(createReqVO.getEndTime())
                .hours(hours)
                .total(totalPrice)
                .payStatus(SeatOrderPayStatusEnum.UNPAID.getStatus())
                .status(SeatOrderStatusEnum.UNPAID.getStatus())
                .tenantId(tenantId)
                .build();

        seatOrderMapper.insert(order);

        if (order.getId() == null) {
            log.error("[createSeatOrder][插入订单成功但ID未回填，tenantId:{}]", tenantId);
            throw new RuntimeException("订单创建失败：ID生成异常");
        }
        log.info("[createSeatOrder][插入订单成功，订单ID:{}]", order.getId());

        Long payOrderId = payOrderApi.createOrder(new PayOrderCreateReqDTO()
                .setAppKey(PAY_APP_KEY)
                .setUserIp(ServletUtils.getClientIP())
                .setUserId(userId)
                .setUserType(UserTypeEnum.MEMBER.getValue())
                .setMerchantOrderId(order.getId().toString())
                .setSubject("自习室预约")
                .setBody("座位预约服务")
                .setPrice(order.getTotal())
                .setExpireTime(LocalDateTimeUtils.addTime(Duration.ofMinutes(30))));

        SeatOrderDO updateOrder = SeatOrderDO.builder()
                .id(order.getId())
                .payOrderId(payOrderId)
                .tenantId(tenantId)
                .build();

        int updateCount = seatOrderMapper.updateById(updateOrder);
        if (updateCount == 0) {
            log.error("[createSeatOrder][更新订单支付ID失败，订单ID:{}, 支付ID:{}，tenantId:{}]",
                    order.getId(), payOrderId, tenantId);
            throw new RuntimeException("订单更新失败：支付ID回写失败");
        }

        log.info("[createSeatOrder][更新订单支付ID成功，订单ID:{}, 支付ID:{}]", order.getId(), payOrderId);
        return order.getId();
    }

    @Override
    public SeatOrderDO getSeatOrder(Long id) {
        return seatOrderMapper.selectById(id);
    }

    @Override
    public SeatOrderDO getAppSeatOrder(Long id) {
        SeatOrderDO order = seatOrderMapper.selectById(id);
        if (order == null) {
            throw exception(SEAT_ORDER_NOT_FOUND);
        }
        Long userId = getLoginUserId();
        if (!ObjectUtil.equal(order.getUserId(), userId)) {
            throw new RuntimeException("无权查看该订单");
        }
        refreshOrderStatusIfNecessary(order);
        return seatOrderMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSeatOrderPaid(Long id, Long payOrderId) {
        log.info("[updateSeatOrderPaid][开始处理订单({})的支付回调，支付单({})]", id, payOrderId);

        SeatOrderDO order = seatOrderMapper.selectById(id);
        if (order == null) {
            log.error("[updateSeatOrderPaid][订单({})不存在]", id);
            throw exception(SEAT_ORDER_NOT_FOUND);
        }

        if (SeatOrderPayStatusEnum.PAID.getStatus().equals(order.getPayStatus())) {
            if (ObjectUtil.equals(order.getPayOrderId(), payOrderId)) {
                log.warn("[updateSeatOrderPaid][订单({})已支付且支付单匹配，忽略重复回调]", id);
                return;
            }
            log.error("[updateSeatOrderPaid][订单({})已支付，但支付单不匹配！当前绑定:{}, 回调:{}]",
                    id, order.getPayOrderId(), payOrderId);
            throw exception(SEAT_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }

        PayOrderRespDTO payOrder = validatePayOrderPaid(order, payOrderId);

        int updateCount = seatOrderMapper.updateByIdAndPayStatus(id,
                SeatOrderPayStatusEnum.UNPAID.getStatus(),
                SeatOrderDO.builder()
                        .payStatus(SeatOrderPayStatusEnum.PAID.getStatus())
                        .status(SeatOrderStatusEnum.PAID.getStatus())
                        .payTime(LocalDateTime.now())
                        .payChannelCode(payOrder.getChannelCode())
                        .build());
        if (updateCount == 0) {
            log.error("[updateSeatOrderPaid][订单({})更新状态失败，可能状态已被其他线程修改]", id);
            throw exception(SEAT_ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }

        try {
            log.info("[updateSeatOrderPaid][订单({})支付成功，业务处理完成]", id);
        } catch (Exception e) {
            log.error("[updateSeatOrderPaid][订单({})后续业务处理异常]", id, e);
        }
    }

    /**
     * 校验支付单
     */
    private PayOrderRespDTO validatePayOrderPaid(SeatOrderDO order, Long payOrderId) {
        PayOrderRespDTO payOrder = payOrderApi.getOrder(payOrderId);
        if (payOrder == null) {
            throw exception(ErrorCodeConstants.PAY_ORDER_NOT_FOUND);
        }

        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validatePayOrderPaid][订单({})支付单状态不正确，status={}]",
                    order.getId(), payOrder.getStatus());
            throw exception(ErrorCodeConstants.PAY_ORDER_STATUS_IS_NOT_WAITING);
        }

        if (notEqual(payOrder.getPrice(), order.getTotal())) {
            log.error("[validatePayOrderPaid][订单({})金额不匹配！订单:{}, 支付单:{}]",
                    order.getId(), order.getTotal(), payOrder.getPrice());
            throw exception(SEAT_ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }

        if (notEqual(payOrder.getMerchantOrderId(), order.getId().toString())) {
            throw exception(ErrorCodeConstants.PAY_ORDER_EXTENSION_IS_PAID);
        }

        return payOrder;
    }

    @Override
    public PageResult<SeatOrderDO> getSeatOrderPage(AppSeatOrderPageReqVO pageReqVO) {
        Long userId = getLoginUserId();
        PageResult<SeatOrderDO> pageResult = seatOrderMapper.selectPageByUserId(pageReqVO, userId);
        if (pageResult.getList() != null) {
            pageResult.getList().forEach(this::refreshOrderStatusIfNecessary);
        }
        return pageResult;
    }

    @Override
    public PageResult<SeatOrderDO> getSeatOrderPageForAdmin(SeatOrderPageReqVO pageReqVO) {
        return seatOrderMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSeatOrder(Long id) {
        SeatOrderDO order = seatOrderMapper.selectById(id);
        if (order == null) {
            throw exception(SEAT_ORDER_NOT_FOUND);
        }

        if (!(SeatOrderStatusEnum.UNPAID.getStatus().equals(order.getStatus())
                || SeatOrderStatusEnum.CANCELED.getStatus().equals(order.getStatus())
                || SeatOrderStatusEnum.CLOSED.getStatus().equals(order.getStatus()))) {
            throw new RuntimeException("当前订单状态不允许删除");
        }

        seatOrderMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSeatOrderListByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (Long id : ids) {
            deleteSeatOrder(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelSeatOrder(Long id) {
        SeatOrderDO order = seatOrderMapper.selectById(id);
        if (order == null) {
            throw exception(SEAT_ORDER_NOT_FOUND);
        }

        Long userId = getLoginUserId();
        if (!ObjectUtil.equal(order.getUserId(), userId)) {
            throw new RuntimeException("无权取消该订单");
        }

        if (SeatOrderStatusEnum.CANCELED.getStatus().equals(order.getStatus())
                || SeatOrderStatusEnum.CLOSED.getStatus().equals(order.getStatus())) {
            return;
        }

        if (SeatOrderPayStatusEnum.PAID.getStatus().equals(order.getPayStatus())) {
            throw new RuntimeException("订单已支付，不允许取消");
        }

        if (!SeatOrderStatusEnum.UNPAID.getStatus().equals(order.getStatus())) {
            throw new RuntimeException("当前订单状态不允许取消");
        }

        SeatOrderDO updateObj = SeatOrderDO.builder()
                .id(id)
                .status(SeatOrderStatusEnum.CANCELED.getStatus())
                .cancelTime(LocalDateTime.now())
                .cancelReason("用户主动取消")
                .build();

        int count = seatOrderMapper.updateById(updateObj);
        if (count == 0) {
            throw new RuntimeException("取消订单失败");
        }

        try {
            if (order.getPayOrderId() != null) {
                // 如果你的支付模块支持主动关闭支付单，可在这里调用
                // payOrderApi.closeOrder(order.getPayOrderId());
            }
        } catch (Exception e) {
            log.error("[cancelSeatOrder][关闭支付单失败, orderId={}, payOrderId={}]", id, order.getPayOrderId(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void closeExpiredUnpaidOrders() {
        PageResult<SeatOrderDO> pageResult = seatOrderMapper.selectPage(
                new SeatOrderPageReqVO() {{
                    setPageNo(1);
                    setPageSize(Integer.MAX_VALUE);
                    setStatus(SeatOrderStatusEnum.UNPAID.getStatus());
                    setPayStatus(SeatOrderPayStatusEnum.UNPAID.getStatus());
                }}
        );

        List<SeatOrderDO> orders = pageResult.getList();
        if (orders == null || orders.isEmpty()) {
            return;
        }

        for (SeatOrderDO order : orders) {
            refreshOrderStatusIfNecessary(order);
        }
    }

    /**
     * 同步业务订单状态：
     * 如果业务订单仍是待支付，但支付单已关闭，则同步为支付超时关闭
     */
    private void refreshOrderStatusIfNecessary(SeatOrderDO order) {
        if (order == null) {
            return;
        }
        if (!SeatOrderStatusEnum.UNPAID.getStatus().equals(order.getStatus())) {
            return;
        }
        if (!SeatOrderPayStatusEnum.UNPAID.getStatus().equals(order.getPayStatus())) {
            return;
        }
        if (order.getPayOrderId() == null) {
            return;
        }

        try {
            PayOrderRespDTO payOrder = payOrderApi.getOrder(order.getPayOrderId());
            if (payOrder == null) {
                return;
            }

            // 30 代表支付关闭，与你前端收银台逻辑保持一致
            if (ObjectUtil.equal(payOrder.getStatus(), 30)) {
                SeatOrderDO updateObj = SeatOrderDO.builder()
                        .id(order.getId())
                        .status(SeatOrderStatusEnum.CLOSED.getStatus())
                        .closeTime(LocalDateTime.now())
                        .closeReason("支付超时自动关闭")
                        .build();
                seatOrderMapper.updateById(updateObj);
                order.setStatus(SeatOrderStatusEnum.CLOSED.getStatus());
                order.setCloseTime(updateObj.getCloseTime());
                order.setCloseReason(updateObj.getCloseReason());
            }
        } catch (Exception e) {
            log.error("[refreshOrderStatusIfNecessary][同步订单状态失败, orderId={}]", order.getId(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refreshPaidOrderLifecycle() {
        LocalDateTime now = LocalDateTime.now();
        int paidToUsing = seatOrderMapper.updatePaidToUsing(now);
        int usingToFinished = seatOrderMapper.updateUsingToFinished(now);
        int paidToFinished = seatOrderMapper.updatePaidToFinishedDirectly(now);
        log.info("[refreshPaidOrderLifecycle][订单生命周期刷新完成 now={}, paidToUsing={}, usingToFinished={}, paidToFinished={}]",
                now, paidToUsing, usingToFinished, paidToFinished);
    }

}