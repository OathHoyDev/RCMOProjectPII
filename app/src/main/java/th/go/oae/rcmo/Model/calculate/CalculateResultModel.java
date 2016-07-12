package th.go.oae.rcmo.Model.calculate;

import java.util.List;

import th.go.oae.rcmo.Module.mGetPlotSuit;

/**
 * Created by SilVeriSm on 6/24/2016 AD.
 */
public class CalculateResultModel {

    public String formularCode = "";
    public String productName = "";
    public List<String []> resultList ;
    public double calculateResult;
    public double compareStdResult;
    public mGetPlotSuit.mRespBody mPlotSuit;
    public  String unit_t1;
    public  double value_t1;
    public  String unit_t2;
    public  double value_t2;

}
