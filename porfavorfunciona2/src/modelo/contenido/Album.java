package modelo.contenido;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import modelo.sistema.Sistema;
import modelo.status.*;
import modelo.usuario.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase Album con herencia de ContenidoComentable, en la 
 * que se va a implementar toda la funcionalidad del album
 */
public class Album extends ContenidoComentable {

	private static final long serialVersionUID = 1L;
	private ArrayList<Cancion> contenido = new ArrayList<Cancion>();

	/**
	 * Constructor de Album con herencia de ContenidoComentable
	 * @param anyo: argumento de tipo entero que determina el año del album  
	 * @param titulo: titulo  del album que se pasa como argumento en su creacion
	 * @param cotenido: argumento de tipo arraylist que contiene el contenido que va a tener el album
	 * @throws Mp3PlayerException provocada por una incorrecta creacion delreproductor
	 * @throws FileNotFoundException provocada por no encontrar el fichero que se esta solicitando
	 */
	public Album (int anyo, String titulo,  Usuario autor) throws FileNotFoundException, Mp3PlayerException {
		super(anyo, titulo, autor, new ArrayList<Comentario>());
		this.setDuracion(this.calcularTiempo());
		
	}
	
	/**
	 *	Getter de contenido del album
	 * 	@return  un ArrayList del contenido del album, este vacio o tenga algo
	 */
	public ArrayList<Cancion> getContenido() {
		return contenido;
	}
	
	/**
	 *	Funcion para anyadir un contenido al album
	 *	@param contenido  contenido a anyadir al album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirContenido(Cancion contenido) {
		if(this.contenido.contains(contenido)) {
			return Status.ERROR;
		} else {
			if (this.contenido.add(contenido)) {
				this.setDuracion(this.calcularTiempo());
				return Status.OK;
			} else {
				return Status.ERROR;
			}
		}
	}
	
	/**
	 *	Funcion para calcular el tiempo que dura el album
	 * 	@return  duracion la suma de las duraciones de sus contenidos
	 */
	public double calcularTiempo() {
		double duracion = 0;
		for(Cancion cancion: contenido) {
			duracion += cancion.getDuracion();
		}
		return duracion;
	}
	
	/**
	 *	Funcion para eliminar un contenido de el album
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido(Cancion contenido) {
		if(this.contenido.contains(contenido) == true) {
			if(this.contenido.remove(contenido)) {
				this.setDuracion(this.calcularTiempo());
				return Status.OK;
			} else {
				return Status.ERROR;
			}
		} else {
			return Status.OK;
		}
	}
	

	
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir un album de cancion en cancion si es un usuario premium el que lo realiza o de manera limita si no lo es
	 * @throws InterruptedException
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 * @throws ExcesoReproduccionesExcepcion
	 */
	
	public EstadoReproduccion reproducirAlbum() throws InterruptedException, FileNotFoundException, Mp3PlayerException{
		
		LocalDate fecha_actual = LocalDate.now();
		int contador=0;
		try {
				
			super.parar();
			super.setMp3Player();
			
			if(Sistema.sistema.getUsuarioActual() != null && (Sistema.sistema.getAdministrador() == true || Sistema.sistema.getUsuarioActual().getPremium() == true)) {
				Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);

				
				for(Cancion canciones_reproduciendose:this.getContenido()) {
					
					if(canciones_reproduciendose.getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() < 18) {
						continue;
					}
					
					super.getReproductor().add(canciones_reproduciendose.getNombreMP3());
					contador++;
				}
				
				if(this.getAutor().equals(Sistema.sistema.getUsuarioActual()) == false) {
					this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
				}
				
				if(contador == 0) {
					return EstadoReproduccion.VACIA;
				}else {
					super.reproducir();
				}
												
			}else {
				if(Sistema.sistema.getUsuarioActual() != null) {
					
					if(Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
						
						Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
						for(Cancion canciones_reproduciendose:this.getContenido()) {
							
							if(canciones_reproduciendose.getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() < 18) {
								continue;
							}
							
							super.getReproductor().add(canciones_reproduciendose.getNombreMP3());
							contador++;
					
						}
						
						if(this.getAutor().equals(Sistema.sistema.getUsuarioActual()) == false) {
							this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
						}
												
						if(contador == 0) {
							return EstadoReproduccion.VACIA;
						}else {
							Sistema.sistema.getUsuarioActual().addContenidoEscuchadoSinSerPremium();
							super.reproducir();
						}

						
					}else {
						return EstadoReproduccion.REPRODUCCIONES_AGOTADAS;
					}
				}else {
					if(Sistema.sistema.getContenidoEscuchadoSinRegistrarse() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
						for(Cancion canciones_reproduciendose:this.getContenido()) {
							
							if(canciones_reproduciendose.getEstado() == EstadoCancion.EXPLICITA) {
								continue;
							}
							
							super.getReproductor().add(canciones_reproduciendose.getNombreMP3());
							contador++;
						}
						
						this.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
												
						if(contador == 0) {
							return EstadoReproduccion.VACIA;
						}else {
							Sistema.sistema.addContenidoEscuchadoSinRegistrarse();
							super.reproducir();
						}

						
					}else {
						return EstadoReproduccion.REPRODUCCIONES_AGOTADAS;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
}
