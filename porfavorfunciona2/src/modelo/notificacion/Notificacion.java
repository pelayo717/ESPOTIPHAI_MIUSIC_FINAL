package modelo.notificacion;


import java.io.Serializable;

import modelo.usuario.*;

/**
 *	Clase Notificacion
 */
public class Notificacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Usuario receptor;
	private String mensaje; 
	private Usuario emisor;
	
	
	public Notificacion(Usuario receptor, String mensaje, Usuario emisor) {
		this.setEmisor(emisor);
		this.setMensaje(mensaje);
		this.setReceptor(receptor);
	}
	
	
	/*=================================================================================*/
	/*=============== FUNCIONES GENERALES DE GETTERS Y SETTERS ========================*/
	/*=================================================================================*/
	
	/**
	 * Setter de receptor
	 * @param receptor receptor de la notificacion
	 */
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
		
	/**
	 * Getter de receptor
	 * @return receptor de la notificacion
	 */
	public Usuario getReceptor() {
		return receptor;
	}
	
	/**
	 * Setter de mensaje
	 * @param mensaje mensaje de la notificacion
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	/**
	 * Getter de mensaje
	 * @return mensaje de la notificacion
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Setter de emisor
	 * @param emisor emisor de la notificacion
	 */
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	/**
	 * Getter de emisor
	 * @return emisor de la notificacion
	 */
	public Usuario getEmisor() {
		return emisor;
	}
}
