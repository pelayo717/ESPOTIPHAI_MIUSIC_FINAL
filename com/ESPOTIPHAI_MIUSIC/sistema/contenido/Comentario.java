package ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido;
import java.io.Serializable;
import java.util.*;

/**
 *	Clase Comentario
 */
public class Comentario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date fecha;
	private String autor;
	private String texto;
	
	/**
	 *	Constructor de Comentario
	 *	@param fecha  fecha del comentario (Date)
	 *	@param texto  texto del comentario (String)
	 */
	public Comentario(String nombre, Date fecha,String texto) {
		this.setFecha(fecha);
		this.setTexto(texto);
		this.setAutor(nombre);
	}
	
	

	
	
	//GETTERS Y SETTERS
	
	
	
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
	public void setAutor(String autor) {
		this.autor = autor;
	}

	/**
	 * Getter de texto
	 * @return the texto
	 */
	public String getAutor() {
		return autor;
	}


	/**
	 * Setter de texto
	 * @param texto del comentario
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}




	@Override
	public String toString() {
		return autor + ": " + texto ;
	}
	

	
	
}