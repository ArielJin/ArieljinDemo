package com.arieljin.library.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arieljin.library.activity.RefreshBaseActivity;
import com.arieljin.library.demo.R;
import com.arieljin.library.demo.net.TestStoreTask;
import com.arieljin.library.demo.net.bean.FrequenterPrepayStores;
import com.arieljin.library.demo.rend.TestAdapterRender;
import com.arieljin.library.listener.OnTaskCompleteListener;
import com.arieljin.library.utils.ToastUtil;
import com.arieljin.library.widget.adapter.AbsRecyclerAdapter;
import com.arieljin.library.widget.recyclerview.AdRecyclerView;

import java.util.ArrayList;


public class DefaultRefreshLoadActivity extends RefreshBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_refresh_load);
        initViews();

    }

    private void initViews() {


//        final RecyclerView recyclerView = new RecyclerView(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        refreshLayout.addView(recyclerView);
        final AdRecyclerView recyclerView = this.findViewById(R.id.recycler_view);

//        final ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.drawable.ic_launcher_background);
//        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

        final LinearLayout headerLinearLayout = new LinearLayout(this);
        headerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout footerLinearLayout = new LinearLayout(this);
        footerLinearLayout.setOrientation(LinearLayout.VERTICAL);

        Button addHeader = this.findViewById(R.id.ad_header);
        Button addFooter = this.findViewById(R.id.ad_footer);

        recyclerView.addHeaderView(headerLinearLayout);
        recyclerView.addFooterView(footerLinearLayout);

        addHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = new ImageView(DefaultRefreshLoadActivity.this);
                imageView.setImageResource(R.drawable.ic_launcher_background);
                headerLinearLayout.addView(imageView);

            }
        });
        addFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView imageView = new ImageView(DefaultRefreshLoadActivity.this);
                imageView.setImageResource(R.drawable.ic_launcher_background);

                footerLinearLayout.addView(imageView);

            }
        });


//        refreshLayout.setRefreshing(true);
        new TestStoreTask(this, new TestStoreTask.TestStoreRequest(), new OnTaskCompleteListener<ArrayList<FrequenterPrepayStores.FrequenterPrepayStore>>() {
            @Override
            public void onTaskComplete(ArrayList<FrequenterPrepayStores.FrequenterPrepayStore> result) {
                recyclerView.setAdapter(new AbsRecyclerAdapter<>(result, new TestAdapterRender()));


            }

            @Override
            public void onTaskLoadMoreComplete(ArrayList<FrequenterPrepayStores.FrequenterPrepayStore> result) {

            }

            @Override
            public void onTaskFailed(String error) {
                ToastUtil.showErrorToast(error);

            }

            @Override
            public void onTaskCancel() {

            }
        }).start();

    }

}


