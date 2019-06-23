package modelo.reporte;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import modelo.usuario.*;
import modelo.contenido.*;



/**
 * La clase reporte se encarga de crear un objeto que para una cancion dada un usuario pueda notificar la posible
 * existencia de plagio en ese contenido. De tal modo un objeto de tipo Reporte debera almacenar una cancion a la que reportar,
 * un usuario reportador y una fecha con hora,minuto y segundo. 
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */
public class Reporte implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Cancion cancion_reportada;
	private Usuario usuario_reportador;
	private LocalDate dia;
	private int hora;
	private int minuto;
	private int segundo;
	
	/**
	 * Constructor de la clase reporte, asigna los parametros de usuario y cancion a los ya mencioncionados previamente
	 * y los demas atributos relacionados con fecha y hora se toman de manera automatica 
	 * @param ur: objeto de tipo usuario que reporto la cancion
	 * @param cr: objeto de tipo cancion que ha sido reportado
	 */
	public Reporte(Usuario ur, Cancion cr) {
		this.usuario_reportador = ur;
		this.cancion_reportada = cr;
		this.dia = LocalDate.now();
		LocalDateTime tiempo = LocalDateTime.now();
		this.hora  = tiempo.getHour();
		this.minuto = tiempo.getMinute();
		this.segundo = tiempo.getSecond();
	}

	/**
	 * Funcion que establece el usuario que realizo el reporte
	 * @param aux: objeto de tipo usuario
	 */
	public void setUsuarioReportador(Usuario aux) {
		this.usuario_reportador = aux;
	}
	
	
	/**
	 * Funcion que establece la cancion en la que se basa el reporte
	 * @param aux: objeto de tipo cancion
	 */
	public void setCancionReportada(Cancion aux) {
		this.cancion_reportada = aux;
	}
	
	/**
	 * Funcion que rentorna el objeto de tipo usuario que realizo el reporte
	 * @return usuario_reportador el usuario que reporto la cancion usuario
	 */
	public Usuario getUsuarioReportador() {
		return this.usuario_reportador;
	}
	
	/**
	 * Funcion que retorna el objeto de tipo cancion sobre el que se realizo el reporte
	 * @return cancion_reportada el objeto sobre el que se reporto
	 */
	public Cancion getCancionReportada() {
		return this.cancion_reportada;
	}
	
	/**
	 * Funcion que retorna la fecha en el que se realizo el reporte
	 * @return dia retornamos el dia del reporte
	 */
	public LocalDate getFecha() {
		return this.dia;
	}
	
	/**
	 * Funcion que retorna la hora en la que se realizo el reporte
	 * @return hora retornamos la hora del reporte
	 */
	public int getHora() {
		return this.hora;
	}
	
	/**
	 * Funcion que retorna el minuto en el que realizo el reporte
	 * @return minuto retornamos el minuto del reporte
	 */
	public int getMinuto() {
		return this.minuto;
	}
	
	/**
	 * Funcion que retorna el segundo en el que realizo el reporte
	 * @return segundo retornamos el segundo del reporte
	 */
	public int getSegundo() {
		return this.segundo;
	}
}
