package com.pressure.mynotes.methods;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.pressure.mynotes.R;
import com.pressure.mynotes.databinding.AdapterLayoutBinding;
import com.pressure.mynotes.model.Entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewholder> implements Filterable {
    private List<Entity> list;
    private Context context;
    private itemclicklistener activity;
    private List<Entity> filter;
   public Adapter(Context context)
    {
        this.context = context;
        activity =(itemclicklistener) context;
        list = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                filter = null;
                if(charSequence.length() == 0)
                    filter = list;
                else
                  filter = getFilteredList(charSequence.toString());

                FilterResults results = new FilterResults();
                results.values = filter;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<Entity>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    protected List<Entity> getFilteredList(String constraint) {
        List<Entity> results = new ArrayList<>();

        for (Entity item : list) {
            if (item.getTitle().contains(constraint) || item.getContent().contains(constraint)) {
                results.add(item);
            }
        }
        return results;
    }



    class viewholder extends RecyclerView.ViewHolder{
        AdapterLayoutBinding adapterLayoutBinding;

        public viewholder(@NonNull AdapterLayoutBinding itemView) {
            super(itemView.getRoot());
             adapterLayoutBinding = itemView;
            adapterLayoutBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onitemclicklistener(list.get(list.indexOf((Entity) v.getTag())).getId());
                }
            });

        }
    }

    @NonNull
    @Override
    public Adapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterLayoutBinding adapterLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.adapter_layout,viewGroup,false);
        return new viewholder(adapterLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.viewholder viewHolder, int i) {
        Entity ent = list.get(i);
        viewHolder.itemView.setTag(ent);
        viewHolder.adapterLayoutBinding.setNote(ent);
        viewHolder.adapterLayoutBinding.executePendingBindings();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    //interface
    public interface itemclicklistener {
        void onitemclicklistener(int index);
    }


    public void set(List<Entity> list)
    {
        this.list =list;
        notifyDataSetChanged();
    }
    //sorting the list according to the title of the note.
    public void sortByTitle()
    {
        Collections.sort(list, new Comparator<Entity>() {
            @Override
            public int compare(Entity entity, Entity t1) {
                return entity.getTitle().compareTo(t1.getTitle());
            }
        });
         notifyDataSetChanged();
        Toast.makeText(context, "Sorted Successfully", Toast.LENGTH_SHORT).show();
    }
    //sorting the list according to the content of the note.
    public void sortByContent()
    {
        Collections.sort(list, new Comparator<Entity>() {
            @Override
            public int compare(Entity entity, Entity t1) {
                return entity.getContent().compareTo(t1.getContent());
            }
        });
        Toast.makeText(context, "Sorted Successfully", Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

}