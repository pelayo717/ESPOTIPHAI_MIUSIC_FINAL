package modelo.contenido;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.status.Status;
import modelo.usuario.Usuario;
import pads.musicPlayer.exceptions.Mp3PlayerException;


class TestCancion {
	Comentario c;
	Cancion x;
	Usuario z;
	boolean explicita;
	boolean prueba;
	String nombre;
	String path;
	
	@BeforeEach
	public void Before() throws FileNotFoundException, Mp3PlayerException {
		z = new Usuario("alex", "alex", LocalDate.of(1967, 12, 26), "alex");
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		x = new Cancion("hive",z,path,nombre);
		c = new Comentario("muy buena cancion",z.getNombreUsuario());
	}
	
	@Test
	public void TestDevolverDuracion() throws FileNotFoundException, Mp3PlayerException {
		double duracion = x.devolverDuracion();
		if(duracion <= 0) 
			prueba = false;
		else
			prueba = true;
		assertEquals(true, prueba);
	}

	@Test
	public void TestValidarCancion() {
		x.validarCancion();
		if(x.getEstado() == EstadoCancion.PENDIENTEAPROBACION)
			prueba = true;
		else 
			prueba = false;
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestCancionRechazada(){
		x.cancionRechazada();
		if(x.getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
			prueba = true;
		}else 
			prueba = false;
		assertEquals(true, prueba);
		assertEquals(LocalDate.now(),x.getFechaModificacion());
	}
	
	@Test
	public void TestReportarPlagio() {
		x.reportarPlagio();
		if(x.getEstado() == EstadoCancion.PLAGIO) {
			prueba = true;
		}else 
			prueba = false;
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestValidarCancionExplicita() {
		x.validarCancionExplicita();
		if(x.getEstado() == EstadoCancion.EXPLICITA) {
			prueba = true;
		}else 
			prueba = false;
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestCancionDescartada() {
		x.cancionDescartada();
		if(x.getEstado() == EstadoCancion.ELIMINADA) {
			prueba = true;
		}else 
			prueba = false;
		assertEquals(true, prueba);
	}
	
	
	@Test
	public void TestAnyadirComentario() {
		if(x.anyadirComentario(c) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarComentario() {
		x.anyadirComentario(c);
		if(x.eliminarComentario(c) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	
}
