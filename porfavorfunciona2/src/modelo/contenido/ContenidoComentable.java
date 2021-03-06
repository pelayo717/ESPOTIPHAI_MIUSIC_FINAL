package modelo.contenido;
import java.io.FileNotFoundException;
import java.util.*;

import modelo.status.*;
import modelo.usuario.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 *	Clase ContenidoComentable con herencia de Contenido
 */

public abstract class ContenidoComentable extends Contenido {

	/**
	 * 
	 */
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
			System.out.print("adei");
		}
		if (this.comentarios.add(comentario) == true) {
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}



	/**
	 *	Getter de comentarios del contenido omentable
	 * 	@return  un ArrayList de los comentarios que tiene el contenido comentable
	 */
	public ArrayList<Comentario> getComentarios() {
		return comentarios;
	}
	
	
	

	//GETTERS Y SETTERS
	
	
	
	/**
	 * Getter de coemntarios
	 * @return comentarios
	 */
	public ArrayList<Comentario> getComentarios(ArrayList<Comentario> comentarios) {
		return comentarios;
	}

	/**
	 * Setter de fecha
	 * @param fecha del comentario
	 */
	public void setComentarios(ArrayList<Comentario> comentarios) {
		if (comentarios == null){
			this.comentarios = new ArrayList<Comentario>();

		} else {
			this.comentarios = comentarios;
		}
	}
	


}