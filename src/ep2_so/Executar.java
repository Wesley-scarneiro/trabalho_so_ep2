/*
 * Realiza a execução do EP, fazendo uma chamada para a classe Gerenciador.
 */

package ep2_so;

import java.io.IOError;
import java.io.IOException;

public class Executar {
	
	/*
	 * Inicializa a classe Texto e Gerenciador.
	 */
	public static void iniciar(String arquivo, int repeticoes) throws IOException {
		
		long startTime = System.nanoTime();					// Inicia a contagem
		
		Texto texto = new Texto(arquivo);
		
		// Número de repetições da simulação.
		for (int repetir = 0; repetir < repeticoes; repetir++) {
			
			// Proporções de leitores e escritores.
			for (int leitores = 0, escritores = 100; leitores <= 100; leitores++, escritores--) {
				
				// Criando uma simulação, com um dado número de leitores e escritores.
				Gerenciador gerenciador = new Gerenciador(leitores, escritores, texto);
			}
		}
		
		long endTime = System.nanoTime();					// Finaliza a contagem
        long duration = (endTime - startTime);
        System.out.println("runtime: " + duration);
	}

	public static void main(String[] args) {
		
		try {
			
			iniciar("entrada.txt", 1);
			
		} catch (IOException e) {
			
			System.out.println("Erro na abertura do arquivo");
			
		} catch (Exception e) {
			
			System.out.println("Um erro desconhecido ocorreu.");
		}
	}
}
