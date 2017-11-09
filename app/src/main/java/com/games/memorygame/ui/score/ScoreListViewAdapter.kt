package com.games.memorygame.ui.score


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.games.memorygame.R
import com.games.memorygame.model.Score
import com.games.memorygame.ui.BaseViewHolder

import java.text.SimpleDateFormat
import java.util.Locale

import butterknife.BindView

internal class ScoreListViewAdapter(private val scores: List<Score>) : RecyclerView.Adapter<ScoreListViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_score, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindView(scores[position])
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    internal inner class ViewHolder(view: View) : BaseViewHolder<Score>(view) {

        @BindView(R.id.user_name) lateinit var userNameView: TextView

        @BindView(R.id.date) lateinit var dateView: TextView

        @BindView(R.id.score) lateinit var scoreView: TextView

        override fun onBindView(item: Score) {
            super.onBindView(item)
            userNameView.text = item.user
            dateView.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(item.date)
            scoreView.text = item.scorePoints.toString()
        }

    }
}
