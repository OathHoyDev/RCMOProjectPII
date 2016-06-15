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
