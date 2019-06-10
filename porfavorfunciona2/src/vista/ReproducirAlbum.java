package vista;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;



public class ReproducirAlbum extends PantallaPrincipal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Album album;
	
	JButton botonPlay;
	JButton botonPause;
	public JList lista_comentarios;
	JScrollPane comentariosScrollPane;
	public JList lista_canciones;
	JScrollPane cancionesScrollPane;
	JButton botonList;
	JButton botonAnyadirComentario;
	JButton botonReportar;
	JLabel datos_album;
	JLabel titulo_album;
	JLabel anyo_album;
	JLabel autor_album;
	JLabel duracion_album;
	JLabel comentarios_label;
	public ArrayList<Comentario> comentarios;
	ArrayList<Cancion> canciones;
	
	public ReproducirAlbum(Album album) {
		super();
		this.album = album;
		
		//Declaracion
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
	    
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		this.botonReportar = new JButton("Reportar");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		if(this.album == null) {
			datos_album = new JLabel("Datos del album", SwingConstants.CENTER);
			titulo_album = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.CENTER);
			anyo_album = new JLabel("Año:\t\t\t\t\t",SwingConstants.LEFT);
			autor_album = new JLabel("Autor:\t\t\t\t\t",SwingConstants.LEFT);
			duracion_album = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
			comentarios_label = new JLabel("Comentarios de la album", SwingConstants.CENTER);
		}else{
			datos_album = new JLabel("Datos del album", SwingConstants.CENTER);
			titulo_album = new JLabel("Titulo:\t\t\t\t\t" + album.getTitulo() ,SwingConstants.CENTER);
			anyo_album = new JLabel("Año:\t\t\t\t\t" + album.getAnyo(),SwingConstants.LEFT);
			autor_album = new JLabel("Autor:\t\t\t\t\t" + album.getAutor(),SwingConstants.LEFT);
			duracion_album = new JLabel("Duracion:\t\t\t\t\t" + album.getDuracion() + " s",SwingConstants.LEFT);
			comentarios_label = new JLabel("Comentarios de la album", SwingConstants.CENTER);
			
			this.actualizarCanciones();
			this.actualizarComentarios();
		}
		
		comentariosScrollPane = new JScrollPane(lista_comentarios);
		
		cancionesScrollPane = new JScrollPane(lista_canciones);
		
		//Style changes
		Font datosFont = new Font(datos_album.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_album.getFont().getName(),Font.BOLD,titulo_album.getFont().getSize());
		Font anyoFont = new Font(anyo_album.getFont().getName(),Font.BOLD,anyo_album.getFont().getSize());
		Font autorFont = new Font(autor_album.getFont().getName(),Font.BOLD,autor_album.getFont().getSize());
		Font duracionFont = new Font(duracion_album.getFont().getName(),Font.BOLD,duracion_album.getFont().getSize());
		Font comentariosFont = new Font(datos_album.getFont().getName(),Font.BOLD,datos_album.getFont().getSize());
        comentariosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        cancionesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_album.setFont(datosFont);
		titulo_album.setFont(tituloFont);
		anyo_album.setFont(anyoFont);
		autor_album.setFont(autorFont);
		duracion_album.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_album.setBounds(screenSize.width/2 - 380, 170, 300, 50);
		autor_album.setBounds(screenSize.width/2  - 375, 210, 150, 50);
		anyo_album.setBounds(screenSize.width/2  - 375,250,150,50);
		duracion_album.setBounds(screenSize.width/2  - 375,290,180,50);
		comentarios_label.setBounds(screenSize.width/2  - 380, 340, 300, 50);
		comentariosScrollPane.setBounds(screenSize.width/2  - 380, 400, 300, 200);
		botonList.setBounds(screenSize.width/2 - 300, 610, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
		botonReportar.setBounds(screenSize.width/2 - 230, 640, 150, 30);

		titulo_album.setBounds(screenSize.width/2 + 150, 210, 200, 50);
		cancionesScrollPane.setBounds(screenSize.width/2  + 100, 260, 300, 300);

		botonPlay.setBounds(screenSize.width/2 + 180, 580, 60, 60);
		botonPause.setBounds(screenSize.width/2 + 250, 580, 60, 60);

	
		
		//Añadimos
		this.add(datos_album);
		this.add(titulo_album);
		this.add(anyo_album);
		this.add(autor_album);
		this.add(duracion_album);
		this.add(comentarios_label);
		this.add(cancionesScrollPane);
		this.add(comentariosScrollPane);
		this.add(botonList);
		this.add(botonAnyadirComentario);
		this.add(botonReportar);
		this.add(botonPlay);
		this.add(botonPause);
	}
	
	public void limpiarBuscador(){
		this.busquedaTextfield.setText("");
		this.grupo_eleccion.clearSelection();
	}
	
	
	public void setUsuarioRegistrado() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
	}
	
	public void setUsuarioNoRegistrado() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setText("Registro");
		this.botonIzquierdaAbajo.setVisible(true);
	}
	
	 // método para asignar un controlador al botón
	 public void setControlador(ActionListener c) {
		 this.botonIzquierdaArriba.addActionListener(c);
		 this.botonIzquierdaAbajo.addActionListener(c);
		 this.botonIzquierdaMedio.addActionListener(c);
		 this.botonBuscar.addActionListener(c);
		 this.botonLimpiarBuscador.addActionListener(c);
		 this.botonList.addActionListener(c);
		 this.botonAnyadirComentario.addActionListener(c);
		 this.botonReportar.addActionListener(c);
		 this.botonPlay.addActionListener(c);
	 }
	 
	 @SuppressWarnings("unchecked")
	public void actualizarComentarios() {
		comentarios = album.getComentarios();
		lista_canciones = new JList(comentarios.toArray());
		comentariosScrollPane = new JScrollPane(lista_canciones);
	 }
	 
	 @SuppressWarnings("unchecked")
	public void actualizarCanciones() {
		canciones = album.getContenido();
		lista_canciones = new JList(canciones.toArray());
		cancionesScrollPane = new JScrollPane(lista_canciones);
	 }
}
