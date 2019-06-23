package vista;

import java.awt.*;
import javax.swing.*;

/**
 * Clase en la que se implementa la vista PantallaPrincipal con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class PantallaPrincipal extends JPanel{ //99.9% esta terminado
	
	private static final long serialVersionUID = 1L;
	private  JButton botonIzquierdaArriba;   
	private  JButton botonIzquierdaMedio;
	private  JButton botonIzquierdaAbajo;
	

	private  JButton botonBuscar; 
	private JButton botonLimpiarBuscador;
	
	private JTextField busquedaTextfield;

	private JRadioButton opcion1;
	private JRadioButton opcion2;
	private JRadioButton opcion3;
	
	private ButtonGroup grupo_eleccion;
	
	/**
	 * Constructor de la clase PantallaPrincipal donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public PantallaPrincipal() { //CAMBIADO, MEJORADO
		
		this.setBackground(new Color(40,159,211));
		this.botonIzquierdaArriba = new JButton("Iniciar Sesion");
		this.botonIzquierdaMedio = new JButton("Registro");
		this.botonIzquierdaAbajo = new JButton("Inicio");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		this.busquedaTextfield = new JTextField(10);
		this.botonBuscar = new JButton("Buscar");
		this.botonLimpiarBuscador = new JButton("Limpiar Buscador");
		
		this.opcion1 = new JRadioButton("Titulo");
		this.opcion2 = new JRadioButton("Album");
		this.opcion3 = new JRadioButton("Autor");
		
		this.grupo_eleccion = new ButtonGroup();
		grupo_eleccion.add(opcion1);
		grupo_eleccion.add(opcion2);
		grupo_eleccion.add(opcion3);
		
		//Style changes
		Font tituloFont = new Font(titulo.getFont().getName(),Font.BOLD,22);

		titulo.setFont(tituloFont);
		titulo.setForeground(Color.yellow);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  
		
		botonIzquierdaArriba.setBounds(10, 10, 150, 30);
		botonIzquierdaMedio.setBounds(10, 50, 150, 30);
		botonIzquierdaAbajo.setBounds(10, 90, 150, 30);
		titulo.setBounds(screenSize.width/2 - 300, 20, 600, 30);
		busquedaTextfield.setBounds(screenSize.width/2 - 250,90,500, 40);
		botonBuscar.setBounds(screenSize.width/2 + 270, 90, 150, 30);
		botonLimpiarBuscador.setBounds(screenSize.width/2 + 270,130,150, 30);
		opcion1.setBounds(screenSize.width/2 - 180, 130, 80, 30);
		opcion2.setBounds(screenSize.width/2 - 40, 130, 80, 30);
		opcion3.setBounds(screenSize.width/2 + 100, 130, 80, 30);


		
		//We add all the components
		this.add(botonIzquierdaArriba);
		this.add(botonIzquierdaMedio);
		this.add(botonIzquierdaAbajo);
		this.add(titulo);
		this.add(busquedaTextfield);
		this.add(botonBuscar);
		this.add(botonLimpiarBuscador);
		this.add(opcion1);
		this.add(opcion2);
		this.add(opcion3);
		
	}
	
	/**
	 * Funcion la cual devuelve la opcion 1 la cual ha 
	 * sido pulsada por el usuario
	 * @return opcion1: duelve la opcion que ha pulsado el usuario
	 */
	public JRadioButton getOpcion1() {
		return this.opcion1;
	}
	
	/**
	 * Funcion la cual devuelve la opcion 2 la cual ha 
	 * sido pulsada por el usuario
	 * @return opcion2: duelve la opcion que ha pulsado el usuario
	 */
	public JRadioButton getOpcion2() {
		return this.opcion2;
	}
	
	/**
	 * Funcion la cual devuelve la opcion 3 la cual ha 
	 * sido pulsada por el usuario
	 * @return opcion3: duelve la opcion que ha pulsado el usuario
	 */
	public JRadioButton getOpcion3() {
		return this.opcion3;
	}
	
	/**
	 * Funcion la cual devuelve que criterio de busqueda ha 
	 * seleccionado el usuario
	 * @return busquedaTextfield: duelve la opcion que ha pulsado el usuario
	 */
	public JTextField getCriterioBusqueda() {
		return this.busquedaTextfield;
	}
	
	/**
	 * Funcion la cual devuelve el grupo de elecciones que ha hecho el 
	 *  usuario
	 * @return grupo_eleccion: atributo de tipo groupButton que devuelve el
	 * grupo de eleccion del usuario
	 */
	public ButtonGroup getGrupoElecciones() {
		return this.grupo_eleccion;
	}
	
	/**
	 * Funcion la cual devuelve la opcion 1 la cual ha 
	 * sido pulsada por el usuario
	 * @return opcion1: duelve la opcion que ha pulsado el usuario
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * Funcion la cual devuelve la opcion 1 la cual ha 
	 * sido pulsada por el usuario
	 * @return opcion1: duelve la opcion que ha pulsado el usuario
	 */
	public JButton getBotonIzquierdaArriba() {
		return botonIzquierdaArriba;
	}


	/**
	 * Funcion la cual devuelve la opcion 1 la cual ha 
	 * sido pulsada por el usuario
	 * @return opcion1: duelve la opcion que ha pulsado el usuario
	 */
	public JButton getBotonIzquierdaMedio() {
		return botonIzquierdaMedio;
	}


	/**
	 * Funcion la cual devuelve el boton situado en la 
	 * parte izquierda baja de la vista
	 * @return botonIzquierdoAbajo: boton el cual se devuelve en la 
	 * funcion
	 */
	public JButton getBotonIzquierdaAbajo() {
		return botonIzquierdaAbajo;
	}

	/**
	 * Funcion la cual devuelve el boton de buscar con lo que se ha pulsado
	 * @return botonBuscar: boton que devuelve el boton de buscar de la vista
	 */
	public JButton getBotonBuscar() {
		return botonBuscar;
	}

	/**
	 * Funcion la cual devuelve el boton de limpiar buscador
	 * @return botonLimpiarBuscador: boton el cual limpia el buscador y 
	 * se devuelve en la funcion
	 */
	public JButton getBotonLimpiarBuscador() {
		return botonLimpiarBuscador;
	}

	/**
	 * Funcion que devuelve el texto que se ha introducido para 
	 * ser buscado
	 * @return busquedaTextflied: texto en el cual el usuario
	 * ha escrito lo que quiere buscar
	 */
	public JTextField getBusquedaTextfield() {
		return busquedaTextfield;
	}

	/**
	 * Funcion la cual devuelve el grupo de eleccion del usuario
	 * @return grupo_eleccion: botongroup que devuelve el grupo de
	 * eleccion del usuario
	 */
	public ButtonGroup getGrupo_eleccion() {
		return grupo_eleccion;
	}

	
	/**
	 * Funcion que limpiar el buscador y lo deja  en blanco para 
	 * la proxima busqueda 
	 */
	public void limpiarBuscador(){
		this.busquedaTextfield.setText("");
		this.grupo_eleccion.clearSelection();
	}

}
