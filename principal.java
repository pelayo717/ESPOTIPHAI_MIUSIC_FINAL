package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import javax.swing.*;  


public class principal {  
	
	public static void main(String[] args) {
		JFrame ventana = new Ventana();
		JPanel reproducirCancion = new ReproducirCancion("Por la bahia",2015,"Layo",223);
		
		
		final String inicioSesionString = "Iniciar Sesion";
		ventana.add(reproducirCancion, inicioSesionString);
		

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setSize(800,600);
		ventana.setVisible(true);
	}
	
	
}  
