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
public class SignUpFrag1 extends Fragment {

    LinearLayout next;
    Bundle savedState;
    ImageButton cancel;


    public SignUpFrag1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_fragment_1_layout, container, false);
        savedState = getArguments().getBundle("savedState1");

        cancel = view.findViewById(R.id.sign_up_step_1_cancel);
        next = view.findViewById(R.id.sign_up_next_step);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCancel();
            }
        });

        return view;
    }

    public void onCancel(){}

}
