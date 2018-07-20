package com.arieljin.library.demo.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.arieljin.library.abs.AbsRequest;
import com.arieljin.library.abs.AbsTask;
import com.arieljin.library.listener.OnTaskCompleteListener;
import com.arieljin.library.task.RefreshBaseTask;
import com.arieljin.library.utils.ToastUtil;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author ArielJin
 * @Email : jiasheng.jin@tom.com
 */
public abstract class KaQuTask<T extends Serializable> extends RefreshBaseTask<T> {

    public boolean needMessageShow = true;
    public String lastMark = "";

    public KaQuTask(Context context, AbsRequest request) {
        super(context, request);
//        progressY = (int)KaQuApplication.getInstance().getResources().getDimension(R.dimen.title_height);
    }

    public KaQuTask(Context context, AbsRequest request, OnTaskCompleteListener<T> completeListener) {
        super(context, request, completeListener);
//        progressY = (int)KaQuApplication.getInstance().getResources().getDimension(R.dimen.title_height);
    }

    public KaQuTask(Context context, AbsRequest request, String lastMark, OnTaskCompleteListener<T> completeListener) {
        super(context, request, completeListener);
        this.lastMark = lastMark;
    }

    public KaQuTask(Context context, AbsRequest request, boolean needToast, String lastMark, OnTaskCompleteListener<T> completeListener) {
        super(context, request, needToast, completeListener);
        this.lastMark = lastMark;
    }

    public KaQuTask(Context context, AbsRequest request, boolean needToast, OnTaskCompleteListener<T> completeListener) {
        super(context, request, needToast, completeListener);
    }


    @Override
    protected T doInBackground(MyThread<T> thread) {
        Context context = weakReference.get();

        if (!thread.isCancelled && context != null && !isActivityFinishing(context)) {

            JSONObject json = null;
            try {
                json = doMainInBackground();


                Log.e(KaQuTask.class.getName(), "doInBackground   getJson --->" + json.toString().trim());
                if (json != null && json.length() > 0) {

                    if (json.optString("err_msg").equals("成功") || json.optInt("err_code") == 0) {
                        // 短链接返回值支持
                        if (needLast && !thread.isLoadMore) {
                            saveLast(json.toString().trim());
                        }
                        if (needMessageShow) {
                            String messageShow = json.optString("message_show");
                            if (!TextUtils.isEmpty(messageShow)) {
                                ToastUtil.showMessageToast(messageShow);
                            }
                        }
                        return praseJson(json);
                    } else {
                        int errCode = json.optInt("err_code");
                        String errMsg = json.optString("err_msg");

                        Log.i("arieljin", getClass().getSimpleName() + ":" + errCode + ":" + errMsg);
//                        if (needLast && !thread.isLoadMore) {
//                            JSONObject lastJson = loadLastJson();
////                            Log.e(KaQuTask.class.getName(), "lastJson  ---- > " + lastJson !=null? lastJson.toString().trim():"lastJson   null");
//                            if (lastJson != null && lastJson.length() > 0) {
//                                return praseJson(lastJson);
//                            }
//                        }
                        if (errCode == -100) {
                            failed(getClass().getSimpleName() + errCode);
                        } else {
                            failed(getClass().getSimpleName() + ":" + errMsg);
                        }
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                failed("网络异常");
                try {

                    if (needLast && needLastOnce) {
                        JSONObject lastJSON = loadLastJson();
                        if (lastJSON != null)
                            return praseJson(lastJSON);
//                    T result = loadLast();
//                    if (result != null) {
//                        completed(result, false, true);
//                        isSending = true;
//                        return praseJson(result)

//                    }
                    }

                } catch (Throwable t) {
                    t.printStackTrace();

                }

            }

        }
        return null;
    }

    @Override
    public void cancel() {
        super.cancel();
        request.listener = null;
    }

    @Override
    protected void handleFail(String error) {
        try {
            if (isDismissToastDialog()) {
                dialog.dismiss();
            }
            if (onTaskSending())
                onTaskFailed();
        } catch (Exception e) {

        }
//        if (KaQuApi.v > 2) {
        ToastUtil.showErrorToast(error);
//        }
        if (onTaskCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskPostCompleteListeners) {
                onTaskCompleteListener.onTaskFailed(error);
            }
        }
        if (!TextUtils.isEmpty(error) && onTaskPostCompleteListeners != null) {
            for (OnTaskCompleteListener<T> onTaskCompleteListener : onTaskPostCompleteListeners) {
                onTaskCompleteListener.onTaskFailed(error);
            }
        }
    }

    @Override
    protected String getRequesturl() {
        return "";
    }

    @Override
    protected String getLastTag() {
        return "db508ozxj86be66b1a4w5goqnh2sef7q4abk7xtedc3mxj31" + lastMark;
    }

    @Override
    protected HashMap<String, String> addHeaders() {
        if (headers == null || headers.size() == 0) {
//            HashMap<String, String> hashMap = KaQuApi.getApiParametersWithTokenForHeader();
//            HashMap<String, String> temp = (HashMap<String, String>) hashMap.clone();
            HashMap<String, String> temp = new HashMap<String, String>();
            String token = "db508ozxj86be66b1a4w5goqnh2sef7q4abk7xtedc3mxj31";
            if (!TextUtils.isEmpty(token)) {
                temp.put("Authorization", "Bearer " + token);
            }


//            for (final Map.Entry<String, String> entry : hashMap.entrySet()) {
//                String key = entry.getKey();
//                String value = hashMap.get(key);
//                if (key.equals("token") && !TextUtils.isEmpty(value)) {
//                    temp.put("Authorization", "Bearer " + hashMap.get(key));
//                }
//            }
            checkHeaders(temp);
            return temp;
        }

        checkHeaders(headers);
        return headers;
    }

    public static void initHeaders() {
        headers = null;
    }

    private void checkHeaders(HashMap<String, String> headersHashMap) {

//                if (!headersHashMap.containsKey("token"))
//                    headersHashMap.put("token", PreferenceManager.getToken());
        if (!headersHashMap.containsKey("Authorization"))
            headersHashMap.put("Authorization", "Bearer " + "db508ozxj86be66b1a4w5goqnh2sef7q4abk7xtedc3mxj31");

    }
}
