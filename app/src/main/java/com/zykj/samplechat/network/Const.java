package com.zykj.samplechat.network;

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
 * public static final String BASE = "http://122.114.222.153:8015/";
 * <p>
 * 佛祖保佑         永无BUG              *
 * *
 * *
 * *****************************************************
 * <p>
 *
 * Created by ninos on 2016/6/2.
 */
public class Const {
    /**
     * URL
//     */
//    public static final String BASE = "http://39.100.240.185";
    public static final String BASE = "http://39.100.197.70";

    //  TUIKitConfigs   BASE 修改图片路径

    public static  final  int sdkAppID=1400275636;
//    public static  final  int sdkAppID=1400258256;


    public static final String REGISTER = "api/User/Register";
    public static final String LOGIN = "api/User/Login";
    public static final String USERINFO = "api/User/UserInfo";
    public static final String UPDATEUSERINFO = "api/User/UpdateUserInfo";
    public static final String EDITPAYPASSWORD = "api/User/EditPayPassword";
    public static final String MYONELINE = "api/User/MyOneLine";
    public static final String MYTWOLINE = "api/User/MyTwoLine";
    public static final String ThreeLine = "api/User/MyThirdLine";
    public static final String FourLine = "api/User/MyOtherLine";
    public static final String APPLYFRIEND = "api/User/ApplyFriend";
    public static final String GETAPPLYLIST = "api/User/GApplyList";
    public static final String MANAGERLIST = "api/User/ManagerList";
    public static final String FRIENDLIST = "api/User/FriendList";
    public static final String DELETEFRIEND = "api/User/DelFriend";
    public static final String DEALAPPLYFRIEND = "api/User/DealApplyFriend";
    public static final String SEARCHFRIENDBYID = "api/User/SearchFriendById";
    public static final String CHECKNEW = "api/User/GVersion";
    public static final String SYSTEMNEWS = "api/User/SystemNews";
    public static final String TRANSFER = "api/User/Transfer";
    public static final String GETFENHONG = "api/User/GFenHong";
    public static final String GETFENHONGLIST = "api/User/GFenHongList";
    public static final String BINDALIPAY = "api/User/BindAliPay";
    public static final String UPLOADQRCODE = "api/User/UploadQrcode";
    public static final String GETTRANSFERLIST = "api/User/GTransferList";
    public static final String GETCASHLIST = "api/User/GCashList";
    public static final String FINTYPELIST = "api/User/FinTypeList";
    public static final String GETUSERFINLIST = "api/User/GUserFinList";
    public static final String BANNERLIST = "api/User/BannerList";
    public static final String CHANGELOGINPASS = "api/User/ChangeLoginPass";
    public static final String LOGINBYOTHERWITHINFOR = "api/User/LoginByOtherWithInfor";
    public static final String GETALIPAYORDERINFO = "api/User/gAliPayOrderInfo";
    public static final String SENDMANYRANDOMPACKET = "api/RedPackage/SendManyRandomPacket";
    public static final String GETSAOLEIGROUPLIST = "api/Team/GSaoLeiGroupList";
    public static final String NORMALTEAMLIST = "api/Team/NormalTeamList";
    public static final String USERCREATETEAM = "api/Team/UserCreateTeam";
    public static final String SELECTTEAMMEMBER = "api/Team/SelectTeamMember";
    public static final String SELECTTEAMMEMBERFORANZHUO = "api/Team/SelectTeamMemberForAnZhuo";
    public static final String GCOUNTANDODDSBYTEAMID = "api/Team/GCountAndOddsByTeamId";
    public static final String GETTEAM = "api/Team/TeamIdByTeamCode";
    public static final String YAOQING = "api/Team/YaoQing";
    public static final String TICHU = "api/Team/TiChu";
    public static final String GREDPACKAGE = "api/RedPackage/GRedPackage";
    public static final String GTEAMUSERINFO = "api/Team/GTeamUserInfo";
    public static final String GREDPACKAGEDETAIL = "api/RedPackage/GRedPackageDetail";
    public static final String ISOPENPACKAGE = "api/RedPackage/IsOpenPackage";
    public static final String ADDTJUSERCODE = "api/User/AddTJUserCode";
    public static final String UPLOADPHOTO = "api/User/UpLoadPhoto";
    public static final String ISINTEAM = "api/Team/IsInTeam";
    public static final String UPLOADIMGBYFILE = "api/User/UploadImgByFile";
    public static final String UPLOADQRCODEBYFILE = "api/User/UploadQrCodeByFile";
    public static final String CHANGEHEADURL = "api/User/ChangeHeadUrl";
    public static final String CREATECASH = "api/User/CreateCash";
    public static final String APPVERSION = "Api/User/GVersion";
    public static final String Forget = "Api/User/FindPassWord";



    public static final String TICHUQun = "/Api/Team/KickoutFromTeam";





    public static final String URL = "WebService/LoginAndRegister.asmx/Entry";
    public static final String URL2 = "WebService/UserService.asmx/Entry";
    public static final String FRIENDS2 = "WebService/TeamService.asmx/Entry";
    public static final String FRIENDS = "WebService/Friends.asmx/Entry";

    public static final String SYSTEMNEWS2 = "WebService/TeamService.asmx/Entry";
    public static final String SUGGESTION = "WebService/Suggestion.asmx/Entry";
    public static final String GETVERSON = "WebService/SystemNews.asmx/GetVerson";
    public static final String SUPPLIMENTAPI = "WebService/SupplimentAPI.asmx/Entry";
    public static final String SUPPLIMENTAPI2 = "WebService/RedPackageService.asmx/Entry";
    public static final String SUGGESTIONSENDYUE = "WebService/Suggestion.asmx/Entry";
    public static final String BAIDU = "http://api.map.baidu.com/staticimage";

    public static final String KEY = "fd15f548-7559-4d40-80a1-f00ca9bfcc02";
    public static final String UID = "48a5f357-484f-4847-97b7-a44a2e73e8d5";
    public static final String AESKEY = "87ad86494a409adf";
    public static final String AESIV = "0402584c317e4286";

    public static final String LISTYUE = "ListYuE.asp?UserID=%d&teamid=%s";

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_FOOTER = -1;
    public static final int VIEW_TYPE_NORMAL = 1;

    public static final int OK = 200;

    public static String getbase(String path) {
        if (path != null) {
            return path.startsWith("http") ? path : BASE + path;
        } else {
            return "";
        }
    }
}
