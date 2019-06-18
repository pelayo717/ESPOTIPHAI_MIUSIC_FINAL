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
import modelo.status.Status;
import modelo.usuario.Usuario;
import vista.BuscadorAutores;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase BuscadorAutores 
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorBuscadorAutores implements ActionListener{


	private BuscadorAutores vista;
	@SuppressWarnings("unused")
	private int modelo;

	/**
	 * Constructor de la clase en la que se inicializan todos los atributos de 
	 * la clase dandoles los valores necesarios
	 * @param x: vista en la que se encuentra el usuario y donde se van a realizar 
	 * todas las acciones 
	 * @param modelo: argumento de tipo entero que representa el modelo que estamos usando
	 */
	public ControladorBuscadorAutores(BuscadorAutores x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}
	
	/**
	 * Funcion que asigna el controlador necesario a la accion o boton que 
	 * el usuario ha pulsado 
	 * @param e: accion o boton que el usuario ha pulsado y se pasa como argumento 
	 * para asignarle el controlador correspondiente 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(((JButton)e.getSource()).getText() == "Elegir contenido") {
			
			if(vista.getContenido().length > 0) {
				Contenido[] contenidos_totales = vista.getContenido();
				int indice = vista.getLista_contenidos().getSelectedIndex();
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir contenido seleccione uno primero");
				}else{
					
					if(contenidos_totales[indice] instanceof Cancion) {
						Ventana.ventana.showReproducirCancion((Cancion) contenidos_totales[indice]);
					}else {
						Ventana.ventana.showReproducirAlbum((Album) contenidos_totales[indice]);
					}
					
				}
				
				vista.getLista_contenidos().clearSelection();
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
			}
		}  else if(((JButton)e.getSource()).getText() == "Elegir autor") {
			
			if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == false) {
				if(vista.getLista_autores().getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir contenido seleccione uno primero");
				}else{
					String[] options = {"Seguir","Dejar Seguir"};
					int indice = vista.getLista_autores().getSelectedIndex();
					Usuario[] presentados = vista.getLosAutores();
					
					if(Sistema.sistema.getUsuarioActual().equals(presentados[indice]) == true) { //SOLO MOSTRAMOS SU INFO
						JOptionPane.showMessageDialog(Ventana.ventana,"Autor: " + presentados[indice].getNombreAutor() + "\nCanciones Totales(se incluyen canciones pendientes de validar y explicitas): " + presentados[indice].getCanciones().size() + "\nAlbumes: " + presentados[indice].getAlbumes().size() + "\nReproducciones de sus contenidos por otros usuario: " + presentados[indice].getNumeroReproducciones());  
					}else { //Son distintos
						int a = JOptionPane.showOptionDialog(Ventana.ventana,"Autor: " + presentados[indice].getNombreAutor() + "\nCanciones Totales(se incluyen canciones pendientes de validar y explicitas): " + presentados[indice].getCanciones().size() + "\nAlbumes: " + presentados[indice].getAlbumes().size() + "\nReproducciones de sus contenidos por otros usuario: " + presentados[indice].getNumeroReproducciones() ,"Usuario seleccionado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
						if(a == 0){ //SEGUIR
							if(Sistema.sistema.getUsuarioActual().follow(presentados[indice]) == Status.OK){
								JOptionPane.showMessageDialog(Ventana.ventana,"Ha comenzado a seguir a este autor");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Usted ya seguia a este autor");
							}
						}else if(a == 1){
							if(Sistema.sistema.getUsuarioActual().unfollow(presentados[indice]) == Status.OK){
								JOptionPane.showMessageDialog(Ventana.ventana,"Ha dejado de seguir a este autor");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Usted no seguia a este autor");
							}
						}
					}	
				}
				
				vista.getLista_contenidos().clearSelection();
				
			}else if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == true) {
				if(vista.getLista_autores().getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir autor seleccione uno primero");
				}else{
					int indice = vista.getLista_autores().getSelectedIndex();
					Usuario[] presentados = vista.getLosAutores();
					JOptionPane.showMessageDialog(Ventana.ventana,"Autor: " + presentados[indice].getNombreAutor() + "\nCanciones Totales(se incluyen canciones pendientes de validar y explicitas): " + presentados[indice].getCanciones().size() + "\nAlbumes: " + presentados[indice].getAlbumes().size() + "\nReproducciones de sus contenidos por otros usuario: " + presentados[indice].getNumeroReproducciones());  
					presentados = null;
				}
			}else {
				if(vista.getLista_autores().getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir autor seleccione uno primero");
				}else{
					String[] options = {"Seguir","Dejar Seguir"};
					int indice = vista.getLista_autores().getSelectedIndex();
					Usuario[] presentados = vista.getLosAutores();
					int a = JOptionPane.showOptionDialog(Ventana.ventana,"Autor: " + presentados[indice].getNombreAutor() + "\nCanciones Totales(se incluyen canciones pendientes de validar y explicitas): " + presentados[indice].getCanciones().size() + "\nAlbumes: " + presentados[indice].getAlbumes().size() + "\nReproducciones de sus contenidos por otros usuario: " + presentados[indice].getNumeroReproducciones() ,"Usuario seleccionado",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
					if(a == 0){
						JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para seguir a este usuario");
					}else if(a == 1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para dejar de seguir a este usuario");
					}
					
				}
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
			if(Ventana.ventana.buscadorAutores.getOpcion1().isSelected() == true) {
				if(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorCanciones(retornadas.toArray(new Cancion[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado canciones por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.buscadorAutores.getOpcion2().isSelected() == true){
				if(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.buscadorAutores.getOpcion3().isSelected() == true) {
				if(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else {
				if(Ventana.ventana.buscadorAutores.getCriterioBusqueda().getText().isEmpty() == true) {
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
