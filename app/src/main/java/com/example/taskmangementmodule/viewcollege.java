package com.example.taskmangementmodule;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class viewcollege extends AppCompatActivity {

    RecyclerView viewclgrcl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcollege);

        viewclgrcl = (RecyclerView) findViewById(R.id.viewclg_rcl);
        viewclgrcl.setLayoutManager(new LinearLayoutManager(this));
        viewcollegeparticular();
    }

    private void viewcollegeparticular() {
        StringRequest request = new StringRequest("http://192.168.154.249/crudapi/viewclg.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                viewcollegemodel viewcollegemodeldata[] = gson.fromJson(response,viewcollegemodel[].class);

                viewcollegemyAdapter adapter = new viewcollegemyAdapter(viewcollegemodeldata,getApplicationContext());
                viewclgrcl.setAdapter(adapter);

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