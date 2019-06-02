package modelo.sistema;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

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
		
		s.cerrarSesion();
		
	}
}
