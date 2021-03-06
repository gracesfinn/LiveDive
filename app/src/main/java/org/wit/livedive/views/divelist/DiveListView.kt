package org.wit.livedive.views.divelist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager

import org.wit.livedive.BaseView
import org.wit.livedive.R
import org.wit.livedive.activities.DiveAdapter
import org.wit.livedive.activities.DiveListener
import org.wit.livedive.databinding.ActivityDivelistBinding
import org.wit.livedive.models.DiveModel

class DiveListView :  BaseView(), DiveListener {

    lateinit var presenter: DiveListPresenter
    private lateinit var binding: ActivityDivelistBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDivelistBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        super.init(binding.toolbar, false)

        binding.bottomAppBar.replaceMenu(R.menu.bottom_app_bar)

        binding.bottomAppBar.setOnMenuItemClickListener{menuItem ->

            when(menuItem.itemId){

                R.id.item_logout -> presenter.doLogout()
                R.id.item_map -> presenter.doShowDivesMap()
                R.id.item_favourite -> presenter.doShowFavourites()
                R.id.item_settings -> presenter.doUpdateUser()
                R.id.item_home-> presenter.doShowList()
            }
            true
        }



        presenter = initPresenter(DiveListPresenter(this)) as DiveListPresenter

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        presenter.loadDives()

        binding.addNew.setOnClickListener{
            presenter.doAddDive()
        }





    }

    override fun showDives(dives: List<DiveModel>) {
        binding.recyclerView.adapter = DiveAdapter(dives, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    


    override fun onDiveClick(dive: DiveModel) {
        presenter.doEditDive(dive)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}