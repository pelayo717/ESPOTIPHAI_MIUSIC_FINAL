package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.*;


public class ReproducirLista extends PantallaPrincipal {

	private Lista lista;
	
	JButton botonPlay;
	JButton botonPause;
	JList lista_canciones;
	JScrollPane scrollPane;
	JButton botonList;
	JButton botonAnyadirComentario;
	JButton botonReportar;
	Firulais[] names;
	Comentario[] comentarios;

	public ReproducirLista(Lista lista) {
		super();
		this.lista = lista;
		
		//Declaracion
		ImageIcon icono_corchea = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/photo_default.jpg");
		ImageIcon icono_reproducir = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/play.png");
		ImageIcon icono_parar = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/pause.png");
		
	    
	    
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		this.botonReportar = new JButton("Reportar");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		/*JLabel datos_lista = new JLabel("Datos de la lista", SwingConstants.CENTER);
		JLabel titulo_lista = new JLabel("Titulo:\t\t\t\t\t" + this.lista.getTitulo(),SwingConstants.CENTER);
		JLabel anyo_lista = new JLabel("Año:\t\t\t\t\t" + this.lista.getAnyo(),SwingConstants.CENTER);
		JLabel autor_lista = new JLabel("Autor:\t\t\t\t\t" + this.lista.getAutor(),SwingConstants.CENTER);
		JLabel duracion_lista = new JLabel("Duracion:\t\t\t\t\t" + this.lista.getDuracion() + " s",SwingConstants.CENTER);
		JLabel comentarios_label = new JLabel("Comentarios de la lista", SwingConstants.CENTER);*/
		
		JLabel datos_lista = new JLabel("Datos de la lista", SwingConstants.CENTER);
		JLabel titulo_lista = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.LEFT);
		JLabel anyo_lista = new JLabel("Año:\t\t\t\t\t" ,SwingConstants.LEFT);
		JLabel autor_lista = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
		JLabel duracion_lista = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
		JLabel comentarios_label = new JLabel("Comentarios de la lista", SwingConstants.CENTER);
		
		/*if (!Sistema.sistema.getUsuarioActual().getListas().isEmpty()) {
			ArrayList<Comentario> arrayComentarios = Sistema.sistema.getUsuarioActual().getListas().get(0).getComentarios();
			this.comentarios = arrayComentarios.toArray(new Comentario[arrayComentarios.size()]);
		}*/
		
		System.out.println(comentarios);
		Firulais pelayo = new Firulais("Pelayo", "Estoy haciendo el codigo");
		Firulais manolo = new Firulais("Manuel", "Estoy jugando al Fornite");
		names = new Firulais[2];
		names[0] = pelayo;
		names[1] = manolo;
		lista_canciones = new JList(names);
		scrollPane = new JScrollPane(lista_canciones);

		
		//Style changes
		Font datosFont = new Font(datos_lista.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_lista.getFont().getName(),Font.BOLD,titulo_lista.getFont().getSize());
		Font anyoFont = new Font(anyo_lista.getFont().getName(),Font.BOLD,anyo_lista.getFont().getSize());
		Font autorFont = new Font(autor_lista.getFont().getName(),Font.BOLD,autor_lista.getFont().getSize());
		Font duracionFont = new Font(duracion_lista.getFont().getName(),Font.BOLD,duracion_lista.getFont().getSize());
		Font comentariosFont = new Font(datos_lista.getFont().getName(),Font.BOLD,datos_lista.getFont().getSize());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_lista.setFont(datosFont);
		titulo_lista.setFont(tituloFont);
		anyo_lista.setFont(anyoFont);
		autor_lista.setFont(autorFont);
		duracion_lista.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_lista.setBounds(screenSize.width/2 - 380, 170, 300, 50);
		autor_lista.setBounds(screenSize.width/2  - 375, 210, 150, 50);
		anyo_lista.setBounds(screenSize.width/2  - 375,250,150,50);
		duracion_lista.setBounds(screenSize.width/2  - 375,290,180,50);
		comentarios_label.setBounds(screenSize.width/2  - 380, 340, 300, 50);

		titulo_lista.setBounds(screenSize.width/2 + 150, 210, 200, 50);
		scrollPane.setBounds(screenSize.width/2  + 100, 260, 300, 300);

		botonPlay.setBounds(screenSize.width/2 + 180, 580, 60, 60);
		botonPause.setBounds(screenSize.width/2 + 250, 580, 60, 60);
		
		
		


	
		
		//Añadimos
		this.add(datos_lista);
		this.add(titulo_lista);
		this.add(anyo_lista);
		this.add(autor_lista);
		this.add(duracion_lista);
		this.add(comentarios_label);
		this.add(scrollPane);
		this.add(botonPlay);
		this.add(botonPause);
	}
	
	public void limpiarBuscador(){
		this.busquedaTextfield.setText("");
		this.grupo_eleccion.clearSelection();
	}
	
	
	public void setUsuarioRegistrado() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaAbajo.setVisible(false);
	}
	
	public void setUsuarioNoRegistrado() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaAbajo.setVisible(true);
	}
	
	 // método para asignar un controlador al botón
	 public void setControlador(ActionListener c) {
		 this.botonIzquierdaArriba.addActionListener(c);
		 this.botonIzquierdaAbajo.addActionListener(c);
		 this.botonBuscar.addActionListener(c);
		 this.botonLimpiarBuscador.addActionListener(c);
		 this.botonList.addActionListener(c);
		 this.botonAnyadirComentario.addActionListener(c);
		 this.botonReportar.addActionListener(c);
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
	 }
}
