package com.example.trpp.NavigationActivity.ui.votes;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trpp.R;

import java.util.List;

public class EditTextAdapter extends RecyclerView.Adapter<EditTextAdapter.ViewHolder> {

    private List<String> mDataset;

    public EditTextAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    public void add(String str) {
        mDataset.add(str);
    }

    @Override
    public EditTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adding_vars, parent, false);
        // pass MyCustomEditTextListener to viewholder in onCreateViewHolder
        // so that we don't have to do this expensive allocation in onBindViewHolder
        ViewHolder vh = new ViewHolder(v, new MyCustomEditTextListener());

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // update MyCustomEditTextListener every time we bind a new item
        // so that it knows what item in mDataset to update
        holder.myCustomEditTextListener.updatePosition(holder.getBindingAdapterPosition());
//        holder.mEditText.setText(mDataset.get(holder.getAdapterPosition()));

        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mImageButton.setEnabled(false);
                int newPosition = holder.getBindingAdapterPosition();
                CreateVotes.mDataSet.remove(newPosition);
                notifyItemRemoved(newPosition);
                notifyItemRangeChanged(newPosition, CreateVotes.mDataSet.size());
                holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public EditText mEditText;
        public MyCustomEditTextListener myCustomEditTextListener;
        public ImageButton mImageButton;

        public ViewHolder(View v, MyCustomEditTextListener myCustomEditTextListener) {
            super(v);

            this.mEditText =  v.findViewById(R.id.list_item_editText);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mEditText.addTextChangedListener(myCustomEditTextListener);

            this.mImageButton = v.findViewById(R.id.list_item_imageBtn);
        }
    }

    // we make TextWatcher to be aware of the position it currently works with
    // this way, once a new item is attached in onBindViewHolder, it will
    // update current position MyCustomEditTextListener, reference to which is kept by ViewHolder
    private class MyCustomEditTextListener implements TextWatcher {
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