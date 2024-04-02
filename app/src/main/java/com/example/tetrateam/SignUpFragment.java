package com.example.tetrateam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

// Sign Up Fragment
public class SignUpFragment extends Fragment {

    // variables
    EditText etUsername, etEmail, etPassword, etPhone;
    Button btnSignUp;
    User user;

    // Firebase Authentication
    FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Firebase Authentication instance
        firebaseAuth = FirebaseAuth.getInstance();

        //define variables and buttons IDs
        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPhone = view.findViewById(R.id.etPhone);

        // button to sign up the user is the data is valid
        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, phone;

                username = String.valueOf(etUsername.getText());
                email = String.valueOf(etEmail.getText());
                password = String.valueOf(etPassword.getText());
                phone = String.valueOf(etPhone.getText());

                // if fields are not valid stop the function
                if (!checkFields()) {
                    return;
                }

                // create new user with email and password and add it to the Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            // write new user to the database
                            user = new User(username, email, phone);
                            user.writeNewUser(firebaseAuth.getCurrentUser().getUid(), username, email, phone);

                            Toast.makeText(getContext(), "Authentication success", Toast.LENGTH_SHORT).show();

                            // go to sign in fragment after sign up
                            goToSignIn(view);
                        }
                    }
                });
            }
        });

        return view;
    }

    // go to sign in fragment
    public void goToSignIn(View view) {
        MainFragmentHub activity = (MainFragmentHub) getActivity();
        if (activity != null) {
            activity.goToSignIn(view);
        }
    }

    // check if fields are valid
    private boolean checkFields() {
        if (etUsername.length() == 0 || etUsername.length() > 18) {
            Toast.makeText(requireContext(), "Please enter name (less than 18 characters)", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPassword.length() < 6) {
            Toast.makeText(requireContext(), "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etEmail.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
            Toast.makeText(requireContext(), "Email doesn't match expected format (example@ex.com)", Toast.LENGTH_SHORT).show();
            return false;
        } else if (etPhone.length() != 10 || !etPhone.getText().toString().matches("[0-9]+")) {
            Toast.makeText(requireContext(), "phone number must be 10 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}