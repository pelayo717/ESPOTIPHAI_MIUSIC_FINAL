package controlador;


import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import modelo.sistema.*;
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
				Ventana.ventana.showPantallaInicio();
			} else if(((JButton)e.getSource()).getText() == "Cerrar Sesion") { //CAMBIADO, MEJORADO
				try {
					int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea cerra la sesion?","Alert",JOptionPane.WARNING_MESSAGE);  
					if(a == JOptionPane.YES_OPTION) {
						Sistema.sistema.cerrarSesion();
						Ventana.ventana.showPantallaInicio();
					}else {
						Ventana.ventana.showPerfil();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.toString());
				}
			} else if(((JButton)e.getSource()).getText() == "Eliminar Cuenta") {
				Sistema.sistema.eliminarCuenta();
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Hacerse PRO") {
				Ventana.ventana.showReproducirAlbum();
			} else {
				System.out.println(e.getSource());
			}
		}
}
