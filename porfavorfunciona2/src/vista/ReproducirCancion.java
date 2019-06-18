package vista;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import modelo.contenido.*;

  



public class ReproducirCancion extends PantallaPrincipal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cancion cancion;
	
	JLabel datos_cancion;
	JLabel titulo_cancion;
	JLabel autor_cancion;
	JLabel duracion_cancion;
	JLabel comentarios_label;
	JLabel estadoCancion;
	
	JButton botonPlay;
	JButton botonPause;
	public JList<String> lista_comentarios;
	JScrollPane comentariosScrollPane;
	JButton botonList;
	JButton botonAnyadirComentario;
	JButton botonReportar;
	
	JButton modificarCancion;
	
	JButton anyadirAlbum;
	JButton anyadirLista;
	
	Dimension screenSize;
	
	public Comentario[] comentarios;
	public DefaultListModel<String> model1;

	public ReproducirCancion(Cancion cancion) {
		super();
		
		this.cancion = cancion;
		model1 = new DefaultListModel<>();

		lista_comentarios = new JList<String>(model1);
		
		comentariosScrollPane = new JScrollPane(lista_comentarios);
		
		//Declaracion
		ImageIcon icono_corchea = new ImageIcon("src/vista/photo_default.jpg");
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
	    
	    
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver comentario");
		this.botonAnyadirComentario = new JButton("Añadir comentario");
		this.botonReportar = new JButton("Reportar");
		this.anyadirAlbum = new JButton("Añadir a Album");
		this.anyadirLista = new JButton("Añadir a Lista");
		this.modificarCancion = new JButton("Modificar cancion");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		
		if(this.cancion != null) {
			
			int horas = (int) (cancion.getDuracion() / 3600);
		    int minutos = (int) ((cancion.getDuracion()-horas*3600)/60);
		    int segundos = (int) (cancion.getDuracion()-(horas*3600+minutos*60));
			
			datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
			titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" + this.cancion.getTitulo(),SwingConstants.CENTER);
			autor_cancion = new JLabel("Autor:\t\t\t\t\t" + this.cancion.getAutor().getNombreAutor(),SwingConstants.CENTER);
			duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + minutos + " m/" + segundos + " s",SwingConstants.CENTER);
			comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
			estadoCancion = new JLabel("Estado Cancion:\t\t\t\t\t" + this.cancion.getEstado().name());
			this.actualizarComentarios();
			if(this.cancion.getEstado() == EstadoCancion.PENDIENTEMODIFICACION) {
				this.modificarCancion.setVisible(true);
			}else{
				this.modificarCancion.setVisible(false);
			}
		}else {
			datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
			titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.LEFT);
			autor_cancion = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
			duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
			comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
			estadoCancion = new JLabel("Estado Cancion:\t\t\t\t\t");
		}
		
		
		

		
		//Style changes
		Font datosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_cancion.getFont().getName(),Font.BOLD,titulo_cancion.getFont().getSize());
		Font autorFont = new Font(autor_cancion.getFont().getName(),Font.BOLD,autor_cancion.getFont().getSize());
		Font duracionFont = new Font(duracion_cancion.getFont().getName(),Font.BOLD,duracion_cancion.getFont().getSize());
		Font comentariosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
        Font estadoCancionFont = new Font(estadoCancion.getFont().getName(),Font.BOLD,estadoCancion.getFont().getSize());
		comentariosScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_cancion.setFont(datosFont);
		titulo_cancion.setFont(tituloFont);
		autor_cancion.setFont(autorFont);
		duracion_cancion.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);
		estadoCancion.setFont(estadoCancionFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_cancion.setBounds(screenSize.width/2 + 125, 170, 200, 50);
		titulo_cancion.setBounds(screenSize.width/2 + 50, 210, 200, 50);
		autor_cancion.setBounds(screenSize.width/2 + 50,250,200,50);
		duracion_cancion.setBounds(screenSize.width/2 + 50,290,250,50);
		comentarios_label.setBounds(screenSize.width/2 + 105, 370, 250, 50);
		estadoCancion.setBounds(screenSize.width/2 + 105, 330, 400, 50);
		comentariosScrollPane.setBounds(screenSize.width/2 + 80, 410, 300, 200);
		botonList.setBounds(screenSize.width/2 + 150, 640, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);
		botonReportar.setBounds(screenSize.width/2 + 230, 680, 150, 30);


		imagen_reproduccion.setBounds(screenSize.width/2 - 350, 190, 300, 300);
		botonPlay.setBounds(screenSize.width/2 - 280, 500, 60, 60);
		botonPause.setBounds(screenSize.width/2 - 210, 500, 60, 60);
		anyadirAlbum.setBounds(screenSize.width/2 - 370, 600, 150, 30);
		anyadirLista.setBounds(screenSize.width/2 - 210, 600, 150, 30);
	
		modificarCancion.setBounds(screenSize.width/2 + 420, 340, 150, 30);
		
		//Añadimos
		this.add(datos_cancion);
		this.add(titulo_cancion);
		this.add(autor_cancion);
		this.add(duracion_cancion);
		this.add(comentarios_label);
		this.add(estadoCancion);
		this.add(comentariosScrollPane);
		this.add(botonList);
		this.add(botonAnyadirComentario);
		this.add(botonReportar);
		this.add(imagen_reproduccion);
		this.add(botonPlay);
		this.add(botonPause);
		this.add(anyadirAlbum);
		this.add(anyadirLista);
		this.add(modificarCancion);
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
		 this.anyadirAlbum.addActionListener(c);
		 this.anyadirLista.addActionListener(c);
		 this.modificarCancion.addActionListener(c);
	 }
	 
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
	
	public void limpiarBuscador(){
		this.busquedaTextfield.setText("");
		this.grupo_eleccion.clearSelection();
	}

	public void setAdministrador() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(false);
		this.botonReportar.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 150, 680, 150, 30);
	}
	
	public void setUsuarioRegistradoPropia() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
		this.anyadirAlbum.setVisible(true);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 210, 600, 150, 30);
		this.botonReportar.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}
	
	public void setUsuarioRegistradoNoPropia() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 280, 600, 150, 30);
		this.botonReportar.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}
	
	
	public void setUsuarioNoRegistradoNoPropia() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setText("Registro");
		this.botonIzquierdaAbajo.setVisible(true);
		this.anyadirAlbum.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.anyadirLista.setBounds(screenSize.width/2 - 280, 600, 150, 30);
		this.botonReportar.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 + 80, 680, 150, 30);

	}
	
	
}
