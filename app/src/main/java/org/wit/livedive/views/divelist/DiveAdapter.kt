package org.wit.livedive.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.jetbrains.anko.toast

import org.wit.livedive.R
import org.wit.livedive.databinding.CardDiveBinding
import org.wit.livedive.helpers.readImageFromPath
import org.wit.livedive.models.DiveModel


interface DiveListener{
    fun onDiveClick(dive: DiveModel)
}

class DiveAdapter constructor(
    private var dives: List<DiveModel>,
    private val listener: DiveListener


) : RecyclerView.Adapter<DiveAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDiveBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val dive = dives[holder.adapterPosition]
        holder.bind(dive, listener)
    }


    override fun getItemCount(): Int = dives.size

    class MainHolder constructor(var viewBinding: CardDiveBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(dive: DiveModel, listener: DiveListener) {
            viewBinding.diveTitleCard.text = dive.title
            viewBinding.descriptionCard.text = dive.description
            val message = "Date Visited: ${dive.dayVisited}/${dive.monthVisited + 1}/${dive.yearVisited} "
            viewBinding.dateCard.text = message
            Glide.with(itemView.context).load(dive.image).into(viewBinding.imageIcon);
            itemView.setOnClickListener{ listener.onDiveClick(dive)}
        }

    }






}

