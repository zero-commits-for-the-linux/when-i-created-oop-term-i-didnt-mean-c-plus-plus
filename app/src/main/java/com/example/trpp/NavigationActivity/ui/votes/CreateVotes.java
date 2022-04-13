package com.example.trpp.NavigationActivity.ui.votes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.trpp.R;

import java.util.ArrayList;
import java.util.List;

public class CreateVotes extends AppCompatActivity {

    static int COUNTER = 3;
    static List<String> mDataSet = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_votes);

        Button btn_add_var = findViewById(R.id.add_new_var);
        RecyclerView mRecyclerView = findViewById(R.id.recycle);
        ImageButton btn_ready = findViewById(R.id.ready);

        mDataSet.add("");
        mDataSet.add("");
        mDataSet.add("");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        EditTextAdapter adapter = new EditTextAdapter(mDataSet);
        mRecyclerView.setAdapter(adapter);

        btn_add_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_add_var.setEnabled(true);
//                mDataSet.add("");
//                EditTextAdapter adapter = new EditTextAdapter(mDataSet);
                adapter.add("");
                mRecyclerView.setAdapter(adapter);
            }
        });

        btn_ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateVotes.this);
                builder.setTitle("Тема опроса")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(CreateVotes.this,"Нажата кнопка 'OK'",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(CreateVotes.this,"Нажата кнопка 'Отмена'",Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setView(R.layout.theme_vote);
                builder.create().show();
            }
        });
    }

    //метод скрытия клавиатры
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(event);
    }
}