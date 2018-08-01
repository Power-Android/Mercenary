package com.power.mercenary.bean;

import com.baidu.mapapi.search.core.PoiInfo;

/**
 * Created by Administrator on 2018/7/27.
 */

public class PoiBean {
    private PoiInfo poiInfo;
    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public PoiInfo getPoiInfo() {
        return poiInfo;
    }

    public void setPoiInfo(PoiInfo poiInfo) {
        this.poiInfo = poiInfo;
    }
}
