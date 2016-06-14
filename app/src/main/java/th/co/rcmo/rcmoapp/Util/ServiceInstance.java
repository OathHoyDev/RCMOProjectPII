package th.co.rcmo.rcmoapp.Util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Taweesin on 5/17/2016.
 */
public class ServiceInstance {
    public static final int version = Build.VERSION.SDK_INT;
    public static final String PREF_NAME = "RCMO";
    public static final String sp_userId    = "sp_user_name";
    public static final String sp_userName  = "sp_user_id";

    public static final String INTENT_GROUP_ID             = "GROUP_ID";
    public static final String INTENT_PROD_HIERARCHY       = "PROD_HIERARCHY";
    public static final String INTENT_SUB_GROUP_ID         = "SUB_GROUP_ID";
    public static final String INTENT_SUB_OF_SUB_GROUP_ID  = "SUB_OF_SUB_GROUP_ID";

    public static final Map<Integer, String> productBGMap = new HashMap<>();
    static {
        // key = prod group id
        productBGMap.put(1, "RcmoPlantBG");
        productBGMap.put(2, "RcmoAnimalBG");
        productBGMap.put(3, "RcmoFishBG");
    }

    public static final Map<Integer, String> productPicMap = new HashMap<>();
        static {
        // key = prod Id
        //plant 1
        productPicMap.put(1, "ic_p_1_1");
        productPicMap.put(2, "ic_p_1_1");
        productPicMap.put(3, "ic_p_1_1");
        productPicMap.put(4, "ic_p_1_1");
        productPicMap.put(5, "ic_p_1_1");
        productPicMap.put(6, "ic_p_1_1");
        productPicMap.put(7, "ic_p_1_1");
        productPicMap.put(8, "ic_p_1_1");
        productPicMap.put(9, "ic_p_1_3");
        productPicMap.put(10,"ic_p_1_4");
        productPicMap.put(11,"ic_p_1_5");
        productPicMap.put(12,"ic_p_1_6");
        productPicMap.put(13,"ic_p_1_7");
        productPicMap.put(14,"ic_p_1_8");

        //plan2
        productPicMap.put(15,"ic_p_2_1");
        productPicMap.put(16,"ic_p_2_2");
        productPicMap.put(17,"ic_p_2_3");
        productPicMap.put(18,"ic_p_2_4");
        productPicMap.put(19,"ic_p_2_4");
        productPicMap.put(20,"ic_p_2_9");
        productPicMap.put(21,"ic_p_2_8");
        productPicMap.put(22,"ic_p_2_7");
        productPicMap.put(23,"ic_p_2_6");
        productPicMap.put(24,"ic_p_2_5");
        productPicMap.put(25,"ic_p_2_10");
        productPicMap.put(26,"ic_p_2_11");
        productPicMap.put(27,"ic_p_2_12");
        productPicMap.put(28,"ic_p_2_13");
        productPicMap.put(29,"ic_p_2_14");
        productPicMap.put(30,"ic_p_2_15");
        productPicMap.put(31,"ic_p_2_17");
        productPicMap.put(32,"ic_p_2_16");
        productPicMap.put(33,"ic_p_2_16");

        //plan3
        productPicMap.put(34,"ic_p_3_1");
        productPicMap.put(35,"ic_p_3_2");
        productPicMap.put(36,"ic_p_3_3");
        productPicMap.put(37,"ic_p_3_4");

        //plan4
        productPicMap.put(38,"ic_p_4_1");

        //animal

        productPicMap.put(39,"ic_m_1");
        productPicMap.put(40,"ic_m_2");
        productPicMap.put(41,"ic_m_3");
        productPicMap.put(42,"ic_m_4");
        productPicMap.put(43,"ic_m_5");
        productPicMap.put(44,"ic_m_6");

        //fish
        productPicMap.put(45,"ic_f_1");
        productPicMap.put(46,"ic_f_2");
        productPicMap.put(47,"ic_f_4");
        productPicMap.put(48,"ic_m_4"); //not found img
        productPicMap.put(49,"ic_m_3");
    }

    public static final Map<Integer, String> productIMGMap = new HashMap<>();
    static {
        // key = prod Id
        //plant 1
        productIMGMap.put(1, "p1_1");
        productIMGMap.put(2, "p1_1");
        productIMGMap.put(3, "p1_1");
        productIMGMap.put(4, "p1_1");
        productIMGMap.put(5, "p1_1");
        productIMGMap.put(6, "p1_1");
        productIMGMap.put(7, "p1_1");
        productIMGMap.put(8, "p1_2");
        productIMGMap.put(9, "p1_3");
        productIMGMap.put(10,"p1_4");
        productIMGMap.put(11,"p1_5");
        productIMGMap.put(12,"p1_6");
        productIMGMap.put(13,"p1_7");
        productIMGMap.put(14,"p1_8");

        //plan2
        productIMGMap.put(15,"p2_1");
        productIMGMap.put(16,"p2_2");
        productIMGMap.put(17,"p2_3");
        productIMGMap.put(18,"p2_4");
        productIMGMap.put(19,"p2_4");
        productIMGMap.put(20,"p2_9");
        productIMGMap.put(21,"p2_8");
        productIMGMap.put(22,"p2_7");
        productIMGMap.put(23,"p2_6");
        productIMGMap.put(24,"p2_5");
        productIMGMap.put(25,"p2_10");
        productIMGMap.put(26,"p2_11");
        productIMGMap.put(27,"p2_12");
        productIMGMap.put(28,"p2_13");
        productIMGMap.put(29,"p2_14");
        productIMGMap.put(30,"p2_15");
        productIMGMap.put(31,"p2_16");
        productIMGMap.put(32,"p2_16");
        productIMGMap.put(33,"p2_18");

        //plan3
        productIMGMap.put(34,"p3_1");
        productIMGMap.put(35,"p3_2");
        productIMGMap.put(36,"p3_3");
        productIMGMap.put(37,"p3_4");

        //plan4
        productIMGMap.put(38,"p4_1");

        //animal

        productIMGMap.put(39,"m1");
        productIMGMap.put(40,"m2");
        productIMGMap.put(41,"m3");
        productIMGMap.put(42,"m4");
        productIMGMap.put(43,"m5");
        productIMGMap.put(44,"m6");

        //fish
        productIMGMap.put(45,"f1");
        productIMGMap.put(46,"f2");
        productIMGMap.put(47,"f4");
        productIMGMap.put(48,"f4"); //not found img
        productIMGMap.put(49,"f3");


        productIMGMap.put(1001,"p1_1");
        productIMGMap.put(1002,"p2");
        productIMGMap.put(1003,"p3");
        productIMGMap.put(1004,"p4");
    }



    public static String GetDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatStrDate(String date) {
        String[] MonthTH = {"มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม"
                , "มิถุนายน", "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"};
        if (date.length() != 0) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            try {
                Date dateLogin = format.parse(date);

                int year;
                try {
                    SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
                    year = Integer.valueOf(format_year.format(dateLogin.getTime()));
                  //  Log.i("Instance", "formatStrDate : " + dateLogin + "//" + dateLogin.getDate() + "//" + dateLogin.getYear()+"//"+year);
                } catch (Exception e) {
                    year = dateLogin.getYear();
                }

                //if (dateLogin.getYear() < 2500) year = year + 543;
                return dateLogin.getDate() + " " + MonthTH[dateLogin.getMonth()] + " " + year;

            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        } else return "";
    }
}
