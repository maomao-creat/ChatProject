package com.zykj.samplechat.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zykj.samplechat.R;
import com.zykj.samplechat.model.About;
import com.zykj.samplechat.model.QuanJu;
import com.zykj.samplechat.presenter.OwnPresenter;
import com.zykj.samplechat.ui.activity.MyShouYiActivity;
import com.zykj.samplechat.ui.activity.ZZhuanQianActivity;
import com.zykj.samplechat.ui.activity.base.ToolBarFragment;
import com.zykj.samplechat.ui.view.OwnView;
import com.zykj.samplechat.utils.UserUtil;
import com.zykj.samplechat.zxing.CaptureActivity;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
 * <p>
 * Created by ninos on 2016/6/2.
 */
public class FenXiangFragment extends ToolBarFragment<OwnPresenter> implements OwnView {


//    @Bind(R.id.img_back)
//    ImageView imgBack;
//    @Bind(R.id.tv_title)
//    TextView tvTitle;
//    @Bind(R.id.tv_action)
//    TextView tvAction;
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.app_bar_layout)
//    AppBarLayout appBarLayout;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    @Bind(R.id.ll_sys)
    LinearLayout llSys;
    @Bind(R.id.ll_fxlj)
    LinearLayout llFxlj;
    @Bind(R.id.ll_fxewm)
    LinearLayout llFxewm;
    @Bind(R.id.ll_wdsy)
    LinearLayout llWdsy;
    @Bind(R.id.id_scrollView)
    ScrollView idScrollView;
    @Bind(R.id.tzxxnr)
    TextView tzxxnr;

    @Override
    protected void initThings(View view) {
        super.initThings(view);
        tzxxnr.setText(QuanJu.getQuanJu().getTzxx());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (new UserUtil(getContext()).isLogin()) {
            //PhotoPath=http://thirdwx.qlogo.cn/mmopen/vi_32/vAQHG6Yhou77kQicrvXO27scAHQYK0ANGSXK0F5IoqibfibD6VINxKPSVrl8MxlGU77KQzICpBtiaTdQ9LlrlmzSlg/132

        }
    }


    @Override
    protected String provideTitle() {
        return "分享";
    }

    @Override
    public void initListeners() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 201) {//二维码回调
            Toast.makeText(getContext(), "识别" + data.getStringExtra("jg"), Toast.LENGTH_SHORT).show();
        }
        if (resultCode == 200) {
//            startActivity(LoginExistActivity.class);
//            new UserUtil(getContext()).removeUserInfo();
//            RongIM.getInstance().logout();
//            finishActivity();
        }
    }

    @Override
    public int provideLayoutId() {
        return R.layout.fragment_fx;
    }

    @Override
    public OwnPresenter createPresenter() {
        return new OwnPresenter();
    }

    @Override
    public void success(final About about) {

    }

    @Override
    public void error() {
        toast("获取数据失败，请检测您的网络连接");
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, rootView);
//        return rootView;
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }

    @OnClick({R.id.ll_sys, R.id.ll_fxlj, R.id.ll_fxewm, R.id.ll_wdsy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sys://扫码
                startActivityForResult(CaptureActivity.class, 17);
                break;
            case R.id.ll_fxlj://分享
                showShare();
                break;
            case R.id.ll_fxewm:
                  /*我的推荐码*/
                startActivity(ZZhuanQianActivity.class);
                break;
            case R.id.ll_wdsy://MyShouYiActivity 我的收益
                startActivity(MyShouYiActivity.class);
                //  startActivity(XuQiuActivity.class);
                break;
        }
    }

    private void showShare() {//分享
        Intent intent1=new Intent(Intent.ACTION_SEND);
        intent1.putExtra(Intent.EXTRA_TEXT,"https://www.pgyer.com/newpengyou");
        intent1.setType("text/plain");
        startActivity(Intent.createChooser(intent1,"小莫"));
    }
     private void showShare2() {//分享
        String fxym = "https://www.pgyer.com/newpengyou";
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("" + fxym);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("快来加入我们把,点击进入下载页面");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("https://appicon.pgyer.com/image/view/app_icons/ccfa64a4de2afe4c37c0906b7b3c8289/120");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("" + fxym);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("" + fxym);

// 启动分享GUI
        oks.show(getContext());
    }
}