package th.co.rcmo.rcmoapp.View;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import th.co.rcmo.rcmoapp.R;


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