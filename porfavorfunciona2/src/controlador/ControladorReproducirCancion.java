package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.ReproducirCancion;
import vista.Ventana;

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
			} else if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.showPantallaInicio();
			} else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
				Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
			}  else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Buscar") {
				
				/*************************************/
				/*************************************/
				/*************************************/
				/*************************************/
				/*************************************/
				
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
				//Ventana.ventana.reproducirCancion.limpiarBuscador();
			} else if((((JButton)e.getSource()).getText() == "Ver comentario")) {
				if(Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex() != -1) {
					Comentario[] para_ver = Ventana.ventana.reproducirCancion.comentarios;
					JOptionPane.showMessageDialog(Ventana.ventana,"Autor: " + para_ver[Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex()].getComentador().getNombreUsuario() + "\n" + "Comentario: " + para_ver[Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex()].getTexto());
					Ventana.ventana.reproducirCancion.lista_comentarios.clearSelection();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Ver comentario seleccione uno primero");
				}
				
			} else if(((JButton)e.getSource()).getText() == "Añadir Comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				Comentario nuevoComentario = new Comentario( new Date() , comentarioEscrito, Sistema.sistema.getUsuarioActual());
				Ventana.ventana.reproducirCancion.insertarComentario(nuevoComentario);
				Ventana.ventana.reproducirCancion.actualizarComentarios();
			} else if(((JButton)e.getSource()).getText() == "Reportar") {
				
				/*************************************/
				/*************************************/
				/*************************************/
				/*************************************/
				/*************************************/
				/*************************************/
			
			}else if(((JButton)e.getSource()).getText() == "play") {
				try {
					Ventana.ventana.reproducirCancion.cancion.reproducirCancion();
				} catch (InterruptedException e1) {
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
				System.out.println(vista.lista_comentarios.isSelectionEmpty());
				System.out.println(e.getSource());
			}
		}
}
