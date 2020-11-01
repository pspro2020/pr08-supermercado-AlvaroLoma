package semaforo;

public class Main {

	public static void main(String[] args) {

		Supermercado supermercado = new Supermercado(4);
		for (int i = 0; i < 50; i++) {
			new Thread(new Cliente(supermercado)).start();
		}

	}

}
