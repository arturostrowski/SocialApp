package pl.almestinio.socialapp.ui.menuView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import pl.almestinio.socialapp.ui.loginView.LoginFragment;

/**
 * Created by mesti193 on 3/7/2018.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<Fragment>();

    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        return MenuActivity.PlaceholderFragment.newInstance(position + 1);
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }



}