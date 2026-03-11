package cn.iocoder.yudao.module.studyroom.service.banner;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerSaveReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.banner.vo.AppStudyroomBannerRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.banner.StudyroomBannerDO;

import java.util.List;

public interface StudyroomBannerService {

    Long createBanner(StudyroomBannerSaveReqVO createReqVO);

    void updateBanner(StudyroomBannerSaveReqVO updateReqVO);

    void deleteBanner(Long id);

    StudyroomBannerDO getBanner(Long id);

    PageResult<StudyroomBannerDO> getBannerPage(StudyroomBannerPageReqVO pageReqVO);

    List<AppStudyroomBannerRespVO> getAppBannerList(Long roomId);

}