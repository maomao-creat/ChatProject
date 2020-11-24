package com.zykj.samplechat.presenter;

import android.graphics.Color;
import android.view.View;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.view.GroupInfoView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ninos on 2016/7/14.
 */
public class GroupInfoPresenter extends BasePresenterImp<GroupInfoView> {
    private ArrayList<Group> list = new ArrayList<>();
    private Dialog dialog;
    private int id;

    public void MessageRepeatAndFavorite(String data) {
        Subscription subscription = Net.getService()
                .MessageRepeatAndFavorite(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            view.successLike();
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void MessageRepeatAndFavoriteCancel(String data) {
        Subscription subscription = Net.getService()
                .MessageRepeatAndFavoriteCancel(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            view.successLikeCancel();
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }


    public void GetDongtaiById(String data) {
        Subscription subscription = Net.getService()
                .GetDongtaiById(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res<Group>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res<Group> res) {
                        if (res.code == Const.OK) {
                            view.successLikeCancel();
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void MessageComment(String data, final String content) {
        Subscription subscription = Net.getService()
                .MessageComment(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            Comment comment = new Comment();
                            comment.Content = content;
                            comment.Id = res.insertid;
                            view.commentSuccess(comment);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void CommentComment(String data, final String nicName, final int commenterId, final String content) {
        Subscription subscription = Net.getService()
                .CommentComment(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            Comment comment = new Comment();
                            comment.Content = content;
                            comment.CommentedNicName = nicName;
                            comment.ParentId = commenterId;
                            comment.Id = res.insertid;
                            view.replySuccess(comment);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void showDialogDelete() {
        if (dialog==null)
            dialog = new Dialog(view.getContext()).title("删除提示").backgroundColor(Color.parseColor("#ffffff")).contentView(R.layout.dialog_delete_msg_2).positiveAction("确定").positiveActionClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Delete(id);
                    dialog.dismiss();
                }
            }).cancelable(true);
        dialog.show();
    }

    public void DeleteComment(int id) {
        this.id = id;
        showDialogDelete();


    }

    public void Delete(int id){
        String data = "";
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","DeleteComment");
        map.put("id",id);
        String json = StringUtil.toJson(map);
        try{
            data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
        }catch (Exception ex){}
        Subscription subscription = Net.getService()
                .SaveSpaceImage(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            view.successDelComment();
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void DelDongtai(String data) {
        Subscription subscription = Net.getService()
                .DelDongtai(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Res>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Res res) {
                        if (res.code == Const.OK) {
                            view.successDelete();
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }
}
