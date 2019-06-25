package modelo.contenido;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import modelo.status.Status;

/**
 * La clase comentario de por si es muy sencilla y tiene pocas funcionalidades pero en el prodcuto final
 * supone un importante modulo para darle mas flexibilidad a las canciones y albumes que son quien contiene
 * a esta. Esta clase utiliza funciones muy basicas y aunque contiene multiples atributos de tiempo, strings o
 * otros comentarios el desarrollo de esta ha sido sencilla y muy productiva por la recursividad
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */
public class Comentario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private LocalDate fecha;
	private String texto;
	private String comentador;
	private ArrayList<Comentario> subComentarios;
	private int hora;
	private int minuto;
	private int segundo;
	
	/**
	 *	Constructor de Comentario
	 *	@param fecha  fecha del comentario (Date)
	 *	@param texto  texto del comentario (String)
	 */
	public Comentario(String texto,String comentador) {
		this.fecha = LocalDate.now();
		this.setTexto(texto);
		this.setComentador(comentador);
		this.subComentarios = new ArrayList<Comentario>();
		LocalDateTime tiempo = LocalDateTime.now();
		this.hora  = tiempo.getHour();
		this.minuto = tiempo.getMinute();
		this.segundo = tiempo.getSecond();
	}
	
	

	
	/**
	 *	Funcion para añadir un subcomentario al comentario
	 * 	@return  OK si no hay errores y ERROR de lo contrario
	 */
	public Status anyadirSubComentario(Comentario comentario) {
		if(comentario == null) {
			return Status.ERROR;
		}
		if (this.subComentarios.add(comentario) == true) {
			return Status.OK;
		} else {
			return Status.ERROR;
		}
	}
	
	
	public Status buscarYEliminarSubComentario(Comentario comentario){
		if (this.subComentarios.isEmpty() == false){
			for(Iterator<Comentario> iteratorComentario = this.subComentarios.iterator(); iteratorComentario.hasNext();) {
				Comentario subcomentario = iteratorComentario.next();
				if(subcomentario.equals(comentario)) {
					subcomentario.eliminarSubComentarios();
					iteratorComentario.remove();
				} else {
					buscarYEliminarSubComentario(subcomentario);
				}
			}
			
			return Status.OK;
		}		
		return Status.ERROR;
	}
	
	/**
	 * Funcion encargada de eliminar los subcomentarios de un comentario
	 * return OK si todo se borro, ERROR si no habia para borrar
	 */
	public Status eliminarSubComentarios() {
		if (this.subComentarios.isEmpty() == false){
			for(Iterator<Comentario> iteratorComentario = this.subComentarios.iterator(); iteratorComentario.hasNext();) {
				Comentario subcomentario = iteratorComentario.next();
				subcomentario.eliminarSubComentarios();
				iteratorComentario.remove();
			}
			
			return Status.OK;
		}		
		return Status.ERROR;
	}

	/**
	 * Funcion encargada de eliminar un subcomentario concreto
	 * @param c: comentario que pretendemos borrar de la lista de subcomentarios
	 * @return OK si se borro correctamente,ERROR en otro caso
	 */
	public Status eliminarSubComentario(Comentario c) {
		
		if (this.subComentarios.isEmpty() == false){
			
			for(Iterator<Comentario> iteratorComentario = this.subComentarios.iterator(); iteratorComentario.hasNext();) {

				Comentario subcomentario = iteratorComentario.next();
				iteratorComentario.remove();
				if(subcomentario.equals(c) == true) {
					this.getSubComentarios().remove(c);
					return Status.OK;
				}
				
			}
		}
		
		return Status.ERROR;
	}
	
	//GETTERS Y SETTERS
	
	/**
	 * Funcion que devuelve el texto del comentario
	 * return el texto que debe ser distinto de null
	 */
	@Override
	public String toString() {
		return texto;
	}

	/**
	 * Funcion que retorna el array de subcomentarios del comentario
	 * @return esta inicializado y siempre se devuelve aunque este vacio,
	 * no contemplamos el caso de null, ya que ese caso se cubre a mas alto nivel
	 */
	public ArrayList<Comentario> getSubComentarios() {
		return subComentarios;
	}

	/**
	 * Establece los subcomentarios de un comentario
	 * @param subComentarios array de comentarios para añadir al comentario dado
	 */
	public void setSubComentarios(ArrayList<Comentario> subComentarios) {
		this.subComentarios = subComentarios;
	}

	/**
	 * Establece el usuario que esta comentando
	 * @param c cadena que indica el nombre de la persona que realizo el comentario
	 */
	public void setComentador(String c) {
		this.comentador = c;
	}
	
	/**
	 * Funcion que retorna el nombre de la persona que realizo el comentario
	 * @return el nombre del usuario que comento, no contemplamos el caso de null,
	 *  ya que ese caso se cubre a mas alto nivel
	 */
	public String getComentador() {
		return this.comentador;
	}
	
	/**
	 * Funcion que retorna la fecha en la que se realizo el comentairo
	 * @return la fecha del comentario
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * Funcion que establece la fecha en la que se realizo el comentario
	 * @param fecha2: fecha que se asignara al comentario, para indicar cuando se realizo
	 */
	public void setFecha(LocalDate fecha2) {
		this.fecha = fecha2;
	}

	/**
	 * Funcion que retorna el texto del comentario
	 * @return el texto en formato string de comentario
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
	
	/**
	 * Funcion que retorna la hora en la que se realizo el comentario
	 * @return la hora del comentario en formato int
	 */
	public int getHora() {
		return this.hora;
	}
	
	/**
	 * Funcion que retorna el minuto de la hora correspondiente en la que se realizo el comentario
	 * @return el minuto del comentario en formato int
	 */
	public int getMinuto() {
		return this.minuto;
	}
	
	/**
	 * Funcion que retorna el segundo del minuto de la hora correspondiente en la que se realizo el comentario
	 * @return el segundo del comentario en formato int
	 */
	public int getSegundo() {
		return this.segundo;
	}

	
	
}