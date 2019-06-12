
package controlador;


import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Comentario;
import modelo.contenido.Contenido;
import modelo.sistema.*;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.PantallaInicio;
import vista.PantallaPrincipal;
import vista.Ventana;

public class ControladorPantallaInicio implements ActionListener{
	private PantallaInicio vista;
	private int modelo;

public ControladorPantallaInicio(PantallaInicio x, int modelo) {
	this.modelo = modelo;
	this.vista = x;
}

@Override
	public void actionPerformed(ActionEvent e) { //CAMBIADO BASTANTE, MEJORADO
		if(((JButton)e.getSource()).getText() == "Elegir cancion") {
			if(Sistema.sistema.getUsuarioActual() != null) {
				if(Sistema.sistema.getUsuarioActual().getCanciones().size() > 0) {
					Cancion[] canciones_totales = vista.misCanciones;
					if(vista.lista_canciones.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir cancion seleccione una primero");
						Ventana.ventana.showPantallaInicio();
					}else{
						Ventana.ventana.showReproducirCancion(canciones_totales[vista.lista_canciones.getSelectedIndex()]);
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
					Ventana.ventana.showPantallaInicio();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus canciones");
				Ventana.ventana.showPantallaInicio();
			}
		}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
			Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
		} else if(((JButton)e.getSource()).getText() == "Elegir album") {
			if(Sistema.sistema.getUsuarioActual() != null) {
				if(Sistema.sistema.getUsuarioActual().getAlbumes().size() > 0) { //ALGUNO HAY
					Album[] albumes_totales = vista.misAlbumes;
					if(vista.lista_albumes.getSelectedIndex() == -1) { //NO HA SELECCIONADO
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir album seleccione uno primero");
						Ventana.ventana.showPantallaInicio();
					}else {
						Ventana.ventana.showReproducirAlbum(albumes_totales[vista.lista_albumes.getSelectedIndex()]);
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay albumes para seleccionar");
					Ventana.ventana.showPantallaInicio();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus albumes");
				Ventana.ventana.showPantallaInicio();
			}
		} else if(((JButton)e.getSource()).getText() == "Elegir lista") {
			if(Sistema.sistema.getUsuarioActual() != null) {
				if(Sistema.sistema.getUsuarioActual().getListas().size() > 0) { //ALGUNO HAY
					Lista[] listas_totales = vista.misListas;
					if(vista.lista_listas.getSelectedIndex() == -1) { //NO HA SELECCIONADO
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir lista seleccione una primero");
						Ventana.ventana.showPantallaInicio();
					}else {
						Ventana.ventana.showReproducirLista(listas_totales[vista.lista_listas.getSelectedIndex()]);
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay albumes para seleccionar");
					Ventana.ventana.showPantallaInicio();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus listas");
				Ventana.ventana.showPantallaInicio();
			}
		} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
			Ventana.ventana.showInicioSesion();
		} else if(((JButton)e.getSource()).getText() == "Registro") {
			Ventana.ventana.showRegistrarse();
		} else if(((JButton)e.getSource()).getText() == "Buscar") {
			if(Ventana.ventana.pantallaInicio.getOpcion1().isSelected() == true) {
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda");
					Ventana.ventana.showPantallaInicio();
				}
			}else if(Ventana.ventana.pantallaInicio.getOpcion2().isSelected() == true){
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Album> retonadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda");
					Ventana.ventana.showPantallaInicio();
				}
			}else if(Ventana.ventana.pantallaInicio.getOpcion3().isSelected() == true) {
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda");
					Ventana.ventana.showPantallaInicio();
				}
			}else {
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda y seleccione un criterio para realizar la busqueda");
					Ventana.ventana.showPantallaInicio();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
					Ventana.ventana.showPantallaInicio();
				}
			}
			
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();
		} else if(((JButton)e.getSource()).getText() == "Crear cancion") {
			 if(Sistema.sistema.getUsuarioActual()!= null) {
				 String titulo = JOptionPane.showInputDialog("Introduzca el titulo de la cancion:","Crear Cancion"); 
				 if(titulo == null || titulo.equals("")) { //POR SI QUIERE CANCELAR LA SUBIDA
					 return;
				 }
				 JFileChooser file=new JFileChooser();
				 file.showOpenDialog(Ventana.ventana);
				 File escogido = file.getSelectedFile();
				 
				 //escogido.renameTo(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName()));
				 //System.out.print(System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName());
				 
				 try {
					 Cancion c;
					if((c = Sistema.sistema.crearCancion(titulo, escogido.getAbsolutePath())) != null) {
						JOptionPane.showMessageDialog(Ventana.ventana,"La cancion " + c.getTitulo() + " se ha creado correctamente");
						vista.actualizarCanciones(Sistema.sistema.getUsuarioActual().getCanciones());
						Ventana.ventana.showPantallaInicio();
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha creado correctamente");
						vista.actualizarCanciones(Sistema.sistema.getUsuarioActual().getCanciones());

						Ventana.ventana.showPantallaInicio();
					}
					
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Ha escogido un fichero que no es de extension .mp3");
					e1.printStackTrace();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear canciones");
				 Ventana.ventana.showPantallaInicio();
			 }
		}else if(((JButton)e.getSource()).getText() == "Crear album") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				JTextField titulo = new JTextField();
				JTextField anyo = new JTextField();
				Object[] message = {
				    "Titulo:", titulo,
				    "Año:", anyo
				};
				
				int option = JOptionPane.showConfirmDialog(null, message, "Crear Album", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
				    Sistema.sistema.crearAlbum(Integer.parseInt(anyo.getText()), titulo.getText());
				}
				Ventana.ventana.showPantallaInicio();
				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear albumes");
				Ventana.ventana.showPantallaInicio();
			}
		}else if(((JButton)e.getSource()).getText() == "Crear lista") {
	
				//FALTA LA PARTE DE CREAR LISTA
		
		}else {
			System.out.println(e.getSource());
		}
	}
	
}
