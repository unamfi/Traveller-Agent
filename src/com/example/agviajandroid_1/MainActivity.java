package com.example.agviajandroid_1;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity{
	
	private GoogleMap mMap;
	private final LatLng locCliente = new LatLng(19.412065,-99.180596);
	Button bmatrix,bCalc,bFirst;
	
	//Algoritmo Ag-Viaj
	int numNodos;
	General gen;
	int costoTotal;
	Nodo nodoSig;
	int nodoInicial;
	
	//Mapa
	GoogleMap map;
    ArrayList<LatLng> markerPoints;
    double mLatitude=0;
    double mLongitude=0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
 
        //if(map!=null){
 
            // Enable MyLocation Button in the Map
            //map.setMyLocationEnabled(true);
 
            // Setting onclick event listener for the map
            /*map.setOnMapClickListener(new OnMapClickListener() {
 
                @Override
                public void onMapClick(LatLng point) {
 
                    // Already two locations
                    if(markerPoints.size()>1){
                        markerPoints.clear();
                        map.clear();
                    }
 
                    // Adding new item to the ArrayList
                    markerPoints.add(point);
 
                    // Creating MarkerOptions
                    MarkerOptions options = new MarkerOptions();
 
                    // Setting the position of the marker
                    options.position(point);
 
                    /**
                    * For the start location, the color of marker is GREEN and
                    * for the end location, the color of marker is RED.
                    */
            /*
                    if(markerPoints.size()==1){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    }else if(markerPoints.size()==2){
                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                    }
 
                    // Add new marker to the Google Map Android API V2
                    map.addMarker(options);
 
                    // Checks, whether start and end locations are captured
                    if(markerPoints.size() >= 2){
                        LatLng origin = markerPoints.get(0);
                        LatLng dest = markerPoints.get(1);
 
                        // Getting URL to the Google Directions API
                        String url = getDirectionsUrl(origin, dest);
 
                        DownloadTask downloadTask = new DownloadTask();
 
                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }
                }
            });*/
           
         /*   
         // Getting URL to the Google Directions API
            String url = getDirectionsUrl(markerPoints.get(1), markerPoints.get(8));

            DownloadTask downloadTask = new DownloadTask();

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);
            
          */  
        //}
        
        inicializaMapa();
        inicializaAlgoritmo();
		
		// CIERRE dibuja ruta
		
		
		//botones activities
		bFirst = (Button) findViewById(R.id.buttonFirst);
		bFirst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getApplicationContext(), FirstActivity.class);
				//startActivity(i);
				startActivityForResult(i,1);
				bmatrix.setEnabled(true);
				bCalc.setEnabled(true);
			}
		});
		
		bmatrix = (Button) findViewById(R.id.buttonMatrix);
		bmatrix.setEnabled(false);
		bmatrix.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bundle b=new Bundle();
				for(int i=0;i<gen.matrizCosto.length;i++){
					b.putFloatArray("m"+i, gen.matrizCosto[i]);
				}
				Intent i = new Intent(getApplicationContext(), MatrixActivity.class);
				i.putExtras(b);
				startActivity(i);
			}
		});
		
		bCalc = (Button) findViewById(R.id.buttonCalc);
		bCalc.setEnabled(false);
		bCalc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				map.clear();
				inicializaMapa();
				inicializaAlgoritmo();
				
				gen.imprimeMatriz();
				
				Nodo nodoSig = new Nodo(""+nodoInicial); //igual a nodo inicial
				gen.agregarAgendaExp(nodoSig.getNombre()); //se agrega el nodo inicial a la lista de expandidos
				
				int f=0;
				while(gen.agendaExp.size()!=numNodos){
					//System.out.println(" !!!!!!!!!!!!!!!!!!!! iteracion "+f);
					//gen.imprimeListaExp();
					nodoSig = gen.buscarCercano(nodoSig.getNombre());
					gen.agregarAgendaExp(nodoSig.getNombre());
					costoTotal += nodoSig.getCosto();
					f++;
				}
				
				//Log.d("****", "tam: "+gen.mapa.size());
				
				//Toast.makeText(getApplicationContext(), "pff", Toast.LENGTH_LONG).show();
				
				
				//mostrnado rutas en mapa
				
				String url;
				DownloadTask downloadTask;
				
				for(int i=0;i<gen.agendaExp.size()-1;i++){
					url = getDirectionsUrl(markerPoints.get(Integer.valueOf(gen.agendaExp.get(i))), markerPoints.get(Integer.valueOf(gen.agendaExp.get(i+1))));
					downloadTask = new DownloadTask();
					downloadTask.execute(url);
				}
				
				//String url = getDirectionsUrl(markerPoints.get(1), markerPoints.get(8));

	            //DownloadTask downloadTask = new DownloadTask();

	            // Start downloading json data from Google Directions API
	            
			}
		});
	
	}
	
	//METODOS DIBUJA RUTA
	
	private String getDirectionsUrl(LatLng origin,LatLng dest){
		 
        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
 
        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
 
        // Sensor enabled
        String sensor = "sensor=false";
 
        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;
 
        // Output format
        String output = "json";
 
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
 
        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
 
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
 
            // Connecting to url
            urlConnection.connect();
 
            // Reading data from url
            iStream = urlConnection.getInputStream();
 
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
 
            StringBuffer sb = new StringBuffer();
 
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
 
            data = sb.toString();
 
            br.close();
 
        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    
    
 // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{
 
    	ProgressDialog dialog;
    	
        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {
 
            // For storing data from web service
            String data = "";
 
            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }
 
        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            
            dialog.dismiss();
 
            ParserTask parserTask = new ParserTask();
 
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
        
        
        @Override
        protected void onPreExecute() {
        	// TODO Auto-generated method stub
        	super.onPreExecute();

			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Calculando ruta");
			dialog.show();
        }
    }
 
    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{
 
    	
    	
        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
 
            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
 
            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
 
                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }
        
       
 
        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        	
        	
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
 
            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
 
                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);
 
                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);
 
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);
 
                    points.add(position);
                }
 
                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.BLUE);
            }
 
            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }
	
	//CIERRE METODOS DIBUJA RUTA
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
	    case (1) : {
	      if (resultCode == Activity.RESULT_OK) {
	        // TODO Extract the data returned from the child Activity.
	    	  Toast.makeText(getApplicationContext(), ".. "+data.getIntExtra("nodo", 0), Toast.LENGTH_SHORT).show();
	    	  nodoInicial = (data.getIntExtra("nodo", 0))-1;
	      }
	      break;
	    } 
	  }
		
	}
	
	
	private void inicializaAlgoritmo(){
        //Lista de latitud y longitud de preparatorias UNAM
        
        /*
         * Prepa1: 19.271362956590064 ,  -99.12188379999998
         * Prepa2: 19.397954,-99.098847
         * Prepa3: 19.489897,-99.094727
         * Prepa4: 19.404147,-99.194748
         * Prepa5: 19.321511,-99.133866
         * Prepa6: 19.356498,-99.157898
         * Prepa7: 19.420135,-99.126747
         * Prepa8: 19.367329,-99.195303
         * Prepa9: 19.483909,-99.127972
         * */
        
        //inicializando estructuras de algoritmo
        numNodos=9;
        gen = new General(numNodos);
        
        //agregando marcadores a mapa de tabla
        for(int i=0;i<markerPoints.size();i++){
        	gen.mapa.put(i, new Nodo(""+i));
        }
        
        
        //llenado de matriz de costos por distancia
        
        float[] arr = new float[3];
        //Location.distanceBetween(19.489897, -99.094727, 19.48390, -99.127972, arr);
        
        for(int i=0;i<gen.matrizCosto.length;i++){
			//costoRand = ran.nextInt(6) + 5;
			//mapa[i] = new Nodo("Nodo "+i); //llenado estandar de nombres de Nodo en el mapa
			//mapa.put(i, new Nodo("Nodo "+i));
        	Log.d("*"+i, "preparatoria "+(i+1));
			for(int j=0;j<gen.matrizCosto[0].length;j++){
				
				if(i==j)
					gen.matrizCosto[i][j]=0;
				else{
					//gen.matrizCosto[j][i]=costoRand;
					Location.distanceBetween(markerPoints.get(i).latitude,markerPoints.get(i).longitude, markerPoints.get(j).latitude, markerPoints.get(j).longitude, arr);
					gen.matrizCosto[i][j]=arr[0];
				}
				
				Log.d("*"+j, "distancia con prepa "+(j+1)+": "+gen.matrizCosto[i][j]);
			}
		}
        
        
	}
	
	private void inicializaMapa(){
		
		// Initializing
        markerPoints = new ArrayList<LatLng>();
 
        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapa);
 
        // Getting Map for the SupportMapFragment
        map = fm.getMap();

        //posiciones de marcadores
        markerPoints.add(new LatLng(19.271362956590064, -99.12188379999998));
        markerPoints.add(new LatLng(19.397954, -99.098847));
        markerPoints.add(new LatLng(19.489897, -99.094727));
        markerPoints.add(new LatLng(19.404147, -99.194748));
        markerPoints.add(new LatLng(19.321511, -99.133866));
        markerPoints.add(new LatLng(19.356498, -99.157898));
        markerPoints.add(new LatLng(19.420135, -99.126747));
        markerPoints.add(new LatLng(19.367329, -99.195303));
        markerPoints.add(new LatLng(19.48390, -99.127972));
        
        MarkerOptions mo = new MarkerOptions();
        //agregando marcadores a mapa grafico
        for(int i=0;i<markerPoints.size();i++){
        	mo.position(markerPoints.get(i));
        	mo.title("Preparatoria "+(i+1));
        	map.addMarker(mo);
        }
        
      //centrando mapa
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.4326058056025, -99.13325399999997), 11.0f));
	}
	
	

}
