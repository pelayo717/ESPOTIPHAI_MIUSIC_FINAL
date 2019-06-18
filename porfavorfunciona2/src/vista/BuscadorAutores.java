package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.contenido.Album;
import modelo.contenido.Cancion;
import modelo.contenido.Contenido;
import modelo.contenido.EstadoCancion;
import modelo.sistema.Sistema;
import modelo.usuario.Usuario;

public class BuscadorAutores extends PantallaPrincipal {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DefaultListModel<String> model1;
	
	public DefaultListModel<String> model2;

	public Contenido[] losContenidos;
	
	public Usuario[] losAutores;

	public JList<String> lista_autores;
	
	public JList<String> lista_contenidos;

	private JScrollPane autores;
	
	private JScrollPane contenidos;

	JLabel autoresEncontrados;
	
	JLabel contenidosEncontrados;
	
	JButton seleccionarAutor;
	
	JButton seleccionarContenido;
	
	
	public BuscadorAutores() {
		
		super();
		
		model1 = new DefaultListModel<>();
		
		model2 = new DefaultListModel<>();
		
		lista_autores = new JList<String>(model1);
		
		lista_contenidos = new JList<String>(model2);
		
		autores = new JScrollPane(lista_autores);
		
		contenidos = new JScrollPane(lista_contenidos);
		
		
		autoresEncontrados = new JLabel("Autores Encontrados",  SwingConstants.CENTER);
		
		contenidosEncontrados = new JLabel("Contenidos Encontrados De Autores",  SwingConstants.CENTER);
		
		seleccionarContenido = new JButton("Elegir contenido");
		
		seleccionarAutor = new JButton("Elegir autor");
		
		//Cambio de estilo en los JLabel
		Font susAutoresFont = new Font(autoresEncontrados.getFont().getName(), Font.BOLD, 16);
		Font susContenidosFont = new Font(contenidosEncontrados.getFont().getName(), Font.BOLD, 16);

		autores.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		contenidos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		this.autoresEncontrados.setFont(susAutoresFont);
		this.contenidosEncontrados.setFont(susContenidosFont);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();				
		
		autoresEncontrados.setBounds(screenSize.width/2 + 220, 200, 290, 30);
		autores.setBounds(screenSize.width/2 + 200, 250, 340, 300);
		seleccionarAutor.setBounds(screenSize.width/2 + 310, 575, 120, 30);
		
		
		contenidosEncontrados.setBounds(screenSize.width/2 - 400, 200, 350, 30);
		contenidos.setBounds(screenSize.width/2 - 550, 250, 700, 300);
		seleccionarContenido.setBounds(screenSize.width/2 - 350, 575, 250, 30);
		
		
		//Anyadimos los elementos a la pantalla principal
		this.add(autores);
		this.add(autoresEncontrados);
		this.add(seleccionarAutor);
		this.add(contenidos);
		this.add(contenidosEncontrados);
		this.add(seleccionarContenido);
	}
	
	
	public void setControlador(ActionListener c) {
		this.botonIzquierdaArriba.addActionListener(c);
		this.botonIzquierdaMedio.addActionListener(c);
		this.seleccionarAutor.addActionListener(c);
		this.seleccionarContenido.addActionListener(c);
		this.botonIzquierdaAbajo.addActionListener(c);
		this.botonBuscar.addActionListener(c);
		this.botonLimpiarBuscador.addActionListener(c);
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
	
	@SuppressWarnings("unlikely-arg-type")
	public void actualizarContenido(Contenido[] autores_propios) {
		model2.clear();
		
		losContenidos = autores_propios;
		
		for(int i=0; i < losContenidos.length; i++) {
						
			int horas = (int) (losContenidos[i].getDuracion() / 3600);
		    int minutos = (int) ((losContenidos[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (losContenidos[i].getDuracion()-(horas*3600+minutos*60));
			
			if(losContenidos[i] instanceof Cancion) {
								
				model2.addElement("Cancion ==> Autor: " + losContenidos[i].getAutor().getNombreAutor() + " // Titulo: " + losContenidos[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos);
			
			}else if(losContenidos[i] instanceof Album) {
								
				int numero_canciones = 0;
				ArrayList<Album> busqueda_temporal = Sistema.sistema.getAlbumTotales();
				
				for(int j=0; j < busqueda_temporal.size(); j++) {
										
					if(busqueda_temporal.equals((Album)losContenidos[i])) {
						
						numero_canciones = busqueda_temporal.get(j).getContenido().size();
				
						break;
					}
				}
				model2.addElement("Album ==> Autor: " + losContenidos[i].getAutor().getNombreAutor() + " // Titulo: " + losContenidos[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Num Canciones: " + numero_canciones);
				busqueda_temporal = null;				
			}
			
		}
	}
	
	public void actualizarAutores(Contenido[] autores_propios) {
		
		int contador=0;
		
		model1.clear();
		ArrayList<Usuario> temporales= new ArrayList<Usuario>();
		
		for(int i=0; i < autores_propios.length;i++) {
			if(temporales.contains(autores_propios[i].getAutor()) == false) {
				temporales.add(autores_propios[i].getAutor());
			}
		}
		
		
		losAutores = temporales.toArray(new Usuario[temporales.size()]);
		temporales = null;
		
		for(int i=0; i < losAutores.length; i++) {
			
			ArrayList<Cancion> encontradas = losAutores[i].getCanciones();
			
			if(Sistema.sistema.getUsuarioActual() != null) {
				
				if(Sistema.sistema.getAdministrador() == true) {
					for(int j=0; j < encontradas.size(); j++) {
						if(encontradas.get(j).getEstado() == EstadoCancion.VALIDA || encontradas.get(j).getEstado() == EstadoCancion.EXPLICITA) {
							contador ++;
						}
					}
					model1.addElement("Autor: " + losAutores[i].getNombreAutor() + " // Canciones Totales: " + contador  + " // Albumes: " + losAutores[i].getAlbumes().size());
					contador = 0;
				}else {
					LocalDate fecha_actual = LocalDate.now();
					Period intervalo = Period.between(Sistema.sistema.getUsuarioActual().getFechaNacimiento(), fecha_actual);
					for(int j=0; j < encontradas.size(); j++) {
						if(encontradas.get(j).getEstado() == EstadoCancion.VALIDA || (encontradas.get(j).getEstado() == EstadoCancion.EXPLICITA && intervalo.getYears() >= 18)) {
							contador ++;
						}
					}
					model1.addElement("Autor: " + losAutores[i].getNombreAutor() + " // Canciones Totales: " + contador  + " // Albumes: " + losAutores[i].getAlbumes().size());
					contador = 0;
				}
				
				
			}else {
				for(int j=0; j < encontradas.size(); j++) {
					if(encontradas.get(j).getEstado() == EstadoCancion.VALIDA) {
						contador ++;
					}
				}
				model1.addElement("Autor: " + losAutores[i].getNombreAutor() + " // Canciones Totales: " + contador  + " // Albumes: " + losAutores[i].getAlbumes().size());
				contador = 0;
			}
			
			encontradas = null;

		}
	}
	
	public void limpiarDatos() {
		model1.clear();
		model2.clear();
	}

	
}
