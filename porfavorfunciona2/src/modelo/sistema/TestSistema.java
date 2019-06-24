package modelo.sistema;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.EstadoCancion;
import modelo.contenido.Lista;
import modelo.status.Status;
import modelo.usuario.Usuario;
import modelo.usuario.UsuarioBloqueado;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class TestSistema {

	Usuario z;
	static Usuario d;
	Album album;
	Cancion cancion;
	Lista l;
	static Sistema a;
	boolean explicita;
	boolean prueba;
	String nombre;
	String path;
	
	@BeforeEach
	public static void before() throws Mp3PlayerException, IOException {
		a = new Sistema();
		d = new Usuario("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.getCancionTotales().clear();
		a.getAlbumTotales().clear();
		a.getUsuariosTotales().clear();
		a.getUsuariosTotales().add(d);		

	}
	
	@Test
	public void TestIniciarSesionUsuarioNormal() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");		
		
		if(a.iniciarSesion("pepe", "pepe") == EstadoInicioSesion.CORRECTO) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestCrearCancion() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		
		
		if(a.crearCancion("pp", path, nombre) != null) {
			prueba  = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
	}
	
	@Test
	public void TestEliminarCuenta() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		
		a.iniciarSesion("pepe","pepe");
		
		Usuario b = a.getUsuarioActual();
		
		a.eliminarCuenta();
		
		assertEquals(false,a.getUsuariosTotales().contains(b));
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	
	@Test 
	public void TestDesbloquearUsuarios() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-30);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		
		a.iniciarSesion("pepe", "pepe");
		
		a.getUsuarioActual().setEstadoBloqueado(UsuarioBloqueado.TEMPORAL);
		
		a.getUsuarioActual().setFechaInicioBloqueado(hoy);
		
		a.cerrarSesion();
		
		a.desbloquearUsuario();
		
		a.iniciarSesion("pepe", "pepe");

		assertEquals(UsuarioBloqueado.NOBLOQUEADO,a.getUsuarioActual().getEstadoBloqueado());
				
		File archivo = new File("datos.obj");
		archivo.delete();
		
	}
	
	@Test
	public void TestEliminarCancionesPendienteModificacionCaducadas() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(4);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		
		a.getUsuarioActual().getCanciones().get(0).setEstado(EstadoCancion.PENDIENTEMODIFICACION);
		
		a.eliminarCancionPendienteModificacionCaducada(hoy);
		
		assertEquals(true,a.getUsuarioActual().getCanciones().isEmpty());
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	
	@Test
	public void TestRegistarUsuario() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		if(a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe") == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		
		a.iniciarSesion("pepe", "pepe");
		
		File archivo = new File("datos.obj");
		archivo.delete();

	}
	
	
	@Test
	public void TestBuscarPorAutorDevolvemosCanciones() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.VALIDA); 
		
		ArrayList<Cancion> auxiliar = a.buscadorPorAutores_DevolvemosCanciones("pepe");
		
		assertEquals(false,auxiliar.isEmpty());
				
		File archivo = new File("datos.obj");
		archivo.delete();
		
	}
	
	
	@Test
	public void TestBuscarPorAutorDevolvemosAlbumes() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearAlbum(2019, "musica");
			
		ArrayList<Album> auxiliar = a.buscadorPorAutores_DevolvemosAlbumes("pepe");
		
		assertEquals(false,auxiliar.isEmpty());
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestBuscarTitulos() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.VALIDA); 
		
		ArrayList<Cancion> auxiliar = a.buscadorPorTitulos("pp");
		
		assertEquals(false,auxiliar.isEmpty());
		
		File archivo = new File("datos.obj");
		archivo.delete();
		
	}
	
	@Test
	public void TestResetearCuentasNuevoMes() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		LocalDate d = LocalDate.of(2019, 2,1);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.getUsuarioActual().addContenidoEscuchadoSinSerPremium();
		a.addContenidoEscuchadoSinRegistrarse();
		
		
		a.resetearContadoresNuevoMes(d);
		
		assertEquals(0, a.getContenidoEscuchadoSinRegistrarse());
		assertEquals(0, a.getUsuarioActual().getContenidoEscuchadoSinSerPremium());

		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestCerrarSesion() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();

		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		if(a.cerrarSesion() == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		assertEquals(false,a.getAdministrador());
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestEliminarCancion() throws Mp3PlayerException, IOException {

		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		Cancion c = a.crearCancion("pp", path, nombre);
		
		if(a.eliminarCancion(c) == Status.OK) {
			prueba  = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestBuscarAlbumes() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearAlbum(2019, "por mi");
		
		
		ArrayList<Album> auxiliar = a.buscadorPorAlbumes("por mi");
		
		assertEquals(false,auxiliar.isEmpty());
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	
	@Test
	public void TestIniciarSesionAdministrador() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		if(a.iniciarSesion("root1967", "ADMINISTRADOR") == EstadoInicioSesion.CORRECTO) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		assertEquals(true,a.getAdministrador());
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestEmpeorarCuenta() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-30);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.getUsuarioActual().setPremium(true);
		a.getUsuarioActual().setFechaInicioPro(hoy);
		
		a.empeorarCuentaPrincipal();
		
		assertEquals(false,a.getUsuarioActual().getPremium());
				
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestBuscarPorAutor() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.VALIDA); 
				
		a.crearAlbum(2019, "musica");
		
				
		ArrayList<Contenido> auxiliar = a.buscadorPorAutores("pepe");
		
		assertEquals(false,auxiliar.isEmpty());
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}

	
	
	@Test
	public void TestModificarCancionCorrecta() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		a.cerrarSesion();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.PENDIENTEMODIFICACION);	
		
		if(a.modificarCancion(a.getCancionTotales().get(0), path,nombre) == Status.OK) {
			prueba  = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		a.eliminarCuenta();
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestModificarCancionIncorrecta() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-4);
		
	
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.PENDIENTEMODIFICACION);
		a.getCancionTotales().get(0).setFechaModificacion(hoy);
		
		if(a.modificarCancion(a.getCancionTotales().get(0), path,nombre) == Status.OK) {
			prueba  = true;
		}else {
			prueba = false;
		}
		
		assertEquals(false,prueba);
		
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	
	@Test
	public void TestCrearAlbum() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-4);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		if(a.crearAlbum(2019, "escuela") != null) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestEliminarAlbum() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-4);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		Album b = a.crearAlbum(2019, "escuela");
		if(a.eliminarAlbum(b) == Status.OK) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	
	@Test
	public void TestCrearLista() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-4);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		if(a.crearLista("pitufo") != null) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestEliminarLista() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-4);
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		Lista l= a.crearLista("pitufo");
		if(a.eliminarLista(l) == Status.OK) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		File archivo = new File("datos.obj");
		archivo.delete();
	}
	
	@Test
	public void TestAnyadirAAlbum() {
		
	}
	
	@Test
	public void TestEliminarDeAlbum() {
		
	}
	
	
	@Test
	public void TestAnyadirALista() {
		
	}
	
	@Test
	public void TestEliminarDeLista() {
		
	}
	
	
	@Test
	public void TestGuardarDatos() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		a.guardarDatosGenerales();
		File archivo = new File("datos.obj");
		assertEquals(true,archivo.exists());
		archivo.delete();
	}
	
	@Test
	public void TestCargarDatos() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		a.guardarDatosGenerales();
		
		Sistema a1 = Sistema.getSistema();
		
		File archivo = new File("datos.obj");
		assertEquals(2,a1.getUsuariosTotales().size());
		archivo.delete();
	}
	
	@Test
	public void TestGestionarCancionesPendientes() throws Mp3PlayerException, IOException {
		
	}
	
	
	@Test
	public void TestDenunciarPlagio() {
		
	}
	
	@Test
	public void TestGestionarReporte() {
		
	}
	
}