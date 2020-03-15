package ua.ck.zabochen.mapapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.clustering.ClusterManager

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var clusterManager: ClusterManager<MyItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // https://developers.google.com/maps/documentation/android-sdk/controls
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = false

        workWithPolygon(googleMap)
    }

    private fun clusterManager() {

        // https://stackoverflow.com/questions/15495171/cluster-markers-in-google-maps-android-v2

        // Show center
        val cherkasy = LatLng(49.445400, 32.062274)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cherkasy, 10f))
        this.clusterManager = ClusterManager(this, mMap)
        mMap.setOnCameraIdleListener(clusterManager)

        // Add a markers
        val roseValley1 = LatLng(49.450952, 32.065195)
        val roseValley2 = LatLng(49.450547, 32.065909)
        val roseValley3 = LatLng(49.450651, 32.065604)
        val roseValley4 = LatLng(49.450789, 32.066091)
        val roseValley5 = LatLng(49.450867, 32.066123)

        val beach = LatLng(49.446138, 32.078189)
        val mcDonald = LatLng(49.441300, 32.064302)

        val markerList = listOf<MarkerOptions>(
            MarkerOptions().position(roseValley1).title("Rose Valley1"),
            MarkerOptions().position(roseValley2).title("Rose Valley2"),
            MarkerOptions().position(roseValley3).title("Rose Valley3"),
            MarkerOptions().position(roseValley4).title("Rose Valley4"),
            MarkerOptions().position(roseValley5).title("Rose Valley5"),
            MarkerOptions().position(beach).title("Beach"),
            MarkerOptions().position(mcDonald).title("McDonald's")
        )

        val clusterItemList = arrayListOf<MyItem>()
        markerList.forEach {
            with(
                MyItem(
                    position = it.position,
                    title = it.title,
                    snippet = "Some Snippet"
                )
            ) {
                clusterItemList.add(this)
            }
        }
        this.clusterManager.addItems(clusterItemList)
    }

    private fun workWithPolygon(googleMap: GoogleMap) {

        // Show center
        val cherkasy = LatLng(49.4508553, 32.0658822)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cherkasy, 10f))

        // Add a markers
        val roseValley1 = LatLng(49.450419, 32.062910)
        val roseValley2 = LatLng(49.452011, 32.064457)
        val roseValley3 = LatLng(49.451079, 32.067123)
        val roseValley4 = LatLng(49.449859, 32.065810)

        val polygonDots = listOf(
            roseValley1,
            roseValley2,
            roseValley3,
            roseValley4
        )

        // https://stackoverflow.com/questions/5248583/how-to-get-a-color-from-hexadecimal-color-string
        val polygon = googleMap.addPolygon(
            PolygonOptions()
                .fillColor(ContextCompat.getColor(this, R.color.color_orange))
                .strokeColor(ContextCompat.getColor(this, R.color.color_blue))
                .addAll(polygonDots)
                .strokeWidth(10f)
        )
    }

    private fun showOneMarker() {
        // https://developers.google.com/maps/documentation/android-sdk/views
        // https://stackoverflow.com/questions/13932441/android-google-maps-v2-set-zoom-level-for-mylocation
        val cherkasy = LatLng(49.445400, 32.062274)
        mMap.addMarker(MarkerOptions().position(cherkasy).title("Cherkasy"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cherkasy, 10f))
    }

    private fun showMarkerList() {
        // Add a markers
        val cherkasy = LatLng(49.445400, 32.062274)
        val roseValley = LatLng(49.450952, 32.065195)
        val beach = LatLng(49.446138, 32.078189)
        val mcDonald = LatLng(49.441300, 32.064302)

        val markerList = listOf<MarkerOptions>(
            MarkerOptions().position(cherkasy).title("Cherkasy"),
            MarkerOptions().position(roseValley).title("Rose Valley"),
            MarkerOptions().position(beach).title("Beach"),
            MarkerOptions().position(mcDonald).title("McDonald's")
        )

        val latLngBounds = LatLngBounds.Builder()

        markerList.forEach {
            mMap.addMarker(it)
            latLngBounds.include(it.position)
        }

        // https://stackoverflow.com/questions/14828217/android-map-v2-zoom-to-show-all-the-markers
        // https://stackoverflow.com/questions/13692579/movecamera-with-cameraupdatefactory-newlatlngbounds-crashes

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val padding = (width * 0.2).toInt()
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                latLngBounds.build(),
                width, height,
                padding
            )
        )
    }
}
