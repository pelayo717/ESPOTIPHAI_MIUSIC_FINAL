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

import modelo.contenido.Cancion;

/**
 * Clase en la que se implementa la vistaBuscadorCanciones con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class BuscadorCanciones extends PantallaPrincipal{ //99.9% esta terminado

	private static final long serialVersionUID = 1L;

	private  DefaultListModel<String> model1;

	private  Cancion[] lasCanciones;

	private  JList<String> lista_canciones;

	private JScrollPane canciones;

	private JLabel cancionesEncontradas;
	
	public JButton seleccionarCancion;
	
	
	/**
	 * Constructor de la clase BuscadorCanciones donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public BuscadorCanciones() {
		
		super();
		
		model1 = new DefaultListModel<>();
		
		
		lista_canciones = new JList<String>(model1);
		
		
		canciones = new JScrollPane(lista_canciones);
		
		
		cancionesEncontradas = new JLabel("Canciones Encontradas",  SwingConstants.CENTER);
		
		
		seleccionarCancion = new JButton("Elegir Cancion");
		
		
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(cancionesEncontradas.getFont().getName(), Font.BOLD, 16);
		
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.cancionesEncontradas.setFont(susCancionesFont);
	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();				
		
		cancionesEncontradas.setBounds(screenSize.width/2 - 115, 200, 290, 30);
		
		canciones.setBounds(screenSize.width/2 - 550, 250, 1100, 300);
		

		seleccionarCancion.setBounds(screenSize.width/2 - 115, 575, 250, 30);
		

		
		//Anyadimos los elementos a la pantalla principal
		this.add(canciones);
		this.add(cancionesEncontradas);
		this.add(seleccionarCancion);
		
	}


	/**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */
	public void setControlador(ActionListener c) {
		super.getBotonIzquierdaArriba().addActionListener(c);
		super.getBotonIzquierdaMedio().addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
		super.getBotonIzquierdaAbajo().addActionListener(c);
		super.getBotonBuscar().addActionListener(c);
		super.getBotonLimpiarBuscador().addActionListener(c);
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
	 * Funcion que cambia el texto de unos determinados botones para ponerlos el 
	 * texto a lo necesario para cuando el usuario no esta registrado
	 */
	public void setUsuarioNoRegistrado() {
		super.getBotonIzquierdaArriba().setText("Iniciar Sesion");
		super.getBotonIzquierdaMedio().setText("Registro");
		super.getBotonIzquierdaAbajo().setVisible(true);
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
	 * Funcion que devuelve un array con todos las canciones que hay 
	 * @return lasCanciones: array de albumes que contiene todos los albumes
	 */
	public Cancion[] getLasCanciones() {
		return lasCanciones;
	}

	/**
	 * Funcion que devuelve una lista de canciones
	 * @return lista_canciones: lista que contiene todos las canciones 
	 */
	public JList<String> getLista_canciones() {
		return lista_canciones;
	}

	/**
	 * Funcion que devuelve un JScrollPane con todos las canciones
	 * @return canciones: JScrollPane en el que dentro esta la lista de canciones
	 */
	public JScrollPane getCanciones() {
		return canciones;
	}

	/**
	 * Funcion que devuelve las canciones despues de la busqueda
	 * @param cancionesEncontradas: JLabel que contiene todos las canciones 
	 * encontrados despues de la busqueda
	 */
	public JLabel getCancionesEncontradas() {
		return cancionesEncontradas;
	}

	/**
	 * Funcion que devuelve el boton de seleccionar cancion
	 * @return seleccionarCancion: devuelve el boton de seleccionarCancion
	 */
	public JButton getSeleccionarCancion() {
		return seleccionarCancion;
	}

	/**
	 * Funcion que actualiza las canciones por el arrat que se le pasa
	 * @param canciones_propias: canciones que se van a poner como nuevas al actualizar
	 */
	public void actualizarCanciones(Cancion[] canciones_propias) {
		model1.clear();
		lasCanciones = canciones_propias;
		for(int i=0; i < lasCanciones.length; i++) {
			
			int horas = (int) (lasCanciones[i].getDuracion() / 3600);
		    int minutos = (int) ((lasCanciones[i].getDuracion()-horas*3600)/60);
		    int segundos = (int) (lasCanciones[i].getDuracion()-(horas*3600+minutos*60));
		    
			model1.addElement("Titulo: " + lasCanciones[i].getTitulo() + " // Duracion HH-MM-SS: " + horas + "-" + minutos + "-" + segundos + " // Autor: " + lasCanciones[i].getAutor().getNombreAutor());
		}
	}
	
	/**
	 * Funcion que limpia los datos completamente
	 */
	public void limpiarDatos() {
		model1.clear();
	}
	
}
