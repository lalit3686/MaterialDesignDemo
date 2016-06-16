package com.example.materialdesigndemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by plalit on 7/30/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder>{

    private List<String> mStringArrayList;
    private CustomOnItemClickListener mCustomOnItemClickListener;

    public MyRecyclerViewAdapter(List<String> mStringArrayList, Context mContext){
        this.mStringArrayList = mStringArrayList;
        this.mCustomOnItemClickListener = (CustomOnItemClickListener) mContext;
    }

    /**
     * Add New Item to the List
     * @param item
     */
    public void addNewItem(String item){
        mStringArrayList.add(item);
        notifyDataSetChanged();
    }

    /**
     * Add New Item at specific position
     * @param item
     * @param position
     */
    public void addNewItemAtPosition(String item, int position){
        mStringArrayList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * Delete Item from the List
     * @param item
     */
    public void deleteItem(String item) {
        mStringArrayList.remove(mStringArrayList.indexOf(item));
    }

    /**
     * Delete Item from the List at position
     * @param position
     */
    public void deleteItemAtPosition(int position){

        if(mStringArrayList.size() <= 10){
            MainActivity.showToast("No Items to delete");
        }
        else{
            mStringArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Delete Item by value
     * @param item
     */
    public void deleteByItem(String item){
        int position = mStringArrayList.indexOf(item);
        if(position != -1){
            mStringArrayList.remove(position);
            notifyItemRemoved(position);
        }
        else{
            MainActivity.showToast("No Items to delete");
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        final MyViewHolder myViewHolder = new MyViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomOnItemClickListener.onItemClick(v, myViewHolder.getAdapterPosition(), mStringArrayList.get(myViewHolder.getAdapterPosition()));
            }
        });

        Log.e(MyRecyclerViewAdapter.class.getSimpleName(), "onCreateViewHolder");
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
        Log.e(MyRecyclerViewAdapter.class.getSimpleName(), "onBindViewHolder - "+position);
        myViewHolder.mTextView.setText(mStringArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStringArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextView;

        public MyViewHolder(View view){
            super(view);

            mTextView = (TextView) view.findViewById(R.id.textView);
        }
    }
}
