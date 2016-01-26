package com.example.agviajandroid_1;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import android.util.Log;


public class General {
	
	public float[][] matrizCosto;
	//public Nodo[] mapa;
	public Hashtable<Integer, Nodo> mapa = new Hashtable<Integer,Nodo>();
	public List<String> agendaExp;
	
	public General(int tam){
		matrizCosto = new float[tam][tam];
		//mapa = new Nodo[tam];
		agendaExp = new ArrayList<String>(); //guarda indice de nodo visitado
		
	}

	public void llenarMatriz(){
		Random ran = new Random();
		int costoRand;
		for(int i=0;i<matrizCosto.length;i++){
			costoRand = ran.nextInt(6) + 5;
			//mapa[i] = new Nodo("Nodo "+i); //llenado estandar de nombres de Nodo en el mapa
			mapa.put(i, new Nodo("Nodo "+i));
			for(int j=0;j<matrizCosto[0].length;j++){
				if(i==j)
					matrizCosto[i][j]=0;
				else{
					matrizCosto[j][i]=costoRand;
					matrizCosto[i][j]=costoRand;
					
				}
			}
		}
	}
	
	public void imprimeMatriz(){
		for(int i=0;i<mapa.size()/*.length*/;i++){
			System.out.println("-- "+mapa.get(i).getNombre());
			for(int j=0;j<mapa.size()/*.length*/;j++){
				//System.out.println(" ** con "+mapa.get(j).getNombre()+" costo = "+matrizCosto[i][j]);
				Log.d("Matriz", " ** con "+mapa.get(j).getNombre()+" costo = "+matrizCosto[i][j]);
			}
		}
	}
	
	public Nodo buscarCercano(String id){
		//busca el indice del nodo en la matriz de costos, por su id
		int indice=0;
		for(int i=0;i<=mapa.size()/*.length*/;i++){
			if(mapa.get(i).getNombre().equals(id)){ //si el id dado, es igual al de la iteracion
				indice =i;
				break;
			}
		}
		//System.out.println("   ** el indice del nodo es "+indice);
		
		Nodo cercano = new Nodo("");
		float costoMin=0; 
		String ide = null;
		for(int i=0;i<matrizCosto.length;i++){
			//System.out.println("**"+i);
			if(!revisaAgendaExp(mapa.get(i).getNombre())){ //valida si el nodo esta en la lista de nodos expandidos
				if(indice!=i){//valida si se trata del mismo nodo (que en la matriz tiene costo min 0)
					//System.out.println("  --analizando contra costo minimo de"+ costoMin+" contra costo "+matrizCosto[indice][i]);
					if(costoMin>matrizCosto[indice][i]||costoMin==0){
						//System.out.println("costo minimo es "+costoMin+".. costo de indice analizado: "+matrizCosto[indice][i]+".. se cambia costo");
						costoMin = matrizCosto[indice][i];
						ide = mapa.get(i).getNombre();
					}
				}
				else{
					//System.out.println("comparando con mismo nodo (costo = 0 )");
				}
			}
		}
		cercano.setCosto(costoMin); //se agrega como dato al nodo el costo
		cercano.setNombre(ide);
		//System.out.println("   ** El ide del nodo mas cercano es "+ide);
		return cercano;
	}
	
	public boolean revisaAgendaExp(String nombre){ //revisa si el nodo ya existe, true si existe, false si no existe
		//en la Agenda de nodos expandidos
		//System.out.println(" revisaAgendaExp()..");
		for(int i=0;i<agendaExp.size();i++){
			//System.out.println("[["+agendaExp.get(i)+" .. [["+nombre);
			if(agendaExp.get(i).equals(nombre)){
				//System.out.println("existe en lista de expandidos");
				return true;
				
			}
		}
		//System.out.println("no existe en lista de expandidos");
		return false;
	}
	
	public void agregarAgendaExp(String nombre){
		agendaExp.add(nombre);
	}
	
	public void imprimeListaExp(){
		System.out.println("--expandidos");
		for(int i=0;i<agendaExp.size();i++){
			//System.out.println(" expandido"+agendaExp.get(i));
			Log.d("listaExpandidos", " expandido"+agendaExp.get(i));
		}
	}

}
