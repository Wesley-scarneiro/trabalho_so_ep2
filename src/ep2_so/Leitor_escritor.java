/*
 * Se leitor, a variável is_reader é TRUE, caso ao contrário será FALSE.
 * Somente os métodos ler_palavra() e escrever_palavra() estão paralelizáveis.
 * Quando a thread do objeto inicia, esses métodos serão executados.
 */

package ep2_so;

import java.util.List;
import java.util.Random;

public class Leitor_escritor extends Thread{

	private boolean is_reader;
	private String tipo;
	private String palavra_lida;
	private Texto texto;
	
	public Leitor_escritor(boolean valor, Texto t) {
		
		this.is_reader = valor;
		this.texto = t;
		
		if (valor) this.tipo = "LEITOR";
		else this.tipo = "ESCRITOR";
	}
	
	
	/*
	 * Método para inicializar a Thread.
	 * Executa diferentes rotinas para um leitor e escritor.
	 */
	public void run() {
		
		if (this.is_reader) {
			
			this.ler_palavra();
		}
		else {
			
			this.modificar_palavra();
		}
	}
	
	public boolean getIsReader() {
		
		return this.is_reader;
	}
	
	/*
	 * A cada execução, um leitor obtêm uma palavra aleatória do texto.
	 */
	public void ler_palavra() {
		
		Random random = new Random();
		List<String> t = texto.getTexto();

		for (int i = 0; i < 100; i++) {
			
			this.palavra_lida = t.get(random.nextInt(100));
		}
	}
	
	/*
	 * A cada execução, um escritor obtêm uma palavra e a substitui pela string "MODIFICADO".	
	 */
	public void modificar_palavra() {
		
		Random random = new Random();
		List<String> t = texto.getTexto();
		
		for (int i = 0; i < 100; i++) {
			
			t.add(random.nextInt(100), "MODIFICADO");
		}
		
	}
	
	public String toString() {
		
		return tipo;
	}
}
