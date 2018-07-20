package com.arieljin.library.demo.net;

import android.text.TextUtils;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ArielJin
 * @Email : jiasheng.jin@tom.com
 */
public class ParamSiginUtil {

    public final static String KAQU_SECRET = "4iT0UCL0BQTS7XN9YC04B2YkV2F4K3";

    public static ApiParameters getParamSigin(ApiParameters parameters) {
        Map<String, String> hashMap = new HashMap<String, String>();
        parameters.add("timestamp", System.currentTimeMillis() / 1000);
        for (int i = 0; i < parameters.size(); i++) {
            String key = parameters.getKey(i);
            String value = parameters.getValue(i);
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value) && !key.equals("password") && !key.equals("token") && !key.equals("soundid")) {
                hashMap.put(key, value);
            }
        }
        if (hashMap != null && hashMap.size() > 0) {
            hashMap.put("appSecret", KAQU_SECRET);
            String sign = sign(hashMap);
            if (!TextUtils.isEmpty(sign)) {
                parameters.add("sign", sign);
            }
        }
        return parameters;
    }

    //    public static List<NameValuePair> getParamSigin(List<NameValuePair> list){
//        HashMap<String, String> hashMap =  KaQuApi.getApiParametersWithTokenForHeader();
//        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
//        if(list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                hashMap.put(list.get(i).getName(), list.get(i).getValue());
//            }
//        }
//        HashMap<String,String> romoveHashMap = new HashMap<String,String>();
//        for (Map.Entry<String, String> entry : hashMap.entrySet()){
//            String key = entry.getKey();
//            if (key.equals("password") || key.equals("token") || key.equals("soundid")){
//                romoveHashMap.put(key,hashMap.get(key));
//            }
//        }
//        if(romoveHashMap.size() > 0){
//            for (Map.Entry<String, String> entry:romoveHashMap.entrySet()){
//                hashMap.remove(entry.getKey());
//            }
//        }
//        hashMap.put("appSecret",KaQuApi.KAQU_SECRET);
//        String sign = sign(hashMap);
//        list.add(new BasicNameValuePair("sign",sign));
//        return list;
//    }
    public static List<NameValuePair> getParamSigin(List<NameValuePair> list) {
        list = getAddHeadParam(list);
        HashMap<String, String> hashMap = getHashMapFromList(list);
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        hashMap.put("timestamp", timestamp);
        hashMap.put("appSecret", KAQU_SECRET);
        list.add(new BasicNameValuePair("sign", doSigin(hashMap)));
        list.add(new BasicNameValuePair("timestamp", timestamp));
        return list;

    }

    private static String doSigin(HashMap<String, String> hashMap) {
        HashMap<String, String> siginMap = new HashMap<String, String>();
        if (hashMap != null && hashMap.size() > 0) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!key.equals("password") && !key.equals("soundid")) {
                    siginMap.put(key, value);
                }
            }
        }
        return sign(siginMap);

    }

    private static HashMap<String, String> getHashMapFromList(List<NameValuePair> list) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                hashMap.put(list.get(i).getName(), list.get(i).getValue());
            }
        }
        return hashMap;
    }


    public static List<NameValuePair> getAddHeadParam(List<NameValuePair> list) {
        HashMap<String, String> hashMap = getApiParametersWithTokenForHeader();
        if (hashMap != null && hashMap.size() > 0) {
            for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value) && !key.equals("token")) {
                    list.add(new BasicNameValuePair(key, value));
                }
            }
        }
        return list;
    }

    public static HashMap<String, String> getParamSigin(HashMap<String, String> hashMap) {
        HashMap<String, String> headerHashMap = getApiParametersWithTokenForHeader();
        hashMap.putAll(headerHashMap);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            if (key.equals("password") || key.equals("token") || key.equals("soundid")) {
                hashMap.remove(key);
            }
        }
        hashMap.put("appSecret", KAQU_SECRET);
        String sign = sign(hashMap);
        hashMap.put("sign", sign);
        return hashMap;
    }

    public static HashMap<String, String> addHeadParam(HashMap<String, String> hashMap) {
        HashMap<String, String> headerHashMap = getApiParametersWithTokenForHeader();
        for (Map.Entry<String, String> entry : headerHashMap.entrySet()) {
            String key = entry.getKey();
            if (!key.equals("token")) {
                hashMap.put(key, headerHashMap.get(key));
            }
        }
        return hashMap;
    }

    private static String toHexValue(byte[] messageDigest) {
        if (messageDigest == null)
            return "";
        StringBuilder hexValue = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            int val = 0xFF & aMessageDigest;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * @param params
     * @return
     */
    private static String sign(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String string = "";
        for (String s : keys) {
            string += params.get(s);
        }
        String sign = "";
        try {
            sign = toHexValue(encryptMD5(string.getBytes(Charset.forName("utf-8"))));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("md5 error");
        }
        return sign;
    }

    private static byte[] encryptMD5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(data);
        return md5.digest();
    }

    public static HashMap<String, String> getApiParametersWithTokenForHeader() {
        HashMap<String, String> headers = new HashMap<String, String>();
        String token = "db508ozxj86be66b1a4w5goqnh2sef7q4abk7xtedc3mxj31";
        if (!TextUtils.isEmpty(token)) {
            headers.put("token", token);
        }
        headers.put("login_id", "201807101735212365468");
        headers.put("login_version", "android_3.30");
        headers.put("project_type", "kaqu");
        return headers;
    }

    //district_ids0
    //typename=全部

}
