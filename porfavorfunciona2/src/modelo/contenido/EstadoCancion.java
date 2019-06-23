package modelo.contenido;

/**
 * Enumeracion de los estados en los que puede estar una cancion
 * VALIDA cancion perectamente validada y subida
 * EXPLICITA cancion subuida pero considerada de contenido explicito
 * PENDEINTEAPROBACION cancion recientemente subida pero sin validar
 * PENDIENTEMODIFICACION cancion subida y denegada en primera instancia a la espera de ser modificada durante un periodo limitado de tiempo
 * PLAGIO cancion bloqueada al ser considerada de plagio
 * ELIMINADA cancion que ha sido eliminada
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */
public enum EstadoCancion {
	VALIDA,EXPLICITA,PENDIENTEAPROBACION,PENDIENTEMODIFICACION,PLAGIO,ELIMINADA;
}