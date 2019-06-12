package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.EstadoCancion;
import modelo.reporte.Reporte;
import modelo.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.PantallaInicioAdmin;
import vista.Ventana;

public class ControladorPantallaInicioAdmin implements ActionListener{
	private PantallaInicioAdmin vista;
	private int modelo;

	public ControladorPantallaInicioAdmin(PantallaInicioAdmin x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
			Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
		
		} else if(((JButton)e.getSource()).getText() == "Seleccionar Cancion") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Seleccionar Cancion seleccione una primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					String[] options = {"Reproducir","Parar"};
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					int a = JOptionPane.showOptionDialog(Ventana.ventana,"Titulo: " + canciones_totales[indice].getTitulo() + " Autor: " + canciones_totales[indice].getAutor().getNombreAutor(),"Cancion Seleccionada",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
					if(a == 0){
						try {
							canciones_totales[indice].reproducirCancion();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						try {
							Sistema.sistema.pararReproductor();
						} catch (FileNotFoundException | Mp3PlayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
		
		} else if(((JButton)e.getSource()).getText() == "Valida") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Valida seleccione una primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.VALIDA);
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Explicita") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Explicita seleccione una primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.EXPLICITA);
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Pendiente") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Pendiente seleccione una primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.PENDIENTEMODIFICACION);
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Eliminada") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminada seleccione una primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.ELIMINADA);
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Buscar") {
			if(Ventana.ventana.pantallaInicioAdmin.getOpcion1().isSelected() == true) {
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText());
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda");
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else if(Ventana.ventana.pantallaInicioAdmin.getOpcion2().isSelected() == true){
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Album> retonadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText());
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda");
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else if(Ventana.ventana.pantallaInicioAdmin.getOpcion3().isSelected() == true) {
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText());
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda");
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}else {
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un criterio de busqueda y seleccione un criterio para realizar la busqueda");
					Ventana.ventana.showPantallaInicioAdmin();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
					Ventana.ventana.showPantallaInicioAdmin();
				}
			}
						
		} else if(((JButton)e.getSource()).getText() == "Aceptar") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				
				Reporte[] reportes_totales = Ventana.ventana.pantallaInicioAdmin.aReportar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Aceptar seleccione uno primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex();
					Sistema.sistema.gestionarReportes(reportes_totales[indice], true);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para valorar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Denegar") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
					
				Reporte[] reportes_totales = Ventana.ventana.pantallaInicioAdmin.aReportar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Denegar seleccione uno primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex();
					Sistema.sistema.gestionarReportes(reportes_totales[indice], false);
				}
				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para valorar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
		} else if(((JButton)e.getSource()).getText() == "Seleccionar Reporte") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				
				Reporte[] reportes_totales = Ventana.ventana.pantallaInicioAdmin.aReportar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Seleccionar Reporte seleccione uno primero");
					Ventana.ventana.showPantallaInicioAdmin();
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					JOptionPane.showMessageDialog(Ventana.ventana,"Titulo: " + reportes_totales[indice].getCancionReportada().getTitulo() + " Reportador: " + reportes_totales[indice].getUsuarioReportador().getNombreUsuario());  
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para seleccionar");
				Ventana.ventana.showPantallaInicioAdmin();
			}
			
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();		
		}else {
			System.out.println(e.getSource());
		}
	}


}
