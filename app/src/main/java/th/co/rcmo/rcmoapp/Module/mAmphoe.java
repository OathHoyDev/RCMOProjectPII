package th.co.rcmo.rcmoapp.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taweesin on 18/5/2559.
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

    public static class mRespBody implements Serializable {
        String AmphoeName;
        int AmphoeID;

        public String getAmphoeName() {
            return AmphoeName;
        }

        public int getAmphoeID() {
            return AmphoeID;
        }

        public void setAmphoeName(String amphoeName) {
            AmphoeName = amphoeName;
        }

        public void setAmphoeID(int amphoeID) {
            AmphoeID = amphoeID;
        }
    }

}
