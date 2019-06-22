package modelo.contenido;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import modelo.sistema.Sistema;
import modelo.usuario.*;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3InvalidFileException;
import pads.musicPlayer.exceptions.Mp3PlayerException;



/**
 *	La clase cancion es practicamente el pilar fundamental en la que se basa la aplicacio, esta contiene multiples
 *	funciones que alteran su estado y otras funciones que le permiten almacenar objetos de otras clases en su interior
 *	Gracias a la existencia de esta clase nuestra aplicacion tiene una utilidad final y ademas esta es dependiente para
 *	otras clases como album o lista
 */


public class Cancion extends ContenidoComentable {
	
	private static final long serialVersionUID = 1L;
	private EstadoCancion estado_anterior;
	private EstadoCancion estado;
	private String nombreMP3;
	private LocalDate fecha_modificar;
	private String nombre_fichero;
	
	/**
	 * Propio constructor de la clase cancion. Durante la construccion de la cancion, establecimos una trampa
	 * para saber si el fichero indicado por el usuario es de tipo mp3, si no es asi el objeto se queda a medio construir
	 * y se retorna y es donde se llama al constructor donde se elimina
	 * @param titulo: titulo que le desea dar el autor
	 * @param autor: propio creador de la cancion
	 * @param nombreMP3: ruta donde encontrar el fichero mp3 para su reproduccion
	 * @param nombre_f: nombre del fichero mp3
	 * @throws FileNotFoundException: provocada si no se encuentra el fichero mp3
	 * @throws Mp3PlayerException: provocada por una mala creacion del reproductor heredado
	 */
	public Cancion(String titulo, Usuario autor,  String nombreMP3, String nombre_f) throws FileNotFoundException, Mp3PlayerException{
		super(-1,titulo, autor, new ArrayList<Comentario>());
		this.setNombreMP3(nombreMP3);
		this.setNombreFichero(nombre_f);
		this.setDuracion(-1); //AÑADIMOS ESTA CONDICION POR SI ACASO RESULTA QUE EL nombreMP3 no es de tipo mp3
		if(this.esMP3() == false) {
			return;
		}
		this.setDuracion(this.devolverDuracion());
		this.setEstado(EstadoCancion.PENDIENTEAPROBACION);
		this.setEstadoAnterior(null);
		this.fecha_modificar = null;		
	}
	

	/**
	 * Retornamos el nombre del fichero que esta asignado a la cancion
	 * @return nombre_fichero: nombre del fichero asignado a la cancion
	 */
	public String getNombreFichero() {
		return this.nombre_fichero;
	}
	
	/**
	 * Retornamos el path entero donde se encuentra la cancion
	 * @return nombreMP3: path donde se encuentra el fichero asignado a la cancion
	 */
	public String getNombreMP3() {
		return nombreMP3;
	}
	
	
	/**
	 * Retornamos la fecha de inicio de modificacion de la cancion
	 * @return fecha_modificar: tipo LocalDate con la fecha de inicio
	 */
	public LocalDate getFechaModificacion() {
		return this.fecha_modificar;
	}
	
	/**
	 * Retornamos el estado de la cancion para un momento determinado
	 * @return estado: tipo EstadoCancion que alberga 6 tipos diferentes
	 */
	public EstadoCancion getEstado() {
		return estado;
	}
	
	/**
	 * Retornamos el estado anterior de la cancion, por ejemplo si es considerada de plagio
	 * tendremos que saber si era valida o explicita antes de considerarse de plagio
	 * @return estado_anterior: tipo EstadoCancion con el estado anterior de la cancion
	 */
	public EstadoCancion getEstadoAnterior() {
		return estado_anterior;
	}
	
	/**
	 *	Esta funcion cambia el estado de la cancion a plagio, debido a un reporte
	 */
	public void reportarPlagio() {
		this.setEstado(EstadoCancion.PLAGIO);
	}
	
	/**
	 * Esta funcion cambia el estado de la cancion a explicita
	 */
	public void validarCancionExplicita() {
		this.setEstado(EstadoCancion.EXPLICITA);
	}
	
