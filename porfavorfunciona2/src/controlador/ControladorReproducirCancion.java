package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import modelo.sistema.*;
import modelo.status.Status;
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
	 
		@SuppressWarnings("null")
		@Override
		public void actionPerformed(ActionEvent e) {
			if (((JButton)e.getSource()).getText() == "Iniciar Sesion") {
				Ventana.ventana.showInicioSesion();
			} else if (((JButton)e.getSource()).getText() == "Inicio") {
				if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
					Ventana.ventana.showPantallaInicioAdmin();
				}else {
					Ventana.ventana.showPantallaInicio();
				}
			} else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
				Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
			}  else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			} else if(((JButton)e.getSource()).getText() == "Buscar") {
				
				if(Ventana.ventana.reproducirCancion.getOpcion1().isSelected() == true) {
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText());
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
				}else if(Ventana.ventana.reproducirCancion.getOpcion2().isSelected() == true){
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText());
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
				}else if(Ventana.ventana.reproducirCancion.getOpcion3().isSelected() == true) {
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText());
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
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() == true) {
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
				//Ventana.ventana.reproducirCancion.limpiarBuscador();
			} else if((((JButton)e.getSource()).getText() == "Ver comentario")) {
				if(Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex() != -1) {
					Comentario[] para_ver = Ventana.ventana.reproducirCancion.comentarios;
					if(Sistema.sistema.getUsuarioActual() == null) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Autor: Desconocido" + "\n" + "Comentario: " + para_ver[Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex()].getTexto());
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Autor: " + para_ver[Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex()].getComentador().getNombreUsuario() + "\n" + "Comentario: " + para_ver[Ventana.ventana.reproducirCancion.lista_comentarios.getSelectedIndex()].getTexto());
					}
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
			} else if(((JButton)e.getSource()).getText() == "Añadir a Album") {
				
				Album[] albumes_totales = Sistema.sistema.getUsuarioActual().getAlbumes().toArray(new Album[Sistema.sistema.getUsuarioActual().getAlbumes().size()]);
				
				String[] nombre_albumes = new String[1000]; //TEMPORAL
				for(int i=0;i < albumes_totales.length; i++) {
					nombre_albumes[i] = albumes_totales[i].getTitulo();
				}
				if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == false) {
					Object opcion = JOptionPane.showInputDialog(null,"Selecciona un album", "Elegir Album",JOptionPane.QUESTION_MESSAGE,null,nombre_albumes, nombre_albumes[0]);
					for(int i=0; i < albumes_totales.length; i++) {
						if(albumes_totales[i].getTitulo().equals(opcion)) {
							if(Sistema.sistema.anyadirCancionAAlbum(albumes_totales[i], Ventana.ventana.reproducirCancion.cancion) == Status.OK) {
								JOptionPane.showMessageDialog(Ventana.ventana,"La cancion se ha añadido correctamente al album " + opcion);
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha añadido al album " + opcion);
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para añadir la cancion a un album");
				}
			} else if(((JButton)e.getSource()).getText() == "Añadir a Lista") {
				
			
			} else {
				System.out.println(vista.lista_comentarios.isSelectionEmpty());
				System.out.println(e.getSource());
			}
		}
}
