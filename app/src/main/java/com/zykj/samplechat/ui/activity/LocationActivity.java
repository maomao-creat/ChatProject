package com.zykj.samplechat.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.zykj.samplechat.R;
import com.zykj.samplechat.network.Const;
import com.zykj.samplechat.ui.adapter.NearAddressAdapter;
import com.zykj.samplechat.ui.fragment.IndexFragment;
import com.zykj.samplechat.ui.widget.ClearEditText;
import com.zykj.samplechat.utils.WindowUtil;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.message.LocationMessage;

/**
 * @author Nate
 */
public class LocationActivity extends AppCompatActivity implements OnClickListener,/*TextWatcher,*/
        OnMapStatusChangeListener,OnGetGeoCoderResultListener/*,OnGetPoiSearchResultListener*/ {
    @Bind(R.id.mapView)
    MapView mMapView;
    @Bind(R.id.search_et)
    ClearEditText _SearchEt;
    @Bind(R.id.location_iv)
    ImageView _LocationIv;
    @Bind(R.id.near_address_list)
    ListView _NearAddressList;
    @Bind(R.id.near_list_empty_ll)
    LinearLayout _NearListEmptyLl;
    @Bind(R.id.near_address_ll)
    LinearLayout _NearAddressLl;
    @Bind(R.id.divider_line)
    View _DividerLine;
    //@Bind(R.id.search_address_list_view)
    //ListView _SearchAddressListView;
    @Bind(R.id.search_empty_tv)
    TextView _SearchEmptyTv;
    //@Bind(R.id.search_ll)
    //LinearLayout _SearchLl;
    @Bind(R.id.img_back)
    ImageView _ImgBack;
    @Bind(R.id.tv_title)
    TextView _TvTitle;
    //@Bind(R.id.tv_action)
    //TextView _TvAction;
    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private BaiduMap mBaiduMap = null;
    private MyLocationListenner myListener = new MyLocationListenner();
    private boolean isFirstLoc = true;// 是否首次定位
    private LatLng latLng;
    private LocationClient mLocClient;// 定位相关
    //private PoiSearch mPoiSearch = null;

    private NearAddressAdapter nearAddressAdapter = null;
    //private SearchAddressAdapter searchAddressAdapter = null;
    private List<PoiInfo> nearAddresses = new ArrayList<>();
    //private List<PoiInfo> searchAddresses = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_service_location);
        ButterKnife.bind(this);
        mBaiduMap = mMapView.getMap();
        ViewGroup.LayoutParams params = mMapView.getLayoutParams();
        params.height = WindowUtil.getScreenWidth(this) * 5 / 8;
        initListeners();
        initViewsAndEvents();
    }

    private void initListeners() {
        _ImgBack.setOnClickListener(this);// 点击返回按钮
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);//market搜索
        // 初始化搜索模块，注册搜索事件监听
        //mPoiSearch = PoiSearch.newInstance();
        //mPoiSearch.setOnGetPoiSearchResultListener(this);//poi搜索
        //_SearchEt.addTextChangedListener(this);//搜索框
        //cityName = "南京";//这个可以通过定位获取
        mMapView.showScaleControl(false);//隐藏比例尺
        mMapView.showZoomControls(false);//隐藏缩放图标
        mBaiduMap.setOnMapStatusChangeListener(this);//监听地图状态变化
        MyLocationConfiguration config = new MyLocationConfiguration(LocationMode.NORMAL, false, null);
        mBaiduMap.setMyLocationConfigeration(config);// 设置定位图标
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                .zoom(19.5f).build()));//设置地图缩放级别为15

        mLocClient = new LocationClient(this);// 定位初始化
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(false);// 打开gpss
        option.setCoorType("bd09ll"); // 设置坐标类型 取值有3个： 返回国测局经纬度坐标系：gcj02
        // 返回百度墨卡托坐标系 ：bd09 返回百度经纬度坐标系 ：bd09ll
        option.setScanSpan(1000);// 扫描间隔 单位毫秒
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    protected void initViewsAndEvents() {
        nearAddressAdapter = new NearAddressAdapter(this, R.layout.item_near_address, nearAddresses);
        _NearAddressList.setAdapter(nearAddressAdapter);
        _NearAddressList.setEmptyView(_NearListEmptyLl);
        _NearAddressList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LatLng tg = nearAddresses.get(position).location;
                //Uri uri = Uri.parse("http://api.map.baidu.com/staticimage/v2?ak=rs8pD43GeAkZpFHii4Z3PLr3&mcode=666666&center=" + poiInfo.location.longitude + "," + poiInfo.location.latitude + "&width=300&height=200&zoom=15");
                String strurl = Const.BAIDU;
                strurl += "?center="+tg.longitude+","+tg.latitude;
                strurl += "&width=306&height=180&zoom=19&markerStyles=l,A,silver";
                strurl += "&markers="+tg.longitude+","+tg.latitude;
                LocationMessage msg = LocationMessage.obtain(tg.latitude, tg.longitude, nearAddresses.get(position).address, Uri.parse(strurl));
                IndexFragment._LocationCallback.onSuccess(msg);
                LocationActivity.this.finish();
            }
        });

