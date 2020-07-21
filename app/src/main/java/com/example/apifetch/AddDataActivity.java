package com.example.apifetch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.Range;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.Connection;
import java.sql.Statement;

public class AddDataActivity extends AppCompatActivity {
EditText id,name,email,phone,message;
Button save;
ProgressDialog progressDialog;
ConnectionClass connectionClass;
private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
       // getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        id = (EditText) findViewById(R.id.id);
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.phone);
        message = (EditText) findViewById(R.id.message);
        save = (Button) findViewById(R.id.save);
        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.phone, "^[2-9]{2}[0-9]{8}$", R.string.phoneerror);
        awesomeValidation.addValidation(this, R.id.message, RegexTemplate.NOT_EMPTY, R.string.messageerror);
        progressDialog = new ProgressDialog(this);
        connectionClass = new ConnectionClass();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications","MyNotifications",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successfully connected";
                        if (!task.isSuccessful()) {
                            msg = "Failed to connect";
                        }
                        Toast.makeText(AddDataActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               submitForm();
                String tittle=name.getText().toString().trim();
                String subject=phone.getText().toString().trim();
                String body=email.getText().toString().trim();
                NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notify=new Notification.Builder
                        (getApplicationContext()).setContentTitle(tittle).setContentText(body).
                        setContentTitle(subject).setSmallIcon(R.drawable.cf).build();
                notify.flags |= Notification.FLAG_AUTO_CANCEL;
                notif.notify(0, notify);
            }
        });
    }
    private void submitForm() {
        if (awesomeValidation.validate()) {
            Doregister doregister = new Doregister();
            doregister.execute("");
        }
    }
    public class Doregister extends AsyncTask<String,String,String>
    {
        Integer idin = Integer.parseInt(id.getText().toString());
        String namestr = name.getText().toString();
        String emailstr = email.getText().toString();
        String phonestr = phone.getText().toString();
        String messagestr = message.getText().toString();
        String z = "";
        boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading....");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings)
        {
                 try {
                        Connection con = connectionClass.CONN();
                        String query = "insert into info values('"+idin+"','"+namestr+"','"+emailstr+"','"+phonestr+"','"+messagestr+"')";
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                        z="Saved Successfully";
                        isSuccess = true;
                        Intent in = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(in);
                }
                 catch (Exception ex)
                 {
                        isSuccess = false;
                        z = "Exceptions"+ex;
                }
          return z;
        }

        protected void onPostExecute(String s){
            if(isSuccess){
                id.setText("");
                name.setText("");
                phone.setText("");
                email.setText("");
                message.setText("");
                Toast.makeText(getBaseContext(),""+z,Toast.LENGTH_SHORT).show();
            }
            progressDialog.hide();
        }
    }
}