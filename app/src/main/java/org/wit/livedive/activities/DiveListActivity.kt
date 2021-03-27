package org.wit.livedive.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_divelist.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import org.wit.livedive.R
import org.wit.livedive.main.MainApp
import org.wit.livedive.models.DiveModel


class DiveListActivity: AppCompatActivity(), DiveListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divelist)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        //recyclerView.adapter = DiveAdapter(app.dives)
        //recyclerView.adapter = DiveAdapter(app.dives.findAll(), this)
        loadDives()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<DiveActivity>(0)
            R.id.item_map -> startActivity<DiveMapsActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDiveClick(dive: DiveModel) {
        startActivityForResult(intentFor<DiveActivity>().putExtra("dive_edit", dive), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //recyclerView.adapter?.notifyDataSetChanged()
        loadDives()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadDives() {
        showDives(app.dives.findAll())
    }

    fun showDives (dives: List<DiveModel>) {
        recyclerView.adapter = DiveAdapter(dives, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }


}