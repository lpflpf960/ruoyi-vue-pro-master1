package cn.iocoder.yudao.module.studyroom.controller.admin.room;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.studyroom.controller.admin.room.vo.*;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.room.RoomDO;
import cn.iocoder.yudao.module.studyroom.service.room.RoomService;

@Tag(name = "管理后台 - 自习室")
@RestController
@RequestMapping("/studyroom/room")
@Validated
public class RoomController {

    @Resource
    private RoomService roomService;

    @PostMapping("/create")
    @Operation(summary = "创建自习室")
    @PreAuthorize("@ss.hasPermission('studyroom:room:create')")
    public CommonResult<Long> createRoom(@Valid @RequestBody RoomSaveReqVO createReqVO) {
        return success(roomService.createRoom(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新自习室")
    @PreAuthorize("@ss.hasPermission('studyroom:room:update')")
    public CommonResult<Boolean> updateRoom(@Valid @RequestBody RoomSaveReqVO updateReqVO) {
        roomService.updateRoom(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除自习室")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('studyroom:room:delete')")
    public CommonResult<Boolean> deleteRoom(@RequestParam("id") Long id) {
        roomService.deleteRoom(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除自习室")
                @PreAuthorize("@ss.hasPermission('studyroom:room:delete')")
    public CommonResult<Boolean> deleteRoomList(@RequestParam("ids") List<Long> ids) {
        roomService.deleteRoomListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得自习室")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('studyroom:room:query')")
    public CommonResult<RoomRespVO> getRoom(@RequestParam("id") Long id) {
        RoomDO room = roomService.getRoom(id);
        return success(BeanUtils.toBean(room, RoomRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得自习室分页")
    @PreAuthorize("@ss.hasPermission('studyroom:room:query')")
    public CommonResult<PageResult<RoomRespVO>> getRoomPage(@Valid RoomPageReqVO pageReqVO) {
        PageResult<RoomDO> pageResult = roomService.getRoomPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, RoomRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出自习室 Excel")
    @PreAuthorize("@ss.hasPermission('studyroom:room:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportRoomExcel(@Valid RoomPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<RoomDO> list = roomService.getRoomPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "自习室.xls", "数据", RoomRespVO.class,
                        BeanUtils.toBean(list, RoomRespVO.class));
    }

}