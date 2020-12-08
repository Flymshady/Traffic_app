package cz.cellar.traffic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {



    GoogleMap mMap;
    Marker mBarton_vysokov;
    Marker mPolska;
    Marker mBarton_centrum;
    Marker mBarton;
    Marker mSlavia;
    Marker mItalie;
    String textResult="";



    int mId;

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
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                Context context = getApplicationContext();
                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(context);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        LatLng barton_vysokov = new LatLng(50.40682739619972, 16.15348811129043);
        LatLng barton_centrum = new LatLng(50.40700629102843, 16.15431743366031);
        LatLng barton = new LatLng(50.40921668375837, 16.159601411521997);
        LatLng slavia = new LatLng(50.4094390855892, 16.159912455238747);
        LatLng italie = new LatLng(50.417247547372234, 16.16878373356698);
        LatLng polska = new LatLng(50.418361035996014, 16.178165471253525);
        LatLng centerPos = new LatLng(50.413239005850215, 16.165829679135452);
        float zoom = (float) 14.2;

        mBarton_vysokov =  mMap.addMarker(new MarkerOptions()
                .position(barton_vysokov)
                .title(textResult)
                .title("Bartoň, směr Vysokov")
                .snippet("neaktualizováno")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );

        mBarton_centrum = mMap.addMarker(new MarkerOptions()
                .position(barton_centrum)
                .title("Bartoň, směr centrum")
                .snippet("neaktualizováno")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );

        mBarton = mMap.addMarker(new MarkerOptions()
                .position(barton)
                .title("Pražská ul., směr Vysokov")
                .snippet("neaktualizováno")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        );
        mPolska = mMap.addMarker(new MarkerOptions()
                .position(polska)
                .title("Polská ul.")
                .snippet("neaktualizováno")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mSlavia = mMap.addMarker(new MarkerOptions()
                .position(slavia)
                .title("Pražská ul, směr Slavie")
                .snippet("neaktualizováno")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mItalie = mMap.addMarker(new MarkerOptions()
                .position(italie)
                .title("Kruhový objezd u Itálie")
                .snippet("neaktualizováno")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));


         mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerPos, zoom));
         mMap.getUiSettings().setZoomControlsEnabled(true);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        System.out.println(textResult+"start--------------------------");

        if(marker.getTitle().equalsIgnoreCase("Bartoň, směr Vysokov")){
            mId=0;
        }
        else if (marker.getTitle().equalsIgnoreCase("Bartoň, směr centrum")){
            mId=1;
        }
        else if(marker.getTitle().equalsIgnoreCase("Pražská ul., směr Vysokov")){
            mId=2;
        }
        else if(marker.getTitle().equalsIgnoreCase("Pražská ul, směr Slavie")){
            mId=3;
        }
        else if(marker.getTitle().equalsIgnoreCase("Polská ul.")){
            mId=5;
        }
        else if(marker.getTitle().equalsIgnoreCase("Kruhový objezd u Itálie")) {
            mId=4;
        }

        String urlBasic="https://ec608c840b83.ngrok.io/";
        String mUrl=urlBasic+"predict/"+mId+"/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mUrl).addConverterFactory(GsonConverterFactory.create()).build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();


        call.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {


                if (!response.isSuccessful()){
                    textResult = String.valueOf(response.code());
                }

                List<Post> posts = response.body();
                if(posts==null){
                    textResult=("Chyba při načítání - null ");
                    //return;
                }
                if(posts.isEmpty()){
                    textResult=("Chyba při načítání- empty");
                    //return;
                }

                for (Post post: posts){
                    String content = "";
                    content+=post.getPred()+"\n";
                    textResult=content;

                }

                if(textResult!=null){

                    if(textResult.contains("no_traffic")){
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        marker.setSnippet("Bez dopravní zácpy");
                    }
                    else if(textResult.contains("error")){
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        marker.setSnippet("Chybí informace o situaci");
                    }
                    else if(textResult.contains("traffic") && textResult.length()<=8) {
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        marker.setSnippet("Dopravní zácpa");
                    }
                    else{
                        marker.setSnippet("Nastala chyba při načítání");
                        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    }
                }else{
                    marker.setSnippet("Chyba při načítání");
                    marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
                for(int i=0;i<20;i++) {
                    System.out.println(textResult+"------------------------------------------------"+i);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textResult=t.getMessage();
            }
        });


       return false;
    }
}
