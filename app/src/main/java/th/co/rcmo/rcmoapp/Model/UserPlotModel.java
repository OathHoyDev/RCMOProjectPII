package th.co.rcmo.rcmoapp.Model;

/**
 * Created by Taweesin on 6/16/2016.
 */
public class UserPlotModel {
    private int pageId;
    private String userID ="";
    private String plotID="";
    private String prdID="";
    private String prdGrpID="";
    private String plotRai="";
    private String pondRai="";
    private String pondNgan="";
    private String pondWa="";
    private String pondMeter="";
    private String coopMeter="";
    private String coopNumber="";
    private String tamCode="";
    private String ampCode="";
    private String provCode="";
    private String animalNumber="";
    private String animalWeight="";
    private String animalPrice="";
    private String fisheryType="";
    private String fisheryNumType="";
    private String fisheryNumber="";
    private String fisheryWeight="";
    private String varName="";
    private String varValue="";
    private String calResult="";
    private String prdValue = "";

    public String getPrdValue() {
        return prdValue;
    }

    public void setPrdValue(String prdValue) {
        this.prdValue = prdValue;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPlotID() {
        return plotID;
    }

    public void setPlotID(String plotID) {
        this.plotID = plotID;
    }

    public String getPrdID() {
        return prdID;
    }

    public void setPrdID(String prdID) {
        this.prdID = prdID;
    }

    public String getPrdGrpID() {
        return prdGrpID;
    }

    public void setPrdGrpID(String prdGrpID) {
        this.prdGrpID = prdGrpID;
    }

    public String getPlotRai() {
        return plotRai;
    }

    public void setPlotRai(String plotRai) {
        this.plotRai = plotRai;
    }

    public String getPondRai() {
        return pondRai;
    }

    public void setPondRai(String pondRai) {
        this.pondRai = pondRai;
    }

    public String getPondNgan() {
        return pondNgan;
    }

    public void setPondNgan(String pondNgan) {
        this.pondNgan = pondNgan;
    }

    public String getPondWa() {
        return pondWa;
    }

    public void setPondWa(String pondWa) {
        this.pondWa = pondWa;
    }

    public String getPondMeter() {
        return pondMeter;
    }

    public void setPondMeter(String pondMeter) {
        this.pondMeter = pondMeter;
    }


    public String getCoopMeter() {
        return coopMeter;
    }

    public void setCoopMeter(String coopMeter) {
        this.coopMeter = coopMeter;
    }

    public String getCoopNumber() {
        return coopNumber;
    }

    public void setCoopNumber(String coopNumber) {
        this.coopNumber = coopNumber;
    }

    public String getTamCode() {
        return tamCode;
    }

    public void setTamCode(String tamCode) {
        this.tamCode = tamCode;
    }

    public String getAmpCode() {
        return ampCode;
    }

    public void setAmpCode(String ampCode) {
        this.ampCode = ampCode;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getAnimalNumber() {
        return animalNumber;
    }

    public void setAnimalNumber(String animalNumber) {
        this.animalNumber = animalNumber;
    }

    public String getAnimalWeight() {
        return animalWeight;
    }

    public void setAnimalWeight(String animalWeight) {
        this.animalWeight = animalWeight;
    }

    public String getAnimalPrice() {
        return animalPrice;
    }

    public void setAnimalPrice(String animalPrice) {
        this.animalPrice = animalPrice;
    }

    public String getFisheryType() {
        return fisheryType;
    }

    public void setFisheryType(String fisheryType) {
        this.fisheryType = fisheryType;
    }

    public String getFisheryNumType() {
        return fisheryNumType;
    }

    public void setFisheryNumType(String fisheryNumType) {
        this.fisheryNumType = fisheryNumType;
    }

    public String getFisheryNumber() {
        return fisheryNumber;
    }

    public void setFisheryNumber(String fisheryNumber) {
        this.fisheryNumber = fisheryNumber;
    }

    public String getFisheryWeight() {
        return fisheryWeight;
    }

    public void setFisheryWeight(String fisheryWeight) {
        this.fisheryWeight = fisheryWeight;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    public String getCalResult() {
        return calResult;
    }

    public void setCalResult(String calResult) {
        this.calResult = calResult;
    }

    @Override
    public String toString() {
        return "UserPlotModel{" +
                "userID='" + userID + '\'' +
                ", plotID='" + plotID + '\'' +
                ", prdID='" + prdID + '\'' +
                ", prdGrpID='" + prdGrpID + '\'' +
                ", plotRai='" + plotRai + '\'' +
                ", pondRai='" + pondRai + '\'' +
                ", pondNgan='" + pondNgan + '\'' +
                ", pondWa='" + pondWa + '\'' +
                ", pondMeter='" + pondMeter + '\'' +
                ", coopMeter='" + coopMeter + '\'' +
                ", coopNumber='" + coopNumber + '\'' +
                ", tamCode='" + tamCode + '\'' +
                ", ampCode='" + ampCode + '\'' +
                ", provCode='" + provCode + '\'' +
                ", animalNumber='" + animalNumber + '\'' +
                ", animalWeight='" + animalWeight + '\'' +
                ", animalPrice='" + animalPrice + '\'' +
                ", fisheryType='" + fisheryType + '\'' +
                ", fisheryNumType='" + fisheryNumType + '\'' +
                ", fisheryNumber='" + fisheryNumber + '\'' +
                ", fisheryWeight='" + fisheryWeight + '\'' +
                ", varName='" + varName + '\'' +
                ", varValue='" + varValue + '\'' +
                ", calResult='" + calResult + '\'' +
                '}';
    }

    /*
    1.สถานะการทำรายการ (1=insert / 2=update)
2.รหัสผู้ใช้งาน
3.รหัสแปลงที่ดิน (เฉพาะกรณี SaveFlag=2)
4.รหัสสินค้า
5.รหัสกลุ่มสินค้า
(1=พืช 2=ประมง  3=ปศุสัตว์)
6.ขนาดแปลงที่ดินหน่วยเป็นไร่
7.ขนาดบ่อหน่วยเป็นไร่
8.ขนาดเป็นหน่วยเป็นงาน
9.ขนาดบ่อหน่วยเป็นตารางวา
10.ขนาดบ่อหน่วยเป็นตารางเมตร
11.ขนาดกระชังหน่วยเป็นตารางเมตร
12.จำนวนกระชัง
13.รหัสตำบล
14.รหัสอำเภอ
15.รหัสจังหวัด
16.จำนวนสัตว์ประเภทปศุสัตว์ (จำนวนเมื่อเริ่มเลี้ยงหน่วยเป็นตัว)
17.น้ำหนักสัตว์ประเภทปศุสัตว์ (น้ำหนักต่อตัวเมื่อเริ่มเลี้ยงหน่วยเป็นกิโล)
18.ราคาสัตว์ประเภทปศุสัตว์ (ราคาต่อตัวเมื่อเริ่มเลี้ยงหน่วยเป็นบาท)
19.ประเภทการเลี้ยงปลา (1=บ่อ 2=กระชัง)
20.ประเภทปลาที่ปล่อย (1=ตัว 2=กิโล)
21.จำนวนสัตว์ประเภทประมง ( จำนวนลูกกุ้งที่ปล่อยหน่วยเป็นตัว /จำนวนลูกปลาที่ปล่อยหน่วยเป็นตัวต่อกระชัง/ จำนวนลูกปลาที่ปล่อยหน่วยเป็นตัว)
22.น้ำหนักสัคว์ประเภทประมง (จำนวนลูกปลาที่ปล่อยหน่วยเป็นกิโลกรัม)
23. Imei เพื่อเก็บ Log
24.ชื่อตัวแปรต่าง ๆ
25.ค่าตัวแปรต่าง ๆ
26.ผลการคำนวณ เช่น -12000.00 / 12000.00
     */
}


