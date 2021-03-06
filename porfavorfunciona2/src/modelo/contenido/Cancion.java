package modelo.contenido;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import modelo.sistema.Sistema;
import modelo.status.*;
import modelo.usuario.*;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3InvalidFileException;
import pads.musicPlayer.exceptions.Mp3PlayerException;
import vista.Ventana;



	/**
 *	Clase Cancion con herencia de ContenidoComentable
 */


public class Cancion extends ContenidoComentable {
	private static final long serialVersionUID = 1L;
	private EstadoCancion estado_anterior;
	private EstadoCancion estado;
	private String nombreMP3;
	private LocalDate fecha_modificar;
	/**
	 *	Constructor de Cancion
	 *	@param estado  estado de la cancion
	 *	@param reproducible  si la cacion es o no reproducible
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	public Cancion(String titulo, Usuario autor,  String nombreMP3) throws FileNotFoundException, Mp3PlayerException{
		super(-1,titulo, autor, new ArrayList<Comentario>());
		this.setNombreMP3(nombreMP3);		
		this.setDuracion(this.devolverDuracion());
		this.setEstado(EstadoCancion.PENDIENTEAPROBACION);
		this.fecha_modificar = LocalDate.now();
	}
	
	
	
	/**
	 *	Funcion para anyadir a la cola de reproduccion
	 *	@param cancion_a_anyadir  string de la cancion a anyadir
	 */
	public void anyadirCola() {
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
	 *	@param cancion string de la cancion
	 *	return true si es de tipo MP3 y false de lo contrario
	 */
	public boolean esMP3() {
				
		if(Mp3Player.isValidMp3File(this.nombreMP3) == true) {

			return true;
		}
			
		return false;
	}
	
	/**
	 *	Funcion de MP3 para devolver la duracion de la cancion
	 *	@param cancion string de la cancion
	 *	return double de la duracion de la cancion
	 */
	public double devolverDuracion() {
		try {
			double duracion = Mp3Player.getDuration(this.nombreMP3);			
			return duracion;
		}catch(FileNotFoundException fe) {
			fe.toString();
			return -1.0;
		}
	}
	
	
	/**
	 *	Funcion de reportarPlagio
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status reportarPlagio() {
		this.setEstado(EstadoCancion.PLAGIO);
		return Status.OK;
	}
	
	
	public Status validarCancionExplicita() {
		this.setEstado(EstadoCancion.EXPLICITA);
		return Status.OK;
	}
	
	/**
	 *	Funcion de validarCancion
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status validarCancion() {
		this.setEstado(EstadoCancion.PENDIENTEAPROBACION);
		return Status.OK;
	}
	
	/**
	 *	Funcion de cancionRechazada
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status cancionRechazada() {
		this.setEstado(EstadoCancion.PENDIENTEMODIFICACION);
		return Status.OK;
	}
	
	/**
	 *	Funcion de cancionCorregida
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status cancionCorregida() {
		this.setEstado(EstadoCancion.VALIDA);
		return Status.OK;
	}
	
	public Status cancionDescartada() {
		this.setEstado(EstadoCancion.ELIMINADA);
		return Status.OK;
	}
	
	
	
	//GETTERS Y SETTERS
	
	public LocalDate getFechaModificacion() {
		return this.fecha_modificar;
	}
	
	/**
	 * Getter de estado
	 * @return the estado
	 */
	public EstadoCancion getEstado() {
		return estado;
	}
	
	/**
	 * Getter de estado
	 * @return the estado
	 */
	public EstadoCancion getEstadoAnterior() {
		return estado_anterior;
	}
	
	
	/**
	 * Setter de estado
	 * @param estado estado de la cancion
	 */
	public void setEstado(EstadoCancion estado) {
		if(estado == EstadoCancion.PENDIENTEMODIFICACION) {
			this.fecha_modificar = LocalDate.now();
		}
		this.estado = estado;
	}
	
	/**
	 * Setter de estado
	 * @param estado estado de la cancion
	 */
	public void setEstadoAnterior(EstadoCancion estado) {
		this.estado_anterior = estado;
	}
	
	
	/**
	 * Getter deL nombre del MP3
	 * @return nombreMP3 nombre de la cancion
	 */
	public String getNombreMP3() {
		return nombreMP3;
	}
	
	/**
	 * Establece la fecha de inicio para realizar la modificacion
	 * @param d
	 */
	public void setFechaModificacion(LocalDate d) {
		this.fecha_modificar = d;
	}
	
	/**
	 * Setter del nombre del MP3
	 * @param nombreMP3 el nombre de la cacion
	 */
	public void setNombreMP3(String nombreMP3) {
		this.nombreMP3 = nombreMP3;
	}
	
	public void reproducirBasico() {
		this.anyadirCola();
		super.reproducir();
	}
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir una cancion que se pase como argumento
	 * @param c
	 * @return 
	 * @throws InterruptedException ExcesoReproduccionesExcepcion
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	
	public EstadoReproduccion reproducirCancion() throws InterruptedException, FileNotFoundException, Mp3PlayerException { //se supone que la cancion ha sido subida, valida y a la hora de buscar se devuelve en base a criterios ya comprobados
					
			LocalDate fecha_actual = LocalDate.now();
			
			if(this.getEstado() == EstadoCancion.PLAGIO || this.getEstado() == EstadoCancion.ELIMINADA) {
				return EstadoReproduccion.OTRO;
			}
			
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
	 * Esta funcion que la puede utilizar los usuarios registrados les permite escribir un comentario sobre una cancion
	 * @param comentario
	 * @param cancion
	 * @return OK si se anyadio correctamente a la cancion o ERROR si no fue asi
	 */
	public Status anyadirComentarioCancion(Comentario comentario) {
		if((comentario == null) && (Sistema.sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && Sistema.sistema.getUsuarioActual() != null)) {
			return Status.ERROR;
		}else {
			return this.anyadirComentario(comentario);
		}
	}
		
	

}