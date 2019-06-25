package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.EstadoCancion;
import modelo.reporte.Reporte;
import modelo.sistema.Sistema;
import modelo.status.Status;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.PantallaInicioAdmin;
import vista.Ventana;

/**
 * Clase que implementa el controlador de la clase PantallaInicioAdmin
 * teniendo en cuenta todos los casos posibles en los que el usuario realiza
 * una accion u otra y asignando el controlador determinado a la accion realizada
 */
public class ControladorPantallaInicioAdmin implements ActionListener{//99.9% esta terminado
	private PantallaInicioAdmin vista;
	@SuppressWarnings("unused")
	private int modelo;

	/**
	 * Constructor de la clase en la que se inicializan todos los atributos de 
	 * la clase dandoles los valores necesarios
	 * @param x: vista en la que se encuentra el usuario y donde se van a realizar 
	 * todas las acciones 
	 * @param modelo: argumento de tipo entero que representa el modelo que estamos usando
	 */
	public ControladorPantallaInicioAdmin(PantallaInicioAdmin x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}
	
	/**
	 * Funcion que asigna el controlador correspondiente a la accion o boton que 
	 * el usuario ha realizado
	 * @param e: accion o boton que el usuario ha pulsado y se pasa como argumento 
	 * para asignarle el controlador correspondiente 
	 */
	public void actionPerformed(ActionEvent e) {
		
		if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();		
		} else if(((JButton)e.getSource()).getText() == "Seleccionar Cancion") {
			
			if(vista.getaValidar().length > 0) {
				
				Cancion[] canciones_totales = vista.getaValidar();
				int indice = vista.getLista_canciones().getSelectedIndex();
				vista.getLista_canciones().clearSelection();
				
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Seleccionar Cancion seleccione una primero");
				}else{
					String[] options = {"Reproducir","Parar"};
					int horas = (int) (canciones_totales[indice].getDuracion() / 3600);
				    int minutos = (int) ((canciones_totales[indice].getDuracion()-horas*3600)/60);
				    int segundos = (int) (canciones_totales[indice].getDuracion()-(horas*3600+minutos*60));
					int a = JOptionPane.showOptionDialog(Ventana.ventana,"Titulo: " + canciones_totales[indice].getTitulo() + "\nAutor: " + canciones_totales[indice].getAutor().getNombreAutor() + "\nDuracion HH/MM/SS: " + horas + "/" + minutos + "/" + segundos,"Cancion Seleccionada",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
					if(a == 0){								
						try {								
							canciones_totales[indice].reproducirCancion();
							Sistema.sistema.setReproductor(canciones_totales[indice].getReproductor());
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (Mp3PlayerException e1) {
							e1.printStackTrace();
						}
					}else {
						canciones_totales[indice].parar();
						Sistema.sistema.setReproductor(null);
					}
					
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
			}
					
		} else if(((JButton)e.getSource()).getText() == "Valida") {
			

			if(vista.getaValidar().length > 0) {
				Cancion[] canciones_totales = vista.getaValidar();
				int indice = vista.getLista_canciones().getSelectedIndex();
				vista.getLista_canciones().clearSelection();
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Valida seleccione una primero");
				}else{
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.VALIDA);
					vista.actualizarCanciones();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
			
		} else if(((JButton)e.getSource()).getText() == "Explicita") {
			
			if(vista.getaValidar().length > 0) {
				Cancion[] canciones_totales = vista.getaValidar();
				int indice = vista.getLista_canciones().getSelectedIndex();
				vista.getLista_canciones().clearSelection();
				
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Explicita seleccione una primero");
				}else{
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.EXPLICITA);
					vista.actualizarCanciones();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
			
		} else if(((JButton)e.getSource()).getText() == "Pendiente") {
			
			if(vista.getaValidar().length > 0) {
				Cancion[] canciones_totales = vista.getaValidar();
				int indice = vista.getLista_canciones().getSelectedIndex();
				vista.getLista_canciones().clearSelection();
				
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Pendiente seleccione una primero");
				}else{
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.PENDIENTEMODIFICACION);
					vista.actualizarCanciones();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}

			
		} else if(((JButton)e.getSource()).getText() == "Eliminada") {
			
			if(vista.getaValidar().length > 0) {
				Cancion[] canciones_totales = vista.getaValidar();
				int indice = vista.getLista_canciones().getSelectedIndex();
				vista.getLista_canciones().clearSelection();
				
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminada seleccione una primero");
				}else{
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.ELIMINADA);
					vista.actualizarCanciones();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
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

						
		} else if(((JButton)e.getSource()).getText() == "Aceptar") {
			
			if(vista.getaReportar().length > 0) {
				
				Reporte[] reportes_totales = vista.getaReportar();
				int indice = vista.getLista_reportes().getSelectedIndex();
				vista.getLista_reportes().clearSelection();
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Aceptar seleccione uno primero");
				}else{
					Sistema.sistema.gestionarReportes(reportes_totales[indice], true);
					vista.actualizarReportes();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para valorar");
			}
			
			
		} else if(((JButton)e.getSource()).getText() == "Denegar") {
			
			if(vista.getaReportar().length > 0) {
				
				Reporte[] reportes_totales = vista.getaReportar();
				int indice = vista.getLista_reportes().getSelectedIndex();
				vista.getLista_reportes().clearSelection();
				
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Denegar seleccione uno primero");
				}else{
					Sistema.sistema.gestionarReportes(reportes_totales[indice], false);
					vista.actualizarReportes();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para valorar");
			}
			
		} else if(((JButton)e.getSource()).getText() == "Seleccionar Reporte") {
			
			if(vista.getaReportar().length > 0) {
				
				Reporte[] reportes_totales = vista.getaReportar();
				int indice = vista.getLista_reportes().getSelectedIndex();
				vista.getLista_reportes().clearSelection();
				
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Seleccionar Reporte seleccione uno primero");
				}else{
					JOptionPane.showMessageDialog(Ventana.ventana,"Titulo: " + reportes_totales[indice].getCancionReportada().getTitulo() + "\nReportador: " + reportes_totales[indice].getUsuarioReportador().getNombreUsuario() + "\nFecha: " + reportes_totales[indice].getFecha().toString() + "\nHora/Minuto/Segundo: " + reportes_totales[indice].getHora() + "/" + reportes_totales[indice].getMinuto() + "/" + reportes_totales[indice].getSegundo());  
				}
				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para seleccionar");
			}
						
		} else if(((JButton)e.getSource()).getText() == "Cambiar Criterios") {
			
			if(vista.getCampoUmbral().getText().isEmpty() == false && vista.getCampoReproducciones().getText().isEmpty() == false && vista.getCampoPrecio().getText().isEmpty() == false) {
				
				int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea modificar los criterios?","Alert",JOptionPane.WARNING_MESSAGE);  
				if(a == JOptionPane.YES_OPTION) {
					int umbral = Integer.parseInt(vista.getCampoUmbral().getText());
					int repro = Integer.parseInt(vista.getCampoReproducciones().getText());
					double precio = 0.0;
					try {
					  precio = Double.parseDouble(vista.getCampoPrecio().getText());
					  if(Sistema.sistema.modificarCriteriosAplicacion(umbral, precio, repro) == Status.OK) {
							JOptionPane.showMessageDialog(Ventana.ventana,"Cambios efectuados correctamente");
					  }else {
							JOptionPane.showMessageDialog(Ventana.ventana,"Los cambios no se efectuaron correctamente");
					  }
					}catch(NumberFormatException e2) {
						//e2.printStackTrace();
						JOptionPane.showMessageDialog(Ventana.ventana,"El formato a introducir para el precio es con . ");
					}
			
				}				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"Rellene todos los criterios para poder cambiarlos");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();

		
		} else if(((JButton)e.getSource()).getText() == "Limpiar Buscador") {
			vista.limpiarBuscador();		
		}else {
			System.out.println(e.getSource());
		}
	}


}
