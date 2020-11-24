package com.zykj.samplechat.presenter;


import com.zykj.samplechat.model.SentRecords;

import java.util.List;

/**
 * 精品店铺的bean对象
 * Created by 11655 on 2016/10/14.
 */

public class HotSellerListBean  {

    private List<SentRecords> shuju;

    public HotSellerListBean(List<SentRecords> shuju) {
        this.shuju = shuju;
    }

    public List<SentRecords> getShuju() {
        return shuju;
    }

    public void setShuju(List<SentRecords> shuju) {
        this.shuju = shuju;
    }
}
