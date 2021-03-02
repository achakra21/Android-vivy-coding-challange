package com.abhijit.vivy_codingtest.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.abhijit.vivy_codingtest.data.local.entity.Doc
import java.io.IOException
import java.util.*

class CommonUtils {
    companion object {
        lateinit var doc: List<Doc>

        @JvmStatic
        fun getAddress(context: Context, latitude: Double, longitude: Double): String {
            var geocoder = Geocoder(context, Locale.getDefault())
            var address = ""
            address = try {
                var addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
                addresses[0].getAddressLine(0)

            } catch (e: IOException) {
                e.message.toString()
            }
            return address
        }

        @JvmStatic
        fun setRecentDoctor(doc: List<Doc>) {
            this.doc = doc

        }

        @JvmStatic
        fun getRecentDoctor(): List<Doc> {
            return doc
        }

    }


}