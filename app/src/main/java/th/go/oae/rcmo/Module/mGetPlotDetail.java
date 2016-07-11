package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/14/2016.
 */
public class mGetPlotDetail {
    List<mRespBody> RespBody;
    mRespStatus RespStatus;


    @Override
    public String toString() {
        return "mJobList{" +
                "RespBody=" + RespBody +
                ", RespStatus=" + RespStatus +
                '}';
    }

    public mRespStatus getRespStatus() {
        return RespStatus;
    }


    public class mRespStatus{
        int StatusID;
        String StatusMsg;

        public int getStatusID() {
            return StatusID;
        }

        public String getStatusMsg() {
            return StatusMsg;
        }
    }

    public List<mRespBody> getRespBody() {
        return RespBody;
    }

    public static class mRespBody {
        String PlotID , PrdID , PrdGrpID , PlantGrpID , RiceTypeID , PlotRai , PondRai , PondNgan , PondWa , PondMeter , CoopMeter , CoopNumber , TamCode , AmpCode , ProvCode , AnimalNumber , AnimalWeight , AnimalPrice , FisheryType , FisheryNumber , FisheryWeight , SeqNo , CalResult , VarName , VarValue ;
        String PlotNgan;
        String PlotWa;
        String PlotMeter;

        public String getPlotNgan() {
            return PlotNgan;
        }

        public void setPlotNgan(String plotNgan) {
            PlotNgan = plotNgan;
        }

        public String getPlotWa() {
            return PlotWa;
        }

        public void setPlotWa(String plotWa) {
            PlotWa = plotWa;
        }

        public String getPlotMeter() {
            return PlotMeter;
        }

        public void setPlotMeter(String plotMeter) {
            PlotMeter = plotMeter;
        }

        public String getPlotID() {
            return PlotID;
        }

        public void setPlotID(String plotID) {
            PlotID = plotID;
        }

        public String getPrdID() {
            return PrdID;
        }

        public void setPrdID(String prdID) {
            PrdID = prdID;
        }

        public String getPrdGrpID() {
            return PrdGrpID;
        }

        public void setPrdGrpID(String prdGrpID) {
            PrdGrpID = prdGrpID;
        }

        public String getPlantGrpID() {
            return PlantGrpID;
        }

        public void setPlantGrpID(String plantGrpID) {
            PlantGrpID = plantGrpID;
        }

        public String getRiceTypeID() {
            return RiceTypeID;
        }

        public void setRiceTypeID(String riceTypeID) {
            RiceTypeID = riceTypeID;
        }

        public String getPlotRai() {
            return PlotRai;
        }

        public void setPlotRai(String plotRai) {
            PlotRai = plotRai;
        }

        public String getPondRai() {
            return PondRai;
        }

        public void setPondRai(String pondRai) {
            PondRai = pondRai;
        }

        public String getPondNgan() {
            return PondNgan;
        }

        public void setPondNgan(String pondNgan) {
            PondNgan = pondNgan;
        }

        public String getPondWa() {
            return PondWa;
        }

        public void setPondWa(String pondWa) {
            PondWa = pondWa;
        }

        public String getPondMeter() {
            return PondMeter;
        }

        public void setPondMeter(String pondMeter) {
            PondMeter = pondMeter;
        }

        public String getCoopNumber() {
            return CoopNumber;
        }

        public void setCoopNumber(String coopNumber) {
            CoopNumber = coopNumber;
        }

        public String getTamCode() {
            return TamCode;
        }

        public void setTamCode(String tamCode) {
            TamCode = tamCode;
        }

        public String getAmpCode() {
            return AmpCode;
        }

        public void setAmpCode(String ampCode) {
            AmpCode = ampCode;
        }

        public String getProvCode() {
            return ProvCode;
        }

        public void setProvCode(String provCode) {
            ProvCode = provCode;
        }

        public String getAnimalNumber() {
            return AnimalNumber;
        }

        public void setAnimalNumber(String animalNumber) {
            AnimalNumber = animalNumber;
        }

        public String getAnimalWeight() {
            return AnimalWeight;
        }

        public void setAnimalWeight(String animalWeight) {
            AnimalWeight = animalWeight;
        }

        public String getAnimalPrice() {
            return AnimalPrice;
        }

        public void setAnimalPrice(String animalPrice) {
            AnimalPrice = animalPrice;
        }

        public String getFisheryType() {
            return FisheryType;
        }

        public void setFisheryType(String fisheryType) {
            FisheryType = fisheryType;
        }

        public String getFisheryNumber() {
            return FisheryNumber;
        }

        public void setFisheryNumber(String fisheryNumber) {
            FisheryNumber = fisheryNumber;
        }

        public String getFisheryWeight() {
            return FisheryWeight;
        }

        public void setFisheryWeight(String fisheryWeight) {
            FisheryWeight = fisheryWeight;
        }

        public String getSeqNo() {
            return SeqNo;
        }

        public void setSeqNo(String seqNo) {
            SeqNo = seqNo;
        }

        public String getCalResult() {
            return CalResult;
        }

        public void setCalResult(String calResult) {
            CalResult = calResult;
        }

        public String getVarName() {
            return VarName;
        }

        public void setVarName(String varName) {
            VarName = varName;
        }

        public String getVarValue() {
            return VarValue;
        }

        public void setVarValue(String varValue) {
            VarValue = varValue;
        }

        public String getCoopMeter() {
            return CoopMeter;
        }

        public void setCoopMeter(String coopMeter) {
            CoopMeter = coopMeter;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "PlotID='" + PlotID + '\'' +
                    ", PrdID='" + PrdID + '\'' +
                    ", PrdGrpID='" + PrdGrpID + '\'' +
                    ", PlantGrpID='" + PlantGrpID + '\'' +
                    ", RiceTypeID='" + RiceTypeID + '\'' +
                    ", PlotRai='" + PlotRai + '\'' +
                    ", PondRai='" + PondRai + '\'' +
                    ", PondNgan='" + PondNgan + '\'' +
                    ", PondWa='" + PondWa + '\'' +
                    ", PondMeter='" + PondMeter + '\'' +
                    ", CoopNumber='" + CoopNumber + '\'' +
                    ", TamCode='" + TamCode + '\'' +
                    ", AmpCode='" + AmpCode + '\'' +
                    ", ProvCode='" + ProvCode + '\'' +
                    ", AnimalNumber='" + AnimalNumber + '\'' +
                    ", AnimalWeight='" + AnimalWeight + '\'' +
                    ", AnimalPrice='" + AnimalPrice + '\'' +
                    ", FisheryType='" + FisheryType + '\'' +
                    ", FisheryNumber='" + FisheryNumber + '\'' +
                    ", FisheryWeight='" + FisheryWeight + '\'' +
                    ", SeqNo='" + SeqNo + '\'' +
                    ", CalResult='" + CalResult + '\'' +
                    ", VarName='" + VarName + '\'' +
                    ", VarValue='" + VarValue + '\'' +
                    '}';
        }
    }
}
