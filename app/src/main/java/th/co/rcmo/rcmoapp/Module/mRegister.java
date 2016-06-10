package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 11/6/2559.
 */
public class mRegister {
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
        String UserID;

        public String getUserID() {
            return UserID;
        }
    }
}
