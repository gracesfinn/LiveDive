package org.wit.livedive.activities

import android.transition.AutoTransition
import android.transition.TransitionManager
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
            viewBinding.ratingBarCard.rating = dive.rating

            val entryMessage = "Entry: ${dive.description}"
            viewBinding.descriptionCard.text = entryMessage
            val dateMessage = "Date Visited: ${dive.dayVisited}/${dive.monthVisited + 1}/${dive.yearVisited} "
            viewBinding.dateCard.text = dateMessage


            val timeMessage = "Dive Time: ${dive.mins} mins"
            viewBinding.timeCard.text = timeMessage

            val depthMessage = "Max Depth: ${dive.maxDepth} ft"
            viewBinding.depthCard.text = depthMessage

            val weatherMessage = "Weather: ${dive.weather}"
            viewBinding.weatherCard.text = weatherMessage

            val oceanMessage = "Ocean: ${dive.ocean}"
            viewBinding.oceanCard.text = oceanMessage

            val weightMessage = "Weight: ${dive.weight} lbs"
            viewBinding.weightCard.text = weightMessage

            viewBinding.wildlifeCard.text = dive.wildlife

            var wetsuitString =
                if(dive.wetsuit)
                    "Wore Wetsuit"
            else
                "No Wetsuit"
            viewBinding.wetsuitCard.text = "${wetsuitString}"

            var airString =
                if(dive.air)
                    "Air Tank"
                else
                    "-----"
            viewBinding.airCard.text = "${airString}"

            var nitroxString =
                if(dive.nitrox)
                    "Nitrox Tank"
                else
                    "-----"
            viewBinding.nitroxCard.text = "${nitroxString}"

            var favImage =
                if(dive.favourite)
                    R.drawable.ic_baseline_favorite_yes
                else
                    R.drawable.ic_baseline_favorite_no

            Glide.with(itemView.context).load(favImage).into(viewBinding.favCard);


            viewBinding.expandBtn.setOnClickListener {
                if (viewBinding.expandableLayout.visibility == View.GONE) {
                    TransitionManager.beginDelayedTransition(viewBinding.cardView, AutoTransition())
                    viewBinding.expandableLayout.visibility = View.VISIBLE
                    viewBinding.expandBtn.text = "COLLAPSE"
                } else{
                    TransitionManager.beginDelayedTransition(viewBinding.cardView, AutoTransition())
                    viewBinding.expandableLayout.visibility = View.GONE
                    viewBinding.expandBtn.text = "EXPAND"
                }

            }




            Glide.with(itemView.context).load(dive.image).into(viewBinding.imageIcon);
            Glide.with(itemView.context).load(dive.wildlifeImage).into(viewBinding.wildlifeImageCard);
            Glide.with(itemView.context).load(dive.poiImage).into(viewBinding.poiImageCard);
            itemView.setOnClickListener{ listener.onDiveClick(dive)}
        }

    }


}

