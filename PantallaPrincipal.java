package ESPOTIPHAI_MIUSIC_FINAL;

import java.awt.*;

import javax.swing.*;

public class PantallaPrincipal extends JPanel{
	public PantallaPrincipal() {
		
		this.setBackground(new Color(40,159,211));
		JButton botonInicio = new JButton("Inicio");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		JLabel usuarioLabel = new JLabel("Nombre de usuario", SwingConstants.LEFT);
		JTextField usuarioTextfield = new JTextField(10);
		JLabel authorLabel = new JLabel("Nombre de autor", SwingConstants.LEFT);
		JTextField authorTextfield = new JTextField(10);
		JLabel birthLabel = new JLabel("Fecha de nacimiento", SwingConstants.LEFT);
		JTextField birthTextfield = new JTextField(10);
		JLabel passwordLabel = new JLabel("Contraseña", SwingConstants.LEFT);
		JTextField passwordTextfield = new JTextField(10);
		JButton botonIniciarSesion = new JButton("Iniciar Sesion");
		JLabel preguntaLabel = new JLabel("¿Ya tiene cuenta?", SwingConstants.CENTER);
		JButton botonRegistrarse = new JButton("Registrarse");
		
		
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
		
		
		
		
		//Manual Constraints
		//x axis, y axis, width, height  

		botonInicio.setBounds(10, 10, 100, 30);
		titulo.setBounds(100, 20, 600, 30);
		usuarioLabel.setBounds(30, 90, 200, 30);
		usuarioTextfield.setBounds(185,90,300, 40);
		authorLabel.setBounds(30, 150, 200, 30);
		authorTextfield.setBounds(185,150,300, 40);
		birthLabel.setBounds(30, 210, 200, 30);
		birthTextfield.setBounds(185,210,300, 40);
		passwordLabel.setBounds(30, 280, 200, 30);
		passwordTextfield.setBounds(185,280,300, 40);
		botonRegistrarse.setBounds(325, 360, 150, 30);
		preguntaLabel.setBounds(100, 410, 600, 30);
		botonIniciarSesion.setBounds(325, 450, 150, 30);

		
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
		
	}

}
