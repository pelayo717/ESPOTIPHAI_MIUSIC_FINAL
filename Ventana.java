package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;

import javax.swing.*;

public class Ventana extends JFrame {
	public InicioSesion inicioSesion;
	public ReproducirCancion reproducirCancion;
	public Registrarse registrarse;
	public Perfil perfil;
	public static Ventana ventana;
	
	public Ventana() {
		Container container = this.getContentPane();
		container.setLayout(new CardLayout());
		
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		final String reproducirCancionString = "Reproducir Cancion";
		final String perfilString = "Perfil";

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(6);
		this.setVisible(true);
		this.setResizable(false);

		this.reproducirCancion = new ReproducirCancion("Por la bahia",2015,"Layo",223);
		this.inicioSesion = new InicioSesion();
		this.registrarse = new Registrarse();
		this.perfil = new Perfil();

		
		ControladorReproducirCancion controladorReproducirCancion = new ControladorReproducirCancion(reproducirCancion, 2);
		ControladorInicioSesion controladorInicioSesion = new ControladorInicioSesion(inicioSesion, 2);
		ControladorRegistrarse controladorRegistrarse = new ControladorRegistrarse(registrarse, 2);

		// configurar la vista con el controlador
		reproducirCancion.setControlador(controladorReproducirCancion);
		inicioSesion.setControlador(controladorInicioSesion);
		registrarse.setControlador(controladorRegistrarse);
		
		this.add(reproducirCancion, reproducirCancionString);
		this.add(inicioSesion, inicioSesionString);
		this.add(registrarse, registrarseString);
		this.add(perfil, perfilString);
		Ventana.ventana = this;
	}
	
	public void showInicioSesion(){
		final String inicioSesionString = "Iniciar Sesion";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), inicioSesionString);
	}
	
	public void showReproducirCancion(){
		final String reproducirCancionString = "Reproducir Cancion";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), reproducirCancionString);
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
