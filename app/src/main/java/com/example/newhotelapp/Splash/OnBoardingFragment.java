package com.example.newhotelapp.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.newhotelapp.LoginSignUp.LoginActivity;
import com.example.newhotelapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingFragment extends Fragment {

    FloatingActionButton fab;

    TextView t1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding, container, false);

        fab = root.findViewById(R.id.fab);

        t1 = root.findViewById(R.id.skip);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });



        return root;
    }

}