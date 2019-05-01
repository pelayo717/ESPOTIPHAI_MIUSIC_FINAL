package ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.status.Status;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

import pads.musicPlayer.Mp3Player;
import pads.musicPlayer.exceptions.Mp3InvalidFileException;
import pads.musicPlayer.exceptions.Mp3PlayerException;




	/**
	 *	Clase Cancion con herencia de ContenidoComentable
	 */


	public class Cancion extends ContenidoComentable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private EstadoCancion estado;
		private String nombreMP3;
		private static Mp3Player repro_mp3;
		private boolean es_explicita=false;
		private LocalDate fecha_modificar;
		/**
		 *	Constructor de Cancion
		 *	@param anyo anyo de creacion de la Cancion (Date)
		 * 	@param titulo el titulo de la Cancion (String)
		 * 	@param autor autor de la Cancion (Usuario) 
		 * 	@param nombreMP3 el titulo de la Cancion (String)
		 *  @param explicita boleean de expolicita de la Cancion (boolean)
		 *	@throws Mp3PlayerException 
		 * 	@throws FileNotFoundException 
		 */
		public Cancion(Date anyo, String titulo, Usuario autor,  String nombreMP3, boolean explicita) throws FileNotFoundException, Mp3PlayerException{
			super(anyo, titulo, autor, new ArrayList<Comentario>());
			Cancion.repro_mp3 = new Mp3Player();
			this.setNombreMP3(nombreMP3);
			this.setDuracion(this.devolverDuracion());
			this.setEstado(EstadoCancion.PENDIENTEAPROBACION);
			this.setEsExplicita(explicita);
			this.fecha_modificar = LocalDate.now();
		}
		
		


		/**
		 *	Funcion para anyadir a la cola de reproduccion
		 *	@param cancion_a_anyadir  string de la cancion a anyadir
		 */
		public void anyadirCola() {
			try {
				Cancion.repro_mp3.add(this.nombreMP3);
				return;
			}catch(Mp3InvalidFileException ie) {
				ie.toString();
				return;
			}
		}
		
		
		
		/**
		 *	Funcion para reproducir una cancion
		 */
		public void reproducir() {
			try {
				Cancion.repro_mp3.play();
				return;
			}catch(Mp3PlayerException pe) {
				pe.toString();
				return;
			}
			
		}
		
		
		/**
		 *	Funcion para parar una cancion
		 */
		public void parar() {
				Cancion.repro_mp3.stop();
		}
		
		/**
		 *	Funcion para saber si es tipo MP3 o no
		 *	@param cancion string de la cancion
		 *	return true si es de tipo MP3 y false de lo contrario
		 */
		public boolean esMP3() {
			if(Mp3Player.isValidMp3File(this.nombreMP3) == true) {
				return true;
			}
			return false;
		}
		
		/**
		 *	Funcion de MP3 para devolver la duracion de la cancion
		 *	@param cancion string de la cancion
		 *	return double de la duracion de la cancion
		 */
		public double devolverDuracion() {
			try {
				double duracion = Mp3Player.getDuration(this.nombreMP3);
				return duracion;
			}catch(FileNotFoundException fe) {
				fe.toString();
				return -1.0;
			}
		}
		

		/**
		 *	Funcion de reportarPlagio
		 * 	@return  OK si no hay errores y ERROR de lo contrario
		 */
		public Status reportarPlagio() {
			this.setEstado(EstadoCancion.PLAGIO);
			return Status.OK;
		}
		
		/**
		 *	Funcion de validarCancionExplicita
		 * 	@return  OK si no hay errores y ERROR de lo contrario
		 */
		public Status validarCancionExplicita() {
			this.setEstado(EstadoCancion.EXPLICITA);
			return Status.OK;
		}
		
		/**
		 *	Funcion de validarCancion
		 * 	@return  OK si no hay errores y ERROR de lo contrario
		 */
		public Status validarCancion() {
			this.setEstado(EstadoCancion.PENDIENTEAPROBACION);
			return Status.OK;
		}
		
		/**
		 *	Funcion de cancionRechazada
		 * 	@return  OK si no hay errores y ERROR de lo contrario
		 */
		public Status cancionRechazada() {
			this.setEstado(EstadoCancion.PENDIENTEMODIFICACION);
			return Status.OK;
		}
		
		/**
		 *	Funcion de cancionCorregida
		 * 	@return  OK si no hay errores y ERROR de lo contrario
		 */
		public Status cancionCorregida() {
			this.setEstado(EstadoCancion.VALIDA);
			return Status.OK;
		}
		
		/**
		 *	Funcion de cancionDescartada
		 * 	@return  OK si no hay errores y ERROR de lo contrario
		 */
		public Status cancionDescartada() {
			this.setEstado(EstadoCancion.ELIMINADA);
			return Status.OK;
		}

		
		
		//GETTERS Y SETTERS
		/**
		 * Getter de fechaModificacion
		 * @return fecha de modificacion
		 */
		public LocalDate getFechaModificacion() {
			return this.fecha_modificar;
		}
		
		/**
		 * Getter de estado
		 * @return estado estado de la cancion
		 */
		public EstadoCancion getEstado() {
			return estado;
		}

		/**
		 * Setter de estado
		 * @param estado estado de la cancion
		 */
		public void setEstado(EstadoCancion estado) {
			if(estado == EstadoCancion.PENDIENTEMODIFICACION) {
				this.fecha_modificar = LocalDate.now();
			}
			this.estado = estado;
		}
		
		/**
		 * Getter del nombre del MP3
		 * @return nombreMP3 nombre de la cancion
		 */
		public String getNombreMP3() {
			return nombreMP3;
		}

		/**
		 * Setter de la fecha de inicio para realizar la modificacion
		 * @param d la fecha de mofificacion de la cancion
		 */
		public void setFechaModificacion(LocalDate d) {
			this.fecha_modificar = d;
		}
		
		/**
		 * Setter del nombre del MP3
		 * @param nombreMP3 el nombre de la cacion
		 */
		public void setNombreMP3(String nombreMP3) {
			this.nombreMP3 = nombreMP3;
		}
		
		/**
		 * Setter del reproductor mp3
		 */
		public void setMp3Player() throws FileNotFoundException, Mp3PlayerException {
			Cancion.repro_mp3 = new Mp3Player();
		}
		
		/**
		 * Getter del boolean de Explicita
		 * @return True si es explicita o false de lo contrario
		 */
		public boolean getEsExplicita() {
			return this.es_explicita;
		}
		
		/**
		 * Setter del boolean de Explicita
		 * @param explicita2 boolean de si es o no explicita
		 */
		public void setEsExplicita(boolean explicita2) {
			this.es_explicita = explicita2;
		}
		

	}