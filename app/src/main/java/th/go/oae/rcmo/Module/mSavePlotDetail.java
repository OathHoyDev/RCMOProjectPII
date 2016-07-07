package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/14/2016.
 */
public class mSavePlotDetail {
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
        int PlotID;

        public int getPlotID() {
            return PlotID;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "PlotID=" + PlotID +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "mSavePlotDetail{" +
                "RespBody=" + RespBody +
                ", RespStatus=" + RespStatus +
                '}';
    }
}
