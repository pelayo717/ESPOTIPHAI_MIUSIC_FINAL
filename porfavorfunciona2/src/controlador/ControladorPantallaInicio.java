
package controlador;


import java.awt.HeadlessException;
import java.awt.event.*;
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
import modelo.sistema.*;
import modelo.status.Status;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.PantallaInicio;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase PantallaInicio 
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorPantallaInicio implements ActionListener{
	private PantallaInicio vista;
	@SuppressWarnings("unused")
	private int modelo;

	
	/**
	 * Constructor de la clase en la que se inicializan todos los atributos de 
	 * la clase dandoles los valores necesarios
	 * @param x: vista en la que se encuentra el usuario y donde se van a realizar 
	 * todas las acciones 
	 * @param modelo: argumento de tipo entero que representa el modelo que estamos usando
	 */
	public ControladorPantallaInicio(PantallaInicio x, int modelo) {
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
	public void actionPerformed(ActionEvent e) { //CAMBIADO BASTANTE, MEJORADO
		if(((JButton)e.getSource()).getText() == "Elegir Cancion") {
			
			if(Sistema.sistema.getUsuarioActual() != null) {
				
				if(vista.getMisCanciones().length > 0) {
					Cancion[] canciones_totales = vista.getMisCanciones();
					int indice = vista.getLista_canciones().getSelectedIndex();
					vista.getLista_canciones().clearSelection();
					
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir Cancion seleccione una primero");
					}else{
						Ventana.ventana.showReproducirCancion(canciones_totales[indice]);
					}					
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus canciones");
			}
		}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
		} else if(((JButton)e.getSource()).getText() == "Elegir Album") {
			
			if(Sistema.sistema.getUsuarioActual() != null) {
				if(vista.getMisAlbumes().length > 0) { //ALGUNO HAY
					Album[] albumes_totales = vista.getMisAlbumes();
					int indice = vista.getLista_albumes().getSelectedIndex();
					vista.getLista_albumes().clearSelection();
					
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir Album seleccione uno primero");
					}else {
						Ventana.ventana.showReproducirAlbum(albumes_totales[indice]);
					}
					
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay albumes para seleccionar");
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus albumes");
			}
			
		} else if(((JButton)e.getSource()).getText() == "Elegir Lista") {
			
			if(Sistema.sistema.getUsuarioActual() != null) {
				if(vista.getMisListas().length > 0) { //ALGUNO HAY
					Lista[] listas_totales = vista.getMisListas();
					int indice = vista.getLista_listas().getSelectedIndex();
					vista.getLista_listas().clearSelection();
					if(indice == -1) { //NO HA SELECCIONADO
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Elegir Lista seleccione una primero");
					}else {
						Ventana.ventana.showReproducirLista(listas_totales[indice]);
					}
									
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas para seleccionar");
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para usar sus listas");
			}
		} else if(((JButton)e.getSource()).getText() == "Iniciar Sesion") {
			Ventana.ventana.showInicioSesion();
		} else if(((JButton)e.getSource()).getText() == "Registro") {
			Ventana.ventana.showRegistrarse();
		} else if(((JButton)e.getSource()).getText() == "Buscar") {
			
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
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado contenidos por ese nombre de autor");
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
		} else if(((JButton)e.getSource()).getText() == "Crear Cancion") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				
				 String titulo = JOptionPane.showInputDialog("Introduzca el titulo de la cancion"); 
				 if(titulo == null || titulo.equals("")) { //POR SI QUIERE CANCELAR LA SUBIDA
					 return;
				 }
				 
				 JFileChooser file=new JFileChooser();
				 int a = file.showOpenDialog(Ventana.ventana);
				 File escogido = file.getSelectedFile();
				 
	
				 try {
					 
					 Cancion c;
					if(a == JFileChooser.APPROVE_OPTION) {
						
						escogido.renameTo(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName()));
						 
						 String temporal = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName();
						 
						if((c = Sistema.sistema.crearCancion(titulo,temporal,escogido.getName())) != null) {
							Ventana.ventana.showPantallaInicio();
							JOptionPane.showMessageDialog(Ventana.ventana,"La cancion " + c.getTitulo() + " se ha creado correctamente");
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"Hubo un problema con la creacion de la cancion, puede que ya tenga una cancion con ese nombre, o con el mismo fichero mp3");
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Se cancelo la creacion de la cancion");
					}
					
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Ha escogido un fichero que no es de extension .mp3");
					//e1.printStackTrace();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear canciones");
			 }
		}else if(((JButton)e.getSource()).getText() == "Crear Album") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				JTextField titulo = new JTextField();
				JTextField anyo = new JTextField();
				Object[] message = {
				    "Titulo:", titulo,
				    "Año:", anyo
				};
				
				int option = JOptionPane.showConfirmDialog(null, message, "Crear Album", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
				    try {
						if(Sistema.sistema.crearAlbum(Integer.parseInt(anyo.getText()), titulo.getText()) != null) {
							Ventana.ventana.showPantallaInicio();
							JOptionPane.showMessageDialog(Ventana.ventana,"El album " + titulo.getText() +" se ha creado correctamente");
						}else{
					    	JOptionPane.showMessageDialog(Ventana.ventana,"Hubo un problema con la creacion del album, puede ser que ya tenga uno con el mismo nombre");
					    }
				    }catch(NumberFormatException f1) {
				    	//f1.printStackTrace();
				    	JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca correctamente los parametros del album");

				    } catch (HeadlessException e1) {
						//e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						//e1.printStackTrace();
					} catch (Mp3PlayerException e1) {
						//e1.printStackTrace();
					}
				}else {
			    	JOptionPane.showMessageDialog(Ventana.ventana,"Se cancelo la creacion del album");
				}
				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear albumes");
			}
		}else if(((JButton)e.getSource()).getText() == "Crear Lista") {
			 
			if(Sistema.sistema.getUsuarioActual()!= null) {
				String titulo = JOptionPane.showInputDialog("Introduzca el titulo de la lista");
				try {
					if(titulo != null && titulo.length() > 0) {
						if(Sistema.sistema.crearLista(titulo) != null) {
							Ventana.ventana.showPantallaInicio();
							JOptionPane.showMessageDialog(Ventana.ventana,"La lista " + titulo +" se ha creado correctamente");
						}else {
							JOptionPane.showMessageDialog(Ventana.ventana,"Hubo un problema con la creacion de la lista, puede que ya tenga alguna con el mismo nombre");
						}
					}else{
						JOptionPane.showMessageDialog(Ventana.ventana,"Se cancelo la creacion de la lista");
					}
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					//e1.printStackTrace();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear listas");
			 }
			
		}else if(((JButton)e.getSource()).getText() == "Eliminar Cancion") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				if(vista.getMisCanciones().length > 0) {
					Cancion[] canciones_totales = vista.getMisCanciones();
					int indice = vista.getLista_canciones().getSelectedIndex();
					vista.getLista_canciones().clearSelection();
					
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar Cancion seleccione una primero");
					}else{
						int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar " + canciones_totales[indice].getTitulo()  + " ?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							if(Sistema.sistema.eliminarCancion(canciones_totales[indice]) == Status.OK) {
								Ventana.ventana.showPantallaInicio();
								JOptionPane.showMessageDialog(Ventana.ventana,"La cancion se elimino correctamente");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Hubo un problema con la eliminacion de la cancion");

							}
						}
					}					
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para eliminar canciones");
			 }
			
		}else if(((JButton)e.getSource()).getText() == "Eliminar Album") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				if(vista.getMisAlbumes().length > 0) {
					Album[] albumes_totales = vista.getMisAlbumes();
					int indice = vista.getLista_albumes().getSelectedIndex();
					vista.getLista_albumes().clearSelection();
					
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar Album seleccione uno primero");
					}else{
						int a = JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar " + albumes_totales[indice].getTitulo()  + " ?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							if(Sistema.sistema.eliminarAlbum(albumes_totales[indice]) == Status.OK) {
								Ventana.ventana.showPantallaInicio();
								JOptionPane.showMessageDialog(Ventana.ventana,"El album se elimino correctamente");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Hubo un problema con la eliminacion del album");
							}
						}
						
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay albumes para seleccionar");
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para eliminar albumes");
			 }
			
		}else if(((JButton)e.getSource()).getText() == "Eliminar Lista") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				if(vista.getMisListas().length > 0) {
					Lista[]	listas_totales = vista.getMisListas();
					int indice = vista.getLista_listas().getSelectedIndex();
					vista.getLista_listas().clearSelection();
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar lista seleccione una primero");
					}else{
						int a = JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar " + listas_totales[indice].getTitulo()  + " ?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							if(Sistema.sistema.eliminarLista(listas_totales[indice]) == Status.OK) {
								Ventana.ventana.showPantallaInicio();
								JOptionPane.showMessageDialog(Ventana.ventana,"La lista se elimino correctamente");
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Hubo un problema con la eliminacion de la lista");
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas para seleccionar");
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para eliminar listas");
			 }
			
		}else {
			System.out.println(e.getSource());
		}
	}
	
	
}
