package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/13/2016.
 */
public class mCompareStatus {
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
        int PrdGrpID;
        int CompareStatus;

        public int getPrdGrpID() {
            return PrdGrpID;
        }

        public void setPrdGrpID(int prdGrpID) {
            PrdGrpID = prdGrpID;
        }

        public int getCompareStatus() {
            return CompareStatus;
        }

        public void setCompareStatus(int compareStatus) {
            CompareStatus = compareStatus;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "PrdGrpID=" + PrdGrpID +
                    ", CompareStatus=" + CompareStatus +
                    '}';
        }
    }
}
