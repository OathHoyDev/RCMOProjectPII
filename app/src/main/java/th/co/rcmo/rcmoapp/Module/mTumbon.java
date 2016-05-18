package th.co.rcmo.rcmoapp.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taweesin on 18/5/2559.
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

    public static class mRespBody implements Serializable {
        String TambonName;
        int TambonID;

        public String getTambonName() {
            return TambonName;
        }

        public int getTambonID() {
            return TambonID;
        }

        public void setTambonName(String tambonName) {
            TambonName = tambonName;
        }

        public void setTambonID(int tambonID) {
            TambonID = tambonID;
        }
    }

}
