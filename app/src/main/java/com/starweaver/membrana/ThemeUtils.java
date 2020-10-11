package com.mycompany.myapp81;

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils
{
private static int cTheme;
public final static int BLACK = 0;
public final static int BLUE = 1;

public static void changeToTheme(Activity activity, int theme)
{
cTheme = theme;
activity.finish();
activity.startActivity(new Intent(activity, activity.getClass()));
}

/*
 @Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) 
 {
 if (requestCode == SETTINGS_ACTION) 
 {
 if (resultCode == ThemePreferenceActivity.RESULT_CODE_THEME_UPDATED) 
 {
 finish();
 startActivity(getIntent());
 return;
 }
 }
 super.onActivityResult(requestCode, resultCode, data);
 }
 */

public static void onActivityCreateSetTheme(Activity activity)
{
switch (cTheme)
{
default:
case BLACK:
activity.setTheme(R.style.BlackTheme);
break;

case BLUE:
activity.setTheme(R.style.BlueTheme);
break;
}
}
}

