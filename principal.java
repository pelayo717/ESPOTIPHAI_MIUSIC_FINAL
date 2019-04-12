package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  


public class principal {  
	
	public static void main(String[] args) throws InterruptedException {
		JFrame ventana = new Ventana();
		JPanel inicioSesion = new InicioSesion();
		JPanel registrarse = new Registrarse();

		
		final String inicioSesionString = "Iniciar Sesion";
		final String registrarseString = "Registrarse";
		ventana.add(inicioSesion, inicioSesionString);
		ventana.add(registrarse, registrarseString);

		

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800,600);
		ventana.setVisible(true);
		
		while(true) {
			Thread.sleep(10);
			CardLayout cl = (CardLayout)(ventana.getContentPane().getLayout());
		    cl.show(ventana.getContentPane(), inicioSesionString);

			
		}
	}
	
	
}  

