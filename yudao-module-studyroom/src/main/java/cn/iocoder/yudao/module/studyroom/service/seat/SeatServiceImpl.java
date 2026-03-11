package cn.iocoder.yudao.module.studyroom.service.seat;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.SeatPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.SeatRespVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.seat.vo.SeatSaveReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.seat.vo.AppSeatRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.seat.SeatDO;
import cn.iocoder.yudao.module.studyroom.dal.mysql.seat.SeatMapper;
import cn.iocoder.yudao.module.studyroom.dal.mysql.seatOrder.SeatOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.studyroom.enums.ErrorCodeConstants.SEAT_NOT_EXISTS;

/**
 * 座位 Service 实现类
 */
@Slf4j
@Service
@Validated
public class SeatServiceImpl implements SeatService {

    @Resource
    private SeatMapper seatMapper;

    @Resource
    private SeatOrderMapper seatOrderMapper;

    @Override
    public Long createSeat(SeatSaveReqVO createReqVO) {
        SeatDO seat = BeanUtils.toBean(createReqVO, SeatDO.class);
        seatMapper.insert(seat);
        return seat.getRoomId();
    }

    @Override
    public void updateSeat(SeatSaveReqVO updateReqVO) {
        validateSeatExists(updateReqVO.getSeatId());
        SeatDO updateObj = BeanUtils.toBean(updateReqVO, SeatDO.class);
        seatMapper.updateById(updateObj);
    }

    @Override
    public void deleteSeat(Long id) {
        validateSeatExists(id);
        seatMapper.deleteById(id);
    }

    @Override
    public void deleteSeatListByIds(List<Long> ids) {
        seatMapper.deleteByIds(ids);
    }

    private void validateSeatExists(Long id) {
        if (seatMapper.selectById(id) == null) {
            throw exception(SEAT_NOT_EXISTS);
        }
    }

    @Override
    public SeatDO getSeat(Long id) {
        return seatMapper.selectById(id);
    }

    @Override
    public PageResult<SeatDO> getSeatPage(SeatPageReqVO pageReqVO) {
        return seatMapper.selectPage(pageReqVO);
    }

    @Override
    public List<SeatDO> getSeatList(Long roomId) {
        return seatMapper.selectList(roomId);
    }

    /**
     * 按预约时间段查询座位列表，并附带 reserved 状态
     */
    @Override
    public List<AppSeatRespVO> getSeatListWithReserved(Long roomId, String startTime, Double orderTotal) throws UnsupportedEncodingException {
        List<SeatDO> seatList = seatMapper.selectList(roomId);
        if (seatList == null || seatList.isEmpty()) {
            return Collections.emptyList();
        }

        if (orderTotal == null || orderTotal <= 0) {
            orderTotal = 1D;
        }

        // 1. URL 解码，避免 %E6 这种编码字符串直接参与时间解析
        String decodedStartTime = URLDecoder.decode(startTime, String.valueOf(StandardCharsets.UTF_8)).trim();

        // 2. 兼容 yyyy-MM-dd HH:mm
        if (decodedStartTime.length() == 16) {
            decodedStartTime = decodedStartTime + ":00";
        }

        // 3. 按标准格式解析
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentStart;
        try {
            currentStart = LocalDateTime.parse(decodedStartTime, formatter);
        } catch (Exception e) {
            log.error("[getSeatListWithReserved][开始时间格式非法 startTime={} decodedStartTime={}]",
                    startTime, decodedStartTime, e);
            throw new RuntimeException("预约开始时间格式错误，正确格式应为 yyyy-MM-dd HH:mm:ss");
        }

        // 支持 0.5 小时
        long minutes = Math.round(orderTotal * 60);
        LocalDateTime currentEnd = currentStart.plusMinutes(minutes);

        // 查询当前时间段内已被占用的座位ID
        List<Long> reservedSeatIds = seatOrderMapper.selectReservedSeatIds(roomId, currentStart, currentEnd);
        Set<Long> reservedSeatIdSet = new HashSet<>(reservedSeatIds);

        List<AppSeatRespVO> result = new ArrayList<>();
        for (SeatDO seat : seatList) {
            AppSeatRespVO vo = BeanUtils.toBean(seat, AppSeatRespVO.class);
            vo.setReserved(reservedSeatIdSet.contains(seat.getSeatId()));
            result.add(vo);
        }
        return result;
    }
}