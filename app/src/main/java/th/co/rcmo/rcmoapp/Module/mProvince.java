package th.co.rcmo.rcmoapp.Module;

import com.android.internal.util.Predicate;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taweesin on 18/5/2559.
 */
public class mProvince {
    List<mRespBody> respBody;
    mRespStatus respStatus;

    public mRespStatus getRespStatus() {
        return respStatus;
    }

    public class mRespStatus{
        int statusID;
        String statusMsg;

        public int getStatusID() {
            return statusID;
        }

        public void setStatusID(int statusID) {
            this.statusID = statusID;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

        public void setStatusMsg(String statusMsg) {
            this.statusMsg = statusMsg;
        }
    }
    public List<mRespBody> getRespBody() {
        return respBody;
    }

    public class mRespBody implements Serializable {
        String provinceName;
        int provinceID,provinceImg;

        public mRespBody(int provinceImg, int provinceID, String provinceName) {
            this.provinceImg = provinceImg;
            this.provinceID = provinceID;
            this.provinceName = provinceName;
        }

        public String getProvinceName() {
            return provinceName;
        }

        public void setProvinceName(String provinceName) {
            this.provinceName = provinceName;
        }

        public int getProvinceID() {
            return provinceID;
        }

        public void setProvinceID(int provinceID) {
            this.provinceID = provinceID;
        }

        public int getProvinceImg() {
            return provinceImg;
        }

        public void setProvinceImg(int provinceImg) {
            this.provinceImg = provinceImg;
        }

        public  Predicate<mRespBody> nameEqualsTo(final int ID) {
            return new Predicate<mRespBody>() {

                @Override
                public boolean apply(mRespBody mProvince) {
                    return mProvince.getProvinceID() == ID;
                }

            };
        }
    }
}
