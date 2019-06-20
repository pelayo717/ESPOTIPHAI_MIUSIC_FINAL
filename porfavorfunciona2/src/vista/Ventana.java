package vista;


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;
import controlador.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class Ventana extends JFrame { //99.9% esta terminado
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
	public BuscadorCanciones buscadorCanciones;
	public BuscadorAlbumes buscadorAlbumes;
	public BuscadorAutores buscadorAutores;

	
	
	public static Ventana ventana;
	public ControladorPantallaInicio controladorPantallaInicio;
	public ControladorReproducirCancion controladorReproducirCancion;
	public ControladorReproducirLista controladorReproducirLista;
	public ControladorReproducirAlbum controladorReproducirAlbum;
	public ControladorInicioSesion controladorInicioSesion;
	public ControladorRegistrarse controladorRegistrarse;
	public ControladorPerfil controladorPerfil;
	public ControladorPantallaInicioAdmin controladorPantallaInicioAdmin;
	public ControladorBuscadorCanciones controladorBuscadorCanciones;
	public ControladorBuscadorAlbumes controladorBuscadorAlbumes;
	public ControladorBuscadorAutores controladorBuscadorAutores;
	
	
	Container container;
	Sistema sistema;
	
	public Ventana() throws Mp3PlayerException, IOException{ //CAMBIADO, MEJORADO
		
		this.sistema = Sistema.getSistema();
		
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
		final String pantallaInicioAdminString = "Pantalla Inicio Admin";
		final String buscadorCancionesString = "Buscador Canciones";
		final String buscadorAlbumesString = "Buscador Albumes";
		final String buscadorAutoresString = "Buscador Autores";
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(6);
		this.setVisible(true);
		this.setResizable(false);

		//Pantallas
		this.pantallaInicio = new PantallaInicio();
		this.reproducirCancion = new ReproducirCancion();
		this.reproducirAlbum = new ReproducirAlbum();
		this.reproducirLista = new ReproducirLista();
		this.inicioSesion = new InicioSesion();
		this.registrarse = new Registrarse();
		this.perfil = new Perfil();
		this.pantallaInicioAdmin = new PantallaInicioAdmin();
		this.buscadorCanciones = new BuscadorCanciones();
		this.buscadorAlbumes = new BuscadorAlbumes();
		this.buscadorAutores = new BuscadorAutores();
		
		//Controladores
		controladorPantallaInicio = new ControladorPantallaInicio(pantallaInicio, 2);
		controladorReproducirCancion = new ControladorReproducirCancion(reproducirCancion, 2);
		controladorReproducirLista = new ControladorReproducirLista(reproducirLista, 2);
		controladorReproducirAlbum = new ControladorReproducirAlbum(reproducirAlbum, 2);
		controladorInicioSesion = new ControladorInicioSesion(inicioSesion, 2);
		controladorRegistrarse = new ControladorRegistrarse(registrarse, 2);
		controladorPerfil = new ControladorPerfil(perfil,2);
		controladorPantallaInicioAdmin = new ControladorPantallaInicioAdmin(pantallaInicioAdmin,2);
		controladorBuscadorCanciones = new ControladorBuscadorCanciones(buscadorCanciones,2);
		controladorBuscadorAlbumes = new ControladorBuscadorAlbumes(buscadorAlbumes,2);
		controladorBuscadorAutores = new ControladorBuscadorAutores(buscadorAutores,2);
		
		// configurar la vista con el controlador
		pantallaInicio.setControlador(controladorPantallaInicio);
		reproducirCancion.setControlador(controladorReproducirCancion);
		reproducirLista.setControlador(controladorReproducirLista);
		reproducirAlbum.setControlador(controladorReproducirAlbum);
		inicioSesion.setControlador(controladorInicioSesion);
		registrarse.setControlador(controladorRegistrarse);
		perfil.setControlador(controladorPerfil);
		pantallaInicioAdmin.setControlador(controladorPantallaInicioAdmin);
		buscadorCanciones.setControlador(controladorBuscadorCanciones);
		buscadorAlbumes.setControlador(controladorBuscadorAlbumes);
		buscadorAutores.setControlador(controladorBuscadorAutores);
		
		
		//anyadimos pantallas al contenedor
		this.add(pantallaInicio, pantallaInicioString);
		this.add(reproducirCancion, reproducirCancionString);
		this.add(reproducirLista, reproducirListaString);
		this.add(reproducirAlbum, reproducirAlbumString);
		this.add(inicioSesion, inicioSesionString);
		this.add(registrarse, registrarseString);
		this.add(perfil, perfilString);
		this.add(pantallaInicioAdmin,pantallaInicioAdminString);
		this.add(buscadorCanciones,buscadorCancionesString);
		this.add(buscadorAlbumes,buscadorAlbumesString);
		this.add(buscadorAutores,buscadorAutoresString);
		Ventana.ventana = this;
		if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == true) {
			this.showPantallaInicioAdmin();
		}else {
			this.showPantallaInicio();
		}
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(Ventana.ventana,  "Estas seguro de que quieres cerrar ESPOTIPHAIMUSIC?", "Cerrar ESPOTIPHAIMUSIC?", 
			            JOptionPane.YES_NO_OPTION,
			            JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
						try {
							Sistema.sistema.guardarDatosGenerales();
						} catch (IOException exception) {
							System.out.println(exception.toString());
						}
			            System.exit(0);
			        }	
			}
		});
		
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
	    
	    this.pantallaInicio.limpiarBuscador();
	}
	
	public void showReproducirCancion(Cancion c){
		
		final String reproducirCancionString = "Reproducir Cancion";
		
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirCancionString);
	   	    
	    if (Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == false) {
	    	if(Sistema.sistema.getUsuarioActual().getCanciones().contains(c) == true) {
		    	this.reproducirCancion.setUsuarioRegistradoPropia();
	    	}else {
	    		this.reproducirCancion.setUsuarioRegistradoNoPropia();
	    	}
	    }else if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == true) {
	    	this.reproducirCancion.setAdministrador();
	    } else {
	    	this.reproducirCancion.setUsuarioNoRegistradoNoPropia();
	    }
	    
		this.reproducirCancion.setInformacion(c);
	    this.reproducirCancion.limpiarBuscador();
	}
	
	public void showReproducirAlbum(Album a){
		
		final String reproducirAlbumString = "Reproducir Album";
		
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirAlbumString);
	    
	    if (Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == true) {
	    	this.reproducirAlbum.setAdministrador();
	    } else {
	    	if(Sistema.sistema.getUsuarioActual() != null) {
		    	this.reproducirAlbum.setUsuarioRegistradoPropia();
	    	}else {
	    		this.reproducirAlbum.setUsuarioRegistradoNoPropia();
	    	}
	    }
	    
	    this.reproducirAlbum.setInformacion(a);
	    this.reproducirAlbum.limpiarBuscador();
	}
	
	
	public void showReproducirLista(Lista l){
		
		final String reproducirListaString = "Reproducir Lista";
		
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirListaString);
	    
    	this.reproducirLista.setUsuarioRegistrado();
    	    
	    this.reproducirLista.setInformacion(l);
	    this.reproducirLista.limpiarBuscador();
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
	    
    	this.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
    	
	    if(Sistema.sistema.getAdministrador() == true) {
	    	this.perfil.setAdministrador();	
	    }else {
	    	if(Sistema.sistema.getUsuarioActual().getPremium() == true) {
	    		this.perfil.setUsuarioPremium();
	    	}else{
	    		this.perfil.setUsuario();
	    	}
	    }
	}

	public void showPantallaInicioAdmin() {
		final String pantallaInicioAdminString = "Pantalla Inicio Admin";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), pantallaInicioAdminString);
	    if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == true) {
	    	this.pantallaInicioAdmin.actualizarCanciones();
	    	this.pantallaInicioAdmin.actualizarReportes();
	    	this.pantallaInicioAdmin.actualizarCriterios();
	    	this.pantallaInicioAdmin.limpiarCriterios();
		    this.pantallaInicioAdmin.limpiarBuscador();
	    }
	    
	}
	
	public void showBuscadorCanciones(Cancion[] retornadas) {
		
		final String buscadorCancionesString = "Buscador Canciones";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), buscadorCancionesString);
	   
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.buscadorCanciones.setUsuarioRegistrado();
	    	this.buscadorCanciones.actualizarCanciones(retornadas);
	    	
	    } else {
	    	this.buscadorCanciones.setUsuarioNoRegistrado();
	    	this.buscadorCanciones.limpiarDatos();
	    	this.buscadorCanciones.actualizarCanciones(retornadas);
	    }
	    
	    this.buscadorCanciones.limpiarBuscador();
	}

	public void showBuscadorAutores(Contenido[] retornadas) {
		
		final String buscadorAutoresString = "Buscador Autores";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), buscadorAutoresString);
	    
	    if(Sistema.sistema.getUsuarioActual() != null) {
	    	this.buscadorAutores.setUsuarioRegistrado();
	    	this.buscadorAutores.actualizarContenido(retornadas);
	    	this.buscadorAutores.actualizarAutores(retornadas);
	    }else {
	    	
	    	this.buscadorAutores.setUsuarioNoRegistrado();
	    	this.buscadorAutores.limpiarDatos();
	    	this.buscadorAutores.actualizarContenido(retornadas);
	    	this.buscadorAutores.actualizarAutores(retornadas);
	    }
	    
		
	}

	public void showBuscadorAlbumes(Album[] retornadas) {
		
		final String buscadorAlbumesString = "Buscador Albumes";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), buscadorAlbumesString);
	    
	    if(Sistema.sistema.getUsuarioActual() != null) {
	    	this.buscadorAlbumes.setUsuarioRegistrado();
	    	this.buscadorAlbumes.actualizarAlbumes(retornadas);
	    }else {
	    	this.buscadorAlbumes.setUsuarioNoRegistrado();
	    	this.buscadorAlbumes.limpiarDatos();
	    	this.buscadorAlbumes.actualizarAlbumes(retornadas);
	    }
		
	}
	
	
}
