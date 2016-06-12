package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class StepTwoActivity extends Activity {
    int groupId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groupId = getIntent().getExtras().getInt("GROUP_ID");

        if(groupId == 1){
            setContentView(R.layout.activity_step_two_plant);
        }else if(groupId == 2){
            setContentView(R.layout.activity_step_two_animal);
        }else if(groupId == 3){
            setContentView(R.layout.activity_step_two_fish);
        }



        setUI();
        setAction();

    }

    private void setUI() {

    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
