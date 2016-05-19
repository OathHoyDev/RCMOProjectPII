package th.co.rcmo.rcmoapp.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taweesin on 18/5/2559.
 */
public class mTumbon {
    List<mRespBody> respBody;
    mRespStatus respStatus;

    public mTumbon(List<mRespBody> respBody, mRespStatus respStatus) {
        this.respBody = respBody;
        this.respStatus = respStatus;
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


    public  class mRespBody implements Serializable {
        String tambonName;
        int tambonID,amphoeImg;

        public String getTambonName() {
            return tambonName;
        }

        public void setTambonName(String tambonName) {
            this.tambonName = tambonName;
        }

        public int getTambonID() {
            return tambonID;
        }

        public void setTambonID(int tambonID) {
            this.tambonID = tambonID;
        }

        public int getAmphoeImg() {
            return amphoeImg;
        }

        public void setAmphoeImg(int amphoeImg) {
            this.amphoeImg = amphoeImg;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "tambonName='" + tambonName + '\'' +
                    ", tambonID=" + tambonID +
                    ", amphoeImg=" + amphoeImg +
                    '}';
        }
    }

}
