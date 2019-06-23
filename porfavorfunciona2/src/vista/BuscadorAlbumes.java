package vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.contenido.Album;

/**
 * Clase en la que se implementa la vista BuscadorAlbumes con todo
 * lo necesario para cumplir los requisitos impuestos
 */

public class BuscadorAlbumes extends PantallaPrincipal{//99.9% esta terminado

	
	private static final long serialVersionUID = 1L;
	
	private DefaultListModel<String> model1;

	private  Album[] losAlbumes;

	private  JList<String> lista_albumes;

	private  JScrollPane albumes;

	private JLabel albumesEncontrados;
	
	public JButton seleccionarAlbum;
	
	
	/**
	 * Constructor de la clase BuscadorAlbumes donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public BuscadorAlbumes() {
		
		super();
		
		model1 = new DefaultListModel<>();
		
		
		lista_albumes = new JList<String>(model1);
		
		
		albumes = new JScrollPane(lista_albumes);
		
		
		albumesEncontrados = new JLabel("Albumes Encontrados",  SwingConstants.CENTER);
		
		
		seleccionarAlbum = new JButton("Elegir Album");
		
		
		
		//Cambio de estilo en los JLabel
		Font susAlbumesFont = new Font(albumesEncontrados.getFont().getName(), Font.BOLD, 16);
		
		albumes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.albumesEncontrados.setFont(susAlbumesFont);
	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();				
		
		albumesEncontrados.setBounds(screenSize.width/2 - 115, 200, 290, 30);
		
		albumes.setBounds(screenSize.width/2 - 550, 250, 1100, 300);
		

		seleccionarAlbum.setBounds(screenSize.width/2 - 115, 575, 250, 30);
		

		
		//Anyadimos los elementos a la pantalla principal
		this.add(albumes);
		this.add(albumesEncontrados);
		this.add(seleccionarAlbum);
	}
	
	
	/**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */
	public void setControlador(ActionListener c) {
		this.getBotonIzquierdaArriba().addActionListener(c);
		this.getBotonIzquierdaMedio().addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
		this.getBotonIzquierdaAbajo().addActionListener(c);
		this.getBotonBuscar().addActionListener(c);
		this.getBotonLimpiarBuscador().addActionListener(c);
	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario si que esa registrado
	 */
	public void setUsuarioRegistrado() {
		this.getBotonIzquierdaArriba().setText("Ver Perfil");
		this.getBotonIzquierdaMedio().setText("Inicio");
		this.getBotonIzquierdaAbajo().setVisible(false);
	}
	
	/**
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario no esta registrado
	 */
	public void setUsuarioNoRegistrado() {
		this.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		this.getBotonIzquierdaMedio().setText("Registro");
		this.getBotonIzquierdaAbajo().setVisible(true);
	}
	
	/**
	 * Funcion que delvuelve el atributo serialVersionUID de la clase
	 * @param serialVersionUID:
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Funcion que devuelve el modelo1 de la clase
	 * @param model1: atributo que indica que estamos en el modelo 1
	 */
	public DefaultListModel<String> getModel1() {
		return model1;
	}
	
	/**
	 * Funcion que devuelve un array con todos los albumes que hay 
	 * @return losAlbumes: array de albumes que contiene todos los albumes
	 */
	public Album[] getLosAlbumes() {
		return losAlbumes;
	}

	/**
	 * Funcion que devuelve una lista de albumes 
	 * @return lista_albumes: lista que contiene todos los albumes 
	 */
	public JList<String> getLista_albumes() {
		return lista_albumes;
	}

	/**
	 * Funcion que devuelve un JScrollPane con todos los albumes 
	 * @return albumes: JScrollPane en el que dentro esta la lista de albumes
	 */
	public JScrollPane getAlbumes() {
		return albumes;
	}

	/**
	 * Funcion que devuelve los albumes despues de la busqueda
	 * @param albumesEncontrados: JLabel que contiene todos los albumes 
	 * encontrados despues de la busqueda
	 */
	public JLabel getAlbumesEncontrados() {
		return albumesEncontrados;
	}

	/**
	 * Funcion que devuelve el boton de seleccionar album
	 * @return seleccionarAlbum: devuelve el boton de seleccionarAlbum
	 */
	public JButton getSeleccionarAlbum() {
		return seleccionarAlbum;
	}

	/**
	 * Funcion que actualiza los albumes por los albumes propios
	 * @param albumes_propios: albumes que se van a poner como nuevos al actualizar
	 */
	public void actualizarAlbumes(Album[] albumes_propios) {
		model1.clear();
		losAlbumes = albumes_propios;
		
		for(int i=0; i < losAlbumes.length; i++) {
			int horas = (int) (losAlbumes[i].getDuracion() / 3600);
		    int minutos = (int) ((losAlbumes[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (losAlbumes[i].getDuracion()-(horas*3600+minutos*60));
		    
			model1.addElement("Titulo: " + losAlbumes[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Autor: " + losAlbumes[i].getAutor().getNombreAutor());
		}
	}
	
	/**
	 * Funcion que limpia los datos completamente
	 */
	public void limpiarDatos() {
		model1.clear();
	}

	/**
	 * Funcion que un array con todos los albumes disponibles
	 * @return losAlbumes: array con todos los albumes
	 */
	public Album[] getAlbum() {
		return this.losAlbumes;
	}

	/**
	 * Funcion que devuelve un Jlist de todos los albumes 
	 * @return lista_albumes: atributo de tipo JList que contiene
	 * una lista con todos los albumes
	 */
	public JList<String> getListAlbum() {
		return this.lista_albumes;
	}

}
