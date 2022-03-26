package com.example.trpp.NavigationActivity.ui.votes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.trpp.R;

import java.util.Arrays;
import java.util.List;

public class CreateVotes extends AppCompatActivity {
    static int COUNTER = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_votes);

        Button btn_add_var = findViewById(R.id.add_new_var);
        RecyclerView mRecyclerView = findViewById(R.id.recycle);
        String[] mDataSet = new String[3];

        CustomAdapter adapter = new CustomAdapter(mDataSet);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);


        btn_add_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                COUNTER++;
                if(COUNTER == 10){
                    btn_add_var.setEnabled(false);
                }else {
                    String[] buffer = new String[COUNTER];
                    String[] mDataSetNew = new String[]{""};
//                    Arrays.copyOf(mDataSet, mDataSet.length + 1);
                    for(int i = 0; i <mDataSet.length; i++) {
                        buffer[i] = mDataSet[i];
                    }
                    buffer[COUNTER-1] = mDataSet[0];
                    CustomAdapter adapter = new CustomAdapter(buffer);
                    mRecyclerView.setAdapter(adapter);
                }
            }
        });
    }
}