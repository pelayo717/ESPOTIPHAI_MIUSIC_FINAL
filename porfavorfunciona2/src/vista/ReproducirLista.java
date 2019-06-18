package vista;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;


public class ReproducirLista extends PantallaPrincipal {

	private Lista lista;
	
	private JButton botonPlay;
	private JButton botonPause;
	private JButton botonAdd;
	private JButton perfilAutor;
	private JList lista_contenido;
	private JScrollPane contenidoScrollPane;
	private JButton botonList;
	
	private JLabel datos_lista;
	private JLabel titulo_lista;
	private JLabel autor_lista;
	private JLabel duracion_lista;
	private JLabel comentarios_label;
	private ArrayList<Contenido> contenido;

	public ReproducirLista(Lista lista) {
		super();
		this.lista = lista;
		
		//Declaracion
		ImageIcon icono_add = new ImageIcon("src/vista/plus.png");
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
		
		perfilAutor = new JButton("Ver Perfil Autor");
		perfilAutor.setOpaque(false);
		perfilAutor.setContentAreaFilled(false);
		perfilAutor.setBorderPainted(false);
		perfilAutor.setForeground(new Color(1f,0f,0f,.0f));
	    
	    
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonAdd = new JButton("add");
		this.botonList = new JButton("Ver comentario");
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);
		botonAdd.setIcon(icono_add);
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaAbajo.setVisible(false);
		
		if(this.lista != null) {
			datos_lista = new JLabel("Datos de la lista", SwingConstants.CENTER);
			titulo_lista = new JLabel("Titulo:\t\t\t\t\t" + this.lista.getTitulo(),SwingConstants.CENTER);
			autor_lista = new JLabel("Autor:\t\t\t\t\t" + this.lista.getAutor(),SwingConstants.CENTER);
			duracion_lista = new JLabel("Duracion:\t\t\t\t\t" + this.lista.getDuracion() + " s",SwingConstants.CENTER);
			comentarios_label = new JLabel("Comentarios de la lista", SwingConstants.CENTER);
			this.actualizarContenido();
		}else {
			datos_lista = new JLabel("Datos de la lista", SwingConstants.CENTER);
			titulo_lista = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.CENTER);
			autor_lista = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
			duracion_lista = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
			comentarios_label = new JLabel("Comentarios de la lista", SwingConstants.CENTER);
		}
		contenidoScrollPane = new JScrollPane(lista_contenido);

		
		//Style changes
		Font datosFont = new Font(datos_lista.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_lista.getFont().getName(),Font.BOLD,titulo_lista.getFont().getSize());
		Font autorFont = new Font(autor_lista.getFont().getName(),Font.BOLD,autor_lista.getFont().getSize());
		Font duracionFont = new Font(duracion_lista.getFont().getName(),Font.BOLD,duracion_lista.getFont().getSize());
		Font comentariosFont = new Font(datos_lista.getFont().getName(),Font.BOLD,datos_lista.getFont().getSize());
        contenidoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_lista.setFont(datosFont);
		titulo_lista.setFont(tituloFont);
		autor_lista.setFont(autorFont);
		duracion_lista.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_lista.setBounds(screenSize.width/2 - 380, 210, 300, 50);
		autor_lista.setBounds(screenSize.width/2  - 375, 250, 150, 50);
		perfilAutor.setBounds(screenSize.width/2  - 375, 250, 150, 50);
		duracion_lista.setBounds(screenSize.width/2  - 375,330,180,50);

		titulo_lista.setBounds(screenSize.width/2 + 150, 210, 200, 50);
		contenidoScrollPane.setBounds(screenSize.width/2  + 100, 260, 300, 300);

		botonPlay.setBounds(screenSize.width/2 + 160, 580, 60, 60);
		botonPause.setBounds(screenSize.width/2 + 230, 580, 60, 60);
		botonAdd.setBounds(screenSize.width/2 + 300, 580, 60, 60);
		
		
		//Añadimos
		this.add(datos_lista);
		this.add(titulo_lista);
		this.add(autor_lista);
		this.add(perfilAutor);
		this.add(duracion_lista);
		this.add(contenidoScrollPane);
		this.add(botonPlay);
		this.add(botonPause);
		this.add(botonAdd);
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
		 this.botonBuscar.addActionListener(c);
		 this.botonLimpiarBuscador.addActionListener(c);
		 this.botonList.addActionListener(c);
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
		 this.botonAdd.addActionListener(c);
		 this.perfilAutor.addActionListener(c);
	 }
	 
	 
	 public void setInformacion(Lista lista) {
			this.lista = lista;

			int horas = (int) (lista.getDuracion() / 3600);
		    int minutos = (int) ((lista.getDuracion()-horas*3600)/60);
		    int segundos = (int) (lista.getDuracion()-(horas*3600+minutos*60));
		    
		    
		    titulo_lista.setText("Titulo:\t\t\t\t\t" + this.lista.getTitulo());
			autor_lista.setText("Autor:\t\t\t\t\t" + this.lista.getAutor());
			duracion_lista.setText("Duracion:\t\t\t\t\t" + minutos + " m/" + segundos + " s");
			
			actualizarContenido();
		}
	 

	 public Lista getLista() {
		return lista;
	}

	public JButton getBotonPlay() {
		return botonPlay;
	}

	public JButton getBotonPause() {
		return botonPause;
	}

	public JButton getBotonAdd() {
		return botonAdd;
	}

	public JButton getPerfilAutor() {
		return perfilAutor;
	}

	public JList getLista_contenido() {
		return lista_contenido;
	}

	public JScrollPane getContenidoScrollPane() {
		return contenidoScrollPane;
	}

	public JButton getBotonList() {
		return botonList;
	}

	public JLabel getDatos_lista() {
		return datos_lista;
	}

	public JLabel getTitulo_lista() {
		return titulo_lista;
	}

	public JLabel getAutor_lista() {
		return autor_lista;
	}

	public JLabel getDuracion_lista() {
		return duracion_lista;
	}

	public JLabel getComentarios_label() {
		return comentarios_label;
	}

	public ArrayList<Contenido> getContenido() {
		return contenido;
	}

	@SuppressWarnings("unchecked")
		public void actualizarContenido() {
			contenido = lista.getContenido();
			lista_contenido = new JList(contenido.toArray());
			contenidoScrollPane = new JScrollPane(lista_contenido);
		 }
}
