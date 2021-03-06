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
	
	private  DefaultListModel<String> model1;
	
	private  DefaultListModel<String> model2;

	private  Contenido[] losContenidos;
	
	private  Usuario[] losAutores;

	private  JList<String> lista_autores;
	
	private  JList<String> lista_contenidos;

	private JScrollPane autores;
	
	private JScrollPane contenidos;

	private JLabel autoresEncontrados;
	
	private JLabel contenidosEncontrados;
	
	public JButton seleccionarAutor;
	
	public JButton seleccionarContenido;
	
	
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
		this.getBotonIzquierdaArriba().addActionListener(c);
		this.getBotonIzquierdaMedio().addActionListener(c);
		this.seleccionarAutor.addActionListener(c);
		this.seleccionarContenido.addActionListener(c);
		this.getBotonIzquierdaAbajo().addActionListener(c);
		this.getBotonBuscar().addActionListener(c);
		this.getBotonLimpiarBuscador().addActionListener(c);
	}
	
	public void setUsuarioRegistrado() {
		this.getBotonIzquierdaArriba().setText("Ver Perfil");
		this.getBotonIzquierdaMedio().setText("Inicio");
		this.getBotonIzquierdaAbajo().setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		this.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		this.getBotonIzquierdaMedio().setText("Registro");
		this.getBotonIzquierdaAbajo().setVisible(true);
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public DefaultListModel<String> getModel1() {
		return model1;
	}


	public DefaultListModel<String> getModel2() {
		return model2;
	}


	public Contenido[] getLosContenidos() {
		return losContenidos;
	}


	public Usuario[] getLosAutores() {
		return losAutores;
	}


	public JList<String> getLista_autores() {
		return lista_autores;
	}


	public JList<String> getLista_contenidos() {
		return lista_contenidos;
	}


	public JScrollPane getAutores() {
		return autores;
	}


	public JScrollPane getContenidos() {
		return contenidos;
	}


	public JLabel getAutoresEncontrados() {
		return autoresEncontrados;
	}


	public JLabel getContenidosEncontrados() {
		return contenidosEncontrados;
	}


	public JButton getSeleccionarAutor() {
		return seleccionarAutor;
	}


	public JButton getSeleccionarContenido() {
		return seleccionarContenido;
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


	public Contenido[] getContenido() {
		// TODO Auto-generated method stub
		return this.losContenidos;
	}

	
}
