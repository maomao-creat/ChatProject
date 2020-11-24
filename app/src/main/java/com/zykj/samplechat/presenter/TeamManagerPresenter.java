package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.TeamInfoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;
import java.util.HashMap;
import java.util.Map;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by csh
 * Created date 2016/12/19.
 * Description 创建群组
 */

public class TeamManagerPresenter extends BasePresenterImp<TeamInfoView>{
    public void UpLoadImage(String data, final String name, final String idlist){
        Subscription subscription = Net.getService()
                .UpLoadImage(data)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<ImageTemp>>() {
                    @Override
                    public void onNext(Res<ImageTemp> res) {
                        if (res.code == Const.OK) {
                            Map<String,Object> map = new HashMap<>();
                            map.put("key", Const.KEY);
                            map.put("uid",Const.UID);
                            map.put("function","BuildTeam");
                            map.put("userid",new UserUtil(view.getContext()).getUserId());
                            map.put("idlist",0);
                            map.put("imagepath",res.data.BigImagePath);
                            map.put("teamname",name);
                            String json = StringUtil.toJson(map);
                            try {
                                String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                                BuildTeam(data,idlist);
                            }catch (Exception e){}
                        }
                    }
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.error();
                    }
                });
        addSubscription(subscription);
    }

    public void BuildTeam(String data, final String idlist){
        Subscription subscription = Net.getService()
                .BuildTeam(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Team>>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        view.error();
                    }
                    @Override
                    public void onNext(Res<Team> res) {
                        if (res.code== Const.OK){
                            YaoQing(idlist,res.data.TeamId,res.data);
                        }
                    }
                });
        addSubscription(subscription);
    }

    private void YaoQing(String dlist,int teamid, final Team team){
//        HttpUtils.YaoQing(new SubscriberRes<String>() {
//            @Override
//            public void onSuccess(String userBean) {
//                view.successTeam(team);
//            }
//            @Override
//            public void completeDialog() {
//
//            }
//        },dlist,teamid);
    }
}