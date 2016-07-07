package th.go.oae.rcmo.Model.calculate;

import android.util.Log;

import java.util.HashMap;
import java.util.List;

public class FormulaEModel extends AbstractFormulaModel {

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
    public double RakaTKai = 0;
    public double RaYaWeRaLeang = 0;
    public double KaSiaOkardLongtoon = 0;
    public double calCost = 0;
    public double calCostPerTua = 0;
    public double calProfitLossPerTua = 0;
    public double calProfitLoss = 0;
    public double calAllRaKaTKaiDai =0;
    //  public double dieRatio = 0;
    //public double NamNakChaLia = 0;
    // public double JumNounTuaTKai = 0;
    // public double NamNakTKai = 0;
    // public double calCostPerKg = 0;


    public void calculate() {

        KaPan = RermLeang*RakaReamLeang;

       // dieRatio = ((RermLeang-JumNounTuaTKai)/RermLeang)*100;

        double costKaRangGgan = (KaRangGgan/30.42)*RaYaWeRaLeang;
        double costKaNamKaFai = (KaNamKaFai/30.42)*RaYaWeRaLeang;
        double costKaNamMan = (KaNamMan/30.42)*RaYaWeRaLeang;
        double costKaChoaTDin = (KaChoaTDin*RaYaWeRaLeang)/365;

       // NamNakTKai = NamNakChaLia*JumNounTuaTKai;

        double tmpCost = (KaPan + KaAHan + KaYa + costKaRangGgan + costKaNamKaFai + costKaNamMan + KaWassaduSinPleung + KaSomRongRaun );

        KaSiaOkardLongtoon = ((tmpCost*0.0675)/365)*RaYaWeRaLeang;

        double costKaSermRongRaun = KaSermRongRaun * RermLeang;
        double costKaSiaOkardRongRaun = KaSiaOkardRongRaun * RermLeang;

        calCost = KaSiaOkardLongtoon + costKaChoaTDin + tmpCost;

        if (isCalIncludeOption){
            calCost += costKaSermRongRaun + costKaSiaOkardRongRaun;
        }

        calCostPerTua = calCost/RermLeang;
        calProfitLossPerTua = RakaTKai-calCostPerTua;
        calAllRaKaTKaiDai =  RakaTKai*RermLeang;
        calProfitLoss = calAllRaKaTKaiDai-calCost;

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
        Log.d("Cal","***** ต้นทุนต่อ 1 ตัว :"+calCostPerTua);
        Log.d("Cal","***** กำไร-ขาดทุน ต่อ 1 ตัว :"+calProfitLossPerTua);
        Log.d("Cal","***** กำไร-ขาดทุนทั้งหมด :"+calProfitLoss);
    }

    @Override
    public void prepareListData() {
        Log.d("TEST ","Do not thing");
    }

}
