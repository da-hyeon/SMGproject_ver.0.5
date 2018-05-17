package com.example.hdh.smgproject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by me2in on 2018. 5. 16..
 */

public class TrainerSwipeAdapter extends FragmentStatePagerAdapter {

    public TrainerSwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment TrainerPageFragment = new TrainerPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", position+1);
        TrainerPageFragment.setArguments(bundle);

        return TrainerPageFragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
