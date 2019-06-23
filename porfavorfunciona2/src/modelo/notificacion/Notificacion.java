package modelo.notificacion;


import java.io.Serializable;

import modelo.usuario.*;

/**
 *	Esta clase nos permite la comunicacion entre clases como sistema y usuario o entre propios objetos de tipo usuario.
 *	Implementa metodos getters y setters unicamente ya que toda la funcionalidad de envio se realiza a mayor nivel.
 *	La estructura de la notificacion es basica, un receptor un emisor y un mensaje a transmitir.
 */

/**
 *  Esta clase nos permite la comunicacion entre clases como sistema y usuario o entre propios objetos de tipo usuario.
 *	Implementa metodos getters y setters unicamente ya que toda la funcionalidad de envio se realiza a mayor nivel.
 *	La estructura de la notificacion es basica, un receptor un emisor y un mensaje a transmitir.
 * @author pelayo rodriguez
 * @author robert pirck
 * @author manuel salvador
 */
public class Notificacion implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Usuario receptor;
	private String mensaje; 
	private Usuario emisor;
	
	/**
	 * Constructor de la clase notificacion, establecemos mediante los parametros pasados la construccion
	 * del objeto notificacion usado entre usuarios y el sistema para informar de sucesos y otros eventos
	 * @param receptor: usuario que va a recibir la notificacion
	 * @param mensaje: informacion de la notificacion
	 * @param emisor: usuario al que va dirigido la notificacion
	 */
	public Notificacion(Usuario receptor, String mensaje, Usuario emisor) {
		this.setEmisor(emisor);
		this.setMensaje(mensaje);
		this.setReceptor(receptor);
	}
	
	
	/*=================================================================================*/
	/*=============== FUNCIONES GENERALES DE GETTERS Y SETTERS ========================*/
	/*=================================================================================*/
	
	/**
	 * Setter de receptor establece el usuario que recibe el mensaje
	 * @param receptor receptor de la notificacion
	 */
	public void setReceptor(Usuario receptor) {
		this.receptor = receptor;
	}
		
	/**
	 * Getter de receptor retorna el usuario que va a recibir la notificacion
	 * @return receptor de la notificacion
	 */
	public Usuario getReceptor() {
		return receptor;
	}
	
	/**
	 * Setter de mensaje establece el mensaje de la notificacion
	 * @param mensaje mensaje de la notificacion
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	/**
	 * Getter de mensaje retorna el mensaje que va incrustado en la notificacion
	 * @return mensaje de la notificacion
	 */
	public String getMensaje() {
		return mensaje;
	}

	/**
	 * Setter de emisor establece el usuasio que va a enviar la notificacion
	 * @param emisor emisor de la notificacion
	 */
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	/**
	 * Getter de emisor retorna el usuario que va enviar la notificacion
	 * @return emisor de la notificacion
	 */
	public Usuario getEmisor() {
		return emisor;
	}
}
