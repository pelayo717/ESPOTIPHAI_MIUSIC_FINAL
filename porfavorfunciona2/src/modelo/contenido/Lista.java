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
 * La clase lista hereda directamente de contenido y le permite englobar a todas las demas clases(albumes,canciones) y
 * a otras listas de ahi que el array general de items sea de tipo Contenido y no se especifique uno en concreto
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */
public class Lista extends Contenido{
	
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Contenido> contenidos = new ArrayList<Contenido>();
	
	/**
	 * Constructor de la clase Lista
	 * @param titulo: titulo de la lista
	 * @param autor: usuario que la ha creado
	 * @param contenido: contenido que se puede asignar nada mas ser creada la lista
	 * @throws FileNotFoundException se da si no se encuentra el fichero indicado
	 * @throws Mp3PlayerException se da si existe cualquier problema con el reproductor
	 */
	public Lista(String titulo,Usuario autor) throws FileNotFoundException, Mp3PlayerException {
		super(-1, titulo,autor);
		this.setContenido(new ArrayList<Contenido>());
		this.setDuracion(this.calcularTiempo());

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
	 *	Funcion para eliminar un contenido de la lista
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status eliminarContenido(Contenido contenido) {		
		
		
		for(Iterator<Contenido> iterator = this.getContenido().iterator(); iterator.hasNext();) {
			Contenido c_l = iterator.next();
			
			if (c_l instanceof Cancion && contenido instanceof Cancion && c_l.equals(contenido) == true) {
				iterator.remove();
				this.setDuracion(this.calcularTiempo());
			} else if (c_l instanceof Album && contenido instanceof Cancion && ((Album) c_l).getContenido().contains(contenido) == true) {
				((Album)c_l).eliminarContenido((Cancion)contenido);
			} else if (c_l instanceof Lista && contenido instanceof Cancion) {
				((Lista) c_l).eliminarContenido((Cancion)contenido);
			} else if(c_l instanceof Album && contenido instanceof Album && c_l.equals(contenido)==true) {
				iterator.remove();
				this.setDuracion(this.calcularTiempo());
			} else if(c_l instanceof Lista && contenido instanceof Album) {
				((Lista) c_l).eliminarContenido((Album)contenido);
			} else if(c_l instanceof Lista && contenido instanceof Lista && ((Lista) c_l).getContenido().contains(contenido) == true) {
				iterator.remove();
				this.setDuracion(this.calcularTiempo());
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
	
	public EstadoReproduccion reproducirLista() throws InterruptedException, FileNotFoundException, Mp3PlayerException {
				
		int contador=0;

		LocalDate fecha_actual = LocalDate.now();
		
		Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);

		
		super.parar();
		super.setMp3Player();
		
		if(Sistema.sistema.getUsuarioActual() != null && (Sistema.sistema.getAdministrador() == true || Sistema.sistema.getUsuarioActual().getPremium() == true)) {
				for(Contenido contenido_reproduciendose:this.getContenido()) {
					if(contenido_reproduciendose instanceof Cancion) {
						if(((Cancion) contenido_reproduciendose).getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() < 18) {
							continue;
						}else{
							super.getReproductor().add(((Cancion) contenido_reproduciendose).getNombreMP3());
							contador++;
							if(contenido_reproduciendose.getAutor().equals(Sistema.sistema.getUsuarioActual()) == false) {
								contenido_reproduciendose.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
							}
						}
					}else if(contenido_reproduciendose instanceof Album) {
						
						for(Cancion aux: ((Album) contenido_reproduciendose).getContenido()) {
							if(aux.getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() < 18) {
								continue;
							}else {
								super.getReproductor().add(aux.getNombreMP3());
								contador++;
							}
						}
						
						//Sumamos una reproduccion para el que es el dueño del album
						if(contenido_reproduciendose.getAutor().equals(Sistema.sistema.getUsuarioActual()) == false) { 
							contenido_reproduciendose.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
						}
						
						//Para las listas hacemos como una funcion recursiva, y no sumamos en uno debido a que estas son propias del usuario que las escucha
					}else if(contenido_reproduciendose instanceof Lista) {
						((Lista) contenido_reproduciendose).reproducirLista(); 
					}	
				}	
				
				if(contador == 0) {
					return EstadoReproduccion.VACIA;
				}else {
					super.reproducir();
				}
			
			
				
		}else {
			if(Sistema.sistema.getUsuarioActual().getContenidoEscuchadoSinSerPremium() < Sistema.sistema.getMaxReproduccionesUsuariosNoPremium()){
				if(this.getContenido().size() > 0) {
					for(Contenido contenido_reproduciendose:this.getContenido()) {
						if(contenido_reproduciendose instanceof Cancion) {
							if(((Cancion) contenido_reproduciendose).getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() < 18) {
								continue;
							}else{
								super.getReproductor().add(((Cancion) contenido_reproduciendose).getNombreMP3());
								contador++;
								if(contenido_reproduciendose.getAutor().equals(Sistema.sistema.getUsuarioActual()) == false) {
									contenido_reproduciendose.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
								}
							}
						}else if(contenido_reproduciendose instanceof Album) {
							
							for(Cancion aux: ((Album) contenido_reproduciendose).getContenido()) {
								if(aux.getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() < 18) {
									continue;
								}else {
									super.getReproductor().add(aux.getNombreMP3());
									contador++;
								}
							}
							
							//Sumamos una reproduccion para el que es el dueño del album
							if(contenido_reproduciendose.getAutor().equals(Sistema.sistema.getUsuarioActual()) == false) { 
								contenido_reproduciendose.getAutor().sumarReproduccion(Sistema.sistema.getUmbralReproducciones());
							}
							
							//Para las listas hacemos como una funcion recursiva, y no sumamos en uno debido a que estas son propias del usuario que las escucha
						}else if(contenido_reproduciendose instanceof Lista) {
							((Lista) contenido_reproduciendose).reproducirLista(); 
						}	
					}
										
					if(contador == 0) {
						return EstadoReproduccion.VACIA;
					}else {
						Sistema.sistema.getUsuarioActual().addContenidoEscuchadoSinSerPremium();
						super.reproducir();
					}
				
				}else {
					return EstadoReproduccion.VACIA;
				}
			}else {
				return EstadoReproduccion.REPRODUCCIONES_AGOTADAS;
			}
		}
		
		return null;
	}
	

}