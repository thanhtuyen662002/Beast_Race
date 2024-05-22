package com.mini_project.beast_race;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button btn_login;
    private TextView tvCreateAccount;
    private final String REQUIRE = "Require";
    Account account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        tvCreateAccount = findViewById(R.id.tvCreateAccount);
        btn_login.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);

        account = new Account();
        account.setId(1);
        account.setUsername("OscarNumberOne");
        account.setPassword("123456");
    }

    private boolean checkInput() {
        if (username.getText().toString().isEmpty()) {
            username.setError(REQUIRE);
            return false;
        }
        if (password.getText().toString().isEmpty()) {
            password.setError(REQUIRE);
            return false;
        } else {
            return true;
        }
    }

    private Account checkLogin(String username, String password) {
        if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    private void signIn() {
        if (!checkInput() || checkLogin(username.getText().toString(), password.getText().toString()) == null) {
            return;
        } else if (checkLogin(username.getText().toString(), password.getText().toString()) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", account.getUsername());
            startActivity(intent);
            finish();
        }
    }

    private void signUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login) {
            signIn();
        } else if (v.getId() == R.id.tvCreateAccount) {
            signUp();
        }
    }
}
