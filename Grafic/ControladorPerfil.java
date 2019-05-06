package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;

public class ControladorPerfil implements ActionListener{
		private Perfil vista;
		private int modelo;
		
		
		public ControladorPerfil(Perfil vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.showReproducirCancion();
			} else if(((JButton)e.getSource()).getText() == "Notificacion") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Cerrar Sesion") {
				try {
					Sistema.sistema.cerrarSesion();
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(((JButton)e.getSource()).getText() == "Eliminar Cuenta") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Hacerse PRO") {
				Ventana.ventana.showReproducirAlbum();
			} else {
				System.out.println(e.getSource());
			}
		}
}
