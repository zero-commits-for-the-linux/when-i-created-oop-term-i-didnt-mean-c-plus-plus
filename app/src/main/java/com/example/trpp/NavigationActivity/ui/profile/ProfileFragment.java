package com.example.trpp.NavigationActivity.ui.profile;


import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.getDrawable;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trpp.R;
import com.example.trpp.databinding.FragmentProfileBinding;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    CircleImageView profileImage;

    public ProfileFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText FIO = binding.profileFIO;
        EditText login = binding.profileLogin;
        ImageButton edit_btn = binding.profileEdit;
        ImageButton save_btn = binding.profileSave;
        ImageButton image_btn = binding.imageEdit;

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_btn.setVisibility(View.GONE);
                save_btn.setVisibility(View.VISIBLE);
                image_btn.setVisibility(View.VISIBLE);
                FIO.setEnabled(true);
                login.setEnabled(true);

                image_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, 1);
                    }

                });

                save_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save_btn.setVisibility(View.INVISIBLE);
                        image_btn.setVisibility(View.INVISIBLE);
                        edit_btn.setVisibility(View.VISIBLE);
                        FIO.setEnabled(false);
                        login.setEnabled(false);
                    }
                });
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        profileImage = binding.profileImage;
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = imageReturnedIntent.getData();
                profileImage.setImageURI(selectedImage);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}