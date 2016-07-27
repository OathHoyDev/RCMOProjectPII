package th.go.oae.rcmo.Module;

import java.util.List;


public class mGetProductSuit {
    List<mRespBody> RespBody;
    mRespStatus RespStatus;

    public mRespStatus getRespStatus() {
        return RespStatus;
    }

    public List<mRespBody> getRespBody() {
        return RespBody;
    }

    @Override
    public String toString() {
        return "mJobList{" +
                "RespBody=" + RespBody +
                ", RespStatus=" + RespStatus +
                '}';
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

    public static class mRespBody {
        int PrdID;
        String PrdName;
        int PrdGrpID;
        String SuitLabel;
        String SuitLevel;
        int MarketCount;
        String OrderID;

        @Override
        public String toString() {
            return "mRespBody{" +
                    "PrdID='" + PrdID + '\'' +
                    ", PrdName='" + PrdName + '\'' +
                    ", PrdGrpID='" + PrdGrpID + '\'' +
                    ", SuitLabel='" + SuitLabel + '\'' +
                    ", SuitLevel='" + SuitLevel + '\'' +
                    ", MarketCount='" + MarketCount + '\'' +
                    ", OrderID='" + OrderID + '\'' +
                    '}';
        }

        public int getPrdID() {
            return PrdID;
        }

        public void setPrdID(int prdID) {
            PrdID = prdID;
        }

        public String getPrdName() {
            return PrdName;
        }

        public void setPrdName(String prdName) {
            PrdName = prdName;
        }

        public int getPrdGrpID() {
            return PrdGrpID;
        }

        public void setPrdGrpID(int prdGrpID) {
            PrdGrpID = prdGrpID;
        }

        public String getSuitLabel() {
            return SuitLabel;
        }

        public void setSuitLabel(String suitLabel) {
            SuitLabel = suitLabel;
        }

        public String getSuitLevel() {
            return SuitLevel;
        }

        public void setSuitLevel(String suitLevel) {
            SuitLevel = suitLevel;
        }

        public int getMarketCount() {
            return MarketCount;
        }

        public void setMarketCount(int marketCount) {
            MarketCount = marketCount;
        }

        public String getOrderID() {
            return OrderID;
        }

        public void setOrderID(String orderID) {
            OrderID = orderID;
        }
    }
}
