
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
	private long startTime;
	
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
			lista[num] = new Leitor_escritor(true, this);
		}
		
		// Cria os escritores
		for (int i = 0; i < num_escritores; i++) {
			
			while(lista[num] != null) num = random.nextInt(100);
			lista[num] = new Leitor_escritor(false, this);
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

	
	/*
	 * Realiza o atendimento das chamadas dos leitores.
	 * Gerencia o acesso ao texto por meio das vari�veis mutex e db da classe Texto.
	 * Antes de sair da regi�o cr�tica, a thread dorme por 1ms e depois � retornada
	 * a palavra solicitada. 
	 */
	public String atender_leitor(Leitor_escritor leitor, int num_acesso, int indice) throws InterruptedException {
		
		// RC bloqueada, precisa dormir e esperar.
		while (texto.getMutex() == 0) leitor.sleep(1);
		
		// RC liberada, pode acessar o texto para ler.
		texto.downMutex();
		texto.addLeitores();
		if (texto.getLeitores() == 1) texto.downDb();				// Bloqueia o acesso aos escritores.
		texto.upMutex();
		String palavra_lida = texto.getTexto().get(indice);
		
		// Verifica se j� pode sair da RC.
		while (texto.getMutex() == 0) leitor.sleep(1);
		texto.downMutex();
		texto.subLeitores();
		
		// No acesso de n�mero 100, dorme por 1ms antes de sair da RC.
		if (num_acesso == 100) leitor.sleep(1);
		
		// Sai da da RC.
		if (texto.getLeitores() == 0) texto.upDb();
		texto.upMutex();
		return palavra_lida;
	}
	
	/*
	 * Realiza o atendimento das chamadas dos escritores.
	 * Gerencia o acesso ao texto por meio das vari�veis mutex e db da classe Texto.
	 * Um escritor sempre espera n�o haver mais leitores na RC para poder acess�-la.
	 * Ap�s o escritor entrar, qualquer outro escritor ou leitor precisar� aguardar a sua sa�da. 
	 * Um escritor sempre escreve "MODIFICADO" em um dado elemento do texto.
	 */
	public void atender_escritor(Leitor_escritor escritor, int acesso, int indice, String p) throws InterruptedException {
		
		// Se a RC n�o estiver liberada e n�o houver leitores
		while(texto.getMutex() == 0 || texto.getDb() == 0) escritor.sleep(1);
		
		// RC desbloqueada, pode entrar para fazer a escrita.
		texto.downMutex();
		texto.downDb();
		texto.getTexto().add(indice, p);
		texto.upDb();
		
		// Dorme no acesso de n�mero 100.
		if (acesso == 100) escritor.sleep(1);
		
		// Sai da RC.
		texto.upMutex();
	}
	
	public void imprimir() {
		
		for (String i : texto.getTexto()) {
			
			System.out.println(i);
		}
	}
	
	public long getStarTime() {
		
		return this.startTime;
	}

}

