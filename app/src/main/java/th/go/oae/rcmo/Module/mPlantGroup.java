package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/13/2016.
 */
public class mPlantGroup {
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
        int PlantGrpID;
        String PlantGrpName;

        public int getPlantGrpID() {
            return PlantGrpID;
        }

        public String getPlantGrpName() {
            return PlantGrpName;
        }
    }
}
