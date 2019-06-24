package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Lista;
import modelo.status.Status;
import modelo.usuario.Usuario;
import pads.musicPlayer.exceptions.Mp3PlayerException;

class TestLista {
	Usuario z;
	String nombre;
	String path;
	Cancion cancion;
	Album album;
	Lista lista_auxiliar;
	Lista lista;
	boolean prueba;
	
	@BeforeEach
	public void before() throws FileNotFoundException, Mp3PlayerException {
		z = new Usuario("alex", "alex", LocalDate.of(1967, 12, 26), "alex");
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		cancion = new Cancion("hive",z,path,nombre);
		album = new Album(LocalDate.now().getYear(), "deporte", z);
		lista_auxiliar = new Lista("despertar",z);
		lista = new Lista("fiesta", z);
	}
	
	
	@Test
	public void TestAnyadirContenidoCancion() {
		if(lista.anyadirContenido(cancion) ==Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestAnyadirContenidoAlbum() {
		if(lista.anyadirContenido(album) ==Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestAnyadirContenidoLista() {
		if(lista.anyadirContenido(lista_auxiliar) ==Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarContenidoCancion() {
		lista.anyadirContenido(cancion);
		if(lista.eliminarContenido(cancion) ==Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarContenidoAlbum() {
		lista.anyadirContenido(album);
		if(lista.eliminarContenido(album) ==Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestEliminarContenidoLista() {
		lista.anyadirContenido(lista_auxiliar);
		if(lista.eliminarContenido(lista_auxiliar) ==Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestCalcularTiempo() {
		
		//una cancion => 0,6
		lista.anyadirContenido(cancion);
		
		//un album con cancion => 0,6
		album.anyadirContenido(cancion);
		
		//una lista auxiliar con cancion => 0,6
		lista_auxiliar.anyadirContenido(cancion);
		
		lista.anyadirContenido(album);
		lista.anyadirContenido(lista_auxiliar);
		
		assertEquals(0,18, lista.getDuracion());
		
	}

}
