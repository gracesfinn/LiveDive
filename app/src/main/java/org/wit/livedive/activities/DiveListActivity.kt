package org.wit.livedive.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dive.view.*
import kotlinx.android.synthetic.main.activity_divelist.*
import kotlinx.android.synthetic.main.card_dive.view.*
import org.jetbrains.anko.startActivityForResult
import org.wit.livedive.R
import org.wit.livedive.main.MainApp
import org.wit.livedive.models.DiveModel


class DiveListActivity: AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divelist)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = DiveAdapter(app.dives)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<DiveActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    class DiveAdapter constructor(private var dives: List<DiveModel>) :
        RecyclerView.Adapter<DiveAdapter.MainHolder>() {

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
            holder.bind(dive)
        }

        override fun getItemCount(): Int = dives.size

        class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(dive: DiveModel) {
                itemView.diveTitleCard.text = dive.title
                itemView.descriptionCard.text = dive.description
            }

        }
    }
}