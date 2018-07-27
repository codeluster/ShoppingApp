package com.example.tanmay.shoppingapp.Activities.SignUp;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.tanmay.shoppingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFrag1 extends Fragment {

    View rootView;
    MaterialButton next;
    SignUp signUp;

    // Required empty public constructor
    public SignUpFrag1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Inflate the view
        rootView = inflater.inflate(R.layout.sign_up_fragment_1, container, false);

        next = rootView.findViewById(R.id.sign_up_fragment_1_button_next);
        signUp = (SignUp) getActivity();
        return rootView;

    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            //Restore state of widgets from previous use
            restoreState(getArguments(), rootView);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View c) {

                signUp.setBundle(1, saveState(rootView));
                //Triggers step2
                signUp.step2(signUp.getBundle(2));
            }
        });

    }

    private void restoreState(Bundle bundle, View view) {

        if (bundle.getString("firstName") != null) {

            EditText firstName = view.findViewById(R.id.sign_up_fragment_1_first_name);
            firstName.setText(bundle.getString("firstName"));

        }
        if (bundle.getString("lastName") != null) {

            EditText lastName = view.findViewById(R.id.sign_up_fragment_1_last_name);
            lastName.setText(bundle.getString("lastName"));

        }

        int gender = bundle.getInt("gender");

        if (gender == 0 || gender == 1 || gender == 2) {

            RadioButton male = view.findViewById(R.id.sign_up_radio_button_male);
            RadioButton female = view.findViewById(R.id.sign_up_radio_button_female);
            RadioButton other = view.findViewById(R.id.sign_up_radio_button_other);

            uncheckButton(male);
            uncheckButton(female);
            uncheckButton(other);

            switch (gender) {

                case 0:
                    male.toggle();
                    break;

                case 1:
                    female.toggle();
                    break;

                case 2:
                    other.toggle();
                    break;

            }

        }
    }

    private Bundle saveState(View view) {

        Bundle bundle = new Bundle();

        //Referencing views in step1
        EditText firstName = view.findViewById(R.id.sign_up_fragment_1_first_name);
        EditText lastName = view.findViewById(R.id.sign_up_fragment_1_last_name);
        RadioButton male = view.findViewById(R.id.sign_up_radio_button_male);
        RadioButton female = view.findViewById(R.id.sign_up_radio_button_female);
        RadioButton other = view.findViewById(R.id.sign_up_radio_button_other);

        //Saving step 1 data
        bundle.putString("firstName", firstName.getText().toString());
        bundle.putString("lastName", lastName.getText().toString());
        if (male.isChecked()) {
            bundle.putInt("gender", 0);
        } else if (female.isChecked()) {
            bundle.putInt("gender", 1);
        } else if (other.isChecked()) {
            bundle.putInt("gender", 2);
        }

        return bundle;

    }

    private void uncheckButton(RadioButton radioButton) {

        if (radioButton.isChecked()) {
            radioButton.toggle();
        }

    }

}
