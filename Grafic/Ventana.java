package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class Ventana extends JFrame {
	public InicioSesion inicioSesion;
	public ReproducirCancion reproducirCancion;
	public ReproducirAlbum reproducirAlbum;
	public ReproducirLista reproducirLista;
	public PantallaInicio pantallaInicio;
	public Registrarse registrarse;
	public Perfil perfil;
	public static Ventana ventana;
	Sistema sistema;
	
	public Ventana() throws Mp3PlayerException, IOException{
		this.sistema = Sistema.getSistema();

		Container container = this.getContentPane();
		container.setLayout(new CardLayout());
		
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		final String reproducirCancionString = "Reproducir Cancion";
		final String reproducirAlbumString = "Reproducir Album";
		final String reproducirListaString = "Reproducir Lista";
		final String pantallaInicioString = "Pantalla Inicio";
		final String perfilString = "Perfil";

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(6);
		this.setVisible(true);
		this.setResizable(false);

		this.pantallaInicio = new PantallaInicio();
		this.reproducirCancion = new ReproducirCancion(null);
		this.reproducirAlbum = new ReproducirAlbum(null);
		this.reproducirLista = new ReproducirLista(null);
		this.inicioSesion = new InicioSesion();
		this.registrarse = new Registrarse();
		this.perfil = new Perfil();

		
		ControladorPantallaInicio controladorPantallaInicio = new ControladorPantallaInicio(pantallaInicio, 2);
		ControladorReproducirCancion controladorReproducirCancion = new ControladorReproducirCancion(reproducirCancion, 2);
		ControladorReproducirLista controladorReproducirLista = new ControladorReproducirLista(reproducirLista, 2);
		ControladorReproducirAlbum controladorReproducirAlbum = new ControladorReproducirAlbum(reproducirAlbum, 2);
		ControladorInicioSesion controladorInicioSesion = new ControladorInicioSesion(inicioSesion, 2);
		ControladorRegistrarse controladorRegistrarse = new ControladorRegistrarse(registrarse, 2);
		ControladorPerfil controladorPerfil = new ControladorPerfil(perfil,2);

		// configurar la vista con el controlador
		pantallaInicio.setControlador(controladorPantallaInicio);
		reproducirCancion.setControlador(controladorReproducirCancion);
		reproducirLista.setControlador(controladorReproducirLista);
		reproducirAlbum.setControlador(controladorReproducirAlbum);
		inicioSesion.setControlador(controladorInicioSesion);
		registrarse.setControlador(controladorRegistrarse);
		perfil.setControlador(controladorPerfil);
		
		this.add(pantallaInicio, pantallaInicioString);
		this.add(reproducirCancion, reproducirCancionString);
		this.add(reproducirLista, reproducirListaString);
		this.add(reproducirAlbum, reproducirAlbumString);
		this.add(inicioSesion, inicioSesionString);
		this.add(registrarse, registrarseString);
		this.add(perfil, perfilString);
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
	    } else {
	    	this.pantallaInicio.setUsuarioNoRegistrado();
	    }
	}
	
	public void showReproducirCancion(Cancion cancion){
		final String reproducirCancionString = "Reproducir Cancion";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirCancionString);
	    if (Sistema.sistema.getUsuarioActual() != null) {
	    	this.reproducirCancion.setUsuarioRegistrado();
	    } else {
	    	this.reproducirCancion.setUsuarioNoRegistrado();
	    }
	    this.reproducirCancion.setInformacion(cancion);
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

}
