package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.sistema.Sistema;
import vista.BuscadorAlbumes;
import vista.PantallaInicio;
import vista.Ventana;

/**
 * Funcion que implementa el controlador de la clase BuscadorAlbumes
 * 
 */
public class ControladorBuscadorAlbumes implements ActionListener {

	private BuscadorAlbumes vista;
	private int modelo;

	/**
	 * Constructor de la clase en la que se inicializan todos 
	 * los atributos con los valores correspondientes
	 * @param x: vista que se le pasa como argumento
	 * @param modelo: argumento de tipo entero que representa el modelo
	 * que estamos usando
	 */
	public ControladorBuscadorAlbumes(BuscadorAlbumes x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}
	
	/**
	 * Funcion que asigna un controlador al boton o la accion que realiza
	 * el usuario 
	 * @param e: boton o accion que el usuario ha pulsado, al que se le va a asignar
	 * el controlador necesario para que realice la accion correctamente
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Elegir album") {		
			if(vista.losAlbumes.length > 0) {
				Album[] albumes_totales = vista.losAlbumes;
				if(vista.lista_albumes.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir album seleccione uno primero");
					Ventana.ventana.showBuscadorAlbumes(albumes_totales);
				}else{
					Ventana.ventana.showReproducirAlbum(albumes_totales[vista.lista_albumes.getSelectedIndex()]);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
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
			if(Ventana.ventana.buscadorAlbumes.getOpcion1().isSelected() == true) {
				if(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorCanciones(retornadas.toArray(new Cancion[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado canciones por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.buscadorAlbumes.getOpcion2().isSelected() == true){
				if(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.buscadorAlbumes.getOpcion3().isSelected() == true) {
				if(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else {
				if(Ventana.ventana.buscadorAlbumes.getCriterioBusqueda().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
				}
			}
			
			vista.limpiarBuscador();
			
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();
		}else {
			System.out.println(e.getSource());
		}
		
	}

}
