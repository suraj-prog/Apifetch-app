package com.example.apifetch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {
ImageView img, imgs,ext,mode;
TextView mod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        img = findViewById(R.id.img1);
        imgs = findViewById(R.id.img2);
        ext = findViewById(R.id.exit);
        mode = findViewById(R.id.mode);
        mod = findViewById(R.id.modes);
        SharedPreferences sharedPreferences
                = getSharedPreferences(
                "sharedPrefs", MODE_PRIVATE);
        final SharedPreferences.Editor editor
                = sharedPreferences.edit();
        final boolean isDarkModeOn
                = sharedPreferences
                .getBoolean(
                        "isDarkModeOn", false);
        if (isDarkModeOn) {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_YES);
            mod.setText(
                    "Enable Dark Mode");
        }
        else {
            AppCompatDelegate
                    .setDefaultNightMode(
                            AppCompatDelegate
                                    .MODE_NIGHT_NO);
            mod.setText(
                    "Disable Dark Mode");
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });
        imgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DashboardActivity.this, AddDataActivity.class);
                startActivity(myIntent);
            }
        });
        ext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setMessage("Are you sure you want to Exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                DashboardActivity.super.onBackPressed();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
                alertDialog.show();
            }
        });
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDarkModeOn) {

                    // if dark mode is on it
                    // will turn it off
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_NO);
                    // it will set isDarkModeOn
                    // boolean to false
                    editor.putBoolean(
                            "isDarkModeOn", false);
                    editor.apply();

                    // change text of Button
                    mod.setText(
                            "Disable Dark Mode");
                }
                else {

                    // if dark mode is off
                    // it will turn it on
                    AppCompatDelegate
                            .setDefaultNightMode(
                                    AppCompatDelegate
                                            .MODE_NIGHT_YES);

                    // it will set isDarkModeOn
                    // boolean to true
                    editor.putBoolean(
                            "isDarkModeOn", true);
                    editor.apply();

                    // change text of Button
                    mod.setText(
                            "Enable Dark Mode");
                }
            }
        });
    }
}