package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  


public class principal {  
	
	public static void main(String[] args) throws InterruptedException {
		JFrame ventana = new Ventana();
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setExtendedState(6);
		ventana.setVisible(true);

		
		JPanel inicioSesion = new PantallaPrincipal();
		JPanel registrarse = new Registrarse();
		ventana.add(inicioSesion, inicioSesionString);
		ventana.add(registrarse, registrarseString);
		
		
		while(true) {
			Thread.sleep(10);
			CardLayout cl = (CardLayout)(ventana.getContentPane().getLayout());
		    cl.show(ventana.getContentPane(), inicioSesionString);

			
		}
	}
	
	
}  

