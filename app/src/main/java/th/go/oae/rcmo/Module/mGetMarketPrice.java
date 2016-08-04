package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by SilVeriSm on 8/2/2016 AD.
 */
public class mGetMarketPrice {

    List<mRespBody> RespBody;
    mRespStatus RespStatus;

    public mRespStatus getRespStatus() {
        return RespStatus;
    }
    public List<mRespBody> getRespBody() {
        return RespBody;
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

    public static class mRespBody{
        String PrdMkID;
        String PrdMkName;
        String PriceValue;
        String UnitValue;

        public String getPrdMkID() {
            return PrdMkID;
        }

        public void setPrdMkID(String prdMkID) {
            PrdMkID = prdMkID;
        }

        public String getPrdMkName() {
            return PrdMkName;
        }

        public void setPrdMkName(String prdMkName) {
            PrdMkName = prdMkName;
        }

        public String getPriceValue() {
            return PriceValue;
        }

        public void setPriceValue(String priceValue) {
            PriceValue = priceValue;
        }

        public String getUnitValue() {
            return UnitValue;
        }

        public void setUnitValue(String unitValue) {
            UnitValue = unitValue;
        }
    }

}

