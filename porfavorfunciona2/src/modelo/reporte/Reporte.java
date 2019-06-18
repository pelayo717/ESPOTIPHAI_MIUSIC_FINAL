package modelo.reporte;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import modelo.usuario.*;
import modelo.contenido.*;

public class Reporte implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Cancion cancion_reportada;
	private Usuario usuario_reportador;
	private LocalDate dia;
	private int hora;
	private int minuto;
	private int segundo;
	
	public Reporte(Usuario ur, Cancion cr) {
		this.usuario_reportador = ur;
		this.cancion_reportada = cr;
		this.dia = LocalDate.now();
		LocalDateTime tiempo = LocalDateTime.now();
		this.hora  = tiempo.getHour();
		this.minuto = tiempo.getMinute();
		this.segundo = tiempo.getSecond();
	}

	public void setUsuarioReportador(Usuario aux) {
		this.usuario_reportador = aux;
	}
	
	public void setCancionReportada(Cancion aux) {
		this.cancion_reportada = aux;
	}
	
	/**
	 * @return the usuarioReportador
	 */
	public Usuario getUsuarioReportador() {
		return this.usuario_reportador;
	}
	
	/**
	 * @return the cancionReportada
	 */
	public Cancion getCancionReportada() {
		return this.cancion_reportada;
	}
	
	public LocalDate getFecha() {
		return this.dia;
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
