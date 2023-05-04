package com.example.newhotelapp.Adapters;

import androidx.fragment.app.Fragment;

public interface ScreenSlidePagerAdapter {
    Fragment getItem(int position);

    int getCount();
}
