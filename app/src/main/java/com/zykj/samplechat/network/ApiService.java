package com.zykj.samplechat.network;

import com.squareup.okhttp.RequestBody;
import com.zykj.samplechat.model.About;
import com.zykj.samplechat.model.ChaiHongbao;
import com.zykj.samplechat.model.CheckNewBean;
import com.zykj.samplechat.model.Friend;
import com.zykj.samplechat.model.FriendMap;
import com.zykj.samplechat.model.Gonggao;
import com.zykj.samplechat.model.Group;
import com.zykj.samplechat.model.Hongbao;
import com.zykj.samplechat.model.HongbaoList;
import com.zykj.samplechat.model.ImageTemp;
import com.zykj.samplechat.model.MultiRes;
import com.zykj.samplechat.model.MyHYLB;
import com.zykj.samplechat.model.MyshouyiBean;
import com.zykj.samplechat.model.MyxiaxianxinxBean;
import com.zykj.samplechat.model.Picture;
import com.zykj.samplechat.model.Res;
import com.zykj.samplechat.model.SentRecords;
import com.zykj.samplechat.model.Team;
import com.zykj.samplechat.model.TeamBean;
import com.zykj.samplechat.model.TiXianBean;
import com.zykj.samplechat.model.TongZhiBean;
import com.zykj.samplechat.model.UploadPhoto;
import com.zykj.samplechat.model.User;
import com.zykj.samplechat.model.VersonBean;
import com.zykj.samplechat.model.WalletList;
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
import com.zykj.samplechat.presenter.WanJiaListBean;
import com.zykj.samplechat.presenter.WanJiasBean;
import com.zykj.samplechat.presenter.base.HotZhiFuBaoBean;

import java.util.ArrayList;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;


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
public interface ApiService {
    //注册
    @POST(Const.REGISTER)
    Observable<BaseEntityRes<String>> Register(@Query("mobile") String data,@Query("password") String data1,@Query("tjusercode") String data2,@Query("nickname") String data3,@Query("signature") String str);

    //登录
    @POST(Const.LOGIN)
    Observable<BaseEntityRes<User>> login(@Query("mobile") String data, @Query("password") String data1,@Query("signature") String str);

    //用户信息
    @POST(Const.USERINFO)
    Observable<BaseEntityRes<User>> UserInfo(@Query("signature") String str);

    //修改用户信息
    @POST(Const.UPDATEUSERINFO)
    Observable<BaseEntityRes<String>> UpdateUserInfo1(@Query("sex") int data,@Query("signature") String str);

    //修改用户信息
    @POST(Const.UPDATEUSERINFO)
    Observable<BaseEntityRes<String>> UpdateUserInfo2(@Query("nickname") String data1, @Query("signature") String str);

    //修改用户信息
    @POST(Const.UPDATEUSERINFO)
    Observable<BaseEntityRes<String>> UpdateUserInfo3(@Query("realname") String data2,@Query("signature") String str);

    //修改支付密码
    @POST(Const.EDITPAYPASSWORD)
    Observable<BaseEntityRes<String>> EditPayPassword (@Query("oldpaypass") String oldpaypass,@Query("newpaypass") String newpaypass,@Query("signature") String str);

