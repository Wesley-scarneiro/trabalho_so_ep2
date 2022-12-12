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

        List<String> log_file = new ArrayList();

        log_file.add("Nº Leitores | Nº Escritores | Tempo total (ms)\n");

        // Proporções de leitores e escritores.
        for (int leitores = 100, escritores = 0; leitores >= 0; leitores--, escritores++) {
            Texto texto = new Texto(arquivo);

        	System.out.println("Leitores: " + leitores + " escritores: " + escritores);
            long totalTime = 0;
            long tempoMedio = 0;

            // Número de repetições da simulação.
            for (int repetir = 0; repetir < repeticoes; repetir++) {

                // Criando uma simulação, com um dado número de leitores e escritores.
                Gerenciador gerenciador = new Gerenciador(leitores, escritores, texto);

                /*
                 * MARCAÇÃO FINAL DO TEMPO: APÓS O TÉRMINO DA ÚLTIMA THREAD.
                 * Marca o tempo total decorrido de uma dada proporção de leitores e escritores.
                 */
                totalTime = System.currentTimeMillis() - gerenciador.getStarTime();
                tempoMedio += totalTime;
            }

            tempoMedio /= repeticoes;
            log_file.add(leitores + " " + escritores + " " + tempoMedio);
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
			
			iniciar("entrada.txt",10);
			
		} catch (IOException e) {
			
			System.out.println("Erro na abertura do arquivo");
		
		} catch (InterruptedException e) {
			
			System.out.println("Erro de thread.");
			
		} catch (Exception e) {
			
			System.out.println("Um erro desconhecido ocorreu.");
		}
	}
}
