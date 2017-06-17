package com.example.dani.etakemongo.ProductionFrontends;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    String tag = "MapsActivity";
    private GoogleMap mMap;
    private Marker marcador;
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
                .findFragmentById(R.id.map);
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
        System.out.println("************************MIRAR AQUI************"+ lat+ing);

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

    private static final double EARTH_RADIUS = 6371000;// meters

    public static double calculateAreaOfGPSPolygonOnEarthInSquareMeters(final List<Location> locations) {
        return calculateAreaOfGPSPolygonOnSphereInSquareMeters(locations, EARTH_RADIUS);
    }

    private static double calculateAreaOfGPSPolygonOnSphereInSquareMeters(final List<Location> locations, final double radius) {
        if (locations.size() < 3) {
            return 0;
        }

        final double diameter = radius * 2;
        final double circumference = diameter * Math.PI;
        final List<Double> listY = new ArrayList<Double>();
        final List<Double> listX = new ArrayList<Double>();
        final List<Double> listArea = new ArrayList<Double>();
        // calculate segment x and y in degrees for each point
        final double latitudeRef = locations.get(0).getLatitude();
        final double longitudeRef = locations.get(0).getLongitude();
        for (int i = 1; i < locations.size(); i++) {
            final double latitude = locations.get(i).getLatitude();
            final double longitude = locations.get(i).getLongitude();
            listY.add(calculateYSegment(latitudeRef, latitude, circumference));
            Log.d(LOG_TAG, String.format("Y %s: %s", listY.size() - 1, listY.get(listY.size() - 1)));
            listX.add(calculateXSegment(longitudeRef, longitude, latitude, circumference));
            Log.d(LOG_TAG, String.format("X %s: %s", listX.size() - 1, listX.get(listX.size() - 1)));
        }

        // calculate areas for each triangle segment
        for (int i = 1; i < listX.size(); i++) {
            final double x1 = listX.get(i - 1);
            final double y1 = listY.get(i - 1);
            final double x2 = listX.get(i);
            final double y2 = listY.get(i);
            listArea.add(calculateAreaInSquareMeters(x1, x2, y1, y2));
            Log.d(LOG_TAG, String.format("area %s: %s", listArea.size() - 1, listArea.get(listArea.size() - 1)));
        }

        // sum areas of all triangle segments
        double areasSum = 0;
        for (final Double area : listArea) {
            areasSum = areasSum + area;
        }

        // get abolute value of area, it can't be negative
        return Math.abs(areasSum);// Math.sqrt(areasSum * areasSum);
    }

    private static Double calculateAreaInSquareMeters(final double x1, final double x2, final double y1, final double y2) {
        return (y1 * x2 - x1 * y2) / 2;
    }

    private static double calculateYSegment(final double latitudeRef, final double latitude, final double circumference) {
        return (latitude - latitudeRef) * circumference / 360.0;
    }

    private static double calculateXSegment(final double longitudeRef, final double longitude, final double latitude,
    final double circumference) {
        return (longitude - longitudeRef) * circumference * Math.cos(Math.toRadians(latitude)) / 360.0;
    }

//recuperar listado pokemons


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
