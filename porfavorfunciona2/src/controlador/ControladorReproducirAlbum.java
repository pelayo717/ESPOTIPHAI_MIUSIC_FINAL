package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.ReproducirAlbum;
import vista.Ventana;

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
			} else if (((JButton)e.getSource()).getText() == "Inicio") {
				Ventana.ventana.showPantallaInicio();
			}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
				Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
			} else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			}else if(((JButton)e.getSource()).getText() == "Buscar") {
				
				if(Ventana.ventana.reproducirAlbum.getOpcion1().isSelected() == true) {
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorCanciones(retornadas.toArray(new Cancion[retornadas.size()]));
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado canciones por ese parametro");
							if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
								Ventana.ventana.showPantallaInicioAdmin();
							}else {
								Ventana.ventana.showPantallaInicio();
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
						if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
							Ventana.ventana.showPantallaInicioAdmin();
						}else {
							Ventana.ventana.showPantallaInicio();
						}
					}
				}else if(Ventana.ventana.reproducirAlbum.getOpcion2().isSelected() == true){
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
						}else {
							
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
							if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
								Ventana.ventana.showPantallaInicioAdmin();
							}else {
								Ventana.ventana.showPantallaInicio();
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
						if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
							Ventana.ventana.showPantallaInicioAdmin();
						}else {
							Ventana.ventana.showPantallaInicio();
						}
					}
				}else if(Ventana.ventana.reproducirAlbum.getOpcion3().isSelected() == true) {
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Album[retornadas.size()]));
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
							if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
								Ventana.ventana.showPantallaInicioAdmin();
							}else {
								Ventana.ventana.showPantallaInicio();
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
						if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
							Ventana.ventana.showPantallaInicioAdmin();
						}else {
							Ventana.ventana.showPantallaInicio();
						}
					}
				}else {
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() == true) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
						if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
							Ventana.ventana.showPantallaInicioAdmin();
						}else {
							Ventana.ventana.showPantallaInicio();
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
						if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
							Ventana.ventana.showPantallaInicioAdmin();
						}else {
							Ventana.ventana.showPantallaInicio();
						}
					}
				}
				
				
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "Ver comentario" && !vista.lista_comentarios.isSelectionEmpty()) {
				Comentario[] para_ver = (Comentario[]) vista.comentarios.toArray();
				JOptionPane.showMessageDialog(vista,"Autor: " + para_ver[vista.lista_comentarios.getSelectedIndex()].getComentador() + "\n" + "Comentario: " + para_ver[vista.lista_comentarios.getSelectedIndex()].getTexto());
				vista.lista_comentarios.clearSelection();
			} else if(((JButton)e.getSource()).getText() == "Añadir Comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				Comentario nuevoComentario = new Comentario(new Date(), comentarioEscrito, Sistema.sistema.getUsuarioActual());
				Sistema.sistema.getCancionTotales().get(0).anyadirComentario(nuevoComentario);
			} else if(((JButton)e.getSource()).getText() == "Reportar") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "play") {
				try {
					Sistema.sistema.getCancionTotales().get(0).reproducirCancion();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Mp3PlayerException e1) {
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
