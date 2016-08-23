package th.go.oae.rcmo.Adapter;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import th.go.oae.rcmo.Model.UserPlotModel;
import th.go.oae.rcmo.PBProdDetailCalculateFment;
import th.go.oae.rcmo.PBProdDetailCalculateFmentA;
import th.go.oae.rcmo.PBProdDetailCalculateFmentB;
import th.go.oae.rcmo.PBProdDetailCalculateFmentC;
import th.go.oae.rcmo.PBProdDetailCalculateFmentD;
import th.go.oae.rcmo.PBProdDetailCalculateFmentE;
import th.go.oae.rcmo.PBProdDetailCalculateFmentF;
import th.go.oae.rcmo.PBProdDetailCalculateFmentG;
import th.go.oae.rcmo.PBProdDetailCalculateFmentH;
import th.go.oae.rcmo.PBProdDetailCalculateFmentI;
import th.go.oae.rcmo.PBProdDetailCalculateFmentJ;
import th.go.oae.rcmo.PBProdDetailCalculateFmentK;
import th.go.oae.rcmo.PBProdDetailStandradFment;
import th.go.oae.rcmo.ProductDetailMapFragment;

/**
 * Created by Taweesin on 24/6/2559.
 */
public class PBCalculatePagerAdapter extends FragmentStatePagerAdapter {

    UserPlotModel userPlotModel;
    Context context;


    public PBCalculatePagerAdapter(FragmentManager fm , UserPlotModel userPlotModel) {
        super(fm);
        this.userPlotModel = userPlotModel;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position) {
            case 0:
                frag = new PBProdDetailStandradFment();
                break;
            case 1:

                Log.d("TAG", "---------------------------plan :"+userPlotModel.getFormularCode());
                if ("A".equalsIgnoreCase(userPlotModel.getFormularCode())) {
                    frag = new PBProdDetailCalculateFmentA();
                } else if ("B".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentB();

                }else if ("C".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentC();

                }else if ("D".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentD();

                }else if ("E".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentE();

                }else if ("F".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentF();

                }else if ("G".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentG();

                }else if ("H".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentH();

                } else if ("I".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentI();

                } else if ("J".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentJ();

                }else if ("K".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag = new PBProdDetailCalculateFmentK();

                }else {
                    frag = new PBProdDetailCalculateFment();
                }
                /*
                if ("A".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentA();
                }else if ("B".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentB();
                }else if ("C".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentC();
                }else if ("D".equalsIgnoreCase(userPlotModel.getFormularCode())){
                    frag=new ProductDetailCalculateFragmentD();
                }else {
                    frag = new PBProdDetailCalculateFment();
                }
                */
                break;
            case 2:
                frag=new ProductDetailMapFragment();
                //frag=new PBProdDetailMapFment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title=" ";
        switch (position){
            case 0:
                title="ค่าเฉลี่ย";
                break;
            case 1:
                title="คำนวนต้นทุน";
                break;
            case 2:
                title="แผนที่";
                break;
        }

        return title;
    }

}
