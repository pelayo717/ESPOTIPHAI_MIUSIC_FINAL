package modelo.contenido;

import java.io.FileNotFoundException;
import java.io.Serializable;

import modelo.usuario.*;
import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3PlayerException;

/**
 * Clase abstacta que engloba a cancio,album,lista y contenido comentable.En esta implementamos las
 * funciones comunes a las demas y mas basicas. Se trabaja con los metadatos de los demas productos
 * como titulo del contenido o autor y tambien se desarollan las funcionalidades basicas del reproductor
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */
public abstract class Contenido implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static int nextID = 0;
	private int anyo;
	private String titulo;
	private double duracion;
	private static Mp3Player repro_mp3;
	private int id;
	private Usuario autor;
	
	/**
	 * Constructor de Contenido
	 * @param estado:  estado de la cancion
	 * @param reproducible:  si la cacion es o no reproducible
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	public Contenido(int anyo, String titulo, Usuario autor ) throws FileNotFoundException, Mp3PlayerException {
		this.setAutor(autor);
		this.setAnyo(anyo);
		this.setDuracion(0);
		this.setId(nextID++);
		this.setTitulo(titulo);
		Contenido.repro_mp3 = new Mp3Player();
	}
	
	
	

	//GETTERS Y SETTERS

	/**
	 *	Getter de anyo
	 * 	@return  anyo de el contenido (Date)
	 */
	public int getAnyo() {
		return anyo;
	}
	
	
	/**
	 *	Setter de anyo
	 *	@param anyo (Date) anyo del contenido
	 */
	public void setAnyo(int anyo) {
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
	
	/**
	 *	Funcion para anyadir a la cola de reproduccion
	 *	@param cancion_a_anyadir  string de la cancion a anyadir
	 */
	
	
	
	/**
	 *	Funcion para reproducir una cancion
	 */
	public void reproducir() {
		try {
			Contenido.repro_mp3.play();
			return;
		}catch(Mp3PlayerException pe) {
			pe.toString();
			return;
		}
		
	}
	
	
	/**
	 *	Funcion para parar una cancion
	 */
	public void parar() {		
			if(Contenido.repro_mp3 != null) {
				Contenido.repro_mp3.stop();
			}
	}
	
	
	public Mp3Player getReproductor() {
		return Contenido.repro_mp3;
	}
	
	/**
	 * Setter del reproductor mp3
	 */
	public void setMp3Player() throws FileNotFoundException, Mp3PlayerException {
		Contenido.repro_mp3 = new Mp3Player();
	}
	
	
}