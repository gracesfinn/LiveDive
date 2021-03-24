package org.wit.livedive.models


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DiveModel (
    var id: Long = 0,
    var title: String = "",
    var description: String = ""): Parcelable