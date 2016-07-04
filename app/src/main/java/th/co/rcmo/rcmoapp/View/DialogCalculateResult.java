package th.co.rcmo.rcmoapp.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.util.StringBuilderPrinter;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.neopixl.pixlui.components.textview.TextView;

import th.co.rcmo.rcmoapp.CalculateResultActivity;
import th.co.rcmo.rcmoapp.Model.UserPlotModel;
import th.co.rcmo.rcmoapp.Model.calculate.CalculateResultModel;
import th.co.rcmo.rcmoapp.PBCalculateResultActivity;
import th.co.rcmo.rcmoapp.R;
import th.co.rcmo.rcmoapp.RegisterActivity;
import th.co.rcmo.rcmoapp.Util.ServiceInstance;
import th.co.rcmo.rcmoapp.Util.Util;


public class DialogCalculateResult {

    Context context;
    OnSelectChoiceListener onSelectChoiceListener;

    public static UserPlotModel userPlotModel;
    public static CalculateResultModel calculateResultModel;

    public interface OnSelectChoiceListener {
        void OnSelect(int choice);
    }

    public DialogCalculateResult(Context c, OnSelectChoiceListener onSelectChoiceListener) {
        this.context = c;
        this.onSelectChoiceListener = onSelectChoiceListener;
    }

    public DialogCalculateResult(Context c) {
        this.context = c;
    }


    public android.app.Dialog Show() {
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_calculate_result);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        ImageView iconResult = (ImageView) dialog.findViewById(R.id.calResultProfitLossImage);
        TextView txResultString = (TextView) dialog.findViewById(R.id.txResultString);
        TextView txResult = (TextView) dialog.findViewById(R.id.txResult);
        TextView txResultValue = (TextView) dialog.findViewById(R.id.txResultValue);

        ImageButton btnShowReport = (ImageButton) dialog.findViewById(R.id.btnShowReport);

        if (calculateResultModel.calculateResult > 0) {
            iconResult.setBackgroundResource(R.drawable.ic_profit);
            MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(), R.raw.profit);
            mp.start();
            txResultString.setText("ดีใจด้วย");
            txResult.setText("คุณได้กำไร");
            txResultValue.setText("จำนวน " + String.format("%,.2f", calculateResultModel.calculateResult) + " บาท");
        } else {
            iconResult.setBackgroundResource(R.drawable.ic_losecost);
            MediaPlayer mp = MediaPlayer.create(context.getApplicationContext(), R.raw.loss);
            mp.start();
            txResultString.setText("เสียใจด้วย");
            txResult.setText("คุณขาดทุน");
            txResultValue.setText("จำนวน " + String.format("%,.2f", calculateResultModel.calculateResult) + " บาท");
        }

        btnShowReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PBCalculateResultActivity.calculateResultModel = calculateResultModel;
                userPlotModel.setCalResult(String.valueOf(calculateResultModel.calculateResult));
                PBCalculateResultActivity.userPlotModel = userPlotModel;
                context.startActivity(new Intent(context, PBCalculateResultActivity.class));
                dialog.dismiss();
            }
        });

        dialog.show();


        return dialog;
    }

}
