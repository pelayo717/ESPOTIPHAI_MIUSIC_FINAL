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
	
	public JButton botonPlay;
	public JButton botonPause;
	public JButton botonDelete;
	public JButton perfilAutor;
	private JList<String> lista_contenido;
	private JScrollPane contenidoScrollPane;
	private JButton botonList;
	
	private JLabel datos_lista;
	private JLabel titulo_lista;
	private JLabel autor_lista;
	private JLabel duracion_lista;
	private JLabel comentarios_label;

	private  Contenido[] contenido;
	private  DefaultListModel<String> model1;
	
	public ReproducirLista() {
		super();
		
		//Declaracion
		ImageIcon icono_reproducir = new ImageIcon("src/vista/play.png");
		ImageIcon icono_parar = new ImageIcon("src/vista/pause.png");
		
		
		model1 = new DefaultListModel<>();

		lista_contenido = new JList<String>(model1);
		
		perfilAutor = new JButton("Ver Perfil Autor");
		perfilAutor.setOpaque(false);
		perfilAutor.setContentAreaFilled(false);
		perfilAutor.setBorderPainted(false);
		perfilAutor.setForeground(new Color(1f,0f,0f,.0f));
	    
	    
		this.botonPlay = new JButton("play");
		this.botonPause = new JButton("pause");
		this.botonDelete = new JButton("Eliminar Contenido");
		this.botonList = new JButton("Ver comentario");
		botonPlay.setIcon(icono_reproducir); 
		botonPause.setIcon(icono_parar);
		
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
		
		
		datos_lista = new JLabel("Datos de la lista", SwingConstants.CENTER);
		titulo_lista = new JLabel("Titulo:\t\t\t\t\t" ,SwingConstants.CENTER);
		autor_lista = new JLabel("Autor:\t\t\t\t\t" ,SwingConstants.LEFT);
		duracion_lista = new JLabel("Duracion:\t\t\t\t\t" + " s",SwingConstants.LEFT);
		
		contenidoScrollPane = new JScrollPane(lista_contenido);

		
		//Style changes
		Font datosFont = new Font(datos_lista.getFont().getName(),Font.BOLD,16);
		Font tituloFont = new Font(titulo_lista.getFont().getName(),Font.BOLD,titulo_lista.getFont().getSize());
		Font autorFont = new Font(autor_lista.getFont().getName(),Font.BOLD,autor_lista.getFont().getSize());
		Font duracionFont = new Font(duracion_lista.getFont().getName(),Font.BOLD,duracion_lista.getFont().getSize());
        contenidoScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_lista.setFont(datosFont);
		titulo_lista.setFont(tituloFont);
		autor_lista.setFont(autorFont);
		duracion_lista.setFont(duracionFont);

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
		botonDelete.setBounds(screenSize.width/2 + 300, 580, 150, 60);
		
		
		//Añadimos
		this.add(datos_lista);
		this.add(titulo_lista);
		this.add(autor_lista);
		this.add(perfilAutor);
		this.add(duracion_lista);
		this.add(contenidoScrollPane);
		this.add(botonPlay);
		this.add(botonPause);
		this.add(botonDelete);
	}
	
	public void limpiarBuscador(){
		super.getBusquedaTextfield().setText("");
		super.getGrupo_eleccion().clearSelection();
	}
	
	public void setUsuarioRegistrado() {
		super.getBotonIzquierdaArriba().setText("Ver Perfil");
		super.getBotonIzquierdaMedio().setText("Inicio");
		super.getBotonIzquierdaAbajo().setVisible(false);
	}

	
	 // método para asignar un controlador al botón
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
		 this.perfilAutor.addActionListener(c);
	 }
	 
	 
	 public void setInformacion(Lista lista) {
		this.lista = lista;

		int horas = (int) (lista.getDuracion() / 3600);
	    int minutos = (int) ((lista.getDuracion()-horas*3600)/60);
	    int segundos = (int) (lista.getDuracion()-(horas*3600+minutos*60));
	    
	    
	    this.titulo_lista.setText("Titulo:\t\t\t\t\t" + this.lista.getTitulo());
		this.autor_lista.setText("Autor:\t\t\t\t\t" + this.lista.getAutor().getNombreAutor());
		this.duracion_lista.setText("Duracion:\t\t\t\t\t" + minutos + " m/" + segundos + " s");
		
		this.actualizarContenido();
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

	public JButton getBotonDelete() {
		return botonDelete;
	}

	public JButton getPerfilAutor() {
		return perfilAutor;
	}

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
					
					ArrayList<Album> temporal = Sistema.sistema.getUsuarioActual().getAlbumes();
					int j=0;
					
					for(j=0; j < temporal.size();j++) {
						if(temporal.get(j).equals(contenido[i])) {
							break;
						}
					}
					
					model1.addElement("Album ==> Titulo: " + contenido[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Num. Canciones: " + temporal.get(j).getContenido().size());

					temporal = null;
					
					
				}else if(contenido[i] instanceof Lista) {
					
					ArrayList<Lista> temporal = Sistema.sistema.getUsuarioActual().getListas();
					int j=0;
					
					for(j=0; j < temporal.size();j++) {
						if(temporal.get(j).equals(contenido[i])) {
							break;
						}
					}
					
					model1.addElement("Lista ==> Titulo: " + contenido[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Num. Contenido: " + temporal.get(j).getContenido().size());

				}
			
			}
		}
	}
}
