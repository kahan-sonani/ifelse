package com.tnj.if_else.activities_and_fragments.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.tnj.if_else.R;
import com.tnj.if_else.databinding.ActivityMainBinding;
import com.tnj.if_else.databinding.MainNavHeaderLayoutBinding;
import com.tnj.if_else.viewModels.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding controls;
    private MainActivityViewModel model;
    private NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainActivityViewModel.class);
        controls = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(controls.getRoot());
        controller = Navigation.findNavController(this, R.id.navigation_drawer_host);

        setSupportActionBar(controls.appToolbar);
        NavigationUI.setupWithNavController(controls.appToolbar, controller, controls.drawerLayout);
        NavigationUI.setupWithNavController(controls.navigationView, controller);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bindHeaderLayout();
    }

    private void bindHeaderLayout() {
        MainNavHeaderLayoutBinding drawerControls =
                MainNavHeaderLayoutBinding.inflate(getLayoutInflater(), controls.navigationView, false);

        drawerControls.getRoot().setOnClickListener(view -> {
            if (controller.getCurrentDestination().getId() != R.id.profileFragment) {
                controller.navigate(R.id.profileFragment);
                controls.drawerLayout.closeDrawer(Gravity.START, true);
            }
        });

        controls.navigationView.addHeaderView(drawerControls.getRoot());
        model.userLiveData.observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                drawerControls.userEmail.setText(firebaseUser.getEmail());
                drawerControls.userPhoto.setImageDrawable(createTextImage(firebaseUser.getEmail(), drawerControls));
            }
        });
    }

    private Drawable createTextImage(String email, MainNavHeaderLayoutBinding layout) {
        ColorGenerator generator = ColorGenerator.DEFAULT;
        return TextDrawable.builder()
                .beginConfig()
                .bold()
                .toUpperCase()
                .endConfig()
                .buildRect(String.valueOf(email.charAt(0)), generator.getRandomColor());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.app_settings == item.getItemId()) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return false;
    }
}
