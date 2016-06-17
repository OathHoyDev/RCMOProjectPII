package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/13/2016.
 */
public class mRiceProduct {
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
        int PrdID;
        String PrdName;
        int RiceTypeID;

        public int getPrdID() {
            return PrdID;
        }

        public String getPrdName() {
            return PrdName;
        }

        public int getRiceTypeID() {
            return RiceTypeID;
        }
    }
}
