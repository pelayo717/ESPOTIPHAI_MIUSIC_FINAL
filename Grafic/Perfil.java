package ESPOTIPHAI_MIUSIC_FINAL.Grafic;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import ESPOTIPHAI_MIUSIC_FINAL.com.ESPOTIPHAI_MIUSIC.sistema.usuario.Usuario;

public class Perfil extends JPanel{
	JButton botonInicio;
	JButton botonNotificaciones;
	
	String nombre_autor;
	String fecha_nacimiento;
	String nombre_usuario;
	String numero_seguidores;
	String numero_seguidos;
	
	JLabel nombreAutor;
	JLabel fechaNacimiento;
	JLabel nombreUsuario;
	JLabel numeroSeguidores;
	JLabel numeroSeguidos;
	
	JButton botonCerrarSesion;
	JButton botonEliminarCuenta;
	JButton botonHacersePRO;
	public Perfil() {
		
		this.nombre_autor =  "MC Pelayo";
		this.fecha_nacimiento =  "7/12/1996";
		this.nombre_usuario =  "Pelayo Sanchez";
		this.numero_seguidores =  "2000";
		this.numero_seguidos =  "4";
		
		ImageIcon icono_corchea = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/Grafic/photo_default.jpg");

		
		this.setBackground(new Color(40,159,211));
		this.botonInicio = new JButton("Inicio");
		this.botonNotificaciones = new JButton("Notificaciones");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		JLabel datosUsuario = new JLabel("Datos del usuario:" , SwingConstants.CENTER);
		this.nombreAutor = new JLabel("Nombre de autor:\t\t " + nombre_autor, SwingConstants.LEFT);
		this.fechaNacimiento = new JLabel("F. de nacimiento:\t\t" + fecha_nacimiento, SwingConstants.LEFT);
		this.nombreUsuario = new JLabel("Nombre de usuario:\t\t" + nombre_usuario, SwingConstants.LEFT);
		this.numeroSeguidores = new JLabel("Numero de seguidores:\t\t"+ numero_seguidores, SwingConstants.LEFT);
		this.numeroSeguidos = new JLabel("Numero de seguidos:\t\t" + numero_seguidos, SwingConstants.LEFT);
		
		this.botonCerrarSesion = new JButton("Cerrar Sesion");
		this.botonEliminarCuenta = new JButton("Eliminar Cuenta");
		this.botonHacersePRO = new JButton("Hacerse PRO");
		
		
		
		//Style changes
		Font tituloFont = new Font(titulo.getFont().getName(),Font.BOLD,22);
		Font datosUsuarioFont = new Font(datosUsuario.getFont().getName(),Font.BOLD,16);
		Font nombreAutorFont = new Font(nombreAutor.getFont().getName(),Font.BOLD,nombreAutor.getFont().getSize());
		Font fechaNacimientoFont = new Font(fechaNacimiento.getFont().getName(),Font.BOLD,fechaNacimiento.getFont().getSize());
		Font nombreUsuarioFont = new Font(nombreUsuario.getFont().getName(),Font.BOLD,nombreUsuario.getFont().getSize());
		Font numeroSeguidoresFont = new Font(numeroSeguidores.getFont().getName(),Font.BOLD,numeroSeguidores.getFont().getSize());
		Font numeroSeguidosFont = new Font(numeroSeguidos.getFont().getName(),Font.BOLD,numeroSeguidos.getFont().getSize());

		datosUsuario.setFont(datosUsuarioFont);
		nombreAutor.setFont(nombreAutorFont);
		fechaNacimiento.setFont(fechaNacimientoFont);
		nombreUsuario.setFont(nombreUsuarioFont);
		numeroSeguidores.setFont(numeroSeguidoresFont);
		numeroSeguidos.setFont(numeroSeguidosFont);

		titulo.setFont(tituloFont);
		titulo.setForeground(Color.yellow);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height  
		botonInicio.setBounds(10, 10, 150, 30);
		botonNotificaciones.setBounds(10, 50, 150, 30);
		titulo.setBounds(screenSize.width/2 - 300, 50, 600, 30);
		datosUsuario.setBounds(screenSize.width/2,100,300, 40);
		nombreAutor.setBounds(screenSize.width/2 , 160, 300, 30);
		fechaNacimiento.setBounds(screenSize.width/2 ,210,300, 30);
		nombreUsuario.setBounds(screenSize.width/2 , 260, 300, 30);
		numeroSeguidores.setBounds(screenSize.width/2 , 310, 300, 30);
		numeroSeguidos.setBounds(screenSize.width/2, 360, 300, 30);
		botonCerrarSesion.setBounds(screenSize.width/2 + 75, 410, 150, 30);
		botonEliminarCuenta.setBounds(screenSize.width/2 + 75, 460, 150, 30);
		botonHacersePRO.setBounds(screenSize.width/2 + 75, 510, 150, 30);

		imagen_reproduccion.setBounds(screenSize.width/2 - 320, 160, 300, 300);


		
		//We add all the components
		this.add(botonInicio);
		this.add(botonNotificaciones);
		this.add(titulo);
		this.add(datosUsuario);
		this.add(nombreAutor);
		this.add(fechaNacimiento);
		this.add(nombreUsuario);
		this.add(numeroSeguidores);
		this.add(numeroSeguidos);
		this.add(botonCerrarSesion);
		this.add(botonEliminarCuenta);
		this.add(botonHacersePRO);
		this.add(imagen_reproduccion);

		
	}
	
	// método para asignar un controlador al botón
	public void setControlador(ActionListener c) {
			 this.botonInicio.addActionListener(c);
			 this.botonNotificaciones.addActionListener(c);
			 this.botonCerrarSesion.addActionListener(c);
			 this.botonEliminarCuenta.addActionListener(c);
			 this.botonHacersePRO.addActionListener(c);
	}
		 
	public void setInformacion(Usuario usuario) {
		this.nombreAutor.setText("Nombre de autor:\t\t " + usuario.getNombreAutor());
		this.fechaNacimiento.setText("F. de nacimiento:\t\t" + "7/12/1996");
		this.nombreUsuario.setText("Nombre de usuario:\t\t" + usuario.getNombreUsuario());
		this.numeroSeguidores.setText("Numero de seguidores:\t\t"+ usuario.getSeguidores().size());
		this.numeroSeguidos.setText("Numero de seguidos:\t\t" + usuario.getSeguidos().size());
	}
}
