package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;  


public class principal {  
	
	public static void main(String[] args) {
		JFrame ventana = new VentanaPrincipal();
		JPanel inicioSesion = new InicioSesion();
		
		
		final String inicioSesionString = "Iniciar Sesion";
		ventana.add(inicioSesion, inicioSesionString);
		
		
		

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800,800);
		ventana.setVisible(true);
	}
	
	
}  

