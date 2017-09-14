package com.nepalicoders.coursebrowserproject;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

/**
 * Created by Jim on 7/6/13.
 */
public class CoursePagerAdapter extends FragmentStatePagerAdapter {

  public static final int COURSE_LIB_ANDROID = 0;
  public static final int COURSE_LIB_IOS = 1;
  public static final int COURSE_LIB_WINDOWSPHONE = 2;
  String[] mCourseTitles;
  String[] mCourseTitlesShort;
  String[] mCourseDescriptions;
  int mCourseLogoResourceId;

  Context mContext;

  public CoursePagerAdapter(FragmentManager fm, Context context) {
    super(fm);
    mContext = context;
    Resources resources = mContext.getResources();
    mCourseTitles = resources.getStringArray(R.array.android_course_titles);
    mCourseTitlesShort = resources.getStringArray(R.array.android_course_titles_short);
    mCourseDescriptions = resources.getStringArray(R.array.android_course_descriptions);
    mCourseLogoResourceId = R.drawable.ps_android_logo;
  }

  public void setCourseLib(int courseLib) {
    boolean isValid = true;
    Resources resources = mContext.getResources();

    switch (courseLib) {
      case COURSE_LIB_ANDROID:
        mCourseTitles = resources.getStringArray(R.array.android_course_titles);
        mCourseTitlesShort = resources.getStringArray(R.array.android_course_titles_short);
        mCourseDescriptions = resources.getStringArray(R.array.android_course_descriptions);
        mCourseLogoResourceId = R.drawable.ps_android_logo;
        break;
      case COURSE_LIB_IOS:
        mCourseTitles = resources.getStringArray(R.array.ios_course_titles);
        mCourseTitlesShort = resources.getStringArray(R.array.ios_course_titles_short);
        mCourseDescriptions = resources.getStringArray(R.array.ios_course_descriptions);
        mCourseLogoResourceId = R.drawable.ps_ios_logo;
        break;
      case COURSE_LIB_WINDOWSPHONE:
        mCourseTitles = resources.getStringArray(R.array.windows_phone_course_titles);
        mCourseTitlesShort = resources.getStringArray(R.array.windows_phone_course_titles_short);
        mCourseDescriptions = resources.getStringArray(R.array.windows_phone_course_descriptions);
        mCourseLogoResourceId = R.drawable.ps_windows_phone_logo;
        break;
      default:
        Toast.makeText(mContext, "Invalid library name", Toast.LENGTH_LONG).show();
        isValid = false;
        break;
    }

    if(isValid)
      notifyDataSetChanged();
  }

  @Override
  public Fragment getItem(int i) {
    Bundle arguments = new Bundle();
    arguments.putString(CourseFragment.COURSE_TITLE, mCourseTitles[i]);
    arguments.putString(CourseFragment.COURSE_DESCRIPTIONS, mCourseDescriptions[i]);
    arguments.putInt(CourseFragment.TOP_CARD, ResourceHelper.translateTopCardIndex(i));
    arguments.putInt(CourseFragment.COURSE_TYPE_LOGO, mCourseLogoResourceId);
    // Use page index to simulate some courses have references and some not
    boolean hasRefs = i % 2 == 0;
    arguments.putBoolean(CourseFragment.COURSE_HAS_REFERENCES_LIST, hasRefs);

    CourseFragment courseFragment = new CourseFragment();
    courseFragment.setArguments(arguments);
    return courseFragment;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mCourseTitlesShort[position];
  }

  @Override
  public int getCount() {
    return mCourseTitles.length;
  }

  @Override
  public int getItemPosition(Object object) {
    // Causes adapter to reload all Fragments when
    //  notifyDataSetChanged is called
    return POSITION_NONE;
  }
}
