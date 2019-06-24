package vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;

import modelo.notificacion.Notificacion;
import modelo.sistema.Sistema;
import modelo.usuario.*;

/**
 * Clase en la que se implementa la vista Ventana con todo
 * lo necesario para cumplir los requisitos impuestos
 */
public class Perfil extends JPanel{
	
	private static final long serialVersionUID = 1L;
	public JButton botonInicio;
	public JButton botonCerrarSesion;
	public JButton botonEliminarCuenta;
	public JButton botonHacersePRO;
	public JButton seleccionarNotificacion;
	 
	
	private String nombre_autor;
	private LocalDate fecha_nacimiento;
	private String nombre_usuario;
	private int numero_seguidores;
	private int numero_seguidos;
	private int reproducciones;
	private int restantes;
	
	private JLabel nombreAutor;
	private JLabel fechaNacimiento;
	private JLabel nombreUsuario;
	private JLabel numeroSeguidores;
	private JLabel numeroSeguidos;
	private JLabel susNotificaciones;
	private JLabel reproRestantes;
	private JLabel susSeguidores;
	private JLabel susSeguidos;
	
	private DefaultListModel<String> model1;
	private DefaultListModel<String> model2;
	private DefaultListModel<String> model3;


	private Notificacion[] lasNotificaciones;
	private JList<String> lista_notificaciones;
	private JScrollPane notificaciones;
	private Dimension screenSize;
	
	
	private Usuario[] losSeguidores;
	private JList<String> lista_seguidores;
	private JScrollPane seguidores;
	private JButton seleccionarSeguidor;
	
	
	private Usuario[] losSeguidos;
	private JList<String> lista_seguidos;
	private JScrollPane seguidos;	
	private JButton seleccionarSeguido;
	
	/**
	 * Constructor de la clase Ventana donde se inicializan
	 * todos los atributos con lo valores correspondientes 
	 */
	public Perfil() {
		
		
		model1 = new DefaultListModel<>();
		lista_notificaciones = new JList<String>(model1);
		notificaciones = new JScrollPane(lista_notificaciones);
		susNotificaciones = new JLabel("Sus Notificaciones",  SwingConstants.CENTER);
		seleccionarNotificacion = new JButton("Elegir Notificacion");
		
		model2 = new DefaultListModel<>();
		lista_seguidores= new JList<String>(model2);
		seguidores = new JScrollPane(lista_seguidores);
		susSeguidores = new JLabel("Sus Seguidores",  SwingConstants.CENTER);
		seleccionarSeguidor = new JButton("Elegir Seguidor");
		
		model3 = new DefaultListModel<>();
		lista_seguidos = new JList<String>(model3);
		seguidos = new JScrollPane(lista_seguidos);
		susSeguidos = new JLabel("Sus Seguidos",  SwingConstants.CENTER);
		seleccionarSeguido = new JButton("Elegir Seguido");
		
	
		Font susNotificacionesFont = new Font(susNotificaciones.getFont().getName(), Font.BOLD, 16);
		
		notificaciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		this.susNotificaciones.setFont(susNotificacionesFont);
		
			
		//VALORES INICIALIZADOS UNICAMENTE PARA QUE NO HAYA PROBLEMAS AL CONSTRUIR LA VENTANA DE PERFIL,
		//ESTOS VALORES NO SE LLEGAN A VER NUNCA, PORQUE ESTA VISTA APARECE PARA AQUELLOS QUE LA TENDRAN QUE VER, ADMIN Y USUARIO REGISTRADO
		
		this.nombre_autor =  "";
		this.fecha_nacimiento =  LocalDate.now();
		this.nombre_usuario =  "";
		this.numero_seguidores = -1;
		this.numero_seguidos =  -1;
		this.reproducciones = -1;
		this.restantes = -1;
		
		
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
		this.reproRestantes = new JLabel("Num Reproducciones restantes:\t\t" + restantes,SwingConstants.LEFT);

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
		Font numeroRestantesFont = new Font(reproRestantes.getFont().getName(),Font.BOLD,reproRestantes.getFont().getSize());
		Font susSeguidoresFont = new Font(susSeguidores.getFont().getName(),Font.BOLD,susSeguidores.getFont().getSize());
		Font susSeguidosFont = new Font(susSeguidos.getFont().getName(),Font.BOLD,susSeguidos.getFont().getSize());

		datosUsuario.setFont(datosUsuarioFont);
		nombreAutor.setFont(nombreAutorFont);
		fechaNacimiento.setFont(fechaNacimientoFont);
		nombreUsuario.setFont(nombreUsuarioFont);
		numeroSeguidores.setFont(numeroSeguidoresFont);
		numeroSeguidos.setFont(numeroSeguidosFont);
		reproRestantes.setFont(numeroRestantesFont);
		susSeguidores.setFont(susSeguidoresFont);
		susSeguidos.setFont(susSeguidosFont);
		
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
		reproRestantes.setBounds(screenSize.width/2 + 350, 160, 300, 30);
		
		susSeguidores.setBounds(screenSize.width/2 + 390, 190, 150, 30);
		seguidores.setBounds(screenSize.width/2 + 350, 220, 230, 70);
		seleccionarSeguidor.setBounds(screenSize.width/2 + 390, 300, 150, 30);
	
		susSeguidos.setBounds(screenSize.width/2 + 390, 350, 150, 30);
		seguidos.setBounds(screenSize.width/2 + 350, 380, 230, 70);
		seleccionarSeguido.setBounds(screenSize.width/2 + 390, 460, 150, 30);

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
		this.add(reproRestantes);
		this.add(susSeguidores);
		this.add(susSeguidos);
		this.add(seguidores);
		this.add(seguidos);
		this.add(seleccionarSeguidor);
		this.add(seleccionarSeguido);

		
	}
	
