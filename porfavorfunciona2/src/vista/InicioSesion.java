package vista;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Clase en la que se implementa la vista InicioSesion con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class InicioSesion extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public JButton botonInicio;
	public JButton botonIniciarSesion;
	public JButton botonRegistrarse;
	private JLabel titulo ;
	private JLabel usuarioLabel;
	private JLabel passwordLabel;
	private JLabel preguntaLabel;
	private JTextField usuarioTextfield;
	private JPasswordField passwordTextfield;
	
	/**
	 * Constructor de la clase InicioSesion donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public InicioSesion() {
		
		this.setBackground(new Color(40,159,211));
		this.botonInicio = new JButton("Inicio");
		this.titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		this.usuarioLabel = new JLabel("Introduzca nombre de usuario", SwingConstants.CENTER);
		this.usuarioTextfield = new JTextField(20);
		this.passwordLabel = new JLabel("Introduzca su contraseña", SwingConstants.CENTER);
		this.passwordTextfield = new JPasswordField(20);
		this.botonIniciarSesion = new JButton("Iniciar Sesion");
		this.preguntaLabel = new JLabel("¿Sin registrarse?", SwingConstants.CENTER);
		this.botonRegistrarse = new JButton("Registrarse");
		
		
		//Style changes
		Font tituloFont = new Font(titulo.getFont().getName(),Font.BOLD,22);
		Font usuarioLabelFont = new Font(usuarioLabel.getFont().getName(),Font.BOLD,usuarioLabel.getFont().getSize());
		Font passwordLabelFont = new Font(passwordLabel.getFont().getName(),Font.BOLD,passwordLabel.getFont().getSize());

		titulo.setFont(tituloFont);
		titulo.setForeground(Color.yellow);
		usuarioLabel.setFont(usuarioLabelFont);
		passwordLabel.setFont(passwordLabelFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();  

		botonInicio.setBounds(10, 10, 100, 30);
		titulo.setBounds(screenSize.width/2 - 300, 20, 600, 30);
		usuarioLabel.setBounds(screenSize.width/2 - 300, 90, 600, 30);
		usuarioTextfield.setBounds(screenSize.width/2 - 150,120,300, 40);
		passwordLabel.setBounds(screenSize.width/2 - 300, 180, 600, 30);
		passwordTextfield.setBounds(screenSize.width/2 - 150,210,300, 40);
		botonIniciarSesion.setBounds(screenSize.width/2 - 75, 250, 150, 30);
		preguntaLabel.setBounds(screenSize.width/2 - 300, 310, 600, 30);
		botonRegistrarse.setBounds(screenSize.width/2 - 75, 340, 150, 30);

		
		//We add all the components
		this.add(titulo);
		this.add(botonInicio);
		this.add(usuarioLabel);
		this.add(usuarioTextfield);
		this.add(passwordLabel);
		this.add(passwordTextfield);
		this.add(botonIniciarSesion);
		this.add(preguntaLabel);
		this.add(botonRegistrarse);
		
	}
	
	
	public void limpiarVentana(){
		this.passwordTextfield.setText("");
		this.usuarioTextfield.setText("");
	}
	
	/**
	 * Funcion que delvuelve el atributo serialVersionUID de la clase
	 * @param serialVersionUID: 
	 */
	 public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Funcion que devuelve el boton de inicio
	 * @return botonInicio: atributo de tipo JButto el cual identifica el boton de Inicio en la vista
	 */
	public JButton getBotonInicio() {
		return botonInicio;
	}
	
	/**
	 * Funcion que devuelve el boton de IniciarSesion
	 * @return botonIniciarSesion: atributo de tipo JButton el cual 
	 * representa el boton de inicio sesion en la vista
	 */
	public JButton getBotonIniciarSesion() {
		return botonIniciarSesion;
	}

	/**
	 * Funcion que devuelve el boton de regisgtrarse de la vista
	 * @return botonRegistrarse: atributo de tipo JButton que representa el boton con nombre BotonRegistrarse
	 * el cual se devuelve en esta funcion
	 */
	public JButton getBotonRegistrarse() {
		return botonRegistrarse;
	}
	
	/**
	 * Funcion que delvuelve el titutlo 
	 * @return titulo: titulo de tipo JLabel el cual es el return de la funcion
	 */
	public JLabel getTitulo() {
		return titulo;
	}

	/**
	 * Funcion que devuelve el label del usuario en la vista
	 * @param usuarioLabel: JLabel del usuario el cual se devuelve
	 */
	public JLabel getUsuarioLabel() {
		return usuarioLabel;
	}

	/**
	 * Funcion que delvuelve el JLabel de la contraseña 
	 * @param serialVersionUID:
	 */
	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	/**
	 * Funcion que devuelve el JLabel de preguntaLabel
	 * @return preguntaLabel: atributo de tipo JLabel el cual indica el campo pregunta en 
	 * la vista
	 */
	public JLabel getPreguntaLabel() {
		return preguntaLabel;
	}

	/**
	 * Funcion que devuelve el texto introducido en el campo de usuario
	 * @return ususarioTextfield: atributo de tipo JTextField la cual contiene 
	 * el texto introducido por el usuario en el campo usuario
	 */
	public JTextField getUsuarioTextfield() {
		return usuarioTextfield;
	}

	/**
	 * Funcion que delvuelve texto que se ha introducido en el campo de 
	 * la contraseña
	 * @return passwordTextfield: atributo que guarda la contraseña introducida
	 */
	public JPasswordField getPasswordTextfield() {
		return passwordTextfield;
	}

	/**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */
	 public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonRegistrarse.addActionListener(c);
		 this.botonIniciarSesion.addActionListener(c);
	 }

}
