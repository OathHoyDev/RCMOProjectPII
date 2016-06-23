package th.co.rcmo.rcmoapp.Model.calculate;

import java.util.Hashtable;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaAModel {

    public double KaNardPlangTDin = 0;

    public double KaRang = 0;
    public double KaTreamDin = 0;
    public double KaPluk = 0;
    public double KaDoolae = 0;
    public double KaGebGeaw = 0;

    public double KaWassadu = 0;
    public double KaPan = 0;
    public double KaPuy = 0;
    public double KaYaplab = 0;
    public double KaWassaduUn = 0;

    public double KaSiaOkardLongtoon = 0;
    public double KaSermOuppakorn = 0;
    public double KaSiaOkardOuppakorn = 0;

    public double PonPalid = 0;
    public double predictPrice = 0;

    public double calSumCost = 0;
    public double calIncome = 0;
    public double calProfitLoss = 0;

    public double AttraDokbia = 0;


    public double stdDepreEquip = 7.55;
    public double stdOpportEquip = 1.77;

    public static Hashtable<String , String> calculateLabel;
    static {
        Hashtable<String,String> tmp = new Hashtable<String,String>();
        tmp.put("KaNardPlangTDin","พื้นที่เพราะปลูก (ไร่)");
        tmp.put("KaRang","ค่าแรงงาน");
        tmp.put("KaTreamDin","ค่าเตรียมดิน");
        tmp.put("KaPluk","ค่าปลูก รวมค่าเตรียมพันธ์");
        tmp.put("KaDoolae","ค่าดูแลรักษา");
        tmp.put("KaGebGeaw","ค่าเก็บเกี่ยว รวบรวม");
        tmp.put("KaWassadu","ค่าวัสดุ");
        tmp.put("KaPan","ค่าพันธุ์");
        tmp.put("KaPuy","ค่าปุ๋ย");
        tmp.put("KaYaplab","ค่ายาปราบศัตรูพืชและวัชพืช");
        tmp.put("KaWassaduUn","ค่าวัสดุอื่น ๆ นำมันเชื้อเพลิง และค่าซ่อมแซมอุปกรณ์");
        tmp.put("KaSiaOkardLongtoon","เสียโอกาสเงินลงทุน");
        tmp.put("landLease","ค่าเช่าที่ดิน");
        tmp.put("KaSermOuppakorn","ค่าเสื่อมอุปกรณ์");
        tmp.put("KaSiaOkardOuppakorn","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("PonPalid","ผลผลิต ที่คาดว่าจะเก็บเกี่ยวได้ในแปลงนี้");
        tmp.put("predictPrice","ราคาที่คาดว่าจะขายได้");
        tmp.put("calSumCost","ต้นทุนรวมของเกษตรกร");
        tmp.put("calIncome","รายได้");
        tmp.put("calProfitLoss","กำไร/ขาดทุน");

        tmp.put("AttraDokbia","อัตราดอกเบี้ยร้อยละ/ปี");

        tmp.put("stdDepreEquip","ค่าเสื่อมอุปกรณ์");
        tmp.put("stdOpportEquip","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("stdCost","ต้นทุนมาตรฐานของ สศก.");

        calculateLabel = tmp;
    }

    public void calculate() {

        KaRang = KaTreamDin + KaPluk + KaDoolae + KaGebGeaw;
        KaWassadu = KaPan + KaPuy + KaYaplab + KaWassaduUn;
        KaSiaOkardLongtoon = Math.pow((KaRang + KaWassadu)*(AttraDokbia /100)*(6/12) , 2);
        KaSermOuppakorn = KaNardPlangTDin * stdDepreEquip;
        KaSiaOkardOuppakorn = KaNardPlangTDin * stdOpportEquip;

        calSumCost = KaRang + KaWassadu + KaSiaOkardLongtoon + KaSermOuppakorn + KaSiaOkardOuppakorn;
        calIncome = PonPalid * predictPrice;

        calProfitLoss = calIncome - calSumCost;
    }



}
