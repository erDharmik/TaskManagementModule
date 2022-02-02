package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class addUser extends AppCompatActivity {

    String[] items = {"Admin","Faculty"};
    String item;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapter;
    String url = "http://192.168.154.249/Log_In/useradd.php";
    private String myString = "";

    EditText txtName, txtEmail, txtContact, txtPassword;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        autoCompleteTextView = findViewById(R.id.auto_complete_progress);

        txtName = findViewById(R.id.name);
        txtEmail = findViewById(R.id.email);
        txtContact  = findViewById(R.id.contact);
        txtPassword  = findViewById(R.id.password);
        addBtn = findViewById(R.id.addUser);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertUser();
                myString = txtContact.toString();
            }
        });

        adapter = new ArrayAdapter<String>(this,R.layout.dropdown_list_item,items);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item = parent.getItemAtPosition(position).toString();
            }
        });
    }
    private void insertUser() {

        final String name = txtName.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String contact = txtContact.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();


        if (name.isEmpty()) {
            Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {
            Toast.makeText(this, "Enter Leader Name", Toast.LENGTH_SHORT).show();
        }
        else if(contact.isEmpty()){
            Toast.makeText(this, "Enter Members", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Enter Description", Toast.LENGTH_SHORT).show();
        }

        else {

            StringRequest request = new StringRequest(Request.Method.POST, "http:// 192.168.154.249/Log_In/userAdd.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Successfull")) {
                                Toast.makeText(addUser.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                txtName.setText("");
                                txtEmail.setText("");
                                txtContact.setText("");
                                txtPassword.setText("");

                            }
                            else {
                                Toast.makeText(addUser.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(addUser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("role",item);
                    params.put("name", name);
                    params.put("email", email);
                    params.put("contact", contact);
                    params.put("password", password);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(addUser.this);
            requestQueue.add(request);
        }
    }

}