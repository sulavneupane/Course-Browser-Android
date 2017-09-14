package com.nepalicoders.coursebrowserproject;

/**
 * Created by Jim on 10/24/13.
 */
public class ResourceHelper {
  public static int translateTopCardIndex(int i) {
    int resourceId = 0;
    switch (i) {
      case 0:
        resourceId = R.drawable.ps_top_card_01;
        break;
      case 1:
        resourceId = R.drawable.ps_top_card_02;
        break;
      case 2:
        resourceId = R.drawable.ps_top_card_03;
        break;
      case 3:
        resourceId = R.drawable.ps_top_card_04;
        break;
      case 4:
        resourceId = R.drawable.ps_top_card_05;
        break;
      case 5:
        resourceId = R.drawable.ps_top_card_06;
        break;
      case 6:
        resourceId = R.drawable.ps_top_card_07;
        break;
    }

    return resourceId;
  }
}