//        _TvAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (nearAddresses != null && nearAddresses.size() > 0) {
//                    PoiInfo poiInfo = nearAddresses.get(0);
//                    Uri uri = Uri.parse("http://api.map.baidu.com/staticimage/v2?ak=rs8pD43GeAkZpFHii4Z3PLr3&mcode=666666&center=" + poiInfo.location.longitude + "," + poiInfo.location.latitude + "&width=300&height=200&zoom=15");
//                    Log.d("BasicLocationMapActivit", uri.toString());
//                    IndexFragment._LocationCallback.onSuccess(LocationMessage.obtain(poiInfo.location.latitude, poiInfo.location.longitude, poiInfo.address, uri));
//                }
//                LocationActivity.this.finish();
//            }
//        });

//        searchAddressAdapter = new SearchAddressAdapter(this, R.layout.item_search_address, searchAddresses);
//        _SearchAddressListView.setAdapter(searchAddressAdapter);
//        _SearchAddressListView.setEmptyView(_SearchEmptyTv);
//
//        _SearchAddressListView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PoiInfo poiInfo = searchAddresses.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putString("Ing", poiInfo.location.longitude + "");
//                bundle.putString("Iat", poiInfo.location.latitude + "");
//                bundle.putString("Address", poiInfo.name);
//                bundle.putString("DetailedAddress", poiInfo.address);
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                setResult(RESULT_OK, intent);
//                finish();
//            }
//        });
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        mSearch.destroy();
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
        super.onDestroy();
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult arg0) {}

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        //market搜索
        nearAddresses.clear();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(LocationActivity.this, "抱歉，未能找到结果", Toast.LENGTH_LONG).show();
        }else{
            List<PoiInfo> list = result.getPoiList();
            if (list != null && list.size() > 0) {
                nearAddresses.addAll(list);
                nearAddressAdapter.notifyDataSetChanged();
            }
        }
    }

    // 定位图标点击，重新设置为初次定位
    @OnClick(value = R.id.location_iv)
    public void reLocation(View view) {
        if(latLng != null){
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
        }else{
            isFirstLoc = true;
            mLocClient.requestLocation();
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location != null && mMapView != null) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(0)
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        //.direction(100)
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                //cityName = location.getCity();
                if (isFirstLoc) {
                    isFirstLoc = false;
                    latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                    mBaiduMap.animateMapStatus(mapStatusUpdate);
                    mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));//第一次反Geo搜索
                }
            }
        }
    }

//    @Override
//    public void onGetPoiDetailResult(PoiDetailResult arg0) {}
//
//    @Override
//    public void onGetPoiResult(PoiResult result) {
//        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
//            return;
//        }
//        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//            List<PoiInfo> list = result.getAllPoi();
//            _SearchLl.setVisibility(View.VISIBLE);
//            if (list != null && list.size() > 0) {
//                searchAddresses.clear();
//                searchAddresses.add(list.get(0));
//                searchAddressAdapter.notifyDataSetChanged();
//            }
//        }
//    }

    @Override
    public void onClick(View v) {
        LocationActivity.this.onBackPressed();//返回按钮
    }

//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//    @Override
//    public void onTextChanged(CharSequence cs, int start, int before, int count) {
//        if (cs == null || cs.length() <= 0) {
//            _SearchLl.setVisibility(View.GONE);
//            return;
//        }
//        //使用建议搜索服务获取建议列表
//        mPoiSearch.searchInCity((new PoiCitySearchOption()).city(cityName)
//                .keyword(cs.toString()).pageNum(0).pageCapacity(20));
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {}

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {}

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(mapStatus.target));//反Geo搜索
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {}
}