package com.example.newhotelapp.LoginSignUp;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newhotelapp.MainActivity;
import com.example.newhotelapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginTabFragment extends Fragment {

    EditText email, password;
    TextView login;
    Button loginBtn, forgetPassword;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.email);
        password = root.findViewById(R.id.password);
        forgetPassword = root.findViewById(R.id.forgotPassword);
        loginBtn = root.findViewById(R.id.login);

        mAuth = FirebaseAuth.getInstance();

        forgetPassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EmailResetActivity.class));
        });

        loginBtn.setOnClickListener(view -> signIn());

//        loginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userEmail = email.getText().toString().trim();
//                String userPassword = password.getText().toString().trim();
//
//                if (userEmail.isEmpty()) {
//                    email.setError("Email is required");
//                    email.requestFocus();
//                    return;
//                }
//
//                if (userPassword.isEmpty()) {
//                    password.setError("Password is required");
//                    password.requestFocus();
//                    return;
//                }
//
//                mAuth.signInWithEmailAndPassword(userEmail, userPassword)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(getActivity(), "Login failed. Please try again later.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });



        email.setTranslationX(800);
        password.setTranslationX(800);
        forgetPassword.setTranslationX(800);
        loginBtn.setTranslationX(800);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forgetPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        loginBtn.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }

    private void signIn() {
        Log.d(TAG, "signIn" + email);
        if (!validateForm()) {
            return;
        }

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Logging...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getActivity(), "Login failed. Please try again later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String em = email.getText().toString();
        if (TextUtils.isEmpty(em)) {
            email.setError("Required.");
            valid=false;
        } else {
            email.setError(null);
        }

        String pw = password.getText().toString();
        if (TextUtils.isEmpty(pw)) {
            password.setError("Required.");
            valid=false;
        } else {
            password.setError(null);
        }

        return valid;
    }
}
