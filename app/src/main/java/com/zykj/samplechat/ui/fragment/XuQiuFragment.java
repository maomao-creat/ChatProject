package com.zykj.samplechat.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zhy.http.okhttp.callback.StringCallback;
import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.adapter.MainListAdapter;
import com.zykj.samplechat.ui.view.lbhd;
import com.zykj.samplechat.utils.CommonUtil;
import com.zykj.samplechat.utils.IsZH;
import com.zykj.samplechat.utils.LogUtils;
import com.zykj.samplechat.utils.NR;
import com.zykj.samplechat.utils.NRUtils;
import com.zykj.samplechat.utils.RecyclerViewAbstract;
import com.zykj.samplechat.utils.RecyclerViewUtil;
import com.zykj.samplechat.utils.UserUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cn.davidsu.library.listener.Listener;
import cn.davidsu.library.loadMore.AbsLoadingMoreView;
import okhttp3.Request;

/**
 * 聊天
 * Created by 11655 on 2016/10/19.
 */

public class XuQiuFragment extends Fragment implements Listener.OnItemClickListener, Listener.LoadMoreListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";

    // TODO: Rename and change types of parameters

    private int mParam;//用来表示当前需要展示的是哪一页
    public XuQiuFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static XuQiuFragment newInstance(int param) {
        XuQiuFragment fragment = new XuQiuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }

    }
    private MainListAdapter mMainListAdapter;
    private static int MAX_SIZE = 35;
    RecyclerViewUtil rvu;
    String bs="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_detail_info, container, false);
       // detail_text= (TextView) view.findViewById(R.id.detail_info);
        //---------------------------------listView 开始

        rvu = new RecyclerViewUtil(view,getContext(),this,this,new MainListAdapter(getActivity(),1, getContext(),new lbhd(){
            @Override
            public void zx(String by, int id) {
                rvu.loadData(true);
            }
        }));//new MainListAdapter();  这个是适配器
        rvu.setRecyclerViewAbstract(new RecyclerViewAbstract() {
            @Override
            public void shuaxin(MainListAdapter mMainListAdapter) {
              //  Toast.makeText(getContext(), "刷新" , Toast.LENGTH_SHORT).show();
              //  mMainListAdapter.setDataList(getPageData(10));
                getxuq();
            }

            @Override
            public void shangla(MainListAdapter mMainListAdapter) {
               // Toast.makeText(getContext(), "加载更多" , Toast.LENGTH_SHORT).show();
               // mMainListAdapter.addData(getPageData(5));
                getxuq();
            }
        });
        //mMainListAdapter.addHeaderView(mHeaderView);添加头部


        //----------------------------------listview 结束----------------------------

        //根据mParam来判断当前展示的是哪一页，根据页数的不同展示不同的信息
        switch (mParam){
            case 0:
                bs="";
               // detail_text.setText("内容1");
                break;
            case 1:
                bs="0";
                //detail_text.setText("内容2");
                break;
            case 2:
                bs="2";
              //  detail_text.setText("内容3");
                break;
            case 3:
                bs="3";
              //  detail_text.setText("内容4");
                break;
                case 4:
                    bs="4";
               // detail_text.setText("内容5");
                break;
            default:break;

        }

        return view;
    }
    @Override
    public void onItemClick(Object data, View view, int position, int viewType) {
            Toast.makeText(getContext(), "Type1 位置：" + position, Toast.LENGTH_SHORT).show();

    }

    public static int rand() {
        Random rd = new Random();
        return rd.nextInt(2);
    }
    @Override
    public void loadMore() {
        if (rvu.getMainListAdapter().getDataList().size() > MAX_SIZE) {//无刷新结果
            rvu.getMainListAdapter().setLoadMoreState(AbsLoadingMoreView.STATE_END);
        } else {
            rvu.loadData(false);
        }
    }
    void getxuq(){
        CommonUtil.showProcessDialog(getContext());
        // CommonUtil.endProcessDialog();
            Map map = new HashMap();
        map.put("function",""+"GetFenHongList");
        map.put("userid",""+new UserUtil(getContext()).getUserId2());
        map.put("page",""+rvu.getindex());
        map.put("size","10");
        NR.posts("WebService/UserService.asmx/Entry",map,new StringCallback(){

            @Override
            public void onError(Request request, Exception e) {
                //  LogUtils.i("xxxxx222  :", "" +e);  //输出测试
                IsZH.getToast(getContext(), "服务器错误");  //吐司
            }

            @Override
            public void onResponse(String s) {
                LogUtils.i("xxxxx", "" +s);  //输出测试
                if(NRUtils.getYse(getContext(),s)) {
                    IsZH.getToast(getContext(), "成功");  //吐司
                    // MyYuEBean mye = NRUtils.getData(s,MyYuEBean.class);
                    //finish();
                   // rvu.setDataList(mye.getList());
                }
            }
        });

    }
}
