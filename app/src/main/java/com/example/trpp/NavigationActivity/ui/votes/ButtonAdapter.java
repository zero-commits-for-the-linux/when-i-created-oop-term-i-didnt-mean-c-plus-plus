package com.example.trpp.NavigationActivity.ui.votes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trpp.R;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    private List<String> mDataset;

    public ButtonAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_overview_votes, parent, false);
        // pass MyCustomEditTextListener to viewholder in onCreateViewHolder
        // so that we don't have to do this expensive allocation in onBindViewHolder
        ButtonAdapter.ViewHolder vh = new ButtonAdapter.ViewHolder(v, new ButtonAdapter.MyCustomButtonListener());

        return vh;
    }

    @Override
    public void onBindViewHolder(ButtonAdapter.ViewHolder holder, final int position) {
        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in mDataset to update
        holder.myCustomButtonListener.updatePosition(holder.getAdapterPosition());
        holder.mButton.setText(mDataset.get(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public Button mButton;
        public ButtonAdapter.MyCustomButtonListener myCustomButtonListener;

        public ViewHolder(View v, ButtonAdapter.MyCustomButtonListener myCustomButtonListener) {
            super(v);

            this.mButton =  v.findViewById(R.id.btn_overview);
            this.myCustomButtonListener = myCustomButtonListener;
            this.mButton.addTextChangedListener(myCustomButtonListener);
        }
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
    private class MyCustomButtonListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            mDataset.set(position, charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}