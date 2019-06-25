package modelo.contenido;
import java.io.FileNotFoundException;
import java.util.*;

import modelo.status.*;
import modelo.usuario.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase ContenidoComentable con herencia de Contenido
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */

public abstract class ContenidoComentable extends Contenido {

	
	private static final long serialVersionUID = 1L;
	private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
	
	public ContenidoComentable (int anyo, String titulo, Usuario autor, ArrayList<Comentario> comentarios) throws FileNotFoundException, Mp3PlayerException {
		super(anyo, titulo,autor);
		this.setComentarios(comentarios);
	}
	
	/**
	 *	Funcion para añadir un comentario al contenido comentable
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirComentario(Comentario comentario) {
		if(comentario == null) {
			return Status.ERROR;
		}
		if (this.comentarios.add(comentario) == true) {
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}
	
	/**
	 * Funcion para eliminar un comentario del array de comentarios de un album o cancion
	 * @param comentario: comentario que se desea eliminar
	 * @return OK si se elimino correctamente, ERROR en otro caso
	 */
	public Status eliminarComentario(Comentario comentario) {
		if(comentario == null) {
			return Status.ERROR;
		}
		if (this.comentarios.contains(comentario)) {
			comentario.eliminarSubComentarios();
			this.comentarios.remove(comentario);
			return Status.OK;	
		} else {
			for(Iterator<Comentario> iteratorComentario = this.comentarios.iterator(); iteratorComentario.hasNext();) {
				Comentario subcomentario = iteratorComentario.next();
				if (subcomentario.buscarYEliminarSubComentario(comentario) == Status.OK) {
					return Status.OK;
				}
			}
			return Status.ERROR;
		}
	}



	/**
	 *	Funcion que retorna un array de comentarios del contenido omentable
	 * 	@return  un ArrayList de los comentarios que tiene el contenido comentable
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}
	
	
	/**
	 * Funcion que establece un array de comentarios para un contenido comentable
	 * @param arraylist de comentarios a establecer
	 */
	public void setComentarios(ArrayList<Comentario> comentarios) {
		if (comentarios == null){
			this.comentarios = new ArrayList<Comentario>();

		} else {
			this.comentarios = comentarios;
		}
	}
	


}