package com.pressure.mynotes.methods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pressure.mynotes.R;
import com.pressure.mynotes.entities.Entity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> {
    List<Entity> entries;
    Context context;
    itemclicklistener activity;
   public Adapter(Context context,List<Entity> entries)
    {
        this.context = context;
        this.entries = entries;
        activity =(itemclicklistener) context;
    }
    class viewholder extends RecyclerView.ViewHolder{
        TextView tvdesc;
        TextView tvtitle;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tvdesc = itemView.findViewById(R.id.tvdesc);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onitemclicklistener(entries.get(entries.indexOf((Entity) v.getTag())).getId());
                }
            });

        }
    }

    @NonNull
    @Override
    public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_layout,viewGroup,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewholder viewHolder, int i) {
        Entity ent = entries.get(i);
        viewHolder.itemView.setTag(ent);
        viewHolder.tvtitle.setText(ent.getTitle());
        viewHolder.tvdesc.setText(ent.getContent());
    }
    @Override
    public int getItemCount() {
        return entries.size();
    }

    //interface

    public interface itemclicklistener {
        void onitemclicklistener(int index);
    }


    public void set(List<Entity> list)
    {
        entries=list;
        notifyDataSetChanged();
    }
    public List<Entity> get()
    {
        return entries;
    }




}