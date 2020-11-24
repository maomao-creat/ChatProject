package com.zykj.samplechat.network;

import android.icu.util.VersionInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.RequestBody;
import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.model.CheckNewBean;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.Hongbao;
import com.zykj.samplechat.model.HongbaoList;
import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.model.MyshouyiBean;
import com.zykj.samplechat.model.MyxiaxianxinxBean;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.model.TiXianBean;
import com.zykj.samplechat.model.TongZhiBean;
import com.zykj.samplechat.model.UploadPhoto;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.model.VersonBean;
import com.zykj.samplechat.model.Xiangyu;
import com.zykj.samplechat.model.ZhunZhangBean;
import com.zykj.samplechat.model.kflb;
import com.zykj.samplechat.model.lbt;
import com.zykj.samplechat.model.mYshouyi;
import com.zykj.samplechat.model.mqunlb;
import com.zykj.samplechat.model.myql;
import com.zykj.samplechat.model.qunxxx;
import com.zykj.samplechat.model.sheqhyy;
import com.zykj.samplechat.model.sshyBean;
import com.zykj.samplechat.model.tj;
import com.zykj.samplechat.presenter.LeiBean;
import com.zykj.samplechat.presenter.base.HotZhiFuBaoBean;
import com.zykj.samplechat.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 徐学坤 on 2018/3/19.
 * Description 接口
 */
public class HttpUtils {

    private static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    protected static CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    private static Scheduler sc1 = Schedulers.io();
    private static Scheduler sc2 = AndroidSchedulers.mainThread();
    public static String STR = "6LKsZfPi1U76lxgg";
    /**执行请求*/
    public static void addSubscription(Subscription s) {
        mCompositeSubscription.add(s);
    }

