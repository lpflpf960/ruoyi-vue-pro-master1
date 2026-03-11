package cn.iocoder.yudao.module.studyroom.job;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.studyroom.service.seatorder.SeatOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SeatOrderStatusRefreshJob implements JobHandler {

    @Resource
    private SeatOrderService seatOrderService;

    @Override
    @TenantJob
    public String execute(String param) {
        try {
            log.info("[SeatOrderStatusRefreshJob][开始刷新座位订单生命周期状态][param={}]", param);
            seatOrderService.closeExpiredUnpaidOrders();
            seatOrderService.refreshPaidOrderLifecycle();
            log.info("[SeatOrderStatusRefreshJob][刷新座位订单生命周期状态结束][param={}]", param);
            return "[SeatOrderStatusRefreshJob][刷新座位订单生命周期状态结束]"+param;
        } catch (Exception e) {
            log.error("[SeatOrderStatusRefreshJob][刷新座位订单生命周期状态异常][param={}]", param, e);
            throw e;
        }
    }

}