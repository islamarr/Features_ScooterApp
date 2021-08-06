package com.ihsan.images;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooseLocationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner citiesSpinner, governoratesSpinner;
    TextView procurationIdTxt;
    Button doneBtn;
    String selectedGov, selectedCity, procurationId;
    ArrayList<String> governoratesId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        doneBtn = findViewById(R.id.doneBtn);
        citiesSpinner = findViewById(R.id.citiesSpinner);
        governoratesSpinner = findViewById(R.id.governoratesSpinner);
        procurationIdTxt = findViewById(R.id.procurationId);

        citiesSpinner.setOnItemSelectedListener(this);
        governoratesSpinner.setOnItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            procurationId = extras.getString("procuration_id");
        }

        ArrayList<String> governorates = new ArrayList<>();
        governoratesId = new ArrayList<>();
        ArrayList<ItemModel> allGovernorates = Utils.getListFromJson(this,"json/governorates.json", "procuration_id", "");
        for (ItemModel governorate : allGovernorates) {
            if (governorate.getColumn1().contains(procurationId)) {
                governorates.add(governorate.getItemName());
                governoratesId.add(governorate.getId());
            }
        }

        ArrayAdapter arrayAdapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, governorates);
        arrayAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        governoratesSpinner.setAdapter(arrayAdapter3);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseLocationActivity.this, DistributorsActivity.class);
                intent.putExtra("governorate",selectedGov);
                intent.putExtra("city", selectedCity);
                intent.putExtra("procuration_id", procurationId);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.governoratesSpinner) {
            selectedGov = parent.getItemAtPosition(position).toString();
            getCities(governoratesId.get(position));
        } else if (parent.getId() == R.id.citiesSpinner) {
            selectedCity = parent.getItemAtPosition(position).toString();
        }

    }

    private void getCities(String governorateId) {
        ArrayList<String> cities = new ArrayList<>();
        ArrayList<ItemModel> allCities = Utils.getListFromJson(this, "json/cities.json", "governorate_id", "procuration_id");
        for (ItemModel city : allCities) {
            if (city.getColumn1().equals(governorateId) && city.getColumn2().contains(procurationId))
                cities.add(city.getItemName());
        }
        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(arrayAdapter2);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

}