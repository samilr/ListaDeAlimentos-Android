package com.example.notesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    List<Model> alimentosList;
    List<Model>newList;

    public Adapter(Context context, Activity activity, List<Model> alimentosList) {
        this.context = context;
        this.activity = activity;
        this.alimentosList = alimentosList;
        newList = new ArrayList<>(alimentosList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycler_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleToShow.setText(alimentosList.get(position).getTitle());
        holder.img.setImageResource(R.drawable.img);
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("title", alimentosList.get(position).getTitle());
                intent.putExtra("id", alimentosList.get(position).getId());
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return alimentosList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private  Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filterList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterList.addAll(newList);
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (Model item:newList){
                    if (item.getTitle().toLowerCase().contains(filterpattern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            alimentosList.clear();
            alimentosList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView titleToShow;
        ImageView img;
        RelativeLayout mLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mLayout  = itemView.findViewById(R.id.note_layout);
            titleToShow = itemView.findViewById(R.id.titleShow);
            img = itemView.findViewById(R.id.imageView);
        }
    }

    public List<Model> getList(){
        return alimentosList;
    }
    public void  removeItem(int poition){
        alimentosList.remove(poition);
        notifyItemRemoved(poition);
    }

    public void  restoreItem(Model item, int position){
        newList.add(position,item);
        notifyItemInserted(position);
    }
}
