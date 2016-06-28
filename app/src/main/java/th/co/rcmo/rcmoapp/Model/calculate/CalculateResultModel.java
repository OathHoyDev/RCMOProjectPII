package th.co.rcmo.rcmoapp.Model.calculate;

import java.util.ArrayList;
import java.util.List;

import th.co.rcmo.rcmoapp.Module.mGetPlotSuit;

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

}
