package com.example.taskmangementmodule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class uploadImage extends AppCompatActivity {

    EditText ImageName;
    Button browse , upload;
    ImageView img;
    String encodedImage;
    Bitmap bitmap;
    String caption;
    String str,username;

    private static final String url = "http://192.168.154.249/crudapi/fileupload.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
//for comment id
        SharedPreferences getShared = getSharedPreferences("demo", MODE_PRIVATE);
        str = getShared.getString("commentId", "id");

        SharedPreferences getusername = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        username = getusername.getString("email", "user");


        img = findViewById(R.id.img);
        upload = findViewById(R.id.upload);
        browse = findViewById(R.id.browse);

        //permission

             browse.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Dexter.withContext(uploadImage.this)
                                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                        .withListener(new PermissionListener() {
                                            @Override
                                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                                Intent intent = new Intent(Intent.ACTION_PICK);
                                                intent.setType("image/*");

                                               startActivityForResult(Intent.createChooser(intent , "Browse Image" ),1);
                                            }

                                            @Override
                                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                                            }

                                            @Override
                                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                                permissionToken.cancelPermissionRequest();
                                            }
                                        }).check();
                            }
                        });

         upload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 uploaddatatodb();
             }
         });
        }



//image receive
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri filepath = data.getData();
            try {

                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
                encodeBitMapImage(bitmap);

            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //encode in base64
    private void encodeBitMapImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG , 100 , byteArrayOutputStream);

        byte [] bytesofimage = byteArrayOutputStream.toByteArray();
        encodedImage = android.util.Base64.encodeToString(bytesofimage , Base64.DEFAULT);

    }

    //volley string request
    private void uploaddatatodb(){
        ImageName = findViewById(R.id.caption);

        caption = ImageName.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ImageName.setText("");
                img.setImageResource(R.drawable.ic_launcher_foreground);
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
                param.put("caption" , caption);
                param.put("upload" , encodedImage);
                param.put("taskId" , str);
                param.put("username" , username);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }
}