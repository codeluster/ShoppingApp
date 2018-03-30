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

        String nameString = preferences.getString("FirstName", "") + " " + preferences.getString("LastName", "Doe");
        String usernameString = preferences.getString("UserName", "");
        String genderString;
        switch (preferences.getInt("Gender", -1)) {

            case 0:
                genderString = "Male";
                break;
            case 1:
                genderString = "Female";
                break;

            case 2:
                genderString = "Other";
                break;

            default:
                genderString = "";
                break;
        }

        name.setText(nameString);
        username.setText(usernameString);
        gender.setText(genderString);


    }
}
