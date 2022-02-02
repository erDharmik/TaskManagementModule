package com.example.taskmangementmodule;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddNewBranch extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String url = "http://192.168.154.249/crudapi/addbranch.php";
    EditText branchname , branchcode, branchdesc;
    Button add_new_branch;

    String SelectedCollege;
    Spinner spinnercollege;
    ArrayList<String> clglist = new ArrayList<>();
    ArrayAdapter<String> clgAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_branch);

        branchname = findViewById(R.id.branchname);
        branchcode = findViewById(R.id.branchcode);
        add_new_branch = findViewById(R.id.branch_add_btn);
        branchdesc = findViewById(R.id.branchdescription);

        requestQueue = Volley.newRequestQueue(this);
        spinnercollege = findViewById(R.id.SpinnerCollege);
        String url = "http://192.168.154.249/crudapi/spinner/college.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("college");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String collegename = jsonObject.optString("name");
                        clglist.add(collegename);
                        clgAdapter = new ArrayAdapter<>(AddNewBranch.this,
                                android.R.layout.simple_spinner_item, clglist);
                        clgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnercollege.setAdapter(clgAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
//        spinnerCountry.setOnItemSelectedListener(this);
        spinnercollege.setOnItemSelectedListener(this);




        add_new_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addbranch();
            }
        });



    }

    private void addbranch() {

        final String clg = SelectedCollege;
        final String name = branchname.getText().toString().trim();
        final String code = branchcode.getText().toString().trim();
        final String desc = branchdesc.getText().toString().trim();


        if (name.isEmpty()) {
            Toast.makeText(this, "Enter Branch name", Toast.LENGTH_SHORT).show();
        }
        else if (code.isEmpty()) {
            Toast.makeText(this, "Enter Branch code", Toast.LENGTH_SHORT).show();
        }

        else {
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("true")) {
                                Toast.makeText(AddNewBranch.this, "Branch Added Successfully!!", Toast.LENGTH_SHORT).show();
                                branchname.setText("");
                                branchcode.setText("");
                                branchdesc.setText("");

                            }
                            else {
                                Toast.makeText(AddNewBranch.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AddNewBranch.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("clg", clg);
                    params.put("code",code);
                    params.put("name", name);
                    params.put("desc", desc);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(AddNewBranch.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        SelectedCollege = adapterView.getSelectedItem().toString();
        Toast.makeText(AddNewBranch.this, SelectedCollege, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}