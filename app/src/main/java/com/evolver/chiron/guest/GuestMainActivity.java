package com.evolver.chiron.guest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;
import android.view.View;

import com.evolver.chiron.R;
import com.evolver.chiron.databinding.ActivityGuestMainBinding;

import java.util.ArrayList;
import java.util.List;

public class GuestMainActivity extends AppCompatActivity {

    ActivityGuestMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuestMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        binding.tabLayout.setupWithViewPager(binding.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(new GuestDropDownListFragment(),"Search By List");
        viewPagerAdapter.addFragment(new GuestSearchByVoiceFragment(),"Search By Voice");
        binding.viewPager.setAdapter(viewPagerAdapter);

        binding.tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_tree);
        binding.tabLayout.getTabAt(1).setIcon(R.drawable.ic_mic);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }

}