package com.yourbcabus.yourbcabus_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BusAdapter adapter;

    List<BusModel> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 10; i++) {
            busList.add(
                    new BusModel(
                            "Lorem ipsum",
                            "Z" + i,
                            "2018-11-17T05:00:00.000Z"
                    )
            );
        }

        adapter = new BusAdapter(this, busList);
        recyclerView.setAdapter(adapter);
    }
}
