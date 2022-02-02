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

public class updateuser extends AppCompatActivity {

    EditText uname, ucontact, uemail;
    TextView uid;
    Button userupdatebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateuser);

        uname =findViewById(R.id.updateusername);
        uemail =findViewById(R.id.updateuseremail);
        ucontact =findViewById(R.id.updateusercontact);
        uid = findViewById(R.id.updateuserid);

        userupdatebutton = findViewById(R.id.updateuserbtn);

        String id = getIntent().getExtras().getString("passuserid");
        uid.setText(id);
        String name = getIntent().getExtras().getString("passusername");
        uname.setText(name);
        String email = getIntent().getExtras().getString("passuseremail");
        uemail.setText(email);
        String contact = getIntent().getExtras().getString("passusercontact");
        ucontact.setText(contact);

        userupdatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
            }
        });

    }

    public void updateUserInfo(){

        final String id = uid.getText().toString().trim();
        final String name = uname.getText().toString().trim();
        final String email = uemail.getText().toString().trim();
        final String contact = ucontact.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else if (email.isEmpty()) {
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
        }
        else if(contact.isEmpty()){
            Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
        }

        else {

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/updateUser.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Success")) {
                                Toast.makeText(updateuser.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(updateuser.this, viewuser.class);
                                startActivity(intent);

                            }
                            else {
                                Toast.makeText(updateuser.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(updateuser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id",id);
                    params.put("name", name);
                    params.put("email", email);
                    params.put("contact", contact);


                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(updateuser.this);
            requestQueue.add(request);
        }
    }

}