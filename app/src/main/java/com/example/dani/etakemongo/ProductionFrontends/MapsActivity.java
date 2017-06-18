package com.example.dani.etakemongo.ProductionFrontends;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.dani.etakemongo.DevelopFrontends.Menu;
import com.example.dani.etakemongo.Modelo.Captura;
import com.example.dani.etakemongo.Modelo.Localizacion;
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
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.dani.etakemongo.R.id.imageView;
import static com.example.dani.etakemongo.R.id.map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    String tag = "MapsActivity";
    private GoogleMap mMap;
    private Marker marcador;
    private Circle mCircle;
    private Marker loc1, loc2, loc3, loc4, loc5, loc6, loc7, loc8, loc9, loc10, loc11, loc12, loc13, loc14, loc15, loc16, loc17, loc18, loc19, loc20;
    private List<Marker> markerList = new ArrayList<>();
    private GoogleApiClient mGoogleApiClient;
    double lat = 0.0;
    double ing = 0.0;
    String email2, emailaMenu;
    int idusuario, idusuarioaMenu;

    private List<Captura> listarecibida;
    private List<Captura> capturaList;

    private List<Localizacion> listarecibidaloca;
    private List<Localizacion> localizacionList;

    private List<Captura> capturaspawn;
    private List<Localizacion> locaspawn;



    FloatingActionButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(tag, "Event onCreate()");
        markerList.add(loc1);
        markerList.add(loc2);
        markerList.add(loc3);
        markerList.add(loc4);
        markerList.add(loc5);
        markerList.add(loc6);
        markerList.add(loc7);
        markerList.add(loc8);
        markerList.add(loc9);
        markerList.add(loc10);
        markerList.add(loc11);
        markerList.add(loc12);
        markerList.add(loc13);
        markerList.add(loc14);
        markerList.add(loc15);
        markerList.add(loc16);
        markerList.add(loc17);
        markerList.add(loc18);
        markerList.add(loc19);
        markerList.add(loc20);

        email2 = getIntent().getExtras().getString("email");
        emailaMenu = email2;


        recuperarLocalizaciones(); //rellenamos lista recibida loca
        recuperarCapturas();

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

       // setLocationMarkers();
        //spawns();
    }


//    private void updateMarkerWithCircle(LatLng location) {
//        mCircle.setCenter(location);
//        mMarker.setPosition(location);
//    }


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


