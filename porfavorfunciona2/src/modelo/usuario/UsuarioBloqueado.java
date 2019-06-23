package modelo.usuario;
/**
 * Enumeracion de los estados en los que se puede encontrar un usuario(su cuenta), debido a reportes
 * POR_REPORTE es cuando ha sido realizado pero ni aceptado ni denegado
 * INDEFINIDO es cuando un reporte ha sido aceptado y el usuario ha sido bloqueado de por vida
 * TEMPORAL es cuando no fue aceptado y esta temporalmente bloqueado
 * NOBLOQUEADO si no hay ningun reporte hacia este usuario
 * @author pelayorodriguezaviles
 * @author roberto pirck
 * @author manuel salvador
 */
public enum UsuarioBloqueado {
	INDEFINIDO, TEMPORAL, NOBLOQUEADO, POR_REPORTE;
}
