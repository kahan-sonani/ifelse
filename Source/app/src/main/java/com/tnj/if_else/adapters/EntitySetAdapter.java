package com.tnj.if_else.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.R;
import com.tnj.if_else.databinding.CookEntitySetLayoutBinding;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.EntityClickListener;

import java.util.ArrayList;
import java.util.List;

public class EntitySetAdapter extends RecyclerView.Adapter<EntitySetAdapter.EntitySetHolder> implements Filterable {

    private ArrayList<EntitySet> list;
    private ArrayList<EntitySet> backUpList;
    private Filter filter;
    private ArrayList<EntitySet> searchList;
    private Drawable iconBackground;
    private EntityClickListener listener;
    private ObservableBoolean showDetails;

    public EntitySetAdapter(ArrayList<EntitySet> set) {
        list = set;
        backUpList = new ArrayList<>(set);
        searchList = new ArrayList<>();
        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if(charSequence == null || charSequence.length() == 0)
                    searchList.addAll(backUpList);
                else{
                    String pattern = charSequence.toString().toLowerCase().trim();
                    for(EntitySet set : backUpList)
                        if(set.name.toLowerCase().contains(pattern))
                            searchList.add(set);
                }
                FilterResults results = new FilterResults();
                results.values = searchList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list.clear();
                list.addAll((List) filterResults.values);
                searchList.clear();
                notifyDataSetChanged();
            }
        };
    }

    public EntitySetAdapter showDetails(ObservableBoolean showDetails) {
        this.showDetails = showDetails;
        return this;
    }

    public EntitySetAdapter setColor(Context context, int color) {
        iconBackground = context.getDrawable(R.drawable.color_drawable);
        iconBackground.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC);
        return this;
    }

    public EntitySetAdapter setClickListener(EntityClickListener listener) {
        this.listener = listener;
        return this;
    }

    @NonNull
    @Override
    public EntitySetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EntitySetHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cook_entity_set_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EntitySetHolder holder, int position) {
        EntitySet set = list.get(position);
        holder.binding.entityName.setText(set.name);
        holder.binding.entityCategoryName.setText(set.category);
        holder.binding.entityIcon.setIcon(set.icon);
        holder.binding.entityIconLayout.setBackground(iconBackground);
        holder.binding.entityDescription.setText(set.description);
        holder.binding.entityDescription.setVisibility(showDetails.get() ? View.VISIBLE : View.GONE);
        holder.binding.getRoot().setOnClickListener(view -> listener.onClick(set, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        list.clear();
        searchList.clear();
        backUpList.clear();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class EntitySetHolder extends RecyclerView.ViewHolder {

        CookEntitySetLayoutBinding binding;

        public EntitySetHolder(@NonNull CookEntitySetLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setShowDetails(showDetails);
        }
    }
}
