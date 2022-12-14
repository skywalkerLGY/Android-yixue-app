package com.example.curriculum_design.search;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.example.curriculum_design.DB_Help.DbHelper;
import com.example.curriculum_design.DB_Help.Kd_information;
import com.example.curriculum_design.DB_Help.Static_KD;
import com.example.curriculum_design.R;
import com.githang.statusbar.StatusBarCompat;
import com.mengpeng.mphelper.ToastUtils;

import java.util.List;


//import android.support.v7.app.AppCompatActivity;


/**
 * ??????????????????poi???????????????????????????
 */
public class PoiCitySearchDemo extends AppCompatActivity implements OnGetPoiSearchResultListener,
        OnGetSuggestionResultListener, AdapterView.OnItemClickListener, PoiListAdapter.OnGetChildrenLocationListener {

    int sum=0;
    private PoiSearch mPoiSearch = null;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    // ???????????????????????????
    private EditText mEditCity = null;
    private AutoCompleteTextView mKeyWordsView = null;
    private RelativeLayout mPoiDetailView;
    private ListView mPoiList;
    // ??????
    private int mLoadIndex = 0;
    private List<PoiInfo> mAllPoi;
    private BitmapDescriptor mBitmap;
    TextView address_text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//???????????????
        setContentView(R.layout.activity_poicitysearch);
        StatusBarCompat.setStatusBarColor(this,getResources().getColor(R.color.login_color),false);

        mBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        // ??????map
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //?????????????????????

        LatLng cenpt = new LatLng(32.206788,119.521066);
        //??????????????????
        MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18).build();
        //??????MapStatusUpdate??????????????????????????????????????????????????????
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //??????????????????
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        // ??????poi???????????????????????????????????????
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mEditCity = (EditText) findViewById(R.id.city);
        mKeyWordsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        address_text = findViewById(R.id.address_text);
        // ????????????????????????
        mPoiDetailView = (RelativeLayout) findViewById(R.id.poi_detail);
        mPoiList = (ListView) findViewById(R.id.poi_list);
        mPoiList.setOnItemClickListener(this);
        // ??????????????????
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                showPoiDetailView(false);
            }

            @Override
            public void onMapPoiClick(MapPoi poi) {

            }
        });
        Button yuyue = findViewById(R.id.yuyue);
        yuyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!address_text.getText().equals("????????????: ??? (???????????????????????????)")){
                    showDialog();
                }
                else{
                    Toast.makeText(PoiCitySearchDemo.this, "????????????,???????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

        RadioButton yuan = findViewById(R.id.yuan), weixin = findViewById(R.id.weixin), reli = findViewById(R.id.reli);
        yuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setBaiduHeatMapEnabled(false);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            }
        });
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setBaiduHeatMapEnabled(false);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
            }
        });
        reli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaiduMap.setBaiduHeatMapEnabled(true);
            }
        });
        KeybordUtil.closeKeybord(this);
        String cityStr = mEditCity.getText().toString();
        String keyWordStr = mKeyWordsView.getText().toString();
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(cityStr)
                .keyword(keyWordStr)
                .pageNum(mLoadIndex) // ????????????
                .cityLimit(true)
                .scope(1));

    }

    /**
     * ?????????????????????????????????????????????
     */
    public void showDialog() {

        final Dialog dialog = new Dialog(PoiCitySearchDemo.this);
        //??????title
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        Window window = dialog.getWindow();
        // ????????????
        window.setContentView(R.layout.dialog_test);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // ????????????
        window.setLayout(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        // ???????????????????????????
        window.setWindowAnimations(R.style.AnimScale);
        window.setGravity(Gravity.CENTER);

        final ImageView img =  window.findViewById(R.id.meeting_img_dialog);
        final TextView name=window.findViewById(R.id.name);
        final TextView phonenum=window.findViewById(R.id.phone);
        final TextView haoplv=window.findViewById(R.id.haoplv);
        final ImageView qishou=window.findViewById(R.id.qishoujuli);
        img.setImageResource(R.drawable.pengyuyan);
        Button cancel_btn = (Button) window.findViewById(R.id.cancel_dialog);
        Button join_btn = (Button) window.findViewById(R.id.join_dialog);
        Button change = (Button) window.findViewById(R.id.change);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PoiCitySearchDemo.this, "?????????", Toast.LENGTH_SHORT).show();
                sum=0;
                dialog.cancel();
            }
        });
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.onSuccessShowToast("????????????");
                sum=0;
                String []s=(name.getText().toString()).split(":");
                Static_KD.worker_name=s[1];
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        DbHelper.Insert_KD(new Static_KD());
                    }
                }).start();
                dialog.cancel();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum++;
                if(sum%2==1) {
                    img.setImageResource(R.drawable.yueyunpeng);
                    name.setText("??????:?????????");
                    phonenum.setText("??????:13771973655");
                    haoplv.setText("?????????:99.68%");
                    qishou.setBackgroundResource(R.drawable.qishou19);
                }
                else{
                    img.setImageResource(R.drawable.pengyuyan);
                    name.setText("??????:?????????");
                    phonenum.setText("??????:13771974655");
                    haoplv.setText("?????????:99.87%");
                    qishou.setBackgroundResource(R.drawable.qishou25);
                }
                if(sum==2)
                    Toast.makeText(PoiCitySearchDemo.this, "????????????,???????????????", Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * ???????????????????????????????????????
     *
     * @param v ??????Button
     */
    public void searchButtonProcess(View v) {
        //  ?????????????????????????????????????????????????????????????????? PoiDetailView ??????????????????????????????poi????????????????????????
        KeybordUtil.closeKeybord(this);
        // ??????????????????
        String cityStr = mEditCity.getText().toString();
        // ?????????????????????
        String keyWordStr = mKeyWordsView.getText().toString();


        // ????????????
        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city(cityStr)
                .keyword(keyWordStr)
                .pageNum(mLoadIndex) // ????????????
                .cityLimit(true)
                .scope(1));
    }

    /**
     * ?????????
     */
    public void goToNextPage(View v) {
        mLoadIndex++;
        searchButtonProcess(null);
    }

    /**
     * ????????????poi????????????
     *
     * @param result poi????????????
     */
    @Override
    public void onGetPoiResult(final PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            mLoadIndex = 0;
            mBaiduMap.clear();
            showPoiDetailView(false);
            Toast.makeText(PoiCitySearchDemo.this, "???????????????", Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            showPoiDetailView(true);
            mBaiduMap.clear();

            // ?????? View ?????????????????????view?????????
            mPoiDetailView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int padding = 50;
                    // ??????poi
                    PoiOverlay overlay = new PoiCitySearchDemo.MyPoiOverlay(mBaiduMap);
                    mBaiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(result);
                    overlay.addToMap();
                    // ?????? view ?????????
                    int PaddingBootom = mPoiDetailView.getMeasuredHeight();
                    // ???????????????????????????????????????????????????
                    overlay.zoomToSpanPaddingBounds(padding, padding, padding, PaddingBootom);
                    // ????????????????????????View????????????????????????????????????
                    mPoiDetailView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });

            // ??????poi??????
            mAllPoi = result.getAllPoi();
            PoiListAdapter poiListAdapter = new PoiListAdapter(this, mAllPoi);
            poiListAdapter.setOnGetChildrenLocationListener(this);
            // ???poi????????????????????????
            mPoiList.setAdapter(poiListAdapter);

            return;
        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            String strInfo = "???";

            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }
            strInfo += "????????????";
            Toast.makeText(PoiCitySearchDemo.this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult result) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult suggestionResult) {

    }

    /**
     * poilist ????????????
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PoiInfo poiInfo = mAllPoi.get(position);
        if (poiInfo.getLocation() == null) {
            return;
        }
        addPoiLoction(poiInfo.getLocation());
        view = mPoiList.getChildAt(position - mPoiList.getFirstVisiblePosition());
        System.out.println(position + "----------------");
        TextView textView = view.findViewById(R.id.poi_address);


        address_text.setText("????????????:" + textView.getText().toString());
    }

    /**
     * ???????????????list ??????????????????poi????????????
     *
     * @param childrenLocation ??????????????????
     */
    @Override
    public void getChildrenLocation(LatLng childrenLocation) {

        addPoiLoction(childrenLocation);
    }


    /**
     * ???????????????????????????
     *
     * @param latLng ??????????????????
     */
    private void addPoiLoction(LatLng latLng) {
        mBaiduMap.clear();
        showPoiDetailView(false);
        OverlayOptions markerOptions = new MarkerOptions().position(latLng).icon(mBitmap);
        mBaiduMap.addOverlay(markerOptions);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng);
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }


    protected class MyPoiOverlay extends PoiOverlay {
        MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            Toast.makeText(PoiCitySearchDemo.this, poi.address, Toast.LENGTH_LONG).show();
            return true;
        }
    }


    /**
     * ?????????????????? view
     */
    private void showPoiDetailView(boolean whetherShow) {
        if (whetherShow) {
            mPoiDetailView.setVisibility(View.VISIBLE);
        } else {
            mPoiDetailView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // ????????????
        showPoiDetailView(false);
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // ??????bitmap
        mBitmap.recycle();
        // ??????????????????
        mPoiSearch.destroy();
        // ??????????????????????????????
        mBaiduMap.clear();
        // ??????????????????
        mMapView.onDestroy();
    }
}
