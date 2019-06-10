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
	public Registrarse registrarse;
	public Perfil perfil;
	public CrearAlbum crearAlbum;
	public static Ventana ventana;
	Sistema sistema;
	
	public Ventana() throws Mp3PlayerException, IOException{ //CAMBIADO, MEJORADO
		
		this.sistema = Sistema.getSistema();
		System.out.print(this.sistema.registrarse("admin", "ADMIN", LocalDate.now(), "admin"));
		
		//obtener contenedor, asignar layout
		Container container = this.getContentPane();
		container.setLayout(new CardLayout());
		
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		final String reproducirCancionString = "Reproducir Cancion";
		final String reproducirAlbumString = "Reproducir Album";
		final String reproducirListaString = "Reproducir Lista";
		final String pantallaInicioString = "Pantalla Inicio";
		final String perfilString = "Perfil";
		final String crearAlbumString = "Crear Album";

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

		//Controladores
		ControladorPantallaInicio controladorPantallaInicio = new ControladorPantallaInicio(pantallaInicio, 2);
		ControladorReproducirCancion controladorReproducirCancion = new ControladorReproducirCancion(reproducirCancion, 2);
		ControladorReproducirLista controladorReproducirLista = new ControladorReproducirLista(reproducirLista, 2);
		ControladorReproducirAlbum controladorReproducirAlbum = new ControladorReproducirAlbum(reproducirAlbum, 2);
		ControladorInicioSesion controladorInicioSesion = new ControladorInicioSesion(inicioSesion, 2);
		ControladorRegistrarse controladorRegistrarse = new ControladorRegistrarse(registrarse, 2);
		ControladorPerfil controladorPerfil = new ControladorPerfil(perfil,2);
		ControladorCrearAlbum controladorCrearAlbum = new ControladorCrearAlbum(crearAlbum,2);

		// configurar la vista con el controlador
		pantallaInicio.setControlador(controladorPantallaInicio);
		reproducirCancion.setControlador(controladorReproducirCancion);
		reproducirLista.setControlador(controladorReproducirLista);
		reproducirAlbum.setControlador(controladorReproducirAlbum);
		inicioSesion.setControlador(controladorInicioSesion);
		registrarse.setControlador(controladorRegistrarse);
		perfil.setControlador(controladorPerfil);
		crearAlbum.setControlador(controladorCrearAlbum);
		
		//anyadimos pantallas al contenedor
		this.add(pantallaInicio, pantallaInicioString);
		this.add(reproducirCancion, reproducirCancionString);
		this.add(reproducirLista, reproducirListaString);
		this.add(reproducirAlbum, reproducirAlbumString);
		this.add(inicioSesion, inicioSesionString);
		this.add(registrarse, registrarseString);
		this.add(perfil, perfilString);
		this.add(crearAlbum,crearAlbumString);
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
	    	System.out.println("registrado");
	    } else {
	    	this.pantallaInicio.setUsuarioNoRegistrado();
	    	System.out.println("no registrado");

	    }
	}
	
	public void showReproducirCancion(){
		final String reproducirCancionString = "Reproducir Cancion";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirCancionString);
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.reproducirCancion.setUsuarioRegistrado();
	    } else {
	    	this.reproducirCancion.setUsuarioNoRegistrado();
	    }
	    this.reproducirCancion.setInformacion(Sistema.sistema.getCancionTotales().get(0));
	}
	
	public void showReproducirLista(){
		final String reproducirListaString = "Reproducir Lista";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirListaString);
	}
	
	public void showReproducirAlbum(){
		final String reproducirAlbumString = "Reproducir Album";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirAlbumString);
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.reproducirAlbum.setUsuarioRegistrado();
	    } else {
	    	this.reproducirAlbum.setUsuarioNoRegistrado();
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

}
