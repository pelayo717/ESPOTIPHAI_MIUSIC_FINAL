package test;

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
import modelo.sistema.EstadoInicioSesion;
import modelo.sistema.Sistema;
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
	String nombre = "hive.mp3";
	String path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
	
	
	@BeforeEach
	public static void before() throws Mp3PlayerException, IOException {
		
		File archivo = new File("datos.ser");
		archivo.delete();
		
		a = new Sistema();
		
	}
	
	@Test
	public void TestAnyadirAAlbum() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
	
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");		
		
		a.iniciarSesion("pepe", "pepe");
		
		Cancion aux_cancion = a.crearCancion("pp", path, nombre);
		
		aux_cancion.setEstado(EstadoCancion.VALIDA);
		
		Album aux_album = a.crearAlbum(2019, "escuela");

		if(a.anyadirCancionAAlbum(aux_album, aux_cancion) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestAnyadirALista() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");		
		
		a.iniciarSesion("pepe", "pepe");
		
		Cancion aux_cancion = a.crearCancion("pp", path, nombre);
		
		aux_cancion.setEstado(EstadoCancion.VALIDA);
		
		Lista aux_lista = a.crearLista("escuela");

		if(a.anyadirALista(aux_lista, aux_cancion) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestIniciarSesionUsuarioNormal() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");		
		
		if(a.iniciarSesion("pepe", "pepe") == EstadoInicioSesion.CORRECTO) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		a.eliminarCuenta();
	}
	
	
	@Test
	public void TestGestionarCancionesPendientes() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearCancion("pp", path, nombre);
		
		a.cerrarSesion();
		
		a.iniciarSesion("root1967", "ADMINISTRADOR");
		
		ArrayList<Cancion> pv = a.getCancionesPendientesValidacion();
		
		a.gestionarCancionesPendientesValidacion_Modificacion(pv.get(0),EstadoCancion.VALIDA);
		
		a.cerrarSesion();
		
		a.iniciarSesion("pepe", "pepe");
		
		ArrayList<Cancion> buscadas = a.buscadorPorTitulos("pp");
		
		assertEquals(EstadoCancion.VALIDA,buscadas.get(0).getEstado());
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestCrearCancion() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		if(a.crearCancion("pp", path, nombre) != null) {
			prueba  = true;
		}else {
			prueba = false;
		}
		
		a.eliminarCuenta();
		assertEquals(true,prueba);		
	}
	
	@Test
	public void TestEliminarCuenta() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		
		a.iniciarSesion("pepe","pepe");
		
		Usuario b = a.getUsuarioActual();
		
		a.eliminarCuenta();
		
		assertEquals(false,a.getUsuariosTotales().contains(b));
				
	}
	
	
	@Test 
	public void TestDesbloquearUsuarios() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-30);
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		
		a.iniciarSesion("pepe", "pepe");
		
		a.getUsuarioActual().setEstadoBloqueado(UsuarioBloqueado.TEMPORAL);
		
		a.getUsuarioActual().setFechaInicioBloqueado(hoy);
		
		a.cerrarSesion();
		
		a.desbloquearUsuario();
		
		a.iniciarSesion("pepe", "pepe");

		assertEquals(UsuarioBloqueado.NOBLOQUEADO,a.getUsuarioActual().getEstadoBloqueado());
				
		a.eliminarCuenta();

	}
	
	@Test
	public void TestEliminarCancionesPendienteModificacionCaducadas() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(4);
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearCancion("pp", path, nombre);
		
		a.getUsuarioActual().getCanciones().get(0).setEstado(EstadoCancion.PENDIENTEMODIFICACION);
		
		//fecha modificada haciendo que pasen mas de tres dias, asi esta cancion pendiente de modificarse es eliminada
		a.eliminarCancionPendienteModificacionCaducada(hoy);
		
		assertEquals(true,a.getUsuarioActual().getCanciones().isEmpty());
		
		a.eliminarCuenta();
				
	}
	
	
	@Test
	public void TestRegistarUsuario() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
		if(a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe") == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		assertEquals(true, prueba);
		
		a.iniciarSesion("pepe", "pepe");
		
		a.eliminarCuenta();

	}
	
	@Test
	public void TestModificarCancionCorrecta() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		a.cerrarSesion();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
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
		
	}
	
	@Test
	public void TestCrearAlbum() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		Album aux_album = a.crearAlbum(2019, "escuela");
		if(aux_album != null) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		a.eliminarCuenta();
	}
	
	@Test
	public void TestCrearLista() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
				
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		Lista aux_lista =a.crearLista("pitufo");
		if(aux_lista != null) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		a.eliminarCuenta();
	}
	
	@Test
	public void TestBuscarPorAutorDevolvemosCanciones() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.VALIDA); 
		
		ArrayList<Cancion> auxiliar = a.buscadorPorAutores_DevolvemosCanciones("pepe");
		
		assertEquals(false,auxiliar.isEmpty());
				
		a.eliminarCuenta();
	
	}
	
	
	@Test
	public void TestBuscarPorAutorDevolvemosAlbumes() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearAlbum(2019, "musica");
			
		ArrayList<Album> auxiliar = a.buscadorPorAutores_DevolvemosAlbumes("pepe");
		
		assertEquals(false,auxiliar.isEmpty());
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestEliminarAlbum() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
	
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		Album b = a.crearAlbum(2019, "escuela");
		if(a.eliminarAlbum(b) == Status.OK) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestEliminarLista() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		Lista l = a.crearLista("pitufo");
		if(a.eliminarLista(l) == Status.OK) {
			prueba = true;
		}else{
			prueba = false;
		}
		
		assertEquals(true,prueba);
		a.eliminarCuenta();
	}
	
	@Test
	public void TestBuscarTitulos() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.VALIDA); 
		
		ArrayList<Cancion> auxiliar = a.buscadorPorTitulos("pp");
		
		assertEquals(false,auxiliar.isEmpty());
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestResetearCuentasNuevoMes() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		LocalDate d = LocalDate.of(2019, 2,1);
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.getUsuarioActual().addContenidoEscuchadoSinSerPremium();
		a.addContenidoEscuchadoSinRegistrarse();
		
		
		a.resetearContadoresNuevoMes(d);
		
		assertEquals(0, a.getContenidoEscuchadoSinRegistrarse());
		assertEquals(0, a.getUsuarioActual().getContenidoEscuchadoSinSerPremium());

		a.eliminarCuenta();
	}
	
	@Test
	public void TestCerrarSesion() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();

		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		if(a.cerrarSesion() == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		assertEquals(false,a.getAdministrador());
		
		a.iniciarSesion("pepe", "pepe");
		a.eliminarCuenta();
	}
	
	@Test
	public void TestEliminarCancion() throws Mp3PlayerException, IOException {

		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");

		Cancion c = a.crearCancion("pp", path, nombre);
		
		if(a.eliminarCancion(c) == Status.OK) {
			prueba  = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestBuscarAlbumes() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearAlbum(2019, "por mi");
		
		ArrayList<Album> auxiliar = a.buscadorPorAlbumes("por mi");
		
		assertEquals(false,auxiliar.isEmpty());
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestModificarCancionIncorrecta() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-4);
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
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
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestGuardarDatos() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		a.guardarDatosGenerales();
		File archivo = new File("datos.ser");
		assertEquals(true,archivo.exists());
		
		a.iniciarSesion("pepe", "pepe");
		a.eliminarCuenta();
	}
	
	
	@Test
	public void TestIniciarSesionAdministrador() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");

		if(a.iniciarSesion("root1967", "ADMINISTRADOR") == EstadoInicioSesion.CORRECTO) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true, prueba);
		assertEquals(true,a.getAdministrador());
		
		a.cerrarSesion();
		
	}
	
	@Test
	public void TestEmpeorarCuenta() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		LocalDate hoy = LocalDate.now();
		hoy = hoy.plusDays(-30);
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.getUsuarioActual().setPremium(true);
		a.getUsuarioActual().setFechaInicioPro(hoy);
		
		a.empeorarCuentaPrincipal();
		
		assertEquals(false,a.getUsuarioActual().getPremium());
				
		a.eliminarCuenta();
	}
	
	@Test
	public void TestEliminarDeAlbum() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		Cancion aux_cancion = a.crearCancion("pp", path, nombre);
		
		aux_cancion.setEstado(EstadoCancion.VALIDA);
		
		Album aux_album = a.crearAlbum(2019, "escuela");
		
		a.anyadirCancionAAlbum(aux_album, aux_cancion);
		
		
		if(a.quitarCancionDeAlbum(aux_album, aux_cancion) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestEliminarDeLista() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
		
		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		

		Cancion aux_cancion = a.crearCancion("pp", path, nombre);
		
		aux_cancion.setEstado(EstadoCancion.VALIDA);
		
		Lista aux_lista = a.crearLista("escuela");
		
		a.anyadirALista(aux_lista, aux_cancion);
		
		if(a.quitarDeLista(aux_lista, aux_cancion) == Status.OK) {
			prueba = true;
		}else {
			prueba = false;
		}
		
		assertEquals(true,prueba);
		
		a.eliminarCuenta();
	}
	
	@Test
	public void TestDenunciarPlagioYGestionarReporte() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();

		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepo", "pepo", LocalDate.of(1967, 12, 12), "pepo");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		
		//pepe inicia sesion y crea cancion
		a.iniciarSesion("pepe", "pepe");
		Cancion aux_cancion = a.crearCancion("pp", path, nombre);
		aux_cancion.setEstado(EstadoCancion.VALIDA);
		a.cerrarSesion();
		
		//pepo inicia sesion, y la denuncia
		a.iniciarSesion("pepo", "pepo");
		a.denunciarPlagio(aux_cancion);
		a.cerrarSesion();
		

		//inicia sesion el admin y valora el reporte
		a.iniciarSesion("root1967", "ADMINISTRADOR");

		assertEquals(false,a.getReportesTotales().isEmpty());
						
		a.gestionarReportes(a.getReportesTotales().get(0), false);
		
		assertEquals(true,a.getReportesTotales().isEmpty());
		
		a.cerrarSesion();
		
		a.iniciarSesion("pepe", "pepe");
		
		a.eliminarCuenta();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void TestCargarDatos() throws Mp3PlayerException, IOException {
		
		Sistema a = Sistema.getSistema();
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		a.guardarDatosGenerales();
		
		Sistema a1 = Sistema.getSistema();
		
		a1.cargarDatosGenerales();
		
		assertEquals(3,a1.getUsuariosTotales().size()); //pepe, pepo(esta bloqueado), admin
	}
	
	@Test
	public void TestBuscarPorAutor() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();

		a.registrarse("root1967", "ADMINISTRADOR", LocalDate.of(1967, 12, 12), "ADMINISTRADOR");
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		a.crearCancion("pp", path, nombre);
		
		//La validamos asi aunque esta funcion la deberia realizar el  administrador desde su cuenta
		a.getCancionTotales().get(0).setEstado(EstadoCancion.VALIDA); 
				
		a.crearAlbum(2019, "musica");
		
		ArrayList<Contenido> auxiliar = a.buscadorPorAutores("pepe");
		
		assertEquals(false,auxiliar.isEmpty());
		
		a.eliminarCuenta();
	}
	
}