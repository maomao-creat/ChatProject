package com.zykj.samplechat.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pgyersdk.update.PgyUpdateManager;
import com.tencent.qcloud.tim.uikit.modules.chat.GroupChatManagerKit;
import com.zykj.samplechat.R;
import com.zykj.samplechat.presenter.base.BasePresenterImp;
import com.zykj.samplechat.ui.activity.base.BaseActivity;
import com.zykj.samplechat.ui.fragment.ConversationFragment;
import com.zykj.samplechat.ui.fragment.FenXiangFragment;
import com.zykj.samplechat.ui.fragment.FriendsFragment;
import com.zykj.samplechat.ui.fragment.HzwFragment;
import com.zykj.samplechat.ui.fragment.IndexFragment;
import com.zykj.samplechat.ui.fragment.OwnFragment;
import com.zykj.samplechat.ui.widget.DragPointView;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.LinkedHashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

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
 * 佛祖保佑         永无BUG               *
 * *
 * *
 * *****************************************************
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class MainActivity extends BaseActivity  {

    private int r = Color.parseColor("#0186ed");        //选中颜色
    private int g = Color.parseColor("#9c9c9c");        //非中颜色
    private static final String TAG = "MainActivity";

    @Bind(R.id.fram_content)
    FrameLayout framContent;
    @Bind(R.id.seal_num)
    DragPointView mUnreadNumView;
    @Bind(R.id.menu_one)
    LinearLayout menuOne;
    @Bind(R.id.menu_two)
    RelativeLayout menuTwo;
    @Bind(R.id.menu_three)
    LinearLayout menuThree;
    @Bind(R.id.menu_four)
    LinearLayout menuFour;
    @Bind(R.id.menu_one_fenx)
    LinearLayout menuonefenx;

    /**
     * 图片
     */
    @Bind(R.id.menu_one_img)
    ImageView menuOneImg;
    @Bind(R.id.menu_two_img)
    ImageView menuTwoImg;
    @Bind(R.id.menu_three_img)
    ImageView menuThreeImg;
    @Bind(R.id.menu_four_img)
    ImageView menuFourImg;
    @Bind(R.id.menu_one_img_fenx)
    ImageView menuoneimgfenx;

    /**
     * 文字
     */
    @Bind(R.id.menu_one_text)
    TextView menuOneText;
    @Bind(R.id.menu_two_text)
    TextView menuTwoText;
    @Bind(R.id.menu_three_text)
    TextView menuThreeText;
    @Bind(R.id.menu_four_text)
    TextView menuFourText;
    @Bind(R.id.menu_one_text_fenx)
    TextView menuonetextfenx;


    private IndexFragment indexFragment;//聊天
    private ConversationFragment conversationFragment;//聊天
    private FriendsFragment friendsFragment;//通讯录
    private HzwFragment hzwFragment;//游戏
    private FenXiangFragment FenXiangfragment;//分享
    private OwnFragment ownFragment;//我的
    private Fragment curFragment;
    private Fragment mTab01;
    private Fragment mTab02;
    private Fragment mTab03;
    private Fragment mTab04;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void beforeSetCView(Bundle savedInstanceState) {
        super.beforeSetCView(savedInstanceState);
    }

    /**
     * 初始化事件监听者
     */
    @Override
    public void initListeners() {
        PgyUpdateManager.setIsForced(true); //设置是否强制更新。true为强制更新；消费凭证（默认值）。
        PgyUpdateManager.register(this);
    }

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        mHandler.sendMessage(mHandler.obtainMessage(1001, "alias_" + new UserUtil(getContext()).getUserId()));

//        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
//        localFragmentTransaction.replace(R.id.fram_content, hzwFragment, "tab_one");
//        localFragmentTransaction.commit();
//        curFragment = hzwFragment;
        CommonUtil.setWindowStatusBarColor(MainActivity.this, R.color.colorPrimary);

        GroupChatManagerKit.getInstance();

        onViewClicked(menuTwo);
    }

    @OnClick({R.id.menu_two, R.id.menu_three, R.id.menu_one, R.id.menu_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menu_two:
                setSelect(0);
                menuTwoText.setTextColor(r);
                menuOneText.setTextColor(g);
                menuThreeText.setTextColor(g);
                menuFourText.setTextColor(g);
                menuTwoImg.setImageResource(R.drawable.liaotian1);
                menuThreeImg.setImageResource(R.drawable.moyou);
                menuOneImg.setImageResource(R.drawable.youxi);
                menuFourImg.setImageResource(R.drawable.wode);
                break;
            case R.id.menu_three:
                setSelect(1);
                menuTwoText.setTextColor(g);
                menuOneText.setTextColor(g);
                menuThreeText.setTextColor(r);
                menuFourText.setTextColor(g);
                menuTwoImg.setImageResource(R.drawable.liaotian);
                menuThreeImg.setImageResource(R.drawable.moyouxz);
                menuOneImg.setImageResource(R.drawable.youxi);
                menuFourImg.setImageResource(R.drawable.wode);
                break;
            case R.id.menu_one:
                setSelect(2);
                menuTwoText.setTextColor(g);
                menuOneText.setTextColor(r);
                menuThreeText.setTextColor(g);
                menuFourText.setTextColor(g);
                menuTwoImg.setImageResource(R.drawable.liaotian);
                menuThreeImg.setImageResource(R.drawable.moyou);
                menuOneImg.setImageResource(R.drawable.youxi1);
                menuFourImg.setImageResource(R.drawable.wode);
                break;
            case R.id.menu_four:
                setSelect(3);
                menuTwoText.setTextColor(g);
                menuOneText.setTextColor(g);
                menuThreeText.setTextColor(g);
                menuFourText.setTextColor(r);
                menuTwoImg.setImageResource(R.drawable.liaotian);
                menuThreeImg.setImageResource(R.drawable.moyou);
                menuOneImg.setImageResource(R.drawable.youxi);
                menuFourImg.setImageResource(R.drawable.wode1);
                break;
        }
    }

    private void setSelect(int i) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragment(transaction);
        // 把图片设置为亮的
        // 设置内容区域
        switch (i) {
            case 0:
                if (mTab01 == null) {
                    mTab01 = new ConversationFragment();
                    transaction.add(R.id.fram_content, mTab01);
                } else {
                    transaction.show(mTab01);
                }
                break;
            case 1:
                if (mTab02 == null) {
                    mTab02 = new FriendsFragment();
                    transaction.add(R.id.fram_content, mTab02);
                } else {
                    transaction.show(mTab02);

                }
                break;
            case 2:
                if (mTab03 == null) {
                    mTab03 = new HzwFragment();
                    transaction.add(R.id.fram_content, mTab03);
                } else {
                    transaction.show(mTab03);
                }
                break;
            case 3:
                if (mTab04 == null) {
                    mTab04 = new OwnFragment();
                    transaction.add(R.id.fram_content, mTab04);
                } else {
                    transaction.show(mTab04);
                }
                break;
            default:
                break;
        }

        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (mTab01 != null) {
            transaction.hide(mTab01);
        }
        if (mTab02 != null) {
            transaction.hide(mTab02);
        }
        if (mTab03 != null) {
            transaction.hide(mTab03);
        }
        if (mTab04 != null) {
            transaction.hide(mTab04);
        }

    }


    /**
     * 绑定Presenter
     */
    @Override
    public BasePresenterImp createPresenter() {
        return null;
    }






    public void switchContent(Fragment from, Fragment to) {
        if (curFragment != to) {
            curFragment = to;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                transaction.hide(from).add(R.id.fram_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
            intent.addCategory(Intent.CATEGORY_HOME);
            this.startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }






    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1001:
                    Log.d(TAG, "Set alias in handler.");
                    Set<String> tagSet = new LinkedHashSet<>();
                    tagSet.add("Tags_1");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, tagSet, mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(1001, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            //toast(logs);
        }
    };

    public void rebackHome() {
        menuOneText.setTextColor(r);
        menuTwoText.setTextColor(g);
        menuThreeText.setTextColor(g);
        menuFourText.setTextColor(g);

        menuOneImg.setImageResource(R.drawable.hzwhou);
        menuTwoImg.setImageResource(R.drawable.weixindianjiqian);
        menuThreeImg.setImageResource(R.drawable.tongxunludianjiqian);
        menuFourImg.setImageResource(R.drawable.wodianjiqian);

        switchContent(curFragment, hzwFragment);
    }
}