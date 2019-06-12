package com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.io.Serializable;
import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase Comentario
 */
public class Comentario implements Serializable{
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