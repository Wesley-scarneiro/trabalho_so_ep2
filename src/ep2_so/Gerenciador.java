
/*
 * Classe para gerenciar a execu��o dos leitores e escritores.
 * 
 * Um leitor ou escritor realiza uma chamada ao Gerenciador, solicitando
 * um acesso ao texto, que � uma regi�o cr�tica. 
 * 
 * 
 */

package ep2_so;

import java.util.Random;

public class Gerenciador {
	
	private Leitor_escritor [] lista = new Leitor_escritor[100];
	private Texto texto;
	private int num_leitores;
	private int num_escritores;
	private long startTime = 0;
	
	public Gerenciador(int num_leitores, int num_escritores, Texto t) throws InterruptedException {
		
		this.num_leitores = num_leitores;
		this.num_escritores = num_escritores;
		this.texto = t;
		
		this.popular_lista(t);
		
		/*
		 * MARCA��O INICIAL DO TEMPO: AP�S TODAS AS THREADS TEREM SIDO CRIADAS.
		 */
		startTime = System.currentTimeMillis();
		
		this.iniciar_threads();
	}
	
	
	/*
	 * Popula o array de leitores e escritores.
	 * Distribui cada leitor ou escritor em uma posi��o aleat�ria do array.
	 */
	private void popular_lista(Texto t) {
		
		Random random = new Random();
		int num = random.nextInt(100);
		
		// Cria os leitores
		for (int i = 0; i < num_leitores; i++) {
			
			while(lista[num] != null) num = random.nextInt(100);
			lista[num] = new Leitor_escritor(true, t);
		}
		
		// Cria os escritores
		for (int i = 0; i < num_escritores; i++) {
			
			while(lista[num] != null) num = random.nextInt(100);
			lista[num] = new Leitor_escritor(false, t);
		}
	}
	
	/*
	 * Inicializa as threads de cada leitor e escritor.
	 * Usa-se join em todas as threads, para que o main n�o termine antes de nenhuma delas.
	 */
	private void iniciar_threads() throws InterruptedException {
		
		for (Leitor_escritor i : lista) {
			
			i.start();
			i.join();
		}
	}
	
	public long getStarTime() {
		
		return this.startTime;
	}
	
	public void excluir_lista() {
		
		lista = null;
	}

}

