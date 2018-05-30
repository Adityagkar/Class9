package com.acadview.assignment12_permissions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class DisplayActivity extends AppCompatActivity {


    TextView details;
    Button submit;
    EditText editText;

    String fileText;
    String OTPnumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


            editText=findViewById(R.id.editText5);
            submit=findViewById(R.id.button);
        OTPnumber=getIntent().getStringExtra("OTP");

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editText.getText().toString().equals(OTPnumber)) {
                        Toast.makeText(DisplayActivity.this, "YOUR OTP IS CORRECT !", Toast.LENGTH_LONG).show();
                    }else
                        Toast.makeText(DisplayActivity.this,"OTP ENTERED IS WRONG !",Toast.LENGTH_LONG).show();
                }
            });

        }



}
