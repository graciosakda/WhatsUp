package com.graciosakda.whatsup;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class IntroActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {}

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_intro, container, false);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.introImgView);
            Button loginBtn = (Button) rootView.findViewById(R.id.loginBtn);
            Button registerBtn = (Button) rootView.findViewById(R.id.registerBtn);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    imageView.setVisibility(View.VISIBLE);
                    loginBtn.setVisibility(View.INVISIBLE);
                    registerBtn.setVisibility(View.INVISIBLE);
                    imageView.setImageDrawable((Drawable)getResources().getDrawable(R.drawable.sample1));
                    break;
                case 2:
                    imageView.setVisibility(View.VISIBLE);
                    loginBtn.setVisibility(View.INVISIBLE);
                    registerBtn.setVisibility(View.INVISIBLE);

                    imageView.setImageDrawable((Drawable)getResources().getDrawable(R.drawable.sample2));
                    break;
                case 3:
                    imageView.setVisibility(View.VISIBLE);
                    loginBtn.setVisibility(View.INVISIBLE);
                    registerBtn.setVisibility(View.INVISIBLE);
                    imageView.setImageDrawable((Drawable)getResources().getDrawable(R.drawable.sample3));
                    break;
                case 4:
                    imageView.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                    registerBtn.setVisibility(View.VISIBLE);
                    break;
            }

            registerBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), RegisterActivity.class);
                    startActivity(intent);
                }
            });

            loginBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        final private int totalPages = 4;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() { return totalPages; }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
