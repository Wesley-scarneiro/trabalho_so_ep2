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
	private String palavra_lida;
	private Texto texto;
	private int implementacao;
	
	public Leitor_escritor(boolean valor, Texto t, int implementacao) {
		
		is_reader = valor;
		texto = t;
		implementacao = implementacao;
	}
	
	
	/*
	 * M�todo para inicializar a Thread.
	 * Executa diferentes rotinas para um leitor e escritor.
	 */
	public void run() {
		
		if (this.is_reader) {
			
			try {
				
				// Implementa��o com Readers e Writes
				if (this.implementacao == 0) {
					
					this.solicitar_leitura();
					
				}
				
				// Implementa��o sem Readers e Writes. 
				else {
					
					synchronized (this) {
						
						this.solicitar_escrita2();

					}
				}
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}
		else {
			
			try {
				
				// Implementa��o com Readers e Writes
				if (this.implementacao == 0) {
					
					this.solicitar_escrita();
				}
				
				// Implementa��o sem Readers e Writes. 
				else {
					
					synchronized (this) {
						
						this.solicitar_escrita2();
					}
				}
				
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
	
	public void solicitar_leitura2() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			palavra_lida = texto.ler_palavra2(this, i, random.nextInt(texto.getSize()));
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
	
	public void solicitar_escrita2() throws InterruptedException {
		
		Random random = new Random();
		
		for (int i = 0; i < 100; i++) {
			
			texto.escrever_palavra2(this, i, random.nextInt(texto.getSize()));
		}
	}
}
