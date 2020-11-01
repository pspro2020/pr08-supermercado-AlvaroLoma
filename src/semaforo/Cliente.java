package semaforo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Cliente implements Runnable {
	private Supermercado supermercado;

	public Cliente(Supermercado supermercado) {

		this.supermercado = supermercado;
	}

	@Override
	public void run() {

		try {
			System.out.printf("El cliente %s ha llegado al supermercado a las %s \n", Thread.currentThread().getName(),
					DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			
			supermercado.entrar();

		} catch (InterruptedException e) {

			return;
		}
		meVoyDelSuper();

	}

	private void meVoyDelSuper() {
		System.out.printf("El cliente %s se va del super a las %s \n", Thread.currentThread().getName(),
				DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));

	}

}
