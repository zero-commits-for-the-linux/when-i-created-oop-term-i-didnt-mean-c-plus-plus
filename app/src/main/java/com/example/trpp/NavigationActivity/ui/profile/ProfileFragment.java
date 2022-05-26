package com.example.trpp.NavigationActivity.ui.profile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.trpp.EntriesActivities.LoginActivity;
import com.example.trpp.NavigationActivity.NavigationMainActivity;
import com.example.trpp.databinding.FragmentProfileBinding;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.IOUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    CircleImageView profileImage;
    EditText edit_text_fio;
    EditText edit_text_login;
    EditText edit_text_password;

    private String response;


    public ProfileFragment() {
    }

    private class MyUpdateThread implements Runnable {

        @Override
        public void run() {
            Log.e("Отслеживаю потоки", Thread.currentThread() + "(probably myUpdateThread) started");
            edit_text_login = binding.profileLogin;
            edit_text_password = binding.profilePassword;

            String register_url = "http://185.18.55.107:5000/updatedata";
            JSONObject register = new JSONObject();
            try {
                register.put("id", getActivity().getIntent().getStringExtra("id"));
                register.put("nlogin", edit_text_login.getText().toString());
                register.put("npassword", edit_text_password.getText().toString());
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

    private class MyPhotoPickerThread implements Runnable {

        @Override
        public void run() {
            Log.e("Отслеживаю потоки", Thread.currentThread() + "(probably myPhotoPickerThread) started");
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult.launch(intent);
        }
    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        profileImage = binding.profileImage;
                        Intent data = result.getData();
                        Uri selectedImage = null;
                        if (data != null) {
                            selectedImage = data.getData();
                        }
                        profileImage.setImageURI(selectedImage);
                    }
                }
            }
    );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        edit_text_fio = binding.profileFIO;
        edit_text_login = binding.profileLogin;
        edit_text_password = binding.profilePassword;
        ImageButton edit_btn = binding.profileEdit;
        ImageButton save_btn = binding.profileSave;
        ImageButton image_btn = binding.imageEdit;
        ImageButton exit = binding.profileExit;

        edit_text_fio.setText(getActivity().getIntent().getStringExtra("fio"));
        edit_text_login.setText(getActivity().getIntent().getStringExtra("login"));
        edit_text_password.setText(getActivity().getIntent().getStringExtra("password"));

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().onBackPressed();
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_btn.setVisibility(View.GONE);
                save_btn.setVisibility(View.VISIBLE);
                image_btn.setVisibility(View.VISIBLE);
                edit_text_password.setEnabled(true);
                edit_text_login.setEnabled(true);
                NavigationMainActivity.navView.setEnabled(false);

                image_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Thread myThread = new Thread(new MyPhotoPickerThread());
                        myThread.start();
                    }
                });

                save_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Thread myThread = new Thread(new MyUpdateThread());
                        myThread.start();
                        save_btn.setVisibility(View.INVISIBLE);
                        image_btn.setVisibility(View.INVISIBLE);
                        edit_btn.setVisibility(View.VISIBLE);
                        NavigationMainActivity.navView.setEnabled(true);
                        edit_text_password.setEnabled(false);
                        edit_text_login.setEnabled(false);
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
