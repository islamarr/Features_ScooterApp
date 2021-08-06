package com.ihsan.images;

import android.content.Context;

import com.ihsan.images.faq.FaqModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Utils {

    public static ArrayList<ItemModel> getListFromJson(Context context, String jsonFile, String column1, String column2) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, jsonFile));
            JSONArray jArray = obj.getJSONArray("data");
            ArrayList<ItemModel> itemList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo_inside = jArray.getJSONObject(i);
                String item = jo_inside.getString("name_ar");
                String id = jo_inside.getString("id");
                String col1 = column1.equals("") ? "" : jo_inside.getString(column1);
                String col2 = column2.equals("") ? "" : jo_inside.getString(column2);
                itemList.add(new ItemModel(id, item, col1, col2));
            }
            return itemList;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public static String loadJSONFromAsset(Context context, String jsonFile) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static ArrayList<FaqModel> getListFromJson(Context context, String jsonFile) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(context, jsonFile));
            JSONArray jArray = obj.getJSONArray("data");
            ArrayList<FaqModel> itemList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo_inside = jArray.getJSONObject(i);
                String item = jo_inside.getString("text");
                String id = jo_inside.getString("id");
                itemList.add(new FaqModel(id, item));
            }
            return itemList;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
