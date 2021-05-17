package org.wit.livedive

import android.content.Intent
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.AnkoLogger
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.Location
import org.wit.livedive.views.dive.DiveView
import org.wit.livedive.views.divelist.DiveListView
import org.wit.livedive.views.location.EditLocationView
import org.wit.livedive.views.login.LoginView
import org.wit.livedive.views.map.DiveMapView
import org.wit.livedive.views.register.RegisterView
import org.wit.livedive.views.settings.SettingsView
import org.wit.livedive.views.socialMap.SocialMapView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 4
val IMAGE_WILDLIFE_REQUEST = 2
val IMAGE_POI_REQUEST = 3
val IMAGE_PROFILE_REQUEST = 5

enum class VIEW {
    LOCATION, DIVE, MAPS, LIST, LOGIN, FAVOURITE, SETTINGS, REGISTER, SOCIAL
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    open var user = FirebaseAuth.getInstance().currentUser

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, DiveListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.DIVE -> intent = Intent(this, DiveView::class.java)
            VIEW.MAPS -> intent = Intent(this, DiveMapView::class.java)
            VIEW.LIST -> intent = Intent(this, DiveListView::class.java)
            VIEW.LOGIN -> intent = Intent(this, LoginView::class.java)
            VIEW.FAVOURITE -> intent = Intent(this, DiveListView :: class.java).putExtra("favourite", true)
            VIEW.SETTINGS -> intent = Intent( this, SettingsView::class.java)
            VIEW.REGISTER  -> intent = Intent(this,RegisterView::class.java)
            VIEW.SOCIAL -> intent = Intent(this,SocialMapView::class.java)


        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }


    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            toolbar.title = "${user.email} 's ${title}"
        }
        if(intent.hasExtra("favourite")){
            toolbar.title = "Favourite Dives"
        }
    }



    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun initLogin(toolbar: Toolbar) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }


    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showDive(dive: DiveModel?) {}
    open fun showDives(dives: List<DiveModel>) {}
    open fun showLocation(location : Location) {}
    open fun showProgress() {}
    open fun hideProgress() {}
}