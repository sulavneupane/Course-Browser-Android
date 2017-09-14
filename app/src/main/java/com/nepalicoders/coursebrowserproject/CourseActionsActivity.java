package com.nepalicoders.coursebrowserproject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CourseActionsActivity extends Activity
implements ListView.OnItemClickListener{
  // Intent information used when this Activity is started by an external notificaiton
  public final static String ACTION_DISPLAY_COURSE_FROM_NOTIFICATION = "com.nepalicoders.action.DISPLAY_COURSE_FROM_NOTIFICATION";
  public final static String COURSE_INDEX = "course index";

  // Intent information used when Activity is shown from within this app
  public final static String COURSE_ACTION = "course action";
  public final static String COURSE_TITLE = CourseFragment.COURSE_TITLE;
  public final static String TOP_CARD = CourseFragment.TOP_CARD;

  private final static int TOP_CARD_RESOURCE_ID = -1;

  String mCourseAction;
  String mCourseTitle;
  int mTopCardResourceId;
  boolean mIsCourseView = false;
  NavigationDrawerHelper mNavigationDrawerHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_course_actions);

    Intent startIntent = getIntent();
    String intentAction = startIntent.getAction();
    if (ACTION_DISPLAY_COURSE_FROM_NOTIFICATION.equalsIgnoreCase(intentAction)) {
      setupDisplayFromNotification(startIntent);
    }
    else {
      mCourseAction = startIntent.getStringExtra(COURSE_ACTION);
      mCourseTitle = startIntent.getStringExtra(COURSE_TITLE);
      mTopCardResourceId =
          startIntent.getIntExtra(TOP_CARD, TOP_CARD_RESOURCE_ID);
    }
    configureActionBar(mCourseAction);

    TextView courseTitleTextView = (TextView) findViewById(R.id.courseTitle);
    ImageView topCardImageView = (ImageView) findViewById(R.id.topCard);

    courseTitleTextView.setText(mCourseTitle);
    topCardImageView.setImageResource(mTopCardResourceId);

    // Are we acting as the course video view page
    mIsCourseView = mCourseAction.equalsIgnoreCase(getString(R.string.title_action_view));
    if(mIsCourseView){
      // Change background color so view page stands out
      View rootLayout = findViewById(R.id.rootView);
      rootLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
    }

    mNavigationDrawerHelper = new NavigationDrawerHelper();
    mNavigationDrawerHelper.init(this, this);

  }

  private void setupDisplayFromNotification(Intent intent) {
    int index = intent.getIntExtra(COURSE_INDEX, 0);
    Resources resources = getResources();
    String[] courseTitles = resources.getStringArray(R.array.android_course_titles);
    mCourseAction = getString(R.string.title_action_view);
    mCourseTitle = courseTitles[index];
    mTopCardResourceId = ResourceHelper.translateTopCardIndex(index);
  }


  private void configureActionBar(String courseAction) {
    ActionBar actionBar = getActionBar();
    actionBar.setTitle(courseAction);
    //actionBar.setDisplayHomeAsUpEnabled(true);

  }

  public boolean onCreateOptionsMenu(Menu menu) {
    // Our only menu option is to view the course, so only show
    //  the menu if we're not already on the view screen
    if(!mIsCourseView)
      getMenuInflater().inflate(R.menu.course_info, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    boolean handled = true;

    mNavigationDrawerHelper.handleOnOptionsItemSelected(item);
    switch (item.getItemId()) {
      case R.id.action_view:
        showVideoActionActivity();
        break;
      default:
        handled = super.onOptionsItemSelected(item);

    }

    return handled;
  }

  private void showVideoActionActivity() {
    Intent intent = new Intent(this, CourseActionsActivity.class);
    intent.putExtra(CourseActionsActivity.COURSE_ACTION, getString(R.string.title_action_view));
    intent.putExtra(CourseActionsActivity.COURSE_TITLE, mCourseTitle);
    intent.putExtra(CourseActionsActivity.TOP_CARD, mTopCardResourceId);

    startActivity(intent);
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int optionLib, long l) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra(MainActivity.EXTRA_COURSE_LIB, optionLib);
    startActivity(intent);


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
  public void onConfigurationChanged(Configuration newConfig) {
    mNavigationDrawerHelper.syncState();
    super.onConfigurationChanged(newConfig);
  }

}
