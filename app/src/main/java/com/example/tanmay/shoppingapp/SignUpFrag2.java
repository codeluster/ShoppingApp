package com.example.tanmay.shoppingapp;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFrag2 extends Fragment {
    SignUp signUp;

    public SignUpFrag2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.sign_up_frag_2, container, false);

        signUp = (SignUp) getActivity();

        LinearLayout previous = v.findViewById(R.id.sign_up_previous_step);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp.step1(false);
            }
        });

        ImageButton cancel;
        cancel = v.findViewById(R.id.sign_up_cancel_step2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Triggers onBackPressed() of the parent activity
                signUp.onBackPressed();

            }
        });

        return v;
    }

}
