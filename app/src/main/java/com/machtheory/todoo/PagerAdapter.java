package com.machtheory.todoo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

        int numberOfTabs;

        public PagerAdapter(FragmentManager fm, int numberOfTabs) {
            super(fm);
            this.numberOfTabs = numberOfTabs;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return new ToDo();
                case 1:
                    return new InProgress();
                case 2:
                    return new Done();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return numberOfTabs;
        }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
            String[] data = {"To Do", "In Progress", "Done"};
        return data[position];
    }
}