	/**
	 *	Esta funcion cambia el estado de la cancion a pendiente de aprobacion
	 */
	public void validarCancion() {
		this.setEstado(EstadoCancion.PENDIENTEAPROBACION);
	}
	
	/**
	 *	Esta funcion cambia el estado de la cancion a pendiente de modificacion
	 */
	public void cancionRechazada() {
		this.setEstado(EstadoCancion.PENDIENTEMODIFICACION);
	}
	
	
	/**
	 * Esta funcion cambia el estado de la cancion a eliminada
	 */
	public void cancionDescartada() {
		this.setEstado(EstadoCancion.ELIMINADA);
	}
	
	/**
	 * Establece el nombre real del fichero que esta asignado a la cancion
	 * Sera util, para cuando se cambie de entorno, ya que la ruta podra ser distinta pero el fichero que
	 * permanecera en sus sitio seguira con su nombre predeterminado, en este caso nombre_fichero
	 * es quien salvara ese nombre
	 * @param nombre_f: nombre del fichero real
	 */
	public void setNombreFichero(String nombre_f) {
		this.nombre_fichero = nombre_f;
	}
	
	/**
	 * Establece el estado indicado por parametro
	 * @param estado: tipo EstadoCancion que contiene 6 tipos diferentes
	 */
	public void setEstado(EstadoCancion estado) {
		if(estado == EstadoCancion.PENDIENTEMODIFICACION) {
			this.setFechaModificacion(LocalDate.now());
		}
		this.estado = estado;
	}
	
	/**
	 * Establece el estado anterior que se almacena cuando se reporta un plagio por ejemplo
	 * @param estado: tipo EstadoCancion que contiene 6 tipos diferentes
	 */
	public void setEstadoAnterior(EstadoCancion estado) {
		this.estado_anterior = estado;
	}
	
	/**
	 * Establece la fecha de inicio para realizar la modificacion
	 * @param d: tipo LocalDate que indica el dia en el que se trato de validar y paso a estado PENDIENTEMODIFICACION
	 */
	public void setFechaModificacion(LocalDate d) {
		this.fecha_modificar = d;
	}
	
	/**
	 * Establece el path completo donde se encuentra la cancion concreta
	 * @param nombreMP3: path completo con la ubicacion del fichero asignado a la cancion
	 */
	public void setNombreMP3(String nombreMP3) {
		this.nombreMP3 = nombreMP3;
	}

	
	/**
	 * Funcion encargada de añadir la ruta de donde se encuentra la cancion
	 * al reproductor
	 * @throws Mp3InvalidFileException si se considera un fichero invalido para el reproductor
	 */
	public void anyadirCola() throws Mp3InvalidFileException{
		try {
			this.getReproductor().add(this.nombreMP3);
			return;
		}catch(Mp3InvalidFileException ie) {
			ie.toString();
			return;
		}
	}
	
	/**
	 *	Funcion para saber si es tipo MP3 o no
	 *	return true si es de tipo MP3 y false de lo contrario
	 */
	public boolean esMP3() {
				
		if(Mp3Player.isValidMp3File(this.nombreMP3) == true) {
			return true;
		}	
		return false;
	}

	/**
	 * Funcion de MP3 para devolver la duracion de la cancion
	 * return double de la duracion de la cancion
	 * @return -1 si no es un fichero valido(tipo mp3), de lo contrario el correspondiente tiempo en segundos
	 * @throws FileNotFoundException si el fichero a buscar no se encuentra en el sistema
	 */
	public double devolverDuracion() throws FileNotFoundException{
		try {
			double duracion = Mp3Player.getDuration(this.nombreMP3);			
			return duracion;
		}catch(FileNotFoundException fe) {
			fe.toString();
			return -1.0;
		}
	}
	
	/**
	 * Funcion basica que se encarga de añadir a la cola del reproductor solo una cancion y reproducirla
	 * @throws Mp3InvalidFileException si se considera un fichero invalido para el reproductor
	 */
	public void reproducirBasico() throws Mp3InvalidFileException {
		this.anyadirCola();
		super.reproducir();
	}
	
