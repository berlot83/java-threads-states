package com.molokotech;

public class ThreadStates {

	static String str = "test states";
	static String str2 = "test blocked";

	public static void main(String[] args) throws InterruptedException {
		/* Se instancia el Thread */
		Thread t1 = new Thread(() -> {
			synchronized (str) {
				System.out.println(str);
				try {
					Thread.sleep(1000);
					System.out.println(str + " adentro del synchronized");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (str2) {
					try {
						str2.wait();
						str2.notify();
						System.out.println(Thread.currentThread().isInterrupted());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getState());
					System.out.println(str2);
				}

			}

		}, "thread-1");
		System.out.println(t1.getState());	// "NEW" --> nunca se arrancó

		Thread.sleep(2000);
		t1.start();
		System.out.println(t1.getState());	// "RUNNABLE" --> se arrancó

		Thread.sleep(700);
		System.out.println(t1.getState());	// "TIMED_WAITING" --> pusimos un sleep cuando ya estaba arrancado el thread
		
		Thread.sleep(5000);
		System.out.println(t1.getState());	// "TERMINATED" --> lo hacemos esperar 5 seg's lo cual supera el tiempo total de espera anterior y termina
	}

}
