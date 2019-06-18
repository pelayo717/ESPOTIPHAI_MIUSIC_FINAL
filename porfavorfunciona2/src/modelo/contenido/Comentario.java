package modelo.contenido;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import modelo.usuario.*;

/**
 *	Clase Comentario
 */
public class Comentario implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate fecha;
	private String texto;
	private Usuario comentador;
	private int hora;
	private int minuto;
	private int segundo;
	
	/**
	 *	Constructor de Comentario
	 *	@param fecha  fecha del comentario (Date)
	 *	@param texto  texto del comentario (String)
	 */
	public Comentario(String texto,Usuario comentador) {
		this.fecha = LocalDate.now();
		this.setTexto(texto);
		this.setComentador(comentador);
		LocalDateTime tiempo = LocalDateTime.now();
		this.hora  = tiempo.getHour();
		this.minuto = tiempo.getMinute();
		this.segundo = tiempo.getSecond();
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
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Setter de fecha
	 * @param fecha2 del comentario
	 */
	public void setFecha(LocalDate fecha2) {
		this.fecha = fecha2;
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
	
	public int getHora() {
		return this.hora;
	}
	
	public int getMinuto() {
		return this.minuto;
	}
	
	public int getSegundo() {
		return this.segundo;
	}

	
	
}