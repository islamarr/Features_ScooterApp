package com.ihsan.images;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.ihsan.images.faq.FAQActivity;
import com.ihsan.images.searchLost.SearchActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView procurationList;
    ProcurationAdapter procurationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        procurationList = findViewById(R.id.procurationList);

        ArrayList<ItemModel> procModel = Utils.getListFromJson(this, "json/procuration.json", "imgRes", "");

        procurationList.setLayoutManager(new LinearLayoutManager(this));
        procurationAdapter = new ProcurationAdapter(this, procModel);
        procurationList.setAdapter(procurationAdapter);


    }
}