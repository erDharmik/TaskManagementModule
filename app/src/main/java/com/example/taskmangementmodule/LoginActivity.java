package com.example.taskmangementmodule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.nio.charset.StandardCharsets;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LoginActivity  extends AppCompatActivity {

    String[] items = {"Admin", "Faculty", "Student"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    Button loginbtn;

    private EditText etEmail, etPassword;
    private String email, password;
    private final String url = "http://192.168.154.249/crudapi/loginuser.php";
    public String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = password = "";
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginbtn = findViewById(R.id.loginbutton);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginuser();
            }
        });

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        adapter = new ArrayAdapter<String>(this, R.layout.dropdown_list_item, items);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
            }
        });


    }

    public void loginuser() {

        String role = item;
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

//        final int min = 111111111;
//        final int max = 999999999;
//        int random = new Random().nextInt((max - min) + 1) + min;
//        String code = String.valueOf(random);

        Long tsLong = System.currentTimeMillis();
        String code = tsLong.toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Enter Your Username First", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Enter Your Password First", Toast.LENGTH_SHORT).show();
        }
        else {
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("true")) {


                                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                myEdit.putBoolean("LoggedIn", true);
                                myEdit.putString("code", code);
                                myEdit.putString("email", email);
                                myEdit.putString("role", role);
                                myEdit.apply();

                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                Toast.makeText(LoginActivity.this, "Welcome "+ email, Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("code", code);
                    params.put("role", role);
                    params.put("username", email);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
