package com.zykj.samplechat.ui.adapter;


import android.app.Activity;
import android.content.Context;

import com.zykj.samplechat.ui.itemdelegate.MyqlDelegate;
import com.zykj.samplechat.ui.itemdelegate.MyqlbDelegate;
import com.zykj.samplechat.ui.itemdelegate.QiTaDelegate;
import com.zykj.samplechat.ui.itemdelegate.ShouYiDelegate;
import com.zykj.samplechat.ui.itemdelegate.TiXianDelegate;
import com.zykj.samplechat.ui.itemdelegate.XiaXianDelegate;
import com.zykj.samplechat.ui.itemdelegate.XuQiuDelegate;
import com.zykj.samplechat.ui.itemdelegate.ZhuanZhangDelegate;
import com.zykj.samplechat.ui.itemdelegate.sqhyDelegate;
import com.zykj.samplechat.ui.view.lbhd;

import cn.davidsu.library.SuperBaseAdapter;

/**
 * 公共的
 * Created by cxzheng on 2018/3/29.
 */

public class MainListAdapter extends SuperBaseAdapter {

    public static int VIEW_TYPE_1 = 1;

    public MainListAdapter(int x, Context content) {
        switch (x){
            case  1:
                addItemType(x, new ShouYiDelegate(content,null));//我收益
                break;
                case  2:
                addItemType(x, new TiXianDelegate(content,null));//提现记录
                break;
                case  3:
                addItemType(x, new ZhuanZhangDelegate(content,null));//转账记录
                break;
                case  4:
                addItemType(x, new XiaXianDelegate(content,null));//我的下线
                break;
                case  5:
                addItemType(x, new MyqlbDelegate(content,null));//首页-游戏-群列表
                break;
                case  6:
                addItemType(x, new MyqlDelegate(content,null));//首页-好友-我的群聊
                break;
                case  7:
                addItemType(x, new QiTaDelegate(content,null));//其他交易记录
                break;

        }
    }
        public MainListAdapter(int x, Context content, String cs, Activity act) {
            switch (x) {
                case 1://ZhaoRenFragment 找人信息
                   // addItemType(x, new XuQiuOKDelegate(content, cs));//我的-我的需求 OK界面
                    break;

            }
        }
    public MainListAdapter( Activity act,int x, Context content, lbhd callback) {
        switch (x){
            case  1://ZhaoRenFragment 找人信息
                addItemType(x, new XuQiuDelegate(content,callback));//我的-我的需求
                break;
            case  2:
                addItemType(x, new sqhyDelegate(act,content,callback));//首页-好友,新申请的好友列表
                break;

        }
    }



}
