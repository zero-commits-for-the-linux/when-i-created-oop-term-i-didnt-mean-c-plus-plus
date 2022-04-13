package com.example.trpp.EntriesActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.example.trpp.NavigationActivity.NavigationMainActivity;
import com.example.trpp.R;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_text_login;
    private EditText edit_text_password;
    private Button enter_button;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Коннект к бд
        //Коннект к таблице с юзерами

        edit_text_login = findViewById(R.id.login_enter);
        edit_text_password = findViewById(R.id.password_enter);
        enter_button = findViewById(R.id.enter_btn_enter);
        register_button = findViewById(R.id.register_btn_enter);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, NavigationMainActivity.class));
                finish();
            }
        });
    }
}