    /**注册*/
    public static void Register(Subscriber<BaseEntityRes<String>> callback, String date1,String date2,String date3,String date4){
        addSubscription(Net.getServices().Register(date1,date2,date3,date4,getMD5(STR+"mobile"+date1+"password"+date2+"tjusercode"+date3+"nickname"+date4)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**登录*/
    public static void login(Subscriber<BaseEntityRes<User>> callback, String  data,String data1){
        addSubscription(Net.getServices().login(data,data1,getMD5(STR+"mobile"+data+"password"+data1)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**用户信息*/
    public static void UserInfo(Subscriber<BaseEntityRes<User>> callback){
        addSubscription(Net.getService().UserInfo(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**修改用户信息*/
    public static void UpdateUserInfo (Subscriber<BaseEntityRes<String>> callback, int data,String data1,String data2){
        if(data!=-1){
            addSubscription(Net.getService().UpdateUserInfo1(data,getMD5(STR+"sex"+data)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }
        if(!StringUtil.isEmpty(data1)){
            addSubscription(Net.getService().UpdateUserInfo2(data1,getMD5(STR+"nickname"+data1)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }
        if(!StringUtil.isEmpty(data2)){
            addSubscription(Net.getService().UpdateUserInfo3(data2,getMD5(STR+"realname"+data2)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }
    }

    /**修改支付密码*/
    public static void EditPayPassword(Subscriber<BaseEntityRes<String>> callback,String oldpaypass,String newpaypass){
        addSubscription(Net.getService().EditPayPassword(oldpaypass,newpaypass,getMD5(STR+"oldpaypass"+oldpaypass+"newpaypass"+newpaypass)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**我的下线*/
    public static void MyLine(Subscriber<BaseEntityRes<MyxiaxianxinxBean>> callback, int page, String size,int type){
        if(type==0){
            addSubscription(Net.getService().MyOneLine(page,size,getMD5(STR+"page"+page+"size"+size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }else if (type==1){
            addSubscription(Net.getService().MyTwoLine(page,size,getMD5(STR+"page"+page+"size"+size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }else if (type==2){
            addSubscription(Net.getService().ThreeLine(page,size,getMD5(STR+"page"+page+"size"+size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }else if (type==3){
            addSubscription(Net.getService().FourLine(page,size,getMD5(STR+"page"+page+"size"+size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
        }
    }

    /**申请好友*/
    public static void ApplyFriend(Subscriber<BaseEntityRes<String>> callback, String friendid) {
        addSubscription(Net.getService().ApplyFriend(friendid, getMD5(STR + "friendid" + friendid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**申请好友列表*/
    public static void GetApplyList(Subscriber<BaseEntityRes<sheqhyy>> callback, int page, String size){
        addSubscription(Net.getService().GetApplyList(page, size, getMD5(STR + "page" + page + "size" + size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**客服列表*/
    public static void ManagerList(Subscriber<BaseEntityRes<ArrayList<kflb>>> callback){
        addSubscription(Net.getService().ManagerList(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**好友列表*/
    public static void FriendList(Subscriber<BaseEntityRes<ArrayList<MyHYLB>>> callback){
        addSubscription(Net.getService().FriendList(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**好友列表*/
    public static void FriendLists(Subscriber<BaseEntityRes<ArrayList<Friend>>> callback){
        addSubscription(Net.getService().FriendLists(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**删除好友*/
    public static void DeleteFriend(Subscriber<BaseEntityRes<String>> callback, int friendid) {
        addSubscription(Net.getService().DeleteFriend(friendid, getMD5(STR + "friendid" + friendid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**同意拒绝好友申请*/
    public static void DealApplyFriend(Subscriber<BaseEntityRes<String>> callback, String friendid, int type) {
        addSubscription(Net.getService().DealApplyFriend(friendid,type, getMD5(STR + "friendid" + friendid+ "type" + type)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**根据Id获取好友详情*/
    public static void SearchFriendById(Subscriber<BaseEntityRes<sshyBean>> callback, String friendid) {
        addSubscription(Net.getService().SearchFriendById(friendid, getMD5(STR + "friendid" + friendid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**系统公告*/
    public static void SystemNews(Subscriber<BaseEntityRes<TongZhiBean>> callback) {
        addSubscription(Net.getService().SystemNews(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**版本更新*/
    public static void checkNew(Subscriber<BaseEntityRes<CheckNewBean>> callback) {
        addSubscription(Net.getService().checkNew(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**转账*/
    public static void Transfer(Subscriber<BaseEntityRes<String>> callback, String touserid, String money) {
        addSubscription(Net.getService().Transfer(touserid,money,getMD5(STR + "touserid" + touserid+ "money" + money)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**分红*/
    public static void GetFenHong(Subscriber<BaseEntityRes<MyshouyiBean>> callback) {
        addSubscription(Net.getService().GetFenHong(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**分红收益记录*/
    public static void GetFenHongList(Subscriber<BaseEntityRes<mYshouyi>> callback, int page, String size){
        addSubscription(Net.getService().GetFenHongList(page, size, getMD5(STR + "page" + page + "size" + size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**绑定支付宝*/
    public static void BindAliPay(Subscriber<BaseEntityRes<String>> callback, String realname, String alicode) {
        addSubscription(Net.getService().BindAliPay(realname,alicode,getMD5(STR + "realname" + realname+ "alicode" + alicode)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**上传提现二维码*/
    public static void UploadQrcode(Subscriber<BaseEntityRes<UploadPhoto>> callback, String filename, String filestream){
        addSubscription(Net.getService().UploadQrcode(filename, filestream, getMD5(STR + "filename" + filename + "filestream" + filestream)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**转账记录*/
    public static void GetTransferList(Subscriber<BaseEntityRes<ZhunZhangBean>> callback, int page, String size){
        addSubscription(Net.getService().GetTransferList(page, size, getMD5(STR + "page" + page + "size" + size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**提现记录*/
    public static void GetCashList(Subscriber<BaseEntityRes<TiXianBean>> callback, int page, String size){
        addSubscription(Net.getService().GetCashList(page, size, getMD5(STR + "page" + page + "size" + size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**财务类型*/
    public static void GUserFinList(Subscriber<BaseEntityRes<ArrayList<tj>>> callback,int page, String size){
        addSubscription(Net.getService().GUserFinList(page, size, getMD5(STR +"page" + page + "size" + size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**财务记录*/
    public static void GetUserFinList(Subscriber<BaseEntityRes<ZhunZhangBean>> callback, String type, int page, String size){
        addSubscription(Net.getService().GetUserFinList(type,page, size, getMD5(STR + "type" + type+ "page" + page + "size" + size)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**轮播图列表*/
    public static void BannerList(Subscriber<BaseEntityRes<ArrayList<lbt>>> callback){
        addSubscription(Net.getService().BannerList(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**获取扫雷红包群列表*/
    public static void GetSaoLeiGroupList(Subscriber<BaseEntityRes<ArrayList<mqunlb>>> callback){
        addSubscription(Net.getService().GetSaoLeiGroupList(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**普通群列表*/
    public static void NormalTeamList(Subscriber<BaseEntityRes<ArrayList<myql>>> callback){
        addSubscription(Net.getService().NormalTeamList(getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**修改登录密码*/
    public static void ChangeLoginPass(Subscriber<BaseEntityRes<String>> callback, String oldpass, String newpass) {
        addSubscription(Net.getService().ChangeLoginPass(oldpass,newpass,getMD5(STR + "oldpass" + oldpass+ "newpass" + newpass)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**会员新建群聊*/
    public static void UserCreateTeam(Subscriber<BaseEntityRes<String>> callback, String frienid) {
        addSubscription(Net.getService().UserCreateTeam(frienid,getMD5(STR + "frienid" + frienid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**微信登录*/
    public static void LoginByOtherWithInfor(Subscriber<BaseEntityRes<User>> callback, String openid, String nicname, String photopath) {
        addSubscription(Net.getServices().LoginByOtherWithInfor(openid,nicname,photopath,getMD5(STR + "openid" + openid+ "nicname" + nicname+ "photopath" + photopath)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**支付宝支付*/
    public static void getAliPayOrderInfo(Subscriber<BaseEntityRes<HotZhiFuBaoBean>> callback, String totalamount, String teamid, String lei) {
        addSubscription(Net.getService().getAliPayOrderInfo(totalamount,teamid,lei,getMD5(STR + "totalamount" + totalamount+ "teamid" + teamid+ "lei" + lei)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

//    /**发扫雷红包*/
//    public static void SendManyRandomPacket(Subscriber<BaseEntityRes<Hongbao>> callback, String body, String subject, String amount) {
//        addSubscription(Net.getService().SendManyRandomPacket(body,subject,amount,getMD5(STR + "body" + body+ "subject" + subject+ "amount" + amount)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
//    }

    /**获取群成员*/
    public static void SelectTeamMember(Subscriber<BaseEntityRes<ArrayList<Friend>>> callback, String teamid){
        addSubscription(Net.getService().SelectTeamMember(teamid,getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**获取群成员*/
    public static void SelectTeamMember1(Subscriber<BaseEntityRes<qunxxx>> callback, String teamid){
        addSubscription(Net.getService().SelectTeamMember1(teamid,getMD5(STR + "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**获取群成员*/
    public static void SelectTeamMemberForAnZhuo(Subscriber<BaseEntityRes<qunxxx>> callback, String teamid){
        addSubscription(Net.getService().SelectTeamMemberForAnZhuo(teamid,getMD5(STR + "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**邀请*/
    public static void YaoQing(Subscriber<BaseEntityRes<String>> callback, String idlist, String teamid) {
        addSubscription(Net.getService().YaoQing(idlist,teamid,getMD5(STR + "idlist" + idlist+ "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**邀请*/
    public static void AddTJUserCode(Subscriber<BaseEntityRes<String>> callback, String tjusercode) {
        addSubscription(Net.getService().AddTJUserCode(tjusercode,getMD5(STR + "tjusercode" + tjusercode)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**踢除*/
    public static void TiChu(Subscriber<BaseEntityRes<String>> callback, String idlist, String teamid) {
        addSubscription(Net.getService().TiChu(idlist,teamid,getMD5(STR + "idlist" + idlist+ "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**发送雷包*/
    public static void GCountAndOddsByTeamId(Subscriber<BaseEntityRes<ArrayList<LeiBean>>> callback, String teamid) {
        addSubscription(Net.getService().GCountAndOddsByTeamId(teamid,getMD5(STR + "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**获取群信息*/
    public static void GetTeam(Subscriber<BaseEntityRes<TeamBean>> callback, String code, String type) {
        addSubscription(Net.getService().GetTeam(code,type,getMD5(STR + "code" + code+"type"+type)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**发送雷包*/
    public static void SendManyRandomPacket(Subscriber<BaseEntityRes<Hongbao>> callback, String totalamount,String countid, String teamid, String lei) {
        addSubscription(Net.getService().SendManyRandomPacket(totalamount,countid,teamid,lei,getMD5(STR + "totalamount" + totalamount+ "countid" +countid+ "teamid" + teamid+ "lei" + lei)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }


    /**抢红包*/
    public static void GRedPackage(Subscriber<BaseEntityRes<ChaiHongbao>> callback, String teamid, String packageid, String randomnum) {
        addSubscription(Net.getService().GRedPackage(teamid,packageid,randomnum,getMD5(STR + "teamid" + teamid+ "packageid" + packageid+ "randomnum" + randomnum)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**是否管理员*/
    public static void GTeamUserInfo (Subscriber<BaseEntityRes<qunxxx.QueryBean>> callback, String teamid) {
        addSubscription(Net.getService().GTeamUserInfo(teamid,getMD5(STR + "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }


    /**红包详情*/
    public static void GRedPackageDetail(Subscriber<BaseEntityRes<HongbaoList>> callback, String packid) {
        addSubscription(Net.getService().GRedPackageDetail(packid,getMD5(STR + "packid" + packid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**红包是否可领*/
    public static void IsOpenPackage(Subscriber<BaseEntityRes<Xiangyu>> callback, String packid) {
        addSubscription(Net.getService().IsOpenPackage(packid,getMD5(STR + "packid" + packid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**修改头像*/
    public static void UpLoadPhoto(Subscriber<BaseEntityRes<UploadPhoto>> callback, String filename, String filestream) {
        addSubscription(Net.getService().UpLoadPhoto(filename,filestream,getMD5(STR + "filename" + filename+ "filestream" + filestream)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**是否在群里*/
    public static void IsInTeam(Subscriber<BaseEntityRes<FriendMap>> callback, String teamid) {
        addSubscription(Net.getService().IsInTeam(teamid,getMD5(STR + "teamid" + teamid)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**上传头像*/
    public static void UploadImgByFile(Subscriber<BaseEntityRes<UploadPhoto>> callback, RequestBody filename){
        addSubscription(Net.getService().UploadImgByFile(filename, getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**上传提现二维码*/
    public static void UploadQrCodeByFile(Subscriber<BaseEntityRes<UploadPhoto>> callback, RequestBody filename){
        addSubscription(Net.getService().UploadQrCodeByFile(filename, getMD5(STR)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**修改头像*/
    public static void ChangeHeadUrl(Subscriber<BaseEntityRes<String>> callback, String headurl) {
        addSubscription(Net.getService().ChangeHeadUrl(headurl,getMD5(STR + "headurl" + headurl)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**提现*/
    public static void CreateCash(Subscriber<BaseEntityRes<String>> callback, int type, String amount, String qrcode) {
        addSubscription(Net.getService().CreateCash(type,amount,qrcode,getMD5(STR + "type" + type+ "amount" + amount+ "qrcode" + qrcode)).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**App版本号*/
    public static void AppVersion(Subscriber<BaseEntityRes<VersonBean>> callback) {
        addSubscription(Net.getService().AppVersion(getMD5(STR+"")).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }

    /**忘记密码*/
    public static void Forget(Subscriber<BaseEntityRes<Object>> callback,String mobile, String password) {
        addSubscription(Net.getServices().Forget(mobile,password,getMD5(STR+ "mobile" + mobile+ "password" + password )).subscribeOn(sc1).observeOn(sc2).subscribe(callback));
    }








    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1)
                {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                }
                else
                {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            Log.e("TAG","密文=" +strBuf.toString());
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}