package com.example.tanmay.shoppingapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        TextView name = findViewById(R.id.user_account_name);
        TextView username = findViewById(R.id.user_account_user_name);
        TextView gender = findViewById(R.id.user_account_gender);

        SharedPreferences preferences = getSharedPreferences("UserInformation", MODE_PRIVATE);

        String nameConcatenated = preferences.getString("FirstName", "John") + " " + preferences.getString("LastName", "Doe");
        name.setText(nameConcatenated);
        String usernameConcat = preferences.getString("UserName", "lol");
        username.setText(usernameConcat);
        String genderConcat;

        switch (preferences.getInt("Gender", -1)) {

            case 0:
                genderConcat = "Male";
                break;
            case 1:
                genderConcat = "Female";
                break;

            case 2:
                genderConcat = "Other";
                break;

            default:
                genderConcat = "Oops!";
                break;
        }
        gender.setText(genderConcat);


    }
}
