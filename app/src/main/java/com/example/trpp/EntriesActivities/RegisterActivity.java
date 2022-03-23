package com.example.trpp.EntriesActivities;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trpp.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

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
                //hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(register_button.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                //end of hide
                Snackbar RegisterSnackBar = Snackbar.make(view, "Регистрация успешна", BaseTransientBottomBar.LENGTH_LONG);
                RegisterSnackBar.show();
                RegisterSnackBar.setAction("Вход", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }
        });
    }
}
