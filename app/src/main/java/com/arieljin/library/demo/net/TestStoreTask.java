package com.arieljin.library.demo.net;


import android.content.Context;

import com.arieljin.library.demo.net.bean.FrequenterPrepayStores;
import com.arieljin.library.listener.OnTaskCompleteListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class TestStoreTask extends KaQuTask<ArrayList<FrequenterPrepayStores.FrequenterPrepayStore>> {



    public TestStoreTask(Context context, TestStoreRequest request, OnTaskCompleteListener<ArrayList<FrequenterPrepayStores.FrequenterPrepayStore>> completeListener) {
        super(context, request, completeListener);
    }

    @Override
    protected String getApiMethodName() {
        return TestApi.STORE_API;
    }

    @Override
    protected ArrayList<FrequenterPrepayStores.FrequenterPrepayStore> praseJson(JSONObject json) throws Throwable {

        Class<FrequenterPrepayStores.FrequenterPrepayStore> cls = FrequenterPrepayStores.FrequenterPrepayStore.class;

        ArrayList<FrequenterPrepayStores.FrequenterPrepayStore> list = new ArrayList<FrequenterPrepayStores.FrequenterPrepayStore>();

        Constructor<FrequenterPrepayStores.FrequenterPrepayStore> constructor = (Constructor<FrequenterPrepayStores.FrequenterPrepayStore>) cls.getConstructor(JSONObject.class);

        JSONArray array = json.optJSONArray("stores");
        if (array != null && array.length() > 0) {
            for (int i = 0, size = array.length(); i < size; i++) {
                list.add(constructor.newInstance(array.optJSONObject(i)));
            }
        }
        return list;
    }

    @Override
    protected void init() {
        super.init();
        needToast = true;
        needLast = false;
        needOnlyLast = false;
    }

    public static class TestStoreRequest extends KaQuRequest{
        public String district_ids;
        public String longitude;
        public String latitude;
        public String typename;
        public String type;

        public int start, count = 30;

        public TestStoreRequest() {
            this.district_ids = "0";
            this.longitude = "";
            this.latitude = "";
            this.typename = "全部";
            this.type = "";
        }

        @Override
        public boolean setNeedSigin() {
            return false;
        }
    }
}
