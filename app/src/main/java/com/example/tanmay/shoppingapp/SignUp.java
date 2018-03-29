package com.example.tanmay.shoppingapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private Bundle step1Bundle;
    private Bundle step2Bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creating new empty bundles at launch
        step1Bundle = new Bundle();
        step2Bundle = new Bundle();

        step1(true);

    }

    public void step1(Boolean firstLaunch) {

        if (!firstLaunch) {

            EditText password = findViewById(R.id.sign_up_step_2_password);
            EditText confirmPassword = findViewById(R.id.sign_up_step_2_confirm_password);

            if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                Toast.makeText(SignUp.this, "Passwords don't match!", Toast.LENGTH_SHORT).show();
            } else {
                EditText username = findViewById(R.id.sign_up_step_2_username);

                step2Bundle.putString("username", username.getText().toString());
                step2Bundle.putString("password", password.getText().toString());

                android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

                //Inflate Step 1
                SignUpFrag1 step1 = new SignUpFrag1();
                step1.setArguments(step1Bundle);
                transaction.replace(android.R.id.content, step1);
                transaction.commit();

            }
        } else if (firstLaunch) {
            //No need to check for EditTexts
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

            //Inflate Step 1
            SignUpFrag1 step1 = new SignUpFrag1();
            step1.setArguments(step1Bundle);
            transaction.replace(android.R.id.content, step1);
            transaction.commit();
        }
    }

    public void step2() {

        //Referencing views in step1
        EditText firstName = findViewById(R.id.sign_up_fragment_1_first_name);
        EditText lastName = findViewById(R.id.sign_up_fragment_1_last_name);
        RadioButton male = findViewById(R.id.sign_up_radio_button_male);
        RadioButton female = findViewById(R.id.sign_up_radio_button_female);
        RadioButton other = findViewById(R.id.sign_up_radio_button_other);

        //Saving step 1 data
        step1Bundle.putString("firstName", firstName.getText().toString());
        step1Bundle.putString("lastName", lastName.getText().toString());
        if (male.isChecked()) {
            step1Bundle.putInt("gender", 0);
        } else if (female.isChecked()) {
            step1Bundle.putInt("gender", 1);
        } else if (other.isChecked()) {
            step1Bundle.putInt("gender", 2);
        }


        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        //Inflate Step 2
        SignUpFrag2 step2 = new SignUpFrag2();
        step2.setArguments(step2Bundle);
        transaction.replace(android.R.id.content, step2);
        transaction.commit();

    }

}
