package ESPOTIPHAI_MIUSIC_FINAL;


import java.awt.event.*;

import javax.swing.*;

public class ControladorPerfil implements ActionListener{
		private ReproducirCancion vista;
		private int modelo;
		
		
		public ControladorPerfil(ReproducirCancion vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
			} else if(((JButton)e.getSource()).getText() == "Registro") {
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
				System.out.println(e.getSource());
			}
		}
}
