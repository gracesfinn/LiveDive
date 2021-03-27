package org.wit.livedive.views.divelist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_divelist.*
import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.activities.DiveAdapter
import org.wit.livedive.activities.DiveListener
import org.wit.livedive.models.DiveModel

class DiveListView :  BaseView(), DiveListener {

    lateinit var presenter: DiveListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divelist)
        setSupportActionBar(toolbar)

        presenter = initPresenter(DiveListPresenter(this)) as DiveListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadDives()
    }

    override fun showDives(dives: List<DiveModel>) {
        recyclerView.adapter = DiveAdapter(dives, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddDive()
            R.id.item_map -> presenter.doShowDivesMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDiveClick(dive: DiveModel) {
        presenter.doEditDive(dive)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}