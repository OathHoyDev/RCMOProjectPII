package th.go.oae.rcmo.Model.calculate;

import android.util.Log;

import th.go.oae.rcmo.Util.Util;

public class FormulaHModel extends AbstractFormulaModel {


    public boolean isCalIncludeOption = false;

    //Standard
    public double KaSermRongRaun = 0;

    public double RermLeang  =0;
    public double NumnukRermLeang =0;;
    public double JumnuanTKai =0;;
    public double KaPan =0;
    public double KaAHanKon=0;
    public double KaAKanYab=0;
    public double KaRang=0;
    public double KaYa=0;
    public double KaNamKaFai =0;
    public double KaWassaduSinPleung=0;
    public double KaChoaTDin=0;
    public double NumnukChalia =0;
    public double RakaChalia=0;
    public double RayaWera =0;
    public double KaSiaOkardLongtoon =0;
    public double KaSiaOkardLongtoonAsset =0;


    public double calRakaTkai =0;
    public double calCost = 0;
    public double calCostPerTua = 0;
    public double calCostReturnPerTua= 0;
    public double calAllCostPerKg = 0;
    public double calAttraRak =0;
    public double calProfitLossPerKg = 0;
    public double calProfitLoss = 0;
    public double calNumnukTungmod =0;
    public double calNumnukTPuem =0;
    public double calKaChaiJaiAll =0;


    public void calculate() {
        Log.d("Cal","***** ค่าแรงงาน  :"+KaRang);
        Log.d("Cal","***** ค่าเสียโอกาสลงทุนในทรัพย์สิน.  :"+KaSiaOkardLongtoonAsset);
        double costKaRangGgan = ((KaRang/30.42)*RayaWera)/RayaWera;
        Log.d("Cal","***** คำนวนค่าแรงงาน  :"+costKaRangGgan);
        double costKaNamKaFai = (KaNamKaFai/30.42)*RayaWera;
        // mod h 08/08/2559  double costKaChoaTDin = (KaChoaTDin/365*RayaWera/JumnuanTKai);
        double costKaChoaTDin = ((KaChoaTDin*RayaWera)/365);

        double costKaSermRongRaun = Util.round((KaSermRongRaun * JumnuanTKai),2);

        calNumnukTungmod = NumnukChalia*JumnuanTKai;
        calNumnukTPuem = NumnukChalia -NumnukRermLeang;
        calRakaTkai    = RakaChalia/NumnukChalia;

        calRakaTkai = Util.verifyDoubleDefaultZero(calRakaTkai);

         calKaChaiJaiAll = Util.round((KaPan+KaAHanKon+KaAKanYab+costKaRangGgan+KaYa+costKaNamKaFai+KaWassaduSinPleung),2);

        KaSiaOkardLongtoon = ( KaPan/*C7*/   +KaAHanKon/*C8*/   +KaAKanYab/*C9*/  +costKaRangGgan/*E10*/
                               +KaYa/*C11*/  +costKaNamKaFai/*D12*/  + KaWassaduSinPleung/*C13*/ + costKaChoaTDin /*D14*/
                             )*0.0675/365*RayaWera  ;

        KaSiaOkardLongtoon = Util.verifyDoubleDefaultZero(KaSiaOkardLongtoon);
        //calCost  =Util.round((costKaSermRongRaun+KaSiaOkardLongtoon +calKaChaiJaiAll+costKaChoaTDin),2);
        calCost  =Util.round((KaSiaOkardLongtoon +calKaChaiJaiAll+costKaChoaTDin),2);


        if (isCalIncludeOption){
            // mod h 08/08/2559 calCost += costKaSermRongRaun ;
            calCost += KaSermRongRaun+KaSiaOkardLongtoonAsset ;
        }

        // mod h  calCostPerTua        = calCost/JumnuanTKai;
        calCostPerTua        = calCost/NumnukChalia;
        // mod h calCostReturnPerTua  = calCost-RakaChalia;
        calCostReturnPerTua  = RakaChalia - calCost;
        calAllCostPerKg      = calCost/calNumnukTungmod;
        calAttraRak          = calNumnukTPuem/RayaWera;
        calProfitLossPerKg   = calRakaTkai - calAllCostPerKg;
        calProfitLoss        = calProfitLossPerKg * calNumnukTungmod;

        calCostPerTua = Util.verifyDoubleDefaultZero(calCostPerTua);
        calCostReturnPerTua = Util.verifyDoubleDefaultZero(calCostReturnPerTua);
        calAllCostPerKg = Util.verifyDoubleDefaultZero(calAllCostPerKg);
        calAttraRak = Util.verifyDoubleDefaultZero(calAttraRak);
        calProfitLossPerKg = Util.verifyDoubleDefaultZero(calProfitLossPerKg);
        calProfitLoss = Util.verifyDoubleDefaultZero(calProfitLoss);

        Log.d("Cal","***** ค่าเสื่อมโรงเรือน :"+KaSermRongRaun);
        Log.d("Cal","***** ค่าใช้จ่ายทั้งหมด QT :"+calKaChaiJaiAll);
        Log.d("Cal","***** ค่าเสื่อมลงทุน :"+KaSiaOkardLongtoon);

        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** ค่าแรงงาน   หลังคำนวน :"+costKaRangGgan);
        Log.d("Cal","***** ค่าน้ำ-ค่าไฟ หลังคำนวน :"+costKaNamKaFai);
        Log.d("Cal","***** ค่าเช่าที่ดิน   หลังคำนวน :"+costKaChoaTDin);
        Log.d("Cal","***** ค่าเสื่อมโรงเรือน หลังคำนวน :"+costKaSermRongRaun);
        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** ต้นทุนทั้งหมด :"+calCost);
        Log.d("Cal","***** ต้นทุนต่อ 1 ตัว :"+calCostPerTua);
        Log.d("Cal","***** ผลตอบแทนต้นทุนทั้งหมดต่อตัว :"+calCostReturnPerTua);
        Log.d("Cal","***** ต้นทุนทั้งหมดต่อน้ำหนัก 1 กิโลกรัม :"+calAllCostPerKg);
        Log.d("Cal","***** อัตราการแลกเนื้อเฉลี่ยต่อวัน :"+calAttraRak);
        Log.d("Cal","***** กำไร-ขาดทุน 1 กิโลกรัม :"+calProfitLossPerKg);
        Log.d("Cal","***** กำไร-ขาดทุนทั้งหมด :"+calProfitLoss);
    }

    public void prepareListData() {


    }
}
