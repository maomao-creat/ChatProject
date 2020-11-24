package com.zykj.samplechat.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.app.Dialog;
import com.zykj.samplechat.R;
import com.zykj.samplechat.model.MyxiaxianxinxBean;
import com.zykj.samplechat.network.HttpUtils;
import com.zykj.samplechat.network.SubscriberRes;
import com.zykj.samplechat.ui.adapter.MainListAdapter;
import com.zykj.samplechat.utils.RecyclerViewAbstract;
import com.zykj.samplechat.utils.RecyclerViewUtil;

import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;

/**
 * 聊天
 * Created by 11655 on 2016/10/19.
 */

public class MyXiaXianFragment extends Fragment implements Listener.OnItemClickListener, Listener.LoadMoreListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters

    private Dialog dialog;

    private int mParam;//用来表示当前需要展示的是哪一页

    public MyXiaXianFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyXiaXianFragment newInstance(int param) {
        MyXiaXianFragment fragment = new MyXiaXianFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showDialog();
            mParam = getArguments().getInt(ARG_PARAM);
        }

    }

    private MainListAdapter mMainListAdapter;
    private static int MAX_SIZE = 35;
    RecyclerViewUtil rvu;
    String bs = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_detail_info, container, false);
        // detail_text= (TextView) view.findViewById(R.id.detail_info);
        //---------------------------------listView 开始

        rvu = new RecyclerViewUtil(view, getContext(), this, this, new MainListAdapter(4, getContext()));//new MainListAdapter();  这个是适配器
        rvu.setRecyclerViewAbstract(new RecyclerViewAbstract() {
            @Override
            public void shuaxin(MainListAdapter mMainListAdapter) {
                //  Toast.makeText(getContext(), "刷新" , Toast.LENGTH_SHORT).show();
                //  mMainListAdapter.setDataList(getPageData(10));
                MyLine();
            }

            @Override
            public void shangla(MainListAdapter mMainListAdapter) {
                // Toast.makeText(getContext(), "加载更多" , Toast.LENGTH_SHORT).show();
                // mMainListAdapter.addData(getPageData(5));
                MyLine();
            }
        });
        //mMainListAdapter.addHeaderView(mHeaderView);添加头部


        //----------------------------------listview 结束----------------------------

        //根据mParam来判断当前展示的是哪一页，根据页数的不同展示不同的信息
        switch (mParam) {
            case 0:
                bs = "MyOneLine";
                // detail_text.setText("内容1");
                break;
            case 1:
                bs = "MyTwoLine";
                //detail_text.setText("内容2");
                break;
            case 2:
                bs = "MyThreeLine";
                //detail_text.setText("内容2");
                break;
            case 3:
                bs = "Other";
                //detail_text.setText("内容2");
                break;
            default:
                break;

        }

        return view;
    }

    @Override
    public void onItemClick(Object data, View view, int position, int viewType) {
        //  Toast.makeText(getContext(), "Type1 位置：" + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadMore() {
        if (rvu.getMainListAdapter().getDataList().size() > MAX_SIZE) {//无刷新结果
            rvu.getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
        } else {
            rvu.loadData(false);
        }
    }

    public void MyLine() {
        HttpUtils.MyLine(new SubscriberRes<MyxiaxianxinxBean>(rvu.getRecyclerView()) {
            @Override
            public void onSuccess(MyxiaxianxinxBean userBean) {
                dismissDialog();
                rvu.setDataList(userBean.getData());
            }

            @Override
            public void completeDialog() {
                dismissDialog();
            }
        }, rvu.getindex(), "10", mParam);
    }
//    void getxuq(){
//        CommonUtil.showProcessDialog(getContext());
//        // CommonUtil.endProcessDialog();
//            Map map = new HashMap();
//        map.put("function",""+bs);
//        map.put("userid",""+new UserUtil(getContext()).getUserId2());
//        map.put("page",""+rvu.getindex());
//        map.put("size","10");
//        NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){
//
//            @Override
//            public void onError(Request request, Exception e) {
//                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
//                IsZH.getToast(getContext(), "服务器错误");  //吐司
//                CommonUtil.endProcessDialog();
//            }
//
//            @Override
//            public void onResponse(String s) {
//                CommonUtil.endProcessDialog();
//               // LogUtils.i("xxxxx", "" +s);  //输出测试
//                if(NRUtils.getYse(getContext(),s)) {
//                  //  IsZH.getToast(getContext(), "成功");  //吐司
//                    MyxiaxianxinxBean mye = NRUtils.getData(s,MyxiaxianxinxBean.class);
//                    //finish();
//                    rvu.setDataList(mye.getData());
//                }
//            }
//        });

    //    }
    public void showDialog() {
        dialog = new Dialog(getContext()).backgroundColor(Color.parseColor("#FFFFFF")).titleColor(Color.parseColor("#292A2F")).contentView(R.layout.dialog_circular).cancelable(false);
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }

    }
}
