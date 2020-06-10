package com.tnj.if_else.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tnj.if_else.R;
import com.tnj.if_else.databinding.SelectedEntityLayoutBinding;
import com.tnj.if_else.utils.helperClasses.EntitySet;
import com.tnj.if_else.utils.interfaces.ContextualMenuListener;

import java.util.ArrayList;

public class EntitySelection extends RecyclerView.Adapter<EntitySelection.SelectedEntityHolder> {

    private ArrayList<EntitySet> list;
    private ContextualMenuListener contextualMenuListener;
    private Drawable iconBackground;

    public EntitySelection(ArrayList<EntitySet> actions) {
        list = actions;
    }

    public EntitySelection setContextualMenuListener(ContextualMenuListener contextualMenuListener) {
        this.contextualMenuListener = contextualMenuListener;
        return this;
    }

    public EntitySelection setColor(Context context, int color) {
        iconBackground = context.getDrawable(R.drawable.color_drawable);
        iconBackground.setColorFilter(ContextCompat.getColor(context, color), PorterDuff.Mode.SRC);
        return this;
    }

    @NonNull
    @Override
    public SelectedEntityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectedEntityHolder(SelectedEntityLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedEntityHolder holder, int position) {
        final EntitySet set = list.get(position);
        holder.binding.selectedEntityIcon.setIcon(set.icon);
        holder.binding.selectedEntityName.setText(set.name);
        holder.binding.selectedEntityIconLayout.setBackground(iconBackground);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SelectedEntityHolder extends RecyclerView.ViewHolder {

        SelectedEntityLayoutBinding binding;

        public SelectedEntityHolder(SelectedEntityLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener(view -> {
                PopupMenu menu = new PopupMenu(view.getContext(), SelectedEntityHolder.this.binding.getRoot());
                menu.inflate(R.menu.cook_contextual_menu);
                menu.show();
                menu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.delete_entity) {
                        list.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                        contextualMenuListener.onDeleteItem();
                    }
                    return true;
                });
            });
        }
    }
}
