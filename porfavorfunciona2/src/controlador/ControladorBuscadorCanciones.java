package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.Lista;
import modelo.sistema.Sistema;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.BuscadorCanciones;
import vista.PantallaInicio;
import vista.Ventana;

public class ControladorBuscadorCanciones implements ActionListener {

	private BuscadorCanciones vista;
	private int modelo;


	
	public ControladorBuscadorCanciones(BuscadorCanciones x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Elegir cancion") {
			
			if(vista.lasCanciones.length > 0) {
				Cancion[] canciones_totales = vista.lasCanciones;
				if(vista.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir cancion seleccione una primero");
					Ventana.ventana.showBuscadorCanciones(canciones_totales);
				}else{
					Ventana.ventana.showReproducirCancion(canciones_totales[vista.lista_canciones.getSelectedIndex()]);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
				Ventana.ventana.showPantallaInicio();
			}
			
		}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
			Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
		} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
			Ventana.ventana.showInicioSesion();
		} else if(((JButton)e.getSource()).getText() == "Registro") {
			Ventana.ventana.showRegistrarse();
		} else if(((JButton)e.getSource()).getText() == "Inicio") {
			if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador()== true) {
				Ventana.ventana.showPantallaInicioAdmin();
			}else {
				Ventana.ventana.showPantallaInicio();
			}
		} else if(((JButton)e.getSource()).getText() == "Buscar") {
			if(Ventana.ventana.buscadorCanciones.getOpcion1().isSelected() == true) {
				if(Ventana.ventana.buscadorCanciones.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.buscadorCanciones.getCriterioBusqueda().getText());
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
			}else if(Ventana.ventana.buscadorCanciones.getOpcion2().isSelected() == true){
				if(Ventana.ventana.buscadorCanciones.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.buscadorCanciones.getCriterioBusqueda().getText());
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
			}else if(Ventana.ventana.buscadorCanciones.getOpcion3().isSelected() == true) {
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
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
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() == true) {
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
		}else {
			System.out.println(e.getSource());
		}
		
	}

}
