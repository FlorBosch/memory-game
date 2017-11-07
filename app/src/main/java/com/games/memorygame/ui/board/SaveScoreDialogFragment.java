package com.games.memorygame.ui.board;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.games.memorygame.R;


public class SaveScoreDialogFragment extends DialogFragment {

    private OnCompleteListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.listener = (OnCompleteListener) context;
        } catch (final ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.save_score)
                .setView(R.layout.insert_score_layout)
                .setCancelable(true)
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    TextInputEditText editText = getDialog().findViewById(R.id.insert_player);
                    listener.onComplete(editText.getText().toString());
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) ->
                        listener.onComplete(null)
                )
                .create();
    }

    public interface OnCompleteListener {
        void onComplete(String userName);
    }
}
