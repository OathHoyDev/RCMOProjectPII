package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 17/5/2559.
 */
public class mSubProductList {
    List<mRespBody> RespBody;
    mRespStatus RespStatus;


    public List<mRespBody> getRespBody() {
        return RespBody;
    }

    public mRespStatus getRespStatus() {
        return RespStatus;
    }

    /**
     * define attribute
     */

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

    public class mRespBody {
        int    productSubId,productSubIconImg;
        String productSubName;

        public int getProductSubId() {
            return productSubId;
        }

        public void setProductSubId(int productSubId) {
            this.productSubId = productSubId;
        }

        public String getProductSubName() {
            return productSubName;
        }

        public void setProductSubName(String productSubName) {
            this.productSubName = productSubName;
        }

        public int getProductSubIconImg() {
            return productSubIconImg;
        }

        public void setProductSubIconImg(int productSubIconImg) {
            this.productSubIconImg = productSubIconImg;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "productSubId=" + productSubId +
                    ", productSubName='" + productSubName + '\'' +
                    ", productSubIconImg='" + productSubIconImg + '\'' +
                    '}';
        }
    }


    /**
     * ========================================
     */
    @Override
    public String toString() {
        return "mSubProductList{" +
                "RespBody=" + RespBody +
                ", RespStatus=" + RespStatus +
                '}';
    }
}
