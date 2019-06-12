package com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.io.Serializable;
import java.util.*;

import com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

/**
 *	Clase Contenido
 */
public abstract class Contenido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int nextID = 0;
	private Date anyo;
	private String titulo;
	private double duracion;
	private int id;
	private Usuario autor;
	
	/**
	 *	Constructor de Contenido
	 *	@param estado  estado de la cancion
	 *	@param reproducible  si la cacion es o no reproducible
	 */
	public Contenido(Date anyo, String titulo, Usuario autor ) {
		this.setAutor(autor);
		this.setAnyo(anyo);
		this.setDuracion(0);
		this.setId(nextID++);
		this.setTitulo(titulo);
	}
	
	
	

	//GETTERS Y SETTERS

	/**
	 *	Getter de anyo
	 * 	@return  anyo de el contenido (Date)
	 */
	public Date getAnyo() {
		return anyo;
	}
	
	
	/**
	 *	Setter de anyo
	 *	@param anyo (Date) anyo del contenido
	 */
	public void setAnyo(Date anyo) {
		this.anyo = anyo;
	}


	/**
	 *	Getter de titullo
	 * 	@return  titulo del Contenido (String)
	 */
	public String getTitulo() {
		return titulo;
	}


	/**
	 *	Setter del titulo
	 *	@param titulo del contenido (String)
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	/**
	 *	Getter de duracion
	 * 	@return duracion del Contenido (double)
	 */
	public double getDuracion() {
		return duracion;
	}


	/**
	 *	Setter de duraccion
	 *	@param duracion del contenido (double)
	 */
	public void setDuracion(double duracion) {
			this.duracion = duracion;
	}


	/**
	 *	Getter de id
	 * 	@return  id del Contenido (int)
	 */
	public int getId() {
		return id;
	}


	/**
	 *	Setter de Id
	 *	@param id del contenido (int)
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * Getter de Autor
	 * @return autor del contenido (Usuario)
	 */
	public Usuario getAutor() {
		return autor;
	}


	/**
	 *Setter de Autor
	 * @param autor del contenido (Usuario)
	 */ 
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
}