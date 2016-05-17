package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 17/5/2559.
 */
public class mMainProductList {
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
        int    productId,productIconImg;
        String productName;


        public mRespBody(int productIconImg, String productName) {
            this.productIconImg = productIconImg;
            this.productName = productName;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public int getProductIconImg() {
            return productIconImg;
        }

        public void setProductIconImg(int productIconImg) {
            this.productIconImg = productIconImg;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "productId=" + productId +
                    ", productIconImg=" + productIconImg +
                    ", productName='" + productName + '\'' +
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
