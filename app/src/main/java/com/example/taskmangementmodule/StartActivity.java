package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String code = sharedPreferences.getString("code","00000000");
        String email = sharedPreferences.getString("email","gmail");
        String role = sharedPreferences.getString("role","User");
        boolean LoggedIn = sharedPreferences.getBoolean("LoggedIn", false);

        if (LoggedIn){
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/validateuser.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("true")) {
                                Toast.makeText(StartActivity.this, "Welcome, " + email, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.clear();
                                myEdit.apply();
                                Toast.makeText(StartActivity.this,response, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(StartActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("code", code);
                    params.put("role",role);
                    params.put("username", email);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(StartActivity.this);
            requestQueue.add(request);

        }
        else{
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}