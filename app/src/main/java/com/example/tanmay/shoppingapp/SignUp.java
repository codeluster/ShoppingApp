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

/*
    private ImageButton cancel;
    private LinearLayout nextStep;
    private SharedPreferences.Editor editor;
    private String fName;
    private String lName;
    private int gender;
    EditText firstName;
    EditText lastName;
*/

    private Bundle step1Bundle;
    private Bundle step2Bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();

        step1Bundle = new Bundle();

        //Inflate Step 1
        SignUpFrag1 step1 = new SignUpFrag1();
        step1.setArguments(step1Bundle);
        transaction.replace(android.R.id.content, step1);
        transaction.commit();

    }


    //Graveyard of deprecated code
/*
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
*/

}
