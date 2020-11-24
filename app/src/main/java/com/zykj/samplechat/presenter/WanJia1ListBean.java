package com.zykj.samplechat.presenter;



import java.util.List;

/**
 * 精品店铺的bean对象
 * Created by 11655 on 2016/10/14.
 */

public class WanJia1ListBean {

    private List<WanJiaListBean> shuju;

    public WanJia1ListBean(List<WanJiaListBean> shuju) {
        this.shuju = shuju;
    }

    public List<WanJiaListBean> getShuju() {
        return shuju;
    }

    public void setShuju(List<WanJiaListBean> shuju) {
        this.shuju = shuju;
    }
}
