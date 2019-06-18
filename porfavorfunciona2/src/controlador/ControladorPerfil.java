package controlador;


import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import modelo.contenido.Comentario;
import modelo.notificacion.Notificacion;
import modelo.sistema.*;
import modelo.status.Status;
import vista.Perfil;
import vista.Ventana;

public class ControladorPerfil implements ActionListener{
		private Perfil vista;
		private int modelo;
		
		
		public ControladorPerfil(Perfil vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
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
				
			} else if(((JButton)e.getSource()).getText() == "Elegir notificacion") {
				
				if(vista.getLasNotificaciones().length > 0) {
					
					Notificacion[] para_ver = vista.getLasNotificaciones();
					int indice = vista.getLista_notificaciones().getSelectedIndex();
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir notificacion seleccione una primero");
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Emisor: " + para_ver[indice].getEmisor().getNombreUsuario()  + "\n" + "Texto: " + para_ver[indice].getMensaje() + "\n" + "Receptor: " + para_ver[indice].getReceptor().getNombreUsuario());
					}
					
					vista.getLista_notificaciones().clearSelection();
					
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay notificaciones para ver");
				}
			
			} else {
				System.out.println(e.getSource());
			}
		}
}
