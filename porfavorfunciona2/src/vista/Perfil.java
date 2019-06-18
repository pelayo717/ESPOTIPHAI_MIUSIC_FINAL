package vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;

import modelo.notificacion.Notificacion;
import modelo.sistema.Sistema;
import modelo.usuario.*;

public class Perfil extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botonInicio;
	private JButton botonCerrarSesion;
	private JButton botonEliminarCuenta;
	private JButton botonHacersePRO;
	private JButton seleccionarNotificacion;
	 
	
	private String nombre_autor;
	private LocalDate fecha_nacimiento;
	private String nombre_usuario;
	private int numero_seguidores;
	private int numero_seguidos;
	private int reproducciones;
	
	private JLabel nombreAutor;
	private JLabel fechaNacimiento;
	private JLabel nombreUsuario;
	private JLabel numeroSeguidores;
	private JLabel numeroSeguidos;
	private JLabel susNotificaciones;
	
	private DefaultListModel<String> model1;

	private Notificacion[] lasNotificaciones;

	private JList<String> lista_notificaciones;

	private JScrollPane notificaciones;
	
	private Dimension screenSize;
	
	public Perfil() {
		
		
		model1 = new DefaultListModel<>();
		
		
		lista_notificaciones = new JList<String>(model1);
		
		
		notificaciones = new JScrollPane(lista_notificaciones);
		
		
		susNotificaciones = new JLabel("Sus Notificaciones",  SwingConstants.CENTER);
		
		
		seleccionarNotificacion = new JButton("Elegir notificacion");
		
	
		Font susNotificacionesFont = new Font(susNotificaciones.getFont().getName(), Font.BOLD, 16);
		
		notificaciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.susNotificaciones.setFont(susNotificacionesFont);
		
			
		//VALORES INICIALIZADOS UNICAMENTE PARA QUE NO HAYA PROBLEMAS AL CONSTRUIR LA VENTANA DE PERFIL,
		//ESTOS VALORES NO SE LLEGAN A VER NUNCA, PORQUE ESTA VISTA APARECE PARA AQUELLOS QUE LA TENDRAN QUE VER
		
		this.nombre_autor =  "";
		this.fecha_nacimiento =  LocalDate.now();
		this.nombre_usuario =  "";
		this.numero_seguidores = -1;
		this.numero_seguidos =  -1;
		this.reproducciones = -1;
		
		
		
		ImageIcon icono_corchea = new ImageIcon("src/vista/photo_default.jpg");

		
		this.setBackground(new Color(40,159,211));
		this.botonInicio = new JButton("Inicio");
		JLabel titulo = new JLabel("ESPOTIPHAIMUSIC", SwingConstants.CENTER);
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		JLabel datosUsuario = new JLabel("Datos del usuario:" , SwingConstants.CENTER);
		this.nombreAutor = new JLabel("Nombre de autor:\t\t " + nombre_autor, SwingConstants.LEFT);
		this.fechaNacimiento = new JLabel("F. de nacimiento:\t\t" + fecha_nacimiento, SwingConstants.LEFT);
		this.nombreUsuario = new JLabel("Nombre de usuario:\t\t" + nombre_usuario, SwingConstants.LEFT);
		this.numeroSeguidores = new JLabel("Seguidores/Seguidos:\t\t"+ numero_seguidores + "/" + numero_seguidos, SwingConstants.LEFT);
		this.numeroSeguidos = new JLabel("Num Reproducciones de tu contenido:\t\t" + reproducciones, SwingConstants.LEFT);
		
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
		
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		 
		botonInicio.setBounds(10, 10, 150, 30);
		titulo.setBounds(screenSize.width/2 - 300, 50, 600, 30);
		datosUsuario.setBounds(screenSize.width/2,100,300, 40);
		nombreAutor.setBounds(screenSize.width/2 , 160, 300, 30);
		fechaNacimiento.setBounds(screenSize.width/2 ,210,300, 30);
		nombreUsuario.setBounds(screenSize.width/2 , 260, 300, 30);
		numeroSeguidores.setBounds(screenSize.width/2 , 310, 300, 30);
		numeroSeguidos.setBounds(screenSize.width/2, 360, 500, 30);
		botonCerrarSesion.setBounds(screenSize.width/2 + 30, 410, 150, 30);
		botonEliminarCuenta.setBounds(screenSize.width/2 + 30, 460, 150, 30);
		botonHacersePRO.setBounds(screenSize.width/2 + 30, 510, 150, 30);

		imagen_reproduccion.setBounds(screenSize.width/2 - 320, 160, 300, 300);

		susNotificaciones.setBounds(screenSize.width/2 - 260, 540, 200, 30);
		
		notificaciones.setBounds(screenSize.width/2 - 450, 580, 550, 100);
		
		seleccionarNotificacion.setBounds(screenSize.width/2 - 260, 690, 200, 30);

		
		//We add all the components
		this.add(botonInicio);
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
		this.add(susNotificaciones);
		this.add(notificaciones);
		this.add(seleccionarNotificacion);

		
	}
	
	public void setUsuario() {
		this.botonEliminarCuenta.setVisible(true);
		this.botonHacersePRO.setVisible(true);
		this.numeroSeguidos.setVisible(true);
		this.numeroSeguidores.setVisible(true);
		this.botonCerrarSesion.setBounds(screenSize.width/2 + 30, 410, 150, 30);
	}
			
	public void setAdministrador() {
		this.numeroSeguidos.setVisible(false);
		this.numeroSeguidores.setVisible(false);
		this.botonEliminarCuenta.setVisible(false);
		this.botonHacersePRO.setVisible(false);
		this.botonCerrarSesion.setBounds(screenSize.width/2 , 310, 150, 30);
	}
	
	// método para asignar un controlador al botón
	public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonCerrarSesion.addActionListener(c);
		 this.botonEliminarCuenta.addActionListener(c);
		 this.botonHacersePRO.addActionListener(c);
		 this.seleccionarNotificacion.addActionListener(c);
	}
	
	public void setUsuarioPremium() {
		this.numeroSeguidos.setVisible(true);
		this.numeroSeguidores.setVisible(true);
		this.botonEliminarCuenta.setVisible(true);
		this.botonHacersePRO.setVisible(false);	
		this.botonCerrarSesion.setBounds(screenSize.width/2 + 30, 410, 150, 30);
	}
		 
	public void setInformacion(Usuario usuario) {
		this.nombreAutor.setText("Nombre de autor:\t\t " + usuario.getNombreAutor());
		this.fechaNacimiento.setText("F. de nacimiento:\t\t" + usuario.getFechaNacimiento());
		this.nombreUsuario.setText("Nombre de usuario:\t\t" + usuario.getNombreUsuario());
		this.numeroSeguidores.setText("Seguidores/Seguidos:\t\t"+ usuario.getSeguidores().size() + "/" +  usuario.getSeguidos().size());
		this.numeroSeguidos.setText("Num Reproducciones de tu contenido:\t\t" + usuario.getNumeroReproducciones());
		this.actualizarNotificaciones();
	}
	
	public void actualizarNotificaciones() {
		model1.clear();
		lasNotificaciones = Sistema.sistema.getUsuarioActual().getNotificacionesTotales().toArray(new Notificacion[Sistema.sistema.getUsuarioActual().getNotificacionesTotales().size()]);
		for(int i=0; i < lasNotificaciones.length; i++) {
			model1.addElement("Emisor: " + lasNotificaciones[i].getEmisor().getNombreUsuario() + " // Mensaje: " + lasNotificaciones[i].getMensaje() + " // Receptor: " + lasNotificaciones[i].getReceptor().getNombreUsuario());
		}
	}

}
