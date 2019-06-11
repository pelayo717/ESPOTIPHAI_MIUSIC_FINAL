package vista;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;

  



public class ReproducirCancion extends PantallaPrincipal {

	public Cancion cancion;
	
	JLabel datos_cancion;
	JLabel titulo_cancion;
	JLabel autor_cancion;
	JLabel duracion_cancion;
	JLabel comentarios_label;
	
	JButton botonPlay;
	JButton botonPause;
	public JList lista_comentarios;
	JScrollPane comentariosScrollPane;
	JButton botonList;
	JButton botonAnyadirComentario;
	JButton botonReportar;
	public Comentario[] comentarios;
	public DefaultListModel<String> model1;

	public ReproducirCancion(Cancion cancion) {
		super();
		
		this.cancion = cancion;
		
		if(cancion != null) {
			System.out.print(cancion.getTitulo());
		}
		model1 = new DefaultListModel<>();

		lista_comentarios = new JList(model1);
		
		comentariosScrollPane = new JScrollPane(lista_comentarios);
		
		//Declaracion
		ImageIcon icono_corchea = new ImageIcon("src/vista/photo_default.jpg");
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
	    
	    
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		this.botonReportar = new JButton("Reportar");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		
		if(this.cancion != null) {
			datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
			titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" + this.cancion.getTitulo(),SwingConstants.CENTER);
			autor_cancion = new JLabel("Autor:\t\t\t\t\t" + this.cancion.getAutor().getNombreAutor(),SwingConstants.CENTER);
			duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + this.cancion.getDuracion() + " s",SwingConstants.CENTER);
			comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
			this.actualizarComentarios();
		}else {
			datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
			titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.LEFT);
			autor_cancion = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
			duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
			comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
		}
		
		
		

		
		//Style changes
		Font datosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_cancion.getFont().getName(),Font.BOLD,titulo_cancion.getFont().getSize());
		Font autorFont = new Font(autor_cancion.getFont().getName(),Font.BOLD,autor_cancion.getFont().getSize());
		Font duracionFont = new Font(duracion_cancion.getFont().getName(),Font.BOLD,duracion_cancion.getFont().getSize());
		Font comentariosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
        comentariosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_cancion.setFont(datosFont);
		titulo_cancion.setFont(tituloFont);
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
		autor_cancion.setBounds(screenSize.width/2 + 50,250,250,50);
		duracion_cancion.setBounds(screenSize.width/2 + 50,290,250,50);
		comentarios_label.setBounds(screenSize.width/2 + 105, 330, 250, 50);
		comentariosScrollPane.setBounds(screenSize.width/2 + 80, 370, 300, 200);
		botonList.setBounds(screenSize.width/2 + 150, 600, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 670, 150, 30);
		botonReportar.setBounds(screenSize.width/2 + 230, 670, 150, 30);


		imagen_reproduccion.setBounds(screenSize.width/2 - 350, 190, 300, 300);
		botonPlay.setBounds(screenSize.width/2 - 280, 500, 60, 60);
		botonPause.setBounds(screenSize.width/2 - 210, 500, 60, 60);

	
		
		//Añadimos
		this.add(datos_cancion);
		this.add(titulo_cancion);
		this.add(autor_cancion);
		this.add(duracion_cancion);
		this.add(comentarios_label);
		this.add(comentariosScrollPane);
		this.add(botonList);
		this.add(botonAnyadirComentario);
		this.add(botonReportar);
		this.add(imagen_reproduccion);
		this.add(botonPlay);
		this.add(botonPause);
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
		 this.botonIzquierdaMedio.addActionListener(c);
		 this.botonIzquierdaAbajo.addActionListener(c);
		 this.botonBuscar.addActionListener(c);
		 this.botonLimpiarBuscador.addActionListener(c);
		 this.botonList.addActionListener(c);
		 this.botonAnyadirComentario.addActionListener(c);
		 this.botonReportar.addActionListener(c);
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
	 }
	 
	 @SuppressWarnings("unchecked")
		public void actualizarComentarios() {
		 	model1.clear();
			comentarios = cancion.getComentarios().toArray(new Comentario[cancion.getComentarios().size()]);
			if(comentarios != null) {
				for(int i=0; i < comentarios.length;i++) {
					model1.addElement(comentarios[i].getTexto());
				}
			}
		 }

	public void insertarComentario(Comentario nuevoComentario) {
		if(this.cancion != null) {
			cancion.anyadirComentario(nuevoComentario);
		}
	}
	
	
}
