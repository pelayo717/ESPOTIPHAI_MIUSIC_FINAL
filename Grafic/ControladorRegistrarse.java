package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;

public class ControladorRegistrarse implements ActionListener{
		private Registrarse vista;
		private int modelo;
		
		
		public ControladorRegistrarse(Registrarse vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.showReproducirCancion();
			} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
			} else if(((JButton)e.getSource()).getText() == "Registrarse") {
				Sistema.sistema.registrarse(vista.usuarioTextfield.getText(),vista.authorTextfield.getText(), LocalDate.now(), vista.passwordTextfield.getText());
			}
		}
}
