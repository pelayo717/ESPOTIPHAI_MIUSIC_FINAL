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

public class ControladorPantallaInicioAdmin implements ActionListener{
	private PantallaInicioAdmin vista;
	private int modelo;

	public ControladorPantallaInicioAdmin(PantallaInicioAdmin x, int modelo) {
		this.modelo = modelo;
		this.vista = x;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getText() == "Ver Perfil") {
			Ventana.ventana.showPerfil();
			Ventana.ventana.perfil.setInformacion(Sistema.sistema.getUsuarioActual());
		
		} else if(((JButton)e.getSource()).getText() == "Seleccionar Cancion") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Seleccionar Cancion seleccione una primero");
				}else{
					String[] options = {"Reproducir","Parar"};
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					int horas = (int) (canciones_totales[indice].getDuracion() / 3600);
				    int minutos = (int) ((canciones_totales[indice].getDuracion()-horas*3600)/60);
				    int segundos = (int) (canciones_totales[indice].getDuracion()-(horas*3600+minutos*60));
					int a = JOptionPane.showOptionDialog(Ventana.ventana,"Titulo: " + canciones_totales[indice].getTitulo() + "\nAutor: " + canciones_totales[indice].getAutor().getNombreAutor() + "\nDuracion HH/MM/SS: " + horas + "/" + minutos + "/" + segundos,"Cancion Seleccionada",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);  
					if(a == 0){
						
						//ARREGLAR LO DE PARAR ANTES
						
						try {
							for(int i=0; i < canciones_totales.length; i++) {
								canciones_totales[indice].parar();
							}
							canciones_totales[indice].setMp3Player();
							canciones_totales[indice].reproducirCancion();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (Mp3PlayerException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else {
						canciones_totales[indice].parar();
					}
					
					Ventana.ventana.pantallaInicioAdmin.lista_canciones.clearSelection();
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para seleccionar");
			}
		
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Valida") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Valida seleccione una primero");
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.VALIDA);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Explicita") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Explicita seleccione una primero");
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.EXPLICITA);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Pendiente") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Pendiente seleccione una primero");
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.PENDIENTEMODIFICACION);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();

			
		} else if(((JButton)e.getSource()).getText() == "Eliminada") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
				Cancion[] canciones_totales = Ventana.ventana.pantallaInicioAdmin.aValidar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Eliminada seleccione una primero");
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_canciones.getSelectedIndex();
					Sistema.sistema.gestionarCancionesPendientesValidacion_Modificacion(canciones_totales[indice], EstadoCancion.ELIMINADA);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay canciones para validar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Buscar") {
			if(Ventana.ventana.pantallaInicioAdmin.getOpcion1().isSelected() == true) {
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Cancion>  retornadas = Sistema.sistema.buscadorPorTitulos(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorCanciones(retornadas.toArray(new Cancion[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado canciones por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.pantallaInicioAdmin.getOpcion2().isSelected() == true){
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Album> retornadas = Sistema.sistema.buscadorPorAlbumes(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorAlbumes(retornadas.toArray(new Album[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado albumes por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else if(Ventana.ventana.pantallaInicioAdmin.getOpcion3().isSelected() == true) {
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() != true) {
					ArrayList<Contenido> retornadas = Sistema.sistema.buscadorPorAutores(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText());
					if(retornadas != null) { //ALGO HAY
						Ventana.ventana.showBuscadorAutores(retornadas.toArray(new Contenido[retornadas.size()]));
					}else {
						JOptionPane.showMessageDialog(Ventana.ventana,"No se han encontrado autores por ese parametro");
					}
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda");
				}
			}else {
				if(Ventana.ventana.pantallaInicioAdmin.getCriterioBusqueda().getText().isEmpty() == true) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Introduzca un parametro de busqueda y seleccione un criterio para realizar la busqueda");
				}else {
					JOptionPane.showMessageDialog(Ventana.ventana,"Debe seleccionar un criterio para poder realizar la busqueda");
				}
			}
			
			vista.limpiarBuscador();

						
		} else if(((JButton)e.getSource()).getText() == "Aceptar") {
			
			Reporte[] reportes_totales = Ventana.ventana.pantallaInicioAdmin.aReportar;
			
			if(reportes_totales.length > 0) {
				
				if(Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Aceptar seleccione uno primero");
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex();
					Sistema.sistema.gestionarReportes(reportes_totales[indice], true);
				}
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para valorar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Denegar") {
			
			if(Sistema.sistema.getCancionesPendientesValidacion().size() > 0) {
					
				Reporte[] reportes_totales = Ventana.ventana.pantallaInicioAdmin.aReportar;
				if(Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Denegar seleccione uno primero");
				}else{
					int indice = Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex();
					Sistema.sistema.gestionarReportes(reportes_totales[indice], false);
				}
				
			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para valorar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Seleccionar Reporte") {
			
			if(Sistema.sistema.getReportesTotales().size() > 0) {
				
				Reporte[] reportes_totales = Ventana.ventana.pantallaInicioAdmin.aReportar;
				int indice = Ventana.ventana.pantallaInicioAdmin.lista_reportes.getSelectedIndex();
				if(indice == -1) {
					JOptionPane.showMessageDialog(Ventana.ventana,"Antes de presionar Seleccionar Reporte seleccione uno primero");
				}else{
					JOptionPane.showMessageDialog(Ventana.ventana,"Titulo: " + reportes_totales[indice].getCancionReportada().getTitulo() + "\nReportador: " + reportes_totales[indice].getUsuarioReportador().getNombreUsuario() + "\nFecha: " + reportes_totales[indice].getFecha().toString() + "\nHora/Minuto/Segundo: " + reportes_totales[indice].getHora() + "/" + reportes_totales[indice].getMinuto() + "/" + reportes_totales[indice].getSegundo());  
				}
				
				Ventana.ventana.pantallaInicioAdmin.lista_reportes.clearSelection();

			}else {
				JOptionPane.showMessageDialog(Ventana.ventana,"No hay reportes para seleccionar");
			}
			
			Ventana.ventana.showPantallaInicioAdmin();
			
		} else if(((JButton)e.getSource()).getText() == "Cambiar Criterios") {
			
			if(Ventana.ventana.pantallaInicioAdmin.campoUmbral.getText().isEmpty() == false && Ventana.ventana.pantallaInicioAdmin.campoReproducciones.getText().isEmpty() == false && Ventana.ventana.pantallaInicioAdmin.campoPrecio.getText().isEmpty() == false) {
				
				int a=JOptionPane.showConfirmDialog(Ventana.ventana,"¿Esta seguro que desea modificar los criterios?","Alert",JOptionPane.WARNING_MESSAGE);  
				if(a == JOptionPane.YES_OPTION) {
					int umbral = Integer.parseInt(Ventana.ventana.pantallaInicioAdmin.campoUmbral.getText());
					int repro = Integer.parseInt(Ventana.ventana.pantallaInicioAdmin.campoReproducciones.getText());
					double precio = 0.0;
					try {
					  precio = Double.parseDouble(Ventana.ventana.pantallaInicioAdmin.campoPrecio.getText());
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
