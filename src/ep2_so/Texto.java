package ep2_so;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Texto {
	
	private List<String> texto = new ArrayList();
	
	
	public Texto(String file) throws IOException {
		
		this.ler_arquivo(file);
	}
	
	/*
	 * Realiza a leitura do arquivo.
	 * Cada palavra lida é guardada em uma posição do arranjo do texto.
	 */
	private void ler_arquivo(String file) throws IOException {
		
		Scanner input = new Scanner(Paths.get(file));
		while (input.hasNext()) texto.add(input.next());
	}
	
	public List<String> getTexto() {
		
		return this.texto;
	}
	
}
