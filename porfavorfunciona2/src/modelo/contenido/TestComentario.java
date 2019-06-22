package modelo.contenido;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.status.Status;
import pads.musicPlayer.exceptions.Mp3PlayerException;

class TestComentario {

	Comentario a;
	Comentario b;
	boolean prueba;
	
	@BeforeEach
	public void before() throws FileNotFoundException, Mp3PlayerException {
		a = new Comentario("muy buena la cancion","alex");
		b = new Comentario("muy grande","alex");
	}
	
	@Test
	public void TestAnyadirSubcomentario() {
		if(a.anyadirSubComentario(b) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarSubcomentario() {
		a.anyadirSubComentario(b);
		if(a.eliminarSubComentario(b) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarSubcomentarios() {
		a.anyadirSubComentario(b);
		if(a.eliminarSubComentarios() == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}

}
