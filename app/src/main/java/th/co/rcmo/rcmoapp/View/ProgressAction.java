package th.co.rcmo.rcmoapp.View;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import th.co.rcmo.rcmoapp.R;

/**
 * Created by Taweesin on 6/30/2016.
 */
public class ProgressAction {
    public static void show(Context c) {
        try {
            ((Activity) c).findViewById(R.id.progress).setVisibility(View.VISIBLE);
        } catch (Exception e) {
        }
    }


    public static void gone(Context c) {
        try {
            ((Activity) c).findViewById(R.id.progress).setVisibility(View.GONE);
        } catch (Exception e) {
        }

    }
}