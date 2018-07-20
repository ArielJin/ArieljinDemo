package com.arieljin.library.demo.activity;

import android.os.Bundle;

import com.arieljin.library.activity.RefreshBaseActivity;
import com.arieljin.library.demo.R;
import com.arieljin.library.demo.net.TestStoreTask;
import com.arieljin.library.demo.net.bean.FrequenterPrepayStores;
import com.arieljin.library.demo.rend.TestAdapterRender;
import com.arieljin.library.listener.OnTaskCompleteListener;
import com.arieljin.library.utils.ToastUtil;
import com.arieljin.library.widget.adapter.AbsRecyclerAdapter;
import com.arieljin.library.widget.recyclerview.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreActivity extends RefreshBaseActivity {

    private int taskCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_more);
        initData();
    }




    private void initData() {

        final LoadMoreRecyclerView recyclerView = this.findViewById(R.id.recycler_view);

        final List<FrequenterPrepayStores.FrequenterPrepayStore> list = new ArrayList<>();

        final AbsRecyclerAdapter adapter = new AbsRecyclerAdapter<>(list, new TestAdapterRender());

        recyclerView.useDefaultLoadMore();

//        recyclerView.loadMoreFinish(false, true);

        final TestStoreTask task = new TestStoreTask(this, new TestStoreTask.TestStoreRequest(), new OnTaskCompleteListener<ArrayList<FrequenterPrepayStores.FrequenterPrepayStore>>() {
            @Override
            public void onTaskComplete(ArrayList<FrequenterPrepayStores.FrequenterPrepayStore> result) {
//                recyclerView.loadMoreFinish(false,false);
                if (taskCount > 3)
                    result = null;
                if (result != null) {

                    list.addAll(result);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                recyclerView.loadMoreFinish((result==null || (result != null && result.isEmpty())),result != null && !result.isEmpty());


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
        });

        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                taskCount++;
                task.start();



            }
        });

        recyclerView.setAdapter(adapter);





        task.start();


    }
}
