package cn.iocoder.yudao.module.studyroom.controller.app.room;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import cn.iocoder.yudao.framework.tenant.core.aop.TenantIgnore;
import cn.iocoder.yudao.module.studyroom.controller.admin.room.vo.RoomPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.room.vo.RoomRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.module.studyroom.service.room.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import java.util.List;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;


@Tag(name = "用户 APP - 自习室")
@RestController
@RequestMapping("/studyroom/room")
@Validated
public class AppRoomController {
    @Resource
    private RoomService roomService;
    @GetMapping("/list")
    @Operation(summary = "获得自习室列表")
    @PermitAll
    @TenantIgnore
    public CommonResult<List<RoomRespVO>> getRoomList() {
        List<RoomDO> listResult = roomService.getRoomList();
        return success(BeanUtils.toBean(listResult, RoomRespVO.class));
    }
}
