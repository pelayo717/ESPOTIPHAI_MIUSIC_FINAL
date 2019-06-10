package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;

/**
 * Clase ControladorPerfil la cual describe todos las posibles
 * acciones que el usuario puede realizar dentro de vista
 */
public class ControladorPerfil implements ActionListener{
		private Perfil vista;
		private int modelo;
		
		/**
		 * Contructor de la clase ControladorPerfil donde le asignamos los valores determinados 
		 * a los atributos de la clase
	 	 * @param vista: parametro de tipo perfil que indica en que vista se esta actualmente
	 	 * @param modelo: parametro de tipo int que nos indica en que modelo nos encontramos
	 	 */
		public ControladorPerfil(Perfil vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		/**
	 	 * Funcion que determina que accion va a realizar el sistema 
		 * dependiendo de los que pulse el usuario
	 	 * @param e: parametro que indica la accion que ha realizado
		 * el usuario
	 	 */
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
