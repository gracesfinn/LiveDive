package org.wit.livedive.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.wit.livedive.helpers.readImageFromPath
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.wit.livedive.models.DiveModel
import org.wit.livedive.models.DiveStore
import java.io.ByteArrayOutputStream
import java.io.File

class DiveFireStore(val context: Context) : DiveStore, AnkoLogger {

    val dives = ArrayList<DiveModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference
    lateinit var st: StorageReference

    override fun findAll(): List<DiveModel> {
        return dives
    }

    override fun findById(id: Long): DiveModel? {
        val foundDive: DiveModel? = dives.find { p -> p.id == id }
        return foundDive
    }

    override fun create(dive: DiveModel) {
        db = FirebaseDatabase.getInstance().reference
        val key = db.child("users").child(userId).child("dives").push().key
        key?.let {
            dive.fbId = key
            dives.add(dive)
            db.child("users").child(userId).child("dives").child(key).setValue(dive)
        }
    }

    override fun update(dive: DiveModel) {
        var foundDive: DiveModel? = dives.find { p -> p.fbId == dive.fbId }
        if (foundDive != null) {
            foundDive.title = dive.title
            foundDive.description = dive.description
            foundDive.image = dive.image
            foundDive.location = dive.location
            foundDive.dayVisited = dive.dayVisited
            foundDive.monthVisited = dive.monthVisited
            foundDive.yearVisited = dive.yearVisited
        }

        dive.fbId?.let { db.child("users").child(userId).child("dives").child(it).setValue(dive) }
        if ((dive.image?.length)!! > 0 && (dive.image?.get(0) != 'h')) {
            updateImage(dive)
        }
    }

    override fun delete(dive: DiveModel) {
        dive.fbId?.let { db.child("users").child(userId).child("dives").child(it).removeValue() }
        dives.remove(dive)
    }

    override fun clear() {
        dives.clear()
    }

    fun fetchDives(divesReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot!!.children.mapNotNullTo(dives) { it.getValue<DiveModel>(DiveModel::class.java) }
                divesReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        dives.clear()
        db.child("users").child(userId).child("dives").addListenerForSingleValueEvent(valueEventListener)
    }

    fun updateImage(dive: DiveModel) {
        if (dive.image != "") {
            val fileName = File(dive.image)
            val imageName = fileName.getName()

            var imageRef = st.child(userId + '/' + imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = dive.image?.let { readImageFromPath(context, it) }

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                       dive.image = it.toString()
                        dive.fbId?.let { it1 ->
                            db.child("users").child(userId).child("dives").child(
                                    it1
                            ).setValue(dive)
                        }
                    }
                }
            }
        }
    }


}
