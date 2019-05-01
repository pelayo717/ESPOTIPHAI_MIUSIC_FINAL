package ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.util.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase Lista con herencia de Contenido
 */
public class Lista extends Contenido{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Cancion> contenidos = new ArrayList<Cancion>();
	
	
	/**
	 *	Constructor de Lista
	 *	@param anyo anyo de creacion de la lista (Date)
	 * 	@param titulo el titulo de la lista (String)
	 * 	@param autor autor de la lista (Usuario) 
	 *  @param contenido array de las canciones de la lista (ArrayList<Cancion>)
	 */
	
	public Lista(Date anyo, String titulo,Usuario autor, ArrayList<Cancion> contenido) {
		super(anyo, titulo,autor);
		this.setContenido(contenido);
		this.setDuracion(this.calcularTiempo());

	}



	/**
	 *	Funcion para calcular el tiempo que dura la lista
	 * 	@return  duracion la suma de las duraciones de sus contenidos
	 */
	public double calcularTiempo() {
		double duracion = 0;
		for(Contenido contenido: contenidos) {
			duracion += contenido.getDuracion();
		}
		return duracion;
	}
	
	
	/**
	 *	Funcion para anyadir contenido a la lista
	 *	@param contenido  contenido a anyadir a la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirContenido(Contenido contenido) {
		if (contenido instanceof Cancion) {
			if(this.contenidos.contains(contenido)) {
				return Status.ERROR;
			} else {
				if (this.contenidos.add((Cancion) contenido)) {
					this.setDuracion(this.calcularTiempo());
					return Status.OK;
				} else {
					return Status.ERROR;
				}
			}
		} else if (contenido instanceof Lista) {
			ArrayList<Cancion> canciones = ((Lista)contenido).getContenido();
			for(Cancion cancion: canciones) {
				if (this.anyadirContenido(cancion) == Status.ERROR){
					return Status.ERROR;
				}
			}
			this.setDuracion(this.calcularTiempo());
			return Status.OK;
		} else if (contenido instanceof Album) {
			ArrayList<Cancion> canciones = ((Album)contenido).getContenido();
			for(Cancion cancion: canciones) {
				if (this.anyadirContenido(cancion) == Status.ERROR){
					return Status.ERROR;
				}
			}
			this.setDuracion(this.calcularTiempo());
			return Status.OK;
		} else {
			return Status.ERROR;
		}
		
	}
	
	/**
	 *	Funcion para eliminar un contenido de la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido(Contenido contenido) {
		if(this.contenidos.contains(contenido)) {
			if(this.contenidos.remove(contenido)) {
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
	 *	Funcion para eliminar los contenidos de estado eliminado de la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status checkContenido() {
		for(Cancion c: contenidos) {
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
			this.contenidos = new ArrayList<Cancion>();
		} else {
			this.contenidos = contenido;
		}
	}
	

	/**
	 *	Getter de contenido de la lista
	 * 	@return  un ArrayList del contenido de la lista
	 */
	public ArrayList<Cancion> getContenido() {
		return contenidos;
	}
	

}