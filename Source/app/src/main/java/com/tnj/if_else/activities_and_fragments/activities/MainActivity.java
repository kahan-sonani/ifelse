package com.tnj.if_else.activities_and_fragments.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tnj.if_else.R;
import com.tnj.if_else.databinding.MainNavHeaderLayoutBinding;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        controller = Navigation.findNavController(this, R.id.navigation_drawer_host);
        NavigationUI.setupWithNavController(toolbar, controller, drawerLayout = findViewById(R.id.drawer_layout));
        NavigationUI.setupWithNavController(navigationView = findViewById(R.id.navigation_view),controller);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bindHeaderLayoutToDrawer();
    }

    private void bindHeaderLayoutToDrawer() {
        MainNavHeaderLayoutBinding drawerControls = MainNavHeaderLayoutBinding.inflate(getLayoutInflater(),
                navigationView,false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        drawerControls.userEmail.setText(user.getEmail());
        drawerControls.userPhoto.setImageDrawable(createTextImage(user.getEmail(),drawerControls));
        drawerControls.getRoot().setOnClickListener(view -> {
            if (controller.getCurrentDestination().getId() != R.id.profileFragment) {
                controller.navigate(R.id.profileFragment);
                drawerLayout.closeDrawers();
            }
        });
        navigationView.addHeaderView(drawerControls.getRoot());
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
    protected void onDestroy() {
        super.onDestroy();
        controller = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.app_settings == item.getItemId()){
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }else
            return false;
    }
}
