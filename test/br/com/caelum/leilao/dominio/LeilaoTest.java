package br.com.caelum.leilao.dominio;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.dominio.builder.CriadorDeLeilao;

public class LeilaoTest {
	
	
	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;

	@Before
	public void setUp() {
		this.usuario1 = new Usuario("Usuario 1");
		this.usuario2 = new Usuario("Usuario 2");
		this.usuario3 = new Usuario("Usuario 3");
	}
	
	@Test
	public void deveReceberUmLance() {
		
		
		Leilao leilao = new CriadorDeLeilao().para("PS4").constroi();
				
		assertEquals(0, leilao.getLances().size());
		
		leilao.propoe(new Lance(this.usuario1, 2000));
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(2000, leilao.getLances().get(0).getValor(), 0.00001);
	}

	
	@Test
	public void deveReceberVariosLances() {
		
		Leilao leilao = new CriadorDeLeilao().para("PS4")
											 .lance(this.usuario1, 2000.0)
											 .lance(this.usuario2, 3000.0).constroi();
	
		assertEquals(2, leilao.getLances().size());
		assertEquals(2000.0, leilao.getLances().get(0).getValor(), 0.00001);
		assertEquals(3000.0, leilao.getLances().get(1).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarDoisLancesSeguidosDoMesmoUsuario() {
		
		
		Leilao leilao = new CriadorDeLeilao().para("PS4")
											 .lance(this.usuario1, 500.0)
											 .lance(this.usuario1, 600.0).constroi();
		
		assertEquals(1, leilao.getLances().size());
		assertEquals(500.0, leilao.getLances().get(0).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveAceitarMaisDo5LancesDeUmMesmoUsuario() {
		
		Leilao leilao = new CriadorDeLeilao().para("PS4")
											 .lance(this.usuario1, 100)
											 .lance(this.usuario2, 200)
											 .lance(this.usuario1, 300)
											 .lance(this.usuario2, 400)
											 .lance(this.usuario1, 500)
											 .lance(this.usuario2, 600)
											 .lance(this.usuario1, 700)
											 .lance(this.usuario2, 800)
											 .lance(this.usuario1, 900)
											 .lance(this.usuario2, 1000)
											 .lance(this.usuario1, 1100).constroi();
		
		
		assertEquals(10, leilao.getLances().size());
        int ultimo = leilao.getLances().size() - 1;
        Lance ultimoLance = leilao.getLances().get(ultimo);
        assertEquals(1000.0, ultimoLance.getValor(), 0.00001);
	}
	
	@Test
	public void deveDobrarOUltimoLance() {
		
		Leilao leilao = new CriadorDeLeilao().para("PS4")
											 .lance(this.usuario1, 500)
											 .lance(this.usuario2, 800)
											 .constroi();
	
		
		leilao.dobraLance(this.usuario1);
		assertEquals(1000.0, leilao.getLances().get(2).getValor(), 0.00001);
	}
	
	@Test
	public void naoDeveDobrarCasoNaoHajaLanceAnterior() {       
        Leilao leilao = new CriadorDeLeilao().para("PS4").constroi();

        leilao.dobraLance(this.usuario1);

        assertEquals(0, leilao.getLances().size());
	}
}














