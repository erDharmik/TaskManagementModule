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

public class chat extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    String str, username;
    Handler mHandler;
    EditText sendMessage;
    RecyclerView rcl;
    ImageView send;
    ImageView attachement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//auto refresh
        this.mHandler = new Handler();
        m_Runnable.run();


        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        str = getShared.getString("commentId", "id");

        SharedPreferences getusername = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username = getusername.getString("email", "user");


        rcl = findViewById(R.id.rcl);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        rcl.setLayoutManager(linearLayoutManager);


        attachement = findViewById(R.id.attachement);

        attachement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(chat.this, view);
                popupMenu.setOnMenuItemClickListener(chat.this);
                popupMenu.inflate(R.menu.attachement_menu);
                popupMenu.show();

            }
        });


        send = findViewById(R.id.send);

        viewMessage();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendmess();

            }
        });

    }

    private final Runnable m_Runnable = new Runnable() {
        public void run() {

            viewMessage();
            chat.this.mHandler.postDelayed(m_Runnable, 2000);
        }

    };

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

    private void sendmess() {


        sendMessage = findViewById(R.id.sendMessage);

        final String message = sendMessage.getText().toString();
        if (message.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty message can't send", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/Log_In/sendComment.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    sendMessage.setText("");
                    Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<String, String>();
                    param.put("comment", message);
                    param.put("taskId", str);
                    param.put("username", username);
                    return param;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(request);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_slide_in_up, R.anim.back_slide_out_up);
        Intent intent = new Intent(getApplicationContext(),dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.attache_image:
               startActivity(new Intent(getApplicationContext(),uploadImage.class));
                return true;

            case R.id.attache_document:

                startActivity(new Intent(getApplicationContext(),uploadDocument.class));
                return true;

            default:
                return false;
        }
    }


}
