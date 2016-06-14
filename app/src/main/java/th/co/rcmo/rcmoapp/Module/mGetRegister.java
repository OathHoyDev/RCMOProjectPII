package th.co.rcmo.rcmoapp.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/14/2016.
 */
public class mGetRegister {

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
        int UserID;
        String UserLogin;
        String UserFirstName;
        String UserLastName;
        String UserEmail;

        public int getUserID() {
            return UserID;
        }

        public String getUserLogin() {
            return UserLogin;
        }

        public String getUserFirstName() {
            return UserFirstName;
        }

        public String getUserLastName() {
            return UserLastName;
        }

        public String getUserEmail() {
            return UserEmail;
        }


    }
}
