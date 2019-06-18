package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.sistema.*;
import vista.InicioSesion;
import vista.Ventana;

public class ControladorInicioSesion implements ActionListener{
		private InicioSesion vista;
		@SuppressWarnings("unused")
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
				EstadoInicioSesion variable = Sistema.sistema.iniciarSesion(vista.getUsuarioTextfield().getText(), String.valueOf(vista.getPasswordTextfield().getPassword()));
				if(variable == EstadoInicioSesion.CORRECTO) {
					if(Sistema.sistema.getAdministrador() == true) {
						Ventana.ventana.inicioSesion.limpiarVentana();
						Ventana.ventana.showPantallaInicioAdmin();
					}else {
						Ventana.ventana.inicioSesion.limpiarVentana();
						Ventana.ventana.showPantallaInicio();
					}
				}else if(variable == EstadoInicioSesion.BLOQUEADO) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Su usuario esta bloqueado de manera indefinida");
					Ventana.ventana.inicioSesion.limpiarVentana();
					Ventana.ventana.showInicioSesion();
				
				}else if(variable == EstadoInicioSesion.TEMPORAL) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Su usuario esta bloqueado durante 30 dias");
					Ventana.ventana.inicioSesion.limpiarVentana();
					Ventana.ventana.showInicioSesion();
				
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Ha introducido incorrectamente su nombre de usuario o contraseña");
					Ventana.ventana.inicioSesion.limpiarVentana();
					Ventana.ventana.showInicioSesion();
				}
				
			}
		}
}
