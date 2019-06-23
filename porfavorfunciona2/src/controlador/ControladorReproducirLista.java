package controlador;


import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;

import modelo.sistema.*;
import modelo.status.Status;
import modelo.contenido.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.ReproducirLista;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase ReproducirLista
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorReproducirLista implements ActionListener{
		private ReproducirLista vista;
		@SuppressWarnings("unused")
		private int modelo;
		
		/**
		 * Constructor de la clase en la que se inicializan todos los atributos de 
		 * la clase dandoles los valores necesarios
		 * @param vista: vista en la que se encuentra el usuario y donde se van a realizar 
		 * todas las acciones 
		 * @param modelo: argumento de tipo entero que representa el modelo que estamos usando
		 */
		public ControladorReproducirLista(ReproducirLista vista, int modelo) {
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
				Ventana.ventana.showPantallaInicio();
			}  else if(((JButton)e.getSource()).getText() == "Ver Perfil") {
				Ventana.ventana.showPerfil();
			}  else if(((JButton)e.getSource()).getText() == "Registro") {
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
			} else if(((JButton)e.getSource()).getText() == "Reportar") {
				vista.limpiarBuscador();
			} else if(((JButton)e.getSource()).getText() == "play") {
				try {
					vista.getLista().parar();
					vista.getLista().setMp3Player();
					EstadoReproduccion  variable = vista.getLista().reproducirLista();
					if( variable == EstadoReproduccion.MENOR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"El album tiene contenido explicito que no esta autorizado a escuchar");
					}else if(variable == EstadoReproduccion.REPRODUCCIONES_AGOTADAS){
						JOptionPane.showMessageDialog(Ventana.ventana,"Ha utilizado todas las reproducciones que tiene posibles, podra conseguir canciones ilimitadas si se hace PRO");
					}else if(variable == EstadoReproduccion.USUARIO_SR) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta categorizada de Explicita, al no ser un usuario registrado desconocemos si cumple o no la edad minima para poder escucharla");
					}else if(variable == EstadoReproduccion.OTRO) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Esta cancion esta bloqueada o ha sido eliminada del sistema");
					}else if(variable == EstadoReproduccion.VACIA) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Es posible que la lista este vacia, o exista contenido que usted no esta autorizado a escuchar");
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (Mp3PlayerException e1) {
					e1.printStackTrace();
				}
			} else if(((JButton)e.getSource()).getText() == "pause") {
				vista.getLista().parar();
			} else if(((JButton)e.getSource()).getText() == "Retirar Contenido") {

				if(vista.getContenido().length > 0) {
					Contenido[] temporal = vista.getContenido();
					int indice = vista.getLista_contenido().getSelectedIndex();
					if(indice == -1) {
						JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminar Contenido debe seleccionar uno");
					}else {
						int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea eliminar el contenido " + temporal[indice].getTitulo() + " de la lista?","Alert",JOptionPane.WARNING_MESSAGE);  
						if(a == JOptionPane.YES_OPTION) {
							Lista l = vista.getLista();
							if(Sistema.sistema.quitarDeLista(l, temporal[indice])== Status.OK) {
								vista.actualizarContenido();
								vista.setInformacion(l);
								JOptionPane.showMessageDialog(Ventana.ventana,"Se elimino correctamente");

							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"Se elimino correctamente");
							}
							
						}
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"La lista no contiene canciones");
				}
				
			} else if(((JButton)e.getSource()).getText() == "Añadir a Lista") {
				
				int contador=0;
				Lista[] listas_totales = Sistema.sistema.getUsuarioActual().getListas().toArray(new Lista[Sistema.sistema.getUsuarioActual().getListas().size()]);
				
				String[] nombre_albumes = new String[1000]; //TEMPORAL
				for(int i=0;i < listas_totales.length; i++) {
					if(listas_totales[i].equals(vista.getLista()) == false) { //ENTENDEMOS QUE A LA MISMA LISTA NO SE PUEDE AÑADIR
						nombre_albumes[i] = listas_totales[i].getTitulo();
						contador++;
					}
				}
				
				
				if(contador > 0) {
					Object opcion = JOptionPane.showInputDialog(null,"Selecciona un Lista", "Elegir Lista",JOptionPane.QUESTION_MESSAGE,null,nombre_albumes, nombre_albumes[0]);
					for(int i=0; i < listas_totales.length; i++) {
						if(listas_totales[i].getTitulo().equals(opcion)) {
							if(Sistema.sistema.anyadirALista(listas_totales[i], vista.getLista()) == Status.OK) {
								Ventana.ventana.showReproducirLista(vista.getLista());
								JOptionPane.showMessageDialog(Ventana.ventana,"La lista se ha añadido correctamente a la lista " + opcion);
							}else {
								JOptionPane.showMessageDialog(Ventana.ventana,"La lista ya se encuentra en la lista " + opcion);
							}
						}
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"No hay listas a las que añadir esta lista");
				}
				
			} else {
				
				System.out.println(e.getSource());
			}
		}
}
