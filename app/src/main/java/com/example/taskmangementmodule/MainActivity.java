package com.example.taskmangementmodule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public CardView card1, card2,card3,card4,card5;
    ImageView logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);

        logout =findViewById(R.id.logoutbtn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create the object of
                // AlertDialog Builder class
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(MainActivity.this);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to logout ?");

                // Set Alert Title
                builder.setTitle("Log Out !");

                // Set Cancelable false
                // for when the user clicks on the outside
                // the Dialog Box then it will remain show
                builder.setCancelable(true);

                // Set the positive button with yes name
                // OnClickListener method is use of
                // DialogInterface interface.

                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                    }
                                });

                // Set the Negative button with No name
                // OnClickListener method is use
                // of DialogInterface interface.
                builder
                        .setNegativeButton(
                                "No",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                        // If user click no
                                        // then dialog box is canceled.
                                        dialog.cancel();
                                    }
                                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();

                // Show the Alert Dialog box
                alertDialog.show();
            }
        });



        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();


        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
    }


        @Override
        public void onClick(View v) {
            Intent i;

            switch (v.getId()) {
                case R.id.c1:
                    i = new Intent(this, dashboard.class);
                    startActivity(i);
                    break;
                case R.id.c2:
                    i = new Intent(this, newp.class);
                    startActivity(i);
                    break;
                case R.id.c3:
                    i = new Intent(this, viewdatamanager.class);
                    startActivity(i);
                    break;
                case R.id.c4:
                    Intent intent = new Intent(getApplicationContext(), AchievedProject.class);
                    startActivity(intent);
                    break;
                case R.id.c5:
                    i = new Intent(this, addUser.class);
                    startActivity(i);
                    break;
        }
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}