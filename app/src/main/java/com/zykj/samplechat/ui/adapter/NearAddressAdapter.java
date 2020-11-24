package com.zykj.samplechat.ui.adapter;

import android.content.Context;

import com.baidu.mapapi.search.core.PoiInfo;
import com.zykj.samplechat.R;
import com.zykj.samplechat.ui.adapter.base.BaseViewHolder;
import com.zykj.samplechat.ui.adapter.base.MyBaseAdapter;

import java.util.List;

/**
 * @author Nate
 * @desc 附近的地址列表适配器
 * @date 2015-12-20
 */
public class NearAddressAdapter extends MyBaseAdapter<PoiInfo> {

    public NearAddressAdapter(Context context, int resource, List<PoiInfo> list) {
        super(context, resource, list);
    }

    @Override
    public void setConvert(BaseViewHolder viewHolder, PoiInfo info, int pos) {
//        if (pos == 0) {
//          viewHolder.getView(R.id.img).setVisibility(View.VISIBLE);
//        }

        viewHolder.setTextView(R.id.item_address_name_tv, info.name);
        viewHolder.setTextView(R.id.item_address_detail_tv, info.address);
    }
}
