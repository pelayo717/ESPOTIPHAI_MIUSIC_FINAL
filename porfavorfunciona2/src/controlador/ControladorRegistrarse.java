package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.sistema.*;
import modelo.status.*;
import vista.Registrarse;
import vista.Ventana;

public class ControladorRegistrarse implements ActionListener{
		private Registrarse vista;
		@SuppressWarnings("unused")
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
								
				try {
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
					String date = vista.getBirthTextfield().getText();
					LocalDate localDate = LocalDate.parse(date, formatter);
					
					if (Sistema.sistema.registrarse(vista.getUsuarioTextfield().getText(),vista.getAuthorTextfield().getText(), localDate, String.valueOf(vista.getPasswordTextfield())) == Status.OK){
						JOptionPane.showMessageDialog(Ventana.ventana,"Su usuario ha sido registrado correctamente en la aplicacion");
						Ventana.ventana.showInicioSesion();
						Ventana.ventana.registrarse.limpiarVentana();
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Su usuario no ha sido registrado correctamente, pruebe de nuevo con otros datos");
						Ventana.ventana.showPantallaInicio();
					}
					
				}catch(DateTimeParseException e4) {
					
					//e4.printStackTrace();
					JOptionPane.showMessageDialog(Ventana.ventana,"El formato a introducir para la fecha es dd/mm/aaaa");
					Ventana.ventana.showRegistrarse();
				}
				
			}
		}
}
