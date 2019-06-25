package vista;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Clase en la que se implementa la vista de Registrarse con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class Registrarse extends JPanel{ //99.9% esta terminado
	
	private static final long serialVersionUID = 1L;
	public JButton botonInicio;
	public JButton botonIniciarSesion ;
	public JButton botonRegistrarse;
	
	private JLabel titulo;
	private JLabel usuarioLabel;
	private JLabel authorLabel;
	private JLabel birthLabel;
	private JLabel passwordLabel;
	private JLabel preguntaLabel;
	private JLabel formato;

	private JTextField usuarioTextfield;
	private JTextField authorTextfield ;
	private JTextField birthTextfield ;

	private JPasswordField passwordTextfield ;
	
	/**
	 * Constructor de la clase Perfil donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public Registrarse() {
		
		this.setBackground(new Color(40,159,211));
		this.botonInicio = new JButton("Inicio");
		this.titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		this.usuarioLabel = new JLabel("Nombre de usuario", SwingConstants.LEFT);
		this.usuarioTextfield = new JTextField(20);
		this.authorLabel = new JLabel("Nombre de autor", SwingConstants.LEFT);
		this.authorTextfield = new JTextField(10);
		this.birthLabel = new JLabel("Fecha nacimiento", SwingConstants.LEFT);
		this.formato = new JLabel("(dd/mm/aaaa)",SwingConstants.LEFT);
		this.birthTextfield = new JTextField(10);
		this.passwordLabel = new JLabel("Contraseña", SwingConstants.LEFT);
		this.passwordTextfield = new JPasswordField(20);
		this.botonIniciarSesion = new JButton("Iniciar Sesion");
		this.preguntaLabel = new JLabel("¿Ya tiene cuenta?", SwingConstants.CENTER);
		this.botonRegistrarse = new JButton("Registrarse");
		
		
		//Style changes
		Font tituloFont = new Font(titulo.getFont().getName(),Font.BOLD,22);
		Font usuarioLabelFont = new Font(usuarioLabel.getFont().getName(),Font.BOLD,usuarioLabel.getFont().getSize());
		Font passwordLabelFont = new Font(passwordLabel.getFont().getName(),Font.BOLD,passwordLabel.getFont().getSize());
		Font birthLabelFont = new Font(birthLabel.getFont().getName(),Font.BOLD,birthLabel.getFont().getSize());
		Font authorLabelFont = new Font(authorLabel.getFont().getName(),Font.BOLD,authorLabel.getFont().getSize());
		Font formatoLabelFont = new Font(formato.getFont().getName(),Font.BOLD,formato.getFont().getSize());

		titulo.setFont(tituloFont);
		titulo.setForeground(Color.yellow);
		usuarioLabel.setFont(usuarioLabelFont);
		passwordLabel.setFont(passwordLabelFont);
		birthLabel.setFont(birthLabelFont);
		authorLabel.setFont(authorLabelFont);
		formato.setFont(formatoLabelFont);
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		
		//Manual Constraints
		//x axis, y axis, width, height  

		botonInicio.setBounds(10, 10, 100, 30);
		titulo.setBounds(screenSize.width/2 - 300, 20, 600, 30);
		usuarioLabel.setBounds(screenSize.width/2 - 200, 90, 200, 40);
		usuarioTextfield.setBounds(screenSize.width/2,90,300, 40);
		authorLabel.setBounds(screenSize.width/2 - 200, 150, 200, 40);
		authorTextfield.setBounds(screenSize.width/2,150,300, 40);
		birthLabel.setBounds(screenSize.width/2  - 200, 210, 200, 40);
		formato.setBounds(screenSize.width/2  - 193, 230, 200, 40);
		birthTextfield.setBounds(screenSize.width/2,210,300, 40);
		passwordLabel.setBounds(screenSize.width/2  - 200, 270, 200, 40);
		passwordTextfield.setBounds(screenSize.width/2,270,300, 40);
		botonRegistrarse.setBounds(screenSize.width/2 - 75, 360, 150, 30);
		preguntaLabel.setBounds(screenSize.width/2 - 300, 410, 600, 30);
		botonIniciarSesion.setBounds(screenSize.width/2 - 75, 450, 150, 30);

		
		//We add all the components
		this.add(titulo);
		this.add(botonInicio);
		this.add(usuarioLabel);
		this.add(usuarioTextfield);
		this.add(authorLabel);
		this.add(authorTextfield);
		this.add(birthLabel);
		this.add(birthTextfield);
		this.add(passwordLabel);
		this.add(passwordTextfield);
		this.add(botonIniciarSesion);
		this.add(preguntaLabel);
		this.add(botonRegistrarse);
		this.add(formato);
		
	}
	
	/**
	 * Funcion que asgina a cada boton la accion que se pasa como argumento
	 * @param c: accion que se va a pasar a cada boton para que luego sse asigne el usuario determinado
	 */
	 public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonIniciarSesion.addActionListener(c);
		 this.botonRegistrarse.addActionListener(c);
	 }
		 
	/**
	 * Funcion que delvuelve el atributo serialVersionUID de la clase
	 * @param serialVersionUID:
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * Funcion la cual devuelve el boton de buscar con lo que se ha pulsado
	 * @return botonInicio: aributo que devuelve el boton de inicio para ir a la pantalla principal
	 */
	public JButton getBotonInicio() {
		return botonInicio;
	}

	/**
	 * Funcion la cual devuelve el boton de iniciar sesion
	 * @return botonIniciarSesion: atributo que devuelve el boton de iniciar sesion
	 */
	public JButton getBotonIniciarSesion() {
		return botonIniciarSesion;
	}

	/**
	 * Funcion la cual devuelve el boton de registrarse de la vista
	 * @return botonRegistrarse: atributo que devuelve el boton de buscar de la vista
	 */
	public JButton getBotonRegistrarse() {
		return botonRegistrarse;
	}

	/**
	 * Funcion que devuelve un JLabel con el titulo 
	 * @return titulo: argumento de tipo JLabel que devuelve el titulo
	 */
	public JLabel getTitulo() {
		return titulo;
	}

	/**
	 * Funcion que devuelve un JLabel con el nombre de usuario del usuario que se esta registrando
	 * @return usuarioLabel: argumento de tipo JLabel que devuelve el nombre de usuario
	 */
	public JLabel getUsuarioLabel() {
		return usuarioLabel;
	}

	/**
	 * Funcion que devuelve un JLabel con el nombre de autor del usuario que se va a registrarse
	 * @return authorLabel: argumento de tipo JLabel que devuelve el nombre de autor
	 */
	public JLabel getAuthorLabel() {
		return authorLabel;
	}
	
	/**
	 * Funcion que devuelve un JLabel con la fecha de nacimiento del usuario
	 * @return birthLabel: argumento de tipo JLabel que devuelve la fecha de nacimiento del usuario
	 */
	public JLabel getBirthLabel() {
		return birthLabel;
	}

	/**
	 * Funcion que devuelve un JLabel con la contraseña
	 * @return passwordLabel: argumento de tipo JLabel que devuelve la contraseña
	 */
	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	/**
	 * Funcion que devuelve un JLabel con el label de pregunta 
	 * @return preguntaLabel: argumento de tipo JLabel que devuelve la pregunta realizada
	 */
	public JLabel getPreguntaLabel() {
		return preguntaLabel;
	}

	/**
	 * Funcion que devuelve el texto que se ha introducido para 
	 * el campo de nombre de usuario
	 * @return usuarioTextflied: texto en el cual el usuario
	 * ha escrito en el campo de nombre de usuario
	 */
	public JTextField getUsuarioTextfield() {
		return usuarioTextfield;
	}

	/**
	 * Funcion que devuelve el texto que se ha introducido para 
	 * el campo de nombre de autor
	 * @return authorTextflied: texto en el cual el usuario
	 * ha escrito en el campo de nombre de autor
	 */
	public JTextField getAuthorTextfield() {
		return authorTextfield;
	}
	
	/**
	 * Funcion que devuelve el texto del campo de la fecha de nacimiento
	 * @return birthTextflied: texto en el cual el usuario
	 * ha escrito en el cuadro de fecha de nacimiento
	 */
	public JTextField getBirthTextfield() {
		return birthTextfield;
	}

	/**
	 * Funcion que devuelve el textField de la contraseña en la vista
	 * @return passwordTextfield: atributo de tipo JPasswordField que devuelve el textfield del
	 * campo contraseña
	 */
	public JPasswordField getPasswordTextfield() {
		return passwordTextfield;
	}

	/**
	 * Funcion que limpiar el la ventana dejando en blanco los los TextFields 
	 */
	public void limpiarVentana(){
		this.passwordTextfield.setText("");
		this.authorTextfield.setText("");
		this.birthTextfield.setText("");
		this.usuarioTextfield.setText("");
	}
	
	 
	 
}

