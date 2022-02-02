package com.example.taskmangementmodule;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
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


import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class pdfRetrive extends AppCompatActivity {



    String getCommentId,pdfName;



    private byte[] pdfInBytes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_retrive);


        getCommentId = getIntent().getExtras().getString("commentId");
        pdfName = getIntent().getExtras().getString("pdfName");


        downloadDocument();





            }

//create directory in download folder.............................................................

    private File createDirectory(String dirName){
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);

        File file = new File(path, "Task");
        if(!file.exists()){
            file.mkdir();
        }
     return file;
    }
    private void save() throws IOException {


        try {

            File fileLocation = new File(createDirectory("demo"),pdfName);

            FileOutputStream fos = new FileOutputStream(fileLocation);
            fos.write(pdfInBytes);
            fos.flush();
            fos.close();

            Toast.makeText(this,createDirectory("demo").getPath() +"/" +pdfName , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),chat.class));


        }
        catch (Exception e){e.printStackTrace();}


    }

    public boolean isStoragePermissionGranted() {
        String TAG = "Storage Permission";
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    private void downloadDocument() {



        StringRequest request = new StringRequest(Request.Method.POST,"http://192.168.154.249/crudapi/pdfFetch.php", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//pdf decode and save it to variable

                 pdfInBytes = Base64.decode(response.trim(), Base64.DEFAULT);

    //save fun
                if (isStoragePermissionGranted()){
                    try {
                        save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<String, String>();
                data.put("commentId", getCommentId);
                return data;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);


    }

}