package com.example.tanmay.shoppingapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUp extends AppCompatActivity {

    FloatingActionButton next;

    @Override
    public void onBackPressed() {
        finish();    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        inflate_step1(transaction);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflate_step2(transaction);
            }
        });


    }

    private void inflate_step1(FragmentTransaction transaction){
        transaction.replace(R.id.sign_up_fragment_container, new SignUpFrag1());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void inflate_step2(FragmentTransaction transaction){
        transaction.replace(R.id.sign_up_fragment_container, new SignUpFrag2());
        transaction.addToBackStack(null);
        transaction.commit();
    }




}
