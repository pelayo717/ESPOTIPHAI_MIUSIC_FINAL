package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.contenido.*;
import modelo.sistema.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.InicioSesion;
import vista.Ventana;

public class ControladorInicioSesion implements ActionListener{
		private InicioSesion vista;
		private int modelo;
		
		
		public ControladorInicioSesion(InicioSesion vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) { //CAMBIADO, MEJORADO
			if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.inicioSesion.limpiarVentana();
				Ventana.ventana.showPantallaInicio();
			} else if(((JButton)e.getSource()).getText() == "Registrarse") {
				Ventana.ventana.inicioSesion.limpiarVentana();
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Sistema.sistema.iniciarSesion(vista.usuarioTextfield.getText(), String.valueOf(vista.passwordTextfield.getPassword()));
				if(Sistema.sistema.getUsuarioActual() != null) {
					Ventana.ventana.showPantallaInicio();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Ha introducido incorrectamente su nombre de usuario o contraseña");
					Ventana.ventana.inicioSesion.limpiarVentana();
					Ventana.ventana.showInicioSesion();
				}
				
			}
		}
}
