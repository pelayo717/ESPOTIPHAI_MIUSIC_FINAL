package ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.notificacion;


import java.io.Serializable;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.UsuarioBloqueado;


/**
 *	Clase Notificacion
 */
public class Notificacion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario receptor;
	private String mensaje; 
	private Usuario emisor;
	private TipoNotificacion tipo;
	private Cancion adjunto;
	
	
	public Notificacion(Usuario receptor, String mensaje, Usuario emisor, TipoNotificacion tipo) {
		this.setEmisor(emisor);
		this.setMensaje(mensaje);
		this.setReceptor(receptor);
		this.setTipoNotificacion(tipo);
		this.adjunto = null;
	}
	
	public Notificacion(Usuario receptor, String mensaje, Usuario emisor, TipoNotificacion tipo,Cancion c) {
		this.setEmisor(emisor);
		this.setMensaje(mensaje);
		this.setReceptor(receptor);
		this.setTipoNotificacion(tipo);
		this.adjunto = c;
	}
	
	
	//GETTERS Y SETTERS
	
	public Cancion getAdjunto() {
		if(this.adjunto != null) {
			return this.adjunto;
		}
		return null;
	}
	
	public void setAdjunto(Cancion c) {
		if(c != null) {
			this.adjunto = c;
		}
	}
	
	/**
	 * Getter de receptor
	 * @return receptor de la notificacion
	 */
	public Usuario getReceptor() {
		return receptor;
	}

	/**
	 * Setter de receptor
	 * @param receptor receptor de la notificacion
	 */
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
	
	
	/**
	 * Getter de mensaje
	 * @return mensaje de la notificacion
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Setter de mensaje
	 * @param mensaje mensaje de la notificacion
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	/**
	 * Getter de tipo de notificacion
	 * @return tipo de notificacion
	 */
	public TipoNotificacion getTipoNotificacion() {
		return tipo;
	}

	/**
	 * Setter de tipo de notificacion
	 * @param tipo tipo de la notificacion
	 */
	public void setTipoNotificacion(TipoNotificacion tipo) {
		this.tipo = tipo;
	}
	
	
	/**
	 * Getter de emisor
	 * @return emisor de la notificacion
	 */
	public Usuario getEmisor() {
		return emisor;
	}

	/**
	 * Setter de emisor
	 * @param emisor emisor de la notificacion
	 */
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	
}
