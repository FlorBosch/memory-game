package com.games.memorygame.ui.board

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

import com.games.memorygame.R


class SaveScoreDialogFragment : DialogFragment() {

    private lateinit var listener: OnCompleteListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as OnCompleteListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context?.toString() + " must implement OnCompleteListener")
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
                .setTitle(R.string.save_score)
                .setView(R.layout.insert_score_layout)
                .setCancelable(true)
                .setPositiveButton(R.string.save) { _, _ ->
                    val editText = dialog.findViewById<TextInputEditText>(R.id.insert_player)
                    listener.onComplete(editText.text.toString())
                }
                .setNegativeButton(R.string.cancel
                ) { _, _ -> listener.onComplete(null) }
                .create()
    }

    interface OnCompleteListener {
        fun onComplete(userName: String?)
    }
}
