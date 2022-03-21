package com.example.trpp.NavigationActivity.ui.votes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.trpp.databinding.FragmentVotesBinding;

public class VotesFragment extends Fragment {

    private FragmentVotesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton btn_add = binding.addNewVote;

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CreateVotes.class));
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