package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/13/2016.
 */
public class mProductCompare {
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
        List<String> ParamName;
        List<mProductValue> ProductValue;

        public List<String> getParamName() {
            return ParamName;
        }

        public void setParamName(List<String> paramName) {
            ParamName = paramName;
        }

        public List<mProductValue> getProductValue() {
            return ProductValue;
        }

        public void setProductValue(List<mProductValue> productValue) {
            ProductValue = productValue;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "ParamName=" + ParamName +
                    ", ProductValue=" + ProductValue +
                    '}';
        }
    }

    public static class mProductValue{
        String PrdID;
        String PrdName;
        List<String> ParamValue;

        public String getPrdID() {
            return PrdID;
        }

        public void setPrdID(String prdID) {
            PrdID = prdID;
        }

        public String getPrdName() {
            return PrdName;
        }

        public void setPrdName(String prdName) {
            PrdName = prdName;
        }

        public List<String> getParamValue() {
            return ParamValue;
        }

        public void setParamValue(List<String> paramValue) {
            ParamValue = paramValue;
        }

        @Override
        public String toString() {
            return "mProductValue{" +
                    "PrdID='" + PrdID + '\'' +
                    ", PrdName='" + PrdName + '\'' +
                    ", ParamValue=" + ParamValue +
                    '}';
        }
    }
}
