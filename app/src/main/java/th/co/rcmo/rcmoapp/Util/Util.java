package th.co.rcmo.rcmoapp.Util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

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
        try {
            if (input != null && !input.equals("")) {
                input = input.replaceAll(",", "");
                input = input.replaceAll("%", "");
                value = Double.parseDouble(input);
            }
        }catch(Exception e){
            e.printStackTrace();
            value = 0;
        }

        return value;
    }

    public static String dobbleToStringNumber(double input){
        return String.format("%,.2f", input);
    }

    public static String dobbleToStringNumberWithClearDigit(double input){
      //String  format =   String.format("%,.2f", input);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern("#,###,###,###.##");
        String formattedString = formatter.format(input);
        return formattedString;

    }

    public static String strToDobbleToStrFormat(String input){
      return dobbleToStringNumberWithClearDigit(strToDoubleDefaultZero(input));
    }



    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
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

    public static  String formatPrice(int i) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String yourFormattedString = formatter.format(i);

        return yourFormattedString;
    }
}
