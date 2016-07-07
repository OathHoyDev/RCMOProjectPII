package th.go.oae.rcmo.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import th.go.oae.rcmo.R;
import th.go.oae.rcmo.Util.BitMapHelper;
import th.go.oae.rcmo.Util.ServiceInstance;
import th.go.oae.rcmo.Util.Util;


public class DialogChoice {

    public static int OK =0;
    public static int CANCEL =1;
    Context context;
    OnSelectChoiceListener onSelectChoiceListener;

    public interface  OnSelectChoiceListener{
        void OnSelect(int choice);
    }
    public DialogChoice(Context c, OnSelectChoiceListener onSelectChoiceListener){
        this.context =c;
        this.onSelectChoiceListener =onSelectChoiceListener;
    }

    public DialogChoice(Context c){
        this.context =c;
    }


    public android.app.Dialog Show(String t,String msg){
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        TextView title =(TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView)dialog.findViewById(R.id.message);
        TextView btn_ok = (TextView)dialog.findViewById(R.id.ok);

        // dialog.findViewById(R.id.cancel).setVisibility(View.GONE);
        //dialog.findViewById(R.id.line).setVisibility(View.GONE);
        if(t.length()==0) title.setVisibility(View.GONE);
        else title.setText(t);
        if(msg.length()==0) detail.setVisibility(View.GONE);
        else detail.setText(msg);

        btn_ok.setVisibility(View.GONE);

        dialog.show();


        return  dialog;
    }


    public void ShowOneChoice(String t,String msg){
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        TextView title =(TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView)dialog.findViewById(R.id.message);
        TextView btn_ok = (TextView)dialog.findViewById(R.id.ok);

        // dialog.findViewById(R.id.cancel).setVisibility(View.GONE);
        //dialog.findViewById(R.id.line).setVisibility(View.GONE);
        if(t.length()==0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });


        dialog.show();


    }

    public void ShowLocationSettingChoice(String t,String msg){

        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title =(TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView)dialog.findViewById(R.id.message);
        TextView btn_cancel = (TextView)dialog.findViewById(R.id.cancel);
        TextView btn_ok = (TextView)dialog.findViewById(R.id.ok);

        if(t.length()==0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(callGPSSettingIntent);

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(CANCEL);
                dialog.cancel();
            }
        });
        dialog.show();

    }

    public void ShowTwoChoice(String t,String msg){

        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_two_choice);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title =(TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView)dialog.findViewById(R.id.message);
        TextView btn_cancel = (TextView)dialog.findViewById(R.id.cancel);
        TextView btn_ok = (TextView)dialog.findViewById(R.id.ok);

        if(t.length()==0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(CANCEL);
                dialog.cancel();
            }
        });
        dialog.show();

    }


    public void ShowAppLink(){
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_app_link);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.TOP | Gravity.LEFT;

        ImageView dialogGotoOtherApp = (ImageView)dialog.findViewById(R.id.dialogGotoOtherApp);
        ImageView imgOicApp = (ImageView)dialog.findViewById(R.id.imgOicApp);
        ImageView imgEconApp = (ImageView)dialog.findViewById(R.id.imgEconApp);


        dialogGotoOtherApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });
        imgOicApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.onLunchAnotherApp(context, ServiceInstance.OIC_PACKAGE_NAME);
                dialog.dismiss();
            }
        });
        imgEconApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.onLunchAnotherApp(context, ServiceInstance.ECON_PACKAGE_NAME);
                dialog.dismiss();
            }
        });
       // wmlp.x = 100;   //x position
        //wmlp.y = 100;   //y position

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

        /*
        TextView title =(TextView) dialog.findViewById(R.id.title);
        TextView detail = (TextView)dialog.findViewById(R.id.message);
        TextView btn_ok = (TextView)dialog.findViewById(R.id.ok);

        // dialog.findViewById(R.id.cancel).setVisibility(View.GONE);
        //dialog.findViewById(R.id.line).setVisibility(View.GONE);
        if(t.length()==0) title.setVisibility(View.GONE);
        else title.setText(t);
        detail.setText(msg);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(OK);
                dialog.dismiss();
            }
        });

*/
        dialog.show();


    }


    public void ShowTutorial(String tutorailBg){
        final android.app.Dialog dialog = new android.app.Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_tutorail);

        dialog.getWindow().setBackgroundDrawable(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(context.getResources(), context.getResources().getIdentifier(tutorailBg, "drawable", context.getPackageName()), 300, 400)));


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        LinearLayout tutoraioBg = (LinearLayout)dialog.findViewById(R.id.tutorail_bg);
        TextView tutorail_close = (TextView)dialog.findViewById(R.id.tutorail_close);

      //  tutoraioBg.setBackground(new BitmapDrawable(BitMapHelper.decodeSampledBitmapFromResource(context.getResources(), context.getResources().getIdentifier(tutorailBg, "drawable", context.getPackageName()), 300, 400)));


        tutoraioBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        tutorail_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


        /*
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onSelectChoiceListener!=null)
                    onSelectChoiceListener.OnSelect(CANCEL);
                dialog.cancel();
            }
        });
        */
     //   dialog.show();

    }
//    public void ShowOneChoice(String title, String message){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);
//
//        if(title.length()!=0)
//            alertDialogBuilder.setTitle(title);
//
//        alertDialogBuilder
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                       if(onSelectChoiceListener!=null)
//                           onSelectChoiceListener.OnSelect(OK);
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

//    public  void ShowTwoChoice(String title,String message){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                context);
//
//        if(title.length()!=0)
//            alertDialogBuilder.setTitle(title);
//
//        alertDialogBuilder
//                .setMessage(message)
//                .setCancelable(false)
//                .setPositiveButton("ตกลง",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        onSelectChoiceListener.OnSelect(OK);
//                    }
//                })
//                .setNegativeButton("ยกเลิก",new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog,int id) {
//                        onSelectChoiceListener.OnSelect(CANCEL);
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

//}