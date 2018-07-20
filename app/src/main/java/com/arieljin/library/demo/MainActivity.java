package com.arieljin.library.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arieljin.library.abs.AbsActivity;
import com.arieljin.library.demo.activity.DefaultRefreshLoadActivity;
import com.arieljin.library.demo.activity.LoadMoreActivity;
import com.arieljin.library.widget.adapter.AbsRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AbsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {

        RecyclerView menuRecycler = this.findViewById(R.id.menu_recycler);

        List<String> list = new ArrayList<>();
        list.add("添加header和footer");
        list.add("LoadMore");
        menuRecycler.setAdapter(new AbsRecyclerAdapter<>(list, new AbsRecyclerAdapter.AdapterRender<String>() {
            @Override
            protected View getViewHolder(ViewGroup parent) {
                return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);
            }

            @Override
            public void fitEvents(final String s) {

                vh.obtainView(R.id.tv_menu_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (s){
                            case "添加header和footer":
                                startActivity(new Intent(MainActivity.this, DefaultRefreshLoadActivity.class));
                                break;
                            case "LoadMore":
                                startActivity(new Intent(MainActivity.this, LoadMoreActivity.class));
                                break;

                        }
                    }
                });

            }

            @Override
            public void fitDatas(String s) {
                vh.obtainView(R.id.tv_menu_item,TextView.class).setText(s);

            }
        }));

    }

}
