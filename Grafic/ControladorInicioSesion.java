package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Date;

import javax.swing.JButton;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase que implementa el controlador de la vista de InicioSesion
 */
public class ControladorInicioSesion implements ActionListener{
		private InicioSesion vista;
		private int modelo;
		
		/**
		 * Contructor de la clase ControladorInicioSesion,
	 	 * @param vista: vista que le se asigna cuando se crea el controlador
		 * @param modelo: argumento de tipo int que indica en que modelo nos
		 * encontramos
	 	 */	
		public ControladorInicioSesion(InicioSesion vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		/**
	 	 * Funcion que realiza una funcion u otra dependiendo que action pasada como 
		 * parametro se realice
	 	 * @param e: parametro que indica la action que se ha realizado en la pantalla 
	 	 */
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
				try {
					Cancion c1 = Sistema.sistema.crearCancion(new Date(), "astronauts", "Parker_-_Astronauts.mp3", false);
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		}
}
