package test;


import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Lista;
import modelo.sistema.Sistema;
import modelo.status.Status;
import pads.musicPlayer.exceptions.Mp3PlayerException;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.usuario.Usuario;
import modelo.usuario.UsuarioBloqueado;

class TestUsuario {

	Usuario z;
	Usuario d;
	Album album;
	Cancion cancion;
	Lista lista;
	Sistema a;
	boolean prueba;
	String nombre;
	String path;
	
	
	@BeforeEach
	public void before() throws Mp3PlayerException, IOException {
		a = Sistema.getSistema();
		
		z = new Usuario("alex", "alex", LocalDate.of(1967, 12, 26), "alex");
		d = new Usuario("pedro","pedro",LocalDate.of(1967, 12, 26),"pedro");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		cancion = new Cancion("hive",z,path,nombre);
		album = new Album(LocalDate.now().getYear(), "deporte", z);
		lista = new Lista("por mi", z);
		
		
		a.getUsuariosTotales().add(z);
		a.getUsuariosTotales().add(d);
		a.iniciarSesion("alex", "alex");
	}
	
	@Test
	public void TestSumarReproduccion() {
		if(z.sumarReproduccion(4) == 1) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getPremium());

	}
	
	@Test
	public void TestSeguirUsuario() {
		if(z.seguirUsuario(d) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getSeguidos().contains(d));
		assertEquals(true, d.getSeguidores().contains(z));
		z.dejarDeSeguirUsuario(d);
	}
	
	@Test
	public void TestDejarDeSeguirUsuario() {
		z.seguirUsuario(d);
		if(z.dejarDeSeguirUsuario(d) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getSeguidos().contains(d));
		assertEquals(false, d.getSeguidores().contains(z));
	}
	
	@Test
	public void TestEliminarSeguido() {
		z.seguirUsuario(d);
		if(z.eliminarSeguido(d) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getSeguidos().contains(d));
		assertEquals(true, d.getSeguidores().contains(z));
		d.eliminarSeguidor(z);
	}
	
	@Test
	public void TestEliminarSeguidor() {
		z.seguirUsuario(d);
		if(d.eliminarSeguidor(z) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getSeguidos().contains(d));
		assertEquals(false, d.getSeguidores().contains(z));
		z.eliminarSeguido(d);
	}
	
	@Test
	public void TestMejorarCuentaReproducciones() {
		if(z.sumarReproduccion(1) == 1) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getPremium());
		assertEquals(LocalDate.now(), z.getFechaInicioPro());	
	}
	
	@Test
	public void TestMejorarCuentaPago() {
		if(z.mejorarCuentaPago("1234123412341234") == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getPremium());
		assertEquals(LocalDate.now(), z.getFechaInicioPro());	
	}
	
	@Test
	public void TestEmpeorarCuenta() {
		if(z.sumarReproduccion(1) == 1) {
			prueba = true;
		}else {
			prueba = false;
		}
		z.empeorarCuenta();
		assertEquals(false, z.getPremium());
		assertEquals(0, z.getNumeroReproParaPro());
		assertEquals(0, z.getContenidoEscuchadoSinSerPremium());
		assertEquals(null, z.getFechaInicioPro());
	}
	
	
	@Test
	public void TestBloquearCuentaIndefinido() {
		z.bloquearCuentaIndefinido();
		assertEquals(UsuarioBloqueado.INDEFINIDO, z.getEstadoBloqueado());
		assertEquals(LocalDate.now(), z.getFechaInicioBloqueado());
	}
	
	@Test
	public void TestBloquearCuentaTemporal() {
		z.bloquearCuentaTemporal();
		assertEquals(UsuarioBloqueado.TEMPORAL, z.getEstadoBloqueado());
		assertEquals(LocalDate.now(), z.getFechaInicioBloqueado());
	}
	
	@Test
	public void TestBloquearCuentaPorReporte() {
		z.bloqueoCuentaPorReporte();
		assertEquals(UsuarioBloqueado.POR_REPORTE, z.getEstadoBloqueado());
	}
	

	@Test
	public void TestDesbloquearCuenta() {
		z.bloquearCuentaTemporal();
		z.desbloquearCuenta();
		assertEquals(UsuarioBloqueado.NOBLOQUEADO, z.getEstadoBloqueado());
		assertEquals(null, z.getFechaInicioBloqueado());
	}
	
	@Test
	public void TestAnyadirACanciones() {
		if(z.anyadirACancionesPersonales(cancion) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getCanciones().contains(cancion));	
	}
	
	@Test
	public void TestEliminarDeCanciones() {
		z.anyadirACancionesPersonales(cancion);
		if(z.eliminarDeCancionesPersonales(cancion) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getCanciones().contains(cancion));	
	}
	
	
	@Test
	public void TestAnyadirAAlbumes() {
		if(z.anyadirAAlbumesPersonales(album) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getAlbumes().contains(album));	
	}
	
	@Test
	public void TestEliminarDeAlbumes() {
		z.anyadirAAlbumesPersonales(album);
		if(z.eliminarDeAlbumesPersonales(album) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getAlbumes().contains(album));	
	}
	
	@Test
	public void TestAnyadirAListas() {
		if(z.anyadirAListasPersonales(lista) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(true, z.getListas().contains(lista));	
	}
	
	@Test
	public void TestEliminarDeListas() {
		z.anyadirAListasPersonales(lista);
		if(z.eliminarDeListasPersonales(lista) == true) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getListas().contains(lista));	
	}
	
	@Test
	public void TestFollow() {
		if(z.follow(d) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
	}
	
	@Test
	public void TestUnfollow() {
		z.follow(d);
		if(z.unfollow(d) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false, z.getSeguidos().contains(d));
		assertEquals(false, d.getSeguidores().contains(z));
	}
	
	
	@Test
	public void TestEnviarNotificacion() {
		
		if(z.enviarNotificacion(d, "gran cancion amigo") == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		assertEquals(false, d.getNotificacionesTotales().isEmpty());
		assertEquals(false, z.getNotificacionesTotales().isEmpty());
	}
	
	@Test
	public void TestEliminarNotificacionesPropias() {
		z.enviarNotificacion(d, "gran cancion amigo");
		if(z.eliminarNotificacionesPropias() == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		assertEquals(false, d.getNotificacionesTotales().isEmpty());
		assertEquals(true, z.getNotificacionesTotales().isEmpty());
	}
}
