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

public class particular_branch extends AppCompatActivity {

    TextView branchid , branchcode, branchname, branchdesc;
    Button branchupdate_button, branchdelete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.particular_branch);

        branchid = findViewById(R.id.particularbranchid);
        branchname = findViewById(R.id.particularbranchname);
        branchcode = findViewById(R.id.particularbranchcode);
        branchdesc = findViewById(R.id.particularbranchdesc);

        branchupdate_button = findViewById(R.id.branchupdatebtn);
        branchdelete_button = findViewById(R.id.branchdeletebtn);

        branchid.setText(getIntent().getStringExtra("branch_id"));
        branchname.setText(getIntent().getStringExtra("branch_name"));
        branchcode.setText(getIntent().getStringExtra("branch_code"));
        branchdesc.setText(getIntent().getStringExtra("branch_desc"));

        branchupdate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = branchid.getText().toString();
                String name = branchname.getText().toString();
                String code = branchcode.getText().toString();
                String desc = branchdesc.getText().toString();


                    Intent intent = new Intent(getApplicationContext(), updatebranch.class);
                intent.putExtra("brnid", id);
                intent.putExtra("brnname", name);
                intent.putExtra("brncode", code);
                intent.putExtra("brndesc", desc);

                startActivity(intent);
            }
        });


        branchdelete_button.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {

                                                       String id = branchid.getText().toString();


                                                       StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/deletebranchinfo.php",
                                                               new Response.Listener<String>() {

                                                                   @Override
                                                                   public void onResponse(String response) {

                                                                       if (response.equalsIgnoreCase("Success")) {
                                                                           Toast.makeText(particular_branch.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                                                           Intent intent = new Intent(particular_branch.this, viewbranch.class);
                                                                           startActivity(intent);

                                                                       } else {
                                                                           Toast.makeText(particular_branch.this, response, Toast.LENGTH_SHORT).show();
                                                                       }

                                                                   }
                                                               }, new Response.ErrorListener() {
                                                           @Override
                                                           public void onErrorResponse(VolleyError error) {
                                                               Toast.makeText(particular_branch.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                       ) {
                                                           @Override
                                                           protected Map<String, String> getParams() throws AuthFailureError {

                                                               Map<String, String> params = new HashMap<String, String>();
                                                               params.put("id", id);
                                                               return params;
                                                           }
                                                       };

                                                       RequestQueue requestQueue = Volley.newRequestQueue(particular_branch.this);
                                                       requestQueue.add(request);
                                                   }
                                               }
        );

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),viewbranch.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}