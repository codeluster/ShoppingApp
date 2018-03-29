package com.example.tanmay.shoppingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFrag2 extends Fragment {
    SignUp signUp;
    View rootView;

    public SignUpFrag2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.sign_up_frag_2, container, false);

        LinearLayout previous = rootView.findViewById(R.id.sign_up_previous_step);

        //Restore state of widgets from previous use
        restoreState(getArguments().getBundle("bundle"), rootView);

        signUp = (SignUp) getActivity();

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Saves step2
                signUp.setBundle(2, saveState(rootView));

                //Triggers step1
                signUp.step1(signUp.getBundle(1));
            }
        });

        ImageButton cancel;

        cancel = rootView.findViewById(R.id.sign_up_cancel_step2);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Triggers onBackPressed() of the parent activity
                signUp.onBackPressed();

            }
        });

        return rootView;
    }

    private void restoreState(Bundle bundle, View view) {

        if (bundle != null) {

            if (bundle.getString("username") != null) {

                EditText username = view.findViewById(R.id.sign_up_step_2_username);
                username.setText(bundle.getString("username"));

            }

            if (bundle.getString("password") != null) {

                EditText password = view.findViewById(R.id.sign_up_step_2_password);
                EditText confPassword = view.findViewById(R.id.sign_up_step_2_confirm_password);

                password.setText(bundle.getString("password"));
                confPassword.setText(bundle.getString("password"));

            }

        }

    }

    private Bundle saveState(View view) {

        Bundle bundle = new Bundle();

        EditText username = view.findViewById(R.id.sign_up_step_2_username);
        EditText password = view.findViewById(R.id.sign_up_step_2_password);

        bundle.putString("username", username.getText().toString());
        bundle.putString("password", password.getText().toString());

        return bundle;
    }

}
