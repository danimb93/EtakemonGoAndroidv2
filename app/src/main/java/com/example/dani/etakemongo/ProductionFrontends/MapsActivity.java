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
import com.example.dani.etakemongo.Modelo.Usuario;
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

import java.io.Serializable;
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
    private GoogleApiClient mGoogleApiClient;
    double lat = 0.0;
    double ing = 0.0;
    String email2, emailaMenu;
    int idlogedmap, idrecibed;
    private List<Captura> listarecibida;
    private List<Captura> capturaList;
    private List<Localizacion> listarecibidaloca;
    private List<Localizacion> localizacionList;
    private FloatingActionButton menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d(tag, "Event onCreate()");

        email2 = getIntent().getExtras().getString("email");
        emailaMenu = email2;

        doGetData(emailaMenu);

        Toast.makeText(MapsActivity.this, "response unsuccessful", Toast.LENGTH_SHORT).show();

       // recuperarLocalizaciones(); //rellenamos lista recibida loca
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
//        Circle circle = mMap.addCircle(new CircleOptions()
//                .center(new LatLng(lat, ing))
//                .radius(125)
//                .strokeColor(Color.RED)
//                .fillColor(Color.TRANSPARENT));
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setScrollGesturesEnabled(false);
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
        marcador.setTag("user");
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
            if (response.isSuccessful()) {
                ImageView imageview = new ImageView(MapsActivity.this);
                listarecibida = response.body();
                capturaList = new ArrayList<Captura>();
                for (int i = 0; i < listarecibida.size(); i++) {
                    capturaList.add(listarecibida.get(i));
                }

                for (int i = 0; i<capturaList.size(); i++){
                        setLocationSpawn(capturaList.get(i),capturaList.get(i).getLatcaptura(),capturaList.get(i).getLoncaptura());
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

    Marker m1;

    public void setLocationSpawn(final Captura captura, final double lat, final double longi){

        ImageView imageview = new ImageView(MapsActivity.this);

        //{
        final LatLng loc = new LatLng(lat, longi);

        Glide.with(MapsActivity.this)
                .load(captura.getImagen())
                .asBitmap()
                .override(100, 100)
                .into(new BitmapImageViewTarget(imageview){
                    @Override
                    protected void setResource(Bitmap resource){
                        m1 = mMap.addMarker(new MarkerOptions()
                                .position(loc)
                                .icon(BitmapDescriptorFactory.fromBitmap(resource)));

                        m1.setTag(captura);

                        //m1.setTag(captura);
                        // m1.setTag(captura.getId()+"-"+captura.getNombreetakemon()+"-"+captura.getTipoetakemon());
                        m1.setTitle(captura.getNombreetakemon());

                    }
                });


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {


            @Override
            public boolean onMarkerClick(Marker arg0) {
               // Toast.makeText(MapsActivity.this, "clicado correctamente", Toast.LENGTH_LONG).show();

                    if (arg0.getTag().equals("user")){
                        Toast.makeText(MapsActivity.this, idlogedmap, Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    else {
                        //   Log.d(tag, "Marker "+ arg0.getTag());

                        //Toast.makeText(MapsActivity.this,arg0.getTag().toString(), Toast.LENGTH_LONG);
                        Log.d(tag, "Marker " + arg0.getTag().getClass().getSimpleName() + "Eetakemon seleccionada: " + arg0.getTitle());
//                     captura1 = (Captura) m1.getTag();
//                     Toast.makeText(MapsActivity.this, captura1.getNombreetakemon(), Toast.LENGTH_LONG);

                        Intent intent = new Intent(MapsActivity.this, game.class);
                        //Bundle bundle = new Bundle();

                        //   bundle.putString("nameExtra",arg0.getTitle());
                        //   bundle.putString("objetoExtra",arg0.getTag().toString());
                        // bundle.putString("list",captura);
                        // intent.putExtras(bundle);


//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("captura", (Serializable) arg0.getTag());
//                        bundle.putInt("userLoger", idlogedmap);

                        intent.putExtra("captura", (Serializable) arg0.getTag());
                        intent.putExtra("userLoger", idlogedmap);
                        startActivity(intent);
                        return false;
                    }
            }



        });


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

                if (response.isSuccessful()) {
                    Toast.makeText(MapsActivity.this, "response successful", Toast.LENGTH_LONG).show();
                    listarecibidaloca = response.body();
                    System.out.println("OJIKOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO k viene");
                    System.out.println(listarecibidaloca.get(1).getLatitud());
                    localizacionList = new ArrayList<Localizacion>();
                    for (int i = 0; i < listarecibidaloca.size(); i++) {
                        localizacionList.add(listarecibidaloca.get(i));
                    }
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

    public void doGetData(String em){
        RetrofitOwn retrofitOwn = new RetrofitOwn();
        Retrofit retrofit = retrofitOwn.getObjectRetrofit();

        //Creamos una instancia de retrofit
        GitHubClient datos = retrofit.create(GitHubClient.class);

        //Hacemos la llamada http
        Call<Usuario> call2 = datos.getUsuario(em);

        call2.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()){
                    Usuario usuario = new Usuario();
                    // idusuario = usuario.getId();
                    idrecibed = response.body().getId();
                    idlogedmap = idrecibed;
                    //   Toast.makeText(Menu.this, String.valueOf(idlogedparatodos), Toast.LENGTH_SHORT).show();
                    Log.d(tag, "Datos obtenidos correctamente");

                }
                else{
                    Log.d(tag, "Datos mal cogidos");
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                Log.d(tag, "No conectado para coger datos");
            }
        });
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

    public void goToMenu(View view){
        Intent intent = new Intent(MapsActivity.this, Menu.class);
        intent.putExtra("email2",emailaMenu);
        startActivityForResult(intent, 800);
    }

}
