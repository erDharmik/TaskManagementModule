package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class addTask extends AppCompatActivity {

    RecyclerView addTask_rcl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        addTask_rcl = findViewById(R.id.t_rcl);
        addTask_rcl.setLayoutManager(new LinearLayoutManager(this));


        processdata();
    }

    public void processdata(){

        StringRequest request = new StringRequest("http://192.168.154.249/Log_In/viewProjectTable.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                model data[] = gson.fromJson(response,model[].class);

                addTask_myAdapter adapter = new addTask_myAdapter(data,getApplicationContext());
                addTask_rcl.setAdapter(adapter);

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


}