package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import javax.swing.*;  


public class principal {  
	
	public static void main(String[] args) {
		JFrame ventana = new Ventana();
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		final String inicioSesionString = "Iniciar Sesion";

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setExtendedState(6);
		ventana.setVisible(true);
		ventana.setResizable(false);

		JPanel reproducirCancion = new ReproducirCancion("Por la bahia",2015,"Layo",223);
		/*JPanel inicioSesion = new PantallaPrincipal();
		JPanel registrarse = new Registrarse();
		ventana.add(inicioSesion, inicioSesionString);
		ventana.add(registrarse, registrarseString);
		
		
		while(true) {
			Thread.sleep(10);
			CardLayout cl = (CardLayout)(ventana.getContentPane().getLayout());
		    cl.show(ventana.getContentPane(), inicioSesionString);

			
		}*/
		
		
		ventana.add(reproducirCancion, inicioSesionString);
		
	}
	
	
}  

