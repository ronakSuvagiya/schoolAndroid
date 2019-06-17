package com.apps.smartschoolmanagement.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> fragmentList = new ArrayList();
    ArrayList<String> tabTitles = new ArrayList();

    public void addTab(Fragment fragment, String title) {
        this.tabTitles.add(title);
        this.fragmentList.add(fragment);
    }

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int pos) {
        return (Fragment) this.fragmentList.get(pos);
    }

    public CharSequence getPageTitle(int position) {
        return (CharSequence) this.tabTitles.get(position);
    }

    public int getCount() {
        return this.fragmentList.size();
    }

    public int getItemPosition(Object object) {
        return -2;
    }
}
