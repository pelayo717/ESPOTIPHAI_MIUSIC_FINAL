package vista;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import modelo.sistema.*;
import modelo.usuario.Usuario;
import modelo.contenido.*;

/**
 * Clase en la que se implementa la vista ReproducirLista con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class ReproducirLista extends PantallaPrincipal {

	
	private static final long serialVersionUID = 1L;

	private Lista lista;
	
	public JButton botonPlay;
	public JButton botonPause;
	public JButton botonDelete;
	private JList<String> lista_contenido;
	private JScrollPane contenidoScrollPane;
	private JButton botonList;
	private JButton anyadirLista;
	
	private JLabel datos_lista;
	private JLabel titulo_lista;
	private JLabel autor_lista;
	private JLabel duracion_lista;
	private JLabel comentarios_label;
	private JLabel num_canciones;
	private JLabel num_albumes;
	private JLabel num_listas;

	private  Contenido[] contenido;
	private  DefaultListModel<String> model1;
	
	/**
	 * Constructor de la clase ReproducirLista donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public ReproducirLista() {
		super();
		
		//Declaracion
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
		
		model1 = new DefaultListModel<>();

		lista_contenido = new JList<String>(model1);
	    
	    
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonDelete = new JButton("Retirar Contenido");
		this.botonList = new JButton("Ver comentario");
		this.anyadirLista = new JButton("Añadir a Lista");
		
		botonPlay.setIcon(icono_reproducir); 
		botonPause.setIcon(icono_parar);
		
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		
		
		datos_lista = new JLabel("Datos de la lista", SwingConstants.CENTER);
		titulo_lista = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.CENTER);
		autor_lista = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
		duracion_lista = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
		num_canciones = new JLabel("Num. Canciones:\t\t\t\t\t",SwingConstants.LEFT);
		num_albumes = new JLabel("Num. Albumes:\t\t\t\t\t",SwingConstants.LEFT);
		num_listas = new JLabel("Num. Listas:\t\t\t\t\t",SwingConstants.LEFT);

		contenidoScrollPane = new JScrollPane(lista_contenido);

		
		//Style changes
		Font datosFont = new Font(datos_lista.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_lista.getFont().getName(),Font.BOLD,titulo_lista.getFont().getSize());
		Font autorFont = new Font(autor_lista.getFont().getName(),Font.BOLD,autor_lista.getFont().getSize());
		Font duracionFont = new Font(duracion_lista.getFont().getName(),Font.BOLD,duracion_lista.getFont().getSize());
        Font numCancionesFont = new Font(num_canciones.getFont().getName(),Font.BOLD,num_canciones.getFont().getSize());
        Font numAlbumesFont = new Font(num_albumes.getFont().getName(),Font.BOLD,num_albumes.getFont().getSize());
        Font numListasFont = new Font(num_listas.getFont().getName(),Font.BOLD,num_listas.getFont().getSize());
		
		contenidoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		num_canciones.setFont(numCancionesFont);
		num_albumes.setFont(numAlbumesFont);
		num_listas.setFont(numListasFont);
		datos_lista.setFont(datosFont);
		titulo_lista.setFont(tituloFont);
		autor_lista.setFont(autorFont);
		duracion_lista.setFont(duracionFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Distribucion
		datos_lista.setBounds(screenSize.width/2 - 350, 210, 300, 50);
		autor_lista.setBounds(screenSize.width/2  - 345, 250, 150, 50);
		duracion_lista.setBounds(screenSize.width/2  - 345,300,180,50);
		num_canciones.setBounds(screenSize.width/2  - 345,350,180,50);
		num_albumes.setBounds(screenSize.width/2  - 345,400,180,50);
		num_listas.setBounds(screenSize.width/2  - 345,450,180,50);

		titulo_lista.setBounds(screenSize.width/2 + 150, 210, 200, 50);
		contenidoScrollPane.setBounds(screenSize.width/2  + 100, 260, 300, 300);

		botonPlay.setBounds(screenSize.width/2 + 180, 580, 60, 60);
		botonPause.setBounds(screenSize.width/2 + 270, 580, 60, 60);
		botonDelete.setBounds(screenSize.width/2 + 420, 350, 150, 30);
		anyadirLista.setBounds(screenSize.width/2 + 420, 400, 150, 30);

		
		//Añadimos
		this.add(datos_lista);
		this.add(titulo_lista);
		this.add(autor_lista);
		this.add(duracion_lista);
		this.add(num_canciones);
		this.add(num_albumes);
		this.add(num_listas);
		this.add(contenidoScrollPane);
		this.add(botonPlay);
		this.add(botonPause);
		this.add(botonDelete);
		this.add(anyadirLista);
	}
	
	/**
	 * Funcion que limpia el buscador de la aplicacion y lo deja vacio 
	 * para que el usuario pueda realizar otra busqueda
	 */
	public void limpiarBuscador(){
		super.getBusquedaTextfield().setText("");
		super.getGrupo_eleccion().clearSelection();
	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario si que esa registrado
	 */
	public void setUsuarioRegistrado() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
	}

	
	 /**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */

	 public void setControlador(ActionListener c) {
		 super.getBotonIzquierdaArriba().addActionListener(c);
		 super.getBotonIzquierdaMedio().addActionListener(c);
		 super.getBotonIzquierdaAbajo().addActionListener(c);
		 super.getBotonBuscar().addActionListener(c);
		 super.getBotonLimpiarBuscador().addActionListener(c);
		 this.botonList.addActionListener(c);
		 this.botonPlay.addActionListener(c);
		 this.botonPause.addActionListener(c);
		 this.botonDelete.addActionListener(c);
		 this.anyadirLista.addActionListener(c);
	 }
	 
	 /**
	 * Funcion que pone la informacion necesaria sobre la vista en base al argumento de
	 * entrada, siendo este la lista que se va a reproducir
	 */
	 public void setInformacion(Lista lista) {
		this.lista = lista;

		int nCanciones = 0;
		int nAlbumes = 0;
		int nListas = 0;
		int horas = (int) (lista.getDuracion() / 3600);
	    int minutos = (int) ((lista.getDuracion()-horas*3600)/60);
	    int segundos = (int) (lista.getDuracion()-(horas*3600+minutos*60));
	    
	    
	    this.titulo_lista.setText("Titulo:\t\t\t\t\t" + this.lista.getTitulo());
		this.autor_lista.setText("Autor:\t\t\t\t\t" + this.lista.getAutor().getNombreAutor());
		this.duracion_lista.setText("Duracion:\t\t\t\t\t" + minutos + " m/" + segundos + " s");
		
		for(Contenido aux: this.lista.getContenido()) {
			if(aux instanceof Cancion) {
				nCanciones++;
			}else if(aux instanceof Album) {
				nAlbumes++;
			}else if(aux instanceof Lista) {
				nListas++;
			}
		}
		this.num_canciones.setText("Num. Canciones:\t\t\t\t\t" + nCanciones);
		this.num_albumes.setText("Num. Albumes:\t\t\t\t\t" + nAlbumes);
		this.num_listas.setText("Num. Listas:\t\t\t\t\t" + nListas);

		this.actualizarContenido();
	}
	 
	/**
	 * Funcion que devuelve la Lista que se va a reproducir
	 * @return lista: atributo el cual se va a reproducir por parte del usuario
	 */
	 public Lista getLista() {
		return lista;
	}

	/**
	 * Funcion que devuelve el boton de play en la aplicacion
	 * @return botonPlay: devuelve el boton de play que se encuentra en la vista
	 */
	public JButton getBotonPlay() {
		return botonPlay;
	}

	/**
	 * Funcion que devuelve el boton de pause en la vista para parar la cancion
	 * @return botonPause: devuelve el boton de pausa que se encuentra en la vista
	 */
	public JButton getBotonPause() {
		return botonPause;
	}
	
	/**
	 * Funcion que devuelve el boton de borrar 
	 * @return botonDelete: devuelve el boton de borrar de la vista
	 */
	public JButton getBotonDelete() {
		return botonDelete;
	}

	/**
	 * Funcion que devuelve un Jlist de todos los contenidos
	 * @return lista_contenido: atributo de tipo JList que contiene
	 * una lista con todos los contenidos de la clase
	 */
	public JList<String> getLista_contenido() {
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

	public Contenido[] getContenido() {
		return contenido;
	}

	public void actualizarContenido() {
		model1.clear();
		
		contenido = lista.getContenido().toArray(new Contenido[lista.getContenido().size()]);

		if(contenido != null) {
			for(int i=0; i < contenido.length;i++) {
				
				int horas = (int) (contenido[i].getDuracion() / 3600);
			    int minutos = (int) ((contenido[i].getDuracion()-horas*3600)/60);
			    int segundos = (int) (contenido[i].getDuracion()-(horas*3600+minutos*60));
			    
				if(contenido[i] instanceof Cancion) {
					
					model1.addElement("Cancion ==> Titulo: " + contenido[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos);

				}else if(contenido[i] instanceof Album){
					
					ArrayList<Usuario> usuarios = Sistema.sistema.getUsuariosTotales();
					ArrayList<Album> temporal = null;
					int j=0,k=0,flag=0;
					for(k=0; k < usuarios.size(); k++) {
						temporal = usuarios.get(k).getAlbumes();
						for(j=0; j < temporal.size();j++) {
							if(temporal.get(j).equals(contenido[i])) {
								flag = -1;
								break;
							}
						}
						
						if(flag == -1) {
							break;
						}
					}
					
					
					model1.addElement("Album ==> Titulo: " + contenido[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Num. Canciones: " + temporal.get(j).getContenido().size());
					
					usuarios = null;
					temporal = null;
					
					
				}else if(contenido[i] instanceof Lista) {
					
					ArrayList<Lista> temporal = Sistema.sistema.getUsuarioActual().getListas(); //SOLO TENEMOS LISTAS PROPIAS
					int j=0;
					
					for(j=0; j < temporal.size();j++) {
						if(temporal.get(j).equals(contenido[i])) {
							break;
						}
					}
					
					model1.addElement("Lista ==> Titulo: " + contenido[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Num. Contenido: " + temporal.get(j).getContenido().size());

					temporal = null;
				}
			
			}
		}
	}
}