private List<Captura> recuperarCapturas() {
    //RETROFIT
    RetrofitOwn retrofitOwn = new RetrofitOwn();
    Retrofit retrofit = retrofitOwn.getObjectRetrofit();

    //Creamos una instancia de retrofit
    GitHubClient getListaCapturasGeneradas = retrofit.create(GitHubClient.class);


    //Hacemos la llamada http
    Call<List<Captura>> call = getListaCapturasGeneradas.getRandomCapturas();

    call.enqueue(new Callback<List<Captura>>() {
        @Override
        public void onResponse(Call<List<Captura>> call, Response<List<Captura>> response) {
            if (response.isSuccessful()){
                ImageView imageview = new ImageView(MapsActivity.this);
                Toast.makeText(MapsActivity.this, "response successful", Toast.LENGTH_LONG).show();
                listarecibida = response.body();
                System.out.println("OJIKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO k viene");
                capturaList = new ArrayList<Captura>();
                for (int i=0; i< listarecibida.size(); i++){
                    capturaList.add(listarecibida.get(i));
                    System.out.println("ID ETAKEMON:   "+capturaList.get(i).getIdetakemon());
                    System.out.println("ID LOCALIZACION:    "+capturaList.get(i).getIdlocalizacion());
                }
                for (int j=0; j< capturaList.size(); j++){
                    final String nombre = capturaList.get(j).getNombreetakemon();
                    if (capturaList.get(j).getIdlocalizacion() == 1){
                        final Marker[] marker = {markerList.get(j)};

                        final LatLng posicionSpwan = new LatLng(localizacionList.get(1).getLatitud(), localizacionList.get(1).getLongitud());
                        if (marker[0] != null){
                            marker[0].remove();
                        }


                        Glide.with(MapsActivity.this).load(capturaList.get(j).getImagen()).asBitmap().override(120,120).into(new BitmapImageViewTarget(imageview){
                            @Override
                            protected void setResource(Bitmap resource){
                                marker[0] = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                        .title(nombre)
                                .icon(BitmapDescriptorFactory.fromBitmap(resource)));

                            }
                        });



//                            marker = mMap.addMarker(new MarkerOptions()
//                                    .position(posicionSpwan)
//                                    .title(""+capturaList.get(j).getNombreetakemon()));
//                                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));


                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 2){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(2).getLatitud(), localizacionList.get(2).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 3){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(3).getLatitud(), localizacionList.get(3).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 4){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(4).getLatitud(), localizacionList.get(4).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 5){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(5).getLatitud(), localizacionList.get(5).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 6){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(6).getLatitud(), localizacionList.get(6).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 7){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(1).getLatitud(), localizacionList.get(1).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 8){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(1).getLatitud(), localizacionList.get(1).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 9){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(9).getLatitud(), localizacionList.get(9).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 10){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(10).getLatitud(), localizacionList.get(10).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 11){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(11).getLatitud(), localizacionList.get(11).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 12){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(12).getLatitud(), localizacionList.get(12).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 13){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(13).getLatitud(), localizacionList.get(13).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 14){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(14).getLatitud(), localizacionList.get(14).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 15){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(15).getLatitud(), localizacionList.get(15).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 16){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(16).getLatitud(), localizacionList.get(16).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 17){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(17).getLatitud(), localizacionList.get(17).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 18){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(18).getLatitud(), localizacionList.get(18).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() ==19){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(19).getLatitud(), localizacionList.get(19).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 20){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(20).getLatitud(), localizacionList.get(20).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 21){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(21).getLatitud(), localizacionList.get(21).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 22){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(22).getLatitud(), localizacionList.get(22).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 23){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(23).getLatitud(), localizacionList.get(23).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 24){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(24).getLatitud(), localizacionList.get(24).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 25){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(25).getLatitud(), localizacionList.get(25).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 26){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(26).getLatitud(), localizacionList.get(26).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 27){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(27).getLatitud(), localizacionList.get(27).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 28){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(28).getLatitud(), localizacionList.get(28).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 29){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(29).getLatitud(), localizacionList.get(29).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
                    else if(capturaList.get(j).getIdlocalizacion() == 30){
                        Marker marker = markerList.get(j);
                        LatLng posicionSpwan = new LatLng(localizacionList.get(30).getLatitud(), localizacionList.get(30).getLongitud());
                        if (marker != null){
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions()
                                .position(posicionSpwan)
                                .title(""+capturaList.get(j).getNombreetakemon())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pokeballicon)));

                    }
         }
            }
            else {
                Toast.makeText(MapsActivity.this, "response unsuccessful", Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Call<List<Captura>> call, Throwable t) {
            Toast.makeText(MapsActivity.this, "No hay conexión", Toast.LENGTH_SHORT).show();
            Log.d(tag, "ERROR en el recogido de capturas generadas para mostrar en mapa");
        }
    });

        return listarecibida;
    }



    private void recuperarLocalizaciones(){

        //RETROFIT
        RetrofitOwn retrofitOwn = new RetrofitOwn();
        Retrofit retrofit = retrofitOwn.getObjectRetrofit();

        //Creamos una instancia de retrofit
        GitHubClient getListaLocalizaciones = retrofit.create(GitHubClient.class);


        //Hacemos la llamada http
        Call<List<Localizacion>> call = getListaLocalizaciones.getLocalizaciones();

        call.enqueue(new Callback<List<Localizacion>>() {

            @Override
            public void onResponse(Call<List<Localizacion>> call, Response<List<Localizacion>> response) {

                if (response.isSuccessful()){
                    Toast.makeText(MapsActivity.this, "response successful", Toast.LENGTH_LONG).show();
                    listarecibidaloca = response.body();
                    System.out.println("OJIKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO k viene");
                    System.out.println(listarecibidaloca.get(1).getLatitud());
                    localizacionList = new ArrayList<Localizacion>();
                    for (int i=0; i<listarecibidaloca.size(); i++){
                        localizacionList.add(listarecibidaloca.get(i));
//                        System.out.println("TAMAÑOOOOOO loco: "+localizacionList.size());
//                        System.out.println("ID: loco    "+localizacionList.get(i).getId());
//                        System.out.println("latitud: "+ localizacionList.get(i).getLatitud());
//                        System.out.println("longitud: " + localizacionList.get(i).getLongitud());
//
//                        Marker marker = markerList.get(i);
//                        LatLng posicionSpwan = new LatLng(localizacionList.get(i).getLatitud(), localizacionList.get(i).getLongitud());
//                        // CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(posicionSpwan, 16);
//                        if (marker != null){
//                            marker.remove();
//                        }
//                        marker = mMap.addMarker(new MarkerOptions()
//                                .position(posicionSpwan)
//                                .title("Captura")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.player8bits)));
                        //mMap.animateCamera(miUbicacion);
                    }
                  //  listarecibidaloca = response.body();
//                    localizacionList = new ArrayList<Localizacion>();
//                    for (int j=0; j < locas.size(); j++){
//                        Localizacion localizacion = locas.get(j);
//                        localizacionList.add(localizacion);
                    }
                else {
                    System.out.println("¡UNSUCESFUL");
                    Toast.makeText(MapsActivity.this, "response unsuccessful All objetos", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<Localizacion>> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "No hay conexión", Toast.LENGTH_SHORT).show();
                Log.d(tag, "ERROR en el recogido de localizaciones asociadas a las capturas generadas para mostrar en mapa");
            }
        });

    }

//    Marker marker;
//
//    private void setLocationMarkers(){
//
//
//
//    }

//    public void spawns(){
//        capturaspawn = recuperarCapturas();
//        //locaspawn = recuperarLocalizaciones();
//
//        double latitud, longitud;
//        int idloca;
//
//        for (int x=0; x<capturaspawn.size(); x++){
//            idloca = capturaspawn.get(x).getIdlocalizacion();
//            for (int z=0; z<locaspawn.size(); z++){
//                if (idloca == locaspawn.get(z).getId()){
//                    latitud = locaspawn.get(z).getLatitud();
//                    longitud = locaspawn.get(z).getLongitud();
//
//                        LatLng posicionSpwan = new LatLng(latitud, longitud);
//                       // CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(posicionSpwan, 16);
//                        if (marcador != null){
//                            marcador.remove();
//                        }
//                        marcador = mMap.addMarker(new MarkerOptions()
//                                .position(posicionSpwan)
//                                .title("Captura")
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_etakemons)));
//                        //mMap.animateCamera(miUbicacion);
//                        System.out.println("************************MIRAR AQUI************"+ lat+ing);
//                }
//            }
//
//        }
//    }


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


    public void goToMenu(View view){
        Intent intent = new Intent(MapsActivity.this, Menu.class);
        intent.putExtra("email2",emailaMenu);
        startActivityForResult(intent, 800);
    }

}
