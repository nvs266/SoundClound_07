package com.framgia.mysoundcloud.widget;

/**
 * Created by sonng266 on 25/02/2018.
 */

public interface DialogManagerInterface {

    void dialogMessage(String msg, String title);

    void dialogButton(String msg, String title,
                      String positiveButton, String negativeButton, DialogListener mListener);

    void dismissDialog();

    interface DialogListener {
        void onDialogPositiveClick();
        void onDialogNegativeClick();
    }
}
