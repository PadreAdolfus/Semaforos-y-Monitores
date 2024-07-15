package main;

import java.util.LinkedList;

import java.util.List;

public class MonitorPuente {

	private List<Integer> cochesCruzando = new LinkedList<Integer>();

	private DIRECTION dirPuente = null;

	public synchronized void pedirCruzar(DIRECTION dir, Integer id) {

		while (dirPuente != dir && dirPuente != null) {

			try {

				wait();

			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

		cochesCruzando.add(id);

		if (dirPuente == null) {

			dirPuente = dir;
		}

	}

	public synchronized void salirPuente(Integer id) {

		if (!cochesCruzando.contains(id)) {

			System.out.println("Coche no puede salir del puente porque no estaba cruzando");

			return;
		}

		cochesCruzando.remove(id);

		if (cochesCruzando.isEmpty()) {

			dirPuente = null;

			notify();

		}
	}

}