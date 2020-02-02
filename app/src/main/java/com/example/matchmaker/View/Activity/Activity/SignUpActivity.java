package com.example.matchmaker.View.Activity.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.example.matchmaker.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    private ImageView backBtn;
    private TextInputEditText nameET, emailET, contactET, passwordET;
    private Button signUpBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initView();
        final RadioGroup rbg=(RadioGroup) findViewById(R.id.gender);


        backBtn = findViewById(R.id.arrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected = rbg.getCheckedRadioButtonId();

                RadioButton gender = findViewById(selected);

                Toast.makeText(SignUpActivity.this, "Gender is "+ gender.getText(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void beforeSignIn() {

        String nameUser = nameET.getText().toString().trim();
        String emailUser = emailET.getText().toString().trim();
        String contactUser = contactET.getText().toString().trim();
        String passwordUser = passwordET.getText().toString().trim();


    }

    private void initView() {
        backBtn = findViewById(R.id.arrow);

        nameET = findViewById(R.id.nameTextInputEditText);
        emailET = findViewById(R.id.emailTextInputEditText);
        contactET = findViewById(R.id.contactTextInputEditText);
        passwordET = findViewById(R.id.passwordTextInputEditText);


        signUpBT = findViewById(R.id.signUpBTN);
    }
}
