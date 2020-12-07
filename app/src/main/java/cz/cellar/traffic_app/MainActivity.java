package cz.cellar.traffic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //callback pro zavolani onready po nacteni
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng barton_vysokov = new LatLng(50.40682739619972, 16.15348811129043);
        LatLng barton_centrum = new LatLng(50.40700629102843, 16.15431743366031);
        LatLng barton = new LatLng(50.40921668375837, 16.159601411521997);
        LatLng slavia = new LatLng(50.4094390855892, 16.159912455238747);
        LatLng italie = new LatLng(50.417247547372234, 16.16878373356698);
        LatLng polska = new LatLng(50.418361035996014, 16.178165471253525);
        LatLng centerPos = new LatLng(50.413239005850215, 16.165829679135452);
        float zoom = (float) 14.2;

        mMap.addMarker(new MarkerOptions()
                .position(barton_vysokov)
                .title("Bartoň, směr Vysokov")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );

        mMap.addMarker(new MarkerOptions()
                .position(barton_centrum)
                .title("Bartoň, směr centrum")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );

        mMap.addMarker(new MarkerOptions()
                .position(barton)
                .title("Pražská ul., směr Vysokov")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );

        mMap.addMarker(new MarkerOptions()
                .position(slavia)
                .title("Pražská ul, směr Slavie")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.addMarker(new MarkerOptions()
                .position(italie)
                .title("Kruhový objezd u Itálie")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.addMarker(new MarkerOptions()
                .position(polska)
                .title("Polská ul.")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPos, zoom));
         mMap.getUiSettings().setZoomControlsEnabled(true);

    }
}
