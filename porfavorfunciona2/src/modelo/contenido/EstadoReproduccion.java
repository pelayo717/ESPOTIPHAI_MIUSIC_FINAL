package modelo.contenido;

/**
 * Estados que se pueden dar durante la reproduccion de una cancion, album o lista
 * Mas que nada estos estados son informativos para que el usuario de no poder reproducir una cancion sepa porque es
 * MENOR si el usuario quiere escuchar contenido explicito y se le deniega
 * REPRODUCCIONES_AGOTADAS cuando un usuario registrado o sin registrar agota sus reproducciones maximas
 * OTRO cualquier otro motivo que pueda sucede durante la reproduccion
 * USUARIO_SR estos son aquellos que no se han registrado y que quieren escuchar contenido explicito y no pueden
 * VACIA cuando quieren escuchar album o lista y no hay contenido en su interior para escuchar
 * @author pelayo rodriguez
 * @author roberto pirck
 * @author manuel salvador
 */
public enum EstadoReproduccion {
	MENOR,REPRODUCCIONES_AGOTADAS,OTRO,USUARIO_SR, VACIA;
}

