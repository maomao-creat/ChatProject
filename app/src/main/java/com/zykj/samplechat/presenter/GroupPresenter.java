package com.zykj.samplechat.presenter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.Comment;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.GroupView;
import com.zykj.samplechat.utils.AESOperator;
import com.zykj.samplechat.utils.Encoder;
import com.zykj.samplechat.utils.StringUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * *****************************************************
 * *
 * *
 * _oo0oo_                      *
 * o8888888o                     *
 * 88" . "88                     *
 * (| -_- |)                     *
 * 0\  =  /0                     *
 * ___/`---'\___                   *
 * .' \\|     |# '.                  *
 * / \\|||  :  |||# \                 *
 * / _||||| -:- |||||- \               *
 * |   | \\\  -  #/ |   |               *
 * | \_|  ''\---/''  |_/ |              *
 * \  .-\__  '-'  ___/-. /              *
 * ___'. .'  /--.--\  `. .'___            *
 * ."" '<  `.___\_<|>_/___.' >' "".          *
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 * \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 * =====`-.____`.___ \_____/___.-`___.-'=====     *
 * `=---='                      *
 * *
 * *
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 * *
 * 佛祖保佑         永无BUG              *
 * *
 * *
 * *****************************************************
 * <p/>
 * Created by ninos on 2016/6/2.
 */
public class GroupPresenter extends RefreshAndLoadMorePresenter<GroupView> {
    private ArrayList<Group> list = new ArrayList<>();
    private Dialog dialog;
    private int id;

    @Override
    public void getData(final int page, final int count) {
        list.clear();
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid", Const.UID);
        map.put("function", "GetDongtaiPageByFriends");
        map.put("userid", new UserUtil(view.getContext()).getUserId());
        map.put("startRowIndex", (page - 1) * count);
        map.put("maximumRows", count);
        String json = StringUtil.toJson(map);
        try {
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
            Log.e("TAG",data);
            Subscription subscription = Net.getService()
                    .GetDongtaiPageByFriends(data)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ArrayList<Group>>>() {

                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.refresh(false);
                        }

                        @Override
                        public void onNext(Res<ArrayList<Group>> res) {
                            if (res.code == Const.OK) {
                                view.success(res.data,res.SpaceImagePath);
                                setDataStatus(page, count, res);
                                view.refresh(false);
                            } else {
                                view.refresh(false);
                            }
                        }
                    });

            addSubscription(subscription);
        } catch (Exception e) {
        }
    }

    public void MessageRepeatAndFavorite(String data, final int pos) {
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
                            view.successLike(pos);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void MessageRepeatAndFavoriteCancel(String data, final int pos) {
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
                            view.successLikeCancel(pos);
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

    public void MessageComment(String data, final String content, final int pos) {
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
                            view.commentSuccess(pos, comment);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void CommentComment(String data, final String nicName, final int commenterId, final String content, final int position) {
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
                            view.replySuccess(position, comment);
                        } else {
                        }
                    }
                });

        addSubscription(subscription);
    }

    /**
     * 显示评论框
     *
     * @param id
     * @param position
     * @param v
     */
    public void showCommentInput(int id, int position, View v) {
        view.showCommentInput(id, position, v);
    }

    public void showReplyInput(int id, String nicName, int commenterId, int position, View v) {
        view.showReplyInput(id, nicName, commenterId, position, v);

    }

    public void UpLoadImage(String path,String id){
        try {
            String fileStream = Encoder.getEnocodeStr(path);

            Map map = new HashMap();
            map.put("key", Const.KEY);
            map.put("uid",Const.UID);
            map.put("function","UpLoadImage");
            map.put("fileName",path);
            map.put("filestream",fileStream);
            String json = StringUtil.toJson(map);
            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);

            Subscription subscription = Net.getService()
                    .UpLoadImage(data)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Res<ImageTemp>>() {
                        @Override
                        public void onNext(Res<ImageTemp> res) {
                            if (res.code == Const.OK) {

                                String p = res.data.BigImagePath;

                                view.toast("上传成功");

                                Map map = new HashMap();
                                map.put("key", Const.KEY);
                                map.put("uid",Const.UID);
                                map.put("function","SaveSpaceImage");
                                map.put("userid",new UserUtil(view.getContext()).getUserId());
                                map.put("imagepath",res.data.BigImagePath);
                                String json = StringUtil.toJson(map);
                                try{
                                    String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
                                    SaveSpaceImage(data,p);
                                }catch (Exception ex){}

                            } else {
                            }
                        }

                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });

            addSubscription(subscription);

        } catch (Exception e) {
            e.toString();
        }
    }

    public void SaveSpaceImage(String data, final String p) {
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
                            view.topImage(p);
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
}
