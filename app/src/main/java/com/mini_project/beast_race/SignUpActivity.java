//package com.mini_project.beast_race;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
//    private EditText username;
//    private EditText password;
//    private EditText confirmPassword;
//    private Button btnSignUp;
//    private TextView tvLogin;
//    private final String REQUIRE = "Require";
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//
//        username = findViewById(R.id.username);
//        password = findViewById(R.id.password);
//        confirmPassword = findViewById(R.id.confirm_password);
//        btnSignUp = findViewById(R.id.btn_login);
//        tvLogin = findViewById(R.id.tvLoginAccount);
//        btnSignUp.setOnClickListener(this);
//        tvLogin.setOnClickListener(this);
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
//        }
//        if (confirmPassword.getText().toString().isEmpty()) {
//            password.setError(REQUIRE);
//            return false;
//        }
//        if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
//            Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        return true;
//    }
//
//    private void signUp() {
//        if (checkInput()) {
//            Toast.makeText(this, "Sign up success", Toast.LENGTH_SHORT).show();
//        }
//        if (!checkInput()) {
//            Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
//            return;
//        }
//    }
//
//    private void signInForm(){
//        Intent intent = new Intent(this, SignInActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn_login) {
//            signUp();
//        }
//        if (v.getId() == R.id.tvLoginAccount) {
//            signInForm();
//        }
//    }
//}
//
//

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

public class SignUpActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "UserPrefs";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText etUsernameSignUp = findViewById(R.id.username);
        EditText etPasswordSignUp = findViewById(R.id.password);
        EditText etConfirmPasswordSignUp = findViewById(R.id.confirm_password);
        Button btnSignUp = findViewById(R.id.btn_login);
        TextView tvLoginAccount = findViewById(R.id.tvLoginAccount);

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        btnSignUp.setOnClickListener(v -> {
            String username = etUsernameSignUp.getText().toString();
            String password = etPasswordSignUp.getText().toString();
            String confirmPassword = etConfirmPasswordSignUp.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu chưa chính xác", Toast.LENGTH_SHORT).show();
            } else {
                // Save credentials to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USERNAME_KEY, username);
                editor.putString(PASSWORD_KEY, password);
                editor.apply();

                Toast.makeText(SignUpActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });

        tvLoginAccount.setOnClickListener(v -> startActivity(new Intent(SignUpActivity.this, SignInActivity.class)));
    }
}

