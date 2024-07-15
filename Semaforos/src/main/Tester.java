package main;

public class Tester {
	
	private static final int N_THREADS = 10;

	public static void main(String[] args) {
		
		Buffer bf = new Buffer ();
		
		for (int i = 0; i < N_THREADS; i++ ) {
			
			MainThread thread = new MainThread(bf, i);

			thread.start();

			}

		}
		
	}


