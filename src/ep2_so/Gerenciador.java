
/*
 * Classe para gerenciar a execução dos leitores e escritores.
 * 
 * Um leitor ou escritor realiza uma chamada ao Gerenciador, solicitando
 * um acesso ao texto, que é uma região crítica. 
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
		 * MARCAÇÃO INICIAL DO TEMPO: APÓS TODAS AS THREADS TEREM SIDO CRIADAS.
		 */
		startTime = System.currentTimeMillis();
		
		this.iniciar_threads();
	}
	
	
	/*
	 * Popula o array de leitores e escritores.
	 * Distribui cada leitor ou escritor em uma posição aleatória do array.
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
	 * Usa-se join em todas as threads, para que o main não termine antes de nenhuma delas.
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

