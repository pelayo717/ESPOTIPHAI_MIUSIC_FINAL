package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.ExcesoReproduccionesExcepcion;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.Comentario;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class ControladorReproducirAlbum implements ActionListener{
		private ReproducirAlbum vista;
		private int modelo;
		
		
		public ControladorReproducirAlbum(ReproducirAlbum vista, int modelo) {
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
			} else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Atras") {
				Ventana.ventana.showReproducirCancion();
			} else if(((JButton)e.getSource()).getText() == "Buscar") {
				System.out.println("buscar");
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "Ver comentario" && !vista.lista_comentarios.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(vista,"Autor: " + vista.comentarios[vista.lista_comentarios.getSelectedIndex()].getAutor() + "\n" + "Comentario: " + vista.comentarios[vista.lista_comentarios.getSelectedIndex()].getTexto());
				vista.lista_comentarios.clearSelection();
			} else if(((JButton)e.getSource()).getText() == "Añadir Comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				Comentario nuevoComentario = new Comentario(Sistema.sistema.getUsuarioActual().getNombreUsuario(), new Date() , comentarioEscrito);
				Sistema.sistema.getCancionTotales().get(0).anyadirComentario(nuevoComentario);
			} else if(((JButton)e.getSource()).getText() == "Reportar") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "play") {
				try {
					System.out.println(Sistema.sistema.getCancionTotales());
					Sistema.sistema.reproducirCancion(Sistema.sistema.getCancionTotales().get(0));
				} catch (InterruptedException | ExcesoReproduccionesExcepcion e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else if(((JButton)e.getSource()).getText() == "pause") {
				try {
					Sistema.sistema.pararReproductor();
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				
				System.out.println(e.getSource());
			}
		}
}
