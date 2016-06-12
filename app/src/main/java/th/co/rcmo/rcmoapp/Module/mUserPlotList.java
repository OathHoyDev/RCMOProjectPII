package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 11/6/2559.
 */
public class mUserPlotList {
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
    public static class mRespBody{
        int PlotID,PrdGrpID,PlantGrpID,PrdID,SeqNo;
        String PrdValue ,PlotLocation,PlotSize,AnimalNumberValue,AnimalPriceValue,AnimalWeightValue,FisheryNumberValue,DateUpdated,CalResult ;

        public int getPlotID() {
            return PlotID;
        }

        public int getPrdGrpID() {
            return PrdGrpID;
        }

        public int getPlantGrpID() {
            return PlantGrpID;
        }

        public int getPrdID() {
            return PrdID;
        }

        public int getSeqNo() {
            return SeqNo;
        }

        public String getPrdValue() {
            return PrdValue;
        }

        public String getPlotLocation() {
            return PlotLocation;
        }

        public String getPlotSize() {
            return PlotSize;
        }

        public String getAnimalNumberValue() {
            return AnimalNumberValue;
        }

        public String getAnimalPriceValue() {
            return AnimalPriceValue;
        }

        public String getAnimalWeightValue() {
            return AnimalWeightValue;
        }

        public String getFisheryNumberValue() {
            return FisheryNumberValue;
        }

        public String getDateUpdated() {
            return DateUpdated;
        }

        public String getCalResult() {return CalResult;}

        @Override
        public String toString() {
            return "mRespBody{" +
                    "PlotID=" + PlotID +
                    ", PrdGrpID=" + PrdGrpID +
                    ", PlantGrpID=" + PlantGrpID +
                    ", PrdID=" + PrdID +
                    ", SeqNo=" + SeqNo +
                    ", PrdValue='" + PrdValue + '\'' +
                    ", PlotLocation='" + PlotLocation + '\'' +
                    ", PlotSize='" + PlotSize + '\'' +
                    ", AnimalNumberValue='" + AnimalNumberValue + '\'' +
                    ", AnimalPriceValue='" + AnimalPriceValue + '\'' +
                    ", AnimalWeightValue='" + AnimalWeightValue + '\'' +
                    ", FisheryNumberValue='" + FisheryNumberValue + '\'' +
                    ", DateUpdated='" + DateUpdated + '\'' +
                    ", CalResult='" + CalResult + '\'' +
                    '}';
        }
    }
}
