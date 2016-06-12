package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StepOneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_one);
        setAction();
    }

    private void setAction() {
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.ic_bg_g1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepOneActivity.this, StepTwoActivity.class)
                        .putExtra("GROUP_ID", 1));
            }
        });

        findViewById(R.id.ic_bg_g2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepOneActivity.this, StepTwoActivity.class)
                        .putExtra("GROUP_ID", 2));
            }
        });

        findViewById(R.id.ic_bg_g3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StepOneActivity.this, StepTwoActivity.class)
                        .putExtra("GROUP_ID", 3));
            }
        });
    }
}
