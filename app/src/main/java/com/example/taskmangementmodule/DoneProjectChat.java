package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

class DoneProjectChat extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    String str, username;
    Handler mHandler;
    RecyclerView rcl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_project_chat);

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        str = getShared.getString("commentId", "id");

        SharedPreferences getusername = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username = getusername.getString("username", "user");
        viewMessage();

        rcl = findViewById(R.id.donercl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rcl.setLayoutManager(linearLayoutManager);

    }

    private void viewMessage() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/Log_In/commentFetch.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                chat_model data[] = gson.fromJson(response, chat_model[].class);

                chat_myAdapter adapter = new chat_myAdapter(data,getApplicationContext());
                rcl.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<String, String>();
                data.put("taskId", str);
                return data;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_slide_in_up, R.anim.back_slide_out_up);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}