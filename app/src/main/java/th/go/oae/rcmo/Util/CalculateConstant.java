package th.go.oae.rcmo.Util;

import java.util.Hashtable;

/**
 * Created by SilVeriSm on 6/19/2016 AD.
 */
public class CalculateConstant {

    public static final String PRODUCT_TYPE_PLANT = "1";
    public static final String PRODUCT_TYPE_ANIMAL = "2";
    public static final String PRODUCT_TYPE_FISH = "3";

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_AB;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("B","อัตราดอกเบี้ยจาก ธกส.");
        tmp.put("F","Fix Variable");
        tmp.put("D","ค่าเสื่อมอุปกรณ์");
        tmp.put("O","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("CA","ต้นทุนเฉลี่ยก่อนให้ผล");
        tmp.put("CS","ต้นทุนเฉลี่ย");
        CALCULATE_STANDARD_CONST_AB = tmp;
    }

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_C;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("D","ค่าเสื่อมอุปกรณ์");
        tmp.put("O","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("CA","ต้นทุนเฉลี่ยก่อนให้ผล");
        tmp.put("CS","ต้นทุนเฉลี่ย");
        CALCULATE_STANDARD_CONST_C = tmp;
    }

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_DEF;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("F","Fix Variable");
        tmp.put("B","อัตราดอกเบี้ยจาก ธกส.");
        tmp.put("D","ค่าเสื่อมโรงเรือน");
        tmp.put("O","ค่าเสียโอกาสโรงเรือน");
        CALCULATE_STANDARD_CONST_DEF = tmp;
    }

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_H;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("F","Fix Variable");
        tmp.put("B","อัตราดอกเบี้ยจาก ธกส.");
        tmp.put("D","ค่าเสื่อมโรงเรือน");
        CALCULATE_STANDARD_CONST_H = tmp;
    }

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_G;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("F","Fix Variable");
        tmp.put("B","อัตราดอกเบี้ยจาก ธกส.");
        tmp.put("DH","ค่าเสื่อมโรงเรือน");
        tmp.put("DD","ค่าเสื่อมแม่โคนม");
        tmp.put("OH","ค่าเสียโอกาสโรงเรือน");
        tmp.put("OD","ค่าเสียโอกาสแม่โคนม");
        CALCULATE_STANDARD_CONST_G = tmp;
    }

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_IJK;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("F","Fix Variable");
        tmp.put("B","อัตราดอกเบี้ยจาก ธกส.");
        tmp.put("DP","ค่าเสื่อมเครื่องมือและอุปกรณ์");
        tmp.put("DB","ค่าเสื่อมเครื่องมือและอุปกรณ์");
        tmp.put("OP","ค่าเสียโอกาสเครื่องมือและอุปกรณ์");
        tmp.put("OB","ค่าเสียโอกาสเครื่องมือและอุปกรณ์");
        CALCULATE_STANDARD_CONST_IJK = tmp;
    }



    //====================================================

    public static String   UNIT_BATH_RAI= "บาท/ไร่";
    public static String   UNIT_BATH_TUA= "บาท/ตัว";
    public static String   UNIT_BATH= "บาท";


    public static final Hashtable<String , String[]> PB_CALCULATE_STANDARD_CONST_AB;
    static{
        Hashtable<String,String[]> tmp =
                new Hashtable<String,String[]>();

        tmp.put("B", new String[]{"อัตราดอกเบี้ยจาก ธกส."  , UNIT_BATH_RAI});
        tmp.put("F",new String[]{"Fix Variable"            , UNIT_BATH_RAI});
        tmp.put("D",new String[]{"ค่าเสื่อมอุปกรณ์"           , UNIT_BATH_RAI});
        tmp.put("O",new String[]{"ค่าเสียโอกาสอุปกรณ์"       , UNIT_BATH_RAI});
        tmp.put("CA",new String[]{"ต้นทุนเฉลี่ยก่อนให้ผล"      , UNIT_BATH_RAI});
        tmp.put("CS",new String[]{"ต้นทุนเฉลี่ย" , UNIT_BATH_RAI});

        PB_CALCULATE_STANDARD_CONST_AB = tmp;
    }

    public static final Hashtable<String , String[]> PB_CALCULATE_STANDARD_CONST_C;
    static{
        Hashtable<String,String[]> tmp =
                new Hashtable<String,String[]>();
        tmp.put("D", new String[]{"ค่าเสื่อมอุปกรณ์"      , UNIT_BATH_RAI});
        tmp.put("O",  new String[]{"ค่าเสียโอกาสอุปกรณ์" ,UNIT_BATH_RAI});
        tmp.put("CA", new String[]{"ต้นทุนเฉลี่ยก่อนให้ผล" ,UNIT_BATH_RAI});
        tmp.put("CS", new String[]{"ต้นทุนเฉลี่ย",UNIT_BATH_RAI});
        PB_CALCULATE_STANDARD_CONST_C = tmp;
    }

    public static final Hashtable<String , String[]> PB_CALCULATE_STANDARD_CONST_DEF;
    static{
        Hashtable<String,String[]> tmp =
                new Hashtable<String,String[]>();
        tmp.put("F", new String[]{"Fix Variable"        ,UNIT_BATH_TUA});
        tmp.put("B", new String[]{"อัตราดอกเบี้ยจาก ธกส.",UNIT_BATH});
        tmp.put("D", new String[]{"ค่าเสื่อมโรงเรือน"      ,UNIT_BATH_TUA});
        tmp.put("O", new String[]{"ค่าเสียโอกาสโรงเรือน"  ,UNIT_BATH_TUA});
        PB_CALCULATE_STANDARD_CONST_DEF = tmp;
    }

    public static final Hashtable<String , String[]> PB_CALCULATE_STANDARD_CONST_H;
    static{
        Hashtable<String,String[]> tmp =
                new Hashtable<String,String[]>();
        tmp.put("F", new String[]{"Fix Variable",UNIT_BATH});
        tmp.put("B", new String[]{"อัตราดอกเบี้ยจาก ธกส.",UNIT_BATH});
        tmp.put("D", new String[]{"ค่าเสื่อมโรงเรือน",UNIT_BATH});
        tmp.put("O", new String[]{"ค่าเสียโอกาสลงทุนในทรัพย์สิน",UNIT_BATH});
        PB_CALCULATE_STANDARD_CONST_H = tmp;
    }

    public static final Hashtable<String , String[]> PB_CALCULATE_STANDARD_CONST_G;
    static{
        Hashtable<String,String[]> tmp =
                new Hashtable<String,String[]>();
        tmp.put("F", new String[]{"Fix Variable",UNIT_BATH_RAI});
        tmp.put("B", new String[]{"อัตราดอกเบี้ยจาก ธกส.",UNIT_BATH_RAI});
        tmp.put("DH", new String[]{"ค่าเสื่อมโรงเรือน","บาท/100 กก."});
        tmp.put("DD", new String[]{"ค่าเสื่อมแม่โคนม","บาท/100 กก."});
        tmp.put("OH", new String[]{"ค่าเสียโอกาสโรงเรือน","บาท/100 กก."});
        tmp.put("OD", new String[]{"ค่าเสียโอกาสแม่โคนม","บาท/100 กก."});
        PB_CALCULATE_STANDARD_CONST_G = tmp;
    }

    public static final Hashtable<String , String[]> PB_CALCULATE_STANDARD_CONST_IJK;
    static{
        Hashtable<String,String[]> tmp =
                new Hashtable<String,String[]>();
        tmp.put("F", new String[]{"Fix Variable",UNIT_BATH_RAI});
        tmp.put("B", new String[]{"อัตราดอกเบี้ยจาก ธกส.",UNIT_BATH_RAI});
        tmp.put("DP", new String[]{"ค่าเสื่อมเครื่องมือและอุปกรณ์",UNIT_BATH_RAI});
        tmp.put("DB", new String[]{"ค่าเสื่อมเครื่องมือและอุปกรณ์",UNIT_BATH_RAI});
        tmp.put("OP", new String[]{"ค่าเสียโอกาสเครื่องมือและอุปกรณ์",UNIT_BATH_RAI});
        tmp.put("OB", new String[]{"ค่าเสียโอกาสเครื่องมือและอุปกรณ์",UNIT_BATH_RAI});
        PB_CALCULATE_STANDARD_CONST_IJK = tmp;
    }

}
