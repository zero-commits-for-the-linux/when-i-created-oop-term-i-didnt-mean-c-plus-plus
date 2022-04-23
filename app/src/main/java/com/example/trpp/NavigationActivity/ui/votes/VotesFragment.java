package com.example.trpp.NavigationActivity.ui.votes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.trpp.databinding.FragmentVotesBinding;

import java.util.ArrayList;
import java.util.List;

public class VotesFragment extends Fragment {

    private FragmentVotesBinding binding;
    public VotesFragment() {
    }
    static List<String> VOTES = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVotesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        VOTES.add("1");
        VOTES.add("2");
        VOTES.add("3");
        VOTES.add("4");

        ImageButton btn_add = binding.addNewVote;
        RecyclerView recyclerView = binding.recycleForVotes;

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ButtonAdapter adapter = new ButtonAdapter(VOTES);
        recyclerView.setAdapter(adapter);


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