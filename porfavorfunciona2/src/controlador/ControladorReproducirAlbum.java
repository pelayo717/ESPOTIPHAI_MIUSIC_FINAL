package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
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
		@SuppressWarnings("unused")
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
			} else if(((JButton)e.getSource()).getText() == "Registro") {
				Ventana.ventana.showRegistrarse();
			}else if(((JButton)e.getSource()).getText() == "Buscar") {
				
				if(vista.getOpcion1().isSelected() == true) {
					if(vista.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(vista.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorCanciones(retornadas.toArray(new Cancion[retornadas.size()]));
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado canciones por ese parametro");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else if(vista.getOpcion2().isSelected() == true){
					if(vista.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(vista.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
						}else {	
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else if(vista.getOpcion3().isSelected() == true) {
					if(vista.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(vista.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado contenido por ese nombre de autor");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else {
					if(vista.getCriterioBusqueda().getText().isEmpty() == true) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
					}
				}
				
				vista.limpiarBuscador();
				
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "Ver Comentario") {
				
				
				if(vista.getComentarioSeleccionado() != null) {
					
					int response;
					String[] options;		
					boolean editable = true;
					if( LocalDateTime.now().getYear() - vista.getComentarioSeleccionado().getFecha().getYear() >= 1) {
					} else if( LocalDateTime.now().getMonthValue() - vista.getComentarioSeleccionado().getFecha().getMonthValue() >= 1) {
						editable = false;
					} else if( LocalDateTime.now().getDayOfMonth() - vista.getComentarioSeleccionado().getFecha().getDayOfMonth() >= 1) {
						editable = false;
					} else if( LocalDateTime.now().getHour() - vista.getComentarioSeleccionado().getHora() >= 1) {
						editable = false;
					} else if( LocalDateTime.now().getMinute() - vista.getComentarioSeleccionado().getMinuto() >= 30) {
						editable = false;
					} 
					
					if(Sistema.sistema.getUsuarioActual() != null) {
					
						if(vista.getComentarioSeleccionado().getComentador().equals(Sistema.sistema.getUsuarioActual().getNombreUsuario()) && editable ) {
							options = new String[] {"Responder","Editar","Cerrar"};
						} else if (vista.getComentarioSeleccionado().getComentador().equals(Sistema.sistema.getUsuarioActual().getNombreUsuario()) && !editable ) {
							options = new String[] {"Responder","Eliminar","Cerrar"};
						}  else {
							options = new String[] {"Responder","Cerrar"};
						}
						if(vista.getComentarioSeleccionado().getComentador() == null) {
							response = JOptionPane.showOptionDialog(Ventana.ventana, "Autor: Desconocido" + "\nComentario: " + vista.getComentarioSeleccionado().getTexto() + "\nFecha: " + vista.getComentarioSeleccionado().getFecha() + "\nHora/Minuto/Segundo: " + vista.getComentarioSeleccionado().getHora() + "/" + vista.getComentarioSeleccionado().getMinuto() + "/" + vista.getComentarioSeleccionado().getSegundo(), "Comentario", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
						}else {
							response = JOptionPane.showOptionDialog(Ventana.ventana, "Autor: " + vista.getComentarioSeleccionado().getComentador() + "\n" + "Comentario: " + vista.getComentarioSeleccionado().getTexto() + "\nFecha: " + vista.getComentarioSeleccionado().getFecha() + "\nHora/Minuto/Segundo: " + vista.getComentarioSeleccionado().getHora() + "/" + vista.getComentarioSeleccionado().getMinuto() + "/" + vista.getComentarioSeleccionado().getSegundo(), "Comentario", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
						}
						if(response == 0) {
							String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
							if(comentarioEscrito.length() > 0) {
								Comentario nuevoComentario = new Comentario(comentarioEscrito, Sistema.sistema.getUsuarioActual().getNombreUsuario());
								vista.getComentarioSeleccionado().anyadirSubComentario(nuevoComentario);
								vista.setTree();
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Debe escribir algo para que podamos añadir el comentario");
							}
						} else if (response == 1 && options[1] == "Eliminar") {
							vista.getAlbum().eliminarComentario(vista.getComentarioSeleccionado());
							vista.setTree();
						} else if (response == 1 && options[1] == "Editar") {
							String comentarioEscrito = JOptionPane.showInputDialog("Edita tu comentario", vista.getComentarioSeleccionado().getTexto());
							vista.getComentarioSeleccionado().setTexto(comentarioEscrito);
							vista.setTree();
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana, "Autor: " + vista.getComentarioSeleccionado().getComentador() + "\n" + "Comentario: " + vista.getComentarioSeleccionado().getTexto() + "\nFecha: " + vista.getComentarioSeleccionado().getFecha() + "\nHora/Minuto/Segundo: " + vista.getComentarioSeleccionado().getHora() + "/" + vista.getComentarioSeleccionado().getMinuto() + "/" + vista.getComentarioSeleccionado().getSegundo());
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un comentario para poder verlo o comentarlo");
				}
				
				
			} else if(((JButton)e.getSource()).getText() == "Añadir Comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				if(comentarioEscrito.length() > 0) {
					Comentario nuevoComentario;
					if(Sistema.sistema.getUsuarioActual() != null) {
						nuevoComentario = new Comentario(comentarioEscrito, Sistema.sistema.getUsuarioActual().getNombreUsuario());
					}else {
						nuevoComentario = new Comentario(comentarioEscrito, "Desconocido");
					}					
					vista.insertarComentario(nuevoComentario);
					vista.setTree();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe escribir algo para que podamos añadir el comentario");
				}
				
			} else if(((JButton)e.getSource()).getText() == "play") {				
				try {
					Sistema.sistema.setReproductor(vista.getAlbum().getReproductor());
					EstadoReproduccion  variable = vista.getAlbum().reproducirAlbum();
					if( variable == EstadoReproduccion.MENOR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"El album tiene contenido explicito que no esta autorizado a escuchar");
					}else if(variable == EstadoReproduccion.REPRODUCCIONES_AGOTADAS){
						JOptionPane.showMessageDialog(Ventana.ventana,"Ha utilizado todas las reproducciones que tiene posibles, podra conseguir canciones ilimitadas si se hace PRO");
					}else if(variable == EstadoReproduccion.USUARIO_SR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta categorizada de Explicita, al no ser un usuario registrado desconocemos si cumple o no la edad minima para poder escucharla");
					}else if(variable == EstadoReproduccion.OTRO) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta bloqueada o ha sido eliminada del sistema");
					}else if(variable == EstadoReproduccion.VACIA) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Es posible que el album este vacio, o existan canciones que usted no esta autorizado a escuchar");
					}
					
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Mp3PlayerException e1) {
					e1.printStackTrace();
				}
				
				
			} else if(((JButton)e.getSource()).getText() == "pause") {
				Sistema.sistema.setReproductor(null);
				vista.getAlbum().parar();
			
			} else if(((JButton)e.getSource()).getText() == "Retirar Cancion") {
				if(vista.getMisCanciones().length > 0) {
					int indice = vista.getLista_canciones().getSelectedIndex();
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Retirar Cancion debe seleccionar una");
					}else {
						Cancion[] entrantes = vista.getMisCanciones();
						Album entrante = vista.getAlbum();
						int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea retirar la cancion " + entrantes[indice].getTitulo() + " del album?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							if(Sistema.sistema.quitarCancionDeAlbum(vista.getAlbum(),entrantes[indice]) == Status.OK) {
								vista.actualizarCanciones();
								vista.setInformacion(entrante);
								JOptionPane.showMessageDialog(Ventana.ventana,"Se retiro correctamente");

							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"No se retiro correctamente");
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
						Object opcion = JOptionPane.showInputDialog(null,"Selecciona una Lista", "Elegir Lista",JOptionPane.QUESTION_MESSAGE,null,nombre_albumes, nombre_albumes[0]);
						for(int i=0; i < listas_totales.length; i++) {
							if(listas_totales[i].getTitulo().equals(opcion)) {
								if(Sistema.sistema.anyadirALista(listas_totales[i], vista.getAlbum()) == Status.OK) {
									JOptionPane.showMessageDialog(Ventana.ventana,"El album se ha añadido correctamente a la lista " + opcion);
								}else {
									JOptionPane.showMessageDialog(Ventana.ventana,"El album ya se encuentra en la lista " + opcion);
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

