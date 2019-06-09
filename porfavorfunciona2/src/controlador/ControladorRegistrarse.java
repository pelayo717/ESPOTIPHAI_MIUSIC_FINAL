package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;

import modelo.sistema.*;
import modelo.status.*;
import vista.Registrarse;
import vista.Ventana;

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
				Ventana.ventana.showPantallaInicio();
				Ventana.ventana.registrarse.limpiarVentana();
			} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
				Ventana.ventana.registrarse.limpiarVentana();
			} else if(((JButton)e.getSource()).getText() == "Registrarse") {
				if (Sistema.sistema.registrarse(vista.usuarioTextfield.getText(),vista.authorTextfield.getText(), LocalDate.now(), String.valueOf(vista.passwordTextfield.getPassword())) == Status.OK){
					Ventana.ventana.showInicioSesion();
					Ventana.ventana.registrarse.limpiarVentana();
				}
			}
		}
}
