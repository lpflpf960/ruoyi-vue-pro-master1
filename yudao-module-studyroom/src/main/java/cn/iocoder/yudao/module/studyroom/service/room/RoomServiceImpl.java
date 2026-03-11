package cn.iocoder.yudao.module.studyroom.service.room;

import cn.iocoder.yudao.module.system.dal.dataobject.tenant.TenantDO;
import cn.iocoder.yudao.module.system.service.tenant.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.studyroom.controller.admin.room.vo.*;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.studyroom.dal.mysql.room.RoomMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.module.studyroom.enums.ErrorCodeConstants.*;

import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;


/**
 * 自习室 Service 实现类
 *
 * @author Lcoco
 */
@Service
@Validated
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomMapper roomMapper;
    @Resource
    private TenantService tenantService;
    @Override
    public Long createRoom(RoomSaveReqVO createReqVO) {
        // 插入
        RoomDO room = BeanUtils.toBean(createReqVO, RoomDO.class);
        TenantDO tenant = tenantService.getTenant(TenantContextHolder.getTenantId());
        log.info("当前租户编号:{}",TenantContextHolder.getTenantId());
        String tenantName=tenant.getName();
        room.setTenantName(tenantName);
        // 3. 插入数据库（原有逻辑，无需修改）
        log.info("创建自习室操作_room信息:{}",room);
        roomMapper.insert(room);
        // 返回
        return room.getRoomId();
    }


    @Override
    public void updateRoom(RoomSaveReqVO updateReqVO) {
        // 校验存在
        validateRoomExists(updateReqVO.getRoomId());
        // 更新
        RoomDO updateObj = BeanUtils.toBean(updateReqVO, RoomDO.class);
        roomMapper.updateById(updateObj);
    }

    @Override
    public void deleteRoom(Long id) {
        // 校验存在
        validateRoomExists(id);
        // 删除
        roomMapper.deleteById(id);
    }

    @Override
        public void deleteRoomListByIds(List<Long> ids) {
        // 删除
        roomMapper.deleteByIds(ids);
        }


    private void validateRoomExists(Long id) {
        if (roomMapper.selectById(id) == null) {
            throw exception(ROOM_NOT_EXISTS);
        }
    }

    @Override
    public RoomDO getRoom(Long id) {
        return roomMapper.selectById(id);
    }

    @Override
    public PageResult<RoomDO> getRoomPage(RoomPageReqVO pageReqVO) {
        return roomMapper.selectPage(pageReqVO);
    }

    //用户端 - 获取自习室列表
    @Override
    public List<RoomDO> getRoomList() {
        return roomMapper.selectList();
    }

}