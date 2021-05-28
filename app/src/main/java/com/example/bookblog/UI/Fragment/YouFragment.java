package com.example.bookblog.UI.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.bookblog.R;
import com.example.bookblog.UI.Adapter.YouFragmentAdapter;
import com.example.bookblog.UtilUser.User;
import com.example.bookblog.UtilUser.UserProfile;
import com.google.android.material.tabs.TabLayout;

public class YouFragment extends Fragment {
    View myFragment;
    ViewPager viewPager;
    TabLayout tabLayout;
    TextView userName;
    ImageView userImage;
    User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_you, container,false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);
         userImage = myFragment.findViewById(R.id.user_image);
         userName = myFragment.findViewById(R.id.user_name);
         user = UserProfile.getUserInfo(getActivity());
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupWithViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        userName.setText(user.getName());
        Glide.with(this).load(user.getPictureURL()).into(userImage);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupWithViewPager(ViewPager viewPager){

        YouFragmentAdapter adapter = new YouFragmentAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new YourPostFragment(), "Your Posts");
        adapter.addFragment(new YourBookMarksFragment(), "BookMarks");
        viewPager.setAdapter(adapter);
    }
}
