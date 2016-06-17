package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class mTumbon {
    List<mRespBody> RespBody;
    mRespStatus RespStatus;

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
        String TamCode;
        String TamNameTH;
        String AmpCode;
        String ProvCode;
        String Latitude;
        String Longitude;

        public void setTamCode(String tamCode) {
            TamCode = tamCode;
        }

        public void setLongitude(String longitude) {
            Longitude = longitude;
        }

        public void setLatitude(String latitude) {
            Latitude = latitude;
        }

        public void setProvCode(String provCode) {
            ProvCode = provCode;
        }

        public void setAmpCode(String ampCode) {
            AmpCode = ampCode;
        }

        public void setTamNameTH(String tamNameTH) {
            TamNameTH = tamNameTH;
        }

        public String getTamCode() {
            return TamCode;
        }

        public String getTamNameTH() {
            return TamNameTH;
        }

        public String getAmpCode() {
            return AmpCode;
        }

        public String getProvCode() {
            return ProvCode;
        }

        public String getLatitude() {
            return Latitude;
        }

        public String getLongitude() {
            return Longitude;
        }
    }
}
