package com.arieljin.library.demo.rend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arieljin.library.demo.R;
import com.arieljin.library.demo.net.bean.FrequenterPrepayStores;
import com.arieljin.library.utils.DipUtil;
import com.arieljin.library.widget.adapter.AbsRecyclerAdapter;

public class TestAdapterRender extends AbsRecyclerAdapter.AdapterRender<FrequenterPrepayStores.FrequenterPrepayStore> {


    @Override
    protected View getViewHolder(ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frequenter_league_merchant,parent,false);
    }


    @Override
    public void fitEvents(FrequenterPrepayStores.FrequenterPrepayStore frequenterPrepayStore) {

        RelativeLayout itemParentsRl = vh.obtainView(R.id.rl_itemParents);
        ViewGroup.LayoutParams lp = itemParentsRl.getLayoutParams();
        lp.height = (DipUtil.getScreenWidth() - 20) * 1 / 3;
        itemParentsRl.setLayoutParams(lp);
        itemParentsRl.invalidate();


        vh.obtainView(R.id.tv_name,TextView.class).setText(frequenterPrepayStore.getName());
        vh.obtainView(R.id.tv_address,TextView.class).setText(frequenterPrepayStore.getDistance() + " "+frequenterPrepayStore.getShortAddress());
        vh.obtainView(R.id.tv_time, TextView.class).setText(frequenterPrepayStore.getBusiness_time());



    }

    @Override
    public void fitDatas(FrequenterPrepayStores.FrequenterPrepayStore frequenterPrepayStore) {

    }
}
