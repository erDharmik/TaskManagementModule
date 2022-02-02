package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class user_info extends AppCompatActivity {


    TextView uname, uemail, uid, ucontact;
    Button newupdatebtn , newdeletebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        uid = findViewById(R.id.user_id);
        uname = findViewById(R.id.user_name);
        uemail = findViewById(R.id.user_email);
        ucontact = findViewById(R.id.user_contact);

        newupdatebtn = findViewById(R.id.userupdatebtn);
        newdeletebtn = findViewById(R.id.userdeletebtn);
        newdeletebtn = findViewById(R.id.userdeletebtn);


        uid.setText(getIntent().getStringExtra("user_id"));
        uname.setText(getIntent().getStringExtra("user_name"));
        uemail.setText(getIntent().getStringExtra("user_email"));
        ucontact.setText(getIntent().getStringExtra("user_contact"));

        newupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = uid.getText().toString();
                String name = uname.getText().toString();
                String email = uemail.getText().toString();
                String contact = ucontact.getText().toString();


                Intent intent = new Intent(getApplicationContext(), updateuser.class);
                intent.putExtra("passuserid", id);
                intent.putExtra("passusername", name);
                intent.putExtra("passuseremail", email);
                intent.putExtra("passusercontact", contact);

                startActivity(intent);
            }
        });

        newdeletebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String id = uid.getText().toString();

                StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/deleteUser.php",
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("Success")) {
                                    Toast.makeText(user_info.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(user_info.this, viewuser.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(user_info.this, response, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(user_info.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("id",id);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(user_info.this);
                requestQueue.add(request);
            }
        });

    }
}