package com.trtho.lab2_ex3;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private TextView tvAlreadyAccount;
    private Button btnSignUp;
    private final String REQUIRE = "Require";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvAlreadyAccount = findViewById(R.id.tvAlreadyAccount);
        btnSignUp = findViewById(R.id.btnSignUp);

        tvAlreadyAccount.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tvAlreadyAccount) {
            signInForm();
        } else if (id == R.id.btnSignUp) {
            signUp();
        }
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(etUsername.getText())) {
            etUsername.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(etPassword.getText())) {
            etPassword.setError(REQUIRE);
            return false;
        }

        if (TextUtils.isEmpty(etConfirmPassword.getText())) {
            etConfirmPassword.setError(REQUIRE);
            return false;
        }

        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(this,"Password and Confirm Password are not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signUp(){
        if (!checkInput()){
            return;
        }
        Toast.makeText(this,"Sign Up Success", Toast.LENGTH_SHORT).show();
    }

    private void signInForm(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}