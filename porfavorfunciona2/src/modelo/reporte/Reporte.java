package modelo.reporte;


import java.io.Serializable;
import modelo.usuario.*;
import modelo.contenido.*;

public class Reporte implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Cancion cancion_reportada;
	private Usuario usuario_reportador;
	
	public Reporte(Usuario ur, Cancion cr) {
		this.usuario_reportador = ur;
		this.cancion_reportada = cr;
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
}
