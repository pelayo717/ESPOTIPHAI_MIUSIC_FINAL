package com.ESPOTIPHAI_MIUSIC.sistema;

public class ExcesoReproduccionesExcepcion extends Exception{

	private String mensaje;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExcesoReproduccionesExcepcion(String m) {
		super();
		this.mensaje = m;
		System.out.println(this.mensaje);
	}
	
	public String getMensaje() {
		return this.mensaje;
	}
}
