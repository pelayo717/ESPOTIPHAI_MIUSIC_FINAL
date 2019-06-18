package modelo.sistema;


import modelo.contenido.*;
import modelo.reporte.*;
import modelo.status.*;
import modelo.usuario.*;


import pads.musicPlayer.exceptions.Mp3PlayerException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

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
	private ArrayList<Reporte> reportes_totales = new ArrayList<Reporte>();
	private Usuario usuario_actual = null;
	private boolean es_administrador = false;
	public static Sistema sistema = null;
	private int umbral_reproducciones = 30;
	private double precio_premium = 9.9;
	private int max_reproducciones_usuarios_no_premium = 4;
	private int contenido_escuchado_sin_registrarse = 0;
	
	
	/**
	 * Constructor de la clase sistema, aunque vacio inicializamos los valores al utilizar el connstructor
	 */
	public Sistema(){}
	
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
			if(archivo.exists() == true) {
				sistema = new Sistema();
				sistema = Sistema.cargarDatosGenerales();
				
				sistema.empeorarCuentaPrincipal();
				sistema.desbloquearUsuario();
				sistema.resetearContadoresNuevoMes();
				sistema.eliminarCancionPendienteModificacionCaducada();
			}else {
				sistema = new Sistema();
				sistema.registrarse("root1967", "ADMINISTRADOR",LocalDate.of(1967, 12, 26), "ADMINISTRADOR");
			}
		}
		return sistema;
	}

	/*=================================================================================*/
	/*======================FUNCIONES GENERALES DE GETTERS=============================*/
	/*=================================================================================*/
	
	/**
	 * Esta funcion retorna el numero maximo de reproducciones que puede realizar un usuario no registrado o registrado normal
	 * @return numero de canciones
	 */
	public int getMaxReproduccionesUsuariosNoPremium() {
		return sistema.max_reproducciones_usuarios_no_premium;
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
	 * Devuelve todos losreportes que han realizado los usuarios
	 * @return retorna el array general de reportes o null si no existe 
	 */
	public ArrayList<Reporte> getReportesTotales(){
		return sistema.reportes_totales;
	}
	
	/**
	 * Esta funcion es la encaragada de retornar el numero de reproducciones que ha escuchado un usuario sin registrarse
	 * @return numero de canciones
	 */
	public int getContenidoEscuchadoSinRegistrarse() {
		return sistema.contenido_escuchado_sin_registrarse;
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
	
	/*=================================================================================*/
	/*======================FUNCIONES GENERALES DE SETTERS=============================*/
	/*=================================================================================*/
	
	
	/**
	 * Esta funcion establece el umbral de reproducciones que debe superar un autor para pasar al estado de premium, y solo es aplicable a aquellos usuarios que estan registrados
	 * @param umbral indica el umbral de reproducciones que debe superar un usuario registrado para hacerse premium sin abonar nada
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
	 * @param precio indica el precio que hay que abonar para ascender a Premium
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
	 * @param x indica el numerode reproducciones maximas que puede escuchar un usuario no registrado o registrado normal
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
	 * Esta funcion es la encargada de sumar +1 cuando se reproduce una cancion si es un usuario sin registrarse
	 */
	public void addContenidoEscuchadoSinRegistrarse() {
		sistema.contenido_escuchado_sin_registrarse++;
	}
	
	/**
	 * Esta funcion es la encargada de establecer el usuario actual
	 * @param aux objeto de tipo usuario que va a inciar sesion
	 * @return devuelve ok si se establecio el usuario correctamente o error si no fue asi
	 */
	public Status setUsuarioActual(Usuario aux) {
		if(aux != null) {
			this.usuario_actual = aux;
			return Status.OK;
		}
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion es la encargada de establecer el usuario actual que es el administrador
	 * @param aux objeto de tipo usuario que va a iniciar sesion y es el administrador
	 */
	public void setAdministrador(boolean aux) {
		this.es_administrador = aux;
	}
	
	
	
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON LA CUENTA DE USUARIO==================*/
	/*=================================================================================*/
	
	/**
	 * Permite registrarse a un usuario creando un objeto de tipo usuario y almacenandolo en el array de usuarios totales
	 * @param nombre_usuario cadena que indica el nombre de usuario que va a recibir el usuario al registrarse
	 * @param nombre_autor cadena que indica el nombre de autor que va a recibir el usuario al registrarse
	 * @param fecha_nacimiento fecha que indica el dia,mes y año de nacimiento del usuario
	 * @param contrasenia cadena que indica la contrasenya que va a utilizar el usuario al iniciar sesion
	 * @return devuelve OK si el usuario se registro correctamente o ERROR si no lo consiguio
	 */
	public Status registrarse(String nombre_usuario,String nombre_autor,LocalDate fecha_nacimiento, String contrasenia) {
		
		int i=0;
		
		if(nombre_usuario == null || contrasenia == null || fecha_nacimiento == null) {
			return Status.ERROR;
		}
		
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true || usuario.getNombreAutor().equals(nombre_autor) == true) {
				break;
			}
			i++;
		}
		
		if(i < sistema.usuarios_totales.size()) {
			return Status.ERROR;
		}		
		
		Usuario usuario_registrado_nuevo = new Usuario(nombre_usuario,nombre_autor,fecha_nacimiento, contrasenia); 
		sistema.usuarios_totales.add(usuario_registrado_nuevo);
		return Status.OK;
	}
	
	/**
	 * Permite iniciar sesion en la aplicacion, ya sea el administrador o un usuario convencional
	 * @param nombre_usuario cadena, nombre de usuario necesario para el login
	 * @param contrasenia cadena, contrasenya necesario para el login
	 * @return devuelve OK si se inicio correctamente la sesion como usuario o ERROR si no se consiguio
	 */
	public EstadoInicioSesion iniciarSesion(String nombre_usuario, String contrasenia) {

		if(nombre_usuario == null || contrasenia == null) {
			return EstadoInicioSesion.DATOS_INCORRECTOS;
		}
		
		if(nombre_usuario.equals("root1967") && contrasenia.equals("ADMINISTRADOR") == true) {
			sistema.es_administrador = true;
		}
			
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getNombreUsuario().equals(nombre_usuario) == true && usuario.getContrasena().equals(contrasenia)== true) {
				if(usuario.getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
					sistema.usuario_actual = usuario;
					return EstadoInicioSesion.CORRECTO;
				}else {
					if(usuario.getEstadoBloqueado() == UsuarioBloqueado.INDEFINIDO) {
						return EstadoInicioSesion.BLOQUEADO;
					}else {
						return EstadoInicioSesion.TEMPORAL;
					}
				}
			}
			
		}
			
		return EstadoInicioSesion.DATOS_INCORRECTOS;
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
			}
			sistema.usuario_actual = null;
			guardarDatosGenerales();
			return Status.OK;
		}
		return Status.ERROR;
	}

	/**
	 * Esta funcion permite a un usuario(mientras no este bloqueado) eliminar su cuenta como usuario del sistema
	 * Mediante su eliminacion las canciones, albumes y listas desapareceran de la aplicacion. Tambien desapareceran
	 * (canciones y albumes) de todas las listas de otros usuarios que los contengan. Seran informados de este evento
	 * ocurrido y del porque. Logicamente tambien informaremos a todos aquellos usuarios que siguen al usuario a eliminar
	 * y a todos los usuarios a los que sigue. Previo a todo esto comprobaremos que el usuario a iniciado sesion y que este
	 * no se encuentra bloqueado por el sistema.
	 */
	public void eliminarCuenta() {
		if(sistema.usuario_actual != null && sistema.getAdministrador() == false && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			 for(Iterator<Usuario> iteratorUsuario = sistema.getUsuariosTotales().iterator(); iteratorUsuario.hasNext();) {
				 Usuario usuario = iteratorUsuario.next();
				 if(usuario.getNombreUsuario().equals(sistema.getUsuarioActual().getNombreUsuario()) == true && usuario.getContrasena().equals(sistema.getUsuarioActual().getContrasena()) == true) {
					 
					//elimino al usuario del array general, solo quedara una referencia al objeto y es el usuario_actual
					 iteratorUsuario.remove(); 
					 
					//Informamos a los seguidos que el usuario se va a eliminar
					 for(Usuario seguidos:usuario.getSeguidos()) {
						 usuario.dejarDeSeguirUsuario(seguidos);
						 usuario.enviarNotificacion(seguidos, "El usuario " + usuario.getNombreUsuario() + " le ha dejado de seguir.");
					 }
					 
					//Informamos a los seguidores que el usuario se va a eliminar
					 for(Usuario seguidores:usuario.getSeguidores()) {
						seguidores.dejarDeSeguirUsuario(sistema.getUsuarioActual());
						usuario.enviarNotificacion(seguidores, "El usuario " + usuario.getNombreUsuario() + " ha eliminado su cuenta.");
					 }
					 
					 //elimino sus albumes e informo a los usuarios que tengan las canciones en sus listas de su eliminacion
					 for(Cancion canciones_usuario: usuario.getCanciones()) {
						 sistema.eliminarCancion(canciones_usuario);
					 }
					 
					 //elimino sus albumes e informo a los usuarios que tengan los albumes en sus listas de su eliminacion
					 for(Iterator<Album> iteratorAlbum = usuario.getAlbumes().iterator(); iteratorAlbum.hasNext();) {
						 Album albumes_usuario = iteratorAlbum.next();
						 sistema.eliminarAlbum(albumes_usuario);
					 }
					 
					 //elimino sus listas
					 for(Iterator<Lista> iteratorAlbum = usuario.getListas().iterator(); iteratorAlbum.hasNext();) {
						 Lista listas_usuario = iteratorAlbum.next();
						 /*sistema.eliminarLista(listas_usuario);*/
					 }
					 
					 //elimino sus notificaciones
					 sistema.getUsuarioActual().eliminarNotificacionesPropias();
					 
					 
					 //Cierro la sesion para este usuario ya no existente
					 sistema.usuario_actual = null;
					 
					 return;
				 }
			 }
		}
		
		return;
	}
	
	/*=================================================================================*/
	/*================FUNCIONES AUTOMATICAS AL INICIAR LA APLICACION===================*/
	/*=================================================================================*/
	
	/**
	 * Funcion que se ejcutara de manera periodica y que comprobara todos 
	 * aquellos usuarios que han excedido el tiempo de usuario premium y los degrada a usuarios registrados normales.
	 * Para todos los usuarios, mirara aquellos que son premium y conociendo el dia que se hicieron premium se 
	 * mirara si ya han pasado 30 dias. De ser asi se les degradara y se les informara de lo sucedido.
	 */
	public void empeorarCuentaPrincipal() {

		LocalDate fecha_actual = LocalDate.now();
		for(Usuario usuario:sistema.usuarios_totales) {
			if(usuario.getPremium() == true) {
				LocalDate fecha_inicio_premium = usuario.getFechaInicioPro();
				if(fecha_actual.minusDays(30).isAfter(fecha_inicio_premium) == true || fecha_actual.minusDays(30).isEqual(fecha_inicio_premium) == true) {
					usuario.emperorarCuenta();
					
					//HACEMOS QUE SEA EL ADMINISTRADOR QUIEN AVISE AL USUARIO
					sistema.getUsuariosTotales().get(0).enviarNotificacion(usuario, "Tras 15 dias su cuenta ha sido degrada a registrado normal, abone el importe para disfrutar de ser premium");
				}
			}
		}
		return;
	}

	/**
	 * Funcion que se ejecuta de manera periodica y que comprobara todos
	 * aquellos usuarios que han superado el tiempo de bloqueo establecido procediendo al desbloqueo
	 * Mirara para todos los usuarios aquellos que estan bloqueados con el estado de TEMPORAL.
	 * Conociendo ademas la fecha en la que fueron bloqueados se calculara si se ha cumplido el tiempo de sancion
	 * y de ser asi se procedera al desbloqueo.
	 */
	public void desbloquearUsuario() {
		
		LocalDate fecha_actual = LocalDate.now();
		for(Usuario usuario: sistema.usuarios_totales) {
			if(usuario.getEstadoBloqueado() == UsuarioBloqueado.TEMPORAL) {
				LocalDate fecha_inicio_bloqueado = usuario.getFechaInicioBloqueado();
				if(fecha_actual.minusDays(30).isAfter(fecha_inicio_bloqueado) == true || fecha_actual.minusDays(30).isEqual(fecha_inicio_bloqueado) == true ) {
					usuario.desbloquearCuenta();
					
					//HACEMOS QUE SEA EL ADMINISTRADOR QUIEN AVISE AL USUARIO
					sistema.getUsuariosTotales().get(0).enviarNotificacion(usuario, "Tras 30 dias ha sido desbloqueado y puede usar de nuevo el sistema");
				}
			}
		}
	}
	
	/**
	 * Funcion que se ejecuta de manera periodica y que se encarga de restear
	 * los contadores de reproducciones de los usuarios no registrados y de los
	 * usuarios registrados no premium el primer dia de cada mes.
	 */
	private void resetearContadoresNuevoMes() {
		
		LocalDate fecha_actual = LocalDate.now();
		if(fecha_actual.getDayOfMonth() == 1) {
			
			//PARA AQUELLOS QUE NO HAN INICIADO SESION
			sistema.contenido_escuchado_sin_registrarse = 0;
			
			//PARA AQUELLOS QUE SI INICIARON SESION PERO NO SON PREMIUM
			for(Usuario usuarios_totales: sistema.usuarios_totales) {
				if(usuarios_totales.getPremium() == false) {
					usuarios_totales.resetContenidoEscuchadosSinSerPremium();
				}
			}
		}
		
	}

	/**
	 * Funcion que se ejecuta de manera periodica y que se encarga de eliminar
	 * aquellas canciones que no han sido modificadas por el usuario y han excedido
	 * el tiempo que se les dio para hacerlo. Ya que una vez creadas las canciones estas
	 * permanecen en ambos arrays de canciones(Sistema y Usuario) aunque con estado de 
	 * PENDIENTEDEVALIDACION o PENDIENTEMODIFICACION se comprobara en el array general 
	 * todas aquellas que cumplen los requisitos anteriores, y luego se procedera a 
	 * eliminarlas de cada array de canciones de cada usuario.
	 */
	private void eliminarCancionPendienteModificacionCaducada() {
		
		LocalDate d = LocalDate.now();
		
		//ELIMINAMOS CANCIONES QUE HAN PASADO EL TIEMPO DE MODIFICACION
		for(Cancion canciones_eliminar_sistema:sistema.getCancionTotales()) {
			if(canciones_eliminar_sistema.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && d.minusDays(3).isAfter(canciones_eliminar_sistema.getFechaModificacion())) {
				sistema.getCancionTotales().remove(canciones_eliminar_sistema);
			}
		}
		
		//ELIMINAMOS LAS CANCIONES QUE SIGUEN EL MIMSO PATRON PERO EN CADA UNO DE LOS ARRAYS DE CANCIONES DE USUARIO
		for(Usuario usuarios_totales:sistema.getUsuariosTotales()) {
			for(Cancion canciones_en_usuario:usuarios_totales.getCanciones()) {
				if(canciones_en_usuario.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && d.minusDays(3).isAfter(canciones_en_usuario.getFechaModificacion())) {
					usuarios_totales.getCanciones().remove(canciones_en_usuario);
				}
			}
		}
		
		
	}
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON LA BUSQUEDA POR CRITERIOS=============*/
	/*=================================================================================*/
	
	/**
	 * Permite realizar una busqueda en todas las canciones al introducir una cadena
	 * @param palabra criterio para buscar por titulo
	 * @return retorna un arraylist de elementos de tipo cancion si se encuentra contenido igual
	 * o que contiene la cadena introducida por parametro
	 */
	public ArrayList<Cancion> buscadorPorTitulos(String palabra){
		
		LocalDate fecha_actual = LocalDate.now();
		ArrayList<Cancion> canciones_retornar = new ArrayList<Cancion>();
		
		if(palabra == null) {
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
					return null;
				}

				return canciones_retornar;
			}
		}

		for(Cancion cancion: sistema.canciones_totales) {
			
			if(cancion.getEstado() == EstadoCancion.VALIDA && (cancion.getTitulo().equals(palabra) == true || cancion.getTitulo().contains(palabra) == true)) {
				canciones_retornar.add(cancion);
			}
		}
		
		if(canciones_retornar.size() == 0) {
			return null;
		}
		return canciones_retornar;
	}

	/**
	 * Permite ralizar una busqueda en todos los albumes al introducir una cadena
	 * @param palabra criterio para buscar por album
	 * @return retorna un arraylist de elementos de tipo album si se encuentra contenido igual
	 * o que contiene la cadena introducida por parametro 
	 */
	public ArrayList<Album> buscadorPorAlbumes(String palabra){
		LocalDate fecha_actual = LocalDate.now();
		ArrayList<Album> albumes_incluidas_explicitas = new ArrayList<Album>();
		int flag = 0;
				
		if(palabra == null) {
			return null;
		}
				
		if(sistema.usuario_actual != null) {
						
			Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
			if(intervalo.getYears() >= 18) {
				
				for(Album album_totales:sistema.albumes_totales) {
					flag = 0;
					if(album_totales.getTitulo().equals(palabra) == true || album_totales.getTitulo().contains(palabra) == true) {
						for(Cancion canciones_album: album_totales.getContenido()) {
							if(canciones_album.getEstado() == EstadoCancion.PLAGIO) { //Estado de la cancion que mientras no este en plagio se anyade
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
					return null;
				}
				
				return albumes_incluidas_explicitas;
			}
		}
		
		for(Album album_totales:sistema.albumes_totales) {
			flag = 0;
			
			if(album_totales.getTitulo().equals(palabra) == true || album_totales.getTitulo().contains(palabra) == true) {
							
				for(Cancion canciones_album: album_totales.getContenido()) {
										
					if(canciones_album.getEstado() == EstadoCancion.PLAGIO) { //Estados de las canciones una vez fueron anyadidas al album
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
			return null;
		}
		
		return albumes_incluidas_explicitas;
		
	}

	/**
	 * Permite para un autor dado buscar todas sus canciones
	 * @param palabra criterio para buscar por autor
	 * @return retorna un arraylist de elementos de tipo cancion aplicados a un autor concreto
	 * si se encuentra alguno mediante el parametro introducido
	 */
	public ArrayList<Cancion> buscadorPorAutores_DevolvemosCanciones(String palabra){
		
		int ide = 0;
		ArrayList<Cancion> lista_autor_canciones = new ArrayList<Cancion>();
		LocalDate fecha_actual = LocalDate.now();
		
		if(palabra == null) {
			return null;
		}
		
		
		for(Usuario usuario: sistema.usuarios_totales) {
			
			if(usuario.getNombreAutor().equals(palabra) == true || usuario.getNombreAutor().contains(palabra) == true) {
				
				if(usuario.equals(Sistema.sistema.getUsuariosTotales().get(0)) == false) { //MIENTRAS NO SEA ROOT
					
					if(sistema.usuario_actual != null) {
						Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
						
						if(intervalo.getYears() >= 18) {
							for(Cancion cancion: usuario.getCanciones()) {
								if(cancion.getEstado() == EstadoCancion.EXPLICITA || cancion.getEstado() == EstadoCancion.VALIDA) {
									lista_autor_canciones.add(cancion);
								}
							}	
						}else {
							for(Cancion cancion: usuario.getCanciones()) {
								if(cancion.getEstado() == EstadoCancion.VALIDA) {
									lista_autor_canciones.add(cancion);
								}
							}
						}
					}else {
					
						for(Cancion cancion: usuario.getCanciones()) {
							if(cancion.getEstado() == EstadoCancion.VALIDA) {
								lista_autor_canciones.add(cancion);
							}
						}
					
					}
				}
			}
		}
		
		if(lista_autor_canciones.size() == 0) {
			return null;
		}
		
		return lista_autor_canciones;
		
		
		
	}
	
	/**
	 * Permite para un autor dado buscar todos sus albumes
	 * @param palabra criterio para buscar por autor
	 * @return retorna un arraylista de elementos de tipo album aplicados a un autor concreto
	 * si se encuentra alguno mediante el parametro introducido
	 */
	public ArrayList<Album> buscadorPorAutores_DevolvemosAlbumes(String palabra){
		
		ArrayList<Album> albumes_filtrados = new ArrayList<Album>();
		
		if(palabra == null) {
			return null;
		}
				
		for(Usuario usuario: sistema.usuarios_totales) {
						
			if(usuario.getNombreAutor().equals(palabra) == true || usuario.getNombreAutor().contains(palabra) == true) {
				
				
				if(usuario.equals(Sistema.sistema.getUsuariosTotales().get(0)) == false) { //MIENTRAS NO SEA ROOT
					
					
					for(Album album: usuario.getAlbumes()) {
						albumes_filtrados.add(album);
						
					}	

				}
			}
			
			
		}
				
		if(albumes_filtrados.size() == 0) {
			return null;
		}
		
		
		return albumes_filtrados;
		
	}
	
	/**
	 * Buscador general de autores, se introduce una cadena y este busca canciones y albumes del autor.
	 * @param palabra criterio para buscar por autor
	 * @return retorna un arraylist de contenido si sencuentran canciones y/o albumes, sino devuelve null.
	 */
	public ArrayList<Contenido> buscadorPorAutores(String palabra){
		if(palabra == null) {
			return null;
		}
		
		
		ArrayList<Contenido> contenido = new ArrayList<Contenido>();
		
		ArrayList<Cancion> canciones_autor = sistema.buscadorPorAutores_DevolvemosCanciones(palabra);
		

		
		if(canciones_autor != null) {
			
			for(Cancion cancion_una:canciones_autor) {
				
				contenido.add((Cancion)cancion_una);
			}
		}
		
		ArrayList<Album> albumes_autor = sistema.buscadorPorAutores_DevolvemosAlbumes(palabra);
		
		
		if(albumes_autor != null) {
						
			for(Album album_una:albumes_autor) {
								
				contenido.add((Album)album_una);
			}
		}
		
		
		if(contenido.size() > 0) {
			return contenido;
		}else {
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
						
			if(sistema.getCancionTotales().contains(c) == true && (d.minusDays(3).isBefore(c.getFechaModificacion()) == true || d.minusDays(3).isEqual(c.getFechaModificacion()) == true)&& c.getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
								
				c.setNombreMP3(NombreMp3);
				if(c.esMP3() == true){
										
					c.setDuracion(c.devolverDuracion());
					Sistema.sistema.getUsuarioActual().enviarNotificacion(Sistema.sistema.getUsuariosTotales().get(0), "El usuario " + Sistema.sistema.getUsuarioActual().getNombreUsuario() + " ha modificado su cancion " + c.getTitulo() + " dentro del plazo permitido");
					return Status.OK;
				}
			}
		}
		
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
	public Cancion crearCancion(String titulo,String nombreMP3) throws FileNotFoundException, Mp3PlayerException{
		//LocalDate fecha_actual = LocalDate.now();
		if(titulo == null || nombreMP3 == null) {
			return null;
		}
		
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			//Period intervalo = Period.between(sistema.usuario_actual.getFechaNacimiento(), fecha_actual);
					
			Cancion c = new Cancion(titulo,sistema.usuario_actual,nombreMP3);
			
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(titulo) == true && cancion.getNombreMP3().equals(nombreMP3) == true) {
					return null;
				}	
			}
			
			
			for(Cancion cancion:sistema.getCancionTotales()) {
				if(cancion.getTitulo().equals(titulo) == true && cancion.getNombreMP3().equals(nombreMP3) == true) {
					return null;
				}
			}
			
			//MIRAMOS LA DURACION DE LA CANCION
			if(c.getDuracion() > 1800) {
				return null;
			}
			
			//Lo introducimos en el array de canciones personales al cearla
			sistema.usuario_actual.anyadirACancionesPersonales(c);
			
			//Lo introducimos en el array de canciones totales de la aplicacion
			sistema.canciones_totales.add(c);
			
			return c;
			
			
		}else {
			return null;
		}
		
	}
	
	/**
	 * Permite a un usuario eliminar una de sus propias canciones(realmente en vez de eliminar el objeto le cambiamso el estado a Eliminado para que luego no se reproduzca ni se reconozca)
	 * @param cancion_eliminar
	 * @return retorna ERROR si la cancion no existe o no se puede eliminar del sistema u OK si se elimina correctamente
	 */
	public Status eliminarCancion(Cancion cancion_eliminar) {		
		
		if(cancion_eliminar == null) {
			return Status.ERROR;
		}
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
					
			if(cancion_eliminar.getAutor().getNombreAutor().equals(sistema.getUsuarioActual().getNombreAutor()) && cancion_eliminar.getEstado() != EstadoCancion.PLAGIO) {
				
				//ELIMINAMOS DE SITIOS DEL PROPIO SISTEMA
				if(sistema.getCancionTotales().contains(cancion_eliminar) == true) {
					sistema.getCancionTotales().remove(cancion_eliminar);
				}
				
				for(Album albumes_totales: sistema.getAlbumTotales()) {
					if(albumes_totales.getContenido().contains(cancion_eliminar) == true) {
						albumes_totales.eliminarContenido(cancion_eliminar);
					}
				}
				
				
				//ELIMINAMOS DE SITIOS DEL PROPIO AUTOR(SU CANCION EN SUS ALBUMES, SOLO EL!!!)
				
				if(sistema.getUsuarioActual().getCanciones().contains(cancion_eliminar) == true) {
					sistema.getUsuarioActual().eliminarDeCancionesPersonales(cancion_eliminar);
				}
				
				for(Album albumes_usuario: sistema.getUsuarioActual().getAlbumes()) {
					if(albumes_usuario.getContenido().contains(cancion_eliminar) == true) {
						albumes_usuario.eliminarContenido(cancion_eliminar);
					}
				}
				
				
				//ELIMINAMOS CONTENIDO DE LAS LISTAS Y ALBUMES DE TODOS LOS USUARIOS
				
				for(Usuario usuarios_totales:sistema.getUsuariosTotales()) {
					if ( usuarios_totales.getListas() != null) {
						for(Lista lista: usuarios_totales.getListas()) {
							System.out.println(lista);
							lista.eliminarContenido(cancion_eliminar);
						}
						
						//ENVIAMOS NOTIFICACION AL USUARIO QUE CONTENIA LA CANCION EN SUS LISTAS
						if(sistema.getUsuariosTotales().contains(sistema.getUsuarioActual()) == false) {
							sistema.getUsuarioActual().enviarNotificacion(usuarios_totales, "El usuario " + sistema.getUsuarioActual().getNombreUsuario() + " ha eliminado la cancion " + cancion_eliminar.getTitulo() + " ya que se ha dado de baja.");
						}else{
							sistema.getUsuarioActual().enviarNotificacion(usuarios_totales, "El usuario " + sistema.getUsuarioActual().getNombreUsuario() + " ha eliminado la cancion " + cancion_eliminar.getTitulo() + ".");
						}
					}
				}
				
				
				
				return Status.OK;
			}else {
				return Status.ERROR;
			}
			
		}else {
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
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	public Album crearAlbum(int anyo,String titulo) throws FileNotFoundException, Mp3PlayerException {
		if(titulo == null) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {			
			Album a = new Album(anyo,titulo,sistema.usuario_actual,new ArrayList<Cancion>());
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(titulo)== true) {
					return null;
				}
			}
			
			for(Album album:sistema.getAlbumTotales()) {
				if(album.getTitulo().equals(titulo) == true) {
					return null;
				}
			}
			
			
			sistema.usuario_actual.anyadirAAlbumesPersonales(a);
			sistema.albumes_totales.add(a);
			return a;
			
		}else {
			return null;
		}
	
	}
	
	/**
	 * Permite a un usuario eliminar uno de sus propios albumes(a diferencia de las canciones que mantendremos las referencias en este caso eliminaremos directamente la referencia al objeto en ambos arraylists de albumes)
	 * @param album_eliminar
	 * @return retorna ERROR si la cancion no se elimina del album, OK si se consigue
	 */
	public Status eliminarAlbum(Album album_eliminar) {
		if(album_eliminar == null) {
			return Status.ERROR;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			if(album_eliminar.getAutor().getNombreAutor().equals(sistema.getUsuarioActual().getNombreAutor())) {
				
				//ELIMINAMOS DEL PROPIO SISTEMA
				sistema.getAlbumTotales().remove(album_eliminar);

				
				//ELIMINAMOS DE ALBUMES DEL AUTOR
				/*sistema.getUsuarioActual().eliminarDeAlbumesPersonales(album_eliminar);*/

				//ELIMINAMOS DE LAS LISTAS EN LAS QUE SE ENCUENTRE
				for(Usuario usuarios_totales:sistema.getUsuariosTotales()) {
					
					for(Lista lista: usuarios_totales.getListas()) {
						lista.eliminarContenido(album_eliminar);
					}
					
					//ENVIAMOS NOTIFICACION AL USUARIO QUE CONTENIA EL ALBUM EN SUS LISTAS
					if(sistema.getUsuariosTotales().contains(sistema.getUsuarioActual()) == false) {
						sistema.getUsuarioActual().enviarNotificacion(usuarios_totales, "El usuario " + sistema.getUsuarioActual().getNombreUsuario() + " ha eliminado el album " + album_eliminar.getTitulo() + " ya que se ha dado de baja.");
					}else {
						sistema.getUsuarioActual().enviarNotificacion(usuarios_totales, "El usuario " + sistema.getUsuarioActual().getNombreUsuario() + " ha eliminado el album " + album_eliminar.getTitulo() + ".");
					}
				}
				
				return Status.OK;
			}else {
				return Status.ERROR;
			}
		}else {
			return Status.ERROR;
		}
	}
	
	/**
	 * Esta funcion permite a un usuario anyadir a sus albumes sus propias canciones
	 * @param a
	 * @param c
	 * @return retorna OK si se anyade correctamente la cancion al album, de lo contrario nos devolvera ERROR
	 */
	public Status anyadirCancionAAlbum(Album a, Cancion c) {
		int x=0;
		
		LocalDate fecha_actual = LocalDate.now();
		Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
		
		if(a == null || c == null) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && sistema.es_administrador == false) {
			for(Cancion cancion:sistema.getUsuarioActual().getCanciones()) {
				
				if(cancion.getTitulo().equals(c.getTitulo()) == true && cancion.getNombreMP3().equals(c.getNombreMP3()) == true) {
					if(c.getEstado() == EstadoCancion.VALIDA || (c.getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() >= 18)) {
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
									return Status.ERROR;
								}
								
								if(album.anyadirContenido(c) == Status.OK) {
									return Status.OK;
								}else {
									return Status.ERROR;
								}
									
							}
						}
					
					}
				}
			}	
		}
		
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
										return Status.OK;
									}else {
										return Status.ERROR;
									}
								}
							}	
						}
					}
				}
			}	
			
		}
		
		return Status.ERROR;
	
	}
	
	/**
	 * Permte al usuario registrado o registrado premium crear una lista de contenidos que inicialmente estara vacia y unicamente sera visible para el
	 * El contenido final que almacenara la lista sera de tipo Cancion aunque a la hora de anyadir podra ser de tipo LISTA,CANCION o ALBUM
	 * @param anyo
	 * @param titulo
	 * @param id
	 * @param autor
	 * @param contenido
	 * @return retorna la referencia al objeto lista si se creo correctamente, y de lo contrario devolvera null
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	public Lista crearLista(String titulo) throws FileNotFoundException, Mp3PlayerException {
		
		boolean lista_repetida_en_usuario = false; 
		if(titulo == null || titulo.equals("") == true) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			Lista l = new Lista(titulo,sistema.usuario_actual,new ArrayList<Contenido>());
			for(Lista lista:sistema.usuario_actual.getListas()) {
				if(lista.getTitulo().equals(titulo) == true) {
					lista_repetida_en_usuario = true;
				}
			}
			if(lista_repetida_en_usuario == true) {
				return null;
			}else {
				sistema.usuario_actual.anyadirAListasPersonales(l);
				return l;
			}
		}else {
			return null;
		}
	}
	
	/**
	 * Permite al usuario eliminar una de sus propias listas(eliminamos en el arraylist de listas en usuario la referencia al objeto correspondiente)
	 * @param lista_eliminar
	 * @return retorna OK si la lista fue eliminada correctamente, ERROR si no se elimino del sistema
	 */
	public Status eliminarLista(Lista lista_eliminar) {
		if(lista_eliminar == null) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			if(sistema.getUsuarioActual().getListas().contains(lista_eliminar)==true) {
				
				//LISTA PROPIA
				sistema.getUsuarioActual().eliminarDeListasPersonales(lista_eliminar);
				for(Iterator<Lista> iterator = sistema.getUsuarioActual().getListas().iterator(); iterator.hasNext();) {
					Lista l_c = iterator.next();
					l_c.eliminarContenido(lista_eliminar);
				}
				
				return Status.OK;
			}else {
				return Status.ERROR;
			}
		}else {
			return Status.ERROR;
		}
	}
	
	/**
	 * Esta funcion permite anyadir un contenido(LISTA,CANCION o ALBUM) a una lista si no esta incluido ya
	 * @param l
	 * @param c
	 * @return retorna OK si se anyadio correctamente, ERROR si no fue asi
	 */
	public Status anyadirALista(Lista l, Contenido c) {
		if(l == null || c == null) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
				if(sistema.getUsuarioActual().getListas().contains(l) == true) {
					if(l.anyadirContenido(c) == Status.OK) {
						return Status.OK;
					}else {
						return Status.ERROR;
					}
				}
		}
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
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			if(sistema.getUsuarioActual().getListas().contains(l) == true) {
				 if(l.eliminarContenido(c) == Status.OK) {
					 return Status.OK;
				 }else {
					 return Status.ERROR;
				 }
			}
		}
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
			FileOutputStream fileOut = new FileOutputStream("datos.obj");
			ObjectOutputStream oos = new ObjectOutputStream(fileOut);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			fileOut.close();
			return Status.OK;
			
		}catch(IOException ie) {
			ie.toString();
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
			FileInputStream in = new FileInputStream("datos.obj");
			ObjectInputStream oin = new ObjectInputStream(in);
			Sistema s1 = (Sistema) oin.readObject();
			oin.close();
			in.close();
			return s1;
		}catch(IOException ie) {
			ie.toString();
			return null;
		}catch(ClassNotFoundException ce) {
			ce.toString();
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

	public Status guardarDatosConfiguracion(double precio,int umbral,int reproducciones) throws IOException {
		if(precio < 0.0 || umbral <= 0 || reproducciones <= 0) {
			return null;
		}
		
		if(sistema.usuario_actual != null && sistema.es_administrador == true) {
			sistema.precio_premium = precio;
			sistema.max_reproducciones_usuarios_no_premium = reproducciones;
			sistema.umbral_reproducciones = umbral;
		}
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
	
	
	public ArrayList<Cancion> getCancionesPendientesValidacion() {
		ArrayList<Cancion> para_validar = new ArrayList<Cancion>();
		
		for(Cancion c_t:sistema.getCancionTotales()) {
			
			if(c_t.getEstado() == EstadoCancion.PENDIENTEAPROBACION) {
				para_validar.add(c_t);
			}else if(c_t.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && (c_t.getFechaModificacion().minusDays(3).isEqual(LocalDate.now()) == true || c_t.getFechaModificacion().minusDays(3).isBefore(LocalDate.now()) == true)) {
				para_validar.add(c_t);
			}
		}
		
		return para_validar;
	}
	
	
	public void gestionarCancionesPendientesValidacion_Modificacion(Cancion c, EstadoCancion estado) {
		
		if(c == null || estado == null) {
			return;
		}
		
		if(sistema.getUsuarioActual() != null && sistema.es_administrador == true) {
			if(estado == EstadoCancion.VALIDA || estado == EstadoCancion.EXPLICITA) {
				c.setEstado(estado);
				c.setEstadoAnterior(estado);
				if(estado == EstadoCancion.VALIDA) {
					sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " ha sido validada correctamente.");
				}else if(estado == EstadoCancion.EXPLICITA) {
					sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " ha sido validada correctamente considerada explicita.");
				}
				
				for(Usuario u_t:sistema.getUsuariosTotales()) {
					if(u_t.getSeguidos().contains(c.getAutor()) == true) {
						sistema.getUsuarioActual().enviarNotificacion(u_t, "El autor " + c.getAutor() + " ha subido la cancion " + c.getTitulo() + ".");
					}
				}
			
			}else if(estado == EstadoCancion.PENDIENTEMODIFICACION) {
				c.setEstado(estado);
				sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " no ha sido validada, tiene 3 dias para modificarla y la validaremos de nuevo.");
			}else if(estado == EstadoCancion.ELIMINADA) {
				sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " no ha sido validada y procederemos a eliminarla.");
				c.getAutor().getCanciones().remove(c);
				sistema.getCancionTotales().remove(c);
			}
		}
	}
	
	
	/*=================================================================================*/
	/*=================FUNCIONES RELACIONADAS CON DENUNCIAS============================*/
	/*=================================================================================*/
	
	public Status denunciarPlagio(Cancion c) {
				
		if(sistema.getUsuarioActual()!= null && sistema.es_administrador == false && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			if(sistema.getUsuarioActual().getCanciones().contains(c) == true) {
				return Status.ERROR;
			}
			
			Reporte r = new Reporte(sistema.getUsuarioActual(),c);
			if(sistema.getReportesTotales().contains(r)) {
				return Status.ERROR; //REPORTE YA REALIZADO POR LA MISMA PERSONA CON LA MISMA CANCION
			}
			
			c.setEstado(EstadoCancion.PLAGIO);
			c.getAutor().bloquearCuentaTemporal();
			sistema.getUsuariosTotales().get(0).enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " ha sido bloqueada por un reporte y usted de manera temporal. Comprobaremos esta informacion con la mayor brevedad porsible.");
			sistema.getUsuarioActual().enviarNotificacion(sistema.getUsuariosTotales().get(0), "El usuario " + sistema.getUsuarioActual().getNombreUsuario() + " ha reportado la cancion " + c.getTitulo() + ".");
			
			sistema.getReportesTotales().add(r);
			
			return Status.OK;
		}
		
		return Status.ERROR;
		
	}
	
	public void eliminar_reporte(Reporte r) {
		if(sistema.getUsuarioActual() != null && sistema.es_administrador == true) {
			sistema.getReportesTotales().remove(r);
		}
	}
	
	public void gestionarReportes(Reporte r,boolean aceptada) {
		if(sistema.getUsuarioActual() != null && sistema.es_administrador == true) {
			if(aceptada == true) {
				//BLOQUEAR CANCION PERMANENTEMENTE
				r.getCancionReportada().setEstado(EstadoCancion.PLAGIO);
				
				//BLOQUEAR USUARIO PERMANENTEMENTE
				r.getCancionReportada().getAutor().bloquearCuentaIndefinido();

				//ENVIAR NOTIFICACION
				sistema.getUsuarioActual().enviarNotificacion(r.getCancionReportada().getAutor(),"Hemos aceptado el reporte de la cancion " + r.getCancionReportada().getTitulo() + " y le informamos que ha sido bloqueado permanentemente.");
				
			}else {
				//BLOQUEAR TEMPORALMENETE AL QUE REPORTO
				r.getUsuarioReportador().bloquearCuentaTemporal();
				
				//DESBLOQUEAR LA CANCION
				r.getCancionReportada().setEstado(r.getCancionReportada().getEstadoAnterior());
				
				//INFORMAR DEL BLOQUEO AL USUARIO Y AL OTRO DEL DESBLOQUEO
				sistema.getUsuarioActual().enviarNotificacion(r.getUsuarioReportador(), "Ha sido bloqueado temporalmente por el reporte de la cancion " + r.getCancionReportada().getTitulo() + " que no ha sido aceptado.");
				sistema.getUsuarioActual().enviarNotificacion(r.getCancionReportada().getAutor(), "El usuario denunciante del reporte ha sido bloqueado durante 30 dias.");
			}
			
			//ELIMINAR REPORTE
			sistema.getReportesTotales().remove(r);
		}
	}
	
	public Status modificarCriteriosAplicacion(int u_r,double p_p, int m_r) {
		if(sistema.getUsuarioActual() != null && sistema.es_administrador == true) {
			sistema.setPrecioPremium(p_p);
			sistema.setUmbralReproducciones(u_r);
			sistema.setMaxReproduccionesUsuarioNoPremium(m_r);
			return Status.OK;
		}
		
		return Status.ERROR;
	}
	
}



