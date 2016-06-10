package th.co.rcmo.rcmoapp.Module;

/**
 * Created by Taweesin on 6/10/2016.
 */
public class mStatus {
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

        @Override
        public String toString() {
            return "mRespStatus{" +
                    "StatusID=" + StatusID +
                    ", StatusMsg='" + StatusMsg + '\'' +
                    '}';
        }
    }
}
