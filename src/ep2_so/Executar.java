/*
 * Realiza a execu��o do EP, fazendo uma chamada para a classe Gerenciador.
 */

package ep2_so;

import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class Executar {
	
	/*
	 * Inicializa a classe Texto e Gerenciador.
	 */
	public static void iniciar(String arquivo, int repeticoes) throws IOException, InterruptedException {
		
		Texto texto = new Texto(arquivo);
		List<String> log_file = new ArrayList();
		
		log_file.add("N� Leitores | N� Escritores | Tempo total (ms)");
		
		// N�mero de repeti��es da simula��o.
		for (int repetir = 0; repetir < repeticoes; repetir++) {
			
			log_file.add("\n");
			log_file.add("Simula��o: " + (1+repetir));
			
			// Propor��es de leitores e escritores.
			for (int leitores = 0, escritores = 100; leitores <= 100; leitores++, escritores--) {
				
				long totalTime = 0;
				
				// Criando uma simula��o, com um dado n�mero de leitores e escritores.
				Gerenciador gerenciador = new Gerenciador(leitores, escritores, texto);
				gerenciador.excluir_lista();
				
				/*
				 * MARCA��O FINAL DO TEMPO: AP�S O T�RMINO DA �LTIMA THREAD.
				 * Marca o tempo total decorrido de uma dada propor��o de leitores e escritores.
				 */
				totalTime = System.currentTimeMillis() - gerenciador.getStarTime();
				log_file.add(escritores + " " + leitores + " " + totalTime);
			}
		}
		
		gerarLog(log_file);
	}
	
	public static void gerarLog(List<String> l) throws FileNotFoundException {
		
		Formatter output = new Formatter("log_com_rw.txt");
		
		for (String i : l) {
			
			output.format("%s\n", i);
		}
		
		output.close();
	}

	public static void main(String[] args) {
		
		try {
			
			iniciar("entrada.txt", 2);
			
		} catch (IOException e) {
			
			System.out.println("Erro na abertura do arquivo");
		
		} catch (InterruptedException e) {
			
			System.out.println("Erro de thread.");
			
		} catch (Exception e) {
			
			System.out.println("Um erro desconhecido ocorreu.");
		}
	}
}
