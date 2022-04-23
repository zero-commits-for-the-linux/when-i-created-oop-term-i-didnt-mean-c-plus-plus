package com.example.trpp.EntriesActivities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.trpp.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterActivity extends AppCompatActivity {

    private EditText edit_text_login;
    private EditText edit_text_password;
    private EditText edit_text_fio;
    private Button register_button;

    private String response;

    private class MyRegisterThread implements Runnable {

        @Override
        public void run() {
            Log.e("Отслеживаю потоки", "поток - " + Thread.currentThread());
            String register_url = "http://185.18.55.107:5000/register";
            JSONObject register = new JSONObject();
            try {
                register.put("fullname", edit_text_fio.getText().toString());
                register.put("login", edit_text_login.getText().toString());
                register.put("password", edit_text_password.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL(register_url);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");

                OutputStream os = conn.getOutputStream();
                os.write(register.toString().getBytes(StandardCharsets.UTF_8));
                os.close();

                InputStream in = new BufferedInputStream(conn.getInputStream());
                response = IOUtils.toString(in, "UTF-8");
                System.out.println(response);
                in.close();

                conn.disconnect();
            } catch (Exception e) {
                System.out.println(e);
                Log.e("______________________________________", String.valueOf(e));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edit_text_login = findViewById(R.id.login_register);
        edit_text_fio = findViewById(R.id.fio_register);
        edit_text_password = findViewById(R.id.password_register);
        register_button = findViewById(R.id.register_btn_register);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(register_button.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                //end of hide

                Thread myThread = new Thread(new MyRegisterThread());
                myThread.start();
                try{
                    myThread.join();
                    Log.e("Отслеживаю потоки", "Мы снова в мэйне " + Thread.currentThread());
                } catch(InterruptedException e){
                }

                switch (response) {
                    case "\"already_have\"\n":
                        Toast.makeText(RegisterActivity.this, "Логие уже занят", Toast.LENGTH_SHORT).show();
                        break;
                    case "\"added\"\n":
                        Snackbar RegisterSnackBar = Snackbar.make(getCurrentFocus(), "Регистрация успешна!", Snackbar.LENGTH_LONG);
                        RegisterSnackBar.show();
                        RegisterSnackBar.setAction("Вход", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        });
                        break;
                }
            }
        });
    }
}