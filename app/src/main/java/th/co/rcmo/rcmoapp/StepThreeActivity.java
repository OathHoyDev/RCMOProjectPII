package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.os.Bundle;

import th.co.rcmo.rcmoapp.Util.ServiceInstance;

public class StepThreeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        int groupId = 0;
        if (bundle != null) {
             groupId = bundle.getInt(ServiceInstance.INTENT_GROUP_ID);

        }
        if(groupId == 1){
            setContentView(R.layout.activity_plant_step_three);
        }else if(groupId == 2){
            setContentView(R.layout.activity_animal_step_three);
        }else{
            setContentView(R.layout.activity_fish_step_three);
        }

    }
}
