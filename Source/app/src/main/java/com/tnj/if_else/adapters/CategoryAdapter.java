package com.tnj.if_else.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.ObservableBoolean;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
import com.tnj.if_else.R;
import com.tnj.if_else.adapters.CategoryAdapter.CategoryHolder;
import com.tnj.if_else.adapters.CategoryAdapter.ChildHolder;
import com.tnj.if_else.databinding.CookChildLayoutBinding;
import com.tnj.if_else.databinding.CookGroupLayoutBinding;
import com.tnj.if_else.utils.helperClasses.EntityCategory;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.EntityClickListener;

import java.util.List;

public class CategoryAdapter extends ExpandableRecyclerViewAdapter<CategoryHolder, ChildHolder>{

    private EntityClickListener listener;
    private int color;
    private ObservableBoolean showDetails;

    public CategoryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    public CategoryAdapter showDetails(ObservableBoolean bool) {
        showDetails = bool;
        return this;
    }

    public CategoryAdapter setClickListener(EntityClickListener listener) {
        this.listener = listener;
        return this;
    }

    public CategoryAdapter setColor(Context context, int color) {
        this.color = ContextCompat.getColor(context, color);
        return this;
    }

    @Override
    public CategoryHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new CategoryHolder(CookGroupLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public ChildHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new ChildHolder(CookChildLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindChildViewHolder(ChildHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final EntitySet entitySet = (EntitySet) group.getItems().get(childIndex);
        holder.binding.childIcon.setIcon(entitySet.icon);
        holder.binding.childName.setText(entitySet.name);
        holder.binding.childDescription.setText(entitySet.description);
        holder.binding.getRoot().setOnClickListener(view -> listener.onClick(new EntitySet(entitySet), childIndex));
        ((MaterialCardView) holder.binding.childName.getRootView()).setCardBackgroundColor(color);
    }

    @Override
    public void onBindGroupViewHolder(CategoryHolder holder, int flatPosition, ExpandableGroup group) {
        holder.binding.categoryIcon.setIcon(((EntityCategory) group).getCategoryIcon());
        holder.binding.categoryName.setText(group.getTitle());
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        getGroups().clear();
    }

    /*---------------------------------
    -----------------------------------
    ----------Group ViewHolder---------
    -----------------------------------
    ---------------------------------*/
    class CategoryHolder extends GroupViewHolder {

        CookGroupLayoutBinding binding;

        public CategoryHolder(CookGroupLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void expand() {
            animateExpand();
        }

        @Override
        public void collapse() {
            animateCollapse();
        }

        private void animateExpand() {
            binding.arrow.setImageDrawable(ContextCompat.getDrawable(binding.arrow.getContext(), R.drawable.ic_collapse));
        }

        private void animateCollapse() {
            binding.arrow.setImageDrawable(ContextCompat.getDrawable(binding.arrow.getContext(), R.drawable.ic_expand));
        }
    }

    /*---------------------------------
    -----------------------------------
    ----------Child ViewHolder---------
    -----------------------------------
    ---------------------------------*/
    class ChildHolder extends ChildViewHolder {

        CookChildLayoutBinding binding;

        public ChildHolder(CookChildLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setShowDetails(showDetails);
        }

    }
}
