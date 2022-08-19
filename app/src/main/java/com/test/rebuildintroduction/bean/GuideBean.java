package com.test.rebuildintroduction.bean;

/**
 * ViewPager2的数据类（Bean）
 *
 * @author sm2884 yuxiangliu
 * in 2022/08/02
 */
public class GuideBean {
    private int resType = 0;  // 判断当前展示控件为图片还是视频，资源类型为0时，控件为图片，为1时，控件为视频
    private int resourceId; // 图片或视频资源id号
    private String guideShowTitle;
    private String GuideShowDetail;

    public GuideBean(int resType, int resourceId, String guideShowTitle, String getGuideShowDetail) {
        this.resType = resType;
        this.resourceId = resourceId;
        this.guideShowTitle = guideShowTitle;
        this.GuideShowDetail = getGuideShowDetail;
    }

    public GuideBean(int resourceId, String guideShowTitle, String getGuideShowDetail) {
        this.resourceId = resourceId;
        this.guideShowTitle = guideShowTitle;
        this.GuideShowDetail = getGuideShowDetail;
    }

    public int getResType() {
        return resType;
    }

    public void setResType(int resType) {
        this.resType = resType;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getGuideShowTitle() {
        return guideShowTitle;
    }

    public void setGuideShowTitle(String guideShowTitle) {
        this.guideShowTitle = guideShowTitle;
    }

    public String getGuideShowDetail() {
        return GuideShowDetail;
    }

    public void setGuideShowDetail(String getGuideShowDetail) {
        this.GuideShowDetail = getGuideShowDetail;
    }
}
