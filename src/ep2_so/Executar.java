/*
 * Realiza a execução do EP, fazendo uma chamada para a classe Gerenciador.
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
		
		log_file.add("Nº Leitores | Nº Escritores | Tempo total (ms)");
		
		// Número de repetições da simulação.
		for (int repetir = 0; repetir < repeticoes; repetir++) {
			
			log_file.add("\n");
			log_file.add("Simulação: " + (1+repetir));
			
			// Proporções de leitores e escritores.
			for (int leitores = 0, escritores = 100; leitores <= 100; leitores++, escritores--) {
				
				long totalTime = 0;
				
				// Criando uma simulação, com um dado número de leitores e escritores.
				Gerenciador gerenciador = new Gerenciador(leitores, escritores, texto);
				gerenciador.excluir_lista();
				
				/*
				 * MARCAÇÃO FINAL DO TEMPO: APÓS O TÉRMINO DA ÚLTIMA THREAD.
				 * Marca o tempo total decorrido de uma dada proporção de leitores e escritores.
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
