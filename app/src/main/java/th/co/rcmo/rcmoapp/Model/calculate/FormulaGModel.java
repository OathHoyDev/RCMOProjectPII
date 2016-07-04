package th.co.rcmo.rcmoapp.Model.calculate;

import android.util.Log;

import th.co.rcmo.rcmoapp.Util.Util;

public class FormulaGModel extends AbstractFormulaModel {


    public boolean isCalIncludeOption = false;

    //Standard
    public double JumuanMaeKo = 0;
    public double ParimanNumnom = 0;
   // public double KumnuanAnimanl;
   // public double JumnuanKo;
    public double KoRakRakGerd = 0;
    public double Ko1_2 = 0;
    public double Ko2 = 0;
    public double MaeKoReedNom = 0;
   // public double MoonkaAnimalTuangNumnuk ;

  //  public double KumnuanTontoonNumnomChaLia;

  //  public double KumnuanTontoonPunPae ;
 //   public double KaRang;
    public double KaReedNom = 0;
    public double KaRangReang= 0;
  //  public double Kawassadu ;
    public double KaPasomPan = 0;
    public double KaAHan = 0;
    public double KaAHanYab= 0;
    public double KaYa= 0;
    public double KaNamKaFai = 0;
    public double KaNamMan = 0;
    public double KaWassaduSinPleung = 0;
    public double KaSomsamOuppakorn = 0;
    public double KaKonsong= 0;
    public double KaChaiJay= 0 ;
    //public double KaSiaOkardLongtoon ;
   // public double KumnuanTontoonKongTee ;
    public double KaChaoTDin= 0 ;
    public double KaSermRongRaun= 0;
    public double KaSermMaeKo = 0;
    public double KaSiaOkardRongRaun = 0;
    public double KaSiaOkardMaeKo= 0;

    public double PerKaNamKaFai= 0;
    public double PerKaNamMan= 0;
    public double PerKaWassaduSinPleung= 0;
    public double PerKaSomsamOuppakorn= 0;
    public double PerKaChaiJay= 0;
    public double RakaTkai= 0;


    //====================================================================
    public double calKoRakRakGerd= 0;
    public double calKo1_2= 0;
    public double calKo2= 0;
    public double calMaeKoReedNom= 0;
    public double calMoonkaAnimalTuangNumnuk= 0;

    public double calKumnuanTontoonPunPae= 0 ;
    public double calKaRang= 0;
    public double calKaReedNom = 0;
    public double calKaRangReang= 0;
    public double calKawassadu = 0;
    public double calKaPasomPan = 0;
    public double calKaAHan = 0;
    public double calKaAHanYab = 0;
    public double calKaYa= 0;
    public double calKaNamKaFai = 0;
    public double calKaNamMan = 0;
    public double calKaWassaduSinPleung = 0;
    public double calKaSomsamOuppakorn= 0 ;
    public double calKaKonsong= 0;
    public double calKaChaiJay= 0 ;
    public double calKaSiaOkardLongtoon = 0;
    public double calKumnuanTontoonKongTee= 0 ;
    public double calKaChaoTDin = 0;
    public double calKaSermRongRaun= 0;
    public double calKaSermMaeKo= 0 ;
    public double calKaSiaOkardRongRaun = 0;
    public double calKaSiaOkardMaeKo= 0 ;
    public double calCost = 0;
    public double costTontunPalitNamnomPerKg = 0;
    public double calProfitLoss = 0;
    public double calNamnomTReedCharia = 0;
    public double calAllSalePrice   = 0;



