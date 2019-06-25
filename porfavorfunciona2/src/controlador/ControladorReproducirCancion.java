package controlador;


import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.*;

import modelo.sistema.*;
import modelo.status.Status;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.ReproducirCancion;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase ReproducirCancion
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorReproducirCancion implements ActionListener{
		private ReproducirCancion vista;
		@SuppressWarnings("unused")
		private int modelo;
		
		/**
		 * Constructor de la clase en la que se inicializan todos los atributos de 
		 * la clase dandoles los valores necesarios
		 * @param vista: vista en la que se encuentra el usuario y donde se van a realizar 
		 * todas las acciones 
		 * @param modelo: argumento de tipo entero que representa el modelo que estamos usando
		 */
		public ControladorReproducirCancion(ReproducirCancion vista, int modelo) {
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
			} else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
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
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else if(Ventana.ventana.reproducirCancion.getOpcion2().isSelected() == true){
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
						}else {
							
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else if(Ventana.ventana.reproducirCancion.getOpcion3().isSelected() == true) {
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() != true) {
						ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText());
						if(retornadas != null) { //ALGO HAY
							Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado contenido por ese nombre de autor");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
					}
				}else {
					if(Ventana.ventana.reproducirCancion.getCriterioBusqueda().getText().isEmpty() == true) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
					}
				}
				
			vista.limpiarBuscador();
				
	
			} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
				vista.limpiarBuscador();
			} else if((((JButton)e.getSource()).getText() == "Ver Comentario")) {
				 
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
							vista.getCancion().eliminarComentario(vista.getComentarioSeleccionado());
							vista.setTree();
						}  else if (response == 1 && options[1] == "Editar") {
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
					Ventana.ventana.reproducirCancion.insertarComentario(nuevoComentario);
					Ventana.ventana.reproducirCancion.setTree();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe escribir algo para que podamos añadir el comentario");
				}
			} else if(((JButton)e.getSource()).getText() == "Reportar") {
				
				if(Sistema.sistema.getUsuarioActual() != null) {
					
					int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea reportar por plagio?","Alert",JOptionPane.WARNING_MESSAGE);  
					if(a == JOptionPane.YES_OPTION) {
						if(Sistema.sistema.denunciarPlagio(vista.getCancion()) == Status.OK) {
							JOptionPane.showMessageDialog(Ventana.ventana,"El reporte fue realizado correctamente");
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"El reporte no se pudo realizar");
						}
					}
					
				}else{
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para poder realizar un reporte");
				}
			
			}else if(((JButton)e.getSource()).getText() == "play") {
				try {
					EstadoReproduccion  variable = vista.getCancion().reproducirCancion();
					if( variable == EstadoReproduccion.MENOR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta categorizada de Explicita y usted no tiene la edad suficiente para escucharla");
					}else if(variable == EstadoReproduccion.REPRODUCCIONES_AGOTADAS){
						JOptionPane.showMessageDialog(Ventana.ventana,"Ha utilizado todas las reproducciones que tiene posibles, podra conseguir canciones ilimitadas si se hace PRO");
					}else if(variable == EstadoReproduccion.USUARIO_SR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta categorizada de Explicita, al no ser un usuario registrado desconocemos si cumple o no la edad minima para poder escucharla");
					}else if(variable == EstadoReproduccion.OTRO) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta bloqueada o ha sido eliminada del sistema");
					}
					
					//Sistema.sistema.setReproductor(vista.getCancion().getReproductor());

				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Mp3PlayerException e1) {
					e1.printStackTrace();
				}
			} else if(((JButton)e.getSource()).getText() == "pause") {
				vista.getCancion().parar();
				Sistema.sistema.setReproductor(null);
			} else if(((JButton)e.getSource()).getText() == "Añadir a Album") {
				
				if(Sistema.sistema.getUsuarioActual() != null) {
					
					Album[] albumes_totales = Sistema.sistema.getUsuarioActual().getAlbumes().toArray(new Album[Sistema.sistema.getUsuarioActual().getAlbumes().size()]);
					
					String[] nombre_albumes = new String[1000]; //TEMPORAL
					for(int i=0;i < albumes_totales.length; i++) {
						nombre_albumes[i] = albumes_totales[i].getTitulo();
					}
					
					
					if(albumes_totales.length > 0) {
						Object opcion = JOptionPane.showInputDialog(null,"Selecciona un album", "Elegir Album",JOptionPane.QUESTION_MESSAGE,null,nombre_albumes, nombre_albumes[0]);
						for(int i=0; i < albumes_totales.length; i++) {
							if(albumes_totales[i].getTitulo().equals(opcion)) {
								if(Sistema.sistema.anyadirCancionAAlbum(albumes_totales[i], vista.getCancion()) == Status.OK) {
									JOptionPane.showMessageDialog(Ventana.ventana,"La cancion se ha añadido correctamente al album " + opcion);
								}else {
									JOptionPane.showMessageDialog(Ventana.ventana,"La cancion o esta pendiente de ser validada o ya se encuentra en el album " + opcion);
								}
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No hay albumes a los que añadir esta cancion");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para añadir la cancion a un album");
				}
				
			} else if(((JButton)e.getSource()).getText() == "Añadir a Lista") {
				
				
				if(Sistema.sistema.getUsuarioActual() != null) {
					
					Lista[] listas_totales = Sistema.sistema.getUsuarioActual().getListas().toArray(new Lista[Sistema.sistema.getUsuarioActual().getListas().size()]);
					String[] nombre_listas = new String[1000]; //TEMPORAL
					for(int i=0;i < listas_totales.length; i++) {
						nombre_listas[i] = listas_totales[i].getTitulo();
					}
					
					if(listas_totales.length > 0) {
						Object opcion = JOptionPane.showInputDialog(null,"Selecciona una lista", "Elegir Lista",JOptionPane.QUESTION_MESSAGE,null,nombre_listas, nombre_listas[0]);
						for(int i=0; i < listas_totales.length; i++) {
							if(listas_totales[i].getTitulo().equals(opcion)) {
								if(Sistema.sistema.anyadirALista(listas_totales[i], vista.getCancion()) == Status.OK) {
									JOptionPane.showMessageDialog(Ventana.ventana,"La cancion se ha añadido correctamente a la lista " + opcion);
								}else {
									JOptionPane.showMessageDialog(Ventana.ventana,"La cancion o esta pendiente de ser validada o ya se encuentra en la lista " + opcion);
								}
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas a las que añadir esta cancion");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para añadir la cancion a una lista");
				}
			
			} else if(((JButton)e.getSource()).getText() == "Modificar Cancion") {		
				
				//BORRAMOS EL ANTIGUO ARCHIVO PARA SUBIR EL NUEVO

			    File fichero=new File(vista.getCancion().getNombreMP3());
			    
			    if(fichero.delete() == true) {
			    	
			    	JFileChooser file=new JFileChooser();
					file.showOpenDialog(Ventana.ventana);
					File escogido = file.getSelectedFile();
					 
					
					
					//POR EL MOMENTO SUPONEMOS QUE SOLO ES COSA DEL MP3
					Cancion c = vista.getCancion();
					
					try {
						
						escogido.renameTo(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName()));

						String temporal = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName();

						if(Sistema.sistema.modificarCancion(c, temporal,escogido.getName()) == Status.OK) {
							JOptionPane.showMessageDialog(Ventana.ventana,"La cancion " + c.getTitulo() + " ha sido modificada correctamente");
							Ventana.ventana.showReproducirCancion(c);
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha podido modificar");
						}
					} catch (HeadlessException | FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	
			    }else {
			    	
			    }
				
			} else {
				System.out.println(e.getSource());
			}
		}
}
