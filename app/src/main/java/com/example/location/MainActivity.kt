//===========================================================================
// Debemos añadir en build.grandle
//
//implementation "com.google.android.gms:play-services-location:15.0.1"
// o versiones posteriores
//===========================================================================

package com.example.location

import android.annotation.SuppressLint
import android.content.IntentSender
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    private lateinit var flocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.createLocationRequest()
        flocationClient = LocationServices.getFusedLocationProviderClient(this)
        var tarea = flocationClient.lastLocation
            tarea.addOnSuccessListener { lugar ->
                println("loc = $lugar \n")
            }

        tarea.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this@MainActivity,
                        1)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
    fun createLocationRequest() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}
