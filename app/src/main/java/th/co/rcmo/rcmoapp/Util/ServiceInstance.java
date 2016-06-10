package th.co.rcmo.rcmoapp.Util;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Taweesin on 5/17/2016.
 */
public class ServiceInstance {
    public static String GetDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
