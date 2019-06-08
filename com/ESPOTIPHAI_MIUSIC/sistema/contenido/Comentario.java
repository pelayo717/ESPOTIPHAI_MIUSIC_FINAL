package ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.io.Serializable;
import java.util.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.*;

/**
 *	Clase Comentario
 */
public class Comentario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fecha;
	private String texto;
	private Usuario comentador;
	
	/**
	 *	Constructor de Comentario
	 *	@param fecha  fecha del comentario (Date)
	 *	@param texto  texto del comentario (String)
	 */
	public Comentario(Date fecha,String texto,Usuario comentador) {
		this.setFecha(fecha);
		this.setTexto(texto);
		this.setComentador(comentador);
	}
	
	

	
	
	//GETTERS Y SETTERS
	
	@Override
	public String toString() {
		return texto;
	}





	/**
	 * Establece el usuario que esta comentando
	 * @param c
	 */
	public void setComentador(Usuario c) {
		this.comentador = c;
	}
	
	/**
	 * Devuelve el comentador
	 * @return la referencia al usuario que ha comentado
	 */
	public Usuario getComentador() {
		return this.comentador;
	}
	
	/**
	 * Getter de fecha
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Setter de fecha
	 * @param fecha del comentario
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	/**
	 * Getter de texto
	 * @return the texto
	 */
	public String getTexto() {
		return texto;
	}


	/**
	 * Setter de texto
	 * @param texto del comentario
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}
	

	
	
}