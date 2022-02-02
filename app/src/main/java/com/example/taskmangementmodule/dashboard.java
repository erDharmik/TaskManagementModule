package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class dashboard extends AppCompatActivity {

    RecyclerView dashboard_rcl;
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard_rcl = findViewById(R.id.d_rcl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        dashboard_rcl.setLayoutManager(linearLayoutManager);

        swipeRefreshLayout = findViewById(R.id.refresh);

        refresh();
        processdata();
    }

    public void refresh(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                processdata();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    } // Refresh stop

    public void processdata(){

        StringRequest request = new StringRequest("http://192.168.154.249/Log_In/viewProjectTable.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                model data[] = gson.fromJson(response,model[].class);

                dashboard_myAdapter adapter = new dashboard_myAdapter(data,getApplicationContext());
                dashboard_rcl.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(dashboard.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}