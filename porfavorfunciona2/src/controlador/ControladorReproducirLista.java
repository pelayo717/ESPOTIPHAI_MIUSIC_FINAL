package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;
import modelo.usuario.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.ReproducirLista;
import vista.Ventana;

public class ControladorReproducirLista implements ActionListener{
		private ReproducirLista vista;
		private int modelo;
		
		
		public ControladorReproducirLista(ReproducirLista vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
			} else if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.showPantallaInicio();
			}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
				Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
			} else if(((JButton)e.getSource()).getText() == "Ver Perfil Autor") {
				for (Usuario usuario : Sistema.sistema.getUsuariosTotales()) {
					if (usuario.getNombreAutor().equals(vista.lista.getAutor()) ) {
						Ventana.ventana.showPerfil();
						Ventana.ventana.perfil.setInformacion(usuario);
					}
				}
			}  else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Buscar") {
				System.out.println("buscar");
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "Añadir Comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				Comentario nuevoComentario = new Comentario( new Date() , comentarioEscrito, Sistema.sistema.getUsuarioActual());
				Sistema.sistema.getCancionTotales().get(0).anyadirComentario(nuevoComentario);
			} else if(((JButton)e.getSource()).getText() == "Reportar") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "play") {
				try {
					Sistema.sistema.getCancionTotales().get(0).reproducirCancion();
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
			} else if(((JButton)e.getSource()).getText() == "add") {
				System.out.println("ADD BUTTON PLRESED");
			} else {
				
				System.out.println(e.getSource());
			}
		}
}
