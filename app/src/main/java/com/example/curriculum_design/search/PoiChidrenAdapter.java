package com.example.curriculum_design.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiChildrenInfo;
import com.example.curriculum_design.R;

import java.util.List;


/**
 * 展示父子节点适配器
 */
public class PoiChidrenAdapter  extends BaseAdapter {

    private Context mContext;
    private List<PoiChildrenInfo> mPoiChildrenInfoList;
    private OnGetChildrenLocationListener mOnGetChildrenLocationListener;

    public PoiChidrenAdapter(Context mContext, List<PoiChildrenInfo> poiChildrenInfoList) {
        this.mContext = mContext;
        this.mPoiChildrenInfoList = poiChildrenInfoList;
    }

    public void updateData(List<PoiChildrenInfo> poiChildrenInfoList) {
        this.mPoiChildrenInfoList = poiChildrenInfoList;
    }

    @Override
    public int getCount() {
        return mPoiChildrenInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPoiChildrenInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.children_poi_item,null);
            viewHolder.poichildrenShowName = (TextView) convertView.findViewById(R.id.children_poi_name);
            convertView.setTag(viewHolder);
        }else {
             viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.poichildrenShowName.setText(mPoiChildrenInfoList.get(position).getShowName());
        viewHolder.poichildrenShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PoiChildrenInfo poiChildrenInfo = mPoiChildrenInfoList.get(position);
                mOnGetChildrenLocationListener.getChildrenLocation(poiChildrenInfo.getLocation());
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView poichildrenShowName;
    }


    public interface OnGetChildrenLocationListener {
        void getChildrenLocation(LatLng childrenLocation);
    }

    public void setOnGetChildrenLocationListener(OnGetChildrenLocationListener onGetChildrenLocationListener){
        this.mOnGetChildrenLocationListener = onGetChildrenLocationListener;
    }
}
