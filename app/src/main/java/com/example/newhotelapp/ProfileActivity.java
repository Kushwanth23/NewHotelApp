package com.example.newhotelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newhotelapp.LoginSignUp.LoginActivity;
import com.example.newhotelapp.Model.UserModel;
import com.example.newhotelapp.databinding.ActivityProfileBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.newhotelapp.Utilities.PreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel, usernameLabel;

    Button b1,b2;

    private String name, Email, phone, Password;

    DatabaseReference reference;

    FirebaseUser user;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    PreferenceManager preferenceManager;

    TextInputEditText t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference().child("Users");

        user = FirebaseAuth.getInstance().getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_profile);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.bottom_profile:
                    return true;
            }
            return false;
        });

//        fullName = findViewById(R.id.full_name_profile);
//        email = findViewById(R.id.email_profile);
//        phoneNo = findViewById(R.id.phone_no_profile);
//        password = findViewById(R.id.password_profile);
//        fullNameLabel = findViewById(R.id.fullname_field);
//        usernameLabel = findViewById(R.id.username_field);

        t1 = binding.fullNameProfile;

        b1 = binding.update;

        b2 = binding.logout;

        b2.setOnClickListener(v -> showLogoutDialogue());

        getUserData();

//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Change the editability of the TextInputEditText view
//                t1.setFocusable(true);
//                t1.setFocusableInTouchMode(true);
//                t1.requestFocus();
//            }
//        });

    }

    private void getUserData() {
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    UserModel model = snapshot.getValue(UserModel.class);
                    if (model != null){
                        binding.fullnameField.setText(model.getUsername());
                        binding.usernameField.setText(model.getUsername());
                        binding.fullNameProfile.setText(model.getUsername());
                        binding.emailProfile.setText(model.getEmil());
                        binding.phoneNoProfile.setText(model.getPhone());
                        binding.passwordProfile.setText(model.getPassword());

                    }
                    else{
                        Toast.makeText(ProfileActivity.this, "User not exist", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showLogoutDialogue(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Are you want to logout from this account?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            AuthUI.getInstance().signOut(ProfileActivity.this)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            preferenceManager.clear();

                        }else {
                            Toast.makeText(ProfileActivity.this, "Failed: "+
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        builder.setNegativeButton("Cancel",null);

        builder.create().show();
    }


}

//        authProfile= FirebaseAuth.getInstance();
//
//        FirebaseUser firebaseUser=authProfile.getCurrentUser();
//        if(firebaseUser==null)
//        {
//            Toast.makeText(ProfileActivity.this,"Something went wrong!User details are not available ",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            progressBar.setVisibility(View.VISIBLE);
//            showUserProfile(firebaseUser);
//        }
//    }

//    private void showAllUserData(FirebaseUser firebaseUser){
//
//        String userID=firebaseUser.getUid();
//
//        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Users");
//
//        Intent intent = getIntent();
//        String user_username = intent.getStringExtra("username");
//        String user_name = intent.getStringExtra("username");
//        String user_email = intent.getStringExtra("emil");
//        String user_phoneNo = intent.getStringExtra("phone");
//        String user_password = intent.getStringExtra("password");
//
//        fullNameLabel.setText(user_name);
//        usernameLabel.setText(user_username);
//        fullName.getEditText().setText(user_name);
//        email.getEditText().setText(user_email);
//        phoneNo.getEditText().setText(user_phoneNo);
//        password.getEditText().setText(user_password);
//
//    }

//    private void showUserProfile(FirebaseUser firebaseUser){
//        String userID=firebaseUser.getUid();
//
//        //Extract user reference from database for "Registered users"
//        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Users");
//        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserModel readUserDetails=snapshot.getValue(UserModel.class);
//                if(readUserDetails!=null)
//                {
//
//                    name = readUserDetails.getUsername();
//                    Email = readUserDetails.getEmil();
//                    phone = readUserDetails.getPhone();
//                    Password = readUserDetails.getPassword();
//
//                    fullNameLabel.setText(name);
//                    usernameLabel.setText(name);
//                    fullName.getEditText().setText(name);
//                    email.getEditText().setText(Email);
//                    phoneNo.getEditText().setText(phone);
//                    password.getEditText().setText(Password);
//
//
//                    //Set user dp(After uploaded)
////                    Uri uri=firebaseUser.getPhotoUrl();
//
//                    //Imageview setImageURI() should not be used with regular URIs.So we are using picasso
////                    try {
////                        Picasso.get().load(readUserDetails.getImage()).placeholder(R.drawable.placeholder)
////                                .into(imageView);
////                    }catch (Exception e){
////                        e.printStackTrace();
////                    }
//
//                }
//                else
//                {
//                    Toast.makeText(ProfileActivity.this,"Something went wrong! ",Toast.LENGTH_LONG).show();
//                }
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(ProfileActivity.this,"Something went wrong! ",Toast.LENGTH_LONG).show();
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//
//    }
//
//}