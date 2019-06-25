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
				vista.limpiarVentana();
				Ventana.ventana.showPantallaInicio();
			} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				vista.limpiarVentana();
				Ventana.ventana.showInicioSesion();
			} else if(((JButton)e.getSource()).getText() == "Registrarse") {
					
				if(vista.getUsuarioTextfield().getText().length() > 0 && vista.getAuthorTextfield().getText().length()> 0 &&  vista.getBirthTextfield().getText().length() > 0 && vista.getPasswordTextfield().getPassword().length > 0) {
					try {
						
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
						String date = vista.getBirthTextfield().getText(); 
						LocalDate localDate = LocalDate.parse(date, formatter);
						
						if (Sistema.sistema.registrarse(vista.getUsuarioTextfield().getText(),vista.getAuthorTextfield().getText(), localDate, String.valueOf(vista.getPasswordTextfield().getPassword())) == Status.OK){
							JOptionPane.showMessageDialog(Ventana.ventana,"Su usuario ha sido registrado correctamente en la aplicacion");
							vista.limpiarVentana();
							Ventana.ventana.showInicioSesion();
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"Su usuario no ha sido registrado correctamente, pruebe de nuevo con otros datos");
							vista.limpiarVentana();
						}
						
					}catch(DateTimeParseException e4) {
						JOptionPane.showMessageDialog(Ventana.ventana,"El formato a introducir para la fecha es dd/mm/aaaa");
						vista.limpiarVentana();
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Rellene todos los datos para poder registrarle como usuario");
				}
			}
		}
}
