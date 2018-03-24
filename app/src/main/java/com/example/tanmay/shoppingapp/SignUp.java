package com.example.tanmay.shoppingapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

public class SignUp extends AppCompatActivity {

    private ImageButton cancel;
    private LinearLayout nextStep;
    private SharedPreferences.Editor editor;
    private String fName;
    private String lName;
    private int gender;
    EditText firstName;
    EditText lastName;

    @Override
    public void onBackPressed() {

        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        cancel = findViewById(R.id.sign_up_cancel);
        nextStep = findViewById(R.id.sign_up_next_step);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Runnable nextStepRunnable = new Runnable() {
            @Override
            public void run() {
                extractData(1);
                gender = getGender();
                updateInfo(1);
            }
        };

        final Thread nextStepThread = new Thread(nextStepRunnable);

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextStepThread.run();

                android.support.v4.app.Fragment frag1 = getSupportFragmentManager().findFragmentById(R.id.sign_up_step1);

                android.support.v4.app.Fragment step2 = new SignUpFrag2();

                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.remove(frag1);

                transaction.replace(R.id.sign_up_step1, step2);

                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    private void extractData(int step) {

        if (step == 1) {

            firstName = findViewById(R.id.sign_up_fragment_1_first_name);
            lastName = findViewById(R.id.sign_up_fragment_1_last_name);

            fName = firstName.getText().toString();
            lName = lastName.getText().toString();

        }

    }

    public int getGender() {

        RadioButton male = findViewById(R.id.sign_up_radio_button_male);
        RadioButton female = findViewById(R.id.sign_up_radio_button_female);
        RadioButton other = findViewById(R.id.sign_up_radio_button_other);

        if (male.isChecked()) {
            return 0;
        } else if (female.isChecked()) {
            return 1;
        } else if (other.isChecked()) {
            return 2;
        } else {
            return -1;
        }
    }

    private void updateInfo(int step) {

        if (step == 1) {
            editor = getSharedPreferences("UserInformation", MODE_PRIVATE).edit();
            editor.putString("FirstName", fName);
            editor.putString("Last Name", lName);
            editor.putInt("Gender", gender);

            editor.apply();
        }

    }

}
