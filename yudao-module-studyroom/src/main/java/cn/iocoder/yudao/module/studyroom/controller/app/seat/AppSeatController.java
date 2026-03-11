package cn.iocoder.yudao.module.studyroom.controller.app.seat;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.SeatRespVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seat.vo.AppSeatRespVO;
import cn.iocoder.yudao.module.studyroom.service.seat.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 座位")
@RestController
@RequestMapping("/studyroom/seat")
@Validated
public class AppSeatController {

    @Resource
    private SeatService seatService;

    @GetMapping("/list")
    @Operation(summary = "获得座位列表（带当前时间段是否已预约）")
    @PermitAll
    @TenantIgnore
    public CommonResult<List<AppSeatRespVO>> getSeatList(@RequestParam Long roomId,
                                                         @RequestParam String startTime,
                                                         @RequestParam Double orderTotal) throws UnsupportedEncodingException {
        return success(seatService.getSeatListWithReserved(roomId, startTime, orderTotal));
    }
}