package com.example.gautham.dr.waterchart;

/**
 * Created by GAUTHAM on 3/24/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                WeekFragment weekFragment=new WeekFragment();
                return weekFragment;
            case 1:
                MonthFragment monthFragment=new MonthFragment();
                return  monthFragment;
            case 2:
                YearFragment yearFragment=new YearFragment();
                return yearFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
