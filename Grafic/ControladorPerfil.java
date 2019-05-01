package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.event.*;

import javax.swing.*;

public class ControladorPerfil implements ActionListener{
		private Perfil vista;
		private int modelo;
		
		
		public ControladorPerfil(Perfil vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
			} else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			} else {
				System.out.println(e.getSource());
			}
		}
}
