package com.example.cryptage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    Button btnEncryptData,btnDecryptData;
    TextView txtResult;
    TextInputLayout tilData;
    private EnCryptionManager enCryptionManager;
    private  String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEncryptData = findViewById(R.id.btnEncryptData);
        btnDecryptData = findViewById(R.id.btnDecryptData);

        txtResult= findViewById(R.id.txtResult);
        tilData= findViewById(R.id.tilData);

        enCryptionManager = EnCryptionManager.getInstance();

        btnEncryptData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data= tilData.getEditText().getText().toString();
                if (!TextUtils.isEmpty(data)) {
                    System.out.println("Plain data: " +data);
                    result = enCryptionManager.encrypt(data);
//                    result = "enCryptionManager.encrypt(data)";
                    System.out.println("Plain result: " +result);
                    txtResult.setText(result);
                }else{
                    Toast.makeText(MainActivity.this, "Field is empty.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDecryptData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(result)) {
                    result= enCryptionManager.decrypt(result);
                    System.out.println("Plain decryptage: " +result);
                    txtResult.setText(result);
                }else{
                    Toast.makeText(MainActivity.this, "No data to decrypt.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}