package com.example.trpp.NavigationActivity.ui.votes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.trpp.R;

import java.util.List;

public class CreateVotes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_votes);

        Button btn_add_var = findViewById(R.id.add_new_var);
        ListView listView = findViewById(R.id.list_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);


        btn_add_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.add(findViewById(R.id.title_create_vote).toString());
                adapter.notifyDataSetChanged();
            }
        });
    }
}