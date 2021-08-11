package com.example.payphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sesion extends AppCompatActivity {

    private EditText code, phone, idcard;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

        code = findViewById(R.id.txtCode);
        phone = findViewById(R.id.txtPhone);
        idcard = findViewById(R.id.txtIDCard);
        login = findViewById(R.id.btnLogin);


        code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                code.setText("");
            }
        });
        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)phone.setText("");
            }
        });
        idcard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)idcard.setText("");
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendto();
            }
        });

    }

    public void sendto()
    {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("code", code.getText().toString());
        i.putExtra("phone", phone.getText().toString());
        i.putExtra("idcard", idcard.getText().toString());
        startActivity(i);
    }
}