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
		
		is_reader = valor;
		texto = t;
		
		if (valor) tipo = "LEITOR";
		else tipo = "ESCRITOR";
	}
	
	
	/*
	 * Método para inicializar a Thread.
	 * Executa diferentes rotinas para um leitor e escritor.
	 */
	public void run() {
		
		if (this.is_reader) {
			
			try {
				
				this.solicitar_leitura();
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		else {
			
			try {
				
				this.solicitar_escrita();
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
	
	public boolean getIsReader() {
		
		return this.is_reader;
	}
	
	/*
	 * Um leitor irá solicitar a leitura de uma palavra para o objeto Texto.
	 * Cabe ao objeto Texto gerenciar os acessos da região crítica.
	 */
	public void solicitar_leitura() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			palavra_lida = texto.ler_palavra(this, i, random.nextInt(texto.getSize()));
		}
	}
	
	/*
	 * Um escritor irá solicitar a escrita de uma palavra no texto para o objeto Texto.	
	 */
	public void solicitar_escrita() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			texto.escrever_palavra(this, i, random.nextInt(texto.getSize()));
		}
	}
	
	public String toString() {
		
		return tipo;
	}
}
