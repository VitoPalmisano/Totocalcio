package it.polito.tdp.totocalcio.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca {

	private Pronostico  pronostico;
	private int N;
	private List<Risultato> soluzione;
	/**
	 * Funzione di interfaccia che permette all'esterno 
	 * di chiamare l'algoritmo di ricerca
	 * 
	 * Prepara l'ambiente in cui la procedura ricorsiva operera'
	 * Poi passa la palla alla procedura ricorsiva
	 * 
	 * @param pronostico
	 * @return
	 */
	public List<Risultato> cerca(Pronostico pronostico) {
		
		this.pronostico = pronostico;
		this.N = pronostico.size();
		this.soluzione = new ArrayList<Risultato>();
		
		List<RisultatoPartita> parziale = new ArrayList<>();
		int livello = 0;
		
		ricorsiva(parziale, livello);
		
		return this.soluzione;
	}
	
	private void ricorsiva(List<RisultatoPartita> parziale, int livello) {
		
		// caso terminale?
		if(livello==N) {
			// questa soluzione parziale e' una soluzione completa
			this.soluzione.add(new Risultato(parziale));
		}
		else {
			// caso generale
			// [ parziale da 0 a livello-1 ] [ livello ] [ livello+1 in poi ]
			PronosticoPartita pp = pronostico.get(livello);
			// pp sono i sotto-problemi da provare
			
			for(RisultatoPartita ris : pp.getRisultati()) {
				// provo a mettere ris nella posizione 'livello'
				// della soluzione parziale
				
				// costruzione della soluzione parziale (sottoproblema)
				parziale.add(ris);
				
				//chiamata ricorsiva
				ricorsiva(parziale, livello+1);
				
				// backtracking
				parziale.remove(parziale.size()-1);
			}
		}
	}
}
/*
 * Livello = numero di partite che sto considerando
 * le partite da livello 0 a livello-1 sono gia' state decise
 * la partita di indice livello la devo decidere io
 * le partite da livello+1 in poi le decideranno le 
 * procedure ricorsive sottostanti.
 * 
 * Soluzione parziale: un elenco di RisultatoPartita di
 * lunghezza pari al livello.
 * 
 * Soluzione totale: ho N risultati, con 
 * N = num partite totali = indice ultima partita +1
 * 
 * Quando sono nel livello 4, io ho gia' 4 elementi (0,1,2,3)
 * e devo decidere l'elemento di indice 4 (il quinto elemento)
 * 
 * Condizione di terminazione: livello==N
 * 
 * Generazione delle soluzioni del livello:
 * provando tutti i pronostici definiti per quel livello.
 * 
 * [ "2X", "1", "1X2", "12" ]        L0
 * [ "2X" ] [ "1", "1X2", "12" ]     L1
 *         [ "1" ] [ "1X2", "12" ]   L2
 *                [ "1X2" ] [ "12" ] L3
*/