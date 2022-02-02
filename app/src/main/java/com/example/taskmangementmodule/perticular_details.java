package com.example.taskmangementmodule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.Map;

public class perticular_details extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {



    TextView pid,ptitle,pdesc,pstartdate,penddate,pleader,pmember;
    TextView comment_open;
    String[] items = {"0%" , "25%" , "50%" , "75%" , "100%"};
    String item;
    String progressId;
    ImageView menu;


    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perticular_details);
//permission for pdf generater.............................................................................
        ActivityCompat.requestPermissions(this , new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE} , PackageManager.PERMISSION_GRANTED);
//........................................................................................................


//menu popup..................................................................................................

        menu = findViewById(R.id.perticular_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(perticular_details.this, view);
                popupMenu.setOnMenuItemClickListener(perticular_details.this);      //on click listner method in last
                popupMenu.inflate(R.menu.perticular_menu);
                popupMenu.show();
            }
        });
//...................................................................................................................


        comment_open = findViewById(R.id.txtcomment);
        comment_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), chat.class));

                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });


        autoCompleteTextView = findViewById(R.id.auto_complete_progress);

        adapterItem = new ArrayAdapter<String>(this, R.layout.drapdownforprogress, items);
        autoCompleteTextView.setAdapter(adapterItem);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                item = parent.getItemAtPosition(position).toString();
                progressId = pid.getText().toString();


                StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/Log_In/insertProgress.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();


                    }
                }) {
                    @Override

                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<String, String>();
                        param.put("item", item);
                        param.put("id", progressId);


                        return param;
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(request);

            }
        });


        pid = findViewById(R.id.p_id);
        ptitle = findViewById(R.id.p_title);
        pdesc = findViewById(R.id.p_desc);
        pstartdate = findViewById(R.id.p_startdate);
        penddate = findViewById(R.id.p_enddate);
        pleader = findViewById(R.id.p_leader);
        pmember = findViewById(R.id.p_member);


        pid.setText(getIntent().getStringExtra("id"));
        ptitle.setText(getIntent().getStringExtra("title"));
        pdesc.setText(getIntent().getStringExtra("description"));
        pstartdate.setText(getIntent().getStringExtra("startDate"));
        penddate.setText(getIntent().getStringExtra("endDate"));
        pleader.setText(getIntent().getStringExtra("leader"));
        pmember.setText(getIntent().getStringExtra("member"));


    }

    public void completeproject(){
        String id = pid.getText().toString().trim();
        String title = ptitle.getText().toString().trim();
        String  desc = pdesc.getText().toString().trim();
        String  start = pstartdate.getText().toString().trim();
        String  end = penddate.getText().toString().trim();
        String  leader = pleader.getText().toString().trim();
        String member  = pmember.getText().toString().trim();

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/completeProject.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("true")) {
                            Toast.makeText(perticular_details.this, "Project Achieved Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(perticular_details.this, AchievedProject.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(perticular_details.this, response, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(perticular_details.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("description", desc);
                params.put("startdate", start);
                params.put("enddate", end);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(perticular_details.this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }


    private void deleteproject() {

            String id = pid.getText().toString();

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.154.249/crudapi/deleteProject.php",
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("true")) {
                                Toast.makeText(perticular_details.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(perticular_details.this, viewcollege.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(perticular_details.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(perticular_details.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params= new HashMap<String, String>();
                    params.put("id", id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(perticular_details.this);
            requestQueue.add(request);
        }

    private void reportgenerate() {
        PdfDocument pdfDocument = new PdfDocument();
        //bolde text
        Paint paint1 = new Paint();
        paint1.setTypeface(Typeface.create("Arial", Typeface.BOLD));

        //normal text
        Paint paint = new Paint();

        PdfDocument.PageInfo pageInfo1 = new PdfDocument.PageInfo.Builder(420, 594,1).create();
        PdfDocument.Page page1 = pdfDocument.startPage(pageInfo1);
        Canvas canvas1 = page1.getCanvas();
        canvas1.drawText( getIntent().getStringExtra("title"),180 ,10,paint1);


        canvas1.drawText("Project Id :" + getIntent().getStringExtra("id") , 10,50,paint);
        canvas1.drawText("Project Title :"  + getIntent().getStringExtra("title"), 10,70,paint);
        canvas1.drawText("Project Leader :" + getIntent().getStringExtra("leader") , 10,90,paint);
        canvas1.drawText("Project Member:" + getIntent().getStringExtra("member"), 10,110,paint);
        canvas1.drawText("Description :" + getIntent().getStringExtra("description") , 10,130,paint);
        canvas1.drawText("Start Date :" + getIntent().getStringExtra("startDate") , 10,150,paint);
        canvas1.drawText("Due Date :" + getIntent().getStringExtra("endDate") , 10,170,paint);



        pdfDocument.finishPage(page1);



        try {

            File fileLocation = new File(createDirectory("Report"),"project.pdf");

            FileOutputStream fos = new FileOutputStream(fileLocation);
            pdfDocument.writeTo(fos);

            Toast.makeText(this,createDirectory("Report").getPath() , Toast.LENGTH_SHORT).show();


        }
        catch (Exception e){e.printStackTrace();}

    }


//folder create in download directory............................................................................
    private File createDirectory(String dirName){
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);

        File file = new File(path, "Report");
        if(!file.exists()){
            file.mkdir();
        }
        return file;
    }

//.....................................................................................................................
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {


            case R.id.perticular_report:

                reportgenerate();


                return true;

            case R.id.perticular_delet:

                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(perticular_details.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to Delete ?");
                builder.setTitle("Delete Project!");

                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        deleteproject();
                    }
                });


                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();

                return true;

            case R.id.perticular_update:

                String id = pid.getText().toString();
                String name = ptitle.getText().toString();
                String startdate = pstartdate.getText().toString();
                String enddate = penddate.getText().toString();
                String desc = pdesc.getText().toString();
                String leader = pleader.getText().toString();
                String member = pmember.getText().toString();

                Intent i = new Intent(getApplicationContext(), updateproject.class);
                i.putExtra("id", id);
                i.putExtra("name", name);
                i.putExtra("startdate", startdate);
                i.putExtra("enddate", enddate);
                i.putExtra("desc", desc);
                i.putExtra("leader", leader);
                i.putExtra("member", member);

                startActivity(i);
                return true;

            case R.id.perticular_Complete:
                completeproject();
                return true;

            default:
                return false;
        }
    }



}