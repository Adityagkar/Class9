package com.acadview.assignment12_permissions;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText name,mail,phone,city;
    Button submit;
    Spinner state;
    String state_string;
    Integer state_position;
    String fileText;
    int random;

    String FILE_NAME="mydata.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        state = findViewById(R.id.statespinner);
        submit = findViewById(R.id.submit);




        final String states[] = {"Bihar", "Chhattisgarh", "Jharkhand", "Madhya Pradesh", "Odisha", "Rajasthan", "Uttar Pradesh"};

        random = new Random().nextInt(9999-1230) + 1230;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, states);
        state.setAdapter(arrayAdapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state_string = states[i];
                state_position=i;
                fileText=" Name : "+name.getText()+" Email : "+mail.getText()+" Phone: "+phone.getText()+" State : "+states[i]+" City : "+city.getText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "You haven't selected anything ! ", Toast.LENGTH_LONG).show();
            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             confirmAlert();
            }
        });


    }

    private void confirmAlert(){
        AlertDialog.Builder alertdialog= new AlertDialog.Builder(MainActivity.this);

        alertdialog.setTitle("Confirm !");
        alertdialog.setMessage("Are you sure you want to submit ?");

        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},123);
        }

                SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(phone.getText().toString(),null,"Your OTP is "+random+"",null,null);
        Intent intent=new Intent(MainActivity.this,DisplayActivity.class);
        intent.putExtra("OTP",random+"");
        startActivity(intent);

            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialogBox=alertdialog.create();
        alertDialogBox.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==123){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this,"Permission Granted !",Toast.LENGTH_LONG).show();
            }
        }
    }
}