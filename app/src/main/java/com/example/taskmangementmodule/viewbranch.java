package com.example.taskmangementmodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class viewbranch extends AppCompatActivity {

    String[] items = {"GMIT", "GMDC", "GMPC"};
    AutoCompleteTextView autoCompleteBranchview;
    ArrayAdapter<String> adapter;

    private final String url = "http://192.168.154.249/crudapi/viewbranch.php";
    public String item;

    RecyclerView viewbranchrecview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbranch);

        viewbranchrecview = (RecyclerView) findViewById(R.id.viewbranch_rcl);
        viewbranchrecview.setLayoutManager(new LinearLayoutManager(this));

        autoCompleteBranchview = findViewById(R.id.autocompletebranch);

        adapter = new ArrayAdapter<String>(this,R.layout.dropdown_list_item,items);
        autoCompleteBranchview.setAdapter(adapter);

        autoCompleteBranchview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item = parent.getItemAtPosition(position).toString();
                viewbranchparticular();

                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }
        });


    }




    private void viewbranchparticular() {
        StringRequest request = new StringRequest(Request.Method.POST,"http://192.168.154.249/crudapi/viewbranch.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                viewbranchmodel viewbranchmodeldata[] = gson.fromJson(response, viewbranchmodel[].class);

                viewbranchmyAdapter adapter = new viewbranchmyAdapter(viewbranchmodeldata, getApplicationContext());
                viewbranchrecview.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> param = new HashMap<String , String>();
                param.put("clg" , item);
                return param;
            }
        };




        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }
}