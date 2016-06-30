package th.co.rcmo.rcmoapp.Model.calculate;

import android.util.Log;

import th.co.rcmo.rcmoapp.Util.Util;

public class FormulaGModel extends AbstractFormulaModel {


    public boolean isCalIncludeOption = false;

    //Standard
    public double JumuanMaeKo ;
    public double ParimanNumnom ;
    public double KumnuanAnimanl;
    public double JumnuanKo;
    public double KoRakRakGerd;
    public double Ko1_2;
    public double Ko2;
    public double MaeKoReedNom;
   // public double MoonkaAnimalTuangNumnuk ;

    public double KumnuanTontoonNumnomChaLia;

  //  public double KumnuanTontoonPunPae ;
 //   public double KaRang;
    public double KaReedNom ;
    public double KaRangReang;
  //  public double Kawassadu ;
    public double KaPasomPan ;
    public double KaAHan ;
    public double KaAHanYab ;
    public double KaYa;
    public double KaNamKaFai ;
    public double KaNamMan ;
    public double KaWassaduSinPleung ;
    public double KaSomsamOuppakorn ;
    public double KaKonsong;
    public double KaChaiJay ;
    //public double KaSiaOkardLongtoon ;
   // public double KumnuanTontoonKongTee ;
    public double KaChaoTDin ;
    public double KaSermRongRaun;
    public double KaSermMaeKo ;
    public double KaSiaOkardRongRaun ;
    public double KaSiaOkardMaeKo ;


    //====================================================================
    public double calKoRakRakGerd;
    public double calKo1_2;
    public double calKo2;
    public double calMaeKoReedNom;
    public double calMoonkaAnimalTuangNumnuk;

    public double calKumnuanTontoonPunPae ;
    public double calKaRang;
    public double calKaReedNom ;
    public double calKaRangReang;
    public double calKawassadu ;
    public double calKaPasomPan ;
    public double calKaAHan ;
    public double calKaAHanYab ;
    public double calKaYa;
    public double calKaNamKaFai ;
    public double calKaNamMan ;
    public double calKaWassaduSinPleung ;
    public double calKaSomsamOuppakorn ;
    public double calKaKonsong;
    public double calKaChaiJay ;
    public double calKaSiaOkardLongtoon ;
    public double calKumnuanTontoonKongTee ;
    public double calKaChaoTDin ;
    public double calKaSermRongRaun;
    public double calKaSermMaeKo ;
    public double calKaSiaOkardRongRaun ;
    public double calKaSiaOkardMaeKo ;

/*
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
*/

    public void calculate() {

        //======= 1  =========
        calKoRakRakGerd = 0.4*KoRakRakGerd;
        calKo1_2        =  0.6*Ko1_2;
        calKo2          =  0.9*Ko2;
        calMaeKoReedNom =  1.0*MaeKoReedNom;

        calMoonkaAnimalTuangNumnuk = KoRakRakGerd+Ko1_2+Ko2+MaeKoReedNom;

        double fixFMoonkaAnimalTuangNumnuk = calKoRakRakGerd +calKo1_2 +calKo2 +calMaeKoReedNom;
        double fixEMoonkaAnimalTuangNumnuk = JumnuanKo/fixFMoonkaAnimalTuangNumnuk;


        // C5 = ParimanNumnom
        //E13 fixEMoonkaAnimalTuangNumnuk
        // ========  2.1.1 ========
        calKaReedNom =KaReedNom/ParimanNumnom;
        calKaRangReang=KaRangReang*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaRang=calKaReedNom+calKaRangReang;

        // ========  2.1.2 ========
        calKaPasomPan = KaPasomPan/ParimanNumnom;
        calKaAHan     = KaAHan/ParimanNumnom;
        calKaAHanYab  = KaAHanYab*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaYa       = KaYa/ParimanNumnom;
        calKaNamKaFai = KaNamKaFai*(40/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaNamMan   = KaNamMan  *(80/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaWassaduSinPleung = KaWassaduSinPleung *(30/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaSomsamOuppakorn  = KaSomsamOuppakorn  *(60/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaKonsong = KaKonsong/ParimanNumnom;;
        calKaChaiJay = KaChaiJay  *(0/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;

        calKawassadu = calKaPasomPan
                      +calKaAHan
                      +calKaAHanYab
                      +calKaYa
                      +calKaNamKaFai
                      +calKaNamMan
                      +calKaWassaduSinPleung
                      +calKaSomsamOuppakorn
                      +calKaKonsong
                      +calKaChaiJay;

        //==============2.1 ================
      // calKumnuanTontoonPunPae =;





    /*
        calKaSiaOkardLongtoon ;
        calKumnuanTontoonKongTee ;
        calKaChaoTDin ;
        calKaSermRongRaun;
        calKaSermMaeKo ;
        calKaSiaOkardRongRaun ;
        calKaSiaOkardMaeKo ;
*/

        /*
        Log.d("Cal","***** ค่าแรงงาน  :"+KaRang);
        double costKaRangGgan = (KaRang/30.42)*RayaWera;
        double costKaNamKaFai = (KaNamKaFai/30.42)*RayaWera;
        double costKaChoaTDin = (KaChoaTDin/365*RayaWera/JumnuanTKai);
        double costKaSermRongRaun = Util.round((KaSermRongRaun * JumnuanTKai),2);

        calNumnukTungmod = NumnukChalia*JumnuanTKai;
        calNumnukTPuem = NumnukChalia -NumnukRermLeang;
        calRakaTkai    = RakaChalia/NumnukChalia;

         calKaChaiJaiAll = Util.round((KaPan+KaAHanKon+KaAKanYab+costKaRangGgan+KaYa+costKaNamKaFai+KaWassaduSinPleung),2);


        calCost  =Util.round((costKaSermRongRaun+KaSiaOkardLongtoon +calKaChaiJaiAll+costKaChoaTDin),2);


        if (isCalIncludeOption){
            calCost += costKaSermRongRaun + KaSiaOkardLongtoon;
        }

        calCostPerTua        = calCost/JumnuanTKai;
        calCostReturnPerTua  = calCost-RakaChalia;
        calAllCostPerKg      = calCost/calNumnukTungmod;
        calAttraRak          = calNumnukTPuem/RayaWera;
        calProfitLossPerKg   = calRakaTkai - calAllCostPerKg;
        calProfitLoss        = calProfitLossPerKg * calNumnukTungmod;


        Log.d("Cal","***** ค่าเสื่อมโรงเรือน :"+KaSermRongRaun);
        Log.d("Cal","***** ค่าใช้จ่ายทั้งหมด QT :"+calKaChaiJaiAll);

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
        */
    }

    public void prepareListData() {


    }
}
