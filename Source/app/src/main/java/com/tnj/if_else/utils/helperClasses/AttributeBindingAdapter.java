package com.tnj.if_else.utils.helperClasses;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;

import com.google.android.material.textfield.TextInputLayout;
import com.tnj.if_else.architecture.baseLevelEntities.Workflow;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

public class AttributeBindingAdapter {

    @BindingAdapter(value = {"endIconDrawable"})
    public static void setIconDrawable(TextInputLayout view, int color) {
        view.getEndIconDrawable().setTint(ContextCompat.getColor(view.getContext(), color));
    }

    @BindingAdapter(value = {"setIcon"})
    public static void setIcon(MaterialIconView view, ObservableField<String> state) {
        view.setIcon(Workflow.State.ACTIVATED.equals(state.get()) ? MaterialDrawableBuilder.IconValue.RADIOBOX_MARKED
                : MaterialDrawableBuilder.IconValue.RADIOBOX_BLANK);
    }

    @BindingAdapter(value = {"setErrorText"})
    public static void setErrorText(View view, ObservableField<String> errorMessage) {
        if ("".equals(errorMessage.get()))
            view.setVisibility(View.GONE);
        else if (view instanceof TextView) {
            ((TextView) view).setText(errorMessage.get());
            view.setVisibility(View.VISIBLE);
        }
    }
}
