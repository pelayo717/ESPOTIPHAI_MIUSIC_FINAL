package ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema;
import ESPOTIPHAI_MIUSIC_FINAL.Grafic.Ventana;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.*;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.notificacion.*;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.*;

/*import es.uam.eps.padsof.telecard.FailedInternetConnectionException;
import es.uam.eps.padsof.telecard.InvalidCardNumberException;
import es.uam.eps.padsof.telecard.OrderRejectedException;
import es.uam.eps.padsof.telecard.TeleChargeAndPaySystem;*/

import pads.musicPlayer.exceptions.Mp3PlayerException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * La clase sistema es la encargada de trabajar con objetos de la clase Contenido que permite reproducirlos, pararlos asi como comentarlos y otras multiples funcionalidades. Por otra parte
 * esta clase es la encargada de gestionar los usuarios. Permite iniciar sesion y registrarse a todos los que no lo hicieron y les proporciona diferentes actividades dependiendo del tipo de usuario
 * que sea cada uno de este modo, aquellos interesados podran disponer de una version mejorada pagando de manera menusal o obtenerla mediante unos requisitos.
 * @author: Pelayo Rodriguez Aviles
 * @version: 24/03/2019
 */

public class Sistema implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Usuario> usuarios_totales =  new ArrayList<Usuario>();
	private ArrayList<Cancion> canciones_totales = new ArrayList<Cancion>();
	private ArrayList<Album> albumes_totales = new ArrayList<Album>();
	private ArrayList<Notificacion> notificaciones_totales = new ArrayList<Notificacion>();
	private Usuario usuario_actual = null;
	private boolean es_administrador = false;
	public static Sistema sistema = null;
	private int umbral_reproducciones = 30;
	private double precio_premium = 9.9;
	private int max_reproducciones_usuarios_no_premium = 8;
	private int reproducciones_usuarios_no_premium = 0;
	private Cancion cancion_reproduciendose = null;
	
	
	/**
	 * Constructor de la clase sistema, aunque vacio inicializamos los valores al utilizar el connstructor
	 */
	public Sistema(){
		this.usuarios_totales =  new ArrayList<Usuario>();
		this.canciones_totales = new ArrayList<Cancion>();
		this.albumes_totales = new ArrayList<Album>();
		this.notificaciones_totales = new ArrayList<Notificacion>();
		this.usuario_actual = null;
		this.es_administrador = false;
		Sistema.sistema = this;
		this.umbral_reproducciones = 30;
		this.precio_premium = 9.9;
		this.max_reproducciones_usuarios_no_premium = 8;
		this.reproducciones_usuarios_no_premium = 0;
		this.cancion_reproduciendose = null;
	}
	
	/**
	 * Para la implementacion del patron singleton, creamos este metodo que comprobara la existencia de un objeto
	 * de tipo Sistema estatico. Si no esta creado lo creara con los valores inciales, si estuvo creado previamente y
	 * se guardaron correctamente los datos se cargan del fichero correspondiente en disco.
	 * @return
	 * @throws Mp3PlayerException
	 * @throws IOException
	 */
	public static Sistema getSistema() throws Mp3PlayerException, IOException {
		if(sistema == null) {
			
			File archivo = new File("datos.obj");
			File archivo_configuracion = new File("configuracion.txt");
			
			if(archivo.exists() == true) {
				
				sistema = new Sistema();
				sistema = Sistema.cargarDatosGenerales();
				
				if(archivo_configuracion.exists() == true) {
					sistema.leerDatosConfiguracion();
				}
				sistema.empeorarCuentaPrincipal();
				sistema.desbloquearUsuario();
			}else {
				sistema = new Sistema();
				sistema.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
			}
		}
		return sistema;
	}
		
	/*=================================================================================*/
	/*================FUNCIONES GENERALES DE GETTERS Y SETTERS*========================*/
	/*=================================================================================*/
	
	/**
	 * Esta funcion establece el umbral de reproducciones que debe superar un autor para pasar al estado de premium, y solo es aplicable a aquellos usuarios que estan registrados
	 * @param umbral
	 * @return retorna un OK si se establece correctamente el umbral o ERROR si no es asi
	 */
	public Status setUmbralReproducciones(int umbral) {
		if(umbral < 0) {
			return Status.ERROR;
		}
		sistema.umbral_reproducciones = umbral;
		return Status.OK;
	}
	
	/**
	 * Esta funcion establece el precio que se cobra a un usuario para hacerse premium
	 * @param precio
	 * @return retorna un OK si se establece correctamente el precio o ERROR si no es asi
	 */
	public Status setPrecioPremium(double precio) {
		if(precio < 0.0) {
			return Status.ERROR;
		}
		sistema.precio_premium = precio;
		return Status.OK;
	}
	
	/**
	 * Esta funcion establece el numero maximo de reproducciones que puede realizar un usuario no registrado o un usuario registrado pero no premium
	 * @param x
	 * @return retorna un OK si se establece correctamente el numero maximo de reproducciones para un usuario o ERROR si no es asi
	 */
	public Status setMaxReproduccionesUsuarioNoPremium(int x) {
		if(x < 0) {
			return Status.ERROR;
		}
		sistema.max_reproducciones_usuarios_no_premium = x;
		return Status.OK;
	}
	
	/**
	 * Devuelve todos los usuarios que estan registrados en el sistema
	 * @return retorna el array general de usuarios o null si no existe
	 */
	public ArrayList<Usuario> getUsuariosTotales(){
		return sistema.usuarios_totales;
	}
	
	/**
	 * Devuelve todas las canciones que estan en el sistema
	 * @return retorna el array general de canciones o null si no existe
	 */
	public ArrayList<Cancion> getCancionTotales(){
		return sistema.canciones_totales;
	}
	
	/**
	 * Devuelve todos los albumes que estan en el sistema
	 * @return retorna el array general de albumes o null si no existe
	 */
	public ArrayList<Album> getAlbumTotales(){
		return sistema.albumes_totales;
	}
	
	/**
	 * Devuelve el usuario que ha iniciado sesion actualmente
	 * @return retorna el usuario actual si inicio sesion, null si no se inicio sesion
	 */
	public Usuario getUsuarioActual() {
		return (Usuario) sistema.usuario_actual;
	}
	
	/**
	 * Devuelve el umbral de reproducciones que deben superar los usuarios
	 * @return retorna el umbral de reproducciones para hacerse premium del sistema 
	 */
	public int getUmbralReproducciones() {
		return sistema.umbral_reproducciones;
	}
	
	/**
	 * Devuelve la cuota que se debe pagar para hacerse premium
	 * @return retorna el precio necesario para hacerse premium
	 */
	public double getPrecioPremium() {
		return sistema.precio_premium;
	}
	
	/**
	 * Devuelve si el usuario actual es un administrador o no
	 * @return nos indica si el usuario actual de la cuenta es un administrador o no
	 */
	public boolean getAdministrador() {
		return sistema.es_administrador;
	}
	
	/**
	 * Devuelve las notificaciones que se han realizado durante el uso del sistema
	 * @return retorna las notificaciones totales de los usuarios
	 */
	public ArrayList<Notificacion> getNotificacionesTotales(){
		return sistema.notificaciones_totales;
	}
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON LA CUENTA DE USUARIO==================*/
	/*=================================================================================*/
	
	/**
	 * Permite registrarse a un usuario creando un objeto de tipo usuario y almacenandolo en el array de usuarios totales
	 * @param nombre_usuario
	 * @param nombre_autor
	 * @param fecha_nacimiento
	 * @param contrasenia
	 * @return devuelve OK si el usuario se registro correctamente o ERROR si no lo consiguio
	 */
	public Status registrarse(String nombre_usuario,String nombre_autor,LocalDate fecha_nacimiento, String contrasenia) {
		
		int i=0;
		
		if(nombre_usuario == null || contrasenia == null || fecha_nacimiento == null) {
			new ExcepcionInformativa("\nLo sentimos pero parece que el formato de los datos introducidos no son los especificados");
			return Status.ERROR;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true || usuario.getNombreAutor().equals(nombre_autor) == true) {
				break;
			}
			i++;
		}
		
		if(i < sistema.usuarios_totales.size()) {
			new ExcepcionInformativa("\nLo sentimos pero estos datos ya han sido utilizados previamente para un registro");
			return Status.ERROR;
		}		
		
		Usuario usuario_registrado_nuevo = new Usuario(nombre_usuario,nombre_autor,fecha_nacimiento, contrasenia); 
		sistema.usuarios_totales.add(usuario_registrado_nuevo);
		new ExcepcionInformativa("\nEnhorabuena, se ha registrado correctamente " + nombre_usuario );
		return Status.OK;
	}
	
	/**
	 * Permite iniciar sesion en la aplicacion, ya sea el administrador o un usuario convencional
	 * @param nombre_usuario
	 * @param contrasenia
	 * @return devuelve OK si se inicio correctamente la sesion como usuario o ERROR si no se consiguio
	 */
	public Status iniciarSesion(String nombre_usuario, String contrasenia) {

		if(nombre_usuario == null || contrasenia == null) {
			new ExcepcionInformativa("\nLos datos que ha introducido no siguen el formato especificado");
			return Status.ERROR;
		}
			
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia)== true) {
				sistema.usuario_actual = usuario;
				if(nombre_usuario.equals("root1967") && contrasenia.equals("ADMINISTRADOR") == true) {
					sistema.es_administrador = true;
				}
				new ExcepcionInformativa("\nHa iniciado correctamente la sesion " + nombre_usuario);
				Ventana.ventana.inicioSesion.limpiarVentana();
				Ventana.ventana.showReproducirCancion();
				Ventana.ventana.reproducirCancion.setUsuarioRegistrado();
				return Status.OK;
			}
			
		}
			
		new ExcepcionInformativa("\nLo sentimos pero parece haber introducido mal sus datos de identificacion, intentelo de nuevo");
		JOptionPane.showMessageDialog(Ventana.ventana.inicioSesion,"Lo sentimos pero parece haber introducido mal sus datos de identificacion, intentelo de nuevo");
		return Status.ERROR;
	}
	
	/**
	 * Comprueba sin un usuario ha iniciado sesion y de ser asi la cierra
	 * @return devuelve un OK si la sesion se cerro correctamente y se almacenaron los valores del sistema, 
	 * y ERROR si no se consiguio ya que no habia ningun usuario 
	 * @throws IOException 
	 */
	public Status cerrarSesion() throws IOException {
		if(sistema.usuario_actual != null) {
			if(this.es_administrador == true) {
				this.es_administrador = false;
				sistema.usuario_actual = null;
				guardarDatosGenerales();
				new ExcepcionInformativa("\nSesion de administrador cerrada correctamente");
				return Status.OK;
			}
			sistema.usuario_actual = null;
			guardarDatosGenerales();
			new ExcepcionInformativa("\nSesion de usuario cerrada correctamente");
			Ventana.ventana.showReproducirCancion();
			Ventana.ventana.reproducirCancion.botonIzquierdaArriba.setText("Iniciar Sesion");
			Ventana.ventana.reproducirCancion.botonIzquierdaAbajo.setVisible(true);
			return Status.OK;
		}
		new ExcepcionInformativa("\nLo sentimos pero para cerrar sesion necesita iniciarla primero");
		return Status.ERROR;
	}

	/**
	 * Esta funcion permite a un usuario(este bloqueado o no) eliminar su cuenta como usuario del sistema
	 */
	public void eliminarCuenta() {
		if(sistema.usuario_actual != null && sistema.getAdministrador() == false) {
			 for(Usuario usuario:sistema.getUsuariosTotales()) {
				 if(usuario.getNombreUsuario().equals(sistema.getUsuarioActual().getNombreUsuario()) == true && usuario.getContrasena().equals(sistema.getUsuarioActual().getContrasena()) == true) {
					 
					 //Compruebo quien es el usuario y elimino sus canciones
					 for(Cancion canciones_usuario: usuario.getCanciones()) {
						 canciones_usuario.setEstado(EstadoCancion.ELIMINADA);
					 }
					 
					 //elimino sus albumes
					 for(int x=0; x < usuario.getAlbumes().size();x++) {
						 sistema.getAlbumTotales().remove(usuario.getAlbumes().get(x));
						 usuario.getAlbumes().remove(x);
					 }
					 
					 //elimino sus listas
					 for(int z=0; z < usuario.getListas().size();z++) {
						 usuario.getListas().remove(z);
					 }
					 
					 //elimino sus notificaciones
					 this.eliminarNotificacionesPropias();
					 
					 //elimino al usuario
					 sistema.getUsuariosTotales().remove(usuario);
					 
					 //Cierro la sesion para este usuario ya no existente
					 sistema.usuario_actual = null;
					 new ExcepcionInformativa("\nCuenta de usuario eliminada correctamente, deseamos verle pronto de nuevo ");
					 return;
				 }
			 }
		}
		
		new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para eliminar una cuenta");
		return;
	}
	
	/**
	 * Permite mejorar la cuenta de un usuario a premium introduciendo su tarjeta de credito y actualizando sus datos al nuevo estado de PREMIUM
	 * @param numero_tarjeta
	 * @return retorna OK si se acepto la transferencia y se hizo premium al usuario,
	 * ERROR si se dio alguno de los problemas(fallo de conexion, tarjeta no valida u otro problema no identificado).
	 */
	public Status mejorarCuentaPago(String numero_tarjeta){
		/*try {
			TeleChargeAndPaySystem.charge(numero_tarjeta, "Mejora de la cuenta a estado PREMIUM", sistema.getPrecioPremium());
			sistema.usuario_actual.mejorarCuentaPorPago();
			new ExcepcionInformativa("\nEnhorabuena, se ha realizado el pago correctamente y ahora puede reproducir de manera ilimitada");
			return Status.OK;
		}catch(FailedInternetConnectionException fe) {
			fe.toString();
			new ExcepcionInformativa("\nLo sentimos pero parece que ha habido un fallo de conexion, vuelva a intentarlo mas tarde");
			return Status.ERROR;
		}catch(InvalidCardNumberException ie) {
			ie.toString();
			new ExcepcionInformativa("\nLo sentimos pero parece que el numero de tarjeta que ha introducido no es valida, vuelva a intentarlo");
			return Status.ERROR;
		}catch(OrderRejectedException re) {
			re.toString();
			new ExcepcionInformativa("\nLo sentimos pero parece que ha habido un problema que desconocemos, vuelva a intentarlo mas tarde");
			return Status.ERROR;
		}*/
		return Status.OK;

	}
	
	/**
	 * Funcion que se ejcutara de manera periodica y que comprobara todos aquellos usuarios que han excedido el tiempo de usuario premium y los degrada a usuarios registrados normales
	 */
	public void empeorarCuentaPrincipal() {

		LocalDate fecha_actual = LocalDate.now();
		for(Usuario usuario:sistema.usuarios_totales) {
			if(usuario.getPremium() == true) {
				LocalDate fecha_inicio_premium = usuario.getFechaInicioPro();
				if(fecha_actual.minusDays(30).isAfter(fecha_inicio_premium) == true || fecha_actual.minusDays(30).isEqual(fecha_inicio_premium) == true) {
					usuario.emperorarCuenta();
					sistema.reproducciones_usuarios_no_premium = 0;
					sistema.enviarNotificacion(usuario, "Tras 15 dias su cuenta ha sido degrada a registrado normal, abone el importe para disfrutar de ser premium", TipoNotificacion.CUENTA);
				}
			}
		}
		return;
	}

	/**
	 * Funcion que se ejecuta de manera periodica y que comprobara todos aqullos usuarios que han superado el tiempo de bloqueo establecido procediendo al desbloqueo
	 */
	public void desbloquearUsuario() {
		LocalDate fecha_actual = LocalDate.now();
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getEstadoBloqueado() == UsuarioBloqueado.TEMPORAL) {
				LocalDate fecha_inicio_bloqueado = usuario.getFechaInicioBloqueado();
				if(fecha_actual.minusDays(30).isAfter(fecha_inicio_bloqueado) == true || fecha_actual.minusDays(30).isEqual(fecha_inicio_bloqueado) == true ) {
					usuario.desbloquearCuenta();
					sistema.reproducciones_usuarios_no_premium = 0;
					sistema.enviarNotificacion(usuario, "Tras 30 dias ha sido desbloqueado y puede usar de nuevo el sistema", TipoNotificacion.BLOQUEO);
				}
			}
		}
	}
	

	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON LA BUSQUEDA POR CRITERIOS=============*/
	/*=================================================================================*/
	
	/**
	 * Permite realizar una busqueda en todas las canciones al introducir una cadena
	 * @param palabra
	 * @return retorna un arraylist de elementos de tipo cancion si se encuentra contenido igual
	 * o que contiene la cadena introducida por parametro
	 * @throws NoHayElementosExcepcion 
	 */
	public ArrayList<Cancion> buscadorPorTitulos(String palabra){
		LocalDate fecha_actual = LocalDate.now();
		ArrayList<Cancion> canciones_retornar = new ArrayList<Cancion>();
		
		if(palabra == null) {
			new ExcepcionInformativa("\nPor favor introduzca algo como criterio para poder realizar una busqueda");
			return null;
		}
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			if(intervalo.getYears() >= 18) {
				for(Cancion cancion: sistema.canciones_totales) {
					if((cancion.getEstado() == EstadoCancion.EXPLICITA || cancion.getEstado() == EstadoCancion.VALIDA) && (cancion.getTitulo().equals(palabra) == true || cancion.getTitulo().contains(palabra) == true)) {
						canciones_retornar.add(cancion);
					}
				}
				
				if(canciones_retornar.size() == 0) {
					new NoHayElementosExcepcion("Lo sentimos pero el parametro no coincide con ningun titulo");
					return null;
				}

				new ExcepcionInformativa("\nBusqueda por TITULO realizada con exito para el criterio: " + palabra);
				return canciones_retornar;
			}
		}

		for(Cancion cancion: sistema.canciones_totales) {
			
			if(cancion.getEstado() == EstadoCancion.VALIDA && (cancion.getTitulo().equals(palabra) == true || cancion.getTitulo().contains(palabra) == true)) {
				canciones_retornar.add(cancion);
			}
		}
		
		if(canciones_retornar.size() == 0) {
			new NoHayElementosExcepcion("Lo sentimos pero el parametro no coincide con ningun titulo");
			return null;
		}
		new ExcepcionInformativa("\nBusqueda por TITULO realizada con exito para el criterio: " + palabra);
		return canciones_retornar;
	}

	/**
	 * Permite ralizar una busqueda en todos los albumes al introducir una cadena
	 * @param palabra
	 * @return retorna un arraylist de elementos de tipo album si se encuentra contenido igual
	 * o que contiene la cadena introducida por parametro
	 * @throws NoHayElementosExcepcion 
	 */
	public ArrayList<Album> buscadorPorAlbumes(String palabra){
		LocalDate fecha_actual = LocalDate.now();
		ArrayList<Album> albumes_incluidas_explicitas = new ArrayList<Album>();
		int flag = 0;
		
		if(palabra == null) {
			new ExcepcionInformativa("\nPor favor introduzca algo como criterio para poder realizar una busqueda");
			return null;
		}
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			if(intervalo.getYears() >= 18) {
				
				for(Album album_totales:sistema.albumes_totales) {
					flag = 0;
					if(album_totales.getTitulo().equals(palabra) == true || album_totales.getTitulo().contains(palabra) == true) {
						for(Cancion canciones_album: album_totales.getContenido()) {
							if(canciones_album.getEstado() == EstadoCancion.ELIMINADA || canciones_album.getEstado() == EstadoCancion.PLAGIO) { //Estados de las canciones una vez fueron a�adidas al album
								flag = 1;
								break;
							}
						}
						
						if(flag == 0) {
							albumes_incluidas_explicitas.add(album_totales);
						}
					}
				}
				
				if(albumes_incluidas_explicitas.size() == 0) {
					new NoHayElementosExcepcion("Lo sentimos pero el parametro no coincide con ningun album");
					return null;
				}
				
				new ExcepcionInformativa("\nBusqueda por ALBUM realizada con exito para el criterio: " + palabra);
				return albumes_incluidas_explicitas;
			}
		}
		
		for(Album album_totales:sistema.albumes_totales) {
			flag = 0;
			if(album_totales.getTitulo().equals(palabra) == true || album_totales.getTitulo().contains(palabra) == true) {
				for(Cancion canciones_album: album_totales.getContenido()) {
					if(canciones_album.getEstado() == EstadoCancion.ELIMINADA || canciones_album.getEstado() == EstadoCancion.PLAGIO || canciones_album.getEstado() == EstadoCancion.EXPLICITA) { //Estados de las canciones una vez fueron a�adidas al album
						flag = 1;
						break;
					}
				}
				
				if(flag == 0) {
					albumes_incluidas_explicitas.add(album_totales);
				}
			}
		}
		
		if(albumes_incluidas_explicitas.size() == 0) {
			new NoHayElementosExcepcion("Lo sentimos pero el parametro no coincide con ningun album");
			return null;
		}
		new ExcepcionInformativa("\nBusqueda por ALBUM realizada con exito para el criterio: " + palabra);
		return albumes_incluidas_explicitas;
		
	}

	/**
	 * Permite para un autor dado buscar todas sus canciones
	 * @param palabra
	 * @return retorna un arraylist de elementos de tipo cancion aplicados a un autor concreto
	 * si se encuentra alguno mediante el parametro introducido
	 * @throws NoHayElementosExcepcion 
	 */
	public ArrayList<Cancion> buscadorPorAutores_DevolvemosCanciones(String palabra){
		
		int ide = 0;
		ArrayList<Cancion> lista_autor_canciones = new ArrayList<Cancion>();
		LocalDate fecha_actual = LocalDate.now();
		
		if(palabra == null) {
			new ExcepcionInformativa("\nPor favor introduzca un parametro para realizar la busqueda");
			return null;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreAutor().equals(palabra) == true || usuario.getNombreAutor().contains(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		if(ide == 0) {
			new ExcepcionInformativa("\nLo sentimos pero no hemos encontrado ningun autor por el criterio introducido");
			return null;
		}
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			
			if(intervalo.getYears() >= 18) {
				for(Cancion cancion: sistema.canciones_totales) {
					if((cancion.getEstado() == EstadoCancion.EXPLICITA || cancion.getEstado() == EstadoCancion.VALIDA) && cancion.getAutor().getId() == ide) {
						lista_autor_canciones.add(cancion);
					}
				}
				
				if(lista_autor_canciones.size() == 0) {
					new NoHayElementosExcepcion("\nLo sentimos pero no hemos encontrado ninguna cancion para el autor seleccionado");
					return null;
				}
				
				return lista_autor_canciones;
			}
		}
		
		for(Cancion cancion: sistema.canciones_totales) {
			if(cancion.getEstado() == EstadoCancion.VALIDA && cancion.getAutor().getId() == ide) {
				lista_autor_canciones.add(cancion);
			}
		}
		
		if(lista_autor_canciones.size() == 0) {
			new NoHayElementosExcepcion("\nLo sentimos pero no hemos encontrado ninguna cancion para el autor seleccionado");
			return null;
		}
		
		return lista_autor_canciones;
	}
	
	/**
	 * Permite para un autor dado buscar todos sus albumes
	 * @param palabra
	 * @return retorna un arraylista de elementos de tipo album aplicados a un autor concreto
	 * si se encuentra alguno mediante el parametro introducido
	 */
	public ArrayList<Album> buscadorPorAutores_DevolvemosAlbumes(String palabra){
		
		int ide = 0;
		ArrayList<Album> albumes_incluidas_explicitas = new ArrayList<Album>();
		LocalDate fecha_actual = LocalDate.now();
		int flag = 0;
		
		if(palabra == null) {
			new ExcepcionInformativa("\nPor favor introduzca un parametro para realizar la busqueda");
			return null;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreAutor().equals(palabra) == true || usuario.getNombreAutor().contains(palabra) == true) {
				ide = usuario.getId();
				break;
			}
		}
		
		if(ide == 0) {
			new ExcepcionInformativa("\nLo sentimos pero no hemos encontrado ningun autor por el criterio introducido");
			return null;
		}
		
		if(sistema.usuario_actual != null) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			
			if(intervalo.getYears() >= 18) {
				
				for(Album album_totales:sistema.albumes_totales) {
					flag = 0;
					if(album_totales.getAutor().getId() == ide) {
						for(Cancion canciones_album: album_totales.getContenido()) {
							if(canciones_album.getEstado() == EstadoCancion.ELIMINADA || canciones_album.getEstado() == EstadoCancion.PLAGIO) { //Estados de las canciones una vez fueron a�adidas al album
								flag = 1;
								break;
							}
						}
						
						if(flag == 0) {
							albumes_incluidas_explicitas.add(album_totales);
						}		
					}
				}
				
				if(albumes_incluidas_explicitas.size() == 0) {
					new NoHayElementosExcepcion("\nLo sentimos pero no hemos encontrado ningun album para el autor seleccionado");
					return null;
				}
				
				return albumes_incluidas_explicitas;		
			}
		}
		
		for(Album album_totales:sistema.albumes_totales) {
			flag = 0;
			if(album_totales.getAutor().getId() == ide) {
				for(Cancion canciones_album: album_totales.getContenido()) {
					if(canciones_album.getEstado() == EstadoCancion.ELIMINADA || canciones_album.getEstado() == EstadoCancion.PLAGIO || canciones_album.getEstado() == EstadoCancion.EXPLICITA) { //Estados de las canciones una vez fueron a�adidas al album
						flag = 1;
						break;
					}
				}
				
				if(flag == 0) {
					albumes_incluidas_explicitas.add(album_totales);
				}
			}
		}
		
		if(albumes_incluidas_explicitas.size() == 0) {
			new NoHayElementosExcepcion("\nLo sentimos pero no hemos encontrado ningun album para el autor seleccionado");
			return null;
		}
		
		return albumes_incluidas_explicitas;
		
	}
	
	/**
	 * Buscador general de autores, se introduce una cadena y este busca canciones y albumes del autor.
	 * @param palabra
	 * @return retorna un arraylist de contenido si sencuentran canciones y/o albumes, sino devuelve null.
	 */
	public ArrayList<Contenido> buscadorPorAutores(String palabra){
		if(palabra == null) {
			new ExcepcionInformativa("\nPor favor introduzca un criterio valido para realizar una busqueda");
			return null;
		}
		
		ArrayList<Contenido> contenido = new ArrayList<Contenido>();
		ArrayList<Cancion> canciones_autor = sistema.buscadorPorAutores_DevolvemosCanciones(palabra);
		for(Cancion cancion_una:canciones_autor) {
			contenido.add((Cancion)cancion_una);
		}
		ArrayList<Album> albumes_autor = sistema.buscadorPorAutores_DevolvemosAlbumes(palabra);
		for(Album album_una:albumes_autor) {
			contenido.add((Album)album_una);
		}
		
		if(contenido.size() > 0) {
			new ExcepcionInformativa("\nBusqueda por AUTOR realizada con exito para el criterio: " + palabra);
			return contenido;
		}else {
			new ExcepcionInformativa("\nHemos realizado un busqueda por el criterio que introdujo y no hemos encontrado contenido, lo sentimos");
			return null;
		}
	}
	
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON CONTENIDO=============================*/
	/*=================================================================================*/
	
	/**
	 * Esta cancion permite a un usuario registrado modificar una cancion que se encuentra en estado PENDIENTEMODIFICACION
	 * Desde su intento de validacion el usuario tendra 3 dias para modificar el contenido
	 * @param c
	 * @param NombreMp3
	 * @return retorna OK si la modificacion se llevo de manera satisfactoria, y ERROR si no fue asi
	 */
	public Status modificarCancion(Cancion c,String NombreMp3) {
		LocalDate d = LocalDate.now();
		if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == false && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			for(Cancion canciones_propias:sistema.getUsuarioActual().getCanciones()) {
				if(canciones_propias.getTitulo().equals(c.getTitulo()) == true && canciones_propias.getNombreMP3().equals(c.getNombreMP3()) == true && canciones_propias.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && d.minusDays(3).isBefore(c.getFechaModificacion()) == true) {
					canciones_propias.setNombreMP3(NombreMp3);
					if(canciones_propias.esMP3() == true) {
						canciones_propias.setDuracion(canciones_propias.devolverDuracion());
						new ExcepcionInformativa("\nLa modificacion de la cancion " + c.getTitulo() + " se ha realizado exitosamente, en breves se procedera a su validacion");
						return Status.OK;
					}

					new ExcepcionInformativa("\nLo sentimos pero parece que ha habido un error con el formato del fichero");
					return Status.ERROR;
				}
			}
		}
		
		new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para modificar una cancion pendiente de validacion");
		return Status.ERROR;
	}
	
	
	/**
	 * Permite a un usuario registrado o registrado premium crear una cancion, a la espera de que el administrador las valide y las haga visibles al publico
	 * @param anyo
	 * @param titulo
	 * @param nombreMP3
	 * @return retorna la referencia a la cancion creada, que esta almacenada en el array general de canciones y en el propio del usuario
	 * @throws FileNotFoundException
	 * @throws Mp3PlayerException
	 */
	public Cancion crearCancion(Date anyo,String titulo,String nombreMP3,boolean explicita) throws FileNotFoundException, Mp3PlayerException{
		LocalDate fecha_actual = LocalDate.now();
		if(anyo == null || titulo == null || nombreMP3 == null) {
			new ExcepcionInformativa("\nPor favor introduzca datos validos para crear la cancion");
			return null;
		}
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			
			if(explicita == true && intervalo.getYears() < 18) {
				new ExcepcionInformativa("\nLa cancion " + titulo + " esta indicada como explicita, y usted no tiene la edad suficiente para subirla a nuestra plataforma");
				return null;
			}
			
			Cancion c = new Cancion(anyo,titulo,sistema.usuario_actual,nombreMP3,explicita);
			
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(titulo) == true && cancion.getNombreMP3().equals(nombreMP3) == true) {
					new ExcepcionInformativa("\nLa cancion " + titulo + " no ha sido creada ya que esta ya esta albergada en nuestros sistemas");
					return null;
				}	
			}
			
			for(Cancion cancion:sistema.getCancionTotales()) {
				if(cancion.getTitulo().equals(titulo) == true && cancion.getNombreMP3().equals(nombreMP3) == true) {
					new ExcepcionInformativa("\nLa cancion " + titulo + " no ha sido creada ya que esta ya esta creada por otro autor");
					return null;
				}
			}
			
			sistema.usuario_actual.anyadirACancionPersonal(c);
			sistema.canciones_totales.add(c);
			new ExcepcionInformativa("\nLa cancion " + titulo + " ha sido creada correctamente. Le enviaremos una notificacion cuando se valide");
			return c;
			
			
		}else {
			new ExcepcionInformativa("\nLa cancion no ha sido creada ya que no ha iniciado sesion o no esta registrado");
			return null;
		}
		
	}
	
	/**
	 * Permite a un usuario eliminar una de sus propias canciones(realmente en vez de eliminar el objeto le cambiamso el estado a Eliminado para que luego no se reproduzca ni se reconozca)
	 * @param cancion_eliminar
	 * @return retorna ERROR si la cancion no existe o no se puede eliminar del sistema u OK si se elimina correctamente
	 */
	public Status eliminarCancion(Cancion cancion_eliminar) {
		boolean existe_cancion_en_usuario = false;
		if(cancion_eliminar == null) {
			new ExcepcionInformativa("\nLa cancion que desea eliminar no parece esta inicializada");
			return Status.ERROR;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(cancion_eliminar.getTitulo()) == true && cancion.getNombreMP3().equals(cancion_eliminar.getNombreMP3()) == true && cancion.getEstado() != EstadoCancion.PLAGIO) {
					existe_cancion_en_usuario = true;
				}
			}
			
			if(existe_cancion_en_usuario == true) {
				new ExcepcionInformativa("\nLa cancion " + cancion_eliminar.getTitulo() + " ha sido eliminada correctamente");
				sistema.usuario_actual.eliminarDeCancionesPersonales(cancion_eliminar);
				return Status.OK;
			}else {
				new ExcepcionInformativa("\nParece ser que la cancion " + cancion_eliminar.getTitulo() + " que desea eliminar no se encuentra entre sus canciones en el sistema");
				return Status.ERROR;
			}
		}else {
			new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para eliminar una cancion");
			return Status.ERROR;
		}
		
	}
	
	/**
	 * Permite al usuario registrado o registrado premium crear un album que inicialmente estara vacio
	 * @param anyo
	 * @param titulo
	 * @param id
	 * @param contenido
	 * @return retorna la referencia al objeto album si se crea correctamente, sino devuelve null 
	 */
	public Album crearAlbum(Date anyo,String titulo) {
		if(anyo == null || titulo == null) {
			new ExcepcionInformativa("\nPor favor introduzca datos validos para crear el album");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {			
			Album a = new Album(anyo,titulo,sistema.usuario_actual,new ArrayList<Cancion>());
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(titulo)== true) {
					new ExcepcionInformativa("\nEl album " + titulo + " ya se encuentra creado entre sus albumes desplegados");
					return null;
				}
			}
			
			for(Album album:sistema.getAlbumTotales()) {
				if(album.getTitulo().equals(titulo) == true) {
					new ExcepcionInformativa("\nEl album " + titulo + " ya se encuentra creado por otro autor");
					return null;
				}
			}
			
			
			sistema.usuario_actual.anyadirAAlbumPersonal(a);
			sistema.albumes_totales.add(a);
			new ExcepcionInformativa("\nEl album " + titulo + " se ha creado correctamente");
			return a;
			
		}else {
			new ExcepcionInformativa("\nEl album no ha sido creado ya que no ha iniciado sesion o no esta registrado");
			return null;
		}
	
	}
	
	/**
	 * Permite a un usuario eliminar uno de sus propios albumes(a diferencia de las canciones que mantendremos las referencias en este caso eliminaremos directamente la referencia al objeto en ambos arraylists de albumes)
	 * @param album_eliminar
	 * @return retorna ERROR si la cancion no se elimina del album, OK si se consigue
	 */
	public Status eliminarAlbum(Album album_eliminar) {
		boolean existe_album_en_usuario = false;
		if(album_eliminar == null) {
			new ExcepcionInformativa("\nEl album que desea eliminar no parece estar inicializado");
			return Status.ERROR;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(album_eliminar.getTitulo()) == true) {
					existe_album_en_usuario = true;
				}
			}
			
			if(existe_album_en_usuario == true) { //SE PUEDE ELIMINAR DEL ARRAY DE CANCIONES PERSONALES
				sistema.usuario_actual.eliminarDeAlbumesPersonales(album_eliminar);
				sistema.albumes_totales.remove(album_eliminar);
				new ExcepcionInformativa("\nEl album " + album_eliminar.getTitulo() + " ha sido eliminado correctamente");
				return Status.OK;
			}else {
				new ExcepcionInformativa("\nParece ser que el album " + album_eliminar.getTitulo() + " que desea eliminar no se encuentra entre sus albumes en el sistema");
				return Status.ERROR;
			}
		}else {
			new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para eliminar un album");
			return Status.ERROR;
		}
	}
	
	/**
	 * Esta funcion permite a un usuario a�adir a sus albumes sus propias canciones
	 * @param a
	 * @param c
	 * @return retorna OK si se a�ade correctamente la cancion al album, de lo contrario nos devolvera ERROR
	 */
	public Status anyadirCancionAAlbum(Album a, Cancion c) {
		int x=0;
		if(a == null || c == null) {
			new ExcepcionInformativa("\nPor favor introduzca datos validos para poder a�adir la cancion al album");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && sistema.es_administrador == false) {
			for(Cancion cancion:sistema.getUsuarioActual().getCanciones()) {
				if(cancion.getTitulo().equals(c.getTitulo()) == true && cancion.getNombreMP3().equals(c.getNombreMP3()) == true && (c.getEstado() == EstadoCancion.VALIDA || c.getEstado() == EstadoCancion.EXPLICITA)) {
					
					for(Album album:sistema.getUsuarioActual().getAlbumes()) {
						if(album.getTitulo().equals(a.getTitulo()) == true && album.getAutor().getId() == sistema.getUsuarioActual().getId()) {
							
							ArrayList<Cancion> canciones_en_album = album.getContenido();
							for(Cancion canciones_album:canciones_en_album) {
								if(canciones_album.getTitulo().equals(c.getTitulo()) == true && canciones_album.getNombreMP3().equals(c.getNombreMP3()) == true) {
									break;
								}
								x++;
							}
							
							if(x < canciones_en_album.size() - 1) {
								new ExcepcionInformativa("\nParece que la cancion " + c.getTitulo() + " ya estaba a�adida al album");
								return Status.ERROR;
							}
							
							if(album.anyadirContenido(c) == Status.OK) {
								new ExcepcionInformativa("\nLa cancion " + c.getTitulo() + " se ha a�adido correctamente al album " + a.getTitulo());
								return Status.OK;
							}else {
								new ExcepcionInformativa("\nParece que ha habido algun problema al a�adir la cancion " + c.getTitulo() + " al album " + a.getTitulo());
								return Status.ERROR;
							}
								
						}
					}
				}
			}	
		}
		
		new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para a�adir contenido a un album");
		return Status.ERROR;
		
	}
	
	/**
	 * Permite eliminar la referencia de una cancion que se encuentra en un album de un usuario registrado o registrado premium
	 * @param a
	 * @param c
	 * @return retorna OK si se elimina la cancion del album indicado correctamente, de lo contrario devolvera ERROR
	 */
	public Status quitarCancionDeAlbum(Album a, Cancion c) {
		if(a == null || c == null) {
			new ExcepcionInformativa("\nPor favor introduzca datos validos para poder eliminar la cancion al album");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && sistema.es_administrador == false) {
			for(Cancion cancion:sistema.getUsuarioActual().getCanciones()) {
				if(cancion.getTitulo().equals(c.getTitulo()) == true && cancion.getNombreMP3().equals(c.getNombreMP3()) == true && (c.getEstado() == EstadoCancion.VALIDA || c.getEstado() == EstadoCancion.EXPLICITA)) {
			
					for(Album album:sistema.getUsuarioActual().getAlbumes()) {
						if(album.getTitulo().equals(a.getTitulo()) == true && album.getAutor().getId() == sistema.getUsuarioActual().getId()) {
					
							ArrayList<Cancion> canciones_en_album = a.getContenido();
							
							for(Cancion canciones_album:canciones_en_album) {
								if(canciones_album.getTitulo().equals(c.getTitulo()) == true && canciones_album.getNombreMP3().equals(c.getNombreMP3()) == true) {
									if(album.eliminarContenido(c) == Status.OK) {
										new ExcepcionInformativa("\nParece que la cancion " + c.getTitulo() + " se ha eliminado correctamente del album " + a.getTitulo());
										return Status.OK;
									}else {
										new ExcepcionInformativa("\nParece que la cancion " + c.getTitulo() + " ya estaba a�adida al album");
										return Status.ERROR;
									}
								}
							}	
						}
					}
				}
			}	
			
		}
		
		new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para eliminar contenido de un album");
		return Status.ERROR;
	
	}
	
	/**
	 * Permte al usuario registrado o registrado premium crear una lista de contenidos que inicialmente estara vacia y unicamente sera visible para el
	 * El contenido final que almacenara la lista sera de tipo Cancion aunque a la hora de a�adir podra ser de tipo LISTA,CANCION o ALBUM
	 * @param anyo
	 * @param titulo
	 * @param id
	 * @param autor
	 * @param contenido
	 * @return retorna la referencia al objeto lista si se creo correctamente, y de lo contrario devolvera null
	 */
	public Lista crearLista(Date anyo, String titulo) {
		
		boolean lista_repetida_en_usuario = false; 
		if(anyo == null || titulo == null) {
			new ExcepcionInformativa("\nPor favor introduzca datos validos para poder crear una lista");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			Lista l = new Lista(anyo,titulo,sistema.usuario_actual,new ArrayList<Cancion>());
			for(Lista lista:sistema.usuario_actual.getListas()) {
				if(lista.getTitulo().equals(titulo) == true) {
					lista_repetida_en_usuario = true;
				}
			}
			if(lista_repetida_en_usuario == true) {
				new ExcepcionInformativa("\nLa lista " + titulo + " ya se encuentra creada entre sus listas desplegadas");
				return null;
			}else {
				sistema.usuario_actual.anyadirAListaPersonal(l);
				new ExcepcionInformativa("\nLa lista " + titulo + " se ha creado correctamente");
				return l;
			}
		}else {
			new ExcepcionInformativa("\nLa lista no ha sido creada ya que no ha iniciado sesion o no esta registrado ");
			return null;
		}
	}
	
	/**
	 * Permite al usuario eliminar una de sus propias listas(eliminamos en el arraylist de listas en usuario la referencia al objeto correspondiente)
	 * @param lista_eliminar
	 * @return retorna OK si la lista fue eliminada correctamente, ERROR si no se elimino del sistema
	 */
	public Status eliminarLista(Lista lista_eliminar) {
		boolean existe_lista_en_usuario = false;
		if(lista_eliminar == null) {
			new ExcepcionInformativa("\nPor favor introduzca datos validos para poder eliminar la lista del sistema");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			for(Lista lista:sistema.usuario_actual.getListas()) {
				if(lista.getTitulo().equals(lista_eliminar.getTitulo()) == true) {
					existe_lista_en_usuario = true;
				}
			}
			
			if(existe_lista_en_usuario == true) {
				sistema.usuario_actual.eliminarDeListasPersonales(lista_eliminar);
				new ExcepcionInformativa("\nLa lista " + lista_eliminar.getTitulo() + " ha sido eliminada correctamente");
				return Status.OK;
			}else {
				new ExcepcionInformativa("\nParece ser que la lista " + lista_eliminar.getTitulo() + " que desea eliminar no se encuentra entre sus listas del sistema");
				return Status.ERROR;
			}
		}else {
			new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para eliminar una lista");
			return Status.ERROR;
		}
	}
	
	/**
	 * Esta funcion permite a�adir un contenido(LISTA,CANCION o ALBUM) a una lista si no esta incluido ya
	 * @param l
	 * @param c
	 * @return retorna OK si se a�adio correctamente, ERROR si no fue asi
	 */
	public Status anyadirALista(Lista l, Contenido c) {
		if(l == null || c == null) {
			new ExcepcionInformativa("\nIntroduzca un contenido y una lista valida");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
				if(sistema.getUsuarioActual().getListas().contains(l) == true) {
					if(l.anyadirContenido(c) == Status.OK) {
						new ExcepcionInformativa("\nEl contenido se ha a�adido correctamente a la lista " + l.getTitulo());
						return Status.OK;
					}else {
						new ExcepcionInformativa("\nParece que el contenido no se ha a�adido a la lista " + l.getTitulo());
						return Status.ERROR;
					}
				}
		}
		new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para a�adir contenido a una lista");
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion permite eliminar un contenido de la lista especificada
	 * @param l
	 * @param c
	 * @return retorna OK si se elimino correctamente, ERROR si no fue asi
	 */
	public Status quitarDeLista(Lista l,Contenido c) {
		if(l == null || c == null) {
			new ExcepcionInformativa("\nIntroduzca un contenido y una lista valida");
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			if(sistema.getUsuarioActual().getListas().contains(l) == true) {
				 if(l.eliminarContenido(c) == Status.OK) {
					 new ExcepcionInformativa("\nEl contenido se ha eliminado correctamente de la lista " + l.getTitulo());
					 return Status.OK;
				 }else {
					 new ExcepcionInformativa("\nEl contenido no ha sido eliminado de la lista " + l.getTitulo());
					 return Status.ERROR;
				 }
			}
		}
		new ExcepcionInformativa("\nLo sentimos pero parece que no ha iniciado sesion o no se ha registrado en nuestro sistema para eliminar contenido de una lista");
		return Status.ERROR;
	}
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON GUARDADO DE DATOS=====================*/
	/*=================================================================================*/
	
	/**
	 * Funcion encargada que nada mas cerrar sesion de un usuario se guarda el estado del sistema en un fichero en disco
	 * Ademas esta implementada de tal forma que conociendo el sistema de ficheros del SO donde se este corriendo consigue
	 * almacenar los datos siguiendo un patron u otro.
	 * @return esta funcion nos devuelve OK si los datos de almacenaron correctamente en el fichero indicado en disco y
	 * ERROR si no fue asi
	 */
	public Status guardarDatosGenerales() throws IOException {
		try {
			
			String ret = "";
			String final_path="";
			ret = File.separator;
			ret = System.getProperty("file.separator");
			ret = String.valueOf(File.separatorChar);
			Properties allProps = System.getProperties();
			Set keySet = allProps.keySet();
			Iterator it = keySet.iterator();
			while(it.hasNext()){
				Object keyObj = it.next();
				String key = (String)keyObj;
				Object valObj = allProps.get(key);
				if(key.equals("user.dir")) {
					final_path = valObj.toString() + ret + "datos.obj";
				}
			}
			
			FileOutputStream fileOut = new FileOutputStream(final_path);
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			fileOut.close();
			return Status.OK;
		}catch(IOException ie) {
			System.out.println(ie.toString());
			return Status.ERROR;
		}
		
	}
	
	
	/**
	 * Funcion encargada de cargar datos leeyendo de un fichero almacenado en disco
	 * Al igual que la anterior esta funcion tambien conoce en que sistema se esta corriendo para asi saber
	 * donde se encuentran almacenados los datos del sistema
	 * @return devuelve el objeto que ha leido del fichero en disco
	 */
	public static Sistema cargarDatosGenerales(){
		
		try {
			
			String ret = "";
			String final_path="";
			ret = File.separator;
			ret = System.getProperty("file.separator");
			ret = String.valueOf(File.separatorChar);
			Properties allProps = System.getProperties();
			Set keySet = allProps.keySet();
			Iterator it = keySet.iterator();
			while(it.hasNext()){
				
				Object keyObj = it.next();
				String key = (String)keyObj;
				Object valObj = allProps.get(key);
				if(key.equals("user.dir")) {
					final_path = valObj.toString() + ret + "datos.obj";
				}
			}
			
			FileInputStream in = new FileInputStream(final_path);
			ObjectInputStream oin = new ObjectInputStream(in);
			System.out.println("works");
			Sistema s1 = (Sistema) oin.readObject();
			System.out.println("works");
			oin.close();
			in.close();
			return s1;
		}catch(IOException ie) {
			System.out.println("IO error:");
			System.out.println(ie.toString());
			return null;
		}catch(ClassNotFoundException ce) {
			System.out.println(ce.toString());
			return null;
		}
		
	}
	
	
	/**
	 * Esta funcion permite al administrador a modificar los datos basicos de configuracion de la aplicacion
	 * Se creara el fichero si no esta creado y de ser asi se creara de nuevo cada vez que se modifiquen los mismos
	 * @param precio
	 * @param umbral
	 * @param reproducciones
	 * @return devuelve OK si la accion se realizo de manera satisfacotria, o ERROR si no fue asi
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public Status guardarDatosConfiguracion(double precio,int umbral,int reproducciones) throws IOException {
		if(precio < 0.0 || umbral <= 0 || reproducciones <= 0) {
			new ExcepcionInformativa("\nIntroduzca datos validos para almacenar en el fichero de configuracion");
			return null;
		}
		String ruta = "configuracion.txt";
		File fichero = new File(ruta);
		FileWriter archivo = new FileWriter(new File(ruta));
		if(sistema.usuario_actual != null && sistema.es_administrador == true) {
			if(fichero.exists() == true) {
				if(fichero.delete() == true) {
					if(fichero.createNewFile()) {
						archivo.write((int)precio);
						archivo.write(umbral);
						archivo.write(reproducciones);
						archivo.close();
						new ExcepcionInformativa("\nSe ha almacenado correctamente el fichero de configuracion del sistema");
						return Status.OK;
					}
				}
				
			}else {
				if(fichero.createNewFile()) {
					archivo.write((int)precio);
					archivo.write(umbral);
					archivo.write(reproducciones);
					archivo.close();
					new ExcepcionInformativa("\nSe ha almacenado correctamente el fichero de configuracion del sistema");
					return Status.OK;
				}
			}
		}
		new ExcepcionInformativa("\nParece que ha habido un error a la hora de almacenar los datos de configuracion, lo sentimos");
		return Status.ERROR;
			
	}
	
	
	/**
	 * Esta funcion que se ejecuta de manera periodica nada mas inicializar el objeto sistema y carga los datos si los hubiese
	 * @throws FileNotFoundException 
	 */
	public void leerDatosConfiguracion() throws FileNotFoundException, IOException {
		String ruta = "configuracion.txt";
		try{
			FileReader fichero = new FileReader(ruta);
			BufferedReader b = new BufferedReader(fichero);
			precio_premium = (double)b.read();
			umbral_reproducciones = (int) b.read();
			max_reproducciones_usuarios_no_premium = (int)b.read();
			b.close();
			fichero.close();
			new ExcepcionInformativa("\nSe han leido correctamente los datos de configuracion del sistema");
			return;
		}catch(FileNotFoundException fi) {
			fi.toString();
			new ExcepcionInformativa("\nParece que el fichero de configuracion no se ha encontrado, lo sentimos");
			return;
		}catch(IOException ie) {
			ie.toString();
			new ExcepcionInformativa("\nParece que ha habido un problema con la lectura de datos del fichero");
			return;
		}
		
	}
	
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON NOTIFICACIONES========================*/
	/*=================================================================================*/
	
	/**
	 * Esta funcion permite a un usuario eliminar todas las notificaciones en las que este se ha visto
	 * involucrado incluyendo cuando este se encuentre bloqueado
	 * @return devuelve OK si las notificaciones fueron eliminadas del sistema correctamente
	 */
	public Status eliminarNotificacionesPropias() {
		int x=0;
		if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == false) {
			for(x=0; x < sistema.getNotificacionesTotales().size(); x++) {
				if(sistema.notificaciones_totales.get(x).getEmisor() == sistema.getUsuarioActual() || sistema.notificaciones_totales.get(x).getReceptor() == sistema.getUsuarioActual()) {
					sistema.notificaciones_totales.remove(x);
				}
			}
			new ExcepcionInformativa("\nSe han eliminado correctamente las notificaciones del sistema");
			return Status.OK;
		}
		new ExcepcionInformativa("\nHa habido algun problema a la hora de eliminar las notificacion, lo sentimos");
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion permite tanto a usuarios como al administrador ver las notificaciones que han enviado
	 * o que han recibido pudiendo ser de (SEGUIMIENTO,BLOQUEO o PLAGIO)
	 */
	public void verNotificacionesPropias(){
		
		if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == false) {
			for(Notificacion notificaciones_propias:sistema.getNotificacionesTotales()) {
				if(notificaciones_propias.getEmisor() == sistema.getUsuarioActual() || notificaciones_propias.getReceptor() == sistema.getUsuarioActual()) {
					System.out.println();
					System.out.println("\tEmisor ==>  " + notificaciones_propias.getEmisor().getNombreAutor() + " Receptor ==>  " + notificaciones_propias.getReceptor().getNombreAutor());
					System.out.println("\tMensjaje: " + notificaciones_propias.getMensaje());
					System.out.println("\tTipo notificacion: " + notificaciones_propias.getTipoNotificacion());
				}
			}
		}else if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == true) {
			for(Notificacion notificaciones_propias:sistema.getNotificacionesTotales()) {
				if(notificaciones_propias.getEmisor() == sistema.getUsuarioActual() || notificaciones_propias.getReceptor() == sistema.getUsuarioActual()) {
					System.out.println();
					System.out.println("\tEmisor ==>  " + notificaciones_propias.getEmisor().getNombreAutor() + " Receptor ==>  " + notificaciones_propias.getReceptor().getNombreAutor());
					System.out.println("\tMensjaje: " + notificaciones_propias.getMensaje());
					System.out.println("\tTipo notificacion: " + notificaciones_propias.getTipoNotificacion());
					System.out.println();
					if(notificaciones_propias.getTipoNotificacion() == TipoNotificacion.PLAGIO && notificaciones_propias.getReceptor().getId() == sistema.getUsuarioActual().getId()) {
						if(sistema.comprobarPlagio(notificaciones_propias.getAdjunto()) == Status.OK) {
							notificaciones_propias.getAdjunto().getAutor().bloquearCuentaIndefinido();
							sistema.enviarNotificacion(notificaciones_propias.getAdjunto().getAutor(), "Sentimos comunicarle que hemos bloueado su cuenta por el plagio que ha realizado de la cancion " + notificaciones_propias.getAdjunto().getTitulo(), TipoNotificacion.BLOQUEO);
							notificaciones_propias.getAdjunto().reportarPlagio();
						}else {
							sistema.enviarNotificacion(notificaciones_propias.getEmisor(), "Sentimos comunicarle que hemos bloueado su cuenta durante 30 dias por el reporte del plagio que ha realizado de la cancion " + notificaciones_propias.getAdjunto().getTitulo(), TipoNotificacion.BLOQUEO);
							notificaciones_propias.getEmisor().bloquearCuentaTemporal();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Esta funcion permite enviar una notificacion a un usuario o al administrador
	 * @param receptor
	 * @param mensaje
	 * @param t
	 * @param cancions
	 * @return retorna OK si la notificacion fue creada y almacenada correctamente en el array general, de lo contrario devolvera ERROR
	 */
	public Status enviarNotificacion(Usuario receptor,String mensaje,TipoNotificacion t,Cancion...cancions) {
		if(receptor == null || mensaje == null || t == null || cancions == null) {
			new ExcepcionInformativa("\nIntroduzca datos validos para enviar la notificacion");
			return null;
		}
		if(sistema.getUsuarioActual() != null && t != TipoNotificacion.PLAGIO && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			Notificacion n = new Notificacion(receptor,mensaje,sistema.getUsuarioActual(),t);
			sistema.notificaciones_totales.add(n);
			new ExcepcionInformativa("\nNotificacion enviada correctamente");
			return Status.OK;
		}else if(sistema.getUsuarioActual() != null && t == TipoNotificacion.PLAGIO && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			if(cancions[0] == null) {
				new ExcepcionInformativa("\nEl fichero que ha adjuntado no parece ser valido");
				return Status.ERROR;
			}
			Notificacion n = new Notificacion(receptor,mensaje,sistema.getUsuarioActual(),t,cancions[0]);
			sistema.notificaciones_totales.add(n);
			new ExcepcionInformativa("\nNotificacion enviada correctamente");
			return Status.OK;
		}
		
		new ExcepcionInformativa("\nLo sentimos pero para poder enviar notificaciones deber iniciar sesion");
		return Status.ERROR;
	}
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON ADMINISTRADOR=========================*/
	/*=================================================================================*/
	
	/**
	 * Esta funcion que unicamente la puede utilizar el administrador al ver sus notificaciones 
	 * permite ver si la cancion pasada como argumento es un plagio de otra o no
	 * @param c
	 * @return devuelve OK si efectivamente se trata de un plagio o ERROR si no lo es
	 */
	public Status comprobarPlagio(Cancion c) {
		if(c == null) {
			return Status.ERROR;
		}else{
			for(Cancion c_t:sistema.getCancionTotales()) {
				if(c.getTitulo().equals(c_t.getTitulo())== true && c.getNombreMP3().equals(c_t.getNombreMP3()) == true && c.getAutor().getId() != c_t.getAutor().getId()) {
					return Status.OK;
				}
			}
		}
		return Status.ERROR;
		
	}
	
	/**
	 * Esta funcion que solo el administrador puede utilizar se encarga de buscar en el array de canciones general del sistema
	 * canciones que esten a la espera de ser validadas en base a unos requisitos establecidos. 
	 * Si es validada se notifica al autor y a sus seguidores pero si no es asi se le da al autor un margen de 3 dias para validarla.
	 * Si tras esos 3 dias la cancion no pasa al estado de valida se elimina directamente del sistema
	 * @param max_tiempo
	 * @return devuelve OK si se llevo a cabo la accion de validar(aunque no necesariamente se validase) y error si no fue asi
	 */
	public Status validarCancion(double max_tiempo) {
		if(max_tiempo <= 0.0) {
			new ExcepcionInformativa("\nIntroduzca un tiempo valido");
			return null;
		}
		if(sistema.getAdministrador() == true && sistema.getUsuarioActual() != null) {
			
			for(Cancion canciones_totales_validar: sistema.getCancionTotales()) {
				if(canciones_totales_validar.getEstado() == EstadoCancion.PENDIENTEAPROBACION) {
					
					if(canciones_totales_validar.getDuracion() <= max_tiempo && canciones_totales_validar.esMP3() == true) {
						
						if(canciones_totales_validar.getEsExplicita()== true) {
							canciones_totales_validar.validarCancionExplicita();
						}else{
							canciones_totales_validar.cancionCorregida();
						}
						this.enviarNotificacion(canciones_totales_validar.getAutor(), "Cancion " + canciones_totales_validar.getTitulo() + " validada perfectamente.", TipoNotificacion.VALIDACION);
						for(Usuario seguidores:canciones_totales_validar.getAutor().getSeguidores()) {
							this.enviarNotificacion(seguidores, "El autor " + canciones_totales_validar.getAutor().getNombreAutor() + " ha subido una nueva cancion, escuchala ya!!", TipoNotificacion.SEGUIMIENTO);
						}
					}else if(canciones_totales_validar.getDuracion() > max_tiempo || canciones_totales_validar.esMP3() != true) {
						
						this.enviarNotificacion(canciones_totales_validar.getAutor(), "Cancion " + canciones_totales_validar.getTitulo() + " no validada(Max. Tiempo:" + max_tiempo + " Tiempo tu Cancion: " + canciones_totales_validar.getDuracion() + " , Archivo valido: " + canciones_totales_validar.esMP3() + ", tiene 3 dias para realizar la modificacion y tras ese tiempo la volveremos a verificar, gracias.", TipoNotificacion.VALIDACION);
						canciones_totales_validar.cancionRechazada();
					}
				}else if(canciones_totales_validar.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && (Period.between(canciones_totales_validar.getFechaModificacion(), LocalDate.now()).getDays() <= 3) ) {
					
					if(canciones_totales_validar.getDuracion() <= max_tiempo && canciones_totales_validar.esMP3() == true) {
						if(canciones_totales_validar.getEsExplicita()== true) {
							canciones_totales_validar.validarCancionExplicita();
						}else{
							canciones_totales_validar.cancionCorregida();
						}
						this.enviarNotificacion(canciones_totales_validar.getAutor(), "Cancion validada perfectamente.", TipoNotificacion.VALIDACION);
						for(Usuario seguidores:canciones_totales_validar.getAutor().getSeguidores()) {
							this.enviarNotificacion(seguidores, "El autor " + canciones_totales_validar.getAutor().getNombreAutor() + " ha subido una nueva cancion, escuchala ya!!", TipoNotificacion.SEGUIMIENTO);
						}
					}
				}else if(canciones_totales_validar.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && (Period.between(canciones_totales_validar.getFechaModificacion(), LocalDate.now()).getDays() > 3)) {
					if(canciones_totales_validar.getDuracion() < max_tiempo && canciones_totales_validar.esMP3() == true) {
						if(canciones_totales_validar.getEsExplicita()== true) {
							canciones_totales_validar.validarCancionExplicita();
						}else{
							canciones_totales_validar.cancionCorregida();
						}
						
						this.enviarNotificacion(canciones_totales_validar.getAutor(), "Cancion validada perfectamente.", TipoNotificacion.VALIDACION);
						for(Usuario seguidores:canciones_totales_validar.getAutor().getSeguidores()) {
							this.enviarNotificacion(seguidores, "El autor " + canciones_totales_validar.getAutor().getNombreAutor() + " ha subido una nueva cancion, escuchala ya!!", TipoNotificacion.SEGUIMIENTO);
						}
					}else {
						this.enviarNotificacion(canciones_totales_validar.getAutor(), "Cancion rechazada, esta no sigue los requisitos establecidos, lo sentimos.", TipoNotificacion.VALIDACION);
						canciones_totales_validar.cancionDescartada();
					}
				}
					
			}
			
			return Status.OK;
		}
		
		return Status.ERROR;
	}
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON USUARIO Y SUS "SERFVICIOS"============*/
	/*=================================================================================*/
	
	/**
	 * Permite a un autor seguir a otro autor
	 * @param autor
	 * @return devuelve OK si se llevo a cabo la tarea de seguimiento o ERROR si no fue asi
	 */
	public Status follow(String autor) {
		if(autor == null) {
			new ExcepcionInformativa("\nIntroduzca un autor valido para buscar");
			return Status.ERROR;
		}
		if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == false && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			for(Usuario totales:sistema.getUsuariosTotales()) {
				if(totales.getNombreAutor().equals(autor) == true) {
					sistema.getUsuarioActual().seguirUsuario(totales);
					sistema.enviarNotificacion(totales, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha comenzado a seguirle", TipoNotificacion.SEGUIMIENTO);
					new ExcepcionInformativa("\nEnhorabuena, usted ha comenzado a seguir a " + autor);
					return Status.OK;
				}
			}
			new ExcepcionInformativa("\nLo sentimos pero no hemos encontrado al autor que pretende seguir, intentelo de nuevo");
			return Status.ERROR;
		}
		new ExcepcionInformativa("\nLo sentimos pero parece que usted no esta registrado o no ha iniciado sesion para poder realizar esta accion");
		return Status.ERROR;
	}
	
	/**
	 * Permite a un autor dejar de seguir a otro autor
	 * @param autor
	 * @return devuelve OK si se llevo a cabo la tarea de dejar de seguir o ERROR si no fue asi
	 */
	public Status unfollow(String autor) {
		if(autor == null) {
			new ExcepcionInformativa("\nIntroduzca un autor valido para dejar de seguir");
			return null;
		}
		if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == false && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			for(Usuario totales:sistema.getUsuariosTotales()) {
				if(totales.getNombreAutor().equals(autor) == true) {
					sistema.getUsuarioActual().dejarDeSeguirUsuario(totales);
					sistema.enviarNotificacion(totales, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha dejado de seguirle", TipoNotificacion.SEGUIMIENTO);
					new ExcepcionInformativa("\nUsted ha dejado de seguir a " + autor);
					return Status.OK;
				}
			}
			new ExcepcionInformativa("\nLo sentimos pero no hemos encontrado al autor que pretende dejar de seguir, intentelo de nuevo");
			return Status.ERROR;
		}
		new ExcepcionInformativa("\nLo sentimos pero parece que usted no esta registrado o no ha iniciado sesion para poder realizar esta accion");
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion que la puede utilizar cualquier tipo de usuario les permite escribir un comentario sobre una cancion
	 * @param comentario
	 * @param cancion
	 * @return OK si se a�adio correctamente a la cancion o ERROR si no fue asi
	 */
	public Status anyadirComentarioCancion(Comentario comentario, Cancion cancion) {
		if((comentario == null  || cancion == null) && (sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO || sistema.getUsuarioActual() == null)) {
			new ExcepcionInformativa("\nEl comentario no se ha podido a�adir, lo sentimos");
			return Status.ERROR;
		}else {
			new ExcepcionInformativa("\nEl comentario fue a�adido correctamente");
			return cancion.anyadirComentario(comentario);
		}
	}
	
	/**
	 * Esta funcion que la puede utilizar cualquier tipo de usuario les permite escribir un comentario sobre un album
	 * @param comentario
	 * @param album
	 * @return OK si se a�adio correctamente al album o ERROR si no fue asi
	 */
	
	public Status anyadirComentarioAlbum(Comentario comentario, Album album) {
		if((comentario == null  || album == null) && (sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO || sistema.getUsuarioActual() == null)) {
			new ExcepcionInformativa("\nEl comentario no se ha podido a�adir, lo sentimos");
			return Status.ERROR;
		}else {
			new ExcepcionInformativa("\nEL comentario ha sido a�adido correctamente");
			return album.anyadirComentario(comentario);
		}
	}

	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON REPRODUCCIONES========================*/
	/*=================================================================================*/
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir una cancion que se pase como argumento
	 * @param c
	 * @throws InterruptedException ExcesoReproduccionesExcepcion
	 */
	
	public void reproducirCancion(Cancion c) throws InterruptedException,ExcesoReproduccionesExcepcion { //se supone que la cancion ha sido subida, valida y a la hora de buscar se devuelve en base a criterios ya comprobados
		if(c == null) {
			new ExcepcionInformativa("\nPor favor introduzca una cancion valida para reproducir");
			return;
		}
		LocalDate fecha_actual = LocalDate.now();
		
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			if(intervalo.getYears() < 18 && c.getEstado() == EstadoCancion.EXPLICITA) {
				return;
			}
			
			if(sistema.usuario_actual.getCanciones().contains(c) != true) {
				sistema.cancion_reproduciendose = c;
				sistema.cancion_reproduciendose.anyadirCola();
				sistema.cancion_reproduciendose.reproducir();
				Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
				sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
				new ExcepcionInformativa("\nReproduciendo la cancion " + c.getTitulo() + " Autor: " + c.getAutor().getNombreAutor() + " Duracion: " + c.getDuracion() + " Num.ReproduccionesAutor: " + c.getAutor().getNumeroReproducciones());
				for(Comentario comentario:c.getComentarios()) {
					new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
				}
			}else {
				sistema.cancion_reproduciendose = c;
				sistema.cancion_reproduciendose.anyadirCola();
				sistema.cancion_reproduciendose.reproducir();
				Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
				new ExcepcionInformativa("\nReproduciendo la cancion " + c.getTitulo() + " Autor: " + c.getAutor().getNombreAutor() + " Duracion: " + c.getDuracion() + " Num.ReproduccionesAutor: " + c.getAutor().getNumeroReproducciones());
				for(Comentario comentario:c.getComentarios()) {
					new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
				}
			}
			
		}else{
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				if(sistema.getUsuarioActual() != null) {
					Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
					if(intervalo.getYears() < 18 && c.getEstado() == EstadoCancion.EXPLICITA) {
						return;
					}
				}
				if(sistema.usuario_actual != null && sistema.usuario_actual.getCanciones().contains(c) != true) {
					sistema.cancion_reproduciendose = c;
					sistema.cancion_reproduciendose.anyadirCola();
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
					sistema.cancion_reproduciendose.getAutor().sumarReproduccion(sistema.getUmbralReproducciones());
					sistema.reproducciones_usuarios_no_premium++;
					new ExcepcionInformativa("\nReproduciendo la cancion " + c.getTitulo() + " Autor: " + c.getAutor().getNombreAutor() + " Duracion: " + c.getDuracion() + " Num.ReproduccionesAutor: " + c.getAutor().getNumeroReproducciones());
					for(Comentario comentario:c.getComentarios()) {
						new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
					}
				}else if(sistema.usuario_actual != null && sistema.usuario_actual.getCanciones().contains(c) == true) {
					sistema.cancion_reproduciendose = c;
					sistema.cancion_reproduciendose.anyadirCola();
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
					sistema.reproducciones_usuarios_no_premium++;
					new ExcepcionInformativa("\nReproduciendo la cancion " + c.getTitulo() + " Autor: " + c.getAutor().getNombreAutor() + " Duracion: " + c.getDuracion() + " Num.ReproduccionesAutor: " + c.getAutor().getNumeroReproducciones());
					for(Comentario comentario:c.getComentarios()) {
						new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
					}
				}else{
					if(c.getEstado() == EstadoCancion.EXPLICITA) {
						return;
					}
					new ExcepcionInformativa("\nReproduciendo la cancion " + c.getTitulo());
					sistema.cancion_reproduciendose = c;
					sistema.cancion_reproduciendose.anyadirCola();
					sistema.cancion_reproduciendose.reproducir();
					Thread.sleep((long) sistema.cancion_reproduciendose.getDuracion());
					sistema.reproducciones_usuarios_no_premium++;
					new ExcepcionInformativa("\nReproduciendo la cancion " + c.getTitulo() + " Autor: " + c.getAutor().getNombreAutor() + " Duracion: " + c.getDuracion() + " Num.ReproduccioneAutor: " + c.getAutor().getNumeroReproducciones());
					for(Comentario comentario:c.getComentarios()) {
						new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
					}
				}
			}else {
				new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
		
	}
	
	
	/**
	 * Esta funcion permite a cualquier usuario reproducir un album de cancion en cancion si es un usuario premium el que lo realiza o de manera limita si no lo es
	 * @param a
	 * @throws InterruptedException
	 * @throws ExcesoReproduccionesExcepcion
	 */
	
	public void reproducirAlbum(Album a) throws InterruptedException, ExcesoReproduccionesExcepcion {
		if(a == null) {
			new ExcepcionInformativa("\nPor favor introduzca un album valido para reproducir");
			return;
		}
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {
			new ExcepcionInformativa("\nReproduciendo el album: " + a.getTitulo() + " NumCancionesAlbum: " + a.getContenido().size());
			for(Comentario comentario:a.getComentarios()) {
				new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
			}
			for(Cancion canciones_reproduciendose:a.getContenido()) {
				sistema.reproducirCancion(canciones_reproduciendose);
			}
			
			return;
								
		}else {
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				new ExcepcionInformativa("\nReproduciendo el album: " + a.getTitulo() +  " NumCancionesAlbum: " + a.getContenido().size());
				for(Comentario comentario:a.getComentarios()) {
					new ExcepcionInformativa("\n\t\t" + comentario.getTexto());
				}
				for(Cancion canciones_reproduciendose:a.getContenido()) {
					if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
						new ExcesoReproduccionesExcepcion("\nHa superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
						return;
					}
					sistema.reproducirCancion(canciones_reproduciendose);
				}
				
				return;				
			}else {
				new ExcesoReproduccionesExcepcion("\nHa superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
	}
	
	/**
	 * Esta funcion permite reproducir una de tus listas
	 * @param l
	 * @throws ExcesoReproduccionesExcepcion 
	 * @throws InterruptedException 
	 */
	
	public void reproducirLista(Lista l) throws ExcesoReproduccionesExcepcion, InterruptedException {
		if(l == null) {
			new ExcepcionInformativa("\nPor favor introduzca una lista valida para ser reproducida");
			return;
		}
		
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {
			for(Cancion canciones_reproduciendose:l.getContenido()) {
				sistema.reproducirCancion(canciones_reproduciendose);
			}	
			return;
				
		}else {
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				for(Cancion canciones_reproduciendose:l.getContenido()) {
					if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
						new ExcesoReproduccionesExcepcion("\nHa superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
						return;
					}
					sistema.reproducirCancion(canciones_reproduciendose);
				}
				
				return;
			}else {
				new ExcesoReproduccionesExcepcion("\nHa superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
	}
	
	/**
	 * Esta funcion permite reproducir contenido de un autor dado
	 * @param contenido
	 * @throws InterruptedException
	 * @throws ExcesoReproduccionesExcepcion
	 */
	public void reproducirAutor(ArrayList<Contenido> contenido) throws InterruptedException, ExcesoReproduccionesExcepcion {
		if(contenido == null || contenido.size() == 0) {
			new ExcepcionInformativa("\nPor favor introduzca contenido valido para poder reproducir");
			return;
		}
		
		if(sistema.usuario_actual != null && (sistema.es_administrador == true || sistema.usuario_actual.getPremium() == true)) {
			for(Contenido contenido_uno:contenido) {
				if(contenido_uno instanceof Cancion) {
					if(sistema.getCancionTotales().contains(contenido_uno) == true) {
						sistema.reproducirCancion((Cancion) contenido_uno);
					}
				}else if(contenido_uno instanceof Album) {
					if(sistema.getAlbumTotales().contains(contenido_uno) == true) {
						sistema.reproducirAlbum((Album) contenido_uno);
					}
				}
			}
			return;
				
		}else {
			if(sistema.reproducciones_usuarios_no_premium < sistema.max_reproducciones_usuarios_no_premium){
				for(Contenido contenido_uno:contenido) {
					if(contenido_uno instanceof Cancion) {
						if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
							new ExcesoReproduccionesExcepcion("\nHa superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
							return;
						}
						sistema.reproducirCancion((Cancion) contenido_uno);
					}else if(contenido_uno instanceof Album) {
							if(sistema.reproducciones_usuarios_no_premium == sistema.max_reproducciones_usuarios_no_premium) {
								new ExcesoReproduccionesExcepcion("\nHa superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
								return;
							}
							sistema.reproducirAlbum((Album) contenido_uno);
						}
					}
			}else {
				throw new ExcesoReproduccionesExcepcion("Ha superado el numero maximo de reproducciones, si desea esuchar musica sin limites hazte premium!!!");
			}
		}
		
	}
	
	/**
	 * Esta funcion permite parar la reproduccion de la cancion que esta actualmente sonando
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	public void pararReproductor() throws FileNotFoundException, Mp3PlayerException {
		if(sistema.cancion_reproduciendose != null) {
			sistema.cancion_reproduciendose.parar();
			sistema.cancion_reproduciendose.setMp3Player();
		}
	}
	
}