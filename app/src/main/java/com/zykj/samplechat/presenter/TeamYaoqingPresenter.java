package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.TeamYaoqingView;
import java.util.ArrayList;

/**
 * Created by ninos on 2016/7/14.
 */
public class TeamYaoqingPresenter extends RefreshAndLoadMorePresenter<TeamYaoqingView> {

    private ArrayList<MyHYLB> list=new ArrayList<>();

    @Override
    public void getData(int page, int count) {}

    public void GetFriendForKeyValue(){
        list.clear();
        HttpUtils.FriendList(new SubscriberRes<ArrayList<MyHYLB>>() {
            @Override
            public void onSuccess(ArrayList<MyHYLB> res) {
                if(res!=null) {
                    view.successFound(res);
                    list = res;
                }
                view.refresh(false);
            }

            @Override
            public void completeDialog() {
            }
        });
    }



    public void YaoQing(String dlist,String teamid){
        HttpUtils.YaoQing(new SubscriberRes<String>() {
            @Override
            public void onSuccess(String userBean) {
                view.success();
            }
            @Override
            public void completeDialog() {

            }
        },dlist,teamid);
    }

    public ArrayList<MyHYLB> getList() {
        return list;
    }
}
