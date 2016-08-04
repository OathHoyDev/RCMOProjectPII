package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by SilVeriSm on 8/2/2016 AD.
 */
public class mGetMarketList {

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
        int MarketCount;
        String UnitValue;
        List<MarketListObj> MarketList;

        public int getMarketCount() {
            return MarketCount;
        }

        public void setMarketCount(int marketCount) {
            MarketCount = marketCount;
        }

        public String getUnitValue() {
            return UnitValue;
        }

        public void setUnitValue(String unitValue) {
            UnitValue = unitValue;
        }

        public List<MarketListObj> getMarketList() {
            return MarketList;
        }

        public void setMarketList(List<MarketListObj> marketList) {
            MarketList = marketList;
        }
    }

    public class MarketListObj {

        String MarketID;
        String MarketName;
        String Latitude;
        String Longitude;
        String PriceValue;

        public String getMarketID() {
            return MarketID;
        }

        public void setMarketID(String marketID) {
            MarketID = marketID;
        }

        public String getMarketName() {
            return MarketName;
        }

        public void setMarketName(String marketName) {
            MarketName = marketName;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public String getPriceValue() {
            return PriceValue;
        }

        public void setPriceValue(String priceValue) {
            PriceValue = priceValue;
        }
    }
}

