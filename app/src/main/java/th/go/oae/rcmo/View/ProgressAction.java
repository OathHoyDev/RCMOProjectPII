package th.go.oae.rcmo.View;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import th.go.oae.rcmo.R;

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