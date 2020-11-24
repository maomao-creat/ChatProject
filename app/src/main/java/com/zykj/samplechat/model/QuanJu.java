package com.zykj.samplechat.model;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.qcloud.tim.uikit.modules.chat.base.ChatInfo;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.ui.activity.ChatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import zhangphil.iosdialog.widget.ActionSheetDialog;

/**
 * Created by ninos on 2016/7/27.
 * gokefu(Context c){//这是一个全局客服接口  这里可以找客服聊天
 * Boolean getlei(String fid) {  //是否走中雷红包
 */
public class QuanJu implements Serializable {//这个是全局的  单利的 存点东西

    public static final String CHAT_INFO = "chatInfo";

    private String tzxx;//这个是 每个首页的通知消息
    private List<mqunlb> mye;//这个是所有的 红包群列表信息


    private QuanJu() {
    }

    private static QuanJu quanJu = new QuanJu();

    public static QuanJu getQuanJu() {
        return quanJu;
    }

    public List<mqunlb> getMye() {
        return mye;
    }

    public void setMye(List<mqunlb> mye) {
        this.mye = mye;
    }

    public String getTzxx() {
        return tzxx;
    }

    public void setTzxx(String tzxx) {
        this.tzxx = tzxx;
    }


    public Boolean getlei(String fid) {  //是否走中雷红包
        List<mqunlb> aa = QuanJu.getQuanJu().getMye();
        for (mqunlb sad : aa) {//
            if (fid.equals(sad.getId())) {
                return true;
            }
        }
        return false;
    }


    public void gokefu(final Context c) {//这是一个全局客服接口  这里可以找客服聊天

        HttpUtils.ManagerList(new SubscriberRes<ArrayList<kflb>>() {
            @Override
            public void onSuccess(ArrayList<kflb> userBean) {
                if (userBean.size() == 1) {
                    ChatInfo chatInfo = new ChatInfo();
                    chatInfo.setType(TIMConversationType.C2C);
                    chatInfo.setId(userBean.get(0).UserCode);
                    chatInfo.setChatName(userBean.get(0).getNickName());
                    Intent intent = new Intent(c, ChatActivity.class);
                    intent.putExtra(CHAT_INFO, chatInfo);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(intent);


//                    RongIM.getInstance().startPrivateChat(c,""+userBean.get(0).getUserId(),""+userBean.get(0).getNickName());
//                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(userBean.get(0).getUserId(), ""+userBean.get(0).getNickName(), Uri.parse(""+NR.url+userBean.get(0).getHeadUrl())));
                } else {
                    tckf(c, userBean);
                }
            }

            @Override
            public void completeDialog() {
            }
        });

//        Map map = new HashMap();
//        map.put("function",""+"ManagerList");
//        map.put("userid",""+new UserUtil(c).getUserId2());
//        NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){
//            @Override
//            public void onError(Request request, Exception e) {
//                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(c, "服务器错误");  //吐司
//            }
//
//            @Override
//            public void onResponse(String s) {
//             //     LogUtils.i("xxxxx", "" +s);  //输出测试
//                if(NRUtils.getYse(c,s)) {
//                 //   IsZH.getToast(c, "成功");  //吐司
//                     List<kflb> mye = NRUtils.getDataList(s,kflb.class);
//                     if(mye.size()==1){
//                         RongIM.getInstance().startPrivateChat(c,""+mye.get(0).getUserId(),""+mye.get(0).getNickName());
//                         RongIM.getInstance().setCurrentUserInfo(new UserInfo(mye.get(0).getUserId(), ""+mye.get(0).getNickName(), Uri.parse(""+NR.url+mye.get(0).getHeadUrl())));
//                     }else{
//                         tckf(c,mye);
//                     }
//                }
//            }
//
//        });
    }

    private void tckf(final Context c, List<kflb> mye) {
        ActionSheetDialog asd = new ActionSheetDialog(c)
                .builder()
                .setCancelable(true)
                .setCanceledOnTouchOutside(true);
        for (final kflb sj : mye) {
            asd.addSheetItem("" + sj.getNickName(),
                    ActionSheetDialog.SheetItemColor.Blue,
                    new ActionSheetDialog.OnSheetItemClickListener() {
                        @Override
                        public void onClick(int which) {
                            Toast.makeText(c, which + "", Toast.LENGTH_SHORT);
                            ChatInfo chatInfo = new ChatInfo();
                            chatInfo.setType(TIMConversationType.C2C);
                            chatInfo.setId(sj.UserCode);
                            chatInfo.setChatName(sj.getNickName());
                            Intent intent = new Intent(c, ChatActivity.class);
                            intent.putExtra(CHAT_INFO, chatInfo);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            c.startActivity(intent);

                        }
                    });
        }
        asd.show();
    }
}
