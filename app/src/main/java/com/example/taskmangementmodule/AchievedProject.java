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

public class AchievedProject extends AppCompatActivity {

    RecyclerView rcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieved_project);

        rcl = (RecyclerView) findViewById(R.id.achievedproject_rcl);
        rcl.setLayoutManager(new LinearLayoutManager(this));

        processdata();

    }

    private void processdata(){

        StringRequest request = new StringRequest("http://192.168.154.249/crudapi/viewAchievedProjectTable.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                achievedmodel achievedmodeldata[] = gson.fromJson(response,achievedmodel[].class);

                achievedproject_myAdapter adapter = new achievedproject_myAdapter(achievedmodeldata ,getApplicationContext());
                rcl.setAdapter(adapter);

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