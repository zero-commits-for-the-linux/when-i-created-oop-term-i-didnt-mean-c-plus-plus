package com.example.trpp.NavigationActivity.ui.profile;

import static android.app.Activity.RESULT_OK;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.trpp.EntriesActivities.LoginActivity;
import com.example.trpp.databinding.FragmentProfileBinding;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    CircleImageView profileImage;

    public ProfileFragment() {
    }

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
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
    );

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText FIO = binding.profileFIO;
        EditText login = binding.profileLogin;
        ImageButton edit_btn = binding.profileEdit;
        ImageButton save_btn = binding.profileSave;
        ImageButton image_btn = binding.imageEdit;
        ImageButton exit = binding.profileExit;

        exit.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().onBackPressed();
        });

        edit_btn.setOnClickListener(view -> {
            edit_btn.setVisibility(View.GONE);
            save_btn.setVisibility(View.VISIBLE);
            image_btn.setVisibility(View.VISIBLE);
            FIO.setEnabled(true);
            login.setEnabled(true);

            image_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult.launch(intent);

//                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//                    photoPickerIntent.setType("image/*");
//                    startActivityForResult(photoPickerIntent, 1);
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