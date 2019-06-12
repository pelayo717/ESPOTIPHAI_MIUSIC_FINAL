package modelo.contenido;
import java.io.FileNotFoundException;
import java.util.*;

import modelo.sistema.Sistema;
import modelo.status.*;
import modelo.usuario.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 *	Clase Lista con herencia de Contenido
 */
public class Lista extends Contenido{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Contenido> contenidos = new ArrayList<Contenido>();
	
	public Lista(String titulo,Usuario autor, ArrayList<Contenido> contenido) {
		super(-1, titulo,autor);
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
		if(this.contenidos.contains(contenido)) {
			return Status.ERROR;
		} else {
			if (this.contenidos.add(contenido)) {
				this.setDuracion(this.calcularTiempo());
				return Status.OK;
			} else {
				return Status.ERROR;
			}
		}	
	}
	
	/**
	 *	Funcion para eliminar un contenido de la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido(Contenido contenido) {		
		
		if(contenido instanceof Cancion) {
			
			for(Contenido c_l: this.getContenido()) {
				
				if(c_l instanceof Cancion && c_l.equals(contenido) == true) {
					this.getContenido().remove(contenido);
					this.setDuracion(this.calcularTiempo());
				}else if(c_l instanceof Album && ((Album) c_l).getContenido().contains(contenido) == true) {
					((Album)c_l).eliminarContenido((Cancion)contenido);
				}else if(c_l instanceof Lista) {
					((Lista) c_l).eliminarContenido((Cancion)contenido);
				}
			}
		}else if(contenido instanceof Album) {
			for(Contenido c_l:this.getContenido()) {
				if(c_l instanceof Album && c_l.equals(contenido)==true) {
					this.getContenido().remove(contenido);
					this.setDuracion(this.calcularTiempo());
				}else if(c_l instanceof Lista) {
					((Lista) c_l).eliminarContenido((Album)contenido);
				}
			}
		}else if(contenido instanceof Lista) {
			for(Contenido c_l:this.getContenido()) {
				if(c_l instanceof Lista) {
					if(c_l.equals(contenido) == true) {
						this.getContenido().remove(contenido);
						this.setDuracion(this.calcularTiempo());
					}else {
						((Lista) c_l).eliminarContenido((Lista)contenido);
					}
				}
			}
		}
		
		return Status.OK;
	}

	
	/**
	 *	Setter de contenido de la lista
	 * 	@param  contenido ArrayList del contenido de la lista
	 */
	public void setContenido(ArrayList<Contenido> contenido) {
		if (contenido == null) {
			this.contenidos = new ArrayList<Contenido>();
		} else {
			this.contenidos = contenido;
		}
	}
	

	/**
	 *	Getter de contenido de la lista
	 * 	@return  un ArrayList del contenido de la lista
	 */
	public ArrayList<Contenido> getContenido() {
		return contenidos;
	}

	/**
	 * Esta funcion permite reproducir una de tus listas
	 * @param l
	 * @throws ExcesoReproduccionesExcepcion 
	 * @throws InterruptedException 
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	
	public void reproducirLista() throws InterruptedException, FileNotFoundException, Mp3PlayerException {
		
		if(Sistema.sistema.getUsuarioActual() != null && (Sistema.sistema.getAdministrador() == true || Sistema.sistema.getUsuarioActual().getPremium() == true)) {
			for(Contenido contenido_reproduciendose:this.getContenido()) {
				if(contenido_reproduciendose instanceof Cancion) {
					((Cancion) contenido_reproduciendose).reproducirCancion();
				}else if(contenido_reproduciendose instanceof Album) {
					((Album) contenido_reproduciendose).reproducirAlbum();
				}else if(contenido_reproduciendose instanceof Lista) {
					((Lista) contenido_reproduciendose).reproducirLista();
				}
			}	
			return;
				
		}else {
			if(Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
				
				for(Contenido contenido_total:this.getContenido()) {
					
					if(Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() == Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()) {
						return;
					}
					
					if(contenido_total instanceof Cancion) {
						((Cancion) contenido_total).reproducirCancion();
					}else if(contenido_total instanceof Album) {
						((Album) contenido_total).reproducirAlbum();
					}else if(contenido_total instanceof Lista) {
						((Lista) contenido_total).reproducirLista();
					}
				}
				
				return;
			}
		}
	}
	

}