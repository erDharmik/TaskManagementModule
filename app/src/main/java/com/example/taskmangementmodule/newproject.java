package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
public class newproject extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerleader;
    Spinner spinnermember;

    final Calendar myCalendar= Calendar.getInstance();

    ArrayList<String> leaderlist = new ArrayList<>();
    ArrayList<String> memberlist = new ArrayList<>();

    ArrayAdapter<String> leaderAdapter;
    ArrayAdapter<String> memberAdapter;

    RequestQueue requestQueue;

//    final Calendar myCalendar= Calendar.getInstance();

    String leaderEdit,memberEdit;

    EditText t1, t2, t3, t6, leaderedit, memberedit, datestart, dateend;
    EditText  t4, t5;
    Button send;
    private final String addApi = "http://192.168.154.249/Log_In/insertTask.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproject);
//date picker

        leaderedit = findViewById(R.id.leaderlist);
        memberedit = findViewById(R.id.memberlist);

        leaderlist.add("Select Leader");
        memberlist.add("Select Member");

        t4=(EditText)findViewById(R.id.datestartnew);
        DatePickerDialog.OnDateSetListener sdate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updatestartLabel();
            }
        };
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(newproject.this,sdate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        t5=(EditText)findViewById(R.id.dateendnew);
        DatePickerDialog.OnDateSetListener edate =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateendLabel();
            }
        };
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(newproject.this,edate,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datestart = findViewById(R.id.datestartnew);
        dateend = findViewById(R.id.dateendnew);

        send = findViewById(R.id.addprojectbtn);

        requestQueue = Volley.newRequestQueue(this);
        spinnerleader = findViewById(R.id.Spinnerleader);
        spinnermember = findViewById(R.id.Spinnermember);

        String url = "http://192.168.154.249/crudapi/spinner/user.php";

        //    Leader Section For Spinner
        JsonObjectRequest leaderrequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("user");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user = jsonObject.optString("name");

                        leaderlist.add(user);

                        leaderAdapter = new ArrayAdapter<>(newproject.this,
                                android.R.layout.simple_spinner_item, leaderlist);
                        leaderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerleader.setAdapter(leaderAdapter);
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
        requestQueue.add(leaderrequest);
        spinnerleader.setOnItemSelectedListener(this);

        //    Member Section For Spinner
        JsonObjectRequest memberrequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("user");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String user = jsonObject.optString("name");

                        memberlist.add(user);
                        memberAdapter = new ArrayAdapter<>(newproject.this,
                                android.R.layout.simple_spinner_item, memberlist);
                        memberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnermember.setAdapter(memberAdapter);
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
        requestQueue.add(memberrequest);
        spinnermember.setOnItemSelectedListener(this);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });

    }

    private void updateendLabel() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.CANADA);
        t5.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updatestartLabel() {
        String myFormat="dd/MM/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.CANADA);
        t4.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void sendData() {

        t1 = findViewById(R.id.titlenew);
//        t2 = findViewById(R.id.membernew);
//        t3 = findViewById(R.id.leadernew);
        t4 = findViewById(R.id.datestartnew);
        t5 = findViewById(R.id.dateendnew);
        t6 = findViewById(R.id.descriptionnew);

        final String title = t1.getText().toString();
//        final String member = t2.getText().toString();
        final String leader = leaderedit.getText().toString();
        final String member = memberedit.getText().toString();
        final String startDate = t4.getText().toString();
        final String dueDate = t5.getText().toString();
        final String description = t6.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, addApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(newproject.this, response.toString(), Toast.LENGTH_SHORT).show();

                t1.setText("");
                t2.setText("");
                t3.setText("");
                t6.setText("");
                datestart.setText("");
                dateend.setText("");

                startActivity(new Intent(newproject.this, dashboard.class));

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
                param.put("title", title);
                param.put("leader", leader);
                param.put("member", member);
                param.put("description", description);
                param.put("start", startDate);
                param.put("end", dueDate);

                return param;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

//        leaderEdit = spinnerleader.getSelectedItem().toString();
//        memberEdit = spinnermember.getSelectedItem().toString();

        leaderEdit = adapterView.getItemAtPosition(i).toString();
        memberEdit = adapterView.getItemAtPosition(i).toString();

        if (adapterView.getId() == R.id.Spinnerleader )
        {
        if (leaderEdit == "Select Leader"){
            leaderedit.setText("");
        }
        else{
            leaderedit.setText(leaderEdit);
        }
        }
        if (adapterView.getId() == R.id.Spinnermember){
        if (memberEdit == "Select Member"){
            memberedit.setText("");
        }
        else{
            memberedit.append(memberEdit + "," + "\n");
        }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(newproject.this, viewProjectTable.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
