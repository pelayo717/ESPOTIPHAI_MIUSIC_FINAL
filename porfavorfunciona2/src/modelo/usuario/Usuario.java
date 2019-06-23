package modelo.usuario;

import java.io.Serializable;
import java.time.LocalDate;

import modelo.status.*;
import modelo.notificacion.*;
import modelo.sistema.Sistema;
import modelo.contenido.*;

import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;

import java.util.ArrayList;



/**
 * El modulo de usuario es el encargado de almacenar el estado de un de ellos y de poder realizar los cambios en los usuarios
 * para pasarlos a estado de premium o al estado de bloqueado entre otros. Por otra parte establece la funciones mas básicas
 * de getters y setters que son necesarias para el uso normal de la clase e incluye funciones necesarias para la interaccion
 * con otros módulos de la aplicación que más adelante explicaremos.
 *
 * @author Pelayo Rodriguez, Manuel Salvador y Roberto Pirck 
 */
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static int nextID = 0;
	
	/*CADENAS (NOMBRE USUARIO, NOMBRE AUTOR, CONTRASEÑA)*/
	private String nombre_usuario;
	private String nombre_autor;
	private String contrasena;

	/*FECHAS (NACIMIENTO,REGISTRO,INICIO DE PREMIUM, INICIO DE BLOQUEADO)*/
	private LocalDate fecha_nacimiento;
	private LocalDate fecha_registro;
	private LocalDate fecha_inicio_pro;
	private LocalDate fecha_inicio_bloqueado;
	
	
	/*ENTEROS (IDENTIFICADOR Y NUMERO DE REPRODUCCIONES)*/
	private int numero_repro_totales;
	private int numero_repro_para_pro;
	
	private int id;
	private int contenido_escuchado_sin_ser_premium;
	
	/*BOOLEANO (ES PREMIUM)*/
	private boolean premium;
	
	/*ESTADO BLOQUEADO (TEMPORAL,INDEFINIDO,NO...)*/
	private UsuarioBloqueado bloqueado;
	
	/*ARRAYS (USUARIOS A LOS QUE SIGUE, USUARIOS QUE LE SIGUEN, LISTAS PROPIAS, CANCIONES PROPIAS, ALBUMES PROPIOS)*/
	private ArrayList<Usuario> seguidores;
	private ArrayList<Usuario> seguidos;
	
	private ArrayList<Lista> listas;
	private ArrayList<Cancion> canciones;
	private ArrayList<Album> albumes;
	
	private ArrayList<Notificacion> notificaciones_propias;

	
	/**
	 * Constructor de la clase Usuario, donde se inicializan los diferentes atributos y se les asignas sus respectivos valores
	 * En esta funcion asignamos datos importantes al usuario que luego nos seran necesarios. De primeras indicamos que el usuario
	 * no se encuentra bloqueado y entre otros datos le asignamos su identificador que sera único entre los usuarios totales del sistema
	 *
	 * @param nombre_usuario: nombre que se le asginar como usuario de la aplicacion para acciones como iniciar sesión o incluir comentarios entre otras funciones
	 * @param nombre_autor: nombre que se le va asignar como persona que aporta contenido a la aplicacion y que puede ser escuchado por otros usuarios o autores
	 * @param fecha_nacimiento: fecha de nacimiento del usuario
	 * @param contraseña: contraseña que el usuario pone a su cuenta
	 * @param id: indica el identificador que se le asigna al usuario
	 */
	
	public Usuario(String nombre_usuario, String nombre_autor, LocalDate fecha_nacimiento, String contrasena) {
		
		this.nombre_usuario = nombre_usuario;
		this.nombre_autor = nombre_autor;
		this.contrasena = contrasena;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_registro = LocalDate.now();
		this.fecha_inicio_pro = null; 		//No hay fecha de inicio de premium
		this.fecha_inicio_bloqueado = null; //No hay fecha de inicio de bloqueo
		
		this.numero_repro_totales = 0; 	//Todavia nadie ha escuchado canciones de este usuario con lo cual sus reproducciones estan a 0
		this.numero_repro_para_pro = 0;

		this.premium = false; 	//Al crear el objeto usuario le indicamos que este no es un usuario PREMIUM por el momento
		this.id = Usuario.nextID++;
		this.contenido_escuchado_sin_ser_premium = 0;
		
		this.seguidores = new ArrayList<Usuario>();
		this.seguidos = new ArrayList<Usuario>();
		this.listas = new ArrayList<Lista>();
		this.canciones = new ArrayList<Cancion>();
		this.albumes = new ArrayList<Album>();
		this.notificaciones_propias = new ArrayList<Notificacion>();
		this.bloqueado = UsuarioBloqueado.NOBLOQUEADO; //De primeras el usuario no esta bloqueado
	}
	
	/*=================================================================================*/
	/*=============== FUNCIONES GENERALES DE GETTERS Y SETTERS ========================*/
	/*=================================================================================*/
	
	/**
	 * Funcion que establece el nombre de usuario al objeto
	 * @param aux: cadena que recoge el nombre del usuario
	 */
	public void setNombreUsuario(String aux) {
		this.nombre_usuario = aux;
	}
	
	/**
	 * Funcion que retorna el nombre de autor que recibe un usuario de la aplicacion
	 * @return nombre_usuario: devuelve el nombre de autor del usuario
	 */
	public String getNombreUsuario() {
		return this.nombre_usuario;
	}
	
	/**
	 * Funcion que establece el nombre de autor al objeto
	 * @param aux: cadena que recoge el nombre del autor
	 */
	public void setNombreAutor(String aux) {
		this.nombre_autor = aux;
	}
	
	/**
	 * Funcion que devuelve el nombre del usuario que utiliza la aplicacion
	 * @return nombre_autor: devuelve el nombre del usuario
	 */
	public String getNombreAutor() {
		return this.nombre_autor;
	}
	
	/**
	 * Funcion que establece la fecha de nacimiento del usuario
	 * @param aux: objeto de tipo LocalDate que almacena la fecha de nacimiento
	 */
	public void setFechaNacimiento(LocalDate aux) {
		this.fecha_nacimiento = aux;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que el usario nacio 
	 * @return fecha_nacimiento: fecha en la que el usuario nacio
	 */
	public LocalDate getFechaNacimiento() {
		return this.fecha_nacimiento;
	}
	
	/**
	 * Funcion que establece la fecha de registro del usuario en el sistema
	 * @param aux: objeto de tipo LocalDate que almacena la fecha en la que un usuario se registro
	 */
	public void setFechaRegistro(LocalDate aux) {
		this.fecha_registro = aux;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que el usuario se regitro
	 * @return fecha_registro: fecha en la que el usuario se registro en la aplicacion
	 */
	public LocalDate getFechaRegistro() {
		return this.fecha_registro;
	}
	
	/**
	 * Funcion que establece la contrasenia que escogio el usuario en su cuenta
	 * @param aux: cadena que almacena la contrasenia
	 */
	public void setContrasena(String aux) {
		this.contrasena = aux;
	}
	
	/**
	 * Funcion que devuelve la constrasenia del usuario
	 * @return contraseña: devuelve la cadena donde se almaceno la contrasenia del usuario cuando se registro
	 */
	public String getContrasena() {
		return this.contrasena;
	}
	
	/**
	 * Funcion que establece la fecha del inicio de premium a un usuario
	 * @param aux: objeto de tipo LocalDate que alberga la fecha de inicio de premium a un usuario
	 */
	public void setFechaInicioPro(LocalDate aux) {
		this.fecha_inicio_pro = aux;
	}
	
	/**
	 * Funcion que devuelve la fecha de cuando el usuario se hizo premium
	 * @return fecha_inicio_pro: indica la fecha en la que el usuario empezo a ser premium 
	 */
	public LocalDate getFechaInicioPro() {
		return this.fecha_inicio_pro;
	}
	
	/**
	 * Funcion que establece el estado premium de un usuario a activado o desactivado
	 * @param aux:booleano que determina si es premium o no un usuario
	 */
	public void setPremium(boolean aux) {
		this.premium = aux;
	}
	
	/**
	 * Funcion que devuelve un boolean indicando si es premium o no 
	 * @return premium: boolean que indica si el usuario es prrmium o no 
	 */
	public boolean getPremium() {
		return this.premium;
	}
	
	/**
	 * Funcion que establece el estado de bloqueado a un usuario(puede ser tambien elestado NOBLOQUEADO)
	 * @param aux: objeto de tipo UsuarioBloqueado que determina el estado del usuario
	 */
	public void setEstadoBloqueado(UsuarioBloqueado aux) {
		this.bloqueado = aux;
	}
	
	/**
	 * Funcion que devuelve el estado de bloqueado del usuario
	 * @return bloqueado: devuelve el estado de si el usuario esta bloqueado(y por cuanto tiempo) o si no lo esta
	 */
	public UsuarioBloqueado getEstadoBloqueado() {
		return this.bloqueado;
		
	}
	
	/**
	 * Funcion que establece el identificador a un usuario
	 * @param aux: entero que indica la identificacion para el usuario
	 */
	public void setId(int aux) {
		this.id = aux;
	}
	
	/**
	 * Funcion que devuelve el identificador del usuario
	 * @return id: devuelve el identificador del usuario 
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Funcion que establece la fecha del inicio de bloqueo a un usuario
	 * @param aux: objeto de tipo LocalDate que alberga la fecha de inicio de bloqueo a un usuario
	 */
	public void setFechaInicioBloqueado(LocalDate aux) {
		this.fecha_inicio_bloqueado = aux;
	}
	
	/**
	 * Funcion que devuelve la fecha en la que un usuario fue bloqueado si es que lo esta
	 * @return fecha_inicio_bloqueado: devuelve la fecha en la que fue bloqueado
	 */
	public LocalDate getFechaInicioBloqueado() {
		return this.fecha_inicio_bloqueado;
	}
	
	/**
	 * Funcion que establece el numero de reproducciones totales de un usuario
	 * @param aux: entero que indica el numero de reproducciones
	 */
	public void setNumeroReproducciones(int aux) {
		this.numero_repro_totales = aux;
	}
	
	/**
	 * Funcion que devuelve el numero de reproducciones del usuario
	 * @return numero_repro: suma total de todas las reproducciones de cada contenido realizadas por usuarios que no fueran su dueño 
	 */
	public int getNumeroReproducciones() {
		return this.numero_repro_totales;
	}
	
	/**
	 * Funcion que establece el numero de reproducciones para que el usuario pase a ser premium
	 * @param aux: entero que indica el numero de reproducciones
	 */
	public void setNumeroReproParaPro(int aux) {
		this.numero_repro_para_pro = aux;
	}
	
	/**
	 * Funcion que retorna el numero de reproducciones para ser premium
	 * @return numero_repro_para_pro: entero que indica las reproducciones que lleva el usuario para ser pro
	 */
	public int getNumeroReproParaPro() {
		return this.numero_repro_para_pro;
	}
	
	/**
	 * Funcion que resetea a 0 el numero de reproducciones para ser premium
	 */
	public void resetNumeroReproParaPro() {
		this.numero_repro_para_pro = 0;
	}
	
	/**
	 * Suma en uno para indicar que el usuario ha escuchado otra reproduccion sea suya o no
	 */
	public void addContenidoEscuchadoSinSerPremium() {
		this.contenido_escuchado_sin_ser_premium++;
	}
	
	/**
	 * Funcion que retorna el numero de contenidos que ha escuchado un usuario
	 * @return contenido_escuchado_sin_ser_premium: entero que nos retorna el contenido escuchado por el usuario(siempre 0 o mayor a 0)
	 */
	public int getContenidoEscuchadoSinSerPremium() {
		return this.contenido_escuchado_sin_ser_premium;
	}
	
	/**
	 * Funcion que resetea a 0 el contenido que ha escuchado un usuario sin ser premium
	 */
	public void resetContenidoEscuchadosSinSerPremium() {
		this.contenido_escuchado_sin_ser_premium = 0;
	}
	
	
	/**
	 * Devuelve el array de usuarios a los que sigue este usuario
	 * @return seguidos: array de usuarios que este sigue
	 */
	public ArrayList<Usuario> getSeguidos() {
		return this.seguidos;
	}
	
	/**
	 * Devuelve el array de usuarios que siguen a este usuario
	 * @return seguidores: array de seguidores de este usuario
	 */
	public ArrayList<Usuario> getSeguidores(){
		return seguidores;
	}
		
	/**
	 * Devuelve el array de listas de un usuario 
	 * @return listas: arraylist de listas
	 */
	public ArrayList<Lista> getListas() {
		return this.listas;
	}
	
	/**
	 * Devuelve el array de canciones de un usuario
	 * @return canciones: arraylist de canciones
	 */
	public ArrayList<Cancion> getCanciones() {
		return this.canciones;
	}
	
	/**
	 * Devuelve el array de albumes de un usuario
	 * @return albumes: arraylist de albumes
	 */
	public ArrayList<Album> getAlbumes() {
		return this.albumes;
	}
	
	/**
	 * Devuelve las notificaciones que se han realizado durante el uso del sistema
	 * @return retorna las notificaciones totales de los usuarios
	 */
	public ArrayList<Notificacion> getNotificacionesTotales(){
		return this.notificaciones_propias;
	}
	
	/*=================================================================================*/
	/*======================== FUNCIONES GENERALES DE USUARIO =========================*/
	/*=================================================================================*/
	
	/**
	 * Funcion que aumenta el numero de reproducciones del usuario en 1
	 * @param x: umbral de reproducciones que si se supera, el usuario se hace premium durante un mes gratis
	 * @return numero_repro: devolvemos el numero de reproducciones del usuario actualizado
	 */
	public int sumarReproduccion(int x) {
		this.numero_repro_totales = this.numero_repro_totales + 1;
		this.numero_repro_para_pro = this.numero_repro_para_pro + 1;
		if(this.numero_repro_para_pro >= x && this.premium == false) {
			mejorarCuentaPorReproducciones();
		}
		return this.numero_repro_para_pro;
	}
	
	
	/**
	 * Funcion con la que un usuario puede seguir a otro usuario
	 * @param x: Usuario al que queremos seguir
	 * @return true si se ejecuta correctamente, false si hay algun error
	 */
	public boolean seguirUsuario(Usuario x) { 

			if(this.seguidos.contains(x) == true) {//Ya se sigue al usuario
				return false;
			}else {
				this.seguidos.add(x);
				x.incluirSeguidor(this);
				return true;
			}
	}
	
	/**
	 * Funcion con la que un usuario puede dejar de seguir a otro usuario
	 * @param x: Usuario al que queremos dejar de seguir
	 * @return true si se ejecuta correctamente la funcion y le ha dejado de seguir, false si hay algun error
	 */
	public boolean dejarDeSeguirUsuario(Usuario x) {
				
		if(this.seguidos.contains(x) == true) {
			this.seguidos.remove(x);		
			x.quitarSeguidor(this);
			return true;
		} else {
			return false;
		}	
	}
	
	/**
	 * Funcion con la cual incluimos en los seguidores de un usuario dado, a otro usuario que le ha comenzado a seguir
	 * @param x: Usuario que nos ha comenzado a seguir
	 * @return true si el usuario no se econtraba en el array y se ha podido incluir correctamente, false si ya se encontraba
	 */
	public boolean incluirSeguidor(Usuario x) {
		if(this.seguidores.contains(x)) {
			return false;
		}else {
			this.seguidores.add(x);
			return true;
		}
	}
	
	/**
	 * Funcion con la cual quitamos en los seguidores de un usuario dado, a otro que le ha dejado de seguir
	 * @param x: Usuario que nos ha dejado de seguir
	 * @return true si se encontraba en el array y se ha eliminado correctamente, false si no se encontraba
	 */
	public boolean quitarSeguidor(Usuario x) {
		
		if(this.seguidores.contains(x) == false) {
			return false;
		}else {
			this.seguidores.remove(x);
			return true;
		}
	}
	
	/**
	 * Esta funcion se encarga de eliminar un usuario dado del array de seguidos si es que se encuentra en el.
	 * Esta funcion unicamente se utiliza al eliminar la cuenta de un usuario.
	 * @param usuario: Usuario que podria ser un seguido, y necesario para eliminar la relacion de this -> usuario
	 * @return true si se encuentra y se puede eliminar la relacion de seguido, false en otro caso
	 */
	public boolean eliminarSeguido(Usuario usuario) {
		if(this.seguidos.contains(usuario)) {
			this.seguidos.remove(usuario);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Esta funcion se encarga de eliminar un usuario dado del array de seguidores si es que se encuentra en el.
	 * Esta funcion unicamente se utiliza al eliminar la cuenta de un usuario.
	 * @param usuario: Usuario que podria ser un seguidor, y necesario para eliminar la relacion de this <- usuario
	 * @return true si se encuentra y se puede eliminar la relacion de seguidor, false en otro caso
	 */
	public boolean eliminarSeguidor(Usuario usuario) {		
		if(this.seguidores.contains(usuario)) {
			this.seguidores.remove(usuario);
			return true;
		}else {
			return false;
		}
	}
	
	
	
	/*===========================================*/
	/*========== LOCK/UPDATE CUENTA =============*/
	/*===========================================*/
	
	/**
	 * Funcion que mejora la cuenta de un usuario modificando los atributos necesarios 
	 * y poniendo la fecha de inicio pro a la fecha actual
	 */
	public void mejorarCuentaPorReproducciones() {
		fecha_inicio_pro = LocalDate.now();
		premium = true;
		Notificacion n = new Notificacion(this,"Felicidades, el numero de reproducciones de sus temas ha superando el umbral y ha pasado a ser un usuario Premium",Sistema.sistema.getUsuariosTotales().get(0));
		this.notificaciones_propias.add(n);
		Sistema.sistema.getUsuariosTotales().get(0).notificaciones_propias.add(n);
	}
	
	/**
	 * Permite mejorar la cuenta de un usuario a premium introduciendo su tarjeta de credito y actualizando sus datos al nuevo estado de PREMIUM
	 * @param numero_tarjeta
	 * @return retorna OK si se acepto la transferencia y se hizo premium al usuario,
	 * ERROR si se dio alguno de los problemas(fallo de conexion, tarjeta no valida u otro problema no identificado).
	 */
	public Status mejorarCuentaPago(String numero_tarjeta){
		try {
			TeleChargeAndPaySystem.charge(numero_tarjeta, "Mejora de la cuenta a estado PREMIUM", Sistema.sistema.getPrecioPremium());
			this.premium = true;
			this.fecha_inicio_pro = LocalDate.now();
			Notificacion n = new Notificacion(this,"Felicidades, ahora usted puede disfrutar de las ventajas de ser PRO durante 1 mes",Sistema.sistema.getUsuariosTotales().get(0));
			this.notificaciones_propias.add(n);
			Sistema.sistema.getUsuariosTotales().get(0).notificaciones_propias.add(n);
			return Status.OK;
		}catch(FailedInternetConnectionException fe) {
			fe.toString();
			return Status.ERROR;
		}catch(InvalidCardNumberException ie) {
			ie.toString();
			return Status.ERROR;
		}catch(OrderRejectedException re) {
			re.toString();
			return Status.ERROR;
		}
	}

	/**
	 * Funcion que baja la cuenta del estatus de premium a una cuenta normal 
	 * que no paga mensualmente
	 */
	public void empeorarCuenta() {
		this.setPremium(false);
		this.resetNumeroReproParaPro();
		this.setFechaInicioPro(null);
		this.resetContenidoEscuchadosSinSerPremium();
	}
	
	/**
	 * Funcion que bloquea la cuenta del usuario indefinidamente modificando los atributos correspondientes
	 */		
	public void bloquearCuentaIndefinido() {
		this.setEstadoBloqueado(UsuarioBloqueado.INDEFINIDO);
		this.setFechaInicioBloqueado(LocalDate.now());
	}
	
	/**
	 * Funcion que bloquea la cuenta temporalmente del usuario modificando los atributos correspondientes
	 */
	public void bloquearCuentaTemporal() {
		this.setEstadoBloqueado(UsuarioBloqueado.TEMPORAL);
		this.setFechaInicioBloqueado(LocalDate.now());
		
	}
	
	/**
	 * Funcion que bloquea la cuenta temporalmente del usuario hasta que el administrador acepte o deniegue el reporte
	 * por el que se bloqueo al usuario
	 */
	public void bloqueoCuentaPorReporte() {
		this.setEstadoBloqueado(UsuarioBloqueado.POR_REPORTE);
	}
	
	/**
	 * Funcion que desbloquea la cuenta del usuario modificando los atributos correspondientes
	 */
	public void desbloquearCuenta() {
		this.setEstadoBloqueado(UsuarioBloqueado.NOBLOQUEADO);
		this.setFechaInicioBloqueado(null);
	}
	
	/*===========================================*/
	/*================ CANCIONES ================*/
	/*===========================================*/
	
	/**
	 * Funcion que añade el contenido a la listas de canciones
	 * @param c: Contenido que se va a incluir a la listas de canciones 
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean anyadirACancionesPersonales(Cancion c) {
		if(this.canciones.contains(c)) {
			return false; //Ya esta contenida la cancion
		} else {
			this.canciones.add(c);
		}
		return true;
	}
	
	/**
	 * Funcion elimina el contenido pasado de la lista de canciones
	 * @param c: Contenido que se va a eliminar de la lista de canciones
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean eliminarDeCancionesPersonales(Cancion c) {
		if(this.canciones.contains(c)) {
			c.eliminarAudio();
			this.canciones.remove(c);
			return true;
		} else
			return false;
	}
	
	/*===========================================*/
	/*================ ALBUMES ==================*/
	/*===========================================*/
	
	/**
	 * Funcion que añade al album de usuario el contenido pasado como argumento
	 * @param c: Contenido que se va a incluir al album 
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean anyadirAAlbumesPersonales(Album c) {
		if(this.albumes.contains(c)) {
			return false; //Ya existe ese album en el array
		} else {
			this.albumes.add(c);
		}
		return true; 
	}
	
	/**
	 * Funcion que elimina el contenido de la lista de albumes 
	 * @param c: Contenido que se va a eliminar al album 
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean eliminarDeAlbumesPersonales(Album c) {
		if(this.albumes.contains(c)) {
			this.albumes.remove(c);
			return true;
		} else
			return false;
	}
	
	/*===========================================*/
	/*================= LISTAS ==================*/
	/*===========================================*/
	
	/**
	 * Funcion que va a incluir el contenido a la lista Listas 
	 * @param c: Contenido que se va a incluir a la lista de Listas  
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean anyadirAListasPersonales(Lista c) {
		
		if(this.listas.contains(c) == true) {
			return false; //Ya esta esa lista
		}else {
			this.listas.add(c);
		}
		return true;
	}

	/**
	 * Funcion que elimina el contenido de la lista de Listas
	 * @param c: Contenido que se va a eliminar de la listas
	 * @return: true si se ha realizado correctamente, false si no
	 */
	public boolean eliminarDeListasPersonales(Lista c) {
		if(this.listas.contains(c)) {
			this.listas.remove(c);
			return true;
		} else
			return false;
	}
	
	/*===========================================*/
	/*=========== FOLLOW/UNFOLLOW ===============*/
	/*===========================================*/
	
	/**
	 * Permite a un autor seguir a otro autor
	 * @param autor
	 * @return devuelve OK si se llevo a cabo la tarea de seguimiento o ERROR si no fue asi
	 */
	public Status follow(Usuario autor) {
		
		if(autor == null) {
			return Status.ERROR;
		}
				
		if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == false && Sistema.sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			for(Usuario totales:Sistema.sistema.getUsuariosTotales()) {
				
				if(totales.getNombreAutor().equals(autor.getNombreAutor()) == true) {

					if(Sistema.sistema.getUsuarioActual().seguirUsuario(totales) == false) {			
						return Status.ERROR;
					}
					
					this.enviarNotificacion(totales,Sistema.sistema.getUsuarioActual().getNombreAutor() + " ha comenzado a seguir " + totales.getNombreAutor());
					return Status.OK;
				}
				
			}
			return Status.ERROR;
		}
		return Status.ERROR;
	}
	
	/**
	 * Permite a un autor dejar de seguir a otro autor
	 * @param autor
	 * @return devuelve OK si se llevo a cabo la tarea de dejar de seguir o ERROR si no fue asi
	 */
	public Status unfollow(Usuario autor) {
		if(autor == null) {
			return null;
		}
		if(Sistema.sistema.getUsuarioActual() != null && Sistema.sistema.getAdministrador() == false && Sistema.sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			for(Usuario totales:Sistema.sistema.getUsuariosTotales()) {
				if(totales.getNombreAutor().equals(autor.getNombreAutor()) == true) {
					if(Sistema.sistema.getUsuarioActual().dejarDeSeguirUsuario(totales) == false) {
						return Status.ERROR;
					}
					this.enviarNotificacion(totales,Sistema.sistema.getUsuarioActual().getNombreAutor() + " ha dejado de seguir a " + totales.getNombreAutor());
					return Status.OK;
				}
			}
			return Status.ERROR;
		}
		return Status.ERROR;
	}

	/*===========================================*/
	/*============= NOTIFICACIONES ==============*/
	/*===========================================*/
	
	/**
	 * Esta funcion permite enviar una notificacion a un usuario o al administrador
	 * @param receptor
	 * @param mensaje
	 * @param t
	 * @param cancions
	 * @return retorna OK si la notificacion fue creada y almacenada correctamente en el array general, de lo contrario devolvera ERROR
	 */
	public Status enviarNotificacion(Usuario receptor,String mensaje) {

		if(receptor == null || mensaje == null) {
			return null;
		}
		
		if(this.getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
						
			Notificacion n = new Notificacion(receptor,mensaje,Sistema.sistema.getUsuarioActual());
			
			this.notificaciones_propias.add(n);
			receptor.notificaciones_propias.add(n);
			return Status.OK;
		}
		
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion permite a un usuario eliminar todas las notificaciones en las que este se ha visto
	 * involucrado incluyendo cuando este se encuentre bloqueado
	 * @return devuelve OK si las notificaciones fueron eliminadas del sistema correctamente
	 */
	public Status eliminarNotificacionesPropias() {

		this.getNotificacionesTotales().clear();
		
		return Status.OK;
	}

	
	
		
}