package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/15/2016.
 */
public class mCurrentLocation {
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
        String AmpCode;
        String TamCode;
        String ProvNameTH;
        String AmpNameTH;
        String TamNameTH;
        String ProveNameTH;

        public String getProveNameTH() {
            return ProveNameTH;
        }

        public void setProveNameTH(String proveNameTH) {
            ProveNameTH = proveNameTH;
        }

        public String getProvCode() {
            return ProvCode;
        }

        public void setProvCode(String provCode) {
            ProvCode = provCode;
        }

        public String getAmpCode() {
            return AmpCode;
        }

        public void setAmpCode(String ampCode) {
            AmpCode = ampCode;
        }

        public String getTamCode() {
            return TamCode;
        }

        public void setTamCode(String tamCode) {
            TamCode = tamCode;
        }

        public String getProvNameTH() {
            return ProvNameTH;
        }

        public void setProvNameTH(String provNameTH) {
            ProvNameTH = provNameTH;
        }

        public String getAmpNameTH() {
            return AmpNameTH;
        }

        public void setAmpNameTH(String ampNameTH) {
            AmpNameTH = ampNameTH;
        }

        public String getTamNameTH() {
            return TamNameTH;
        }

        public void setTamNameTH(String tamNameTH) {
            TamNameTH = tamNameTH;
        }
    }
}
