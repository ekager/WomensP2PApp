package com.ghc2015.womensp2p;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {
    ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position){
                getActionBar().setSelectedNavigationItem(position);
            }
        });

        final ActionBar actionBar = getActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener(){
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        String[] tabTitles = new String[]{"Candidates", "Reports", "Supporters", "Add New Report"};

        for (int i = 0; i < 4; i++)
        {
            actionBar.addTab(actionBar.newTab().setText(tabTitles[i]).setTabListener(tabListener));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public int getCount(){
            return 3;
        }

        @Override
        public Fragment getItem(int position){
            return ArrayListFragment.newInstance(position);
        }
    }

    public static class ArrayListFragment extends ListFragment{
        int mNum;

        static ArrayListFragment newInstance(int num){
            ArrayListFragment f = new ArrayListFragment();
            Bundle args = new Bundle();

            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.fragment_pager_list, container, false);
            View tv = v.findViewById(R.id.text);
            ((TextView)tv).setText("Fragment #" + mNum);
            return v;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            ArrayList<String> list = new ArrayList<String>();
            list.add("t1");
            list.add("t2");
            list.add("t3");
            setListAdapter(new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, list));

        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id){
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }


}

