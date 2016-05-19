package th.co.rcmo.rcmoapp.Module;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taweesin on 18/5/2559.
 */
public class mAmphoe {
    List<mRespBody> respBody;
    mRespStatus respStatus;

    public List<mRespBody> getRespBody() {
        return respBody;
    }

    public mRespStatus getRespStatus() {
        return respStatus;
    }

    public class mRespStatus{
        int statusID;
        String statusMsg;

        public int getStatusID() {
            return statusID;
        }

        public String getStatusMsg() {
            return statusMsg;
        }

    }

    public class mRespBody implements Serializable {
        String amphoeName;
        int amphoeID , amphoeImg;

        public mRespBody(int amphoeImg,int amphoeID,String amphoeName ) {
            this.amphoeName = amphoeName;
            this.amphoeID = amphoeID;
            this.amphoeImg = amphoeImg;
        }

        public String getAmphoeName() {
            return amphoeName;
        }

        public void setAmphoeName(String amphoeName) {
            this.amphoeName = amphoeName;
        }

        public int getAmphoeID() {
            return amphoeID;
        }

        public void setAmphoeID(int amphoeID) {
            this.amphoeID = amphoeID;
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
                    "amphoeName='" + amphoeName + '\'' +
                    ", amphoeID=" + amphoeID +
                    ", amphoeImg=" + amphoeImg +
                    '}';
        }
    }

}
