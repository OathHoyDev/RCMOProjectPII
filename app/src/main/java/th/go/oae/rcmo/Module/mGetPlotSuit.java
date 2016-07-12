package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by SilVeriSm on 6/20/2016 AD.
 */
public class mGetPlotSuit {
    List<mRespBody> RespBody;
    mRespStatus RespStatus;

    public mRespStatus getRespStatus() {
        return RespStatus;
    }

    public List<mRespBody> getRespBody() {
        return RespBody;
    }

    @Override
    public String toString() {
        return "mJobList{" +
                "RespBody=" + RespBody +
                ", RespStatus=" + RespStatus +
                '}';
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

    public static class mRespBody {
        String TamNameTH , AmpNameTH , ProvNameTH , PrdName , SuitLabel ,RecommendLabel , RecommendProduct , SuitValue , SuitLevel;

        @Override
        public String toString() {
            return "mRespBody{" +
                    "TamNameTH='" + TamNameTH + '\'' +
                    ", AmpNameTH='" + AmpNameTH + '\'' +
                    ", ProvNameTH='" + ProvNameTH + '\'' +
                    ", PrdName='" + PrdName + '\'' +
                    ", SuitLabel='" + SuitLabel + '\'' +
                    ", RecommendLabel='" + RecommendLabel + '\'' +
                    ", SuitValue=" + SuitValue +
                    ", SuitLevel=" + SuitLevel +
                    '}';
        }

        public String getTamNameTH() {
            return TamNameTH;
        }

        public void setTamNameTH(String tamNameTH) {
            TamNameTH = tamNameTH;
        }

        public String getAmpNameTH() {
            return AmpNameTH;
        }

        public void setAmpNameTH(String ampNameTH) {
            AmpNameTH = ampNameTH;
        }

        public String getProvNameTH() {
            return ProvNameTH;
        }

        public void setProvNameTH(String provNameTH) {
            ProvNameTH = provNameTH;
        }

        public String getPrdName() {
            return PrdName;
        }

        public void setPrdName(String prdName) {
            PrdName = prdName;
        }

        public String getSuitLabel() {
            return SuitLabel;
        }

        public void setSuitLabel(String suitLabel) {
            SuitLabel = suitLabel;
        }

        public String getRecommendLabel() {
            return RecommendLabel;
        }

        public void setRecommendLabel(String recommendLabel) {
            RecommendLabel = recommendLabel;
        }

        public String getSuitValue() {
            return SuitValue;
        }

        public void setSuitValue(String suitValue) {
            SuitValue = suitValue;
        }

        public String getSuitLevel() {
            return SuitLevel;
        }

        public void setSuitLevel(String suitLevel) {
            SuitLevel = suitLevel;
        }

        public String getRecommendProduct() {
            return RecommendProduct;
        }

        public void setRecommendProduct(String recommendProduct) {
            RecommendProduct = recommendProduct;
        }
    }
}
