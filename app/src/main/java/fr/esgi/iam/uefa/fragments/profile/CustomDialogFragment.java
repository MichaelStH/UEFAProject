package fr.esgi.iam.uefa.fragments.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.esgi.iam.uefa.R;
import fr.esgi.iam.uefa.model.User;

/**
 * Created by MichaelWayne on 04/09/2016.
 */
public class CustomDialogFragment extends DialogFragment implements View.OnClickListener{

    private static final String TAG = CustomDialogFragment.class.getSimpleName();

    private EditText oldPasswordEt ;
    private EditText newPasswordEt;

    private Button mButtonCancel;
    private Button mButtonValidate;

    Dialog dialog = this.getDialog();

    private User user;

    // 1. Defines the listener interface with a method passing back data result.
    public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

    public static CustomDialogFragment newInstance(User user, String title) {
        CustomDialogFragment frag = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if ( bundle != null){
            user = bundle.getParcelable("user");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Change password");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_change_user_password, null);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setView(view);

        oldPasswordEt = (EditText) view.findViewById(R.id.team_profile_user_old_password_editText);
        newPasswordEt = (EditText) view.findViewById(R.id.team_profile_user_new_password_editText);

        mButtonCancel = (Button) view.findViewById(R.id.button_change_user_password_cancel) ;
        mButtonValidate = (Button) view.findViewById(R.id.button_change_user_password_validate) ;

        mButtonCancel.setOnClickListener(CustomDialogFragment.this);
        mButtonValidate.setOnClickListener(CustomDialogFragment.this);

        // Show soft keyboard automatically and request focus to field
        oldPasswordEt.requestFocus();
        getActivity().getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        /*
        alertDialogBuilder.setPositiveButton(R.string.validate,  new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // on success
                if ( user == null ){
                    Log.e( TAG, "user is null" );
                    return;
                }
                else{
                    if ( user.getPassword() != oldPasswordEt.getText().toString() ){

                        Log.e(TAG, "sendBackResult() - Wrong old password");
                        Toast.makeText(getActivity(), "Wrong old password", Toast.LENGTH_SHORT).show();
                    }else{

                        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
                        EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();
                        listener.onFinishEditDialog(newPasswordEt.getText().toString());
                        dismiss();
                        Log.e(TAG, "sendBackResult() - dialog.dismiss()");
                    }
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e(TAG, "dialog.dismiss()");
                dialog.dismiss();
            }
        });*/

        return alertDialogBuilder.create();
    }



    @Override
    public void onClick(View view) {

        Log.e( "OHOH"," blabla " );

        switch (view.getId()){
            case R.id.button_change_user_password_validate:

                // on success
                if ( user == null ){
                    Log.e( TAG, "user is null" );
                    return;
                }
                else{
                    if ( !user.getPassword().equals( oldPasswordEt.getText().toString( ) ) ){

                        Log.e(TAG, "sendBackResult() - Wrong old password");
                        Toast.makeText(getActivity(), "Wrong old password", Toast.LENGTH_SHORT).show();
                    }else{

                        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
                        EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();
                        listener.onFinishEditDialog(newPasswordEt.getText().toString());
                        dismiss();
                        Log.e(TAG, "sendBackResult() - dialog.dismiss()");
                    }
                }
                break;

            case R.id.button_change_user_password_cancel:
                this.dismiss();
                break;

            default:
                break;
        }

    }


/*
    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {

        if ( user != null ){
            Log.e( TAG, "user is null" );
            return;
        }
        else{
            if ( user.getPassword() != oldPasswordEt.getText().toString() ){

                Log.e(TAG, "sendBackResult() - Wrong old password");
                Toast.makeText(getActivity(), "Wrong old password", Toast.LENGTH_SHORT).show();
            }else{

                // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
                EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();
                listener.onFinishEditDialog(newPasswordEt.getText().toString());
                dismiss();
                Log.e(TAG, "sendBackResult() - dialog.dismiss()");
            }
        }

    }*/
}
