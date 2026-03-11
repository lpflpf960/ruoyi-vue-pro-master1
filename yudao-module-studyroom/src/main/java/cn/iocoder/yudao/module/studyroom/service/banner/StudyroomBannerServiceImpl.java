package cn.iocoder.yudao.module.studyroom.service.banner;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerPageReqVO;
import cn.iocoder.yudao.module.studyroom.controller.admin.banner.vo.StudyroomBannerSaveReqVO;
import cn.iocoder.yudao.module.studyroom.controller.app.banner.vo.AppStudyroomBannerRespVO;
import cn.iocoder.yudao.module.studyroom.convert.banner.StudyroomBannerConvert;
import cn.iocoder.yudao.module.studyroom.dal.dataobject.banner.StudyroomBannerDO;
import cn.iocoder.yudao.module.studyroom.dal.mysql.banner.StudyroomBannerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

@Service
@Validated
public class StudyroomBannerServiceImpl implements StudyroomBannerService {

    @Resource
    private StudyroomBannerMapper studyroomBannerMapper;

    @Override
    public Long createBanner(StudyroomBannerSaveReqVO createReqVO) {
        validateTime(createReqVO);
        StudyroomBannerDO banner = StudyroomBannerConvert.INSTANCE.convert(createReqVO);
        studyroomBannerMapper.insert(banner);
        return banner.getId();
    }

    @Override
    public void updateBanner(StudyroomBannerSaveReqVO updateReqVO) {
        if (updateReqVO.getId() == null) {
            throw new IllegalArgumentException("轮播图编号不能为空");
        }
        validateBannerExists(updateReqVO.getId());
        validateTime(updateReqVO);
        StudyroomBannerDO updateObj = StudyroomBannerConvert.INSTANCE.convert(updateReqVO);
        studyroomBannerMapper.updateById(updateObj);
    }

    @Override
    public void deleteBanner(Long id) {
        validateBannerExists(id);
        studyroomBannerMapper.deleteById(id);
    }

    @Override
    public StudyroomBannerDO getBanner(Long id) {
        return studyroomBannerMapper.selectById(id);
    }

    @Override
    public PageResult<StudyroomBannerDO> getBannerPage(StudyroomBannerPageReqVO pageReqVO) {
        return studyroomBannerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<AppStudyroomBannerRespVO> getAppBannerList(Long roomId) {
        List<StudyroomBannerDO> list = studyroomBannerMapper.selectAppList(roomId);
        return StudyroomBannerConvert.INSTANCE.convertAppList(list);
    }

    private void validateBannerExists(Long id) {
        if (studyroomBannerMapper.selectById(id) == null) {
            throw new IllegalArgumentException("轮播图不存在");
        }
    }

    private void validateTime(StudyroomBannerSaveReqVO reqVO) {
        if (reqVO.getStartTime() != null && reqVO.getEndTime() != null
                && reqVO.getStartTime().isAfter(reqVO.getEndTime())) {
            throw new IllegalArgumentException("生效开始时间不能晚于结束时间");
        }
    }

}