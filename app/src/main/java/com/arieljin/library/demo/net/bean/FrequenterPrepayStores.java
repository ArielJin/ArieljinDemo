package com.arieljin.library.demo.net.bean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ArielJin
 * @Email : jiasheng.jin@tom.com
 */
public class FrequenterPrepayStores implements Serializable {

    private String counts;
    private List<FrequenterPrepayStore> frequenterPrepayStores;


    public FrequenterPrepayStores(JSONObject json) {
        if(json != null){
            this.counts = json.optString("counts");
            JSONArray jsonArray = json.optJSONArray("stores");
            frequenterPrepayStores = new ArrayList<FrequenterPrepayStore>();
            if(jsonArray != null && jsonArray.length()>0){
                for (int i=0;i<jsonArray.length();i++){
                    FrequenterPrepayStore frequentrerPrepayStore = new FrequenterPrepayStore(jsonArray.optJSONObject(i));
                    if(frequentrerPrepayStore != null){
                        frequenterPrepayStores.add(frequentrerPrepayStore);
                    }
                }
            }

        }
    }

    public int getCounts() {
        try {
            return Integer.parseInt(counts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public List<FrequenterPrepayStore> getFrequenterPrepayStores() {
        return frequenterPrepayStores;
    }

    public boolean isFrequenterPrepayStoreNull(){
        return !(frequenterPrepayStores!= null && frequenterPrepayStores.size()>0);
    }

    public static class FrequenterPrepayStore implements Serializable{

        private String id;
        private String name;
        private String district;
        private String shortAddress;
        private String bg_pic;
        private String distance;
        private String business_time;
        private String join_info;

        public FrequenterPrepayStore(JSONObject json) {
            if(json != null){
                this.id = json.optString("id");
                this.name = json.optString("name");
                this.district = json.optString("district");
                this.shortAddress = json.optString("shortAddress");
                this.bg_pic = json.optString("bg_pic");
                this.distance = json.optString("distance");
                this.business_time = json.optString("business_time");
                this.join_info = json.optString("join_info");
            }
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDistrict() {
            return district;
        }

        public String getShortAddress() {
            return shortAddress;
        }

        public String getBg_pic() {
            return bg_pic;
        }

        public String getDistance() {
            return distance;
        }

        public String getBusiness_time() {
            return business_time;
        }

        public String getJoinInfo() {
            return join_info;
        }
    }

}
