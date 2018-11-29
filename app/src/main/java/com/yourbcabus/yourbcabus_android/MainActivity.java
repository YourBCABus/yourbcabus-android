package com.yourbcabus.yourbcabus_android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://db.yourbcabus.com/schools/5bca51e785aa2627e14db459/buses";

    RecyclerView recyclerView;
    BusAdapter adapter;

    List<BusModel> busList;

    private SwipeRefreshLayout swipeRefreshLayout;
    Handler handler = new Handler();

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBuses();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        loadBuses(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBuses();
        handler.post(callLoadBuses);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(callLoadBuses);
    }

    private Runnable callLoadBuses = new Runnable() {
        @Override
        public void run() {
            loadBuses();
            handler.postDelayed(callLoadBuses, 10*1000);
        }
    };

    private void loadBuses(final boolean firstRun) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        busList.clear();
                        db.deleteAll();
                        try {
                            JSONArray busArray = new JSONArray(response);

                            for (int i = 0; i < busArray.length(); i++) {
                                JSONObject busObject = busArray.getJSONObject(i);

                                String name = busObject.getString("name");
                                String invalidateTime = busObject.getString("invalidate_time");
                                String id = busObject.getString("_id");
                                String location = "";

                                try {
                                    location = busObject.getJSONArray("locations").getString(0);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    location = "?";
                                }

                                BusModel bus = new BusModel(name, location, invalidateTime, id);
                                busList.add(bus);
                                db.addBus(bus);
                            }

                            Collections.sort(busList, new BusComparator());

                            if (firstRun) {
                                adapter = new BusAdapter(MainActivity.this, busList);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        busList = db.getAllBuses();

                        if (firstRun) {
                            adapter = new BusAdapter(MainActivity.this, busList);
                            recyclerView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void loadBuses() {
        loadBuses(false);
    }

    public class BusComparator implements Comparator<BusModel> {
        @Override
        public int compare(BusModel o1, BusModel o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    }
}
