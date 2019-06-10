package vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.*;

import modelo.sistema.Sistema;
import modelo.usuario.*;

public class Perfil extends JPanel{
	JButton botonInicio;
	JButton botonNotificaciones;
	
	String nombre_autor;
	LocalDate fecha_nacimiento;
	String nombre_usuario;
	int numero_seguidores;
	int numero_seguidos;
	
	JLabel nombreAutor;
	JLabel fechaNacimiento;
	JLabel nombreUsuario;
	JLabel numeroSeguidores;
	JLabel numeroSeguidos;
	
	JButton botonCerrarSesion;
	JButton botonEliminarCuenta;
	JButton botonHacersePRO;
	public Perfil() {
		
		if(Sistema.sistema.getUsuarioActual() != null) {
			this.nombre_autor =  Sistema.sistema.getUsuarioActual().getNombreAutor();
			this.fecha_nacimiento =  Sistema.sistema.getUsuarioActual().getFechaNacimiento();
			this.nombre_usuario =  Sistema.sistema.getUsuarioActual().getNombreUsuario();
			this.numero_seguidores =  Sistema.sistema.getUsuarioActual().getSeguidores().size();
			this.numero_seguidos =  Sistema.sistema.getUsuarioActual().getSeguidos().size();
		}else { 
			
			//VALORES INICIALIZADOS UNICAMENTE PARA QUE NO HAYA PROBLEMAS AL CONSTRUIR LA VENTANA DE PERFIL,
			//ESTOS VALORES NO SE LLEGAN A VER NUNCA
			
			this.nombre_autor =  "";
			this.fecha_nacimiento =  LocalDate.now();
			this.nombre_usuario =  "";
			this.numero_seguidores = -1;
			this.numero_seguidos =  -1;
		}
		
		
		
		ImageIcon icono_corchea = new ImageIcon("src/vista/photo_default.jpg");

		
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
