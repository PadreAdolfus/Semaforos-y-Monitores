package main;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Buffer {

	public static final int BSIZE = 50;

	private static Semaphore SReserva = new Semaphore(1, true);
	
	private static Semaphore SLibera = new Semaphore(1, true);

	private static Queue<Integer> recurso = new LinkedList<Integer>();

	private static Map<Integer, List<Integer>> reservas = new HashMap<Integer, List<Integer>>();

	public static Queue<Integer> getRecurso() {

		return recurso;
	}

	public Buffer() {

		for (int i = 0; i < BSIZE; i++) {

			Random rdmNum = new Random();

			int numP = rdmNum.nextInt(999) + 1;

			recurso.add(numP);

		}

	}

	public List<Integer> reserva(int r, int id) {

		try {

			Buffer.getSReserva().acquire();
		}

		catch (InterruptedException e) {

			e.printStackTrace();
		}

		while (Buffer.getRecurso().size() - r < 0) {

			System.out.println("No quedan suficientes recursos esperando a que se liberen.");

			try {

				Thread.sleep(1000);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		List<Integer> recursosReservados = reservaAux(r, id);

		Buffer.getSReserva().release();

		return recursosReservados;

	}

	private List<Integer> reservaAux(int r, int id) {

		List<Integer> recursosAsignados;

		List<Integer> recursosRes = new LinkedList<Integer>();

		if (reservas.containsKey(id)) {

			recursosAsignados = reservas.get(id);

		}

		else {

			recursosAsignados = new LinkedList<Integer>();

			reservas.put(id, recursosAsignados);
		}

		for (int i = 0; i < r; i++) {

			Integer bloque = recurso.poll();

			recursosRes.add(bloque);

			recursosAsignados.add(bloque);

		}

		return recursosRes;
	}

	public List<Integer> libera(int r, int id){
		
		try {

			Buffer.getSLibera().acquire();
		}

		catch (InterruptedException e) {

			e.printStackTrace();
		}

		List<Integer> recursosLiberados = null;
		
		if (Buffer.reservas.get(id).size() - r < 0) {

			System.out.println("No hay tantos recursos reservados.");
			
		}
		else {
			
			recursosLiberados = liberaAux(r, id);
			
		}
	
		Buffer.getSLibera().release();

		return recursosLiberados;

	}
	
	private List<Integer> liberaAux(int r, int id) {

		List<Integer> recursosAsignados = reservas.get(id);

		List<Integer> recursosRes = new LinkedList<Integer>();

		for (int i = 0; i < r; i++) {

			Integer bloque = recursosAsignados.getFirst();

			recursosRes.add(bloque);

			recursosAsignados.remove(bloque);
			
			recurso.add(bloque);

		}

		return recursosRes;
	}

	public static Semaphore getSReserva() {
		return SReserva;
	}

	public static Semaphore getSLibera() {
		return SLibera;
	}

}
