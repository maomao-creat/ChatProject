package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.ArrayView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 徐学坤 on 2018/2/1.
 */
public class JiLuPresenter extends RefreshAndLoadMorePresenter<ArrayView> {
    @Override
    public void getData(final int page, final int count) {
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SelectApplyList");
        map.put("startRowIndex",(page-1)*count);
        map.put("maximumRows",count);
        map.put("userid",new UserUtil(view.getContext()).getUserId());
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Subscription subscription = Net.getService()
                    .SelectApplyList(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ArrayList<FriendMap>>>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.error();
                        }

                        @Override
                        public void onNext(Res<ArrayList<FriendMap>> res) {
                            if (res.code== Const.OK){
                                setDataStatus(page, count, res);
                            }else {
                                view.error();
                            }
                        }
                    });

            addSubscription(subscription);
        }catch (Exception e){
        }
    }
}
