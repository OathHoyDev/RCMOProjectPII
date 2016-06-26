package th.co.rcmo.rcmoapp.Model.calculate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaDModel extends AbstractFormulaModel {

    public static List<String> listDataHeader;
    public static HashMap<String, List<String[]>> listDataChild;

    public boolean isCalIncludeOption = false;

    //Standard
    public double KaSermRongRaun = 0.7;
    public double KaSiaOkardRongRaun = 0.58;

    public double RermLeang = 0;
    public double RakaReamLeang = 0;
    public double KaPan = 0;
    public double KaAHan = 0;
    public double KaYa = 0;
    public double KaRangGgan = 0;
    public double KaNamKaFai = 0;
    public double KaNamMan = 0;
    public double KaWassaduSinPleung = 0;
    public double KaSomRongRaun = 0;
    public double KaChoaTDin = 0;
    public double NamNakChaLia = 0;
    public double JumNounTuaTKai = 0;
    public double NamNakTKai = 0;
    public double RakaTKai = 0;
    public double RaYaWeRaLeang = 0;
    public double KaSiaOkardLongtoon = 0;

    public double calCost = 0;
    public double calCostPerUnit = 0;
    public double calCostPerKg = 0;
    public double calProfitLossPerKg = 0;
    public double calProfitLoss = 0;

    public double dieRatio = 0;

    public static Hashtable<String , String> calculateLabel;
    static {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        tmp.put("RermLeang","จำนวนตัวทั้งหมด");
        tmp.put("RakaReamLeang","ราคาลูกxxเมื่อเริ่มเลี้ยง");
        tmp.put("KaPan","ค่าพันธุ์ทั้งหมด");
        tmp.put("KaAHan","ค่าอาหาร");
        tmp.put("KaYa","ค่ายา");
        tmp.put("KaRangGgan","ค่าแรงงาน");
        tmp.put("KaNamKaFai","ค่าน้ำ-ค่าไฟ");
        tmp.put("KaNamMan","ค่าน้ำมัน");
        tmp.put("KaWassaduSinPleung","ค่าวัสดุสิ้นเปลือง");
        tmp.put("KaSomRongRaun","ค่าซ่อมโรงเรือง");
        tmp.put("KaChoaTDin","ค่าเช่าที่ดิน (จ่ายจริงเป็นเงินสด)");
        tmp.put("NamNakChaLia","น้ำหนักเฉลี่ยต่อตัว");
        tmp.put("JumNounTuaTKai","จำนวนตัวที่ขายทั้งหมด");
        tmp.put("NamNakTKai","น้ำหนักที่ขายได้ทั้งหมด");
        tmp.put("RakaTKai","ราคาที่เกษตรกรขายได้");
        tmp.put("RaYaWeRaLeang","ระยะเวลาเลี้ยง");
        tmp.put("KaSiaOkardLongtoon","ค่าเสียโอกาสเงินลงทุน");

        tmp.put("dieRatio","อัตราการตาย");

        calculateLabel = tmp;
    }

    public static Hashtable<String , String> calculateUnit;
    static {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        tmp.put("RermLeang","ตัว");
        tmp.put("RakaReamLeang","บาท/ตัว");
        tmp.put("KaPan","บาท/รุ่น");
        tmp.put("KaAHan","บาท/รุ่น");
        tmp.put("KaYa","บาท/รุ่น");
        tmp.put("KaRangGgan","บาท/เดือน");
        tmp.put("KaNamKaFai","บาท/เดือน");
        tmp.put("KaNamMan","บ่ท/เดือน");
        tmp.put("KaWassaduSinPleung","บาท/รุ่น");
        tmp.put("KaSomRongRaun","บาท/รุ่น");
        tmp.put("KaChoaTDin","บาท/ปี");
        tmp.put("NamNakChaLia","กิโลกรัม");
        tmp.put("JumNounTuaTKai","ตัว");
        tmp.put("NamNakTKai","กิโลกรัม");
        tmp.put("RakaTKai","บาท/กก.");
        tmp.put("RaYaWeRaLeang","วัน/รุ่น");
        tmp.put("KaSiaOkardLongtoon","บาท/รุ่น");

        tmp.put("dieRatio","%");

        calculateUnit = tmp;
    }

    public void calculate() {

        KaPan = RermLeang*RakaReamLeang;

        dieRatio = ((RermLeang-JumNounTuaTKai)/RermLeang)*100;

        double costKaRangGgan = (KaRangGgan/30.42)*RaYaWeRaLeang;
        double costKaNamKaFai = (KaNamKaFai/30.42)*RaYaWeRaLeang;
        double costKaNamMan = (KaNamMan/30.42)*RaYaWeRaLeang;
        double costKaChoaTDin = (KaChoaTDin*RaYaWeRaLeang)/365;

        NamNakTKai = NamNakChaLia*JumNounTuaTKai;

        double tmpCost = (KaPan + KaAHan + KaYa + costKaRangGgan + costKaNamKaFai + costKaNamMan + KaWassaduSinPleung + KaSomRongRaun );

        KaSiaOkardLongtoon = ((tmpCost*0.0675)/365)*RaYaWeRaLeang;

        double costKaSermRongRaun = KaSermRongRaun * RermLeang;
        double costKaSiaOkardRongRaun = KaSiaOkardRongRaun * RermLeang;

        calCost = KaSiaOkardLongtoon + costKaChoaTDin + tmpCost;

        if (isCalIncludeOption){
            calCost += costKaSermRongRaun + costKaSiaOkardRongRaun;
        }

        calCostPerUnit = calCost/JumNounTuaTKai;
        calCostPerKg = calCost/NamNakTKai;
        calProfitLossPerKg = RakaTKai-calCostPerUnit;
        calProfitLoss = calProfitLossPerKg*NamNakTKai;
    }

    public void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String[]>>();

        listDataHeader.add("ค่าใช้จ่าย");
        listDataHeader.add("อัตราการตาย");
        listDataHeader.add("อื่นๆ");

        List<String[]> cost = new ArrayList<String[]>(); // canEdit , Label , value , unit
        cost.add(new String[]{"false" , calculateLabel.get("KaPan"), String.format("%,.2f", KaPan), calculateUnit.get(KaPan) , "KaPan"});
        cost.add(new String[]{"true" , calculateLabel.get("KaAHan"), String.format("%,.2f", KaAHan), calculateUnit.get("KaAHan") , "KaAHan"});
        cost.add(new String[]{"true" , calculateLabel.get("KaYa"), String.format("%,.2f", KaYa), calculateUnit.get("KaYa") , "KaYa"});
        cost.add(new String[]{"true" , calculateLabel.get("KaRangGgan"), String.format("%,.2f", KaRangGgan), calculateUnit.get("KaRangGgan") , "KaRangGgan"});
        cost.add(new String[]{"true" , calculateLabel.get("KaNamKaFai"), String.format("%,.2f", KaNamKaFai), calculateUnit.get("KaNamKaFai") , "KaNamKaFai"});
        cost.add(new String[]{"true" , calculateLabel.get("KaNamMan"), String.format("%,.2f", KaNamMan), calculateUnit.get("KaNamMan") , "KaNamMan"});
        cost.add(new String[]{"true" , calculateLabel.get("KaWassaduSinPleung"), String.format("%,.2f", KaWassaduSinPleung), calculateUnit.get("KaWassaduSinPleung") , "KaWassaduSinPleung"});
        cost.add(new String[]{"true" , calculateLabel.get("KaSomRongRaun"), String.format("%,.2f", KaSomRongRaun), calculateUnit.get("KaSomRongRaun") , "KaSomRongRaun"});
        cost.add(new String[]{"true" , calculateLabel.get("KaChoaTDin"), String.format("%,.2f", KaChoaTDin), calculateUnit.get("KaChoaTDin") , "KaChoaTDin"});

        List<String[]> feedTimeList = new ArrayList<String[]>();
        feedTimeList.add(new String[]{"false" , calculateLabel.get("dieRatio"), String.format("%,.2f", dieRatio), calculateUnit.get("dieRatio") , "dieRatio"});

        List<String[]> sellMixSize = new ArrayList<String[]>();
        sellMixSize.add(new String[]{"true" , calculateLabel.get("NamNakChaLia"), String.format("%,.2f", NamNakChaLia), calculateUnit.get("NamNakChaLia") , "NamNakChaLia"});
        sellMixSize.add(new String[]{"true" , calculateLabel.get("JumNounTuaTKai"), String.format("%,.2f", JumNounTuaTKai), calculateUnit.get("JumNounTuaTKai") , "JumNounTuaTKai"});
        sellMixSize.add(new String[]{"False" , calculateLabel.get("NamNakChaLia"), String.format("%,.2f", NamNakChaLia), calculateUnit.get("NamNakChaLia") , "NamNakChaLia"});
        sellMixSize.add(new String[]{"true" , calculateLabel.get("RakaTKai"), String.format("%,.2f", RakaTKai), calculateUnit.get("RakaTKai") , "RakaTKai"});
        sellMixSize.add(new String[]{"true" , calculateLabel.get("RaYaWeRaLeang"), String.format("%,.2f", RaYaWeRaLeang), calculateUnit.get("RaYaWeRaLeang") , "RaYaWeRaLeang"});

        sellMixSize.add(new String[]{"false" , calculateLabel.get("KaSiaOkardLongtoon"), String.format("%,.2f", KaSiaOkardLongtoon), calculateUnit.get("KaSiaOkardLongtoon") , "KaSiaOkardLongtoon"});

        listDataChild.put(listDataHeader.get(0), cost); // Header, Child data
        listDataChild.put(listDataHeader.get(1), feedTimeList);
        listDataChild.put(listDataHeader.get(2), sellMixSize);
    }
}
