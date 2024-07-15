package main;

import java.util.Random;

public class Coche extends Thread {

	private enum STATE {
		LLEGANDO, ESPERANDO, CRUZANDO
	};

	private DIRECTION direction;

	private STATE state;

	private int id;

	private static int TotalCoches;

	private MonitorPuente monitor;

	public Coche(MonitorPuente m) {

		id = TotalCoches++;

		state = STATE.LLEGANDO;

		if (TotalCoches % 2 == 0) {

			direction = DIRECTION.NORTE;
		}

		else {

			direction = DIRECTION.SUR;
		}

		monitor = m;

		start();

	}

	private void llegar() {

		System.out.println("El coche esta llegando al puente. Id: " + id);

		Random rnd = new Random();

		int tiempoLlegar = rnd.nextInt(250 - 50 + 1) + 50;

		try {

			sleep(tiempoLlegar);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		System.out.println("El coche ha llegado al puente. Id: " + id);

		state = STATE.ESPERANDO;
	}

	private void esperar() {

		System.out.println("El coche esta esperando a poder cruzar el puente. Id: " + id);

		monitor.pedirCruzar(this.direction, id);

		state = STATE.CRUZANDO;

	}

	private void cruzar() {
		System.out.println("El coche empieza a cruzar el puente. Id: " + id);

		Random rnd = new Random();

		int tiempoCruzar = rnd.nextInt(250 - 50 + 1) + 50;

		try {
			
			sleep(tiempoCruzar);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}

		monitor.salirPuente(id);

		System.out.println(" El coche termina de cruzar el puente. Id: " + id);

		state = STATE.LLEGANDO;
	}

	public DIRECTION getDirection() {

		return direction;
	}

	public void setDirection(DIRECTION direction) {

		this.direction = direction;
	}

	public void run() {

		boolean running = true;

		while (running) {

			switch (state) {

			case LLEGANDO:

				llegar();

				break;

			case ESPERANDO:

				esperar();

				break;

			case CRUZANDO:

				cruzar();

				running = false;

				break;
			}
		}
	}

}
