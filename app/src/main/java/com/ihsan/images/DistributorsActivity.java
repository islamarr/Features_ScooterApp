package com.ihsan.images;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistributorsActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity4";

    TextView selectedTxt;
    String selectedGov, selectedCity, procurationId;

    RecyclerView distList;
    DistributorAdapter distributorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        selectedTxt = findViewById(R.id.selectedTxt);
        distList = findViewById(R.id.distList);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            selectedGov = extras.getString("governorate");
            selectedCity = extras.getString("city");
            procurationId = extras.getString("procuration_id", "null");

        }

        ArrayList<DistributorModel> distArr = new ArrayList<>();
        if (!procurationId.equals("null")) {
            ArrayList<DistributorModel> distModel = getListFromJson("json/distributor.json", "procuration_id");
            for (DistributorModel dist : distModel) {
                if (selectedCity.equals(dist.getCityName())) {
                    if (procurationId.equals(dist.getProId())) {
                        distArr.add(dist);
                    }
                }
            }
        } else {
            ArrayList<DistributorModel> serviceModel = getListFromJson("json/services.json", "service_id");
            for (DistributorModel serv : serviceModel) {
                if (selectedCity.equals(serv.getCityName())) {
                    if (!serv.getProId().equals("0")) {
                        distArr.add(serv);
                    }
                }
            }
        }

        distList.setLayoutManager(new LinearLayoutManager(this));
        distributorAdapter = new DistributorAdapter(this, distArr);
        distList.setAdapter(distributorAdapter);

    }



    public ArrayList<DistributorModel> getListFromJson(String jsonFile, String providerId) {
        try {
            JSONObject obj = new JSONObject(Utils.loadJSONFromAsset(DistributorsActivity.this, jsonFile));
            JSONArray jArray = obj.getJSONArray("data");
            ArrayList<DistributorModel> itemList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo_inside = jArray.getJSONObject(i);
                String id = jo_inside.getString("id");
                String cityName = jo_inside.getString("city_name");
                String proId = jo_inside.getString(providerId);
                String latLong = jo_inside.getString("latLong");
                String phone = jo_inside.getString("phone");
                String fbLink = jo_inside.getString("fbLink");
                String imgRes = jo_inside.getString("imgRes");

                itemList.add(new DistributorModel(id, cityName, proId, fbLink, imgRes, phone, latLong));
            }
            return itemList;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}