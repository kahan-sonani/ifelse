package com.tnj.if_else.activities_and_fragments.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tnj.if_else.R;

public class UserAuthentication extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.myTheme);
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }else {
            setContentView(R.layout.activity_user_authentication);
        }
    }
}
