package modelo.sistema;


import modelo.contenido.*;
import modelo.reporte.*;
import modelo.status.*;
import modelo.usuario.*;
import pads.musicPlayer.exceptions.Mp3PlayerException;

import java.util.ArrayList;
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
 * @author Pelayo Rodriguez Aviles
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
	 * Constructor de la clase sistema, se encarga de inicializar los datos que seran mas tarde utilizados
	 * @throws Mp3PlayerException 
	 * @throws FileNotFoundException 
	 */
	public Sistema(){}
	
	/**
	 * Para la implementacion del patron singleton, creamos este metodo que comprobara la existencia de un objeto
	 * de tipo Sistema estatico. Si no esta creado lo creara con los valores inciales, si estuvo creado previamente y
	 * se guardaron correctamente los datos se cargan del fichero correspondiente en disco.
	 * @return Objeto de tipo sistema que sera cargado con datos si previamente habia un fichero de carga 
	 * o se creara uno nuevo si no es asi
	 * @throws Mp3PlayerException
	 * @throws IOException
	 */
	public static Sistema getSistema() throws Mp3PlayerException, IOException {
		if(sistema == null) {
			File archivo = new File("datos.ser");		
			if(archivo.exists() == true) {
				sistema = new Sistema();
				sistema = Sistema.cargarDatosGenerales();

				sistema.actualizarPathCanciones();
				sistema.empeorarCuentaPrincipal();
				sistema.desbloquearUsuario();
				sistema.resetearContadoresNuevoMes(LocalDate.now());
				sistema.eliminarCancionPendienteModificacionCaducada(LocalDate.now());
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
	 * @return max_reproducciones_usuarios_no_premium: entero y siempre positivo o igual a 0
	 */
	public int getMaxReproduccionesUsuariosNoPremium() {
		return sistema.max_reproducciones_usuarios_no_premium;
	}
	
	
	/**
	 * Devuelve todos los usuarios que estan registrados en el sistema
	 * @return usuarios_totales: retorna el array general de usuarios o null si no existe
	 */
	public ArrayList<Usuario> getUsuariosTotales(){
		return sistema.usuarios_totales;
	}
	
	/**
	 * Devuelve todas las canciones que estan en el sistema
	 * @return canciones_totales: retorna el array general de canciones o null si no existe
	 */
	public ArrayList<Cancion> getCancionTotales(){
		return sistema.canciones_totales;
	}
	
	/**
	 * Devuelve todos los albumes que estan en el sistema
	 * @return albumes_totales: retorna el array general de albumes o null si no existe
	 */
	public ArrayList<Album> getAlbumTotales(){
		return sistema.albumes_totales;
	}
	
	/**
	 * Devuelve todos losreportes que han realizado los usuarios
	 * @return reportes_totales: retorna el array general de reportes o null si no existe 
	 */
	public ArrayList<Reporte> getReportesTotales(){
		return sistema.reportes_totales;
	}
	
	/**
	 * Esta funcion es la encaragada de retornar el numero de reproducciones que ha escuchado un usuario sin registrarse
	 * @return contenido_escuchado_sin_registrarse: entero que solo acepta valores positivos o 0
	 */
	public int getContenidoEscuchadoSinRegistrarse() {
		return sistema.contenido_escuchado_sin_registrarse;
	}
	
	
	/**
	 * Devuelve el usuario que ha iniciado sesion actualmente
	 * @return usuario_actual: retorna el usuario actual si inicio sesion, null si no se inicio sesion
	 */
	public Usuario getUsuarioActual() {
		return sistema.usuario_actual;
	}
	
	/**
	 * Devuelve el umbral de reproducciones que deben superar los usuarios
	 * @return umbral_reproducciones: retorna el umbral de reproducciones para hacerse premium del sistema 
	 */
	public int getUmbralReproducciones() {
		return sistema.umbral_reproducciones;
	}
	
	/**
	 * Devuelve la cuota que se debe pagar para hacerse premium
	 * @return precio_premium: retorna el precio necesario para hacerse premium
	 */
	public double getPrecioPremium() {
		return sistema.precio_premium;
	}
	
	
	/**
	 * Devuelve si el usuario actual es un administrador o no
	 * @return es_administrador: si es true es administrador, false en caso de que no lo sea
	 */
	public boolean getAdministrador() {
		return sistema.es_administrador;
	}
	
	/*=================================================================================*/
	/*======================FUNCIONES GENERALES DE SETTERS=============================*/
	/*=================================================================================*/
	
	
	
	/**
	 * Esta funcion establece el umbral de reproducciones que debe superar un autor para pasar al estado de premium, y solo es aplicable a aquellos usuarios que estan registrados
	 * @param umbral: indica el umbral de reproducciones que debe superar un usuario registrado para hacerse premium sin abonar nada
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
	 * @param precio: indica el precio que hay que abonar para ascender a Premium
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
	 * @param max: indica el numerode reproducciones maximas que puede escuchar un usuario no registrado o registrado normal
	 * @return retorna un OK si se establece correctamente el numero maximo de reproducciones para un usuario o ERROR si no es asi
	 */
	public Status setMaxReproduccionesUsuarioNoPremium(int max) {
		if(max < 0) {
			return Status.ERROR;
		}
		sistema.max_reproducciones_usuarios_no_premium = max;
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
	 * @param aux: objeto de tipo usuario que va a inciar sesion
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
	 * @param aux: objeto de tipo usuario que va a iniciar sesion y es el administrador
	 */
	public void setAdministrador(boolean aux) {
		this.es_administrador = aux;
	}
	
	
	
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON LA CUENTA DE USUARIO==================*/
	/*=================================================================================*/
	
	/**
	 * Permite registrarse a un usuario creando un objeto de tipo usuario y almacenandolo en el array de usuarios totales
	 * @param nombre_usuario: cadena que indica el nombre de usuario que va a recibir el usuario al registrarse
	 * @param nombre_autor: cadena que indica el nombre de autor que va a recibir el usuario al registrarse
	 * @param fecha_nacimiento: fecha que indica el dia,mes y año de nacimiento del usuario
	 * @param contrasenia: cadena que indica la contrasenya que va a utilizar el usuario al iniciar sesion
	 * @return devuelve OK si el usuario se registro correctamente o ERROR si no lo consiguio
	 */
	public Status registrarse(String nombre_usuario,String nombre_autor,LocalDate fecha_nacimiento, String contrasenia) {

		int i=0;
				
		if(nombre_usuario == null || contrasenia == null || fecha_nacimiento == null) {
			return Status.ERROR;
		}
		if(sistema.usuarios_totales.size() > 0) {
			for(Usuario usuario: sistema.usuarios_totales) {
							
				if(usuario.getNombreUsuario().equals(nombre_usuario) == true || usuario.getNombreAutor().equals(nombre_autor) == true) {
					break;
				}
				i++;
			}
					
			if(i < sistema.usuarios_totales.size()) {
				return Status.ERROR;
			}
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
					}else if(usuario.getEstadoBloqueado() == UsuarioBloqueado.POR_REPORTE) {
						return EstadoInicioSesion.POR_REPORTE;
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
	 * @throws IOException puede lanzarse al llamar a la funcion guardarDatosGenerales();
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
					 for(Iterator<Usuario> iteratorSeguido = usuario.getSeguidos().iterator(); iteratorSeguido.hasNext();) {
						 Usuario seguidos = iteratorSeguido.next();
						 iteratorSeguido.remove();
						 if(seguidos.eliminarSeguidor(usuario)==true) {
							 usuario.enviarNotificacion(seguidos, "El autor " + usuario.getNombreAutor() + " le ha dejado de seguir");
						 }
					 }
					 					 
					//Informamos a los seguidores que el usuario se va a eliminar
					 for(Iterator<Usuario> iteratorSeguidores = usuario.getSeguidores().iterator(); iteratorSeguidores.hasNext();) {
						Usuario seguidores = iteratorSeguidores.next();
						iteratorSeguidores.remove();
						if(seguidores.eliminarSeguido(usuario) == true){
							usuario.enviarNotificacion(seguidores, "El autor " + usuario.getNombreAutor() + " ha eliminado su cuenta");
						}
					 }
					 
					 //limpiamos los arrays propios de seguidores y seguidos
					 Sistema.sistema.getUsuarioActual().getSeguidos().clear();
					 Sistema.sistema.getUsuarioActual().getSeguidores().clear();
					 
					 
					//elimino sus listas
					 for(Iterator<Lista> iteratorLista = usuario.getListas().iterator(); iteratorLista.hasNext();) {
						 Lista listas_usuario = iteratorLista.next();
						 iteratorLista.remove();
						 sistema.eliminarLista(listas_usuario);
					 }
					 
					 
					//elimino sus albumes e informo a los usuarios que tengan los albumes en sus listas de su eliminacion
					 for(Iterator<Album> iteratorAlbum = usuario.getAlbumes().iterator(); iteratorAlbum.hasNext();) {
						 Album albumes_usuario = iteratorAlbum.next();
						 iteratorAlbum.remove();
						 sistema.eliminarAlbum(albumes_usuario);
					 }
					 
					 
					 
					 //elimino sus albumes e informo a los usuarios que tengan las canciones en sus listas de su eliminacion
					 for(Iterator<Cancion> iteratorCanciones = usuario.getCanciones().iterator(); iteratorCanciones.hasNext();) {
						 Cancion canciones_usuario = iteratorCanciones.next();
						 iteratorCanciones.remove();
						 sistema.eliminarCancion(canciones_usuario);
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
					usuario.empeorarCuenta();
					
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
	public void resetearContadoresNuevoMes(LocalDate fecha_actual) {
		
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
	public void eliminarCancionPendienteModificacionCaducada(LocalDate d) {
		
		
		//ELIMINAMOS CANCIONES QUE HAN PASADO EL TIEMPO DE MODIFICACION
		for(Iterator<Cancion> cancionesIterator = sistema.getCancionTotales().iterator(); cancionesIterator.hasNext();) {
			Cancion aux = cancionesIterator.next();
			if(aux.getEstado() == EstadoCancion.PENDIENTEMODIFICACION && d.minusDays(3).isAfter(aux.getFechaModificacion())) {
				cancionesIterator.remove();
				sistema.eliminarCancion(aux);
			}			
		}
		
	}
	
	
	/**
	 * Esta funcion que se ejecuta de manera periodica, busca una independencia del sistema donde se este corriendo este proyecto.
	 * Tras iniciarse el objeto sistema cada cancion es alterada cambiando su path al sistema operativo correspondiente. 
	 * Al ser un proyecto de eclipse las funciones son las mismas(System.getProperty()) y estas nos permiten conocer la 
	 * nueva ruta hasta el fichero que esta asignado a cada cancion. Gracias a esto conseguimos que la unica limitacion para
	 * mover el proyecto entre distintos sistemas sea solucionada con una capa de abstraccion que nos proporciona eclipse. 
	 */
	public void actualizarPathCanciones() {
		String temporal = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs" + System.getProperty("file.separator");
		String aux = null;
		 
		for(Cancion auxiliar: sistema.getCancionTotales()) {
			aux = temporal + auxiliar.getNombreFichero();
			auxiliar.setNombreMP3(aux);
		}
	}
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON LA BUSQUEDA POR CRITERIOS=============*/
	/*=================================================================================*/
	
	/**
	 * Permite realizar una busqueda en todas las canciones al introducir una cadena
	 * @param palabra: criterio para buscar por titulo
	 * @return canciones_retornar: retorna un arraylist de elementos de tipo cancion si se encuentra contenido igual
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
	 * @param palabra: criterio para buscar por album
	 * @return albumes_incluidas_explicitas: retorna un arraylist de elementos de tipo album si se encuentra contenido igual
	 * o que contiene la cadena introducida por parametro 
	 */
	public ArrayList<Album> buscadorPorAlbumes(String palabra){
		//LocalDate fecha_actual = LocalDate.now();
		ArrayList<Album> albumes_incluidas_explicitas = new ArrayList<Album>();
		int flag = 0;
				
		if(palabra == null) {
			return null;
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
	 * @param palabra: criterio para buscar por autor
	 * @return lista_autor_canciones: retorna un arraylist de elementos de tipo cancion aplicados a un autor concreto
	 * si se encuentra alguno mediante el parametro introducido
	 */
	public ArrayList<Cancion> buscadorPorAutores_DevolvemosCanciones(String palabra){
		
		ArrayList<Cancion> lista_autor_canciones = new ArrayList<Cancion>();
		LocalDate fecha_actual = LocalDate.now();
		
		if(palabra == null) {
			return null;
		}
		
		
		for(Usuario usuario: sistema.usuarios_totales) {
			
			if(usuario.getNombreAutor().equals(palabra) == true || usuario.getNombreAutor().contains(palabra) == true) {
				
				if(usuario.equals(Sistema.sistema.getUsuariosTotales().get(0)) == false) { //MIENTRAS NO SEA ROOT, porque este no tiene contenido
					
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
	 * @param palabra: criterio para buscar por autor
	 * @return albumes_filtrados: retorna un arraylista de elementos de tipo album aplicados a un autor concreto
	 * si se encuentra alguno mediante el parametro introducido
	 */
	public ArrayList<Album> buscadorPorAutores_DevolvemosAlbumes(String palabra){
		
		ArrayList<Album> albumes_filtrados = new ArrayList<Album>();
		
		if(palabra == null) {
			return null;
		}
				
		for(Usuario usuario: sistema.usuarios_totales) {
			
			if(usuario.getNombreAutor().equals(palabra) == true || usuario.getNombreAutor().contains(palabra) == true) {
				
				if(usuario.equals(Sistema.sistema.getUsuariosTotales().get(0)) == false) { //MIENTRAS NO SEA ROOT, porque este no tiene contenido	
					
					for(Album album: usuario.getAlbumes()) { 
						albumes_filtrados.add(album);}	
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
	 * @param palabra: criterio para buscar por autor
	 * @return contenido: retorna un arraylist de contenido si sencuentran canciones y/o albumes, sino devuelve null.
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
	 * @param c: cancion a modificar
	 * @param NombreMp3: la ruta del nuevo fichero a subir
	 * @param aux: nombre real del fichero que se va a subir
	 * @return retorna OK si la modificacion se llevo de manera satisfactoria, y ERROR si no fue asi
	 * @throws FileNotFoundException se da si el fichero a buscar no se ha encontrado 
	 */
	public Status modificarCancion(Cancion c,String NombreMp3, String aux) throws FileNotFoundException {
		LocalDate d = LocalDate.now();

		if(sistema.getUsuarioActual() != null && sistema.getAdministrador() == false && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
									
			if(sistema.getCancionTotales().contains(c) == true && (d.minusDays(3).isBefore(c.getFechaModificacion()) == true || d.minusDays(3).isEqual(c.getFechaModificacion()) == true)&& c.getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
								
				c.setNombreMP3(NombreMp3);
				c.setNombreFichero(aux);
				if(c.esMP3() == true){
										
					c.setDuracion(c.devolverDuracion());
					Sistema.sistema.getUsuarioActual().enviarNotificacion(Sistema.sistema.getUsuariosTotales().get(0), "El autor " + Sistema.sistema.getUsuarioActual().getNombreAutor() + " ha modificado su cancion " + c.getTitulo() + " dentro del plazo permitido");
					return Status.OK;
				}
			}
		}
		
		return Status.ERROR;
	}
	
	
	/**
	 * Permite a un usuario registrado o registrado premium crear una cancion, a la espera de que el administrador las valide y las haga visibles al publico
	 * @param titulo: nombre que va a recibir la cancion por el que luego se buscara
	 * @param nombreMP3: ruta completa donde se encuentra el fichero
	 * @param auxiliar: nombre real del fichero
	 * @return retorna la referencia a la cancion creada, que esta almacenada en el array general de canciones y en el propio del usuario
	 * @throws FileNotFoundException se da si el fichero a buscar no se encuentra
	 * @throws Mp3PlayerException se da si existe cualquier problema al crear el reproductor
	 */
	public Cancion crearCancion(String titulo,String nombreMP3, String auxiliar) throws FileNotFoundException, Mp3PlayerException{

		if(titulo == null || nombreMP3 == null || auxiliar == null) {
			return null;
		}
				
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
						
			
			Cancion c = new Cancion(titulo,sistema.usuario_actual,nombreMP3,auxiliar);


			if(c.getDuracion() == -1) { //ESTO SIGNIFICA QUE NO ES MP3 Y LA CANCION ESTARIA MAL CONSTRUIDA
				c = null;
				return null;
			}
						
			for(Cancion cancion:sistema.usuario_actual.getCanciones()) {
				if(cancion.getTitulo().equals(titulo) == true || cancion.getNombreMP3().equals(nombreMP3) == true) {
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
	 * Permite a un usuario eliminar una de sus propias canciones, y esto implica eliminarla de todos los
	 * lugares donde la misma se encuentre(albumes y listas)
	 * @param cancion_eliminar: objeto de tipo cancion que vamos a eliminar totalmente del sistema
	 * @return retorna ERROR si la cancion no existe o no se puede eliminar del sistema u OK si se elimina correctamente
	 */
	public Status eliminarCancion(Cancion cancion_eliminar) {		
		
		if(cancion_eliminar == null) {
			return Status.ERROR;
		}
		
		String nombre = cancion_eliminar.getTitulo();
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
					
			if(cancion_eliminar.getAutor().getNombreAutor().equals(sistema.getUsuarioActual().getNombreAutor()) && cancion_eliminar.getEstado() != EstadoCancion.PLAGIO) {
				
				//PRIMERO LA ELIMINAMOS DEL CONTENIDO DE LAS LISTAS DE TODOS LOS USUARIOS QUE LA CONTENGAN
				for(Iterator<Usuario> iteratorUsuarios = sistema.getUsuariosTotales().iterator(); iteratorUsuarios.hasNext();) {
					 Usuario usuarios_totales = iteratorUsuarios.next();
										 
					 if ( usuarios_totales.getListas() != null) {
						for(Iterator<Lista> iteratorListas = usuarios_totales.getListas().iterator(); iteratorListas.hasNext();) {
							Lista lista = iteratorListas.next();
							if(lista.eliminarContenido(cancion_eliminar) == Status.OK) {
								if(usuarios_totales.equals(Sistema.sistema.getUsuarioActual()) == false) {
									sistema.getUsuarioActual().enviarNotificacion(usuarios_totales, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha eliminado la cancion " + nombre);
								}
							}
						}
					}
				}
				
				
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
	 * @param anyo: anyo en el que se creo el album
	 * @param titulo: titulo que va a recibir el album
	 * @return retorna la referencia al objeto album si se crea correctamente, sino devuelve null 
	 * @throws Mp3PlayerException se da si existe algun problema con el reproductor
	 * @throws FileNotFoundException se da si el fichero referenciado no se encuentra
	 */
	public Album crearAlbum(int anyo,String titulo) throws FileNotFoundException, Mp3PlayerException {
		if(titulo == null) {
			return null;
		}
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {			
						
			Album a = new Album(anyo,titulo,sistema.usuario_actual);
			for(Album album:sistema.usuario_actual.getAlbumes()) {
				if(album.getTitulo().equals(titulo)== true) {
					return null;
				}
			}
			
			
			sistema.usuario_actual.anyadirAAlbumesPersonales(a);
			sistema.albumes_totales.add(a);

			for(Usuario u_t:sistema.getUsuariosTotales()) {
				if(u_t.getSeguidos().contains(sistema.getUsuarioActual()) == true) {
					sistema.getUsuarioActual().enviarNotificacion(u_t, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha subido el album "+ titulo);
				}
			}
			return a;
			
		}else {
			return null;
		}
	
	}
	
	/**
	 * Permite a un usuario eliminar uno de sus propios albumes eliminandolo del array general de albumes y del propio
	 * @param album_eliminar: album que va a ser eliminado del sistema y de todas las listas de todos los usuarios
	 * @return retorna ERROR si la cancion no se elimina del album, OK si se consigue
	 */
	public Status eliminarAlbum(Album album_eliminar) {
		if(album_eliminar == null) {
			return Status.ERROR;
		}
		
		String nombre = album_eliminar.getTitulo();
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			if(album_eliminar.getAutor().getNombreAutor().equals(sistema.getUsuarioActual().getNombreAutor())) {
				
				
				//ELIMINAMOS DE LAS LISTAS EN LAS QUE SE ENCUENTRE
				for(Iterator<Usuario> iteratorUsuarios = sistema.getUsuariosTotales().iterator(); iteratorUsuarios.hasNext();) {
					
					
					Usuario usuarios_totales = iteratorUsuarios.next();
					for(Iterator<Lista> iteratorListas = usuarios_totales.getListas().iterator(); iteratorListas.hasNext();) {
						

						Lista lista = iteratorListas.next();
						if(lista.eliminarContenido(album_eliminar) == Status.OK) {
							

							
							if(usuarios_totales.equals(Sistema.sistema.getUsuarioActual()) == false) {
								

								sistema.getUsuarioActual().enviarNotificacion(usuarios_totales, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha eliminado el album " + nombre);
							}
						}
					}
					
				}
				
		
				//ELIMINAMOS DEL PROPIO SISTEMA
				sistema.getAlbumTotales().remove(album_eliminar);
				
				//ELIMINAMOS DE ALBUMES DEL AUTOR
				sistema.getUsuarioActual().eliminarDeAlbumesPersonales(album_eliminar);

				
				
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
	 * @param a: album al que vamos a anyadirle la cancion pasadapor argumento
	 * @param c: cancion que vamos a anyadir al album
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
								
								if(x < canciones_en_album.size() - 1) { //si x se a quedado antes del total de canciones en el album es por
									return Status.ERROR;				//que esta ya se encuentra dentro del album
								}
								
								if(album.anyadirContenido(c) == Status.OK) {
									
									for(Usuario u_t:sistema.getUsuariosTotales()) {
										if(u_t.getSeguidos().contains(c.getAutor()) == true) {
											sistema.getUsuarioActual().enviarNotificacion(u_t, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha añadido una cancion al album " + a.getTitulo());
										}
									}
									return Status.OK;
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
	 * @param a: album del que vamos a retirar la cancion pasada por argumento
	 * @param c: cancion que queremos retir del album
	 * @return retorna OK si se elimina la cancion del album indicado correctamente, de lo contrario devolvera ERROR
	 */
	public Status quitarCancionDeAlbum(Album a, Cancion c) {
		
		if(a == null || c == null) {
			return null;
		}
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && sistema.es_administrador == false) {

			for(Cancion cancion:sistema.getUsuarioActual().getCanciones()) {
				
				if(cancion.getTitulo().equals(c.getTitulo()) == true && cancion.getAutor().getId() == sistema.getUsuarioActual().getId() && (c.getEstado() == EstadoCancion.VALIDA || c.getEstado() == EstadoCancion.EXPLICITA)) {

					for(Album album:sistema.getUsuarioActual().getAlbumes()) {
						
						if(album.getTitulo().equals(a.getTitulo()) == true && album.getAutor().getId() == sistema.getUsuarioActual().getId()) {
					
							ArrayList<Cancion> canciones_en_album = a.getContenido();
							
							for(Cancion canciones_album:canciones_en_album) {
								if(canciones_album.getTitulo().equals(c.getTitulo()) == true && canciones_album.getNombreMP3().equals(c.getNombreMP3()) == true) {
									if(album.eliminarContenido(c) == Status.OK) {
										for(Usuario u_t:sistema.getUsuariosTotales()) {
											if(u_t.getSeguidos().contains(c.getAutor()) == true) {
												sistema.getUsuarioActual().enviarNotificacion(u_t, "El autor " + sistema.getUsuarioActual().getNombreAutor() + " ha retirado una cancion del album " + a.getTitulo());
											}
										}
										return Status.OK;
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
	 * @param titulo: cadena que hace referencia al nombre del objeto de tipo lista
	 * @return retorna la referencia al objeto lista si se creo correctamente, y de lo contrario devolvera null
	 * @throws Mp3PlayerException se puede dar si existe algun problema al crearel reproductor
	 * @throws FileNotFoundException se da si no se encuentra el fichero especifico
	 */
	public Lista crearLista(String titulo) throws FileNotFoundException, Mp3PlayerException {
		
		boolean lista_repetida_en_usuario = false; 
		if(titulo == null || titulo.equals("") == true) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			Lista l = new Lista(titulo,sistema.usuario_actual);
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
	 * @param lista_eliminar: objeto de tipo lista que pretendemos eliminar del sistema
	 * @return retorna OK si la lista fue eliminada correctamente, ERROR si no se elimino del sistema
	 */
	public Status eliminarLista(Lista lista_eliminar) {
		
		if(lista_eliminar == null) {
			return null;
		}
				
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO) {
			
			if(sistema.getUsuarioActual().getListas().contains(lista_eliminar) == true) {
				//LISTA PROPIA
				if(sistema.getUsuarioActual().eliminarDeListasPersonales(lista_eliminar) == true) {
					return Status.OK;

				}
				
			}
		}
		
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion permite anyadir un contenido(LISTA,CANCION o ALBUM) a una lista si no esta incluido ya
	 * @param l: lista a la que le vamos a anyadir un contenido
	 * @param c: contenido a anyadir a la lista especificada
	 * @return retorna OK si se anyadio correctamente, ERROR si no fue asi
	 */
	public Status anyadirALista(Lista l, Contenido c) {
		LocalDate d = LocalDate.now();
		
		if(l == null || c == null) {
			return null;
		}
		
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && sistema.getAdministrador() == false) {
				
			Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), d);

			
				if(sistema.getUsuarioActual().getListas().contains(l) == true) {
					if(c instanceof Cancion && ((Cancion) c).getEstado() == EstadoCancion.VALIDA) {
					
						if(l.anyadirContenido(c) == Status.OK) {
							return Status.OK;
						}else {
							return Status.ERROR;
						}
						
					}else if( c instanceof Cancion && ((Cancion)c).getEstado() == EstadoCancion.EXPLICITA) {
						if(intervalo.getYears() >= 18) {
							
							if(l.anyadirContenido(c) == Status.OK) {
								return Status.OK;
							}else {
								return Status.ERROR;
							}
						}
					}else if(c instanceof Album || c instanceof Lista){
						if(l.anyadirContenido(c) == Status.OK) {
							return Status.OK;
						}else {
							return Status.ERROR;
						}
					}
				}
		}
		return Status.ERROR;
	}
	
	/**
	 * Esta funcion permite eliminar un contenido de la lista especificada
	 * @param l: lista de la que vamos a retirar un contenido
	 * @param c: contenido que va a ser retirado de la lista
	 * @return retorna OK si se elimino correctamente, ERROR si no fue asi
	 */
	public Status quitarDeLista(Lista l,Contenido c) {
		if(l == null || c == null) {
			return null;
		}
		if(sistema.usuario_actual != null && sistema.getUsuarioActual().getEstadoBloqueado() == UsuarioBloqueado.NOBLOQUEADO && sistema.getAdministrador() == false) {
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
			FileOutputStream fileOut = new FileOutputStream("datos.ser");
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
			FileInputStream in = new FileInputStream("datos.ser");
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
	

	
	
	
	
	/*=================================================================================*/
	/*================FUNCIONES RELACIONADAS CON ADMINISTRADOR=========================*/
	/*=================================================================================*/
	
	/**
	 * Funcion que retorna las canciones que estan pendientes de ser validadas o modificadas, y si son del tipo
	 * de las segundas que sigan dentro del plazo de modificacion
	 * @return un arraylist de canciones existan o no
	 */
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
	
	/**
	 * Funcion que decide si una cancion es validada(se incluye explicita), pasa a estar 
	 * pendiente de modificacion o es eliminada del sistema
	 * @param c: cancion a valorar
	 * @param estado: estado que va a adquirir la cancion
	 */
	public void gestionarCancionesPendientesValidacion_Modificacion(Cancion c, EstadoCancion estado) {
		
		if(c == null || estado == null) {
			return;
		}
		
		if(sistema.getUsuarioActual() != null && sistema.es_administrador == true) {
			if(estado == EstadoCancion.VALIDA || estado == EstadoCancion.EXPLICITA) {
				c.setEstado(estado);
				c.setEstadoAnterior(estado);
				if(estado == EstadoCancion.VALIDA) {
					sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " ha sido validada correctamente");
				}else if(estado == EstadoCancion.EXPLICITA) {
					sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " ha sido validada correctamente considerada explicita");
				}
				
				for(Usuario u_t:sistema.getUsuariosTotales()) {
					if(u_t.getSeguidos().contains(c.getAutor()) == true) {
						sistema.getUsuarioActual().enviarNotificacion(u_t, "El autor " + c.getAutor().getNombreAutor() + " ha subido la cancion " + c.getTitulo());
					}
				}
			
			}else if(estado == EstadoCancion.PENDIENTEMODIFICACION) {
				c.setEstado(estado);
				sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " no ha sido validada, tiene 3 dias para modificarla y la validaremos de nuevo");
			}else if(estado == EstadoCancion.ELIMINADA) {
				sistema.getUsuarioActual().enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " no ha sido validada y procederemos a eliminarla");
				c.getAutor().getCanciones().remove(c);
				sistema.getCancionTotales().remove(c);
			}
		}
	}
	
	
	/*=================================================================================*/
	/*=================FUNCIONES RELACIONADAS CON DENUNCIAS============================*/
	/*=================================================================================*/
	
	/**
	 * Funcion que puede realizar solo los usuarios para indicar el posible palgio en una cancion
	 * Tras realizar el reporte, el duenio de la cancion es bloqueado de manera indefinida hasta que
	 * el administrador lo acepte o lo deniegue, y la cancion pasada al estado de plagio
	 * @param c: cancion que ha sido reportada
	 * @return OK si el reporte es satisfactorio, ERROR en caso contrario
	 */
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
			c.getAutor().bloqueoCuentaPorReporte();
			sistema.getUsuariosTotales().get(0).enviarNotificacion(c.getAutor(), "Su cancion " + c.getTitulo() + " ha sido bloqueada por un reporte y usted de manera temporal. Comprobaremos esta informacion con la mayor brevedad porsible.");
			sistema.getUsuarioActual().enviarNotificacion(sistema.getUsuariosTotales().get(0), "El usuario " + sistema.getUsuarioActual().getNombreUsuario() + " ha reportado la cancion " + c.getTitulo());
			
			sistema.getReportesTotales().add(r);
			
			return Status.OK;
		}
		
		return Status.ERROR;
		
	}
	
	/**
	 * Funcion que elimina un determinado reporte del array general del sistema de reportes
	 * @param r: reporte a eliminar del sistema
	 */
	public void eliminar_reporte(Reporte r) {
		if(sistema.getUsuarioActual() != null && sistema.es_administrador == true) {
			sistema.getReportesTotales().remove(r);
		}
	}
	
	/**
	 * Funcion especifica del administrador en el que el valora si el reporte es aceptado
	 * o denegado. Cada decision implica consecuencias diferentes.
	 * Si es aceptado, el dueño de la cancion es bloqueado de manera indefinida, y la cancion considerada de plagio
	 * Si es denegado, la persona que realizo el reporte es bloqueado por 30 dias, y la cancion vuelve
	 * a sus estado anterior
	 * @param r: reporte a ser aceptado, o denegado
	 * @param aceptada: true si es aceptado, false si no lo es
	 */
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
				
				r.getCancionReportada().getAutor().setEstadoBloqueado(UsuarioBloqueado.NOBLOQUEADO);
				
				//INFORMAR DEL BLOQUEO AL USUARIO Y AL OTRO DEL DESBLOQUEO
				sistema.getUsuarioActual().enviarNotificacion(r.getUsuarioReportador(), "Ha sido bloqueado temporalmente por el reporte de la cancion " + r.getCancionReportada().getTitulo() + " que no ha sido aceptado.");
				sistema.getUsuarioActual().enviarNotificacion(r.getCancionReportada().getAutor(), "El usuario denunciante del reporte ha sido bloqueado durante 30 dias.");
			}
			
			//ELIMINAR REPORTE
			sistema.getReportesTotales().remove(r);
		}
	}
	
	/**
	 * Funcion especifica del administrador que le permite alterar los criterios de la aplicacion(precio,umbral, y reproducciones
	 * maximas).
	 * @param u_r: umbral de reproducciones para pasar a ser premium
	 * @param p_p: precio para abonar la tarifa y ser premium
	 * @param m_r: maximas reproducciones para los usuarios
	 * @return OK si todo se sobreescribe correctamente, ERROR en otro caso
	 */
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



