package vista;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

import javax.swing.*;

import modelo.sistema.*;
import modelo.contenido.*;



public class ReproducirAlbum extends PantallaPrincipal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Album album;
	
	public JList<String> lista_comentarios;
	public JList<String> lista_canciones;

	
	JScrollPane comentariosScrollPane;
	JScrollPane cancionesScrollPane;

	JButton botonList;
	JButton botonAnyadirComentario;
	JButton eliminarCancion;
	JButton anyadirLista;
	JButton botonPlay;
	JButton botonPause;
	
	
	JLabel datos_album;
	JLabel titulo_album;
	JLabel anyo_album;
	JLabel autor_album;
	JLabel duracion_album;
	JLabel comentarios_label;
	
	public Comentario[] misComentarios;
	public DefaultListModel<String> model1;
	
	public Cancion[] misCanciones;
	public DefaultListModel<String> model2;
	
	//public Cancion reproduciendose;
	
	Dimension screenSize;
	
	public ReproducirAlbum() {
		super();
		
		model1 = new DefaultListModel<>();

		lista_comentarios = new JList<String>(model1);
		
		comentariosScrollPane = new JScrollPane(lista_comentarios);
		
		model2 = new DefaultListModel<>();

		lista_canciones = new JList<String>(model2);
		
		lista_canciones.setCellRenderer( new RowColor());

		
		cancionesScrollPane = new JScrollPane(lista_canciones);
		
		
		//Declaracion
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
	    
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonList = new JButton("Ver comentario");
		this.botonAnyadirComentario = new JButton("Añadir Comentario");
		
		this.eliminarCancion = new JButton("Eliminar Cancion");
		
		this.anyadirLista = new JButton("Añadir a Lista");
		
		botonPlay.setIcon(icono_reproducir);
		botonPause.setIcon(icono_parar);

		
			datos_album = new JLabel("Datos del album", SwingConstants.CENTER);
			titulo_album = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.CENTER);
			anyo_album = new JLabel("Año:\t\t\t\t\t",SwingConstants.LEFT);
			autor_album = new JLabel("Autor:\t\t\t\t\t",SwingConstants.LEFT);
			duracion_album = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
			comentarios_label = new JLabel("Comentarios de la album", SwingConstants.CENTER);
	
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
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Distribucion
		datos_album.setBounds(screenSize.width/2 - 380, 170, 300, 50);
		autor_album.setBounds(screenSize.width/2  - 375, 210, 150, 50);
		anyo_album.setBounds(screenSize.width/2  - 375,250,150,50);
		duracion_album.setBounds(screenSize.width/2  - 375,290,180,50);
		comentarios_label.setBounds(screenSize.width/2  - 380, 340, 300, 50);
		comentariosScrollPane.setBounds(screenSize.width/2  - 380, 400, 300, 200);
		botonList.setBounds(screenSize.width/2 - 300, 610, 150, 30);
		botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
		anyadirLista.setBounds(screenSize.width/2 - 230, 640, 150, 30);

		titulo_album.setBounds(screenSize.width/2 + 150, 210, 200, 50);
		cancionesScrollPane.setBounds(screenSize.width/2  + 100, 260, 300, 300);

		botonPlay.setBounds(screenSize.width/2 + 180, 580, 60, 60);
		botonPause.setBounds(screenSize.width/2 + 250, 580, 60, 60);

		
		eliminarCancion.setBounds(screenSize.width/2  + 430, 390, 150, 40);
	
		
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
		this.add(botonPlay);
		this.add(botonPause);
		this.add(eliminarCancion);
		this.add(anyadirLista);
	}
	
	public void limpiarBuscador(){
		this.busquedaTextfield.setText("");
		this.grupo_eleccion.clearSelection();
	}
	
	public void setAdministrador() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
		this.anyadirLista.setVisible(false);
		this.eliminarCancion.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 - 300, 640, 150, 30);
	}
	
	public void setUsuarioRegistradoPropia() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setText("Inicio");
		this.botonIzquierdaAbajo.setVisible(false);
		this.anyadirLista.setVisible(true);
		this.eliminarCancion.setVisible(true);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
	}
	
	public void setUsuarioRegistradoNoPropia() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setText("Registro");
		this.botonIzquierdaAbajo.setVisible(true);
		this.anyadirLista.setVisible(true);
		this.eliminarCancion.setVisible(false);
		this.botonAnyadirComentario.setBounds(screenSize.width/2 - 380, 640, 150, 30);
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
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
		 this.anyadirLista.addActionListener(c);
		 this.eliminarCancion.addActionListener(c);
	 }
	 
	 public void setInformacion(Album album_entrante) {
		this.album = album_entrante; 
		 
	 	this.datos_album.setText("Datos del album");
		this.titulo_album.setText("Titulo album:\t\t\t\t\t" + this.album.getTitulo());
		this.anyo_album.setText("Año:\t\t\t\t\t" + this.album.getAnyo());
		this.autor_album.setText("Autor:\t\t\t\t\t" + this.album.getAutor().getNombreAutor());
		int horas = (int) (this.album.getDuracion() / 3600);
	    int minutos = (int) ((this.album.getDuracion()-horas*3600)/60);
	    int segundos = (int) (this.album.getDuracion()-(horas*3600+minutos*60));
		this.duracion_album.setText("Duracion:\t\t\t\t\t" + horas + " h/" + minutos + " m/" + segundos + " s");
		this.comentarios_label.setText("Comentarios de la album");
		
		this.actualizarCanciones();
		this.actualizarComentarios();
	 }
	 
	 public void actualizarComentarios() {
		model1.clear();
		misComentarios = album.getComentarios().toArray(new Comentario[album.getComentarios().size()]);
		if(misComentarios != null) {
			for(int i=0; i < misComentarios.length;i++) {
				model1.addElement(misComentarios[i].getTexto());
			}
		}
	 }
	 
	 public void actualizarCanciones() {
		model2.clear();
		misCanciones = album.getContenido().toArray(new Cancion[album.getContenido().size()]);
		if(misCanciones != null) {
			for(int i=0; i < misCanciones.length;i++) {
				model2.addElement("Titulo: " + misCanciones[i].getTitulo() + " // Duracion: " + misCanciones[i].getDuracion() + " // Estado: " + misCanciones[i].getEstado().toString());
			}
		}
	 }
	 
	 public void insertarComentario(Comentario nuevoComentario) {
			if(this.album != null) {
				album.anyadirComentario(nuevoComentario);
			}
	 }
	 
	 private class RowColor extends DefaultListCellRenderer{
		
			LocalDate fecha_actual = LocalDate.now();

		 
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent( JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
	            Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
	            	
	            	if(Sistema.sistema.getUsuarioActual() != null) {
	            		if(Sistema.sistema.getAdministrador() == true) {
	            			c.setBackground( Color.green);
		                    c.setForeground( Color.black );
	            		}else{
	    					Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
	    					if(intervalo.getYears() >= 18) {
	    						c.setBackground( Color.green);
			                    c.setForeground( Color.black );
	    					}else {
	    						if(misCanciones[index].getEstado() == EstadoCancion.VALIDA) {
	    		                    c.setBackground( Color.green);
	    		                    c.setForeground( Color.black );
	    		            	}else {
	    		            		c.setBackground( Color.red );
	    		                    c.setForeground( Color.black );
	    		            	}
	    					}
	            			
	            		}
	            	}else {
	            		if(misCanciones[index].getEstado() == EstadoCancion.VALIDA) {
		                    c.setBackground( Color.green);
		                    c.setForeground( Color.black );
		            	}else if(misCanciones[index].getEstado() == EstadoCancion.EXPLICITA){
		            		c.setBackground( Color.red);
		                    c.setForeground( Color.black );
		            	}
	            	}
	            	
	            	
	            
	            return c;
	        }
			
		}

	
	 
	 
}
