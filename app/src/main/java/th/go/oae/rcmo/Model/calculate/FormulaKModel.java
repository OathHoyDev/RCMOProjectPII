package th.go.oae.rcmo.Model.calculate;

import java.util.HashMap;
import java.util.List;

import th.go.oae.rcmo.Util.Util;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaKModel {

    public List<String> listDataHeader;
    public HashMap<String, List<String[]>> listDataChild;

    public boolean isCalIncludeOption;

    public int TuaOrKilo;
    public int CustomSize;
    public int CalType; //1=คละ , 2=แยกขนาด

    public double KanardKaChang = 0;
    public double JumnounKachang = 0;
    public double LookPla = 0;

    public double Raka = 0;
    public double KaAHan = 0;
    public double KaRangNganLeang = 0;
    public double KaRangNganJub = 0;
    public double KaYa = 0;
    public double KaSanKMe = 0;
    public double KaNamman = 0;
    public double KaFaifa = 0;
    public double KaSomSam = 0;
    public double KaChaijai = 0;
    public double RayaWela = 0;
    public double NamnakTKai = 0;
    public double RakaTKai = 0;
    public double KanardPlaChalia = 0;
    public double KanardPla1 = 0;
    public double KanardPla2 = 0;
    public double KanardPla3 = 0;
    public double KanardPla4 = 0;
    public double RakaPla1 = 0;
    public double RakaPla2 = 0;
    public double RakaPla3 = 0;
    public double RakaPla4 = 0;
    public double NamnakPla1 = 0;
    public double NamnakPla2 = 0;
    public double NamnakPla3 = 0;
    public double NamnakPla4 = 0;

    public double calKaSiaOkardLongtoon = 0;

    public double KaSermOuppakorn = 0;
    public double KaSiaOkardOuppakorn = 0;

    public double calNamnakTKai = 0;
    public double calRaidai = 0;
    public double calRakaTKaiChalia = 0;

    public double costTontoonMix = 0;
    public double costTontoonMixTorRai = 0;
    public double costTontoonMixTorKilo = 0;
    public double KumraiKadtoonMix = 0;
    public double KumraiKadtoonMixTorKilo = 0;

    public double costTontoonSize = 0;
    public double costTontoonSizeTorRai = 0;
    public double costTontoonSizeTorKilo = 0;
    public double KumraiKadtoonSize = 0;
    public double KumraiKadtoonSizeTorKilo = 0;


    public double KumraiKadtoonMixTorKaChang = 0;
    public double KumraiKadtoonSizeTorKaChang = 0;
    public double calRakaTKai1 = 0;
    public double calRakaTKai2 = 0;
    public double calRakaTKai3 = 0;
    public double calRakaTKai4 = 0;

    public double calKaPan = 0;
    public double calKaRangNgan = 0;



    public void calculate() {

        //double NueaTeeBor = ((Rai*4*400)+(Ngan*400)+(TarangWa*4)+TarangMeter)/1600;

         calKaPan = LookPla * Raka * JumnounKachang;

        double calKaRangNganLeang = (KaRangNganLeang/30.42) * RayaWela;

         calKaRangNgan = calKaRangNganLeang + KaRangNganJub;

        double calKaFaifa = (KaFaifa/30.42) * RayaWela;

        double calCost = calKaPan + KaAHan + calKaRangNgan + KaYa + KaSanKMe + KaNamman + calKaFaifa + KaSomSam + KaChaijai;

        double calKaSermOuppakorn = KaSermOuppakorn * JumnounKachang;
        double calKaSiaOkardOuppakorn = KaSiaOkardOuppakorn * JumnounKachang;

        if (isCalIncludeOption){
            calCost = calCost + calKaSermOuppakorn + calKaSiaOkardOuppakorn;
        }



        calKaSiaOkardLongtoon = calCost*0.0675*(RayaWela/365);

        double calRakaTKai = NamnakTKai*RakaTKai;

        calNamnakTKai = NamnakTKai*JumnounKachang;

        calRaidai = calNamnakTKai * RakaTKai;



        double calNamnakPla1 = KanardPla1 * NamnakPla1;
        double calNamnakPla2 = KanardPla2 * NamnakPla2;
        double calNamnakPla3 = KanardPla3 * NamnakPla3;
        double calNamnakPla4 = KanardPla4 * NamnakPla4;

        double calNamnakPlaAll = calNamnakPla1 + calNamnakPla2 + calNamnakPla3 + calNamnakPla4;


         calRakaTKai1 = NamnakPla1 * RakaPla1;
         calRakaTKai2 = NamnakPla2 * RakaPla2;
         calRakaTKai3 = NamnakPla3 * RakaPla3;
         calRakaTKai4 = NamnakPla4 * RakaPla4;

        double calRakaTKaiAll = calRakaTKai1 + calRakaTKai2 + calRakaTKai3 + calRakaTKai4;

        KanardPlaChalia = calNamnakPlaAll/(NamnakPla1 + NamnakPla2 + NamnakPla3 + NamnakPla4);

        calRakaTKaiChalia = calRakaTKaiAll/(NamnakPla1 + NamnakPla2 + NamnakPla3 + NamnakPla4);

        if (CalType == 1) {

            costTontoonMix = calCost + calKaSiaOkardLongtoon;
            costTontoonMix = Util.verifyDoubleDefaultZero(costTontoonMix);

            costTontoonMixTorRai = costTontoonMix / JumnounKachang;
            costTontoonMixTorRai = Util.verifyDoubleDefaultZero(costTontoonMixTorRai);

            costTontoonMixTorKilo = costTontoonMix / calNamnakTKai;
            costTontoonMixTorKilo= Util.verifyDoubleDefaultZero(costTontoonMixTorKilo);

            KumraiKadtoonMix = calRaidai - costTontoonMix;
            KumraiKadtoonMix =  Util.verifyDoubleDefaultZero(KumraiKadtoonMix);

            KumraiKadtoonMixTorKilo = RakaTKai - costTontoonMixTorKilo;
            KumraiKadtoonMixTorKilo =  Util.verifyDoubleDefaultZero(KumraiKadtoonMixTorKilo);

            KumraiKadtoonMixTorKaChang = KumraiKadtoonMix/JumnounKachang;
            KumraiKadtoonMixTorKaChang =  Util.verifyDoubleDefaultZero(KumraiKadtoonMixTorKaChang);

        }else if(CalType == 2) {

            costTontoonSize = calCost + calKaSiaOkardLongtoon;
            costTontoonSize =  Util.verifyDoubleDefaultZero(costTontoonSize);

            costTontoonSizeTorRai = costTontoonSize / JumnounKachang;
            costTontoonSizeTorRai =  Util.verifyDoubleDefaultZero(costTontoonSizeTorRai);

            costTontoonSizeTorKilo = costTontoonSize / (NamnakPla1 + NamnakPla2 + NamnakPla3 + NamnakPla4);
            costTontoonSizeTorKilo =  Util.verifyDoubleDefaultZero(costTontoonSizeTorKilo);

            KumraiKadtoonSize = calRakaTKaiAll - costTontoonSize;
            KumraiKadtoonSize =  Util.verifyDoubleDefaultZero(KumraiKadtoonSize);

            KumraiKadtoonSizeTorKilo = calRakaTKaiChalia - costTontoonSizeTorKilo;
            KumraiKadtoonSizeTorKilo =  Util.verifyDoubleDefaultZero(KumraiKadtoonSizeTorKilo);

            KumraiKadtoonSizeTorKaChang = KumraiKadtoonSize/JumnounKachang;
            KumraiKadtoonSizeTorKaChang =  Util.verifyDoubleDefaultZero(KumraiKadtoonSizeTorKaChang);


        }

    }

}
