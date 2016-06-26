package th.co.rcmo.rcmoapp.Util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import th.co.rcmo.rcmoapp.View.DialogChoice;

/**
 * Created by Taweesin on 6/20/2016.
 */
public class Util {
    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int ms, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, ms ); // afterDelay will be executed after (secs*1000) milliseconds.
    }

    public static void onLunchAnotherApp(Context context,String appPackageName) {

      //  final String appPackageName = context.getPackageName();

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(appPackageName);
        if (intent != null) {

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        } else {

            onGoToAnotherInAppStore(intent,context, appPackageName);

        }

    }

    public static void onGoToAnotherInAppStore(Intent intent,Context context, String appPackageName) {

        try {

            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + appPackageName));
            context.startActivity(intent);

        } catch (android.content.ActivityNotFoundException anfe) {

            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName));
            context.startActivity(intent);

        }

    }

    public static double strToDoubleDefaultZero(String input){
        double value = 0;
        if(input!=null && !input.equals("")) {
             value = Double.parseDouble(input);
        }
        return value;
    }


    public static void showDialogAndDismiss(Context context,String msg){
        final android.app.Dialog dialog =   new DialogChoice(context).Show(msg,"");
        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        };
        handler.postDelayed(runnable, ServiceInstance.DISMISS_DURATION_MS);
    }
}
