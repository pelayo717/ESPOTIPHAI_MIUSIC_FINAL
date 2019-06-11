package vista;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;
import controlador.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class Ventana extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InicioSesion inicioSesion;
	public ReproducirCancion reproducirCancion;
	public ReproducirAlbum reproducirAlbum;
	public ReproducirLista reproducirLista;
	public PantallaInicio pantallaInicio;
	public PantallaInicioAdmin pantallaInicioAdmin;
	public Registrarse registrarse;
	public Perfil perfil;
	public CrearAlbum crearAlbum;
	public static Ventana ventana;
	public ControladorPantallaInicio controladorPantallaInicio;
	public ControladorReproducirCancion controladorReproducirCancion;
	public ControladorReproducirLista controladorReproducirLista;
	public ControladorReproducirAlbum controladorReproducirAlbum;
	public ControladorInicioSesion controladorInicioSesion;
	public ControladorRegistrarse controladorRegistrarse;
	public ControladorPerfil controladorPerfil;
	public ControladorCrearAlbum controladorCrearAlbum;
	public ControladorPantallaInicioAdmin controladorPantallaInicioAdmin;
	Container container;
	Sistema sistema;
	
	public Ventana() throws Mp3PlayerException, IOException{ //CAMBIADO, MEJORADO
		
		this.sistema = Sistema.getSistema();
		System.out.print(this.sistema.registrarse("admin", "ADMIN", LocalDate.now(), "admin"));
		
		//obtener contenedor, asignar layout
		container = this.getContentPane();
		container.setLayout(new CardLayout());
		
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		final String reproducirCancionString = "Reproducir Cancion";
		final String reproducirAlbumString = "Reproducir Album";
		final String reproducirListaString = "Reproducir Lista";
		final String pantallaInicioString = "Pantalla Inicio";
		final String perfilString = "Perfil";
		final String crearAlbumString = "Crear Album";
		final String pantallaInicioAdminString = "Pantalla Inicio Admin";

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(6);
		this.setVisible(true);
		this.setResizable(false);

		//Pantallas
		this.pantallaInicio = new PantallaInicio();
		this.reproducirCancion = new ReproducirCancion(null);
		this.reproducirAlbum = new ReproducirAlbum(null);
		this.reproducirLista = new ReproducirLista(null);
		this.inicioSesion = new InicioSesion();
		this.registrarse = new Registrarse();
		this.perfil = new Perfil();
		this.crearAlbum = new CrearAlbum();
		this.pantallaInicioAdmin = new PantallaInicioAdmin();

		//Controladores
		controladorPantallaInicio = new ControladorPantallaInicio(pantallaInicio, 2);
		controladorReproducirCancion = new ControladorReproducirCancion(reproducirCancion, 2);
		controladorReproducirLista = new ControladorReproducirLista(reproducirLista, 2);
		controladorReproducirAlbum = new ControladorReproducirAlbum(reproducirAlbum, 2);
		controladorInicioSesion = new ControladorInicioSesion(inicioSesion, 2);
		controladorRegistrarse = new ControladorRegistrarse(registrarse, 2);
		controladorPerfil = new ControladorPerfil(perfil,2);
		controladorCrearAlbum = new ControladorCrearAlbum(crearAlbum,2);
		controladorPantallaInicioAdmin = new ControladorPantallaInicioAdmin(pantallaInicioAdmin,2);

		// configurar la vista con el controlador
		pantallaInicio.setControlador(controladorPantallaInicio);
		reproducirCancion.setControlador(controladorReproducirCancion);
		reproducirLista.setControlador(controladorReproducirLista);
		reproducirAlbum.setControlador(controladorReproducirAlbum);
		inicioSesion.setControlador(controladorInicioSesion);
		registrarse.setControlador(controladorRegistrarse);
		perfil.setControlador(controladorPerfil);
		crearAlbum.setControlador(controladorCrearAlbum);
		pantallaInicioAdmin.setControlador(controladorPantallaInicioAdmin);
		
		//anyadimos pantallas al contenedor
		this.add(pantallaInicio, pantallaInicioString);
		this.add(reproducirCancion, reproducirCancionString);
		this.add(reproducirLista, reproducirListaString);
		this.add(reproducirAlbum, reproducirAlbumString);
		this.add(inicioSesion, inicioSesionString);
		this.add(registrarse, registrarseString);
		this.add(perfil, perfilString);
		this.add(crearAlbum,crearAlbumString);
		this.add(pantallaInicioAdmin,pantallaInicioAdminString);
		Ventana.ventana = this;
		this.showPantallaInicio();
	}
	
	public void showInicioSesion(){
		
		final String inicioSesionString = "Iniciar Sesion";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), inicioSesionString);
	    
	}
	
	public void showPantallaInicio(){
		
		final String pantallaInicioString = "Pantalla Inicio";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), pantallaInicioString);
	    
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.pantallaInicio.setUsuarioRegistrado();
	    	this.pantallaInicio.actualizarCanciones(Sistema.sistema.getUsuarioActual().getCanciones());
	    	this.pantallaInicio.actualizarAlbumes(Sistema.sistema.getUsuarioActual().getAlbumes());
	    	this.pantallaInicio.actualizarListas(Sistema.sistema.getUsuarioActual().getListas());
	    } else {
	    	this.pantallaInicio.setUsuarioNoRegistrado();
	    	this.pantallaInicio.limpiarDatos();
	    }
	}
	
	public void showReproducirCancion(Cancion c){
		
		System.out.print(c.getTitulo());
		final String reproducirCancionString = "Reproducir Cancion";
		this.remove(this.reproducirCancion);
		this.reproducirCancion = new ReproducirCancion(c);
		this.reproducirCancion.setControlador(this.controladorReproducirCancion);
		this.add(reproducirCancion, reproducirCancionString);
		
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirCancionString);
	   
	    System.out.print(this.reproducirCancion.titulo_cancion.getText());
	    
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.reproducirCancion.setUsuarioRegistrado();
	    } else {
	    	this.reproducirCancion.setUsuarioNoRegistrado();
	    }
	}
	
	public void showReproducirAlbum(Album a){
		final String reproducirAlbumString = "Reproducir Album";
		this.remove(this.reproducirAlbum);
		this.reproducirAlbum = new ReproducirAlbum(a);
		this.reproducirAlbum.setControlador(this.controladorReproducirAlbum);
		this.add(reproducirAlbum,reproducirAlbumString);
		
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirAlbumString);
	    
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.reproducirAlbum.setUsuarioRegistrado();
	    } else {
	    	this.reproducirAlbum.setUsuarioNoRegistrado();
	    }
	}
	
	
	public void showReproducirLista(Lista l){
		final String reproducirListaString = "Reproducir Lista";
		this.remove(this.reproducirLista);
		this.reproducirLista = new ReproducirLista(l);
		this.reproducirLista.setControlador(this.controladorReproducirLista);
		this.add(reproducirLista, reproducirListaString);
		
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirListaString);
	    
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.reproducirLista.setUsuarioRegistrado();
	    } else {
	    	this.reproducirLista.setUsuarioNoRegistrado();
	    }
	}
	
	
	
	public void showRegistrarse(){
		final String registrarseString = "Registrarse";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), registrarseString);
	}
	
	public void showPerfil(){
		final String perfilString = "Perfil";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), perfilString);
	}
	
	public void showCrearAlbum() {
		final String crearAlbumString = "Crear Album";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), crearAlbumString);
	}

	public void showPantallaInicioAdmin() {
		final String pantallaInicioAdminString = "Pantalla Inicio Admin";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), pantallaInicioAdminString);
	    if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == true) {
	    	this.pantallaInicioAdmin.actualizarCanciones();
	    	this.pantallaInicioAdmin.actualizarReportes();
	    }
	}
	
}
