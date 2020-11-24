package com.zykj.samplechat.presenter;

import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.Net;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.presenter.base.RefreshAndLoadMorePresenter;
import com.zykj.samplechat.ui.view.FriendsView;
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
 ******************************************************
 *                                                    *
 *                                                    *
 *                       _oo0oo_                      *
 *                      o8888888o                     *
 *                      88" . "88                     *
 *                      (| -_- |)                     *
 *                      0\  =  /0                     *
 *                    ___/`---'\___                   *
 *                  .' \\|     |# '.                  *
 *                 / \\|||  :  |||# \                 *
 *                / _||||| -:- |||||- \               *
 *               |   | \\\  -  #/ |   |               *
 *               | \_|  ''\---/''  |_/ |              *
 *               \  .-\__  '-'  ___/-. /              *
 *             ___'. .'  /--.--\  `. .'___            *
 *          ."" '<  `.___\_<|>_/___.' >' "".          *
 *         | | :  `- \`.;`\ _ /`;.`/ - ` : | |        *
 *         \  \ `_.   \_ __\ /__ _/   .-` /  /        *
 *     =====`-.____`.___ \_____/___.-`___.-'=====     *
 *                       `=---='                      *
 *                                                    *
 *                                                    *
 *     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    *
 *                                                    *
 *               佛祖保佑         永无BUG              *
 *                                                    *
 *                                                    *
 ******************************************************
 *
 * Created by ninos on 2016/6/2.
 *
 */
public class FriendsPresenter extends RefreshAndLoadMorePresenter<FriendsView> {

//    private ArrayList<Friend> list=new ArrayList<>();
    private ArrayList<Friend> listAdd=new ArrayList<>();

    @Override
    public void getData(int page, int count) {
//        Map map = new HashMap();
//        map.put("key", Const.KEY);
//        map.put("uid",Const.UID);
//        map.put("function","GetFriendForKeyValue");
//        map.put("userid",new UserUtil(view.getContext()).getUserId());
//        String json = StringUtil.toJson(map);
//        try {
//            String data = AESOperator.getInstance().encrypt(json.length() + "&" + json);
//            GetFriendForKeyValue(data);
//        }catch (Exception ex){
//        }

        HttpUtils.FriendList(new SubscriberRes<ArrayList<MyHYLB>>() {
            @Override
            public void onSuccess(ArrayList<MyHYLB> userBean) {
                ArrayList<Friend> list=new ArrayList<>();
                int i=0;
                ArrayList<Friend> friends = new ArrayList<>();
                for (MyHYLB friendMap1 : userBean) {
                    i=i+1;
                    Friend friend = new Friend();
                    friend.NicName = friendMap1.getNickName();
                    friend.PhotoPath = friendMap1.getHeadUrl();
                    friend.MemnerId2 = friendMap1.getFriendId();
                    friend.MemnerId = i;
                    friend.UserCode = friendMap1.getUserCode();
                    //     friend.Id = friendMap1.getFriendId();
                    list.add(friend);
//                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(friend.MemnerId2 + "", friend.NicName, Uri.parse(Const.getbase(friend.PhotoPath ))));
                }
                view.successFound(list, friends,1);
                view.credit(1);
                view.refresh(false);
            }

            @Override
            public void completeDialog() {
            }
        });
//        Map map = new HashMap();
//        map.put("function",""+"FriendList");
//        map.put("userid",""+new UserUtil(view.getContext()).getUserId2());

//        NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){
//
//            @Override
//            public void onError(Request request, Exception e) {
//                view.errorFound();
//                view.refresh(false);
//                // LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(view.getContext(), "服务器错误");  //吐司
//            }
//
//            @Override
//            public void onResponse(String s) {
//
//                ArrayList<Friend> list=new ArrayList<>();
//                // LogUtils.i("xxxxx", "" +s);  //输出测试
//                if(NRUtils.getYse(view.getContext(),s)) {
//                    //  IsZH.getToast(view.getContext(), "成功");  //吐司
//                    List<MyHYLB> mye = NRUtils.getDataList(s,MyHYLB.class);
//                    //  LogUtils.i("xxxxx2", "" +mye.size());  //输出测试
//                    int i=0;
//                    ArrayList<Friend> friends = new ArrayList<>();
//                    for (MyHYLB friendMap1 : mye) {
//                        i=i+1;
//                        Friend friend = new Friend();
//                        friend.NicName = friendMap1.getNickName();
//                        friend.PhotoPath = friendMap1.getHeadUrl();
//                        friend.MemnerId2 = friendMap1.getFriendId();
//                        friend.MemnerId = i;
//                        friend.UserCode = friendMap1.getUserCode();
//                        //     friend.Id = friendMap1.getFriendId();
//                        list.add(friend);
//                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.MemnerId2 + "", friend.NicName, Uri.parse(Const.getbase(friend.PhotoPath ))));
//                    }
////                    for (FriendMap friendMap : res.data) {
////                        Friend friend = friendMap.Value;
////                        friend.Id = friendMap.Key;
////                        list.add(friend);
////                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.Id + "", friend.RemarkName.trim().equals("")?friend.NicName:friend.NicName, Uri.parse(Const.getbase(friend.PhotoPath ))));
////                    }
////                    for (FriendMap friendMap : res.data1) {
////                        Friend friend = friendMap.Value;
////                        friend.Id = friendMap.Key;
////                        friends.add(friend);
////                    }
//                    view.successFound(list, friends,1);
//                    view.credit(1);
//                    view.refresh(false);
//                }else{
//                    view.errorFound();
//                    view.refresh(false);
//                }
//            }
//
//        });
    }

//    public void GetFriendForKeyValue(String data){
//        list.clear();
//        Subscription subscription = Net.getService()
//                .GetFriendForKeyValue(data)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Res<ArrayList<FriendMap>>>() {
//                    @Override
//                    public void onCompleted() {}
//                    @Override
//                    public void onError(Throwable e) {
//                        view.errorFound();
//                        view.refresh(false);
//                    }
//                    @Override
//                    public void onNext(Res<ArrayList<FriendMap>> res) {
//                        if (res.code== Const.OK){
//                            ArrayList<Friend> friends = new ArrayList<>();
//                            for (FriendMap friendMap : res.data) {
//                                Friend friend = friendMap.Value;
//                                friend.Id = friendMap.Key;
//                                list.add(friend);
//                                RongIM.getInstance().refreshUserInfoCache(new UserInfo(friend.Id + "", friend.RemarkName.trim().equals("")?friend.NicName:friend.NicName, Uri.parse(Const.getbase(friend.PhotoPath ))));
//                            }
//                            for (FriendMap friendMap : res.data1) {
//                                Friend friend = friendMap.Value;
//                                friend.Id = friendMap.Key;
//                                friends.add(friend);
//                            }
//                            view.successFound(list, friends,res.Credit);
//                            view.credit(res.Credit);
//                            view.refresh(false);
//                        }else {
//                            view.errorFound();
//                            view.refresh(false);
//                        }
//                    }
//                });
//        addSubscription(subscription);
//    }

    public void getUserAdd(){//好友数量 无视
        Map map = new HashMap();
        map.put("key", Const.KEY);
        map.put("uid",Const.UID);
        map.put("function","SelectApplyList");
        map.put("startRowIndex",0);
        map.put("maximumRows",99);
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

                        }

                        @Override
                        public void onNext(Res<ArrayList<FriendMap>> res) {
                            if (res.code== Const.OK){
                                listAdd.clear();
                                int count = 0;
                                for (FriendMap friendMap : res.data) {
                                    if(friendMap.State == 0)
                                        count++;
                                }
                                view.success(count);
                            }else {
                            }
                            view.refresh(false);
                        }
                    });

            addSubscription(subscription);
        }catch (Exception e){
        }
    }
}