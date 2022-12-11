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
	private Gerenciador gerenciador;
	
	public Leitor_escritor(boolean valor, Gerenciador g) {
		
		is_reader = valor;
		gerenciador = g;
		
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
				
				this.ler_palavra();
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		else {
			
			try {
				
				this.escrever_palavra();
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
	}
	
	public boolean getIsReader() {
		
		return this.is_reader;
	}
	
	/*
	 * Um leitor solicita ao Gerenciador a leitura de uma palavra do texto em uma posição 'i' aleatória. 
	 * O gerenciador retorna a palavra solicitada.
	 */
	public void ler_palavra() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			this.palavra_lida = gerenciador.atender_leitor(this, i, random.nextInt(100));
		}
	}
	
	/*
	 * A cada execução, um escritor obtêm uma palavra e a substitui pela string "MODIFICADO".	
	 */
	public void escrever_palavra() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			gerenciador.atender_escritor(this, i, random.nextInt(100), "MODIFICADO");
		}
	}
	
	public String toString() {
		
		return tipo;
	}
}
