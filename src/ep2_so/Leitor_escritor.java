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
		
		this.is_reader = valor;
		this.texto = t;
		
		if (valor) this.tipo = "LEITOR";
		else this.tipo = "ESCRITOR";
	}
	
	
	/*
	 * M�todo para inicializar a Thread.
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
	 * A cada execu��o, um leitor obt�m uma palavra aleat�ria do texto.
	 */
	public void ler_palavra() {
		
		Random random = new Random();
		List<String> t = texto.getTexto();

		for (int i = 0; i < 100; i++) {
			
			this.palavra_lida = t.get(random.nextInt(100));
		}
	}
	
	/*
	 * A cada execu��o, um escritor obt�m uma palavra e a substitui pela string "MODIFICADO".	
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
