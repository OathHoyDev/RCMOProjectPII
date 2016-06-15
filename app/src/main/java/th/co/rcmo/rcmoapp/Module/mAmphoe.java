package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class mAmphoe {
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
        String AmpCode;
        String AmpNameTH;
        String ProvCode;

        public String getAmpCode() {
            return AmpCode;
        }

        public String getAmpNameTH() {
            return AmpNameTH;
        }

        public String getProvCode() {
            return ProvCode;
        }
    }
}