	/**
	 * Esta funcion reproduce el propio fichero asignado a la clase,
	 * teniendo en cuenta el tipo de usuario de la aplicacion que la quiere escuchar y si a este se le permite
	 * o no en base a esos factores
	 * @return EstadoReproduccion: tipo que informa del problema por el que no se pudo reproducir la cancion
	 * @throws InterruptedException para cuando el hilo del reproductor se interrumpe
	 * @throws FileNotFoundException si no se encuentra el fichero a reproducir
	 * @throws Mp3PlayerException si existe algun problema con el propio reproductor
	 */
	
	public EstadoReproduccion reproducirCancion() throws InterruptedException, FileNotFoundException, Mp3PlayerException { //se supone que la cancion ha sido subida, valida y a la hora de buscar se devuelve en base a criterios ya comprobados
					
			LocalDate fecha_actual = LocalDate.now();
			
			if(this.getEstado() == EstadoCancion.PLAGIO || this.getEstado() == EstadoCancion.ELIMINADA) {
				return EstadoReproduccion.OTRO;
			}
			
			super.parar();
			super.setMp3Player();
			
			if(Sistema.sistema.getUsuarioActual() != null && ((Sistema.sistema.getAdministrador() == true || Sistema.sistema.getUsuarioActual().getPremium() == true))){
				
				if(Sistema.sistema.getAdministrador() == true) {
					
					this.reproducirBasico();				
					
					this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
				}else {
					
					Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
					if(intervalo.getYears() < 18 && this.getEstado() == EstadoCancion.EXPLICITA) {
						return EstadoReproduccion.MENOR;
					}
					
					if(Sistema.sistema.getUsuarioActual().getCanciones().contains(this) == true && (this.getEstado() == EstadoCancion.PENDIENTEAPROBACION || this.getEstado() == EstadoCancion.PENDIENTEMODIFICACION )) {
		
						this.reproducirBasico();

					}else {
					
						this.reproducirBasico();
						
						if(Sistema.sistema.getUsuarioActual().getCanciones().contains(this) != true) {
							this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
						}
					
					}
				
				}
				
			}else{
								
				if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
					
					Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
					if(intervalo.getYears() < 18 && this.getEstado() == EstadoCancion.EXPLICITA) {
						return EstadoReproduccion.MENOR;
					}
					
					
					if(Sistema.sistema.getUsuarioActual().getCanciones().contains(this) == true && (this.getEstado() == EstadoCancion.PENDIENTEAPROBACION || this.getEstado() == EstadoCancion.PENDIENTEMODIFICACION )) {
						
						this.reproducirBasico();

						Sistema.sistema.getUsuarioActual().addContenidoEscuchadoSinSerPremium();
					}else {
												
						this.reproducirBasico();
						
						Sistema.sistema.getUsuarioActual().addContenidoEscuchadoSinSerPremium();
						
						if(Sistema.sistema.getUsuarioActual().getCanciones().contains(this) != true) {
							this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());						
						}
					}
				}else if(Sistema.sistema.getContenidoEscuchadoSinRegistrarse() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
					
					if(this.getEstado() == EstadoCancion.EXPLICITA ) { 
						return EstadoReproduccion.USUARIO_SR;
					}
										
					this.reproducirBasico();
					
					this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
					Sistema.sistema.addContenidoEscuchadoSinRegistrarse();
					
				}else {
					return EstadoReproduccion.REPRODUCCIONES_AGOTADAS;
				}
			}
			return null;
						
		}
	


	/**
	 * Esta funcion es llamada cuando una cancion se elimina, y se encarga de eliminar del sistema el fichero
	 * asignado a esta cancion
	 * return true si se elimino correctamente el fichero o false si no fue asi
	 */
	public boolean eliminarAudio() {
		File aux = new File(this.nombreMP3);
		if(aux.delete() == true) {
			return true;
		}
		return false;
	}
		
	

}