    public void calculate() {

        //======= 1  =========
        calKoRakRakGerd = 0.4*KoRakRakGerd;
        calKo1_2        =  0.6*Ko1_2;
        calKo2          =  0.9*Ko2;
        calMaeKoReedNom =  1.0*MaeKoReedNom;

        calMoonkaAnimalTuangNumnuk = KoRakRakGerd+Ko1_2+Ko2+MaeKoReedNom;

        double fixFMoonkaAnimalTuangNumnuk = calKoRakRakGerd +calKo1_2 +calKo2 +calMaeKoReedNom;
        double fixEMoonkaAnimalTuangNumnuk = Util.round(JumuanMaeKo/fixFMoonkaAnimalTuangNumnuk,2);


        // C5 = ParimanNumnom
        //E13 fixEMoonkaAnimalTuangNumnuk
        // ========  2.1.1 ========
        calKaReedNom =KaReedNom/ParimanNumnom;
        calKaRangReang=KaRangReang*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;

        //F18
        calKaRang=calKaReedNom+calKaRangReang;

        // ========  2.1.2  ========
        calKaPasomPan = KaPasomPan/ParimanNumnom;
        calKaAHan     = KaAHan/ParimanNumnom;
        calKaAHanYab  = KaAHanYab*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaYa       = KaYa/ParimanNumnom;
        calKaNamKaFai = KaNamKaFai*(PerKaNamKaFai/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaNamMan   = KaNamMan  *(PerKaNamMan/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaWassaduSinPleung = KaWassaduSinPleung *(PerKaWassaduSinPleung/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaSomsamOuppakorn  = KaSomsamOuppakorn  *(PerKaSomsamOuppakorn/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;
        calKaKonsong = KaKonsong/ParimanNumnom;;
        calKaChaiJay = KaChaiJay  *(PerKaChaiJay/100)*fixEMoonkaAnimalTuangNumnuk/ParimanNumnom;


        //F21
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

        //============== 2.1.3 ==============
        //F32
        Log.d("Cal","***** calKaRang :"+calKaRang);
        Log.d("Cal","***** calKawassadu :"+calKawassadu);
        calKaSiaOkardLongtoon = Util.round(((calKaRang+calKawassadu)*(0.07))* (30.42/365),2);
        Log.d("Cal","***** calKaSiaOkardLongtoon :"+(calKaRang+calKawassadu)*(0.07));

        //============== 2.1 ================
        //F17
       calKumnuanTontoonPunPae = calKaRang+calKawassadu+calKaSiaOkardLongtoon;


        //============== 2.2 ===============
        calKaChaoTDin     = KaChaoTDin/365*30.42/ParimanNumnom;
        calKaSermRongRaun = KaSermRongRaun/100;
        calKaSermMaeKo    = KaSermMaeKo/100;
        calKaSiaOkardRongRaun = KaSiaOkardRongRaun/100;
        calKaSiaOkardMaeKo     = KaSiaOkardMaeKo/100;

        //F33
        calKumnuanTontoonKongTee =  calKaChaoTDin
                                  + calKaSermRongRaun
                                  + calKaSermMaeKo
                                  + calKaSiaOkardRongRaun
                                  + calKaSiaOkardMaeKo;



        // ====================== Calculate =============================
        calCost = (calKumnuanTontoonPunPae)*ParimanNumnom;
        costTontunPalitNamnomPerKg = calKumnuanTontoonPunPae;

        if (isCalIncludeOption){
            calCost = (calKumnuanTontoonPunPae+calKumnuanTontoonKongTee)*ParimanNumnom;
            costTontunPalitNamnomPerKg = calKumnuanTontoonPunPae+calKumnuanTontoonKongTee;
          //  calCost += (calKaSermRongRaun + calKaSermMaeKo+calKaSiaOkardRongRaun+calKaSiaOkardMaeKo)*ParimanNumnom;
            //costTontunPalitNamnomPerKg += (calKaSermRongRaun + calKaSermMaeKo+calKaSiaOkardRongRaun+calKaSiaOkardMaeKo);

        }


        calProfitLoss = RakaTkai - costTontunPalitNamnomPerKg;
        calNamnomTReedCharia =ParimanNumnom/JumuanMaeKo/30.42;
        calAllSalePrice   = RakaTkai*ParimanNumnom;


        Log.d("Cal","***** จำนวนแม่โครีดนม :"+JumuanMaeKo);
        Log.d("Cal","***** ปริมาณน้ำนมดิบที่รีดได้ทั้งหมด :"+ParimanNumnom);
        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** ค่าเสื่อมโรงเรือน :"+KaSermRongRaun);
        Log.d("Cal","***** ค่าเสื่อมแม่โคนม :"+KaSermMaeKo);
        Log.d("Cal","***** ค่าเสียโอกาสโรงเรือน :"+KaSiaOkardRongRaun);
        Log.d("Cal","***** ค่าเสียโอกาสแม่โคนม :"+KaSiaOkardMaeKo);

        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** 1. การคำนวณค่าหน่วยสัตว์ :"+calMoonkaAnimalTuangNumnuk);
        Log.d("Cal","-----------------------------------------");
        Log.d("Cal","***** 2.1 การคำนวณต้นทุนผันแปร     :"+calKumnuanTontoonPunPae);
        Log.d("Cal","***** -- 2.1.1 ค่าแรงงาน            :"+calKaRang);
        Log.d("Cal","***** -- 2.1.2 ค่าวัสดุ               :"+calKawassadu);
        Log.d("Cal","***** -- 2.1.3 ค่าเสียโอกาสเงินลงทุน  :"+calKaSiaOkardLongtoon);
        Log.d("Cal","***** 2.2 การคำนวณต้นทุนคงที่ :"+calKumnuanTontoonKongTee);
        Log.d("Cal","-----------------------------------------");

        Log.d("Cal","***** ต้นทุนการผลิตน้ำนมดิบเฉลี่ยต่อ 1 กก. :"+costTontunPalitNamnomPerKg);
        Log.d("Cal","***** ราคาที่เกษตรกรขายได้ :"+RakaTkai);
        Log.d("Cal","***** กำไร-ขาดทุน :"+calProfitLoss);
        Log.d("Cal","***** ปริมาณน้ำนมดิบที่รีดได้เฉลี่ย :"+calNamnomTReedCharia);
        Log.d("Cal","***** มูลค่าที่ขายได้ทั้งหมด :"+calAllSalePrice);
        Log.d("Cal","***** ต้นทุนทั้งหมด :"+calCost);


    }

    public void prepareListData() {


    }
}
