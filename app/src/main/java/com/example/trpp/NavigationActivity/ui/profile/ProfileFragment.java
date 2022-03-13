package com.example.trpp.NavigationActivity.ui.profile;


import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.trpp.R;
import com.example.trpp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

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
                        //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        //Тип получаемых объектов - image:
                        photoPickerIntent.setType("image/*");
                        //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
//                        startActivity(photoPickerIntent, image_btn.getRootWindowInsets());
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}