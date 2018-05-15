package odisee.be.vardo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CampussesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campusses);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoAdapter(CampussesActivity.this));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        LatLng brussels = new LatLng(50.850346, 4.351721);

        // Aalst
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.931949, 4.021755))
                .title("Campus Aalst")
                .snippet("Kwalestraat 154, 9320 Aalst" + "\n" + "T. 053-72 71 70" + "\n" + "info.aalst@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.931468, 4.022563))
                .title("Campus Aalst")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Brussel
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.849016, 4.356508))
                .title("Campus Brussel")
                .snippet("Warmoesberg 26, 1000 Brussel" + "\n" + "T. 02-210 12 11" + "\n" + "info@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.849719, 4.356408))
                .title("Campus Brussel")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Brussel
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.853315, 4.358557))
                .title("Campus Brussel")
                .snippet("Blekerijstraat 23-29, 1000 Brussel" + "\n" + "T. 02-608 14 44" + "\n" + "info@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.853341, 4.358686))
                .title("Campus Brussel")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Brussel
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.874961, 4.385359))
                .title("Campus Brussel")
                .snippet("Huart Hamoirlaan 136, 1030 Brussel" + "\n" + "T. 02-240 68 40-44" + "\n" + "info.hig@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.874957, 4.385293))
                .title("Campus Brussel")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Dilbeek
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.866387, 4.245164))
                .title("Campus Dilbeek")
                .snippet("Stationsstraat 301, 1700 Dilbeek" + "\n" + "T. 02-466 51 51" + "\n" + "spp@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(50.866319, 4.244965))
                .title("Campus Dilbeek")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Gent
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.061153, 3.708705))
                .title("Campus Gent")
                .snippet("Gebr. De Smetstraat 1, 9000 Gent" + "\n" + "T. 09-265 86 10" + "\n" + "info.gent@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.061541, 3.708791))
                .title("Campus Gent")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        // Sint-Niklaas
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.161382, 4.151223))
                .title("Campus Sint-Niklaas")
                .snippet("Hospitaalstraat 23, 9100 Sint-Niklaas" + "\n" + "T. 03-776 43 48" + "\n" + "info.waas@odisee.be")
                .zIndex(100)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(51.161209, 4.150675))
                .title("Campus Sint-Niklaas")
                .snippet("Parking")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(brussels));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }
}
