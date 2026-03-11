package cn.iocoder.yudao.module.studyroom.service.info;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.info.vo.InfoSaveReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.info.vo.AppInfoPageReqVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.info.InfoDO;

public interface InfoService {

    Long createInfo(InfoSaveReqVO createReqVO);

    void updateInfo(InfoSaveReqVO updateReqVO);

    void deleteInfo(Long id);

    InfoDO getInfo(Long id);

    PageResult<InfoDO> getInfoPage(InfoPageReqVO pageReqVO);

    void publishInfo(Long id);

    void offlineInfo(Long id);

    InfoDO getAppInfo(Long id, Long roomId);

    PageResult<InfoDO> getAppInfoPage(AppInfoPageReqVO pageReqVO);
}