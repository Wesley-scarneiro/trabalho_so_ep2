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
	private int leitores = 0;							// N�mero de leitores na regi�o cr�tica.
	
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
	
	public List<String> getTexto() {
		
		return this.texto;
	}

	public int getMutex() {
		
		return mutex;
	}

	public void upMutex() {
		
		this.mutex++;
	}
	
	public void downMutex() {
		
		this.mutex--;
	}

	public int getDb() {
		
		return db;
	}
	
	public void upDb() {
		
		this.db++;
	}

	public void downDb() {
		
		this.db--;
	}
	
	public void addLeitores() {
		
		this.leitores++;
	}
	
	public void subLeitores() {
		
		this.leitores--;
	}
	
	public int getLeitores()
	{
		
		return this.leitores;
	}
}
