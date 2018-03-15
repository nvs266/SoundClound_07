package com.framgia.mysoundcloud.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.framgia.mysoundcloud.R;
import com.framgia.mysoundcloud.data.model.Playlist;
import com.framgia.mysoundcloud.data.model.Track;
import com.framgia.mysoundcloud.data.repository.TrackRepository;
import com.framgia.mysoundcloud.data.source.TrackDataSource;

import java.util.List;

/**
 * Created by sonng266 on 25/02/2018.
 */

public class DialogManager implements DialogManagerInterface {

    private Context mContext;
    private AlertDialog mAlertDialog;

    public DialogManager(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void dialogMessage(String msg, String title) {
        if (mContext != null) {
            mAlertDialog = new AlertDialog.Builder(mContext)
                    .setMessage(msg)
                    .setTitle(title)
                    .create();
            mAlertDialog.show();
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

    public void dialogAddToPlaylist(final Track track, Activity activity) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_select_playlist, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(mContext.getString(R.string.action_add_to_playlist));
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

}
