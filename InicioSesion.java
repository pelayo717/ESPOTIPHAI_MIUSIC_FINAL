package ESPOTIPHAI_MIUSIC_FINAL;
import java.awt.*;

import javax.swing.*;


public class InicioSesion extends JPanel{
	public InicioSesion() {
		
		this.setBackground(new Color(40,159,211));
		JButton botonInicio = new JButton("Inicio");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		JLabel usuarioLabel = new JLabel("Introduzca nombre de usuario", SwingConstants.CENTER);
		JTextField usuarioTextfield = new JTextField(10);
		JLabel passwordLabel = new JLabel("Introduzca su contraseña", SwingConstants.CENTER);
		JTextField passwordTextfield = new JTextField(10);
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		JLabel preguntaLabel = new JLabel("¿Sin registrarse?", SwingConstants.CENTER);
		JButton botonRegistrarse = new JButton("Registrarse");
		
		
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
		
		
		
		
		//Manual Constraints
		//x axis, y axis, width, height  

		botonInicio.setBounds(10, 10, 100, 30);
		titulo.setBounds(100, 20, 600, 30);
		usuarioLabel.setBounds(100, 90, 600, 30);
		usuarioTextfield.setBounds(250,120,300, 40);
		passwordLabel.setBounds(100, 180, 600, 30);
		passwordTextfield.setBounds(250,210,300, 40);
		botonIniciarSesion.setBounds(325, 250, 150, 30);
		preguntaLabel.setBounds(100, 310, 600, 30);
		botonRegistrarse.setBounds(325, 340, 150, 30);

		
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

}
