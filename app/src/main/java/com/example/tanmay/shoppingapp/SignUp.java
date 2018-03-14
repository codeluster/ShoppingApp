package com.example.tanmay.shoppingapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class SignUp extends AppCompatActivity {

    FloatingActionButton previous;
    boolean advance = false;
    boolean receed = false;
    SignUpFrag1 frag1;
    SignUpFrag2 frag2;
    LinearLayout container;
    FragmentManager fragmentManager;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        frag1 = new SignUpFrag1();

        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        inflate_step1(transaction);
    }

    private void refreshProgress() {

        advance = frag1.getAdvance();
        receed = frag2.getReceed();

    }

    private void inflate_step1(FragmentTransaction transaction) {
        transaction.replace(R.id.sign_up_fragment_container, new SignUpFrag1());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void inflate_step2(FragmentTransaction transaction) {
        transaction.replace(R.id.sign_up_fragment_container, new SignUpFrag2());
        transaction.addToBackStack(null);
        transaction.commit();
        advance = false;
    }

}
