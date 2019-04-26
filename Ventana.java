package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;

import javax.swing.*;

public class Ventana extends JFrame {
	public InicioSesion ventanaInicio;
	public static Ventana ventana;
	
	public Ventana() {
		Container container = this.getContentPane();
		container.setLayout(new CardLayout());
		
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		final String reproducirCancionString = "Reproducir Cancion";

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(6);
		this.setVisible(true);
		this.setResizable(false);

		ReproducirCancion reproducirCancion = new ReproducirCancion("Por la bahia",2015,"Layo",223);
		InicioSesion inicioSesion = new InicioSesion();
		Registrarse registrarse = new Registrarse();

		
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

}
