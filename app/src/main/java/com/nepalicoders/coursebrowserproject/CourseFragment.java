package com.nepalicoders.coursebrowserproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jim on 7/6/13.
 */
public class CourseFragment extends Fragment {
  public final static String COURSE_TITLE = "course title";
  public final static String COURSE_DESCRIPTIONS = "course description";
  public final static String TOP_CARD = "top card";
  public final static String COURSE_TYPE_LOGO = "course type logo";
  public final static String COURSE_HAS_REFERENCES_LIST = "course has references list";

  private final static int COURSE_ACTION_NOT_SET = -1;
  private final static boolean COURSE_HAS_REFERENCES_LIST_NOT_SET = false;

  String mCourseTitle;
  String mCourseDescription;
  int mTopCardResourceId;
  int mCourseTypeLogoResourceId;
  boolean mCourseHasRefUrls;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View theView = inflater.inflate(R.layout.fragment_course_info, container, false);

    Bundle arguments = getArguments();
    if (arguments != null) {
      mCourseTitle = arguments.getString(COURSE_TITLE);
      mCourseDescription = arguments.getString(COURSE_DESCRIPTIONS);
      mTopCardResourceId = arguments.getInt(TOP_CARD);
      mCourseTypeLogoResourceId = arguments.getInt(COURSE_TYPE_LOGO);
      mCourseHasRefUrls =
          arguments.getBoolean(COURSE_HAS_REFERENCES_LIST,
          COURSE_HAS_REFERENCES_LIST_NOT_SET);

      displayValues(theView, mCourseTitle, mCourseDescription,
          mTopCardResourceId, mCourseTypeLogoResourceId);

    }
    return theView;
  }

  private void displayValues(View theView, String courseTitle,
                             String courseDescription, int topCardResourceId, int courseTypeLogoResourceId) {
    TextView courseTitleTextView = (TextView) theView.findViewById(R.id.courseTitle);
    TextView courseDescriptionTextView = (TextView) theView.findViewById(R.id.courseDescription);
    ImageView topCardImageView = (ImageView) theView.findViewById(R.id.topCard);
    ImageView courseTypeLogoImageView = (ImageView) theView.findViewById(R.id.courseTypeLogo);

    courseTitleTextView.setText(courseTitle);
    courseDescriptionTextView.setText(courseDescription);

    topCardImageView.setImageResource(topCardResourceId);
    courseTypeLogoImageView.setImageResource(courseTypeLogoResourceId);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    if(mCourseHasRefUrls)
      inflater.inflate(R.menu.course_refs, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    boolean handled = true;
    int courseActionResourceId = COURSE_ACTION_NOT_SET;

    switch (item.getItemId()) {
      case R.id.action_view:
        courseActionResourceId = R.string.title_action_view;
        break;
      case R.id.action_contents:
        courseActionResourceId = R.string.title_action_contents;
        break;
      case R.id.action_description:
        courseActionResourceId = R.string.title_action_description;
        break;
      case R.id.action_assessment:
        courseActionResourceId = R.string.title_action_assessment;
        break;
      case R.id.action_exercises:
        courseActionResourceId = R.string.title_action_exercises;
        break;
      case R.id.action_refs:
        courseActionResourceId = R.string.title_action_refs;
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }

    if (courseActionResourceId != COURSE_ACTION_NOT_SET)
      showActionActivity(courseActionResourceId);

    return handled;
  }

  private void showActionActivity(int courseActionResourceId) {
    Intent intent = new Intent(getActivity(), CourseActionsActivity.class);
    intent.putExtra(CourseActionsActivity.COURSE_ACTION, getString(courseActionResourceId));
    intent.putExtra(CourseActionsActivity.COURSE_TITLE, mCourseTitle);
    intent.putExtra(CourseActionsActivity.TOP_CARD, mTopCardResourceId);

    startActivity(intent);
  }
}
