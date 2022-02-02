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

public class updateproject extends AppCompatActivity {

    EditText utitle, uleader, umember, udescription, ustartdate, uenddate;
    TextView uid;
    Button updatebutton;
    String id, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateproject);

        updatebutton = findViewById(R.id.updateprojectbtn);

        uid = findViewById(R.id.textid);
        utitle = (EditText) findViewById(R.id.project);
        uleader = findViewById(R.id.leader);
        umember = findViewById(R.id.member);
        udescription = findViewById(R.id.description);
        ustartdate = findViewById(R.id.datestart);
        uenddate = findViewById(R.id.dateend);

        String id = getIntent().getExtras().getString("id");
        uid.setText(id);
        String title = getIntent().getExtras().getString("name");
        utitle.setText(title);
        String leader = getIntent().getExtras().getString("leader");
        uleader.setText(leader);
        String member = getIntent().getExtras().getString("member");
        umember.setText(member);
        String desc = getIntent().getExtras().getString("desc");
        udescription.setText(desc);
        String start = getIntent().getExtras().getString("startdate");
        ustartdate.setText(start);
        String end = getIntent().getExtras().getString("enddate");
        uenddate.setText(end);


        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProjectinfo();
            }
        });

    }

    public void updateProjectinfo() {
        final String id = uid.getText().toString().trim();
        final String title = utitle.getText().toString().trim();
        final String leader = uleader.getText().toString().trim();
        final String member = umember.getText().toString().trim();
        final String description = udescription.getText().toString().trim();
        final String startdate = ustartdate.getText().toString().trim();
        final String enddate = uenddate.getText().toString().trim();




        if (title.isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
        }
        else if (leader.isEmpty()) {
            Toast.makeText(this, "Enter Leader Name", Toast.LENGTH_SHORT).show();
        }
        else if(member.isEmpty()){
            Toast.makeText(this, "Enter Members", Toast.LENGTH_SHORT).show();
        }
        else if(description.isEmpty()){
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        }
        else if(startdate.isEmpty()){
            Toast.makeText(this, "Enter Start Date", Toast.LENGTH_SHORT).show();
        }
        else if(enddate.isEmpty()){
            Toast.makeText(this, "Enter End Date", Toast.LENGTH_SHORT).show();
        }

        else {

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/updateProject.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Success")) {
                                Toast.makeText(updateproject.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(updateproject.this, viewProjectTable.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(updateproject.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(updateproject.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id",id);
                    params.put("title", title);
                    params.put("leader", leader);
                    params.put("member", member);
                    params.put("description", description);
                    params.put("startdate", startdate);
                    params.put("enddate", enddate);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(updateproject.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(updateproject.this, viewProjectTable.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}