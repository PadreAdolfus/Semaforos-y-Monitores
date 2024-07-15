package main;

import java.util.List;
import java.util.Random;

public class MainThread extends Thread{
	
	Buffer recursoCompartido; 
	
	int id;

	public MainThread(Buffer bf, int id) {

		recursoCompartido = bf;
		
	 	this.id = id;
	}

	public void run() {
		
		System.out.println ("Creado proceso " +id);
		
		while (true) {
			
			Random rdmNum = new Random();
			
			List <Integer> resultados;
			
			int aReservar = rdmNum.nextInt(10);
			
			int aLiberar = rdmNum.nextInt(10);
			
			try {
				sleep(rdmNum.nextInt(250 - 25 +1) + 25);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			resultados = recursoCompartido.reserva(aReservar, id);
			
			System.out.println("Estos son los recursos reservado por " +id + ":" +resultados.toString());
			
			try {
				sleep(rdmNum.nextInt(250 - 25 +1) + 25);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
			resultados = recursoCompartido.libera(aLiberar, id);
			
			
			if (resultados == null) {
				
				System.out.println(id + "No ha podido liberar" + aLiberar + "Recursos, porque no tenia tantos reservados");
			}
			
			else {
			
				System.out.println("Estos son los recursos liberados por " +id + ":" +resultados.toString());
			}
		}
	}
	
	
	

}
