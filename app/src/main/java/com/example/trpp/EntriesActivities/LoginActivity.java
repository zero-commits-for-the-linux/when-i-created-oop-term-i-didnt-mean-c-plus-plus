package com.example.trpp.EntriesActivities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trpp.NavigationActivity.NavigationMainActivity;
import com.example.trpp.NavigationActivity.ui.profile.ProfileFragment;
import com.example.trpp.R;
import org.json.JSONObject;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_text_login;
    private EditText edit_text_password;
    private Button enter_button;
    private Button register_button;

    private class MyLoginThread implements Runnable{

        @Override
        public void run() {
            Log.e("Отслеживаю потоки", Thread.currentThread() + "(probably myLoginThread) started");
            String Login = "http://185.18.55.107:5000/login?login=" + edit_text_login.getText().toString();
            Looper.prepare();
            try {
                URL obj = new URL(Login);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Accept-Charset", "UTF-8");

                InputStream response = con.getInputStream();
                Scanner s = new Scanner(response).useDelimiter("\\A");
                String result = s.hasNext() ? s.next() : "";

                Log.e("_________________________", result);

                if (!result.equals("null\n")) {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.get("login").equals(edit_text_login.getText().toString()) && edit_text_password.getText().toString().equals(jsonObject.get("password"))) {
                        Intent successfulLoginIntent = new Intent(LoginActivity.this, NavigationMainActivity.class);
                        successfulLoginIntent.putExtra("fio", jsonObject.getString("fullname"));
                        successfulLoginIntent.putExtra("login", jsonObject.getString("login"));
                        successfulLoginIntent.putExtra("password", jsonObject.getString("password"));
                        successfulLoginIntent.putExtra("id", jsonObject.getString("id"));
                        startActivity(new Intent(successfulLoginIntent));
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Неверные данные", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Неверные данные", Toast.LENGTH_SHORT).show();
                }
                con.disconnect();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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
                Thread myThread = new Thread(new MyLoginThread());
                myThread.start();
                try {
                    myThread.join();
                    Log.e("Отслеживаю потоки", "Мы снова в мэйне " + Thread.currentThread());
                } catch (InterruptedException e) {
                }
            }
        });
    }
}