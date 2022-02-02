package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class particularAchievedProject extends AppCompatActivity {

    TextView aaid, aatitle, aadesc, aastartdate, aaenddate, aaleader, aamember;
    TextView comment_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_achieved_project);

        aaid = findViewById(R.id.a_id);
        aatitle = findViewById(R.id.a_title);
        aadesc = findViewById(R.id.a_desc);
        aastartdate = findViewById(R.id.a_startdate);
        aaenddate = findViewById(R.id.a_enddate);
        aaleader = findViewById(R.id.a_leader);
        aamember = findViewById(R.id.a_member);

        comment_open = findViewById(R.id.a_comment);

        comment_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),DoneProjectChat.class));

                overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
            }
        });



//        aid.setText(getIntent().getStringExtra("aid"));
//        atitle.setText(getIntent().getStringExtra("atitle"));
//        adesc.setText(getIntent().getStringExtra("adescription"));
//        astartdate.setText(getIntent().getStringExtra("astartDate"));
//        aenddate.setText(getIntent().getStringExtra("aendDate"));
//        aleader.setText(getIntent().getStringExtra("aleader"));
//        amember.setText(getIntent().getStringExtra("amember"));
//
        String id = getIntent().getExtras().getString("aid");
        aaid.setText(id);
        String title = getIntent().getExtras().getString("atitle");
        aatitle.setText(title);
        String desc = getIntent().getExtras().getString("adescription");
        aadesc.setText(desc);
        String start = getIntent().getExtras().getString("astartDate");
        aastartdate.setText(start);
        String end = getIntent().getExtras().getString("aendDate");
        aaenddate.setText(end);
        String leader = getIntent().getExtras().getString("aleader");
        aaleader.setText(leader);
        String member = getIntent().getExtras().getString("amember");
        aamember.setText(member);

    }
}