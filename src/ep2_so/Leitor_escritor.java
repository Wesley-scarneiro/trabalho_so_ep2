/*
 * Se leitor, a vari�vel is_reader � TRUE, caso ao contr�rio ser� FALSE.
 * Somente os m�todos ler_palavra() e escrever_palavra() est�o paraleliz�veis.
 * Quando a thread do objeto inicia, esses m�todos ser�o executados.
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
	 * M�todo para inicializar a Thread.
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
	 * Um leitor ir� solicitar a leitura de uma palavra para o objeto Texto.
	 * Cabe ao objeto Texto gerenciar os acessos da regi�o cr�tica.
	 */
	public void solicitar_leitura() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			palavra_lida = texto.ler_palavra(this, i, random.nextInt(texto.getSize()));
		}
	}
	
	/*
	 * Um escritor ir� solicitar a escrita de uma palavra no texto para o objeto Texto.	
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
