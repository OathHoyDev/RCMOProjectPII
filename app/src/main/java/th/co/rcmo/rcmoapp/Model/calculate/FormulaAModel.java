package th.co.rcmo.rcmoapp.Model.calculate;

import java.util.Hashtable;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaAModel {

    public double plantSize = 0;

    public double costLabor = 0;
    public double costPrepareLand = 0;
    public double costPrepareBreed = 0;
    public double costFarming = 0;
    public double costHarvest = 0;

    public double costEquip = 0;
    public double costBreed = 0;
    public double costFertilizer = 0;
    public double costWeedkilling = 0;
    public double costOtherEquip = 0;

    public double opportInvestment = 0;
    public double landLease = 0;
    public double depreEquip = 0;
    public double opportEquip = 0;

    public double predictHarvest = 0;
    public double predictPrice = 0;

    public double calSumCost = 0;
    public double calIncome = 0;
    public double calProfitLoss = 0;

    public double interestRate = 0;

    public double stdDepreEquip = 0;
    public double stdOpportEquip = 0;
    public double stdCost = 0;

    public static Hashtable<String , String> calculateLabel;
    static {
        Hashtable<String,String> tmp = new Hashtable<String,String>();
        tmp.put("plantSize","พื้นที่เพราะปลูก (ไร่)");
        tmp.put("costLabor","ค่าแรงงาน");
        tmp.put("costPrepareLand","ค่าเตรียมดิน");
        tmp.put("costPrepareBreed","ค่าปลูก รวมค่าเตรียมพันธ์");
        tmp.put("costFarming","ค่าดูแลรักษา");
        tmp.put("costHarvest","ค่าเก็บเกี่ยว รวบรวม");
        tmp.put("costEquip","ค่าวัสดุ");
        tmp.put("costBreed","ค่าพันธุ์");
        tmp.put("costFertilizer","ค่าปุ๋ย");
        tmp.put("costWeedkilling","ค่ายาปราบศัตรูพืชและวัชพืช");
        tmp.put("costOtherEquip","ค่าวัสดุอื่น ๆ นำมันเชื้อเพลิง และค่าซ่อมแซมอุปกรณ์");
        tmp.put("opportInvestment","เสียโอกาสเงินลงทุน");
        tmp.put("landLease","ค่าเช่าที่ดิน");
        tmp.put("depreEquip","ค่าเสื่อมอุปกรณ์");
        tmp.put("opportEquip","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("predictHarvest","ผลผลิต ที่คาดว่าจะเก็บเกี่ยวได้ในแปลงนี้");
        tmp.put("predictPrice","ราคาที่คาดว่าจะขายได้");
        tmp.put("calSumCost","ต้นทุนรวมของเกษตรกร");
        tmp.put("calIncome","รายได้");
        tmp.put("calProfitLoss","กำไร/ขาดทุน");

        tmp.put("interestRate","อัตราดอกเบี้ยร้อยละ/ปี");

        tmp.put("stdDepreEquip","ค่าเสื่อมอุปกรณ์");
        tmp.put("stdOpportEquip","ค่าเสียโอกาสอุปกรณ์");
        tmp.put("stdCost","ต้นทุนมาตรฐานของ สศก.");

        calculateLabel = tmp;
    }

    public void calculate() {

        costLabor = costPrepareLand + costPrepareBreed + costFarming + costHarvest;
        costEquip = costBreed + costFertilizer + costWeedkilling + costOtherEquip;
        opportInvestment = Math.pow((costLabor+costEquip)*(interestRate/100)*(6/12) , 2);
        depreEquip = plantSize * stdDepreEquip;
        opportEquip = plantSize * stdOpportEquip;

        calSumCost = costLabor + costEquip + opportInvestment + depreEquip + opportEquip;
        calIncome = predictHarvest * predictPrice;

        calProfitLoss = calIncome - calSumCost;
    }



}
