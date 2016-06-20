package th.co.rcmo.rcmoapp.Util;

import android.os.Handler;

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
}
