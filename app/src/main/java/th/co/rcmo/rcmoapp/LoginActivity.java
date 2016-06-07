package th.co.rcmo.rcmoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        final ImageView imageSwitcher = (ImageView)findViewById(R.id.logoLogin);

        imageSwitcher.postDelayed(new Runnable() {
            int i = 0;
            public void run() {
                imageSwitcher.setImageResource(
                        i++ % 2 == 0 ?
                                R.drawable.logo_cloeseyes :
                                R.drawable.logo_openeyes);
                imageSwitcher.postDelayed(this, 1000);
            }
        }, 1000);

    }

    public void actionToCalculateCostActivity(View view)
    {

            Intent intent = new Intent(LoginActivity.this, CalculatePlantCostActivity.class);
            startActivity(intent);




    }

    public void actionToRegisterActivity(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    public void actionToChangePasswordActivity(View view)
    {
        Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
        startActivity(intent);

    }
}
