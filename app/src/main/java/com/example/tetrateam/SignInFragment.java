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


public class SignInFragment extends Fragment {

    FirebaseAuth mAuth;
    Button btnSignIn;
    EditText etEmail, etPassword;
    TextView tvForgotPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);

        mAuth = FirebaseAuth.getInstance();
        btnSignIn = view.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidSignInData())
                    signIn();
            }
        });

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

    private void sendPasswordReset() {
        FirebaseManager.sendPasswordReset(etEmail.getText().toString())
                .addOnCompleteListener(task -> {
                    String msg = task.isSuccessful() ? "Please check your email address for the password reset" : task.getResult().toString();
                    showShortToast(msg);
                });
    }


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


    private void showShortToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}