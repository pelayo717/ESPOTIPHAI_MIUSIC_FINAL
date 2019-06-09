
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
import javax.swing.JOptionPane;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Comentario;
import modelo.contenido.Contenido;
import modelo.sistema.*;
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
				Ventana.ventana.showReproducirCancion();
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus canciones");
				Ventana.ventana.showPantallaInicio();
			}
		}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
			Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
		} else if(((JButton)e.getSource()).getText() == "Elegir album") {
			if(Sistema.sistema.getUsuarioActual() != null) {
				Ventana.ventana.showReproducirAlbum();
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus albumes");
				Ventana.ventana.showPantallaInicio();
			}
		} else if(((JButton)e.getSource()).getText() == "Elegir lista") {
			if(Sistema.sistema.getUsuarioActual() != null) {
				Ventana.ventana.showReproducirLista();
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
				ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
			}else if(Ventana.ventana.pantallaInicio.getOpcion2().isSelected() == true){
				ArrayList<Album> retonadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
			}else if(Ventana.ventana.pantallaInicio.getOpcion3().isSelected() == true) {
				ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
				Ventana.ventana.showPantallaInicio();
			}
			
			System.out.println("buscar");
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();
		} else if(((JButton)e.getSource()).getText() == "Crear cancion") {
			 if(Sistema.sistema.getUsuarioActual()!= null) {
				 String titulo = JOptionPane.showInputDialog("Introduzca el titulo de la cancion:"); 
				 JFileChooser file=new JFileChooser();
				 file.showOpenDialog(Ventana.ventana);
				 File escogido = file.getSelectedFile();
				 try {
					Sistema.sistema.crearCancion(titulo, escogido.getAbsolutePath());
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Ha escogido un fichero que no es de extension .mp3");
					e1.printStackTrace();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear canciones");
				 Ventana.ventana.showPantallaInicio();
			 }
		}else if(((JButton)e.getSource()).getText() == "Crear Album") {
			
		}else if(((JButton)e.getSource()).getText() == "Crear Lista") {
			
		
		}else {
			System.out.println(e.getSource());
		}
	}
	
}
