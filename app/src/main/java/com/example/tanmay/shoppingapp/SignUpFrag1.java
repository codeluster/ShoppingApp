package com.example.tanmay.shoppingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFrag1 extends Fragment {


    // Required empty public constructor
    public SignUpFrag1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_fragment_1_layout, container, false);

        Bundle bundle = getArguments().getBundle("step1Bundle");

        LinearLayout next;
        final SignUp signUp;

        if (bundle != null) {
            if (bundle.getString("firstName") != null) {
                EditText firstName = (EditText) view.findViewById(R.id.sign_up_fragment_1_first_name);
                firstName.setText(bundle.getString("firstName"));
            }
            if (bundle.getString("lastName") != null) {
                EditText lastName = (EditText) view.findViewById(R.id.sign_up_fragment_1_last_name);
                lastName.setText(bundle.getString("firstName"));
            }
            if (bundle.getInt("gender") == 0 || bundle.getInt("gender") == 1 || bundle.getInt("gender") == 2) {

                switch (bundle.getInt("gender")) {

                    case 0:
                        RadioButton male = view.findViewById(R.id.sign_up_radio_button_male);
                        male.toggle();

                    case 1:
                        RadioButton female = view.findViewById(R.id.sign_up_radio_button_female);
                        female.toggle();

                    case 2:
                        RadioButton other = view.findViewById(R.id.sign_up_radio_button_other);
                        other.toggle();

                }

            }
        }

        next = view.findViewById(R.id.sign_up_next_step);
        signUp = (SignUp) getActivity();

        ;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Proceeds to step 2
                signUp.step2();
            }
        });

        return view;

    }


}
