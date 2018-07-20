package com.arieljin.library.demo.net;

import android.content.Context;
import android.util.Log;

import com.arieljin.library.abs.AbsRequest;
import com.arieljin.library.abs.AbsTask;
import com.arieljin.library.entity.CustomMultipartEntity;

import org.apache.http.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * @author ArielJin
 * @Email : jiasheng.jin@tom.com
 */
public abstract class KaQuRequest extends AbsRequest {
//    public String statuses;
    public KaQuRequest(){}
    public KaQuRequest(UploadType uploadType){
        super(uploadType);
    }
    private boolean needSigin;

    @Override
    public List<NameValuePair> getBody(Context context) {
        init();
        List<NameValuePair> list = super.getBody(context);
//        if(KaQuApi.v>0){
            for (int i = 0,size = list.size(); i < size; i++){
                NameValuePair pair = list.get(i);
                Log.i("json", pair.getName() + ":" + pair.getValue());
            }
        if(needSigin && list != null && list.size() > 0){
            return ParamSiginUtil.getParamSigin(list);
        }
//        }
//        Log.e("aaaaaaaaaaaaa", ParamSiginUtil.getAddHeadParam(list).toString());
        return ParamSiginUtil.getAddHeadParam(list);
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public CustomMultipartEntity getMultipartEntity(Context context, AbsTask<?> task) {
//        CustomMultipartEntity entity = super.getMultipartEntity(context, task);
//        Log.i("arilejin", entity + ":" + entity.getContentLength());
//        return entity;
//    }


    @Override
    public CustomMultipartEntity getMultipartEntity(Context context, AbsTask<?> task) {
        init();
        CustomMultipartEntity entity = new CustomMultipartEntity(listener,task);
        HashMap<String, String> hashMap = new HashMap<String,String>();
        for (Field field : getClass().getFields()){
            try {
                field.setAccessible(true);
                Object obj = field.get(this);
                if(obj instanceof CustomMultipartEntity.OnFileUploadProgressListener){
                    continue;
                }
                if(obj != null){
                    hashMap.put(field.getName(),obj.toString().trim());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        if(needSigin && hashMap != null && hashMap.size() >0){
//            hashMap = ParamSiginUtil.getParamSigin(hashMap);
//        }
        try {
            super.putBody(context, entity, "map", hashMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return entity;
    }


    public abstract boolean setNeedSigin();

    @Override
    protected void init() {
        super.init();
        this.needSigin = setNeedSigin();

    }
}
