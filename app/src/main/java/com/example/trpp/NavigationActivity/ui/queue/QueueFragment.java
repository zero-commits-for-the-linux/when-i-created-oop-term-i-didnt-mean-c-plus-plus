package com.example.trpp.NavigationActivity.ui.queue;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.trpp.databinding.FragmentQueueBinding;


public class QueueFragment extends Fragment {


    private FragmentQueueBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentQueueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button to_queue = binding.toQueue;
        Button out_queue = binding.outQueue;
        EditText input = binding.testFIO;

        to_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "This is to queue btn", Toast.LENGTH_LONG).show();

            }
        });

        out_queue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "This is out queue btn", Toast.LENGTH_LONG).show();
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