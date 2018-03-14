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
public class SignUpFrag2 extends Fragment {

    boolean receed;
    FloatingActionButton back;


    public SignUpFrag2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View nono = inflater.inflate(R.layout.sign_up_frag_2, container, false);

        receed = false;

        back = nono.findViewById(R.id.sign_up_previous_step);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                receed = true;
            }
        });

        return nono;
    }

    public boolean getReceed() {

        return receed;

    }
}