	/**
	 * Funcion que hace visibles o invisibles los diferentes botones que 
	 * va a poder usar usuarios 
	 */
	public void setUsuario() {
		this.botonEliminarCuenta.setVisible(true);
		this.botonHacersePRO.setVisible(true);
		this.numeroSeguidos.setVisible(true);
		this.numeroSeguidores.setVisible(true);
		this.botonCerrarSesion.setBounds(screenSize.width/2 + 30, 410, 150, 30);
		this.reproRestantes.setVisible(true);
		this.seleccionarSeguido.setVisible(true);
		this.seleccionarSeguidor.setVisible(true);
		this.seguidores.setVisible(true);
		this.seguidos.setVisible(true);
		this.susSeguidores.setVisible(true);
		this.susSeguidos.setVisible(true);
		this.reproRestantes.setVisible(true);
	}
			
	public void setAdministrador() {
		this.numeroSeguidos.setVisible(false);
		this.numeroSeguidores.setVisible(false);
		this.botonEliminarCuenta.setVisible(false);
		this.botonHacersePRO.setVisible(false);
		this.botonCerrarSesion.setBounds(screenSize.width/2 , 310, 150, 30);
		this.reproRestantes.setVisible(false);
		this.seleccionarSeguido.setVisible(false);
		this.seleccionarSeguidor.setVisible(false);
		this.seguidores.setVisible(false);
		this.seguidos.setVisible(false);
		this.susSeguidores.setVisible(false);
		this.susSeguidos.setVisible(false);

	}
	
	// método para asignar un controlador al botón
	public void setControlador(ActionListener c) {
		 this.botonInicio.addActionListener(c);
		 this.botonCerrarSesion.addActionListener(c);
		 this.botonEliminarCuenta.addActionListener(c);
		 this.botonHacersePRO.addActionListener(c);
		 this.seleccionarNotificacion.addActionListener(c);
		 this.seleccionarSeguido.addActionListener(c);
		 this.seleccionarSeguidor.addActionListener(c);
	}
	
	public void setUsuarioPremium() {
		this.numeroSeguidos.setVisible(true);
		this.numeroSeguidores.setVisible(true);
		this.botonEliminarCuenta.setVisible(true);
		this.botonCerrarSesion.setBounds(screenSize.width/2 + 30, 410, 150, 30);
		this.seleccionarSeguido.setVisible(true);
		this.seleccionarSeguidor.setVisible(true);
		this.seguidores.setVisible(true);
		this.seguidos.setVisible(true);
		this.susSeguidores.setVisible(true);
		this.susSeguidos.setVisible(true);
		this.botonHacersePRO.setVisible(false);	
		this.reproRestantes.setVisible(false);
		this.botonCerrarSesion.setBounds(screenSize.width/2 + 30, 410, 150, 30);
	}
		 
