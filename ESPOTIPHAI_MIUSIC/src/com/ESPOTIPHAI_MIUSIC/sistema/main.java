package com.ESPOTIPHAI_MIUSIC.sistema;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Comentario;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Contenido;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Lista;

import pads.musicPlayer.exceptions.Mp3PlayerException;

public class main {

public static void main(String[] args) throws Mp3PlayerException, InterruptedException, IOException, NoHayElementosExcepcion, ExcesoReproduccionesExcepcion {
		
		/*=================INICIO PRUEBA=====================*/
		Sistema s = Sistema.getSistema();
		
		/*------------Usuario 1-----------*/
		System.out.println("\n\t\tUsuario 1  PELAYO <==> BISHOP");
		s.registrarse("pelayo", "bishop", LocalDate.of(1997, 12, 27), "mare_nostrum");
		
		/*------------Usuario 2-----------*/
		System.out.println("\n\t\tUsuario 2 ROBERTO <==> MANOLO");
		s.registrarse("pelayo", "bishop", LocalDate.of(1997, 12, 27), "mare_mia"); //Se intenta registrar el usuario 2 con los datos de un usuario ya existente(nos basamos en el nombre de usuario y de autor)
		
		s.registrarse("roberto","manolo",LocalDate.of(2002,12,26),"mar_mediterraneo");//Por tanto nos registramos con unos datos que si son validos
		
		s.crearCancion(new Date(), "astronauts", "Parker_-_Astronauts.mp3", false); //Intenta crear una cancion sin haberse iniciado sesion
		
		/*------------Usuario 1-----------*/
		System.out.println("\n\t\tUsuario 1  PELAYO <==> BISHOP");
		
		s.iniciarSesion("pelayo", "mare_nostru"); //Intenta iniciar sesion el usuario 1 con los datos incorrectos
		
		s.iniciarSesion("pelayo", "mare_nostrum"); //Inicio de sesion correcto
		
		System.out.println("\nUsuario actual: " + s.getUsuarioActual().getNombreUsuario() + " Nombre autor: " + s.getUsuarioActual().getNombreAutor() + " Admin: " + s.getAdministrador());
		
		Cancion c1 = s.crearCancion(new Date(), "astronauts", "Parker_-_Astronauts.mp3", false);
		
		Cancion c2 = s.crearCancion(new Date(), "the edge","Rising_Bones_-_The_Edge.mp3",false);
		
		Cancion c3 = s.crearCancion(new Date(), "cannot come back", "Above_Envy_-_Cannot_Come_Back.mp3", true);
		
		Album a1 = s.crearAlbum(new Date(), "mark I");
		
		Lista l1 = s.crearLista(new Date(), "sport");
		
		s.follow("manolo");
		
		s.cerrarSesion();
		
		/*------------Usuario 2-----------*/
		System.out.println("\n\t\tUsuario 2 ROBERTO <==> MANOLO");
		
		s.iniciarSesion("roberto", "mar_mediterraneo");
		
		Cancion c4 = s.crearCancion(new Date(), "astronauts", "Parker_-_Astronauts.mp3", false);//Intentamos crear canciones ya creadas por otro autor y el sistema nos informa de ello
		
		Cancion c5 = s.crearCancion(new Date(), "the edge","Rising_Bones_-_The_Edge.mp3",false);
		
		Cancion c6 = s.crearCancion(new Date(), "mourning day", "SadMe_-_Mourning_Day.mp3", true); //La cancion no se ha podido crear ya que esta categorizada como explicita y el usuario es menor de edad
		
		Cancion c7 = s.crearCancion(new Date(), "all i need is you", "SemMueL_-_All_I_Need_Is_You.mp3", false);
		
		Cancion c8 = s.crearCancion(new Date(), "hive", "hive.mp3", false);
		
		Album a2 = s.crearAlbum(new Date(), "mark I");
		
		Album a3 = s.crearAlbum(new Date(), "mark II");
		
		Lista l2 = s.crearLista(new Date(), "sport");
		
		s.verNotificacionesPropias(); //Vemos las notificaciones que nos ha enviado el sistema por el momento
		
		s.cerrarSesion();
		
		/*------------Administrador-----------*/
		System.out.println("\n\t\tAdministrador");
		
		s.iniciarSesion("root1967", "ADMINISTRADOR");
		
		s.validarCancion(220);
		
		s.cerrarSesion();
		
		Sistema s1 = Sistema.getSistema();
		
		System.out.println(s1.getPrecioPremium());
		
		/*------------Usuario 1-----------*/
		/*System.out.println("\n\t\tUsuario 1  PELAYO <==> BISHOP");
		
		s.iniciarSesion("pelayo", "mare_nostrum");
		
		s.verNotificacionesPropias();
		
		System.out.println("\n\nREALIMOS UNA BUSQUEDA CON DATOS ERRONEOS PARA QUE FALLE");
		s.buscadorPorTitulos(null);//Realizamos una busqueda provocando un fallo pra conocer 
		
		
		System.out.println("\n\nREALIMOS UNA BUSQUEDA POR TITULO");
		ArrayList<Cancion> canciones_encontradas = s.buscadorPorTitulos("astronauts"); //Realizamos una busqueda por Titulo y con una cadena
		s.reproducirCancion(canciones_encontradas.get(0)); //Logicamente las reproducciones no aumentan si son tus propias canciones
		Thread.sleep(10000); //Dejamos que reproduzca 10 segundos y la paramos para seguir
		s.pararReproductor();
		
		
		System.out.println("\n\nAÑADIMOS LA CANCION VALIDADA DE LAS DOS Y HACEMOS UNOS COMENTARIOS AL ALBUM Y A LA CANCION Y REPRODUCIMOS");
		s.aniadirComentarioCancion(new Comentario(new Date(),"Esta es la primera cancion del album"), c1);//añadimos un comentario a nuestra cancion
		s.aniadirCancionAAlbum(a1, c1); //Añadimos en nuestro album nuestras canciones y reproducimos
		s.aniadirComentarioAlbum(new Comentario(new Date(),"Este album esta dedicado a mi familia ya todos aquellos que me apollaron"), a1); //Añadimos un comentario al album
		s.reproducirAlbum(a1); //Probamos a reproducir el album y trasn 15 segundos paramos la reproduccion
		Thread.sleep(15000);
		s.pararReproductor();
		
		
		System.out.println("\n\nREALIZAMOS BUSQUEDA POR AUTOR Y REPRODUCIMOS");
		ArrayList<Contenido> contenido_autor = s.buscadorPorAutores("manolo"); //Aumentan las reproduciones en una unidad porque es de otro autor
		s.reproducirAutor(contenido_autor);
		
		
		System.out.println("\n\nCOMO HEMOS AGOTADO EL NUMERO DE REPRODUCCIONES NOS HACEMOS PREMIUM PAGANDO");
		s.mejorarCuentaPago("1111222233334444");
		
		System.out.println("\n\nTRATAMOS DE MODIFICAR LAS CANCION QUE NO FUE VALIDADA EN SUS MOMENTO");
		s.modificarCancion(c2, "np.mp3");//Modificamos la cancion que no se nos valido para que cuando simulemos el avance de fecha comprobemos si se ha modificado o no
		
		System.out.println("\n\nAÑADIMOS UNAS CANCIONES A LA LISTA 1 Y REPRODUCIMOS PARA PROBAR QUE TENEMOS REPRODUCCIONES ILIMITADAS");
		s.aniadirALista(l1, contenido_autor.get(0));
		s.reproducirLista(l1);
	
		
		s.cerrarSesion();
		
		/*------------Administrador-----------*/
		/*System.out.println("\n\n\t\tAdministrador");
		LocalDate d = LocalDate.now();
		
		s.iniciarSesion("root1967", "ADMINISTRADOR");
		
		System.out.println("\n\nCAMBIAMOS LA FECHA DE LA MODIFICACION DE LA CANCION PARA PODER PASAR LA VALIDACION DE NUEVO");
		c2.setFechaModificacion(d.plusDays(3));
		
		s.validarCancion(250);
		
		s.reproducirAutor(s.buscadorPorAutores("bishop"));
		
		/*Avanzamos 12 dias mas para empeorar_cuenta*/
		
		
		/*------------Usuario 2-----------*/
		/*System.out.println("\n\n\t\tUsuario 2 ROBERTO <==> MANOLO");
		
		s.iniciarSesion("roberto", "mar_mediterraneo");
		
		s.verNotificacionesPropias();
		
		System.out.println("\n\n");
		ArrayList<Album> album_filtrado = s.buscadorPorAlbumes("mark I");
		
		s.aniadirComentarioAlbum(new Comentario(new Date(),"Con muchas ganas de escucharlo"), album_filtrado.get(0));
		
		s.reproducirAlbum(album_filtrado.get(0)); //Aumenta la reproduccion para el usuario 1 pues es el propietario del album y no el usuario 2 que lo esta escuchando
		
		/*reportar plagio*/
		
		/*------------Administrador-----------*/
		/*System.out.println("\n\n\t\tAdministrador");
		
		/*Vemos notificaciones y atendemos el mensaje de plagio*/
		
		/*Se bloquea a usuario 2*/
		//s.comprobarPlagio(null);
		
		
	}
}
