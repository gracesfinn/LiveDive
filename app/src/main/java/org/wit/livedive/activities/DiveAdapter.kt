package org.wit.livedive.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_dive.view.*
import org.wit.livedive.R
import org.wit.livedive.models.DiveModel


interface DiveListener{
    fun onDiveClick(dive: DiveModel)
}

class DiveAdapter constructor(
    private var dives: List<DiveModel>,
    private val listener: DiveListener
    ) : RecyclerView.Adapter<DiveAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_dive,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val dive = dives[holder.adapterPosition]
        holder.bind(dive, listener)
    }

    override fun getItemCount(): Int = dives.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(dive: DiveModel, listener: DiveListener) {
            itemView.diveTitleCard.text = dive.title
            itemView.descriptionCard.text = dive.description
            itemView.setOnClickListener{ listener.onDiveClick(dive)}
        }

    }

}

