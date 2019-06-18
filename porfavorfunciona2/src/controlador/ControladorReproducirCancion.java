package controlador;


import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

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
		@SuppressWarnings("null")
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
				Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
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
							JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
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
			} else if((((JButton)e.getSource()).getText() == "Ver comentario")) {
				
				if(vista.getLista_comentarios().getSelectedIndex() != -1) {
					Comentario[] para_ver = vista.getComentarios();
					int indice = vista.getLista_comentarios().getSelectedIndex();
					if(para_ver[indice].getComentador() == null) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Autor: Desconocido" + "\nComentario: " + para_ver[indice].getTexto() + "\nFecha: " + para_ver[indice].getFecha() + "\nHora/Minuto/Segundo: " + para_ver[indice].getHora() + "/" + para_ver[indice].getMinuto() + "/" + para_ver[indice].getSegundo());  
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"Autor: " + para_ver[indice].getComentador().getNombreUsuario() + "\n" + "Comentario: " + para_ver[indice].getTexto() + "\nFecha: " + para_ver[indice].getFecha() + "\nHora/Minuto/Segundo: " + para_ver[indice].getHora() + "/" + para_ver[indice].getMinuto() + "/" + para_ver[indice].getSegundo());
					}
					vista.getLista_comentarios().clearSelection();
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Ver comentario seleccione uno primero");
				}
				
			} else if(((JButton)e.getSource()).getText() == "Añadir comentario") {
				String comentarioEscrito = JOptionPane.showInputDialog("Escribe tu comentario");
				Comentario nuevoComentario = new Comentario(comentarioEscrito, Sistema.sistema.getUsuarioActual());
				Ventana.ventana.reproducirCancion.insertarComentario(nuevoComentario);
				Ventana.ventana.reproducirCancion.actualizarComentarios();
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
					vista.getCancion().parar();
					vista.getCancion().setMp3Player();
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
					
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Mp3PlayerException e1) {
					e1.printStackTrace();
				}
			} else if(((JButton)e.getSource()).getText() == "pause") {
				
				vista.getCancion().parar();
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
									JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha añadido al album " + opcion);
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
									JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha añadido a la lista " + opcion);
								}
							}
						}
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas a las que añadir esta cancion");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe iniciar sesion para añadir la cancion a una lista");
				}
			
			} else if(((JButton)e.getSource()).getText() == "Modificar cancion") {
				
				 JFileChooser file=new JFileChooser();
				 file.showOpenDialog(Ventana.ventana);
				 File escogido = file.getSelectedFile();
				 
				 //escogido.renameTo(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName()));
				 //System.out.print(System.getProperty("user.dir") + System.getProperty("file.separator") + "src" + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator") + escogido.getName());
				 
				 //POR EL MOMENTO SUPONEMOS QUE SOLO ES COSA DEL MP3
				 Cancion c = vista.getCancion();
				if(Sistema.sistema.modificarCancion(c, escogido.getAbsolutePath()) == Status.OK) {
					JOptionPane.showMessageDialog(Ventana.ventana,"La cancion " + c.getTitulo() + " ha sido modificada correctamente");
					Ventana.ventana.showReproducirCancion(c);
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"La cancion no se ha podido modificar");
				}
			
			} else {
				System.out.println(e.getSource());
			}
		}
}
