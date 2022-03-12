package com.example.trpp.NavigationActivity.ui.profile;


import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_btn.setVisibility(View.GONE);
                save_btn.setVisibility(View.VISIBLE);
                FIO.setEnabled(true);
                login.setEnabled(true);

                save_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        save_btn.setVisibility(View.INVISIBLE);
                        FIO.setEnabled(false);
                        login.setEnabled(false);
                        edit_btn.setVisibility(View.VISIBLE);
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