package cn.iocoder.yudao.module.studyroom.service.info;

import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.tenant.core.context.TenantContextHolder;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoSaveReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.info.vo.AppInfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.info.InfoDO;
import cn.iocoder.yudao.module.studyroom.dal.mysql.info.InfoMapper;
import cn.iocoder.yudao.module.studyroom.enums.InfoStatusEnum;
import cn.iocoder.yudao.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.iocoder.yudao.module.trade.service.delivery.DeliveryPickUpStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Validated
@Slf4j
public class InfoServiceImpl implements InfoService {

    @Resource
    private InfoMapper infoMapper;
    @Resource
    private DeliveryPickUpStoreService deliveryPickUpStoreService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createInfo(InfoSaveReqVO createReqVO) {
        Long tenantId = TenantContextHolder.getTenantId();

        String roomName = null;
        if (createReqVO.getRoomId() != null) {
            DeliveryPickUpStoreDO room = deliveryPickUpStoreService.getDeliveryPickUpStore(createReqVO.getRoomId());
            if (room == null) {
                throw new RuntimeException("门店不存在");
            }
            roomName = room.getName();
        }

        InfoDO info = InfoDO.builder()
                .title(createReqVO.getTitle())
                .category(createReqVO.getCategory())
                .summary(createReqVO.getSummary())
                .coverUrl(createReqVO.getCoverUrl())
                .content(createReqVO.getContent())
                .roomId(createReqVO.getRoomId())
                .roomName(roomName)
                .status(ObjectUtil.defaultIfNull(createReqVO.getStatus(), InfoStatusEnum.DRAFT.getStatus()))
                .sort(ObjectUtil.defaultIfNull(createReqVO.getSort(), 0))
                .sticky(ObjectUtil.defaultIfNull(createReqVO.getSticky(), false))
                .publishTime(createReqVO.getPublishTime())
                .browseCount(0)
                .tenantId(tenantId)
                .build();

        infoMapper.insert(info);
        return info.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(InfoSaveReqVO updateReqVO) {
        InfoDO old = validateInfoExists(updateReqVO.getId());

        String roomName = null;
        if (updateReqVO.getRoomId() != null) {
            DeliveryPickUpStoreDO room = deliveryPickUpStoreService.getDeliveryPickUpStore(updateReqVO.getRoomId());
            if (room == null) {
                throw new RuntimeException("门店不存在");
            }
            roomName = room.getName();
        }

        InfoDO updateObj = InfoDO.builder()
                .id(updateReqVO.getId())
                .title(updateReqVO.getTitle())
                .category(updateReqVO.getCategory())
                .summary(updateReqVO.getSummary())
                .coverUrl(updateReqVO.getCoverUrl())
                .content(updateReqVO.getContent())
                .roomId(updateReqVO.getRoomId())
                .roomName(roomName)
                .status(updateReqVO.getStatus())
                .sort(ObjectUtil.defaultIfNull(updateReqVO.getSort(), old.getSort()))
                .sticky(ObjectUtil.defaultIfNull(updateReqVO.getSticky(), old.getSticky()))
                .publishTime(updateReqVO.getPublishTime())
                .build();

        infoMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInfo(Long id) {
        validateInfoExists(id);
        infoMapper.deleteById(id);
    }

    @Override
    public InfoDO getInfo(Long id) {
        return infoMapper.selectById(id);
    }

    @Override
    public PageResult<InfoDO> getInfoPage(InfoPageReqVO pageReqVO) {
        return infoMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishInfo(Long id) {
        InfoDO info = validateInfoExists(id);
        InfoDO updateObj = InfoDO.builder()
                .id(id)
                .status(InfoStatusEnum.PUBLISHED.getStatus())
                .publishTime(info.getPublishTime() == null ? LocalDateTime.now() : info.getPublishTime())
                .build();
        infoMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offlineInfo(Long id) {
        validateInfoExists(id);
        InfoDO updateObj = InfoDO.builder()
                .id(id)
                .status(InfoStatusEnum.OFFLINE.getStatus())
                .build();
        infoMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public InfoDO getAppInfo(Long id, Long roomId) {
        InfoDO info = validateInfoExists(id);

        if (!InfoStatusEnum.PUBLISHED.getStatus().equals(info.getStatus())) {
            throw new RuntimeException("信息不存在或未发布");
        }
        if (info.getPublishTime() == null || info.getPublishTime().isAfter(LocalDateTime.now())) {
            throw new RuntimeException("信息不存在或未到发布时间");
        }
        if (info.getRoomId() != null && roomId != null && !ObjectUtil.equal(info.getRoomId(), roomId)) {
            throw new RuntimeException("无权查看该门店信息");
        }

        infoMapper.incrementBrowseCount(id);
        return infoMapper.selectById(id);
    }

    @Override
    public PageResult<InfoDO> getAppInfoPage(AppInfoPageReqVO pageReqVO) {
        return infoMapper.selectAppPage(pageReqVO, LocalDateTime.now());
    }

    private InfoDO validateInfoExists(Long id) {
        InfoDO info = infoMapper.selectById(id);
        if (info == null) {
            throw new RuntimeException("信息不存在");
        }
        return info;
    }
}