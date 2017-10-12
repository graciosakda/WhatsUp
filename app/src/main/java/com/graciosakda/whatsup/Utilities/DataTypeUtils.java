package com.graciosakda.whatsup.Utilities;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by graciosa.kda on 01/09/2017.
 */

public class DataTypeUtils {
    private static final String LOG_TAG = DataTypeUtils.class.getSimpleName();

    public static Map<Integer, String> objectJsonArrayToMap(Object objectJsonArray){
        Map<Integer, String> data = new HashMap<>();
        try {
            if(objectJsonArray instanceof JSONArray){
                Log.i("JSon", "jsonarray");
                JSONArray jsonArray = (JSONArray) objectJsonArray;
                for(int x = 0; x < jsonArray.length(); x++){
                    JSONObject object = jsonArray.getJSONObject(x);
                    data.put(object.getInt("id"), object.getString("name"));
                    Log.i("JSON",object.getInt("id")+" "+object.getString("name"));
                }
            }
        } catch (JSONException e) {
            Log.i(LOG_TAG, "--- objectJsonArrayToMap --- "+e.getMessage());
        }

        return data;
    }

    @SuppressLint("NewApi")
    public static Integer getHashMapKeyByValue(Map<Integer, String> data, String value){
        Integer key = -1;
        for(Map.Entry entry : data.entrySet()){
            Log.i("DataUtils", entry.getValue()+" "+value);
            if(Objects.equals(value, entry.getValue())){
                Log.i("DataUtilsE", entry.getKey()+" ");
                return (Integer)entry.getKey();
            }else{
                Log.i("DataUtilsE", "NOOOT");
            }
        }
        return key;
    }

    public static JSONObject mapToJsonObject(Map<String, String> params){
        return new JSONObject(params);
    }

    public  static Map<String, String> jsonObjectToMap(JSONObject jsonObject) throws JSONException {
        Map<String, String> map = new HashMap<String, String>();
        Iterator<?> keys = jsonObject.keys();

        while( keys.hasNext() ){
            String key = (String)keys.next();
            String value = jsonObject.getString(key);
            map.put(key, value);
        }
        return map;
    }
}
