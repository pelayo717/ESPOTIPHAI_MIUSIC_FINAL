package Grafic;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;


public class InicioSesion extends JPanel{
	JButton botonInicio;
	JLabel titulo ;
	JLabel usuarioLabel;
	JTextField usuarioTextfield;
	JLabel passwordLabel;
	JTextField passwordTextfield;
	JButton botonIniciarSesion;
	JLabel preguntaLabel;
	JButton botonRegistrarse;;
	
	public InicioSesion() {
		
		this.setBackground(new Color(40,159,211));
		this.botonInicio = new JButton("Inicio");
		this.titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		this.usuarioLabel = new JLabel("Introduzca nombre de usuario", SwingConstants.CENTER);
		this.usuarioTextfield = new JTextField(10);
		this.passwordLabel = new JLabel("Introduzca su contraseña", SwingConstants.CENTER);
		this.passwordTextfield = new JTextField(10);
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

		
		//Manual Constraints
		//x axis, y axis, width, height  

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
	
	 // método para asignar un controlador al botón
	 public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonRegistrarse.addActionListener(c);
	 }

}
