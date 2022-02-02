package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class viewuser extends AppCompatActivity {

    RecyclerView Viewuserrcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuser);

        Viewuserrcl = findViewById(R.id.viewuser_rcl);
        Viewuserrcl.setLayoutManager(new LinearLayoutManager(this));

        fetchUserdata();
    }

    private void fetchUserdata() {
        StringRequest request = new StringRequest("http://192.168.154.249/crudapi/fetchUsers.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                GsonBuilder builder = new GsonBuilder();
                Gson gson =builder.create();
                usermodel userdata[] = gson.fromJson(response,usermodel[].class);

                viewuser_myAdapter adapter = new viewuser_myAdapter(userdata,getApplicationContext());
                Viewuserrcl.setAdapter(adapter);

            }}, new Response.ErrorListener() {
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
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
