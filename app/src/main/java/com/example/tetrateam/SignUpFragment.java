package com.example.tetrateam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignUpFragment extends Fragment {


    Intent intent;
    EditText etUsername, etEmail, etPassword, etPhone;
    Button btnSignUp;
    User user;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPhone = view.findViewById(R.id.etPhone);

        btnSignUp = view.findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username, email, password, phone;

                username = String.valueOf(etUsername.getText());
                email = String.valueOf(etEmail.getText());
                password = String.valueOf(etPassword.getText());
                phone = String.valueOf(etPhone.getText());

                if (!checkFields()) {
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            user = new User(username, email, phone);
                            user.writeNewUser(firebaseAuth.getCurrentUser().getUid(), username, email, phone);

                            Toast.makeText(getContext(), "Authentication success", Toast.LENGTH_SHORT).show();

                            goToSignIn(view);
                        }
                    }
                });
            }
        });

        return view;
    }

    public void goToSignIn(View view) {
        MainFragmentHub activity = (MainFragmentHub) getActivity();
        if (activity != null) {
            activity.goToSignIn(view);
        }
    }

    private boolean checkFields() {
        if (etUsername.length() == 0) {
            Toast.makeText(requireContext(), "Please enter name", Toast.LENGTH_SHORT).show();
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