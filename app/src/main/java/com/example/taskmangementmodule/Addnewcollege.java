package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import android.os.Bundle;

public class Addnewcollege extends AppCompatActivity {

    EditText textcollegename , textcollegecode, textcollegedescription;
    Button addcollegebutton;
    private final String url = "http://192.168.154.249/crudapi/addclg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewcollege);

        textcollegename = findViewById(R.id.collegename);
        textcollegecode = findViewById(R.id.collegecode);
        textcollegedescription = findViewById(R.id.addcollegedescription);

        addcollegebutton = findViewById(R.id.addcollegebtn);

        addcollegebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addcollegedata();
            }
        });

    }

    public void addcollegedata(){

        String name = textcollegename.getText().toString();
        String code = textcollegecode.getText().toString();
        String description = textcollegedescription.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter College", Toast.LENGTH_SHORT).show();
        }
        else if (code.isEmpty()) {
            Toast.makeText(this, "Enter College Code", Toast.LENGTH_SHORT).show();
        }
        else{

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("true")) {
                                Toast.makeText(Addnewcollege.this, "College Added Successfully!!", Toast.LENGTH_SHORT).show();
                                textcollegename.setText("");
                                textcollegecode.setText("");
                                textcollegedescription.setText("");

                            }
                            else {
                                Toast.makeText(Addnewcollege.this, response.toString(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Addnewcollege.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("code",code);
                    params.put("name", name);
                    params.put("description", description);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Addnewcollege.this);
            requestQueue.add(request);
        }
    }

}