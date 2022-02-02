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
public class updatebranch extends AppCompatActivity {

    EditText branchname, branchcode, branchdesc;
    TextView branchid;
    Button updatebranchbtninfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatebranch);

        branchid = findViewById(R.id.branchidupdate);
        branchname = findViewById(R.id.branch_name_update);
        branchcode = findViewById(R.id.branch_code_update);
        branchdesc = findViewById(R.id.branch_desc_update);


        updatebranchbtninfo = findViewById(R.id.updatebranchinfobtn);

        String id = getIntent().getExtras().getString("brnid");
        branchid.setText(id);
        String name = getIntent().getExtras().getString("brnname");
        branchname.setText(name);
        String code = getIntent().getExtras().getString("brncode");
        branchcode.setText(code);
        String desc = getIntent().getExtras().getString("brndesc");
        branchdesc.setText(desc);


        updatebranchbtninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatebranchinformation();
            }
        });


    }
    public void updatebranchinformation(){

        final String id = branchid.getText().toString().trim();
        final String name = branchname.getText().toString().trim();
        final String code = branchcode.getText().toString().trim();
        final String desc = branchdesc.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter College Name", Toast.LENGTH_SHORT).show();
        }
        else if (code.isEmpty()) {
            Toast.makeText(this, "Enter College Code", Toast.LENGTH_SHORT).show();
        }
        else{


            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/updateBranchinfo.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Success")) {
                                Toast.makeText(updatebranch.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(updatebranch.this, viewbranch.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(updatebranch.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(updatebranch.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

            RequestQueue requestQueue = Volley.newRequestQueue(updatebranch.this);
            requestQueue.add(request);

        }

    }
}
