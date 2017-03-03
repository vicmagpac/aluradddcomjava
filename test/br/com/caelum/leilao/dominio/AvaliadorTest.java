package br.com.caelum.leilao.dominio;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.dominio.builder.CriadorDeLeilao;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class AvaliadorTest {
	
	private Avaliador leiloeiro;
	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;
	
	@Before
	public void setUp() {
		this.leiloeiro = new Avaliador();
		
		this.usuario1 = new Usuario("Usuario 1");
		this.usuario2 = new Usuario("Usuario 2");
		this.usuario3 = new Usuario("Usuario 3");
	}
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new CriadorDeLeilao().para("PS3").constroi();
		
		this.leiloeiro.avaliar(leilao);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void naoDeveReceberLancesMenorOuIgualAZero() {
		Leilao leilao = new CriadorDeLeilao().para("PS3").lance(this.usuario1, 0).constroi();
		this.leiloeiro.avaliar(leilao);
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		
		Leilao leilao = new CriadorDeLeilao().para("Play 4")
											.lance(this.usuario1, 300)
											.lance(this.usuario2, 400)
											.lance(this.usuario3, 250)
											.constroi();
		
		this.leiloeiro.avaliar(leilao);
		
		double maiorEsperado = 400;
		double menorEsperado = 250;
		
		assertEquals(maiorEsperado, this.leiloeiro.getMaiorLance(), 0.001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.001);
	}
	
	@Test
	public void deveCalcularAMedia() {				
		
		Leilao leilao = new CriadorDeLeilao().para("Play 4")
											.lance(this.usuario1, 300)
											.lance(this.usuario2, 400)
											.lance(this.usuario3, 500)
											.constroi();
		
		this.leiloeiro.avaliar(leilao);
		
		assertEquals(400, this.leiloeiro.getMedia(), 0.0001);
		
	}
	
	@Test
	public void deveEntenderLeilaoComApenaUmLance() {		
	
		Leilao leilao = new CriadorDeLeilao().para("Play 4")
											 .lance(this.usuario1, 500)
											 .constroi();
		
		this.leiloeiro.avaliar(leilao);
		
		assertEquals(500, this.leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(500, this.leiloeiro.getMenorLance(), 0.00001);
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		
		
		Leilao leilao = new CriadorDeLeilao().para("Play 4")
											.lance(this.usuario1, 200)
											.lance(this.usuario2, 300)
											.lance(this.usuario1, 600)
											.lance(this.usuario2, 900)
											.constroi();
									
	
		this.leiloeiro.avaliar(leilao);
		
		List<Lance> maiores = this.leiloeiro.getTresMaiores();
		
		assertEquals(3, maiores.size());
		assertEquals(900, maiores.get(0).getValor(), 0.00001);
		assertEquals(600, maiores.get(1).getValor(), 0.00001);
		assertEquals(300, maiores.get(2).getValor(), 0.00001);
	}
	
}











