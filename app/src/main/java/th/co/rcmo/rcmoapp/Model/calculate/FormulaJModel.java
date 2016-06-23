package th.co.rcmo.rcmoapp.Model.calculate;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class FormulaJModel extends AbstractFormulaModel {

    // Standard Variable
    public double stdDepreEquip = 6.27;
    public double stdOpportEquip = 5.09;

    public double fishpondSizeRai = 0;
    public double fishpondSizeNgan = 0;
    public double fishpondSizeSqrWah = 0;
    public double fishpondSizeSqrMeters = 0;
    public double fishpondSize = 0;

    public double amountFish = 0;
    public double amountFishWeight = 0;
    public double costFish = 0;
    public double costFishSpecies = 0;
    public double costFishWeight = 0;
    public double costFood = 0;

    public double costLabor = 0;
    public double costFeed = 0;
    public double costFishing = 0;

    public double costMedicine = 0;
    public double costChemical = 0;
    public double costFuel = 0;
    public double costElectricGas = 0;
    public double costDredgeUpMud = 0;
    public double costRepairEquip = 0;
    public double costOther = 0;
    public double costLandLease = 0;

    public static double feedTime = 0;
    public double opportInvestment = 0;

    public double sellFish = 0;
    public double sellFishAvg = 0;
    public double sellPrice = 0;
    public double sizeAvg = 0;
    public double sellSummary = 0;

    public double fishSize1Weight = 0;
    public double fishSize1SumWeight = 0;
    public double fishSize1Sell = 0;
    public double fishSize1SumSell = 0;

    public double fishSize2Weight = 0;
    public double fishSize2SumWeight = 0;
    public double fishSize2Sell = 0;
    public double fishSize2SumSell = 0;

    public double fishSize3Weight = 0;
    public double fishSize3SumWeight = 0;
    public double fishSize3Sell = 0;
    public double fishSize3SumSell = 0;

    public double fishSize4Weight = 0;
    public double fishSize4SumWeight = 0;
    public double fishSize4Sell = 0;
    public double fishSize4SumSell = 0;

    public double fishSellSumWeight = 0;
    public double fishSellSizeAvg = 0;
    public double fishSellAvg = 0;
    public double fishSellSum = 0;


    public double costMixAll = 0;
    public double costMixPerLand = 0;
    public double costMixPerWeight = 0;
    public double calMixProfitLost = 0;
    public double calMixProfitLostPerWeight = 0;

    public double costBySizeAll = 0;
    public double costBySizePerLand = 0;
    public double costBySizePerWeight = 0;
    public double calBySizeProfitLost = 0;
    public double calBySizeProfitLostPerWeight = 0;



    public static Hashtable<String , String> calculateLabel;
    static {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
//        tmp.put("fishpondSizeRai" , "เนื้อที่บ่อ");
//        tmp.put("fishpondSizeNgan" , "");
//        tmp.put("fishpondSizeSqrWah" , "");
//        tmp.put("fishpondSizeSqrMeters" , "");
        tmp.put("fishpondSize" , "เนื้อที่บ่อ");

        tmp.put("amountFish" , "จำนวนลูกปลาที่ปล่อย");
        tmp.put("amountFishWeight" , "จำนวนลูกปลาที่ปล่อย");
        tmp.put("costFish" , "ราคาตัวละ");
        tmp.put("costFishWeight" , "ราคากิโลกรัมละ");
        tmp.put("costFishSpecies" , "ค่าพันธ์ปลา");
        tmp.put("costFood" , "ค่าอาหาร");

        tmp.put("KaRang" , "ค่าแรงงาน");
        tmp.put("costFeed" , "เลี้ยง");
        tmp.put("costFishing" , "จับและคัดขนาด");

        tmp.put("costMedicine" , "ค่ายารักษาโรค");
        tmp.put("costChemical" , "ค่าสารเคมี");
        tmp.put("costFuel" , "ค่าน้ำมันเชื้อเพลิงและหล่อลื่น");
        tmp.put("costElectricGas" , "ค่าไฟฟ้าและแก๊ส");
        tmp.put("costDredgeUpMud" , "ค่าลอกเลน");
        tmp.put("costRepairEquip" , "ค่าซ่อมแซมอุปกรณ์");
        tmp.put("costOther" , "ค่าใช้จ่ายอื่น ๆ");
        tmp.put("costLandLease" , "ค่าเช่าที่ดิน");

        tmp.put("feedTime" , "ระยะเวลาที่เลี้ยง");
        tmp.put("KaSiaOkardLongtoon" , "ค่าเสียโอกาสเงินลงทุน");

        tmp.put("sellFish" , "น้ำหนักที่ขายทั้งหมด");
        tmp.put("sellFishAvg" , "น้ำหนักที่ขายเฉลี่ย");
        tmp.put("sellPrice" , "ราคาที่ขายได้");
        tmp.put("sizeAvg" , "ขนาดปลาเฉลี่ยที่จับขาย");
        tmp.put("sellSummary" , "คิดเป็นเงินทั้งหมด");

        tmp.put("fishSize1Weight" , "ขนาดปลาที่จับ 1");
        tmp.put("fishSize1SumWeight" , "น้ำหนักที่ขาย");
        tmp.put("fishSize1Sell" , "ราคาที่ขายได้ขนาดที่1");
        tmp.put("fishSize1SumSell" , "คิดเป็นเงิน");

        tmp.put("fishSize2Weight" , "ขนาดปลาที่จับ 2");
        tmp.put("fishSize2SumWeight" , "น้ำหนักที่ขาย");
        tmp.put("fishSize2Sell" , "ราคาที่ขายได้ขนาดที่2");
        tmp.put("fishSize2SumSell" , "คิดเป็นเงิน");

        tmp.put("fishSize3Weight" , "ขนาดปลาที่จับ 3");
        tmp.put("fishSize3SumWeight" , "น้ำหนักที่ขาย");
        tmp.put("fishSize3Sell" , "ราคาที่ขายได้ขนาดที่3");
        tmp.put("fishSize3SumSell" , "คิดเป็นเงิน");

        tmp.put("fishSize4Weight" , "ขนาดปลาที่จับ 4");
        tmp.put("fishSize4SumWeight" , "น้ำหนักที่ขาย");
        tmp.put("fishSize4Sell" , "ราคาที่ขายได้ขนาดที่4");
        tmp.put("fishSize4SumSell" , "คิดเป็นเงิน");

        tmp.put("fishSellSumWeight" , "น้ำหนักปลาที่ขายได้");
        tmp.put("fishSellSizeAvg" , "ขนาดปลาเฉลี่ยที่จับขาย");
        tmp.put("fishSellAvg" , "ราคาที่ขายได้เฉลี่ยทุกขนาด");
        tmp.put("fishSellSum" , "คิดเป็นเงิน");

        tmp.put("costMixAll" , "ต้นทุนทั้งหมด");
        tmp.put("costMixPerLand" , "ต้นทุนต่อ 1 ไร่");
        tmp.put("costMixPerWeight" , "ต้นทุนต่อ 1 กิโลกรัม");
        tmp.put("calMixProfitLost" , "กำไร-ขาดทุนทั้งหมด");
        tmp.put("calMixProfitLostPerWeight" , "กำไร-ขาดทุนต่อ 1 กิโลกรัม");

        tmp.put("costBySizeAll" , "ต้นทุนทั้งหมด");
        tmp.put("costBySizePerLand" , "ต้นทุนต่อ 1 ไร่");
        tmp.put("costBySizePerWeight" , "ต้นทุนต่อ 1 กิโลกรัม");
        tmp.put("calBySizeProfitLost" , "กำไร-ขาดทุนทั้งหมด");
        tmp.put("calBySizeProfitLostPerWeight" , "กำไร-ขาดทุนต่อ 1 กิโลกรัม");

        tmp.put("stdDepreEquip" , "ค่าเสื่อมเครื่องมือและอุปกรณ์");
        tmp.put("stdOpportEquip" , "ค่าเสียโอกาสเครื่องมืออุปกรณ์");

        calculateLabel = tmp;
    }

    public static Hashtable<String , String> calculateUnit;
    static {
        Hashtable<String, String> tmp = new Hashtable<String, String>();
        tmp.put("fishpondSizeRai" , "ไร่");
        tmp.put("fishpondSizeNgan" , "งาน");
        tmp.put("fishpondSizeSqrWah" , "ตารางวา");
        tmp.put("fishpondSizeSqrMeters" , "ตารางเมตร");

        tmp.put("amountFish" , "ตัว");
        tmp.put("amountFishWeight" , "กิโลกรัม");
        tmp.put("costFish" , "บาท");
        tmp.put("costFishWeight" , "บาท");
        tmp.put("costFishSpecies" , "บาท");
        tmp.put("costFood" , "บาท/รุ่น");

        tmp.put("KaRang" , "บาท");
        tmp.put("costFeed" , "บาท/เดือน");
        tmp.put("costFishing" , "บาท/รุ่น");

        tmp.put("costMedicine" , "บาท/รุ่น");
        tmp.put("costChemical" , "บาท/รุ่น");
        tmp.put("costFuel" , "บาท/รุ่น");
        tmp.put("costElectricGas" , "บาท/เดือน");
        tmp.put("costDredgeUpMud" , "บาท/รุ่น");
        tmp.put("costRepairEquip" , "บาท/รุ่น");
        tmp.put("costOther" , "บาท/รุ่น");
        tmp.put("costLandLease" , "บาท/ปี");

        tmp.put("feedTime" , "วัน/รุ่น");
        tmp.put("KaSiaOkardLongtoon" , "วัน/รุ่น");

        tmp.put("sellFish" , "กิโลกรัม");
        tmp.put("sellFishAvg" , "กิโลกรัม/ไร่");
        tmp.put("sellPrice" , "บาท/กิโลกรัม");
        tmp.put("sizeAvg" , "ขีด/ตัว");
        tmp.put("sellSummary" , "บาท");

        tmp.put("fishSize1Weight" , "ขีด/ตัว");
        tmp.put("fishSize1SumWeight" , "กิโลกรัม");
        tmp.put("fishSize1Sell" , "บาท/กิโลกรัม");
        tmp.put("fishSize1SumSell" , "บาท");

        tmp.put("fishSize2Weight" , "ขีด/ตัว");
        tmp.put("fishSize2SumWeight" , "กิโลกรัม");
        tmp.put("fishSize2Sell" , "บาท/กิโลกรัม");
        tmp.put("fishSize2SumSell" , "บาท");

        tmp.put("fishSize3Weight" , "ขีด/ตัว");
        tmp.put("fishSize3SumWeight" , "กิโลกรัม");
        tmp.put("fishSize3Sell" , "บาท/กิโลกรัม");
        tmp.put("fishSize3SumSell" , "บาท");

        tmp.put("fishSize4Weight" , "ขีด/ตัว");
        tmp.put("fishSize4SumWeight" , "กิโลกรัม");
        tmp.put("fishSize4Sell" , "บาท/กิโลกรัม");
        tmp.put("fishSize4SumSell" , "บาท");

        tmp.put("fishSellSumWeight" , "กิโลกรัม");
        tmp.put("fishSellSizeAvg" , "ขีด/ตัว");
        tmp.put("fishSellAvg" , "บาท/กิโลกรัม");
        tmp.put("fishSellSum" , "บาท");

        tmp.put("costMixAll" , "บาท/รุ่น");
        tmp.put("costMixPerLand" , "บาท/ไร่");
        tmp.put("costMixPerWeight" , "บาท/กิโลกรัม");
        tmp.put("calMixProfitLost" , "บาท");
        tmp.put("calMixProfitLostPerWeight" , "บาท/กก.");

        tmp.put("costBySizeAll" , "บาท/รุ่น");
        tmp.put("costBySizePerLand" , "บาท/ไร่");
        tmp.put("costBySizePerWeight" , "บาท/กิโลกรัม");
        tmp.put("calBySizeProfitLost" , "บาท");
        tmp.put("calBySizeProfitLostPerWeight" , "บาท/กก.");

        tmp.put("stdDepreEquip" , "บาท");
        tmp.put("stdOpportEquip" , "บาท");

        calculateUnit = tmp;
    }

    public void calculate() {

        fishpondSize = ((fishpondSizeRai*4*400) + (fishpondSizeNgan*400) + (fishpondSizeSqrWah*4) + (fishpondSizeSqrMeters)) / 1600;

        double tmpCostFeed = (costFeed / 30.42) * feedTime;
        costLabor = tmpCostFeed + costFishing;

        double tmpCostElectricGas = (costElectricGas / 30.42) * feedTime;
        double tmpCostLandLease = (costLandLease / 365) * feedTime;

        double tmpAllCost = costFishSpecies + costFood + costLabor + costMedicine + costChemical + costFuel + tmpCostElectricGas + costDredgeUpMud + costRepairEquip + costOther;

        opportInvestment = (tmpAllCost * 0.0675) * (feedTime / 365);

        sellFishAvg = sellFish / fishpondSize;

        sellSummary = sellFish * sellPrice;

        fishSellSumWeight = fishSize1SumWeight + fishSize2SumWeight + fishSize3SumWeight + fishSize4SumWeight;

        fishSellSizeAvg = ((fishSize1Weight*fishSize1SumWeight) + (fishSize2Weight*fishSize2SumWeight) + (fishSize3Weight*fishSize3SumWeight) + (fishSize4Weight*fishSize4SumWeight)) / fishSellSumWeight;

        fishSize1SumSell = fishSize1Sell*fishSize1SumWeight;
        fishSize2SumSell = fishSize2Sell*fishSize2SumWeight;
        fishSize3SumSell = fishSize3Sell*fishSize3SumWeight;
        fishSize4SumSell = fishSize4Sell*fishSize4SumWeight;

        fishSellSum = fishSize1SumSell + fishSize2SumSell + fishSize3SumSell + fishSize4SumSell;

        fishSellAvg = fishSellSum / fishSellSumWeight;

        costMixAll = tmpAllCost + tmpCostLandLease + (stdDepreEquip*fishpondSize) + (stdOpportEquip*fishpondSize);
        costBySizeAll = costMixAll;

        costMixPerLand = costBySizeAll/fishpondSize;
        costBySizePerLand = costMixPerLand;

        costMixPerWeight = costMixAll / sellFish;
        costBySizePerWeight = costMixPerWeight;

        calMixProfitLost = sellSummary - costMixAll;
        calMixProfitLostPerWeight = sellPrice - costMixPerWeight;

        calBySizeProfitLost = fishSellSum - costBySizeAll;
        calBySizeProfitLostPerWeight = fishSellAvg - costBySizePerWeight;

        Log.i("calMixProfitLost", "calculate calMixProfitLost: " + calMixProfitLost);
    }

    public void prepareListData() {

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String[]>>();

        listDataHeader.add("ค่าใช้จ่าย");
        listDataHeader.add("ระยะเวลาและค่าเสียโอกาส");
        listDataHeader.add("ขายคละขนาด");

        List<String[]> cost = new ArrayList<String[]>(); // canEdit , Label , value , unit
        cost.add(new String[]{"true" , calculateLabel.get("costFish"), String.format("%,.2f", costFish), calculateUnit.get("costFish") , "costFish"});
        cost.add(new String[]{"true" , calculateLabel.get("costFishSpecies"), String.format("%,.2f", costFishSpecies), calculateUnit.get("costFishSpecies") , "costFishSpecies"});
        cost.add(new String[]{"true" , calculateLabel.get("costFood"), String.format("%,.2f", costFood), calculateUnit.get("costFood") , "costFood"});
        cost.add(new String[]{"true" , calculateLabel.get("costMedicine"), String.format("%,.2f", costMedicine), calculateUnit.get("costMedicine") , "costMedicine"});
        cost.add(new String[]{"true" , calculateLabel.get("costChemical"), String.format("%,.2f", costChemical), calculateUnit.get("costChemical") , "costChemical"});
        cost.add(new String[]{"true" , calculateLabel.get("costFuel"), String.format("%,.2f", costFuel), calculateUnit.get("costFuel") , "costFuel"});
        cost.add(new String[]{"true" , calculateLabel.get("costElectricGas"), String.format("%,.2f", costElectricGas), calculateUnit.get("costElectricGas") , "costElectricGas"});
        cost.add(new String[]{"true" , calculateLabel.get("costDredgeUpMud"), String.format("%,.2f", costDredgeUpMud), calculateUnit.get("costDredgeUpMud") , "costDredgeUpMud"});
        cost.add(new String[]{"false" , calculateLabel.get("KaRang"), String.format("%,.2f", costLabor), calculateUnit.get("KaRang") , "KaRang"});
        cost.add(new String[]{"true" , calculateLabel.get("costFeed"), String.format("%,.2f", costFeed), calculateUnit.get("costFeed") , "costFeed"});
        cost.add(new String[]{"true" , calculateLabel.get("costFishing"), String.format("%,.2f", costFishing), calculateUnit.get("costFishing") , "costFishing"});
        cost.add(new String[]{"true" , calculateLabel.get("costRepairEquip"), String.format("%,.2f", costRepairEquip), calculateUnit.get("costRepairEquip") , "costRepairEquip"});
        cost.add(new String[]{"true" , calculateLabel.get("costFishSpecies"), String.format("%,.2f", costFishSpecies), calculateUnit.get("costFishSpecies") , "costFishSpecies"});
        cost.add(new String[]{"true" , calculateLabel.get("costOther"), String.format("%,.2f", costOther), calculateUnit.get("costOther") , "costOther"});
        cost.add(new String[]{"true" , calculateLabel.get("costLandLease"), String.format("%,.2f", costLandLease), calculateUnit.get("costLandLease") , "costLandLease"});

        List<String[]> feedTimeList = new ArrayList<String[]>();
        feedTimeList.add(new String[]{"true" , calculateLabel.get("feedTime"), String.format("%,.2f", feedTime), calculateUnit.get("feedTime") , "feedTime"});
        feedTimeList.add(new String[]{"false" , calculateLabel.get("KaSiaOkardLongtoon"), String.format("%,.2f", opportInvestment), calculateUnit.get("KaSiaOkardLongtoon") , "KaSiaOkardLongtoon"});

        List<String[]> sellMixSize = new ArrayList<String[]>();
        sellMixSize.add(new String[]{"true" , calculateLabel.get("sellFish"), String.format("%,.2f", sellFish), calculateUnit.get("sellFish") , "sellFish"});
        sellMixSize.add(new String[]{"false" , calculateLabel.get("sellFishAvg"), String.format("%,.2f", sellFishAvg), calculateUnit.get("sellFishAvg") , "sellFishAvg"});
        sellMixSize.add(new String[]{"true" , calculateLabel.get("sellPrice"), String.format("%,.2f", sellPrice), calculateUnit.get("sellPrice") , "sellPrice"});
        sellMixSize.add(new String[]{"true" , calculateLabel.get("sizeAvg"), String.format("%,.2f", sizeAvg), calculateUnit.get("sizeAvg") , "sizeAvg"});
        sellMixSize.add(new String[]{"false" , calculateLabel.get("fishSellSum"), String.format("%,.2f", fishSellSum), calculateUnit.get("fishSellSum") , "fishSellSum"});

        listDataChild.put(listDataHeader.get(0), cost); // Header, Child data
        listDataChild.put(listDataHeader.get(1), feedTimeList);
        listDataChild.put(listDataHeader.get(2), sellMixSize);
    }
}
