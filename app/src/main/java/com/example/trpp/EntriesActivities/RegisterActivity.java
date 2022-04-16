package com.example.trpp.EntriesActivities;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.trpp.R;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class RegisterActivity extends AppCompatActivity {

    private final String nullStr = "null";
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
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String newLogin = "http://185.18.55.107:5000/login?login=" + edit_text_login.getText().toString();

                checkLogin(newLogin);

                //hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(register_button.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                //end of hide
            }
        });
    }

    public void post(String json) {
        try {
            String query_url = "http://185.18.55.107:5000/register";
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
            Log.e("______________________________________", String.valueOf(e));
            Snackbar RegisterSnackBar = Snackbar.make(getCurrentFocus(), "Ошибка регистрации", BaseTransientBottomBar.LENGTH_LONG);
            RegisterSnackBar.show();
            RegisterSnackBar.setAction("Повтор", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    public void checkLogin(String mystr) {
        try {
            URL obj = new URL(mystr);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            InputStream response = con.getInputStream();
            Scanner s = new Scanner(response).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";

            Log.e("_________________________", result);
            if (!result.equals("null")) {
                Snackbar RegisterSnackBar = Snackbar.make(getCurrentFocus(), "Логин уже занят", BaseTransientBottomBar.LENGTH_LONG);
                RegisterSnackBar.show();
                RegisterSnackBar.setAction("Повтор", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            }else {
                String json = "{ \"fullname\" : \"NONE\", \"login\" : \"" + edit_text_login.getText().toString() + "\", \"password\" : \"" + edit_text_password.getText().toString() + "\" }";
                post(json);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
