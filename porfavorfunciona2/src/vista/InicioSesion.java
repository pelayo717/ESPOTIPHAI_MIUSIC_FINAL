package vista;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class InicioSesion extends JPanel{
	/**
	 * 
	 */
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
	
	
	 public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getBotonInicio() {
		return botonInicio;
	}

	public JButton getBotonIniciarSesion() {
		return botonIniciarSesion;
	}

	public JButton getBotonRegistrarse() {
		return botonRegistrarse;
	}

	public JLabel getTitulo() {
		return titulo;
	}

	public JLabel getUsuarioLabel() {
		return usuarioLabel;
	}

	public JLabel getPasswordLabel() {
		return passwordLabel;
	}

	public JLabel getPreguntaLabel() {
		return preguntaLabel;
	}

	public JTextField getUsuarioTextfield() {
		return usuarioTextfield;
	}

	public JPasswordField getPasswordTextfield() {
		return passwordTextfield;
	}

	// método para asignar un controlador al botón
	 public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonRegistrarse.addActionListener(c);
		 this.botonIniciarSesion.addActionListener(c);
	 }

}
