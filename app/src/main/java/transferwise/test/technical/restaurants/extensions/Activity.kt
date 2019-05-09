package transferwise.test.technical.restaurants.extensions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import transferwise.test.technical.restaurants.R

inline fun Activity.isGooglePlayServicesAvailable(crossinline  dismiss: () -> Unit): Boolean {
    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
    if (status != ConnectionResult.SUCCESS) {
        if (googleApiAvailability.isUserResolvableError(status)) {
            val dialog = googleApiAvailability.getErrorDialog(this, status, 2404)
            dialog.setOnDismissListener {
                dismiss()
            }
            dialog.show()
        }
        return false
    }
    return true
}

inline fun Activity.requestLocation(
        crossinline denied: () -> Unit,
        crossinline onLocationHad: (Location) -> Unit
) {

    getDecimal(R.dimen.default_latitude).toDouble()
    val defaultLocation = Location("").apply {
        latitude = getDecimal(R.dimen.default_latitude).toDouble()
        longitude = getDecimal(R.dimen.default_longitude).toDouble()
    }

    val isGPSAvailable = isGooglePlayServicesAvailable {
        denied()
    }

    if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && isGPSAvailable) {

        LocationServices.getFusedLocationProviderClient(this).lastLocation
                .addOnSuccessListener {
                    if(it == null)
                        onLocationHad(defaultLocation)
                    else
                        onLocationHad(it)
                }
                .addOnCanceledListener {
                    onLocationHad(defaultLocation)
                }
                .addOnFailureListener {
                    onLocationHad(defaultLocation)
                }
    }
    else if(isGPSAvailable)
        denied()
}