package com.ESPOTIPHAI_MIUSIC.sistema;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Album;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Cancion;
import com.ESPOTIPHAI_MIUSIC.sistema.contenido.Lista;
import pads.musicPlayer.exceptions.Mp3PlayerException;



public class Main {

public static void main(String[] args) throws Mp3PlayerException, InterruptedException, IOException{
		
	 	String entradaTeclado = "";
	 	Scanner entradaEscaner = new Scanner (System.in);
	 	LocalDate d = LocalDate.now();
	 	
		/*=================INICIO PRUEBA=====================*/
	 	
		System.out.println("\nNADA MAS CREAR EL SISTEMA CREAMOS AL ADMINISTRADOR <==> root1967");
		Sistema s = Sistema.getSistema();
		
		/*------------Usuario 1-----------*/
		
		System.out.println("\n\n===================================================================================================================");
		System.out.println("\n========================================== USUARIO 1 PELAYO <==> BISHOP ===========================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\nCREAMOS A UN USUARIO 1 = pelayo <==> bishop");
		s.registrarse("pelayo", "bishop", LocalDate.of(1997, 12, 27), "mare_nostrum");
		
		/*------------Usuario 2-----------*/
		System.out.println("\n\n===================================================================================================================");
		System.out.println("\n========================================== USUARIO 2 ROBERTO <==> MANOLO ===========================================");
		System.out.println("\n===================================================================================================================");

		
		System.out.println("\n\tCREAMOS A UN USUARIO 2 CON LOS MISMOS DATOS DE USUARIO 1");
		s.registrarse("pelayo", "bishop", LocalDate.of(1997, 12, 27), "mare_mia"); //Se intenta registrar el usuario 2 con los datos de un usuario ya existente(nos basamos en el nombre de usuario y de autor)
		
		System.out.println("\n\n\tCREAMOS A UN USUARIO 2 = roberto <==> manolo");
		s.registrarse("roberto","manolo",LocalDate.of(2002,12,26),"mar_mediterraneo");//Por tanto nos registramos con unos datos que si son validos
				
		/*------------Usuario 1-----------*/
		System.out.println("\n\n===================================================================================================================");
		System.out.println("\n========================================== USUARIO 1 PELAYO <==> BISHOP ===========================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\n\tINICIAMOS SESION CON USUARIO 1");
		s.iniciarSesion("pelayo", "mare_nostrum"); //Inicio de sesion correcto
		
		System.out.println("\nUsuario actual: " + s.getUsuarioActual().getNombreUsuario() + " Nombre autor: " + s.getUsuarioActual().getNombreAutor() + " Admin: " + s.getAdministrador());
		
		System.out.println("\n\n\tCREAMOS 3 CANCIONES, 1 ALBUM Y 1 LISTA");
		Cancion c1 = s.crearCancion(new Date(), "astronauts", "Parker_-_Astronauts.mp3", false);
		
		Cancion c2 = s.crearCancion(new Date(), "the edge","Rising_Bones_-_The_Edge.mp3",false);
		
		Cancion c3 = s.crearCancion(new Date(), "cannot come back", "Above_Envy_-_Cannot_Come_Back.mp3", true);
		
		Album a1 = s.crearAlbum(new Date(), "mark I");
		
		Lista l1 = s.crearLista(new Date(), "sport");
		
		System.out.println("\n\n\tPROBAMOS A SEGUIR AL OTRO USUARIO DEL SISTEMA => manolo");
		s.follow("manolo");
		
		System.out.println("\n\n\tCERRAMOS SESION DE USUARIO 1");
		s.cerrarSesion();
		
		/*------------Usuario 2-----------*/
		System.out.println("\n\n===================================================================================================================");
		System.out.println("\n========================================== USUARIO 2 ROBERTO <==> MANOLO ===========================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\n\tINICIAMOS SESION CON USUARIO 2");
		s.iniciarSesion("roberto", "mar_mediterraneo");
		
		System.out.println("\n\n\tCREAMOS 3 CANCIONES, 2 ALBUM Y 1 LISTA");
		Cancion c4 = s.crearCancion(new Date(), "astronauts", "Parker_-_Astronauts.mp3", false);//Intentamos crear canciones ya creadas por otro autor y el sistema nos informa de ello
				
		Cancion c6 = s.crearCancion(new Date(), "mourning day", "SadMe_-_Mourning_Day.mp3", true); //La cancion no se ha podido crear ya que esta categorizada como explicita y el usuario es menor de edad
		
		Cancion c7 = s.crearCancion(new Date(), "all i need is you", "SemMueL_-_All_I_Need_Is_You.mp3", false);
		
		Cancion c8 = s.crearCancion(new Date(), "hive", "hive.mp3", false);
		
		Album a2 = s.crearAlbum(new Date(), "mark I");
		
		Album a3 = s.crearAlbum(new Date(), "mark II");
		
		Lista l2 = s.crearLista(new Date(), "sport");
		
		System.out.println("\n\n\tVEMOS LAS NOTIFICACIONES PROPIAS");
		s.verNotificacionesPropias(); //Vemos las notificaciones que nos ha enviado el sistema por el momento
		
		System.out.println("\n\n\tCERRAMOS SESION");
		s.cerrarSesion();
		
		/*------------Administrador-----------*/
		System.out.println("\n\n===================================================================================================================");
		System.out.println("\n================================================== ADMINISTRADOR ==================================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\n\tINICIAMOS SESION CON ADMINISTRADOR");
		s.iniciarSesion("root1967", "ADMINISTRADOR");
		
		System.out.println("\n\n\tVALIDAMOS LAS CANCIONES");
		s.validarCancion(220);
		
		System.out.println("\n\n\tCERRAMOS SESION");
		s.cerrarSesion();
		
		System.out.println("Introduzca una letra para continuar con la ejecucion del main USUARIO 1 que va reproducir y a buscar:>");
		entradaTeclado = entradaEscaner.nextLine (); 
		
		/*------------Usuario 1-----------*/
		System.out.println("\n\n===================================================================================================================");
		System.out.println("\n========================================== USUARIO 1 PELAYO <==> BISHOP ===========================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\n\tINICIAMOS SESION CON USUARIO 1");
		s.iniciarSesion("pelayo", "mare_nostrum");
		
		System.out.println("\n\n\tVEMOS LAS NOTIFICACIONES PROPIAS");
		s.verNotificacionesPropias();
		
		System.out.println("\n\n\tREALIZAMOS UNA BUSQUEDA CON DATOS ERRONEOS PARA QUE FALLE ==> introducimos (null) ");
		s.buscadorPorTitulos(null);//Realizamos una busqueda provocando un fallo pra conocer 
		
		
		System.out.println("\n\n\tREALIZAMOS UNA BUSQUEDA POR TITULO ==> introducimos (cannot come back)");
		ArrayList<Cancion> canciones_encontradas = s.buscadorPorTitulos("cannot come back"); //Realizamos una busqueda por Titulo y con una cadena
		
		
		/*System.out.println("\n\n\tREPRODUCIMOS LA CANCION BUSCADA ANTERIORMENTE Y TRAS 10 SEGUNDOS LA PARAMOS");
		s.reproducirCancion(canciones_encontradas.get(0)); //Logicamente las reproducciones no aumentan si son tus propias canciones
		Thread.sleep(10000); //Dejamos que reproduzca 10 segundos y la paramos para seguir
		s.pararReproductor();
		
		System.out.println("\n\n\tA�ADIMOS LA CANCION VALIDADA Y A�ADIMOS UNOS COMENTARIOS AL ALBUM Y A LA CANCION Y REPRODUCIMOS DURANTE 15 SEGUNDOS Y PARAMOS");
		s.anyadirComentarioCancion(new Comentario(new Date(),"Esta es la primera cancion del album",s.getUsuarioActual()), c1);//añadimos un comentario a nuestra cancion
		s.anyadirCancionAAlbum(a1, c1); //Añadimos en nuestro album nuestras canciones y reproducimos
		s.anyadirComentarioAlbum(new Comentario(new Date(),"Este album esta dedicado a mi familia ya todos aquellos que me apollaron",s.getUsuarioActual()), a1); //Añadimos un comentario al album
		s.reproducirAlbum(a1); //Probamos a reproducir el album y trasn 15 segundos paramos la reproduccion
		Thread.sleep(15000);
		s.pararReproductor();
				
		System.out.println("\n\n\tHACEMOS PREMIUM AL USUARIO 1 INTRODUCIENDO SU TARJETA SIN ESPACIOS");
		s.mejorarCuentaPago("1111222233334444");
		
		System.out.println("\n\n\tTRATAMOS DE MODIFICAR LAS CANCION QUE NO FUE VALIDADA EN SUS MOMENTO");
		s.modificarCancion(c2, "np.mp3");//Modificamos la cancion que no se nos valido para que cuando simulemos el avance de fecha comprobemos si se ha modificado o no
		
		System.out.println("\n\n\tCERRAMOS SESION");
		s.cerrarSesion();
		
		System.out.println("Introduzca una letra para continuar con la ejecucion del main USUARIO 2 que va reproducir y a buscar:>");
		entradaTeclado = entradaEscaner.nextLine (); 
		
		/*--------------Usuario 2-------------*/
		/*System.out.println("\n\n===================================================================================================================");
		System.out.println("\n========================================== USUARIO 2 ROBERTO <==> MANOLO ===========================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\n\tINICIAMOS SESION CON USUARIO 2");
		s.iniciarSesion("roberto", "mar_mediterraneo");
		
		System.out.println("\n\n\tREALIZAMOS UNA BUSQUEDA POR AUTOR ==> bishop");
		ArrayList<Contenido> contenido_filtrado = s.buscadorPorAutores("bishop");
		
		System.out.println("\n\n\tPROBAMOS A REPRODUCIR EL CONTENIDO DEL AUTOR BUSCADO Y TRAS 15 SEGUNDOS LO PARAMOS");
		s.reproducirAutor(contenido_filtrado); //NOS SALE UNA CANCION 
		Thread.sleep(15000);
		s.pararReproductor();
		System.out.println("\n\tEN ESTA PODEMOS REPRODUCIR UNA CANCION Y UN ALBUM, LA OTRA CANCION ES EXPLICITA Y EL USUARIO ES MENOR DE EDAD");
		
		
		System.out.println("\n\n\tCERRAMOS SESION");
		s.cerrarSesion();
		
		System.out.println("Introduzca una letra para continuar con la ejecucion del ADMINISTRADOR que va reproducir y a buscar:>");
		entradaTeclado = entradaEscaner.nextLine (); 
		
		/*------------Administrador-----------*/
		/*System.out.println("\n\n===================================================================================================================");
		System.out.println("\n================================================== ADMINISTRADOR ==================================================");
		System.out.println("\n===================================================================================================================");
		
		System.out.println("\n\tINICIAMOS SESION CON ADMINISTRADOR");
		s.iniciarSesion("root1967", "ADMINISTRADOR");
		
		System.out.println("\n\n\tCAMBIAMOS LA FECHA DE LA MODIFICACION DE LA CANCION PARA PODER PASAR LA VALIDACION DE NUEVO");
		c2.setFechaModificacion(d.plusDays(3));
		System.out.println("\n\tEL ADMINISTRADOR SIN EMBARGO PRUEBA A REPRODUCIR COMO EL USUARIO 2 Y LE APRECE TODO DEL USUARIO 1");

		s.validarCancion(250);
		
		System.out.println("\n\nAHORA VEMOS COMO PARA EL AUTOR BISHOP SE REPRODUCE TAMBIEN LA CANCION VALIDADA PREVIAMENTE");
		s.reproducirAutor(s.buscadorPorAutores("bishop"));
		Thread.sleep(20000);
		s.pararReproductor();
		
		/*Avanzamos 12 dias mas para empeorar_cuenta*/
		
	}
}
