
package controlador;


import java.awt.HeadlessException;
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

/**
 * Clase que implementa el controlador de la clase PantallaInicio
 */
public class ControladorPantallaInicio implements ActionListener{
	private PantallaInicio vista;
	private int modelo;

	/**
	 * Constructor de la clase, donde se inicialiazan todos los atributos de la
	 * clase asignando los valores pertinentes a los atributos
	 */
	public ControladorPantallaInicio(PantallaInicio x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}

	/**
	 * Funcion que asigna un controlador al boton que el usuario 
	 * ha pulsado
	 * @param c: argumento que indica que boton se ha pulsado
	 */ 
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
					
					Ventana.ventana.pantallaInicio.lista_canciones.clearSelection();
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
					
					Ventana.ventana.pantallaInicio.lista_albumes.clearSelection();
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
					
					Ventana.ventana.pantallaInicio.lista_listas.clearSelection();
					
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
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorCanciones(retornadas.toArray(new Cancion[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado canciones por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.pantallaInicio.getOpcion2().isSelected() == true){
								
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() != true) {
										
					ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						
						Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.pantallaInicio.getOpcion3().isSelected() == true) {
							
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() != true) {
										
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText());
										
					if(retornadas != null) { //ALGO HAY
						
						Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
						
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else {
				if(Ventana.ventana.pantallaInicio.getCriterioBusqueda().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
				}
			}
			
			vista.limpiarBuscador();

			
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();
		} else if(((JButton)e.getSource()).getText() == "Crear cancion") {
			 if(Sistema.sistema.getUsuarioActual()!= null) {
				 String titulo = JOptionPane.showInputDialog("Introduzca el titulo de la cancion"); 
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
						JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha creado");
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
				    try {
						if(Sistema.sistema.crearAlbum(Integer.parseInt(anyo.getText()), titulo.getText()) != null) {
					    	JOptionPane.showMessageDialog(Ventana.ventana,"El album " + titulo.getText() +" se ha creado correctamente");
					    }else{
					    	JOptionPane.showMessageDialog(Ventana.ventana,"El album no se ha creado");
					    }
				    }catch(NumberFormatException f1) {
				    	//f1.printStackTrace();
				    	JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca correctamente los parametros del album");

				    } catch (HeadlessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Mp3PlayerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				Ventana.ventana.showPantallaInicio();
				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear albumes");
				Ventana.ventana.showPantallaInicio();
			}
		}else if(((JButton)e.getSource()).getText() == "Crear lista") {
			 
			if(Sistema.sistema.getUsuarioActual()!= null) {
				String titulo = JOptionPane.showInputDialog("Introduzca el titulo de la lista");
				try {
					Sistema.sistema.crearLista(titulo);
				} catch (FileNotFoundException | Mp3PlayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(Ventana.ventana,"La lista " + titulo + " se ha creado correctamente");
				Ventana.ventana.showPantallaInicio();
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para crear listas");
				 Ventana.ventana.showPantallaInicio();
			 }
			
		}else if(((JButton)e.getSource()).getText() == "Eliminar cancion") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				Cancion[] canciones_totales = vista.misCanciones;
				if(canciones_totales.length > 0) {
					
					if(vista.lista_canciones.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar cancion seleccione una primero");
						Ventana.ventana.showPantallaInicio();
					}else{
						int indice = vista.lista_canciones.getSelectedIndex();
						int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar " + canciones_totales[indice].getTitulo()  + " ?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							Sistema.sistema.eliminarCancion(canciones_totales[indice]);
						}
					}
					
					Ventana.ventana.pantallaInicio.lista_canciones.clearSelection();
					Ventana.ventana.showPantallaInicio();
					
				}else {

					JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
					Ventana.ventana.showPantallaInicio();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para eliminar canciones");
				 Ventana.ventana.showPantallaInicio();
			 }
			
		}else if(((JButton)e.getSource()).getText() == "Eliminar album") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				Album[] albumes_totales = vista.misAlbumes;
				if(albumes_totales.length > 0) {
					
					if(vista.lista_albumes.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar album seleccione uno primero");
						Ventana.ventana.showPantallaInicio();
					}else{
						int indice = vista.lista_albumes.getSelectedIndex();
						int a = JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar " + albumes_totales[indice].getTitulo()  + " ?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							Sistema.sistema.eliminarAlbum(albumes_totales[indice]);
						}
						
						Ventana.ventana.pantallaInicio.lista_albumes.clearSelection();
						Ventana.ventana.showPantallaInicio();
					}
				}else {

					JOptionPane.showMessageDialog(Ventana.ventana,"No hay albumes para seleccionar");
					Ventana.ventana.showPantallaInicio();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para eliminar albumes");
				 Ventana.ventana.showPantallaInicio();
			 }
			
		}else if(((JButton)e.getSource()).getText() == "Eliminar lista") {
			
			if(Sistema.sistema.getUsuarioActual()!= null) {
				Lista[]	listas_totales = vista.misListas;
				if(listas_totales.length > 0) {
					
					if(vista.lista_listas.getSelectedIndex() == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar lista seleccione una primero");
						Ventana.ventana.showPantallaInicio();
					}else{
						int indice = vista.lista_listas.getSelectedIndex();
						int a = JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar " + listas_totales[indice].getTitulo()  + " ?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							Sistema.sistema.eliminarLista(listas_totales[indice]);
							Ventana.ventana.showPantallaInicio();
						}else {
							Ventana.ventana.showPantallaInicio();
						}
					}
				}else {

					JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas para seleccionar");
					Ventana.ventana.showPantallaInicio();
				}
			 }else {
				 JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para eliminar listas");
				 Ventana.ventana.showPantallaInicio();
			 }
			
		}else {
			System.out.println(e.getSource());
		}
	}
	
}
