package com.example.momoleague;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MyApp extends Application {
    private static String uid;
    private static boolean isAdmin = false;

    public static Dialog getInputDialog(Context cntx, DialogInputCallback callback){
        Dialog dialog = new Dialog(cntx);
        View view = View.inflate(cntx,R.layout.input_dialog,null);
        dialog.setContentView(view);
        Button ok = view.findViewById(R.id.dialog_ok);
        Button cancel = view.findViewById(R.id.dialog_cancel);
        EditText input = view.findViewById(R.id.dialog_input);
        ok.setOnClickListener(v->{
            if(!input.getText().toString().isEmpty())
                callback.onOk(input.getText().toString());
            else
                return;
            dialog.dismiss();});
        cancel.setOnClickListener(v->dialog.dismiss());
        return dialog;
    }

    public static Dialog getGuessDialog(Context cntx, GuessDialogCallback callback){
        Dialog dialog = new Dialog(cntx);
        View view = View.inflate(cntx,R.layout.guess_dialog,null);
        dialog.setContentView(view);
        Button ok = view.findViewById(R.id.guess_dialog_button);
        RadioGroup radioGroup = view.findViewById(R.id.guess_dialog_radio_group);
        ok.setOnClickListener(v->{
            int id = radioGroup.getCheckedRadioButtonId();
            switch (id){
                case R.id.guess_dialog_radio_1:
                    callback.onOk(Guess.ONE);
                    break;
                case R.id.guess_dialog_radio_2:
                    callback.onOk(Guess.TWO);
                    break;
                case R.id.guess_dialog_radio_x:
                    callback.onOk(Guess.X);
                    break;
                default:
                    return;
            }
            dialog.dismiss();
        });
        return dialog;
    }

    public static String getUid(){
        return uid;
    }

    public static void setUid(String uid) {
        MyApp.uid = uid;
    }

    public static boolean getIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        MyApp.isAdmin = isAdmin;
    }

    public enum Guess{
        ONE,X,TWO
    }

    public interface GuessDialogCallback{
        void onOk(Guess guess);
    }

    public interface DialogInputCallback{
        void onOk(String input);
    }
}
