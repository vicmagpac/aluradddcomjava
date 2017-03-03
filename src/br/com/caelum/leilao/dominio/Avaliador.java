package br.com.caelum.leilao.dominio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {
	
	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private double media;
	private List<Lance> maiores;
	
	public void avaliar(Leilao leilao) {
		
		
		if (leilao.getLances().size() == 0) {
			throw new RuntimeException("Não é possível avaliar um leilão sem lances");
		}
		
		
		double total = 0;
		for (Lance lance : leilao.getLances()) {
			if (lance.getValor() >  this.maiorDeTodos) {
				this.maiorDeTodos = lance.getValor();
			} 
			
			if (lance.getValor() < this.menorDeTodos) {
				this.menorDeTodos = lance.getValor();
			}
			
			total += lance.getValor();
		}
		
		this.pegaOsDoisMaioresNo(leilao);
		
		this.media = total / leilao.getLances().size();
	}
	
	public void pegaOsDoisMaioresNo(Leilao leilao) {
		this.maiores = new ArrayList<Lance>(leilao.getLances());
		Collections.sort(this.maiores, new Comparator<Lance>() {
			public int compare(Lance o1, Lance o2) {
				if (o1.getValor() < o2.getValor()) {
					return 1;
				}
				
				if (o1.getValor() > o2.getValor()) {
					return -1;
				}
				return 0;
			}
		});
		
		this.maiores = this.maiores.subList(0, this.maiores.size() > 3 ? 3 : this.maiores.size());
	}
	
	public List<Lance> getTresMaiores() {
		return this.maiores;
	}
	
	public Double getMaiorLance() {
		return this.maiorDeTodos;
	}
	
	public Double getMenorLance() {
		return this.menorDeTodos;
	}

	public double getMedia() {
		return this.media;
	}
}