    //我的下级
    @POST(Const.MYONELINE)
    Observable<BaseEntityRes<MyxiaxianxinxBean>> MyOneLine(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //我的下线2级
    @POST(Const.MYTWOLINE)
    Observable<BaseEntityRes<MyxiaxianxinxBean>> MyTwoLine(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //我的下线3级
    @POST(Const.ThreeLine)
    Observable<BaseEntityRes<MyxiaxianxinxBean>> ThreeLine(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //我的下线其他
    @POST(Const.FourLine)
    Observable<BaseEntityRes<MyxiaxianxinxBean>> FourLine(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //申请好友
    @POST(Const.APPLYFRIEND)
    Observable<BaseEntityRes<String>> ApplyFriend(@Query("friendid") String friendid,@Query("signature") String str);

    //好友申请列表
    @POST(Const.GETAPPLYLIST)
    Observable<BaseEntityRes<sheqhyy>> GetApplyList(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //客服列表
    @POST(Const.MANAGERLIST)
    Observable<BaseEntityRes<ArrayList<kflb>>> ManagerList(@Query("signature") String str);

    //好友列表
    @POST(Const.FRIENDLIST)
    Observable<BaseEntityRes<ArrayList<MyHYLB>>> FriendList(@Query("signature") String str);

    //好友列表
    @POST(Const.FRIENDLIST)
    Observable<BaseEntityRes<ArrayList<Friend>>> FriendLists(@Query("signature") String str);

    //删除好友
    @POST(Const.DELETEFRIEND)
    Observable<BaseEntityRes<String>> DeleteFriend(@Query("friendid") int friendid,@Query("signature") String str);

    //同意或拒绝好友申请
    @POST(Const.DEALAPPLYFRIEND)
    Observable<BaseEntityRes<String>> DealApplyFriend(@Query("friendid") String friendid,@Query("type") int type,@Query("signature") String str);

    //根据ID获取好友详情
    @POST(Const.SEARCHFRIENDBYID)
    Observable<BaseEntityRes<sshyBean>> SearchFriendById(@Query("friendid") String friendid,@Query("signature") String str);

    //系统公告
    @POST(Const.CHECKNEW)
    Observable<BaseEntityRes<CheckNewBean>> checkNew(@Query("signature") String str);

    //版本更新
    @POST(Const.SYSTEMNEWS)
    Observable<BaseEntityRes<TongZhiBean>> SystemNews(@Query("signature") String str);

    //转账
    @POST(Const.TRANSFER)
    Observable<BaseEntityRes<String>> Transfer(@Query("touserid") String touserid,@Query("money") String money,@Query("signature")String str);

    //分红
    @POST(Const.GETFENHONG)
    Observable<BaseEntityRes<MyshouyiBean>> GetFenHong(@Query("signature") String str);

    //分红收益记录
    @POST(Const.GETFENHONGLIST)
    Observable<BaseEntityRes<mYshouyi>> GetFenHongList(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //绑定支付宝
    @POST(Const.BINDALIPAY)
    Observable<BaseEntityRes<String>> BindAliPay(@Query("realname") String realname,@Query("alicode") String alicode,@Query("signature")String str);

    //上传提现二维码
    @POST(Const.UPLOADQRCODE)
    Observable<BaseEntityRes<UploadPhoto>> UploadQrcode(@Query("filename") String filename, @Query("filestream") String filestream, @Query("signature") String str);

    //转账记录
    @POST(Const.GETTRANSFERLIST)
    Observable<BaseEntityRes<ZhunZhangBean>> GetTransferList(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //提现记录
    @POST(Const.GETCASHLIST)
    Observable<BaseEntityRes<TiXianBean>> GetCashList(@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //财务类型
    @POST(Const.FINTYPELIST)
    Observable<BaseEntityRes<ArrayList<tj>>> GUserFinList(@Query("page") int page, @Query("size") String size,@Query("signature") String str);

    //财务记录
    @POST(Const.GETUSERFINLIST)
    Observable<BaseEntityRes<ZhunZhangBean>> GetUserFinList( @Query("type") String type,@Query("page") int page, @Query("size") String size, @Query("signature") String str);

    //轮播图列表
    @POST(Const.BANNERLIST)
    Observable<BaseEntityRes<ArrayList<lbt>>> BannerList(@Query("signature") String str);

    //获取扫雷红包群列表
    @POST(Const.GETSAOLEIGROUPLIST)
    Observable<BaseEntityRes<ArrayList<mqunlb>>> GetSaoLeiGroupList(@Query("signature") String str);

    //普通群列表
    @POST(Const.NORMALTEAMLIST)
    Observable<BaseEntityRes<ArrayList<myql>>> NormalTeamList(@Query("signature") String str);

    //修改登录密码
    @POST(Const.CHANGELOGINPASS)
    Observable<BaseEntityRes<String>> ChangeLoginPass(@Query("oldpass") String oldpass,@Query("newpass") String newpass,@Query("signature") String str);

    //会员新建群聊
    @POST(Const.USERCREATETEAM)
    Observable<BaseEntityRes<String>> UserCreateTeam(@Query("frienid") String frienid,@Query("signature") String str);

    //微信登录
    @POST(Const.LOGINBYOTHERWITHINFOR)
    Observable<BaseEntityRes<User>> LoginByOtherWithInfor(@Query("openid") String openid,@Query("nicname") String nicname,@Query("photopath") String photopath,@Query("signature") String str);

    //支付宝支付
    @POST(Const.GETALIPAYORDERINFO)
    Observable<BaseEntityRes<HotZhiFuBaoBean>> getAliPayOrderInfo(@Query("body") String body,@Query("subject") String subject,@Query("amount") String amount,@Query("signature") String str);

//    //发扫雷红包
//    @POST(Const.SENDMANYRANDOMPACKET)
//    Observable<BaseEntityRes<Hongbao>> SendManyRandomPacket(@Query("totalamount") String totalamount,@Query("teamid") String teamid,@Query("lei") String lei,@Query("signature") String str);

    //获取群成员
    @POST(Const.SELECTTEAMMEMBER)
    Observable<BaseEntityRes<ArrayList<Friend>>> SelectTeamMember(@Query("teamid") String teamid,@Query("signature") String str);

    //获取群成员
    @POST(Const.SELECTTEAMMEMBER)
    Observable<BaseEntityRes<qunxxx>> SelectTeamMember1(@Query("teamid") String teamid, @Query("signature") String str);

    //获取群成员
    @POST(Const.SELECTTEAMMEMBERFORANZHUO)
    Observable<BaseEntityRes<qunxxx>> SelectTeamMemberForAnZhuo(@Query("teamid") String teamid, @Query("signature") String str);

    //填写邀请码
    @POST(Const.ADDTJUSERCODE)
    Observable<BaseEntityRes<String>> AddTJUserCode(@Query("tjusercode") String tjusercode, @Query("signature") String str);

    //踢除
    @POST(Const.TICHU)
    Observable<BaseEntityRes<String>> TiChu(@Query("idlist") String idlist,@Query("teamid") String teamid, @Query("signature") String str);

    //邀请
    @POST(Const.YAOQING)
    Observable<BaseEntityRes<String>> YaoQing(@Query("idlist") String idlist,@Query("teamid") String teamid, @Query("signature") String str);

    //发送雷包
    @POST(Const.GCOUNTANDODDSBYTEAMID)
    Observable<BaseEntityRes<ArrayList<LeiBean>>> GCountAndOddsByTeamId(@Query("teamid") String teamid, @Query("signature") String str);

    //获取群信息
    @POST(Const.GETTEAM)
    Observable<BaseEntityRes<TeamBean>> GetTeam(@Query("code") String code, @Query("type") String type, @Query("signature") String str);

    //发送雷包
    @POST(Const.SENDMANYRANDOMPACKET)
    Observable<BaseEntityRes<Hongbao>> SendManyRandomPacket(@Query("totalamount") String totalamount,@Query("countid") String countid,@Query("teamid") String teamid,@Query("lei") String lei, @Query("signature") String str);

    //抢红包
    @POST(Const.GREDPACKAGE)
    Observable<BaseEntityRes<ChaiHongbao>> GRedPackage(@Query("teamid") String teamid,@Query("packageid") String packageid,@Query("randomnum") String randomnum, @Query("signature") String str);

    //是否管理员
    @POST(Const.GTEAMUSERINFO)
    Observable<BaseEntityRes<qunxxx.QueryBean>> GTeamUserInfo(@Query("teamid") String teamid,@Query("signature") String str);

    //红包详情
    @POST(Const.GREDPACKAGEDETAIL)
    Observable<BaseEntityRes<HongbaoList>> GRedPackageDetail(@Query("packid") String packid,@Query("signature") String str);

    //红包是否可领
    @POST(Const.ISOPENPACKAGE)
    Observable<BaseEntityRes<Xiangyu>> IsOpenPackage(@Query("packid") String packid,@Query("signature") String str);

    //修改头像
    @POST(Const.UPLOADPHOTO)
    Observable<BaseEntityRes<UploadPhoto>> UpLoadPhoto(@Query("filename") String filename,@Query("filestream") String filestream,@Query("signature") String str);

    //是否在群里
    @POST(Const.ISINTEAM)
    Observable<BaseEntityRes<FriendMap>> IsInTeam(@Query("teamid") String teamid,@Query("signature") String str);

    //修改头像
    @POST(Const.UPLOADIMGBYFILE)
    Observable<BaseEntityRes<UploadPhoto>> UploadImgByFile(@Body() RequestBody filename, @Query("signature") String str);

    //上传提现二维码
    @POST(Const.UPLOADQRCODEBYFILE)
    Observable<BaseEntityRes<UploadPhoto>> UploadQrCodeByFile(@Body() RequestBody filename, @Query("signature") String str);

    //修改头像
    @POST(Const.CHANGEHEADURL)
    Observable<BaseEntityRes<String>> ChangeHeadUrl(@Query("headurl") String headurl,@Query("signature") String str);

    //提现
    @POST(Const.CREATECASH)
    Observable<BaseEntityRes<String>> CreateCash(@Query("type") int type,@Query("amount") String amount,@Query("qrcode") String qrcode,@Query("signature") String str);

    @POST(Const.Forget)
    Observable<BaseEntityRes<Object>> Forget(@Query("mobile") String mobile,@Query("password") String password,@Query("signature") String str);


    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> ChangeInfor(@Field("args") String data);
    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> Login(@Field("args") String data);
    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> Logins(@Field("args") String data);
    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> yaoqing(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> ChangePassWordByPhone(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res<User>> LoginWithoutPassword(@Field("args") String data);
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res<WanJiasBean>> WanJia(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res<UploadPhoto>> UpLoadPhoto(@Field("args") String data);
    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res<UploadPhoto>> UpLoadPayCode(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ArrayList<Friend>>> ContactsMatch(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res<sshyBean>> SearchFriendByPhone(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res<ArrayList<FriendMap>>> GetFriendForKeyValue(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res<Friend>> SelectMyFriend(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res<sshyBean>> GetUserInfor(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res<Object>> getRecommendCode(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ArrayList<FriendMap>>> SelectApplyList(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> AgreeFriendAndSaveFriend(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res> SaveFriend(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> DelDongtai(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ArrayList<Team>>> GetTeamList(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ArrayList<Group>>> GetDongtaiPageByFriends(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> MessageRepeatAndFavorite(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> MessageRepeatAndFavoriteCancel(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> MessageComment(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> CommentComment(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> SaveDongtai(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ImageTemp>> UpLoadImage(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> UpLoadVideo(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> DeleteFriend(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> SaveSpaceImage(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> EditTeam(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> ReportUser(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> DeleteComment(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> YaoQing(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> DismissTeam(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res> UpdateSuggestion(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res> TiChu(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res> SendYue(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res> SendLiuShui(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> ChangeFriendRName(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<Team>> BuildTeam(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS2)
    Observable<Res<Team>> SelectTeam(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ArrayList<Team>>> GetAllGonggao(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res<About>> SelectAboutUs(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<Group>> GetDongtaiById(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<ArrayList<Team>>> SelectAllTeamByUserId(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<MultiRes<ArrayList<Friend>>> SelectTeamMember(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SYSTEMNEWS2)
    Observable<Res<ArrayList<Gonggao>>> SelectGongGaoList(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SYSTEMNEWS)
    Observable<Res<Picture>> GetPicture(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res<String>> permission(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.URL)
    Observable<Res> PushRegister(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<String>> IsWuyou(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.FRIENDS)
    Observable<Res<FriendMap>> IsInTeam(@Field("args") String data);

    @FormUrlEncoded
    @POST(Const.SYSTEMNEWS)
    Observable<Res> SaveMessage(@Field("args") String data);

    //一对一
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<Hongbao>> SendSingleRedPacket(@Field("args") String data);    //一对一

    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<String>> Sendxx(@Field("args") String data);

    //一对一
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI2)
    Observable<Res<Xiangyu>> IsOpenPacket(@Field("args") String data);
    //一对一
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<Xiangyu>> IsOpenPacketj(@Field("args") String data);

    //群固定
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<Hongbao>> SendManyOrdinaryPacket(@Field("args") String data);

    //群随机
    @FormUrlEncoded
   // @POST(Const.SUPPLIMENTAPI)
    @POST(Const.SUGGESTION)
    Observable<Res> SendManyRandomPacket(@Field("args") String data);
    //群中雷
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res> SendManyRandomPacket2(@Field("args") String data);
  //查余额
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res> getyue(@Field("args") String data);

    //提现
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res> doWithdraw(@Field("args") String data);
  //获取充值结构
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res> getce(@Field("args") String data);
    //获取充值支付宝
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res<HotZhiFuBaoBean>> getzfb(@Field("args") String data);
    //获取转账
    @FormUrlEncoded
    @POST(Const.URL2)
    Observable<Res> getzhuanzhang(@Field("args") String data);
    //充值记录
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res<ArrayList<SentRecords>>>  getchongzhi(@Field("args") String data);

    //我的玩家
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI)
    Observable<Res<ArrayList<WanJiaListBean>>>  getPlayerList(@Field("args") String data);

    //拆一对一
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<ChaiHongbao>> OpenSingleRedPacket(@Field("args") String data);
    //拆一对一
    @FormUrlEncoded
    @POST(Const.SUPPLIMENTAPI2)
    Observable<Res<ChaiHongbao>> OpenSingleRedPacket1(@Field("args") String data);

    //看手气
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<HongbaoList>> ViewPacketList(@Field("args") String data);

    //收包记录
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<ArrayList<WalletList>>> MyRecvicePacket(@Field("args") String data);

    //发包记录
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res<ArrayList<WalletList>>> MySendPacket(@Field("args") String data);

    //发包之后
    @FormUrlEncoded
    @POST(Const.SUGGESTION)
    Observable<Res> SelectIsLei(@Field("args") String data);

    @GET(Const.GETVERSON)
    Observable<Res<About>> getVersion();

    @FormUrlEncoded
    @POST(Const.TICHUQun)
    Observable<Res> TichuQun(@Field("args") String data);


    @POST(Const.APPVERSION)
    Observable<BaseEntityRes<VersonBean>> AppVersion(@Query("signature") String str);




}