package com.example.tanmay.shoppingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    Bundle step1Bundle;
    Bundle step2Bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        step1Bundle = new Bundle();
        step2Bundle = new Bundle();

        //And "step" other than 1 or 2 gets a new Bundle
        step1(getBundle(3));

    }

    public void step1(Bundle bundle) {

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        //Inflate Step 1
        SignUpFrag1 step1 = new SignUpFrag1();
        step1.setArguments(bundle);
        transaction.replace(android.R.id.content, step1);
        transaction.commit();

    }

    public void step2(Bundle bundle) {

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        //Inflate Step 2
        SignUpFrag2 step2 = new SignUpFrag2();
        step2.setArguments(bundle);
        transaction.replace(android.R.id.content, step2);
        transaction.commit();

    }

    public void setBundle(int step, Bundle bundle) {

        switch (step) {

            case 1:
                step1Bundle = bundle;
                break;
            case 2:
                step2Bundle = bundle;
                break;

        }

    }

    public Bundle getBundle(int step) {

        switch (step) {

            case 1:
                return step1Bundle;

            case 2:
                return step2Bundle;

            default:
                return new Bundle();
        }

    }


}
