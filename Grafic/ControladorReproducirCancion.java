package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.event.*;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.ExcesoReproduccionesExcepcion;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;

public class ControladorReproducirCancion implements ActionListener{
		private ReproducirCancion vista;
		private int modelo;
		
		
		public ControladorReproducirCancion(ReproducirCancion vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
			} else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
				Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
			}  else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Buscar") {
				System.out.println("buscar");
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
				System.out.println("limpiar buscador");
			} else if(((JButton)e.getSource()).getText() == "Ver comentario" && !vista.lista_comentarios.isSelectionEmpty()) {
		        final JFrame parent = new JFrame();
				JOptionPane.showMessageDialog(parent,"Autor: " + vista.names[vista.lista_comentarios.getSelectedIndex()].autor + "\n" + "Comentario: " + vista.names[vista.lista_comentarios.getSelectedIndex()].comentario);
				vista.lista_comentarios.clearSelection();
			} else {
				try {
					System.out.println(Sistema.sistema.getCancionTotales());
					Sistema.sistema.reproducirCancion(Sistema.sistema.getCancionTotales().get(0));
				} catch (InterruptedException | ExcesoReproduccionesExcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(e.getSource());
			}
		}
}