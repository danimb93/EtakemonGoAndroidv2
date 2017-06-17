package com.example.dani.etakemongo.ProductionFrontends;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.dani.etakemongo.Modelo.Etakemon;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

import static com.example.dani.etakemongo.R.id.map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    String tag = "MapsActivity";
    private GoogleMap mMap;
    private Marker marcador;
    private Circle mCircle;
    private Marker mMarker;
    private GoogleApiClient mGoogleApiClient;
    double lat = 0.0;
    double ing = 0.0;
   // String email2, emailaMenu;    ***********COMENTADO PARA HACER PRUEBAS CON MAPS******
    int idusuario, idusuarioaMenu;


    FloatingActionButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(tag, "Event onCreate()");

/*
        email2 = getIntent().getExtras().getString("email");
        emailaMenu = email2;


        //BOTON MENU
        menu = (FloatingActionButton) findViewById(R.id.fab_menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goToMenu(v);
                } catch (Exception ex) {
                    String error = ex.getMessage();
                    Toast.makeText(MapsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

*/

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
    //************************    mMap.setMyLocationEnabled(true);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();
        System.out.println("************************MIRAR AQUI************" + lat + ing);
        // ... get a map.
        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(lat, ing))
                .radius(125)
                .strokeColor(Color.RED)
                .fillColor(Color.TRANSPARENT));
    }


    private void updateMarkerWithCircle(LatLng location) {
        mCircle.setCenter(location);
        mMarker.setPosition(location);
    }





    //Metodo para incluir un marker, CameraUpdate para centrar la camara a la posicion del marker
    private void agregarMarcador(double lat, double ing) {

        LatLng coordenadas = new LatLng(lat, ing);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null){
            marcador.remove();
        }
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi posicion")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.player8bits)));
        mMap.animateCamera(miUbicacion);
        System.out.println("************************MIRAR AQUI************"+ lat+ing);
    }

    //Metodo para obtener latitud y longitud de nuestra posicion actual
    private void actualizarUbicacion(Location location) {
        if (location != null) {  //Comrpobamos la localizacion recibida es diferente de null antes de asignar valores a las valariables
            lat = location.getLatitude();
            ing = location.getLongitude();
            agregarMarcador(lat, ing);
            System.out.println("************************MIRAR AQUI************"+ lat+ing);
        }
    }

    //Implementamos un objeto del tipo LocationListener, su funcion es estar atento a cambio de localidad recividio por el GPS
    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);//Llamamos anuestro metodo para actualizar la ubicacion
            System.out.println("************************MIRAR AQUI************"+ lat+ing);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    //Metodo para obtener servicio de posicionamiento, nos da la ultima posicion obtenida y se actualiza cada 15 segundos

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
      //  calculateAreaOfGPSPolygonOnEarthInSquareMeters(location,2.5);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,4000,0,locListener);
        System.out.println("************************MIRAR AQUI************"+ lat+ing);
    }
private void recuperarEtakemons() {
    //RETROFIT
    RetrofitOwn retrofitOwn = new RetrofitOwn();
    Retrofit retrofit = retrofitOwn.getObjectRetrofit();

    //Creamos una instancia de retrofit
    GitHubClient getListaEtakemons = retrofit.create(GitHubClient.class);


    //Hacemos la llamada http
    Call<List<Etakemon>> call = getListaEtakemons.getListaEtakemons();
    System.out.println("***********DATOS**************************");


    //Recibimos la llamada
   //call.enqueue(new Callback() {
    }



    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "Event onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "Event onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "Event onPause()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "Event onStop()");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag, "Event onRestart()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "Event onDestroy()");

    }
/*

    public void goToMenu(View view){
        Intent intent = new Intent(MapsActivity.this, Menu.class);
        intent.putExtra("email2",emailaMenu);
        startActivityForResult(intent, 800);
    }
    */
}
