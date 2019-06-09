package vista;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Registrarse extends JPanel{
	JButton botonInicio;
	JLabel titulo;
	JLabel usuarioLabel;
	public JTextField usuarioTextfield;
	JLabel authorLabel;
	public JTextField authorTextfield ;
	JLabel birthLabel;
	JTextField birthTextfield ;
	JLabel passwordLabel;
	public JPasswordField passwordTextfield ;
	JButton botonIniciarSesion ;
	JLabel preguntaLabel;
	JButton botonRegistrarse;
	
	public Registrarse() {
		
		this.setBackground(new Color(40,159,211));
		this.botonInicio = new JButton("Inicio");
		this.titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		this.usuarioLabel = new JLabel("Nombre de usuario", SwingConstants.LEFT);
		this.usuarioTextfield = new JTextField(20);
		this.authorLabel = new JLabel("Nombre de autor", SwingConstants.LEFT);
		this.authorTextfield = new JTextField(10);
		this.birthLabel = new JLabel("Fecha de nacimiento", SwingConstants.LEFT);
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

		titulo.setFont(tituloFont);
		titulo.setForeground(Color.yellow);
		usuarioLabel.setFont(usuarioLabelFont);
		passwordLabel.setFont(passwordLabelFont);
		birthLabel.setFont(birthLabelFont);
		authorLabel.setFont(authorLabelFont);

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
		
		/*
		layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		      .addComponent(botonInicio)
		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
		           .addComponent(titulo)
		           .addComponent(campo))
		);
		layout.setVerticalGroup(
		   layout.createSequentialGroup()
		      .addComponent(botonInicio)
		      .addComponent(titulo)
		      .addComponent(campo)
		);
		*/
	}
	
	public void limpiarVentana(){
		this.passwordTextfield.setText("");
		this.authorTextfield.setText("");
		this.birthTextfield.setText("");
		this.usuarioTextfield.setText("");
	}
	
	 // método para asignar un controlador al botón
	 public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonIniciarSesion.addActionListener(c);
		 this.botonRegistrarse.addActionListener(c);
	 }
	 
}

