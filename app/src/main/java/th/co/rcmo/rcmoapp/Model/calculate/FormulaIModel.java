package th.co.rcmo.rcmoapp.Model.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaIModel {

    public static List<String> listDataHeader;
    public static HashMap<String, List<String[]>> listDataChild;

    public boolean isCalIncludeOption = false;

    public static double Rai = 0;
    public static double Ngan = 0;
    public static double TarangWa = 0;

    public static double rookKung = 0;

    public static double RakaTuaLa = 0;
    public static double RakaPan = 0;
    public static double KaAHan = 0;
    public static double KaYa = 0;
    public static double KaSankemee = 0;
    public static double KaNamMan = 0;
    public static double KaFai = 0;
    public static double KaRokRain = 0;
    public static double KaRang = 0;
    public static double KaLeang = 0;
    public static double KaJub = 0;
    public static double KaSomsamOuppakorn = 0;
    public static double KaChaiJay = 0;
    public static double KaChaoTDin = 0;

    public static double PonPalidKung = 0;
    public static double RakaChalia = 0;
    public static double RayDaiTungmod = 0;
    public static double RayDaiChalia = 0;
    public static double RayaWelaTeeLeang = 0;
    public static double KaSiaOkardLongtoon = 0;

    public static double TontoonTungmod = 0;
    public static double TontoonTorRai = 0;
    public static double TontoonTorKilo = 0;
    public static double KumraiKadtoon = 0;
    public static double KumraiKadtoonTorRai = 0;
    public static double KumraiKadtoonTorKilo = 0;

    public static double KaSermOuppakorn = 0;
    public static double KaSiaOkardOuppakorn = 0;

//    public static Hashtable<String, String> calculateLabel;
//    static {
//        Hashtable<String, String> tmp = new Hashtable<String, String>();
//        tmp.put("KaNardPlangTDin", "พื้นที่เพราะปลูก (ไร่)");
//        tmp.put("KaRang", "ค่าแรงงาน");
//        tmp.put("KaTreamDin", "ค่าเตรียมดิน");
//        tmp.put("KaPluk", "ค่าปลูก รวมค่าเตรียมพันธ์");
//        tmp.put("KaDoolae", "ค่าดูแลรักษา");
//        tmp.put("KaGebGeaw", "ค่าเก็บเกี่ยว รวบรวม");
//        tmp.put("KaWassadu", "ค่าวัสดุ");
//        tmp.put("KaPan", "ค่าพันธุ์");
//        tmp.put("KaPuy", "ค่าปุ๋ย");
//        tmp.put("KaYaplab", "ค่ายาปราบศัตรูพืชและวัชพืช");
//        tmp.put("KaWassaduUn", "ค่าวัสดุอื่น ๆ นำมันเชื้อเพลิง และค่าซ่อมแซมอุปกรณ์");
//        tmp.put("KaSiaOkardLongtoon", "เสียโอกาสเงินลงทุน");
//        tmp.put("KaSermOuppakorn", "ค่าเสื่อมอุปกรณ์");
//        tmp.put("KaChaoTDin", "ค่าเช่าที่ดิน");
//        tmp.put("KaSiaOkardOuppakorn", "ค่าเสียโอกาสอุปกรณ์");
//        tmp.put("PonPalid", "ผลผลิต ที่คาดว่าจะเก็บเกี่ยวได้ในแปลงนี้");
//        tmp.put("predictPrice", "ราคาที่คาดว่าจะขายได้");
//        tmp.put("AttraDokbia", "อัตราดอกเบี้ยร้อยละ/ปี");
//
//        calculateLabel = tmp;
//    }
//
//    public static Hashtable<String, String> calculateUnit;
//    static {
//        Hashtable<String, String> tmp = new Hashtable<String, String>();
//        tmp.put("KaNardPlangTDin", "บาท");
//        tmp.put("KaRang", "บาท");
//        tmp.put("KaTreamDin", "บาท");
//        tmp.put("KaPluk", "บาท");
//        tmp.put("KaDoolae", "บาท");
//        tmp.put("KaGebGeaw", "บาท");
//        tmp.put("KaWassadu", "บาท");
//        tmp.put("KaPan", "บาท");
//        tmp.put("KaPuy", "บาท");
//        tmp.put("KaYaplab", "บาท");
//        tmp.put("KaWassaduUn", "บาท");
//        tmp.put("KaSiaOkardLongtoon", "บาท/ไร่");
//        tmp.put("KaSermOuppakorn", "บาท/ไร่");
//        tmp.put("KaChaoTDin", "บาท/ไร่");
//        tmp.put("KaSiaOkardOuppakorn", "บาท/ไร่");
//        tmp.put("PonPalid", "กก.");
//        tmp.put("predictPrice", "บาท/ตัน");
//        tmp.put("AttraDokbia", "ร้อยละ/ปี");
//
//        calculateUnit = tmp;
//    }
//
//    public void prepareListData() {
//
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String[]>>();
//
//        listDataHeader.add("ค่าใช้จ่าย");
//        listDataHeader.add("ผลผลิต ที่คาดว่าจะเก็บได้ในแปลงนี้");
//        listDataHeader.add("ราคาที่ควรจะขายได้");
//        listDataHeader.add("อัตราดอกเบี้ย ร้อยละ / ปี");
//
//        List<String[]> cost = new ArrayList<String[]>(); // canEdit , Label , value , unit
//        cost.add(new String[]{"false", calculateLabel.get("KaRang"), String.format("%,.2f", KaRang), calculateUnit.get("KaRang"), "KaRang"});
//        cost.add(new String[]{"true", calculateLabel.get("KaTreamDin"), String.format("%,.2f", KaTreamDin), calculateUnit.get("KaTreamDin"), "KaTreamDin"});
//        cost.add(new String[]{"true", calculateLabel.get("KaPluk"), String.format("%,.2f", KaPluk), calculateUnit.get("KaPluk"), "KaPluk"});
//        cost.add(new String[]{"true", calculateLabel.get("KaDoolae"), String.format("%,.2f", KaDoolae), calculateUnit.get("KaDoolae"), "KaDoolae"});
//        cost.add(new String[]{"true", calculateLabel.get("KaGebGeaw"), String.format("%,.2f", KaGebGeaw), calculateUnit.get("KaGebGeaw"), "KaGebGeaw"});
//        cost.add(new String[]{"false", calculateLabel.get("KaWassadu"), String.format("%,.2f", KaWassadu), calculateUnit.get("KaWassadu"), "KaWassadu"});
//        cost.add(new String[]{"true", calculateLabel.get("KaPan"), String.format("%,.2f", KaPan), calculateUnit.get("KaPan"), "KaPan"});
//        cost.add(new String[]{"true", calculateLabel.get("KaPuy"), String.format("%,.2f", KaPuy), calculateUnit.get("KaPuy"), "KaPuy"});
//        cost.add(new String[]{"true", calculateLabel.get("KaYaplab"), String.format("%,.2f", KaYaplab), calculateUnit.get("dieRatio"), "KaYaplab"});
//        cost.add(new String[]{"true", calculateLabel.get("KaWassaduUn"), String.format("%,.2f", KaWassaduUn), calculateUnit.get("KaWassaduUn"), "KaWassaduUn"});
//        cost.add(new String[]{"false", calculateLabel.get("KaSiaOkardLongtoon"), String.format("%,.2f", KaSiaOkardLongtoon), calculateUnit.get("KaSiaOkardLongtoon"), "KaSiaOkardLongtoon"});
//        cost.add(new String[]{"true", calculateLabel.get("KaChaoTDin"), String.format("%,.2f", KaChaoTDin), calculateUnit.get("KaChaoTDin"), "KaChaoTDin"});
//        cost.add(new String[]{"false", calculateLabel.get("KaSermOuppakorn"), String.format("%,.2f", KaSermOuppakorn), calculateUnit.get("KaSermOuppakorn"), "KaSermOuppakorn"});
//        cost.add(new String[]{"false", calculateLabel.get("KaSiaOkardOuppakorn"), String.format("%,.2f", KaSiaOkardOuppakorn), calculateUnit.get("KaSiaOkardOuppakorn"), "KaSiaOkardOuppakorn"});
//
//        List<String[]> predict = new ArrayList<String[]>();
//        predict.add(new String[]{"true", "", String.format("%,.2f", PonPalid), calculateUnit.get("PonPalid"), "PonPalid"});
//
//        List<String[]> predictPriceList = new ArrayList<String[]>();
//        predictPriceList.add(new String[]{"true", "", String.format("%,.2f", predictPrice), calculateUnit.get("predictPrice"), "predictPrice"});
//
//        List<String[]> attraDokbia = new ArrayList<String[]>();
//        attraDokbia.add(new String[]{"true", "", String.format("%,.2f", AttraDokbia), calculateUnit.get("AttraDokbia"), "AttraDokbia"});
//
//        listDataChild.put(listDataHeader.get(0), cost); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), predict);
//        listDataChild.put(listDataHeader.get(2), predictPriceList);
//        listDataChild.put(listDataHeader.get(3), attraDokbia);
//    }

    public void calculate() {

        TontoonTungmod = 0;

        double NueaTeeBor = ((Rai*4)*400 + (Ngan*400) + TarangWa*4)/1600;

        double calKaLeang = (KaLeang/30.42) * RayaWelaTeeLeang;

        KaRang = calKaLeang + KaJub;

        RakaPan = (RakaTuaLa / 100) * rookKung;

        double calKaChaoTDin = (KaChaoTDin/365) * NueaTeeBor;

        double calCost = RakaPan + KaAHan + KaYa + KaSankemee + KaNamMan + KaFai + KaRokRain + KaRang + KaSomsamOuppakorn + KaChaiJay;

        RayDaiTungmod = PonPalidKung * RakaChalia;

        RayDaiChalia =  RayDaiTungmod / NueaTeeBor;

        KaSiaOkardLongtoon = calCost *0.0675 * (RayaWelaTeeLeang / 365);

        TontoonTungmod = calCost + KaSiaOkardLongtoon + calKaChaoTDin;

        if (isCalIncludeOption) {
            TontoonTungmod += (KaSermOuppakorn + KaSiaOkardOuppakorn) * NueaTeeBor;
        }

        TontoonTorRai = TontoonTungmod / NueaTeeBor;
        TontoonTorKilo = TontoonTungmod / PonPalidKung;
        KumraiKadtoon = RayDaiTungmod - TontoonTungmod;
        KumraiKadtoonTorRai = RayDaiChalia - TontoonTorRai;
        KumraiKadtoonTorKilo = RakaChalia - TontoonTorKilo;

    }


}
