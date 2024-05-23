//package com.mini_project.beast_race;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
//    private EditText username;
//    private EditText password;
//    private Button btn_login;
//    private TextView tvCreateAccount;
//    private final String REQUIRE = "Require";
//    Account account;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        username = findViewById(R.id.username);
//        password = findViewById(R.id.password);
//        btn_login = findViewById(R.id.btn_login);
//        tvCreateAccount = findViewById(R.id.tvCreateAccount);
//        btn_login.setOnClickListener(this);
//        tvCreateAccount.setOnClickListener(this);
//
//        account = new Account();
//        account.setId(1);
//        account.setUsername("OscarNumberOne");
//        account.setPassword("123456");
//    }
//
//    private boolean checkInput() {
//        if (username.getText().toString().isEmpty()) {
//            username.setError(REQUIRE);
//            return false;
//        }
//        if (password.getText().toString().isEmpty()) {
//            password.setError(REQUIRE);
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private Account checkLogin(String username, String password) {
//        if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
//            return account;
//        }
//        return null;
//    }
//
//    private void signIn() {
//        if (!checkInput() || checkLogin(username.getText().toString(), password.getText().toString()) == null) {
//            return;
//        } else if (checkLogin(username.getText().toString(), password.getText().toString()) != null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.putExtra("username", account.getUsername());
//            startActivity(intent);
//            finish();
//        }
//    }
//
//    private void signUp() {
//        Intent intent = new Intent(this, SignUpActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn_login) {
//            signIn();
//        } else if (v.getId() == R.id.tvCreateAccount) {
//            signUp();
//        }
//    }
//}

package com.mini_project.beast_race;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etUsernameLogin = findViewById(R.id.username);
        EditText etPasswordLogin = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvCreateAccount = findViewById(R.id.tvCreateAccount);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(v -> {
            String username = etUsernameLogin.getText().toString();
            String password = etPasswordLogin.getText().toString();

            String storedUsername = sharedPreferences.getString(USERNAME_KEY, null);
            String storedPassword = sharedPreferences.getString(PASSWORD_KEY, null);

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (username.equals(storedUsername) && password.equals(storedPassword)) {
                Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                // Chuyển sang layout hướng dẫn
//                Intent intent = new Intent(SignInActivity.this, HuongdanActivity.class);
//                startActivity(intent);
//                finish();  // Finish SignInActivity to remove it from the back stack

            } else {
                Toast.makeText(SignInActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            }
        });

        tvCreateAccount.setOnClickListener(v -> startActivity(new Intent(SignInActivity.this, SignUpActivity.class)));
    }
}

