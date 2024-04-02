package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

// Sign In Fragment
public class SignInFragment extends Fragment {

    // variables
    Button btnSignIn;
    EditText etEmail, etPassword;
    TextView tvForgotPassword;

    // Firebase Authentication
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();

        // button to sign in the user is the data is valid
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidSignInData())
                    signIn();
            }
        });

        // button to send a password reset to the user email if the email is valid
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword);
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidEmail())
                    sendPasswordReset();
            }
        });
        return view;
    }

    // function to send a password reset
    private void sendPasswordReset() {
        FirebaseManager.sendPasswordReset(etEmail.getText().toString())
                .addOnCompleteListener(task -> {
                    String msg = task.isSuccessful() ? "Please check your email address for the password reset" : task.getResult().toString();
                    showShortToast(msg);
                });
    }

    // function to sign in
    private void signIn() {
        FirebaseManager.signIn(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Intent mainActivityIntent = new Intent(getActivity(), GameMenuActivity.class);
                        startActivity(mainActivityIntent);
                    } else
                        showShortToast(task.getException().getMessage());
                });
    }

    // function that returns true if the data is valid else false
    private boolean isValidSignInData() {
        String password = etEmail.getText().toString();
        if (!isValidEmail())
            return false;
        if (password.isEmpty()) {
            showShortToast("Please enter your password");
            return false;
        }
        return true;
    }

    // function that returns true if the email is valid else false
    private boolean isValidEmail() {
        String emailAddress = etEmail.getText().toString();
        if (emailAddress.isEmpty()) {
            showShortToast("Please enter your email address");
            return false;
        }
        //Use builtin regex to determine if is valid address
        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            showShortToast("Please enter a valid email address");
            return false;
        }
        return true;
    }

    // function to show a short toast (to make it easier to write Toast code)
    private void showShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}