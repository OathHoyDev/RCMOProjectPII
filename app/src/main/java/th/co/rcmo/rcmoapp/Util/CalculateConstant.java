package th.co.rcmo.rcmoapp.Util;

import java.util.Collections;
import java.util.HashMap;
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
        tmp.put("CS","ต้นทุนมาตรฐาน ของ สศก.");
        CALCULATE_STANDARD_CONST_AB = tmp;
    }

    public static final Hashtable<String , String> CALCULATE_STANDARD_CONST_C;
    static{
        Hashtable<String,String> tmp =
                new Hashtable<String,String>();
        tmp.put("D","ค่าเสื่อมอุปกรณ์");
        tmp.put("O","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("CA","ต้นทุนเฉลี่ยก่อนให้ผล");
        tmp.put("CS","ต้นทุนมาตรฐาน ของ สศก.");
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

}
