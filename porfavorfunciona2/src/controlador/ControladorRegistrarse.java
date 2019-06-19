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

/**
 * Clase que implementa el controlador de la clase Registrarse
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorRegistrarse implements ActionListener{
		private Registrarse vista;
		@SuppressWarnings("unused")
		private int modelo;
		
		/**
	 	* Constructor de la clase en la que se inicializan todos los atributos de 
	 	* la clase dandoles los valores necesarios
	 	* @param vista: vista en la que se encuentra el usuario y donde se van a realizar 
	 	* todas las acciones 
	 	* @param modelo: argumento de tipo entero que representa el modelo que estamos usando
	 	*/
		public ControladorRegistrarse(Registrarse vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		/**
	 	* Funcion que asigna el controlador necesario a la accion o boton que 
	 	* el usuario ha pulsado 
	 	* @param e: accion o boton que el usuario ha pulsado y se pasa como argumento 
	 	* para asignarle el controlador correspondiente 
	 	*/
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
					
					if (Sistema.sistema.registrarse(vista.getUsuarioTextfield().getText(),vista.getAuthorTextfield().getText(), localDate, String.valueOf(vista.getPasswordTextfield().getPassword())) == Status.OK){
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
