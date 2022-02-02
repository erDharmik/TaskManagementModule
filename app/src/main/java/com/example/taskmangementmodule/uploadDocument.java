
package com.example.taskmangementmodule;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class uploadDocument extends AppCompatActivity {

    Button browse , upload;
    EditText caption;
    String encodedpdf;
    String str,username;
    private static final String url = "http://192.168.154.249/crudapi/pdfupload.php";
    ActivityResultLauncher<String> getAction;

   private static final int Req_Code = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);

        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        str = getShared.getString("commentId", "id");

        SharedPreferences getusername = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username = getusername.getString("email", "user");

        browse = findViewById(R.id.browseDocument);
        upload = findViewById(R.id.uploadDocument);
        caption = findViewById(R.id.pdfCaption);

        getAction = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {


                try {

                    InputStream inputStream = getContentResolver().openInputStream(result);
                    byte[] pdfInBytes = new byte[inputStream.available()];
                    inputStream.read(pdfInBytes);
                    //encoded pdf in string
                    encodedpdf = Base64.encodeToString(pdfInBytes, Base64.DEFAULT);



                } catch (Exception ex) {


                }
            }
        });
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                getAction.launch("application/*");

                }
            });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploaddatatodb();
            }
        });


    }

    public void uploaddatatodb(){
        String pdfcaption = caption.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString() ,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> param = new HashMap<String , String>();
                param.put("pdf" , encodedpdf);
                param.put("taskid" , str);
                param.put("email" , username);
                param.put("caption" , pdfcaption);

                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
