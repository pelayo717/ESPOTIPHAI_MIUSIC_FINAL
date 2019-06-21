package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

import modelo.contenido.Comentario;
import modelo.notificacion.Notificacion;
import modelo.sistema.*;
import modelo.status.Status;
import modelo.usuario.Usuario;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.Perfil;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase Perfil 
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorPerfil implements ActionListener{//99.9% esta terminado
		private Perfil vista;
		private int modelo;
		
		/**
	 	* Constructor de la clase en la que se inicializan todos los atributos de 
	 	* la clase dandoles los valores necesarios
	 	* @param vista: vista en la que se encuentra el usuario y donde se van a realizar 
	 	* todas las acciones 
	 	* @param modelo: argumento de tipo entero que representa el modelo que estamos usando
	 	*/
		public ControladorPerfil(Perfil vista, int modelo) {
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
				if(Sistema.sistema.getAdministrador() == false) {
					Ventana.ventana.showPantallaInicio();
				}else {
					Ventana.ventana.showPantallaInicioAdmin();
				}
			} else if(((JButton)e.getSource()).getText() == "Cerrar Sesion") { //CAMBIADO, MEJORADO
				try {
					int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea cerrar la sesion?","Alert",JOptionPane.WARNING_MESSAGE);  
					if(a == JOptionPane.YES_OPTION) {
						Sistema.sistema.cerrarSesion();
						Ventana.ventana.showPantallaInicio();
					}else {
						Ventana.ventana.showPerfil();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if(((JButton)e.getSource()).getText() == "Eliminar Cuenta") {
				int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar su cuenta?","Alert",JOptionPane.WARNING_MESSAGE);  
				if(a == JOptionPane.YES_OPTION) {
					Sistema.sistema.eliminarCuenta();
					Ventana.ventana.showPantallaInicio();
				}else {
					Ventana.ventana.showPerfil();
				}
			} else if(((JButton)e.getSource()).getText() == "Hacerse PRO") {

				String titulo = JOptionPane.showInputDialog("Introduzca su numero de tarjeta");
				if(titulo.isEmpty() == true) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe introducir su tarjeta para ascender a PRO");
				}else {
					if(Sistema.sistema.getUsuarioActual().mejorarCuentaPago(titulo) == Status.OK) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Pago efectuado correctamente");
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Pago no efectuado");
					}
				}
				
				Ventana.ventana.showPerfil();
				
			} else if(((JButton)e.getSource()).getText() == "Elegir Notificacion") {
				
				if(vista.getLasNotificaciones().length > 0) {
					
					Notificacion[] para_ver = vista.getLasNotificaciones();
					int indice = vista.getLista_notificaciones().getSelectedIndex();
					vista.getLista_notificaciones().clearSelection();
					
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir Notificacion seleccione una primero");
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Emisor: " + para_ver[indice].getEmisor().getNombreUsuario()  + "\n" + "Texto: " + para_ver[indice].getMensaje() + "\n" + "Receptor: " + para_ver[indice].getReceptor().getNombreUsuario());
					}
										
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay notificaciones para ver");
				}
			
			} else if(((JButton)e.getSource()).getText() == "Elegir Seguidor") {	
								
				if(vista.getSeguidores().length > 0) {
					
					Usuario[] seguidores = vista.getSeguidores();
					int indice = vista.getLista_seguidores().getSelectedIndex();
					vista.getLista_seguidores().clearSelection();
					
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir Seguidor seleccione uno primero");
					}else {
						String[] options = {"Seguir"};

						int a = JOptionPane.showOptionDialog(Ventana.ventana,"Autor: " + seguidores[indice].getNombreAutor() + "\nCanciones Totales(se incluyen canciones pendientes de validacion y explicitas): " + seguidores[indice].getCanciones().size() + "\nAlbumes: " + seguidores[indice].getAlbumes().size() + "\nReproducciones de sus contenidos por otros usuario: " + seguidores[indice].getNumeroReproducciones() + "\nPremium: " + seguidores[indice].getPremium(),"Usuario seleccionado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
						if(a == 0){ //SEGUIR
							if(Sistema.sistema.getUsuarioActual().follow(seguidores[indice]) == Status.OK){
								Ventana.ventana.showPerfil();
								JOptionPane.showMessageDialog(Ventana.ventana,"Ha comenzado a seguir a este autor");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Usted ya seguia de antes a este autor");
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay seguidores para ver");
				}
				
			} else if(((JButton)e.getSource()).getText() == "Elegir Seguido") {	
				
				if(vista.getSeguidos().length > 0) {
					
					Usuario[] seguidos = vista.getSeguidos();
					int indice = vista.getLista_seguidos().getSelectedIndex();
					vista.getLista_seguidos().clearSelection();
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir Seguido seleccione uno primero");
					}else {
						String[] options = {"Dejar Seguir"};

						int a = JOptionPane.showOptionDialog(Ventana.ventana,"Autor: " + seguidos[indice].getNombreAutor() + "\nCanciones Totales(se incluyen canciones pendientes de validacion y explicitas): " + seguidos[indice].getCanciones().size() + "\nAlbumes: " + seguidos[indice].getAlbumes().size() + "\nReproducciones de sus contenidos por otros usuario: " + seguidos[indice].getNumeroReproducciones() + "\nPremium: " + seguidos[indice].getPremium(),"Usuario seleccionado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
						if(a == 0){
							if(Sistema.sistema.getUsuarioActual().unfollow(seguidos[indice]) == Status.OK){
								Ventana.ventana.showPerfil();
								JOptionPane.showMessageDialog(Ventana.ventana,"Ha dejado de seguir a este autor");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Usted no seguia de antes a este autor");
							}
						}
					}
					
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay seguidores para ver");
				}
				
			} else {
				System.out.println(e.getSource());
			}
		}
}
