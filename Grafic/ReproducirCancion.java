package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.Sistema;
import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.contenido.Comentario;

class Firulais {
	   public String autor;
	   public String comentario;
	   public Firulais(String autor, String comentario) {
			this.autor = autor;
			this.comentario = comentario;
	   }
	   
	   @Override
	    public String toString() {
	        return autor + ":\n\t " + comentario;
	    }
};  



public class ReproducirCancion extends PantallaPrincipal {

	private String titulo;
	private int anyo;
	private String autor;
	private int duracion;
	
	JButton botonPlay;
	JButton botonPause;
	JList lista_comentarios;
	JScrollPane scrollPane;
	JButton botonList;
	JButton botonAnyadirComentario;
	JButton botonReportar;
	Firulais[] names;
	Comentario[] comentarios;

	public ReproducirCancion(String arg1,int arg2,String arg3,int arg4) {
		super();
		this.titulo = arg1;
		this.anyo = arg2;
		this.autor = arg3;
		this.duracion = arg4;
		
		//Declaracion
		ImageIcon icono_corchea = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/photo_default.jpg");
		ImageIcon icono_reproducir = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/play.png");
		ImageIcon icono_parar = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/pause.png");
		
	    
	    
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		this.botonPlay = new JButton();
		this.botonPause = new JButton();
		this.botonList = new JButton("Ver comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		this.botonReportar = new JButton("Reportar");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		JLabel datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
		JLabel titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" + this.titulo,SwingConstants.CENTER);
		JLabel anyo_cancion = new JLabel("Año:\t\t\t\t\t" + this.anyo,SwingConstants.CENTER);
		JLabel autor_cancion = new JLabel("Autor:\t\t\t\t\t" + this.autor,SwingConstants.CENTER);
		JLabel duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + this.duracion + " s",SwingConstants.CENTER);
		JLabel comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
		if (!Sistema.sistema.getCancionTotales().isEmpty()) {
			ArrayList<Comentario> arrayComentarios = Sistema.sistema.getCancionTotales().get(0).getComentarios();
			this.comentarios = arrayComentarios.toArray(new Comentario[arrayComentarios.size()]);
		}
		
		System.out.println(comentarios);
		Firulais pelayo = new Firulais("Pelayo", "Estoy haciendo el codigo");
		Firulais manolo = new Firulais("Manuel", "Estoy jugando al Fornite");
		names = new Firulais[2];
		names[0] = pelayo;
		names[1] = manolo;
		lista_comentarios = new JList(names);
		scrollPane = new JScrollPane(lista_comentarios);

		
		//Style changes
		Font datosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_cancion.getFont().getName(),Font.BOLD,titulo_cancion.getFont().getSize());
		Font anyoFont = new Font(anyo_cancion.getFont().getName(),Font.BOLD,anyo_cancion.getFont().getSize());
		Font autorFont = new Font(autor_cancion.getFont().getName(),Font.BOLD,autor_cancion.getFont().getSize());
		Font duracionFont = new Font(duracion_cancion.getFont().getName(),Font.BOLD,duracion_cancion.getFont().getSize());
		Font comentariosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_cancion.setFont(datosFont);
		titulo_cancion.setFont(tituloFont);
		anyo_cancion.setFont(anyoFont);
		autor_cancion.setFont(autorFont);
		duracion_cancion.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_cancion.setBounds(screenSize.width/2 + 125, 170, 200, 50);
		titulo_cancion.setBounds(screenSize.width/2 + 50, 210, 200, 50);
		anyo_cancion.setBounds(screenSize.width/2 + 50, 250, 150, 50);
		autor_cancion.setBounds(screenSize.width/2 + 50,290,150,50);
		duracion_cancion.setBounds(screenSize.width/2 + 50,330,180,50);
		comentarios_label.setBounds(screenSize.width/2 + 105, 370, 250, 50);
		scrollPane.setBounds(screenSize.width/2 + 80, 420, 300, 200);
		botonList.setBounds(screenSize.width/2 + 150, 630, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 670, 150, 30);
		botonReportar.setBounds(screenSize.width/2 + 230, 670, 150, 30);


		imagen_reproduccion.setBounds(screenSize.width/2 - 350, 190, 300, 300);
		botonPlay.setBounds(screenSize.width/2 - 280, 500, 70, 70);
		botonPause.setBounds(screenSize.width/2 - 210, 500, 70, 70);

	
		
		//Añadimos
		this.add(datos_cancion);
		this.add(titulo_cancion);
		this.add(anyo_cancion);
		this.add(autor_cancion);
		this.add(duracion_cancion);
		this.add(comentarios_label);
		this.add(scrollPane);
		this.add(botonList);
		this.add(botonAnyadirComentario);
		this.add(botonReportar);
		this.add(imagen_reproduccion);
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
	 }
}
