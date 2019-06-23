package modelo.sistema;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
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
		a.eliminarCuenta();
	}
	
	@Test
	public void TestEliminarCuenta() throws Mp3PlayerException, IOException {
		Sistema a = Sistema.getSistema();
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
		
		a.registrarse("pepe", "pepe", LocalDate.of(1967, 12, 12), "pepe");
		a.iniciarSesion("pepe", "pepe");
		
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		a.crearCancion("pp", path, nombre);
		
		
		a.getUsuarioActual().getCanciones().get(0).setEstado(EstadoCancion.PENDIENTEMODIFICACION);
		
		a.eliminarCancionPendienteModificacionCaducada(hoy);
		
		assertEquals(true,a.getUsuarioActual().getCanciones().isEmpty());
		
		a.eliminarCuenta();
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
		a.eliminarCuenta();
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

		a.eliminarCuenta();
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
		a.eliminarCuenta();
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
	}
	

	
	
	
	
	
	
	
	

}