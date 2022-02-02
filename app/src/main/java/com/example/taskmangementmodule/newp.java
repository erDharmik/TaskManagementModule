package com.example.taskmangementmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class newp extends AppCompatActivity implements View.OnClickListener {

    public CardView card1, card2, card3, card4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newp);


        card1 = (CardView) findViewById(R.id.collage);
        card2 = (CardView) findViewById(R.id.branch);
        card3 = (CardView) findViewById(R.id.recievedImg);
        card4 = (CardView) findViewById(R.id.project);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.collage:
                i = new Intent(this, Addnewcollege.class);
                startActivity(i);
                break;
            case R.id.branch:
                i = new Intent(this, AddNewBranch.class);
                startActivity(i);
                break;
            case R.id.recievedImg:
                i = new Intent(this, addUser.class);
                startActivity(i);
                break;
            case R.id.project:
                i = new Intent(this, newproject.class);
                startActivity(i);
                break;
        }
    }
}
