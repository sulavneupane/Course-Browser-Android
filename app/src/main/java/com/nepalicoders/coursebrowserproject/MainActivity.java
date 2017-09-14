package com.nepalicoders.coursebrowserproject;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity
  implements ListView.OnItemClickListener
{
  public static final String EXTRA_COURSE_LIB = "course lib";

  private static final int COURSE_LIB_NOT_SET = -1;

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
   * will keep every loaded fragment in memory. If this becomes too memory
   * intensive, it may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
//    SectionsPagerAdapter mSectionsPagerAdapter;
    CoursePagerAdapter mCoursePagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  ViewPager mViewPager;
  NavigationDrawerHelper mNavigationDrawerHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    // Create the adapter that will return a fragment for each of the three
    // primary sections of the app.
//        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
    mCoursePagerAdapter = new CoursePagerAdapter(getSupportFragmentManager(), this);

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.pager);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
    mViewPager.setAdapter(mCoursePagerAdapter);
    mNavigationDrawerHelper = new NavigationDrawerHelper();
    mNavigationDrawerHelper.init(this, this);

    Intent startupIntent = getIntent();
    int courseLib = startupIntent.getIntExtra(EXTRA_COURSE_LIB, COURSE_LIB_NOT_SET);
    if (courseLib != COURSE_LIB_NOT_SET) {
      mCoursePagerAdapter.setCourseLib(courseLib);
      mNavigationDrawerHelper.setSelection(courseLib);

    }

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int optionLib, long l) {
    mCoursePagerAdapter.setCourseLib(optionLib);
    mNavigationDrawerHelper.handleSelect(optionLib);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    mNavigationDrawerHelper.syncState();
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    mNavigationDrawerHelper.handleOnPrepareOptionsMenu(menu);
    return super.onPrepareOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    mNavigationDrawerHelper.handleOnOptionsItemSelected(item);
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    mNavigationDrawerHelper.syncState();
    super.onConfigurationChanged(newConfig);
  }
}
