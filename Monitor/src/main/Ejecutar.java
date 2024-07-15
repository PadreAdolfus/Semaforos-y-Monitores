package main;

public class Ejecutar {

	private static final int N_THREADS = 20;

	public static void main(String[] args) {

		MonitorPuente puente = new MonitorPuente();

		for (int i = 0; i < N_THREADS; i++) {

			Coche coche = new Coche(puente);

		}
	}
}
