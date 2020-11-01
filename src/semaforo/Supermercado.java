package semaforo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.locks.ReentrantLock;

public class Supermercado {
	int numCajas;
	Semaphore semaforo;
	boolean cajasLibres[];
	ReentrantLock cerrojo = new ReentrantLock(true);

	public Supermercado(int numCajas) {
		this.numCajas = numCajas;
		semaforo = new Semaphore(numCajas, true);

		cajasLibres = new boolean[numCajas];
		for (int i = 0; i < numCajas; i++) {
			cajasLibres[i] = true;
		}
	}

	public void entrar() throws InterruptedException {
		semaforo.acquire();
		try {
			System.out.printf("El cliente %s ha llegado a la zona de cajeros a las %s \n",
					Thread.currentThread().getName(),
					DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			int caja = seleccionarCaja();
			if (caja >= 0) {
				comprar(caja);
				terminarDeComprar(caja);
			}

		} finally {
			semaforo.release();

		}

	}

	private int seleccionarCaja() {
		cerrojo.lock();
		try {
			for (int i = 0; i < numCajas; i++) {

				if (cajasLibres[i]) {
					cajasLibres[i] = false;

					return i;
				}
			}
			return -1;
		} finally {
			cerrojo.unlock();
		}

	}

	private void terminarDeComprar(int caja) throws InterruptedException {
		TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(4) + 1);

		cajasLibres[caja] = true;
		System.out.printf("El cliente %s esta saliendo de la caja %d a las %s \n", Thread.currentThread().getName(),
				caja + 1, DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));

	}

	private void comprar(int caja) throws InterruptedException {

		System.out.printf("El cliente %s esta siendo atendido en la caja %d a las %s \n",
				Thread.currentThread().getName(), caja + 1,
				DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(3) + 1);
	}

}
