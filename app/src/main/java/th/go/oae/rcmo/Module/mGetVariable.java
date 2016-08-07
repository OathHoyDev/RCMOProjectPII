package th.go.oae.rcmo.Module;

import java.util.List;

/**
 * Created by Taweesin on 6/14/2016.
 */
public class mGetVariable {
    List<mRespBody> RespBody;
    mRespStatus RespStatus;


    @Override
    public String toString() {
        return "mJobList{" +
                "RespBody=" + RespBody +
                ", RespStatus=" + RespStatus +
                '}';
    }

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

    public static class mRespBody {
        String
                FormularCode ,
                ConfigCode ,
                B , F , D , O , CA , CS , DH , DD , DP , DB , OH , OD , OP , OB,V;


        public String getV() {
            return V;
        }

        public void setV(String v) {
            V = v;
        }

        public String getFormularCode() {
            return FormularCode;
        }

        public void setFormularCode(String formularCode) {
            FormularCode = formularCode;
        }

        public String getConfigCode() {
            return ConfigCode;
        }

        public void setConfigCode(String configCode) {
            ConfigCode = configCode;
        }

        public String getB() {
            return B;
        }

        public void setB(String b) {
            B = b;
        }

        public String getF() {
            return F;
        }

        public void setF(String f) {
            F = f;
        }

        public String getD() {
            return D;
        }

        public void setD(String d) {
            D = d;
        }

        public String getO() {
            return O;
        }

        public void setO(String o) {
            O = o;
        }

        public String getCA() {
            return CA;
        }

        public void setCA(String CA) {
            this.CA = CA;
        }

        public String getCS() {
            return CS;
        }

        public void setCS(String CS) {
            this.CS = CS;
        }

        public String getDH() {
            return DH;
        }

        public void setDH(String DH) {
            this.DH = DH;
        }

        public String getDD() {
            return DD;
        }

        public void setDD(String DD) {
            this.DD = DD;
        }

        public String getDP() {
            return DP;
        }

        public void setDP(String DP) {
            this.DP = DP;
        }

        public String getDB() {
            return DB;
        }

        public void setDB(String DB) {
            this.DB = DB;
        }

        public String getOH() {
            return OH;
        }

        public void setOH(String OH) {
            this.OH = OH;
        }

        public String getOD() {
            return OD;
        }

        public void setOD(String OD) {
            this.OD = OD;
        }

        public String getOP() {
            return OP;
        }

        public void setOP(String OP) {
            this.OP = OP;
        }

        public String getOB() {
            return OB;
        }

        public void setOB(String OB) {
            this.OB = OB;
        }

        @Override
        public String toString() {
            return "mRespBody{" +
                    "FormularCode='" + FormularCode + '\'' +
                    ", ConfigCode='" + ConfigCode + '\'' +
                    ", B='" + B + '\'' +
                    ", F='" + F + '\'' +
                    ", D='" + D + '\'' +
                    ", O='" + O + '\'' +
                    ", CA='" + CA + '\'' +
                    ", CS='" + CS + '\'' +
                    ", DH='" + DH + '\'' +
                    ", DD='" + DD + '\'' +
                    ", DP='" + DP + '\'' +
                    ", DB='" + DB + '\'' +
                    ", OH='" + OH + '\'' +
                    ", OD='" + OD + '\'' +
                    ", OP='" + OP + '\'' +
                    ", OB='" + OB + '\'' +
                    '}';
        }
    }
}
