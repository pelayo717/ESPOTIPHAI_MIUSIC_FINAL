package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;

import javax.swing.*;

public class Ventana extends JFrame {
	public InicioSesion ventanaInicio;
	
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

		JPanel reproducirCancion = new ReproducirCancion("Por la bahia",2015,"Layo",223);
		JPanel inicioSesion = new InicioSesion();
		JPanel registrarse = new Registrarse();
		
		this.add(reproducirCancion, reproducirCancionString);
		this.add(inicioSesion, inicioSesionString);
		this.add(registrarse, registrarseString);
	}
	
	public void showInicioSesion(){
		final String inicioSesionString = "Iniciar Sesion";
		CardLayout cl = (CardLayout)(this.getContentPane().getLayout());
	    cl.show(this.getContentPane(), inicioSesionString);
	}

}
