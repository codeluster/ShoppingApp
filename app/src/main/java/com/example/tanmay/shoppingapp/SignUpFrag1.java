package com.example.tanmay.shoppingapp;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFrag1 extends Fragment {

    FloatingActionButton next;
    boolean advance = false;


    public SignUpFrag1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_fragment_1_layout, container, false);

        next = view.findViewById(R.id.nextStepSignUp);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                advance = true;
            }
        });


        return view;
    }

    public boolean getAdvance() {
        return advance;
    }

}
