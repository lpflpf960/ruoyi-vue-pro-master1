package cn.iocoder.yudao.module.studyroom.convert.banner;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerRespVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerSaveReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.banner.vo.AppStudyroomBannerRespVO;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.banner.StudyroomBannerDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudyroomBannerConvert {

    StudyroomBannerConvert INSTANCE = Mappers.getMapper(StudyroomBannerConvert.class);

    StudyroomBannerDO convert(StudyroomBannerSaveReqVO bean);

    StudyroomBannerRespVO convert(StudyroomBannerDO bean);

    List<StudyroomBannerRespVO> convertList(List<StudyroomBannerDO> list);

    PageResult<StudyroomBannerRespVO> convertPage(PageResult<StudyroomBannerDO> page);

    AppStudyroomBannerRespVO convertApp(StudyroomBannerDO bean);

    List<AppStudyroomBannerRespVO> convertAppList(List<StudyroomBannerDO> list);

}