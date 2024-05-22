package com.mini_project.beast_race;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private EditText confirmPassword;
    private Button btnSignUp;
    private TextView tvLogin;
    private final String REQUIRE = "Require";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirm_password);
        btnSignUp = findViewById(R.id.btn_login);
        tvLogin = findViewById(R.id.tvLoginAccount);
        btnSignUp.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    private boolean checkInput() {
        if (username.getText().toString().isEmpty()) {
            username.setError(REQUIRE);
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError(REQUIRE);
            return false;
        }
        if (confirmPassword.getText().toString().isEmpty()) {
            password.setError(REQUIRE);
            return false;
        }
        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void signUp() {
        if (checkInput()) {
            Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show();
        }
        if (!checkInput()) {
            Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void signInForm(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            signUp();
        }
        if (v.getId() == R.id.tvLoginAccount) {
            signInForm();
        }
    }
}
