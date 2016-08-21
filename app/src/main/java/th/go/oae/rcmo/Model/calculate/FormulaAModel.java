package th.go.oae.rcmo.Model.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import th.go.oae.rcmo.Util.Util;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaAModel extends AbstractFormulaModel {

    public static List<String> listDataHeader;
    public static HashMap<String, List<String[]>> listDataChild;

    public boolean isCalIncludeOption = false;

    //public static double KaNardPlangTDin = 0;
    public static double Rai = 0;
    public static double Ngan = 0;
    public static double Wa = 0;
    public static double Meter = 0;

    public static double SumRai = 0;

    public static double KaRang = 0;
    public static double KaTreamDin = 0;
    public static double KaPluk = 0;
    public static double KaDoolae = 0;
    public static double KaGebGeaw = 0;

    public static double KaWassadu = 0;
    public static double KaPan = 0;
    public static double KaPuy = 0;
    public static double KaYaplab = 0;
    public static double KaWassaduUn = 0;

    public static double KaSiaOkardLongtoon = 0;

    public static double KaChaoTDin = 0;

    public static double PonPalid = 0;
    public static double predictPrice = 0;

    public static double calSumCost = 0;
    public static double calIncome = 0;
    public static double calProfitLoss = 0;

    public static double calSumCostPerRai = 0;
    public static double calSumCostPerKK = 0;
    public static double calIncomePerRai = 0;
    public static double calProfitLossPerRai = 0;
    public static double calProfitLossPerKK = 0;

    public static double AttraDokbia = 0;

    public static double TontumMattratarn = 0;

    public static double KaSermOuppakorn = 0;
    public static double KaSiaOkardOuppakorn = 0;
    public static double TontumMattratarnPerRai = 0;

    public static double costKaSermOuppakorn = 0;
    public static double costKaSiaOkardOuppakorn = 0;

    public static double V = 0;
    /*
    public static double KaSermOuppakorn = 7.37;
    public static double KaSiaOkardOuppakorn = 1.81;
    public static double TontumMattratarnPerRai = 4689.83;
    */

    public static Hashtable<String, String> calculateLabel;
    static {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        tmp.put("KaNardPlangTDin", "พื้นที่เพราะปลูก (ไร่)");
        tmp.put("KaRang", "ค่าแรงงาน");
        tmp.put("KaTreamDin", "ค่าเตรียมดิน");
        tmp.put("KaPluk", "ค่าปลูก รวมค่าเตรียมพันธ์");
        tmp.put("KaDoolae", "ค่าดูแลรักษา");
        tmp.put("KaGebGeaw", "ค่าเก็บเกี่ยว รวบรวม");
        tmp.put("KaWassadu", "ค่าวัสดุ");
        tmp.put("KaPan", "ค่าพันธุ์");
        tmp.put("KaPuy", "ค่าปุ๋ย");
        tmp.put("KaYaplab", "ค่ายาปราบศัตรูพืชและวัชพืช");
        tmp.put("KaWassaduUn", "ค่าวัสดุอื่น ๆ นำมันเชื้อเพลิง และค่าซ่อมแซมอุปกรณ์");
        tmp.put("KaSiaOkardLongtoon", "เสียโอกาสเงินลงทุน");
        tmp.put("KaSermOuppakorn", "ค่าเสื่อมอุปกรณ์");
        tmp.put("KaChaoTDin", "ค่าเช่าที่ดิน");
        tmp.put("KaSiaOkardOuppakorn", "ค่าเสียโอกาสอุปกรณ์");
        tmp.put("PonPalid", "ผลผลิต ที่คาดว่าจะเก็บเกี่ยวได้ในแปลงนี้");
        tmp.put("predictPrice", "ราคาที่คาดว่าจะขายได้");
        tmp.put("AttraDokbia", "อัตราดอกเบี้ยร้อยละ/ปี");

        calculateLabel = tmp;
    }

    public static Hashtable<String, String> calculateUnit;
    static {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        tmp.put("KaNardPlangTDin", "บาท");
        tmp.put("KaRang", "บาท");
        tmp.put("KaTreamDin", "บาท");
        tmp.put("KaPluk", "บาท");
        tmp.put("KaDoolae", "บาท");
        tmp.put("KaGebGeaw", "บาท");
        tmp.put("KaWassadu", "บาท");
        tmp.put("KaPan", "บาท");
        tmp.put("KaPuy", "บาท");
        tmp.put("KaYaplab", "บาท");
        tmp.put("KaWassaduUn", "บาท");
        tmp.put("KaSiaOkardLongtoon", "บาท/ไร่");
        tmp.put("KaSermOuppakorn", "บาท/ไร่");
        tmp.put("KaChaoTDin", "บาท/ไร่");
        tmp.put("KaSiaOkardOuppakorn", "บาท/ไร่");
        tmp.put("PonPalid", "กก.");
        tmp.put("predictPrice", "บาท/ตัน");
        tmp.put("AttraDokbia", "ร้อยละ/ปี");

        calculateUnit = tmp;
    }

    public void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String[]>>();

        listDataHeader.add("ค่าใช้จ่าย");
        listDataHeader.add("ผลผลิต ที่คาดว่าจะเก็บได้ในแปลงนี้");
        listDataHeader.add("ราคาที่ควรจะขายได้");
        listDataHeader.add("อัตราดอกเบี้ย ร้อยละ / ปี");

        List<String[]> cost = new ArrayList<String[]>(); // canEdit , Label , value , unit
        cost.add(new String[]{"false", calculateLabel.get("KaRang"), String.format("%,.2f", KaRang), calculateUnit.get("KaRang"), "KaRang"});
        cost.add(new String[]{"true", calculateLabel.get("KaTreamDin"), String.format("%,.2f", KaTreamDin), calculateUnit.get("KaTreamDin"), "KaTreamDin"});
        cost.add(new String[]{"true", calculateLabel.get("KaPluk"), String.format("%,.2f", KaPluk), calculateUnit.get("KaPluk"), "KaPluk"});
        cost.add(new String[]{"true", calculateLabel.get("KaDoolae"), String.format("%,.2f", KaDoolae), calculateUnit.get("KaDoolae"), "KaDoolae"});
        cost.add(new String[]{"true", calculateLabel.get("KaGebGeaw"), String.format("%,.2f", KaGebGeaw), calculateUnit.get("KaGebGeaw"), "KaGebGeaw"});
        cost.add(new String[]{"false", calculateLabel.get("KaWassadu"), String.format("%,.2f", KaWassadu), calculateUnit.get("KaWassadu"), "KaWassadu"});
        cost.add(new String[]{"true", calculateLabel.get("KaPan"), String.format("%,.2f", KaPan), calculateUnit.get("KaPan"), "KaPan"});
        cost.add(new String[]{"true", calculateLabel.get("KaPuy"), String.format("%,.2f", KaPuy), calculateUnit.get("KaPuy"), "KaPuy"});
        cost.add(new String[]{"true", calculateLabel.get("KaYaplab"), String.format("%,.2f", KaYaplab), calculateUnit.get("dieRatio"), "KaYaplab"});
        cost.add(new String[]{"true", calculateLabel.get("KaWassaduUn"), String.format("%,.2f", KaWassaduUn), calculateUnit.get("KaWassaduUn"), "KaWassaduUn"});
        cost.add(new String[]{"false", calculateLabel.get("KaSiaOkardLongtoon"), String.format("%,.2f", KaSiaOkardLongtoon), calculateUnit.get("KaSiaOkardLongtoon"), "KaSiaOkardLongtoon"});
        cost.add(new String[]{"true", calculateLabel.get("KaChaoTDin"), String.format("%,.2f", KaChaoTDin), calculateUnit.get("KaChaoTDin"), "KaChaoTDin"});
        cost.add(new String[]{"false", calculateLabel.get("KaSermOuppakorn"), String.format("%,.2f", KaSermOuppakorn), calculateUnit.get("KaSermOuppakorn"), "KaSermOuppakorn"});
        cost.add(new String[]{"false", calculateLabel.get("KaSiaOkardOuppakorn"), String.format("%,.2f", KaSiaOkardOuppakorn), calculateUnit.get("KaSiaOkardOuppakorn"), "KaSiaOkardOuppakorn"});

        List<String[]> predict = new ArrayList<String[]>();
        predict.add(new String[]{"true", "", String.format("%,.2f", PonPalid), calculateUnit.get("PonPalid"), "PonPalid"});

        List<String[]> predictPriceList = new ArrayList<String[]>();
        predictPriceList.add(new String[]{"true", "", String.format("%,.2f", predictPrice), calculateUnit.get("predictPrice"), "predictPrice"});

        List<String[]> attraDokbia = new ArrayList<String[]>();
        attraDokbia.add(new String[]{"true", "", String.format("%,.2f", AttraDokbia), calculateUnit.get("AttraDokbia"), "AttraDokbia"});

        listDataChild.put(listDataHeader.get(0), cost); // Header, Child data
        listDataChild.put(listDataHeader.get(1), predict);
        listDataChild.put(listDataHeader.get(2), predictPriceList);
        listDataChild.put(listDataHeader.get(3), attraDokbia);
    }

    public void calculate() {

        SumRai = ((Rai*4*400)+(Ngan*400)+(Wa*4)+Meter)/1600;


        KaRang = KaTreamDin + KaPluk + KaDoolae + KaGebGeaw;
        KaWassadu = KaPan + KaPuy + KaYaplab + KaWassaduUn;
        KaSiaOkardLongtoon = Util.round((KaRang + KaWassadu) * (AttraDokbia / 100) * (V/12), 2);
         costKaSermOuppakorn = SumRai * KaSermOuppakorn;
         costKaSiaOkardOuppakorn = SumRai * KaSiaOkardOuppakorn;

        calSumCost = KaRang + KaWassadu + KaSiaOkardLongtoon + KaChaoTDin ;


        if (isCalIncludeOption) {
            calSumCost += costKaSermOuppakorn + costKaSiaOkardOuppakorn;
        }


        calSumCostPerRai = calSumCost/SumRai;
        calSumCostPerRai = Util.verifyDoubleDefaultZero(calSumCostPerRai);

        calSumCostPerKK = calSumCost/PonPalid;
        calSumCostPerKK = Util.verifyDoubleDefaultZero(calSumCostPerKK);

      //  calIncome = PonPalid * (predictPrice/1000);
        calIncome = PonPalid * (predictPrice);
        calIncome = Util.verifyDoubleDefaultZero(calIncome);

        calIncomePerRai = calIncome/SumRai;
        calIncomePerRai = Util.verifyDoubleDefaultZero(calIncomePerRai);

        calProfitLoss = calIncome - calSumCost;
        calProfitLoss = Util.verifyDoubleDefaultZero(calProfitLoss);

        calProfitLossPerRai = calProfitLoss / SumRai;
        calProfitLossPerRai = Util.verifyDoubleDefaultZero(calProfitLossPerRai);

        calProfitLossPerKK = calProfitLoss/PonPalid;
        calProfitLossPerKK = Util.verifyDoubleDefaultZero(calProfitLossPerKK);

        TontumMattratarn = TontumMattratarnPerRai * SumRai;
        TontumMattratarn = Util.verifyDoubleDefaultZero(TontumMattratarn);


    }


}
