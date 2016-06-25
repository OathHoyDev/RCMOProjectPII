package th.co.rcmo.rcmoapp.Adapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import th.co.rcmo.rcmoapp.PBProdDetailCalculateFment;
import th.co.rcmo.rcmoapp.PBProdDetailMapFment;
import th.co.rcmo.rcmoapp.PBProdDetailStandradFment;
import th.co.rcmo.rcmoapp.ProductDetailMapFragment;

/**
 * Created by Taweesin on 24/6/2559.
 */
public class PBCalculatePagerAdapter extends FragmentStatePagerAdapter {

    public PBCalculatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new PBProdDetailStandradFment();
                break;
            case 1:
                frag=new PBProdDetailCalculateFment();
                break;
            case 2:
               // frag=new PBProdDetailMapFment();
                   frag = new ProductDetailMapFragment();
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
                title="ค่ามาตรฐาน";
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
