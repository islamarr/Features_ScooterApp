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
            procurationId = extras.getString("procuration_id");

            //selectedTxt.setText(procurationId+"\n\n"+selectedCity +", "+selectedGov);
        }

        ArrayList<DistributorModel> distModel = getListFromJson("json/distributor.json");
        ArrayList<DistributorModel> distArr = new ArrayList<>();
        for (DistributorModel dist : distModel) {
            if (procurationId.equals(dist.getProcId()) && selectedCity.equals(dist.getCityName())) { //TODO
                distArr.add(dist);
            }
        }

        distList.setLayoutManager(new LinearLayoutManager(this));
        distributorAdapter = new DistributorAdapter(this, distArr);
        distList.setAdapter(distributorAdapter);

    }



    public ArrayList<DistributorModel> getListFromJson(String jsonFile) { //TODO
        try {
            JSONObject obj = new JSONObject(Utils.loadJSONFromAsset(DistributorsActivity.this, jsonFile));
            JSONArray jArray = obj.getJSONArray("data");
            ArrayList<DistributorModel> itemList = new ArrayList<>();
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jo_inside = jArray.getJSONObject(i);
                String id = jo_inside.getString("id");
                String cityName = jo_inside.getString("city_name");
                String procId = jo_inside.getString("procuration_id");
                String latLong = jo_inside.getString("latLong");
                String phone = jo_inside.getString("phone");
                String fbLink = jo_inside.getString("fbLink");
                String imgRes = jo_inside.getString("imgRes");

                itemList.add(new DistributorModel(id, cityName, procId, fbLink, imgRes, phone, latLong));
            }
            return itemList;
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}