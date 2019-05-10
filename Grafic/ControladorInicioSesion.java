package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Date;

import javax.swing.JButton;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class ControladorInicioSesion implements ActionListener{
		private InicioSesion vista;
		private int modelo;
		
		
		public ControladorInicioSesion(InicioSesion vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.inicioSesion.limpiarVentana();
				Ventana.ventana.showReproducirCancion();
			} else if(((JButton)e.getSource()).getText() == "Registrarse") {
				Ventana.ventana.inicioSesion.limpiarVentana();
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Sistema.sistema.iniciarSesion(vista.usuarioTextfield.getText(), String.valueOf(vista.passwordTextfield.getPassword()));
				

			}
		}
}
