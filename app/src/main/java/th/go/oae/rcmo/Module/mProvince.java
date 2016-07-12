package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class mProvince {

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
        String ProvCode;
        String ProvNameTH;

        public String getProvCode() {
            return ProvCode;
        }

        public String getProvNameTH() {
            return ProvNameTH;
        }

        public void setProvCode(String provCode) {
            ProvCode = provCode;
        }

        public void setProvNameTH(String provNameTH) {
            ProvNameTH = provNameTH;
        }
    }
}
