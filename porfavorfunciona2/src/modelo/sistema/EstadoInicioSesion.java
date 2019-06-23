package modelo.sistema;
/**
 * 
 * Enumeracion de los estados que se pueden dar cuando un usuario trata de inicar sesion.
 * Mas que nada estos estados son informativos para que el usuario de no poder entrar, sepa a que se debe
 * CORRECTO inicio de sesion sin problemas
 * DATOS_INCORRECTOS usuario ocontrasenia estan mal introducidos
 * BLOQUEADO bloqueo indefinido a un usuario
 * POR_REPORTE usuario bloqueado por un reporte pero sin ser aceptado o denegado(el reporte)
 * TEMPORAL recporte denegado y boqueo temporal
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 *
 */
public enum EstadoInicioSesion {
	CORRECTO,DATOS_INCORRECTOS,BLOQUEADO,POR_REPORTE,TEMPORAL;
}
