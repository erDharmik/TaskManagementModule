package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class updateclginfo extends AppCompatActivity {

    TextView collegeidupdate;
    EditText clgnameupdate, clgcodeupdate, clgdescupdate;
    Button clgdataupdatebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateclginfo);

        collegeidupdate = findViewById(R.id.clgidupdate);
        clgnameupdate = findViewById(R.id.clg_name_update);
        clgcodeupdate = findViewById(R.id.clg_code_update);
        clgdescupdate = findViewById(R.id.clg_desc_update);

        clgdataupdatebtn = findViewById(R.id.updatecollegeinfobtn);

        String id = getIntent().getExtras().getString("clgid");
        collegeidupdate.setText(id);
        String name = getIntent().getExtras().getString("clgname");
        clgnameupdate.setText(name);
        String code = getIntent().getExtras().getString("clgcode");
        clgcodeupdate.setText(code);
        String desc = getIntent().getExtras().getString("clgdesc");
        clgdescupdate.setText(desc);


        clgdataupdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateclginformation();
            }
        });


    }

    public void updateclginformation(){

        final String id = collegeidupdate.getText().toString().trim();
        final String name = clgnameupdate.getText().toString().trim();
        final String code = clgcodeupdate.getText().toString().trim();
        final String desc = clgdescupdate.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter College Name", Toast.LENGTH_SHORT).show();
        }
        else if (code.isEmpty()) {
            Toast.makeText(this, "Enter College Code", Toast.LENGTH_SHORT).show();
        }
        else{


            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/updateCollegeinfo.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Success")) {
                                Toast.makeText(updateclginfo.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(updateclginfo.this, viewcollege.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(updateclginfo.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(updateclginfo.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id",id);
                    params.put("name", name);
                    params.put("code", code);
                    params.put("description", desc);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(updateclginfo.this);
            requestQueue.add(request);

        }

    }

}