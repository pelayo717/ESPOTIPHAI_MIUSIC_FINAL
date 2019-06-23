package modelo.sistema;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Lista;
import modelo.usuario.Usuario;
import pads.musicPlayer.exceptions.Mp3PlayerException;

public class TestSistema {

	Usuario z;
	Usuario d;
	Album album;
	Cancion cancion;
	Lista l;
	Sistema a;
	boolean explicita;
	boolean prueba;
	String nombre;
	String path;
	
	@BeforeEach
	public void before() throws Mp3PlayerException, IOException {
		a = Sistema.getSistema();
		z = new Usuario("alex", "alex", LocalDate.of(1967, 12, 26), "alex");
		d = new Usuario("pedro","pedro",LocalDate.of(1967, 12, 26),"pedro");
		nombre = "hive.mp3";
		path = System.getProperty("user.dir") + System.getProperty("file.separator") + "songs_junit" + System.getProperty("file.separator") + nombre;
		cancion = new Cancion("hive",z,path,nombre);
		album = new Album(LocalDate.now().getYear(), "deporte", z);
		l = new Lista("por mi", z);
		a.getUsuariosTotales().add(z);
		a.getUsuariosTotales().add(d);
		a.iniciarSesion("alex", "alex");
	}

}