package ep2_so;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Texto {
	
	private List<String> texto = new ArrayList();
	private int mutex = 1;							// Controla a entrada da regi�o cr�tica.
	private int db = 1;								// Controla a escrita na regi�o cr�tica.
	private int leitores = 0;						// N�mero de leitores na regi�o cr�tica.
	
	public Texto(String file) throws IOException {
		
		this.ler_arquivo(file);
	}
	
	/*
	 * Realiza a leitura do arquivo.
	 * Cada palavra lida � guardada em uma posi��o do arranjo do texto.
	 */
	private void ler_arquivo(String file) throws IOException {
		
		Scanner input = new Scanner(Paths.get(file));
		while (input.hasNext()) texto.add(input.next());
		input.close();
	}
	
	/*
	 * Realiza o gerenciamento da regi�o cr�tica para os leitores.
	 * M�todo chamado por um leitor.
	 * Permite que somente um leitor esteja na regi�o cr�tica quando for preciso manipular as vari�veis globais.
	 * Permite que v�rios leitores possam ler uma palavra do texto, quando n�o manipulam vari�veis globais.
	 */
	public String ler_palavra(Thread leitor, int num_acesso, int indice) throws InterruptedException {
		
		// RC bloqueada, precisa dormir e esperar.
		while (mutex == 0) leitor.sleep(1);
				
		// RC liberada, pode acessar o texto para ler.
		--mutex;
		++leitores;
		if (leitores == 1) --db;				// Bloqueia o acesso aos escritores.
		++mutex;
		String palavra_lida = texto.get(indice);
				
		// Verifica se j� pode sair da RC.
		while (mutex == 0) leitor.sleep(1);
		--mutex;
		--leitores;
				
		// No acesso de n�mero 100, dorme por 1ms antes de sair da RC.
		if (num_acesso == 100) leitor.sleep(1);
				
		// Sai da da RC.
		if (leitores == 0) ++db;
		++mutex;
		return palavra_lida;
	}
	
	public String ler_palavra2(Thread t, int num_acesso, int indice) throws InterruptedException {
		
		String palavra_lida = texto.get(indice);
		if (num_acesso == 100) t.sleep(1);
		return palavra_lida;
	}
	
	public void escrever_palavra2(Thread t, int num_acesso, int indice) throws InterruptedException {
		
		texto.add(indice, "MODIFICADO");
		if (num_acesso == 100) t.sleep(1);
	}
	
	/*
	 * M�todo chamado por um escritor.
	 * Permite somente um escritor por vez na regi�o cr�tica.
	 * Um escritor s� poder� entrar na RC, quando n�o existirem mais leitores.
	 */
	public void escrever_palavra(Thread escritor, int num_acesso, int indice) throws InterruptedException {
		
		// Se a RC n�o estiver liberada e n�o houver leitores
		while(db == 0) escritor.sleep(1);
		
		// RC desbloqueada, pode entrar para fazer a escrita.
		--mutex;
		--db;
		texto.add(indice, "MODIFICADO");;
		++db;
		
		// Dorme no acesso de n�mero 100.
		if (num_acesso == 100) escritor.sleep(1);
		
		// Sai da RC.
		++mutex;
	}
	
	public int getSize() {
		
		return texto.size();
	}

}
