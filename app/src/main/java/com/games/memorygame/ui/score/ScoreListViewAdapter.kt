package com.games.memorygame.ui.score


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.games.memorygame.R
import com.games.memorygame.model.Score
import kotlinx.android.synthetic.main.item_list_score.view.*
import java.text.SimpleDateFormat
import java.util.*


internal class ScoreListViewAdapter(private val scores: List<Score>) :
        RecyclerView.Adapter<ScoreListViewAdapter.ScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        return ScoreViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_score, parent, false))
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.onBindView(scores[position])
    }

    override fun getItemCount(): Int = scores.size

    inner class ScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun onBindView(item: Score) {
            itemView.user_name.text = item.user
            itemView.date.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(item.date)
            itemView.score.text = item.scorePoints.toString()
        }
    }
}
