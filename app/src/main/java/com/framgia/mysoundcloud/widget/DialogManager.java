package com.framgia.mysoundcloud.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by sonng266 on 25/02/2018.
 */

public class DialogManager implements DialogManagerInterface {

    private Context mContext;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void dialogMessage(String msg, String title) {
        if (mContext != null) {
            new AlertDialog.Builder(mContext)
                    .setMessage(msg)
                    .setTitle(title)
                    .create()
                    .show();
        }
    }

    @Override
    public void dialogButton(
            String msg, String title, String positiveButton,
            String negativeButton, final DialogListener mListener) {
        if (mContext != null && mListener != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(msg)
                    .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mListener.onDialogPositiveClick();
                        }
                    })
                    .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mListener.onDialogNegativeClick();
                        }
                    })
                    .create()
                    .show();
        }
    }

}
