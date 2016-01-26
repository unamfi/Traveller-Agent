package com.example.agviajandroid_1;

import java.text.DecimalFormat;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MatrixActivity extends Activity {
	
	TableLayout tabla;
	TableRow tr;
	TextView tvi;
	public float[][] matrizCosto =  new float[9][9];
	DecimalFormat df = new DecimalFormat("#####.#");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matrix);
		
		Bundle b=this.getIntent().getExtras();
		for(int i=0;i<9;i++){
			matrizCosto[i] = b.getFloatArray("m"+i);
		}
		
		/*
		int[][] matrizCosto = new int[10][10];
		//prueba de llenado
		Random ran = new Random();
		int costoRand;
		for(int i=0;i<matrizCosto.length;i++){
			costoRand = ran.nextInt(6) + 5;
			//mapa[i] = new Nodo("Nodo "+i); //llenado estandar de nombres de Nodo en el mapa
			//mapa.put(i, new Nodo("Nodo "+i));
			for(int j=0;j<matrizCosto[0].length;j++){
				if(i==j)
					matrizCosto[i][j]=0;
				else{
					matrizCosto[j][i]=costoRand;
					matrizCosto[i][j]=costoRand;
					
				}
			}
		}
		
		*/
		//prueba de llenado
		
		tabla = (TableLayout) findViewById(R.id.tabla);
		TableRow.LayoutParams paramsTr = new TableRow.LayoutParams(100, 60);
		
		for(int i=0;i<matrizCosto.length;i++){
			tr = new TableRow(getApplicationContext());
			//tr.setLayoutParams(paramsTr);
			for(int j=0;j<matrizCosto.length;j++){
				tvi = new TextView(getApplicationContext());
				tvi.setText(""+df.format(matrizCosto[i][j]));
				tr.addView(tvi,paramsTr);
			}
			tabla.addView(tr);
		}
		/*
		tr = new TableRow(getApplicationContext());
		tvi = new TextView(getApplicationContext());
		tvi.setText("prueba");
		tr.addView(tvi);
		
		tabla.addView(tr);*/
		
		
	}

	

}