	public void setInformacion(Usuario usuario) {
		this.nombreAutor.setText("Nombre de autor:\t\t " + usuario.getNombreAutor());
		this.fechaNacimiento.setText("F. de nacimiento:\t\t" + usuario.getFechaNacimiento());
		this.nombreUsuario.setText("Nombre de usuario:\t\t" + usuario.getNombreUsuario());
		this.numeroSeguidores.setText("Seguidores/Seguidos:\t\t"+ usuario.getSeguidores().size() + "/" +  usuario.getSeguidos().size());
		this.numeroSeguidos.setText("Num Reproducciones de tu contenido:\t\t" + usuario.getNumeroReproducciones());
		this.reproRestantes.setText("Num Reproducciones restantes:\t\t" + (Sistema.sistema.getMaxReproduccionesUsuariosNoPremium() - usuario.getContenidoEscuchadoSinSerPremium()));
		this.actualizarNotificaciones();
		this.actualizarSeguidores();
		this.actualizarSeguidos();
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getBotonInicio() {
		return botonInicio;
	}

	public JButton getBotonCerrarSesion() {
		return botonCerrarSesion;
	}

	public JButton getBotonEliminarCuenta() {
		return botonEliminarCuenta;
	}

	public JButton getBotonHacersePRO() {
		return botonHacersePRO;
	}

	public JButton getSeleccionarNotificacion() {
		return seleccionarNotificacion;
	}

	public String getNombre_autor() {
		return nombre_autor;
	}

	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public String getNombre_usuario() {
		return nombre_usuario;
	}

	public int getNumero_seguidores() {
		return numero_seguidores;
	}

	public int getNumero_seguidos() {
		return numero_seguidos;
	}

	public int getReproducciones() {
		return reproducciones;
	}

	public JLabel getNombreAutor() {
		return nombreAutor;
	}

	public JLabel getFechaNacimiento() {
		return fechaNacimiento;
	}

	public JLabel getNombreUsuario() {
		return nombreUsuario;
	}

	public JLabel getNumeroSeguidores() {
		return numeroSeguidores;
	}

	public JLabel getNumeroSeguidos() {
		return numeroSeguidos;
	}

	public JLabel getSusNotificaciones() {
		return susNotificaciones;
	}

	public DefaultListModel<String> getModel1() {
		return model1;
	}

	public Notificacion[] getLasNotificaciones() {
		return lasNotificaciones;
	}

	public JList<String> getLista_notificaciones() {
		return lista_notificaciones;
	}

	public JScrollPane getNotificaciones() {
		return notificaciones;
	}

	public Dimension getScreenSize() {
		return screenSize;
	}
	
	public Usuario[] getSeguidores() {
		return losSeguidores;
	}
	
	public Usuario[] getSeguidos() {
		return losSeguidos;
	}
	
	public JList<String> getLista_seguidores(){
		return lista_seguidores;
	}
	
	public JList<String> getLista_seguidos(){
		return lista_seguidos;
	}

	public void actualizarNotificaciones() {
		model1.clear();
		lasNotificaciones = Sistema.sistema.getUsuarioActual().getNotificacionesTotales().toArray(new Notificacion[Sistema.sistema.getUsuarioActual().getNotificacionesTotales().size()]);
		for(int i=0; i < lasNotificaciones.length; i++) {
			model1.addElement("Emisor: " + lasNotificaciones[i].getEmisor().getNombreUsuario() + " // Mensaje: " + lasNotificaciones[i].getMensaje() + " // Receptor: " + lasNotificaciones[i].getReceptor().getNombreUsuario());
		}
	}
	
	public void actualizarSeguidores() {
		model2.clear();
		losSeguidores = Sistema.sistema.getUsuarioActual().getSeguidores().toArray(new Usuario[Sistema.sistema.getUsuarioActual().getSeguidores().size()]);
		for(int i=0; i < losSeguidores.length; i++) {
			model2.addElement("Ususario: " + losSeguidores[i].getNombreUsuario() + " // Autor: " + losSeguidores[i].getNombreAutor());
		}
	}
	
	public void actualizarSeguidos() {
		model3.clear();
		losSeguidos = Sistema.sistema.getUsuarioActual().getSeguidos().toArray(new Usuario[Sistema.sistema.getUsuarioActual().getSeguidos().size()]);
		for(int i=0; i < losSeguidos.length; i++) {
			model3.addElement("Ususario: " + losSeguidos[i].getNombreUsuario() + " // Autor: " + losSeguidos[i].getNombreAutor());
		}
	}

}
