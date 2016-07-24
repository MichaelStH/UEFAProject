package fr.esgi.iam.uefa.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.esgi.iam.uefa.R;

/**
 * Created by MichaelWayne on 23/07/2016.
 */
public class ForgotPasswordDialogFragment extends DialogFragment{

    private IDialogFrag dialogInterface;

    public interface IDialogFrag {

        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        dialogInterface = (IDialogFrag) activity;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Mot de passe oublié");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialog.setView(inflater.inflate(R.layout.forgot_password_fragment, null))
                .setPositiveButton(R.string.forgot_password_send, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialogInterface.onDialogPositiveClick(ForgotPasswordDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.forgot_password_cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialogInterface.onDialogNegativeClick(ForgotPasswordDialogFragment.this);

                    }
                });

        return dialog.create();
    }

}
