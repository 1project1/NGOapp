package ngo.donate.project.app.donatengo.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import ngo.donate.project.app.donatengo.model.Aakash;
import ngo.donate.project.app.donatengo.model.Aman;
import ngo.donate.project.app.donatengo.model.Archit;
import ngo.donate.project.app.donatengo.model.Arup;
import ngo.donate.project.app.donatengo.model.Group;
import ngo.donate.project.app.donatengo.model.Harsh;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 6;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show Group
                return Group.newInstance();
            case 1: // Fragment # 1 - This will show Aakash
                return Aakash.newInstance();
            case 2: // Fragment # 2 - This will show Aman
                return Aman.newInstance();
            case 3: // Fragment # 3 - This will show Archit
                return Archit.newInstance();
            case 4: // Fragment # 4 - This will show Arup
                return Arup.newInstance();
            case 5: // Fragment # 5 - This will show Harsh
                return Harsh.newInstance();
            default:
                return null;
        }
    }

}
