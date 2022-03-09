package com.example.trpp.EntriesActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trpp.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_text_login;
    private EditText edit_text_password;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_text_login = findViewById(R.id.login_register);
        edit_text_password = findViewById(R.id.password_register);
        register_button = findViewById(R.id.register_btn_register);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Регистрация успешна", Toast.LENGTH_LONG).show();
            }
        });
    }
}
