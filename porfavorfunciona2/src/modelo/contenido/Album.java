package modelo.contenido;


import java.util.*;

import modelo.sistema.Sistema;
import modelo.status.*;
import modelo.usuario.*;

/**
 *	Clase Album con herencia de ContenidoComentable
 */
public class Album extends ContenidoComentable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Cancion> contenido = new ArrayList<Cancion>();

	/**
	 *	Constructor de Album con herencia de ContenidoComentable
	 *	@param contenido  canciones del album
	 */
	public Album (Date anyo, String titulo,  Usuario autor, ArrayList<Cancion> contenido) {
		super(anyo, titulo, autor, new ArrayList<Comentario>());
		this.setContenido(contenido);
		this.setDuracion(this.calcularTiempo());
		
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
	 *	Funcion para eliminar los contenidos de estado eliminado dEL aLBUM
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status checkContenido() {
		for(Cancion c: contenido) {
			if (c.getEstado() == EstadoCancion.ELIMINADA) {
				if (this.eliminarContenido(c) == Status.ERROR) {
					return Status.ERROR;
				}
			}
		}
		return Status.OK;
			
			
	}
	
	/**
	 *	Setter de contenido de la lista
	 * 	@param  contenido ArrayList del contenido de la lista
	 */
	public void setContenido(ArrayList<Cancion> contenido) {
		if (contenido == null) {
			this.contenido = new ArrayList<Cancion>();
		} else {
			this.contenido = contenido;
		}
	}
	

	/**
	 *	Getter de contenido de la lista
	 * 	@return  un ArrayList del contenido de la lista
	 */
	public ArrayList<Cancion> getContenido() {
		return contenido;
	}
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir un album de cancion en cancion si es un usuario premium el que lo realiza o de manera limita si no lo es
	 * @param a
	 * @throws InterruptedException
	 * @throws ExcesoReproduccionesExcepcion
	 */
	
	public void reproducirAlbum() throws InterruptedException{
		
		if(Sistema.sistema.getUsuarioActual() != null && (Sistema.sistema.getAdministrador() == true || Sistema.sistema.getUsuarioActual().getPremium() == true)) {
			for(Cancion canciones_reproduciendose:this.getContenido()) {
				canciones_reproduciendose.reproducirCancion();
			}
			
			return;
								
		}else {
			if(Sistema.sistema.getUsuarioActual() != null) {
				if(Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
					for(Cancion canciones_reproduciendose:this.getContenido()) {
						if(Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() == Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()) {
							return;
						}
						canciones_reproduciendose.reproducirCancion();
					}
					
					return;				
				}
			}else {
				if(Sistema.sistema.getContenidoEscuchadoSinRegistrarse() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
					for(Cancion canciones_reproduciendose:this.getContenido()) {
						if(Sistema.sistema.getContenidoEscuchadoSinRegistrarse() == Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()) {
							return;
						}
						canciones_reproduciendose.reproducirCancion();
					}
					
					return;				
				}
			}
		}
	}
	
	
	/**
	 * Esta funcion que la puede utilizar los usuario registrados les permite escribir un comentario sobre un album
	 * @param comentario
	 * @param album
	 * @return OK si se a�adio correctamente al album o ERROR si no fue asi
	 */
	
	public Status anyadirComentarioAlbum(Comentario comentario) {
		if((comentario == null) && (Sistema.sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && Sistema.sistema.getUsuarioActual() != null)) {
			return Status.ERROR;
		}else {
			return this.anyadirComentario(comentario);
		}
	
	}
	
	
}