package modelo.contenido;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.status.Status;
import modelo.usuario.Usuario;
import pads.musicPlayer.exceptions.Mp3PlayerException;

class TestAlbum {
	Usuario z;
	Album album;
	Cancion x;
	ArrayList<Cancion> contenido;
	Comentario c;
	boolean explicita;
	boolean prueba;
	String nombre;
	String path;
	
	@BeforeEach
	public void before() throws FileNotFoundException, Mp3PlayerException {
		z = new Usuario("alex", "alex", LocalDate.of(1967, 12, 26), "alex");
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		x = new Cancion("hive",z,path,nombre);
		album = new Album(LocalDate.now().getYear(), "deporte", z);
		c = new Comentario("muy buena cancion",z.getNombreUsuario());
	}

	
	@Test
	public void TestAyadirContenido() throws FileNotFoundException, Mp3PlayerException {
		if(album.anyadirContenido(x) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		
	}
	
	@Test
	public void TestEliminarContenido() throws FileNotFoundException, Mp3PlayerException {
		album.anyadirContenido(x);
		if(album.eliminarContenido(x) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true,prueba);
	}
	
	@Test
	public void TestGetContenido() throws FileNotFoundException, Mp3PlayerException {
		album.anyadirContenido(x);
		contenido = new ArrayList<Cancion>();
		contenido.add(x);
		assertEquals(contenido, album.getContenido());
	}
	
	@Test
	public void TestCalcularTiempo() throws FileNotFoundException, Mp3PlayerException {
		album.anyadirContenido(x);
		double aux = album.calcularTiempo();
		assertEquals(0,6, aux);
	}
	
	@Test
	public void TestAnyadirComentario() {
		if(album.anyadirComentario(c) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarComentario() {
		album.anyadirComentario(c);
		if(album.eliminarComentario(c) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	
	
	
}

