package com.example.taskmangementmodule;

import  androidx.appcompat.app.AppCompatActivity;
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

public class particular_college extends AppCompatActivity {
    TextView collegeid, collegename, collegecode, collegedesc;
    Button collegeupdatebtn, collegedeletebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.particular_college);

        collegeid = findViewById(R.id.particularcollegeid);
        collegename = findViewById(R.id.particularcollegename);
        collegecode = findViewById(R.id.particularcollegecode);
        collegedesc = findViewById(R.id.particularcollegedesc);

        collegeupdatebtn = findViewById(R.id.collegeupdatebtn);
        collegedeletebtn = findViewById(R.id.collegedeletebtn);

        collegeid.setText(getIntent().getStringExtra("college_id"));
        collegename.setText(getIntent().getStringExtra("college_name"));
        collegecode.setText(getIntent().getStringExtra("college_code"));
        collegedesc.setText(getIntent().getStringExtra("college_desc"));

        collegeupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = collegeid.getText().toString();
                String name = collegename.getText().toString();
                String code = collegecode.getText().toString();
                String desc = collegedesc.getText().toString();

                Intent i = new Intent(getApplicationContext(), updateclginfo.class);
                i.putExtra("clgid", id);
                i.putExtra("clgname", name);
                i.putExtra("clgcode", code);
                i.putExtra("clgdesc", desc);

                startActivity(i);
            }
        });

        collegedeletebtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                String id = collegeid.getText().toString();

                StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/deletecollegeinfo.php",
                        new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("Success")) {
                            Toast.makeText(particular_college.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(particular_college.this, viewcollege.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(particular_college.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(particular_college.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params= new HashMap<String, String>();
                        params.put("id", id);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(particular_college.this);
                requestQueue.add(request);
            }
        }
        );
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),viewcollege.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}