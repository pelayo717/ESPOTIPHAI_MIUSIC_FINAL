package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;

import modelo.sistema.*;
import modelo.status.Status;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.ReproducirAlbum;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase ReproducirAlbum
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorReproducirAlbum implements ActionListener{
		private ReproducirAlbum vista;
		private int modelo;
		
		/**
		 * Constructor de la clase en la que se inicializan todos los atributos de 
		 * la clase dandoles los valores necesarios
		 * @param vista: vista en la que se encuentra el usuario y donde se van a realizar 
		 * todas las acciones 
		 * @param modelo: argumento de tipo entero que representa el modelo que estamos usando
		 */
		public ControladorReproducirAlbum(ReproducirAlbum vista, int modelo) {
			this.vista = vista;
			this.modelo = modelo;
		}
	 
		/**
	 	* Funcion que asigna el controlador necesario a la accion o boton que 
	 	* el usuario ha pulsado 
	 	* @param e: accion o boton que el usuario ha pulsado y se pasa como argumento 
	 	* para asignarle el controlador correspondiente 
	 	*/
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
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else if(Ventana.ventana.reproducirAlbum.getOpcion2().isSelected() == true){
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
						}else {	
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else if(Ventana.ventana.reproducirAlbum.getOpcion3().isSelected() == true) {
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else {
					if(Ventana.ventana.reproducirAlbum.getCriterioBusqueda().getText().isEmpty() == true) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
					}
				}
				
				vista.limpiarBuscador();
				
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "Ver comentario") {
				int response;
				
				String[] options = new String[] {"Responder","Cerrar"};
				
				if(vista.getComentarioSeleccionado() != null) {
					if(vista.getComentarioSeleccionado().getComentador() == null) {
						response = JOptionPane.showOptionDialog(Ventana.ventana, "Autor: Desconocido" + "\nComentario: " + vista.getComentarioSeleccionado().getTexto() + "\nFecha: " + vista.getComentarioSeleccionado().getFecha() + "\nHora/Minuto/Segundo: " + vista.getComentarioSeleccionado().getHora() + "/" + vista.getComentarioSeleccionado().getMinuto() + "/" + vista.getComentarioSeleccionado().getSegundo(), "Comentario", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
					}else {
						response = JOptionPane.showOptionDialog(Ventana.ventana, "Autor: " + vista.getComentarioSeleccionado().getComentador().getNombreUsuario() + "\n" + "Comentario: " + vista.getComentarioSeleccionado().getTexto() + "\nFecha: " + vista.getComentarioSeleccionado().getFecha() + "\nHora/Minuto/Segundo: " + vista.getComentarioSeleccionado().getHora() + "/" + vista.getComentarioSeleccionado().getMinuto() + "/" + vista.getComentarioSeleccionado().getSegundo(), "Comentario", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
					}
					if(response == 0) {
						String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
						Comentario nuevoComentario = new Comentario(comentarioEscrito, Sistema.sistema.getUsuarioActual());
						vista.getComentarioSeleccionado().anyadirSubComentario(nuevoComentario);
						Ventana.ventana.reproducirAlbum.setTree();
					}					
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un comentario para poder verlo o comentarlo");
				}
				
				
			} else if(((JButton)e.getSource()).getText() == "Añadir Comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				Comentario nuevoComentario = new Comentario(comentarioEscrito, Sistema.sistema.getUsuarioActual());
				Ventana.ventana.reproducirAlbum.insertarComentario(nuevoComentario);
				Ventana.ventana.reproducirAlbum.setTree();
			} else if(((JButton)e.getSource()).getText() == "play") {				
				try {

					EstadoReproduccion  variable = vista.getAlbum().reproducirAlbum();
					if( variable == EstadoReproduccion.MENOR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"El album tiene contenido explicito que no esta autorizado a escuchar");
					}else if(variable == EstadoReproduccion.REPRODUCCIONES_AGOTADAS){
						JOptionPane.showMessageDialog(Ventana.ventana,"Ha utilizado todas las reproducciones que tiene posibles, podra conseguir canciones ilimitadas si se hace PRO");
					}else if(variable == EstadoReproduccion.USUARIO_SR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta categorizada de Explicita, al no ser un usuario registrado desconocemos si cumple o no la edad minima para poder escucharla");
					}else if(variable == EstadoReproduccion.OTRO) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta bloqueada o ha sido eliminada del sistema");
					}
					
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Mp3PlayerException e1) {
					e1.printStackTrace();
				}
				
				
			} else if(((JButton)e.getSource()).getText() == "pause") {
				
				vista.getAlbum().parar();
			
			} else if(((JButton)e.getSource()).getText() == "Eliminar Cancion") {
				if(vista.getMisCanciones().length > 0) {
					int indice = vista.getLista_canciones().getSelectedIndex();
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar Cancion debe seleccionar una");
					}else {
						Cancion[] entrantes = vista.getMisCanciones();
						Album entrante = vista.getAlbum();
						int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar la cancion " + entrantes[indice].getTitulo() + " del album?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							if(Sistema.sistema.quitarCancionDeAlbum(vista.getAlbum(),entrantes[indice]) == Status.OK) {
								Ventana.ventana.reproducirAlbum.actualizarCanciones();
								vista.setInformacion(entrante);
								JOptionPane.showMessageDialog(Ventana.ventana,"Se elimino correctamente");

							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Se elimino correctamente");
							}
							
						}
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"El album no contiene canciones");
				}
			} else if(((JButton)e.getSource()).getText() == "Añadir a Lista") {
			
				if(Sistema.sistema.getUsuarioActual() != null) {
					
					Lista[] listas_totales = Sistema.sistema.getUsuarioActual().getListas().toArray(new Lista[Sistema.sistema.getUsuarioActual().getListas().size()]);
					
					String[] nombre_albumes = new String[1000]; //TEMPORAL
					for(int i=0;i < listas_totales.length; i++) {
						nombre_albumes[i] = listas_totales[i].getTitulo();
					}
					
					
					if(listas_totales.length > 0) {
						Object opcion = JOptionPane.showInputDialog(null,"Selecciona un album", "Elegir Album",JOptionPane.QUESTION_MESSAGE,null,nombre_albumes, nombre_albumes[0]);
						for(int i=0; i < listas_totales.length; i++) {
							if(listas_totales[i].getTitulo().equals(opcion)) {
								if(Sistema.sistema.anyadirALista(listas_totales[i], vista.getAlbum()) == Status.OK) {
									JOptionPane.showMessageDialog(Ventana.ventana,"El album se ha añadido correctamente a la lista " + opcion);
								}else {
									JOptionPane.showMessageDialog(Ventana.ventana,"El album no se ha añadido a la lista " + opcion);
								}
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas a las que añadir este album");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para añadir el album a una lista");
				}
				
			} else {
				
				System.out.println(e.getSource());
			}
		}
}
