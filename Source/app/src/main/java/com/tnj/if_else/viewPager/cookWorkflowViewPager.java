package com.tnj.if_else.viewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tnj.if_else.activities_and_fragments.fragments.ActionFragment;
import com.tnj.if_else.activities_and_fragments.fragments.CriteriaFragment;
import com.tnj.if_else.activities_and_fragments.fragments.TriggerFragment;

public class cookWorkflowViewPager extends FragmentPagerAdapter {

    public cookWorkflowViewPager(@NonNull FragmentManager fm, int constant) {
        super(fm, constant);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TriggerFragment();
            case 1:
                return new ActionFragment();
            case 2:
                return new CriteriaFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
