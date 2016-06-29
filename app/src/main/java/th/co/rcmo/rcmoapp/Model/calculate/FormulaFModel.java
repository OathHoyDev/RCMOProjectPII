package th.co.rcmo.rcmoapp.Model.calculate;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import th.co.rcmo.rcmoapp.Util.Util;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaFModel extends AbstractFormulaModel {

    public static List<String> listDataHeader;
    public static HashMap<String, List<String[]>> listDataChild;

    public boolean isCalIncludeOption = false;

    //Standard
    public double KaSermRongRaun = 0;
    public double KaSiaOkardRongRaun = 0;

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

    public double KaiTDaiTangTaeRoem = 0;
    public double PonPloyDai = 0;

    public double RakaTKai = 0;
    public double RaYaWeRaLeang = 0;
    public double KaSiaOkardLongtoon = 0;


    public double calCost = 0;
    public double calCostPerUnit = 0;
    public double calCostPerEgg = 0;
    public double calCostNotPonPloyDai = 0;
    public double calAllCostNotPonPloyDai =0;
    public double calCostNotPonPloyDaiPerEgg =0;
    public double calProfitLossNet = 0;
    public double calProfitLoss = 0;

    public double calAllPonPloyDai =0;
    public double calAllEgg =0;
    public double calPriceAllEgg =0;

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

       // dieRatio = ((RermLeang-JumNounTuaTKai)/RermLeang)*100;

        double costKaRangGgan = (KaRangGgan/30.42)*RaYaWeRaLeang*7;
        double costKaNamKaFai = (KaNamKaFai/30.42)*RaYaWeRaLeang*7;
        double costKaNamMan = (KaNamMan/30.42)*RaYaWeRaLeang*7;
        double costKaChoaTDin = (KaChoaTDin);

       // NamNakTKai = NamNakChaLia*JumNounTuaTKai;

        double tmpCost = (KaPan + KaAHan + KaYa + costKaRangGgan + costKaNamKaFai + costKaNamMan + KaWassaduSinPleung + KaSomRongRaun );

        //KaSiaOkardLongtoon = ((tmpCost*0.0675)/365)*RaYaWeRaLeang;
        KaSiaOkardLongtoon = Util.round((tmpCost*0.0675),2);

        double costKaSermRongRaun = KaSermRongRaun * RermLeang;
        double costKaSiaOkardRongRaun = KaSiaOkardRongRaun * RermLeang;

        Log.d("Cal","***** ค่าแรงงาน   KaSiaOkardLongtoon :"+KaSiaOkardLongtoon);
        Log.d("Cal","***** ค่าแรงงาน   costKaChoaTDin :"+costKaChoaTDin);
        Log.d("Cal","***** ค่าแรงงาน   tmpCost :"+tmpCost);

        calCost = (KaSiaOkardLongtoon + costKaChoaTDin + tmpCost);

        if (isCalIncludeOption){
            calCost += costKaSermRongRaun + costKaSiaOkardRongRaun;
        }
        Log.d("Cal","***** ค่าแรงงาน   calCost :"+ calCost);

        calAllPonPloyDai = PonPloyDai*RermLeang;
        calAllEgg        = KaiTDaiTangTaeRoem*RermLeang;
        calPriceAllEgg   = calAllEgg*RakaTKai;

        calAllCostNotPonPloyDai = calCost-calAllPonPloyDai;
        calCostPerUnit       = calCost/RermLeang;
        calCostPerEgg        = calCost/calAllEgg;
        calCostNotPonPloyDai = calCostPerUnit - PonPloyDai;
        calCostNotPonPloyDaiPerEgg = calAllCostNotPonPloyDai/calAllEgg;
        calProfitLossNet = RakaTKai-calCostNotPonPloyDaiPerEgg;
        calProfitLoss = calProfitLossNet*calAllEgg;


        Log.d("Cal","***** ค่าเสื่อมโรงเรือน :"+KaSermRongRaun);
        Log.d("Cal","***** ค่าเสียโอกาสโรงเรื่อน :"+KaSiaOkardRongRaun);

        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** ค่าแรงงาน   หลังคำนวน :"+costKaRangGgan);
        Log.d("Cal","***** ค่าน้ำ-ค่าไฟ หลังคำนวน :"+costKaNamKaFai);
        Log.d("Cal","***** ค่าน้ำมัน    หลังคำนวน :"+costKaNamMan);
        Log.d("Cal","***** ค่าเช่าที่ดิน   หลังคำนวน :"+costKaChoaTDin);
        Log.d("Cal","***** ค่าเสื่อมโรงเรือน หลังคำนวน :"+costKaSermRongRaun);
        Log.d("Cal","***** ค่าเสียโอกาสโรงเรื่อน หลังคำนวน :"+costKaSiaOkardRongRaun);
        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** ต้นทุนทั้งหมด :"+calCost);
        Log.d("Cal","***** ต้นทุนทั้งหมดเมื่อหักผลพลอยได้ :"+calAllCostNotPonPloyDai);
        Log.d("Cal","***** ต้นทุนต่อ 1 ตัว :"+calCostPerUnit);
        Log.d("Cal","***** ต้นทุนไข่ไก่ 1 ฟอง :"+calCostPerEgg);
        Log.d("Cal","***** ต้นทุนเมื่อหักผลพลอยได้ :"+calCostNotPonPloyDai);
        Log.d("Cal","***** ต้นทุนเมื่อหักผลพลอยได้ไข่ไก่ 1 ฟอง :"+calCostNotPonPloyDaiPerEgg);
        Log.d("Cal","***** กำไร-ขาดทุนสุทธิ :"+calProfitLossNet);
        Log.d("Cal","***** กำไร-ขาดทุนทั้งหมด :"+calProfitLoss);
    }

    public void prepareListData() {


    }
}
