package ESPOTIPHAI_MIUSIC_FINAL;

import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Date;

import javax.swing.*;

public class ReproducirCancion extends PantallaPrincipal {

	private String titulo;
	private int anyo;
	private String autor;
	private int duracion;
	
	public ReproducirCancion(String arg1,int arg2,String arg3,int arg4) {
		super();
		String[] names = { "Bill", "Geri", "Greg", "Jean", "Kirk", "Phillip", "Susan","Bill", "Geri", "Greg", "Jean", "Kirk", "Phillip", "Susan"};
		this.titulo = arg1;
		this.anyo = arg2;
		this.autor = arg3;
		this.duracion = arg4;
		
		//Declaracion
		ImageIcon icono_corchea = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/photo_default.jpg");
		ImageIcon icono_reproducir = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/play.png");
		ImageIcon icono_parar = new ImageIcon("src/ESPOTIPHAI_MIUSIC_FINAL/pause.png");
		
	    
	    
		JLabel imagen_reproduccion = new JLabel("",icono_corchea,JLabel.CENTER);
		JButton imagen_play = new JButton();
		JButton imagen_pause = new JButton();
		imagen_play.setIcon(icono_reproducir);
		imagen_pause.setIcon(icono_parar);

		JLabel datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
		JLabel titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" + this.titulo,SwingConstants.CENTER);
		JLabel anyo_cancion = new JLabel("Año:\t\t\t\t\t" + this.anyo,SwingConstants.CENTER);
		JLabel autor_cancion = new JLabel("Autor:\t\t\t\t\t" + this.autor,SwingConstants.CENTER);
		JLabel duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + this.duracion + " s",SwingConstants.CENTER);
		JLabel comentarios_label = new JLabel("Comentarios de la cancion", SwingConstants.CENTER);
		JList lista_comentarios = new JList(names);
		JScrollPane scrollPane = new JScrollPane(lista_comentarios);

		
		//Style changes
		Font datosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
		Font tituloFont = new Font(titulo_cancion.getFont().getName(),Font.BOLD,titulo_cancion.getFont().getSize());
		Font anyoFont = new Font(anyo_cancion.getFont().getName(),Font.BOLD,anyo_cancion.getFont().getSize());
		Font autorFont = new Font(autor_cancion.getFont().getName(),Font.BOLD,autor_cancion.getFont().getSize());
		Font duracionFont = new Font(duracion_cancion.getFont().getName(),Font.BOLD,duracion_cancion.getFont().getSize());
		Font comentariosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		datos_cancion.setFont(datosFont);
		titulo_cancion.setFont(tituloFont);
		anyo_cancion.setFont(anyoFont);
		autor_cancion.setFont(autorFont);
		duracion_cancion.setFont(duracionFont);
		comentarios_label.setFont(comentariosFont);

		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//Manual Constraints
		//x axis, y axis, width, height 
		
		//Distribucion
		datos_cancion.setBounds(screenSize.width/2 + 150, 190, 150, 50);
		titulo_cancion.setBounds(screenSize.width/2 + 50, 240, 200, 50);
		anyo_cancion.setBounds(screenSize.width/2 + 50, 280, 150, 50);
		autor_cancion.setBounds(screenSize.width/2 + 50,320,150,50);
		duracion_cancion.setBounds(screenSize.width/2 + 50,360,180,50);
		comentarios_label.setBounds(screenSize.width/2 + 105, 420, 250, 50);
		scrollPane.setBounds(screenSize.width/2 + 80, 480, 300, 200);

		imagen_reproduccion.setBounds(screenSize.width/2 - 350, 190, 300, 300);
		imagen_play.setBounds(screenSize.width/2 - 280, 500, 70, 70);
		imagen_pause.setBounds(screenSize.width/2 - 210, 500, 70, 70);

		
		
		//Añadimos
		this.add(datos_cancion);
		this.add(titulo_cancion);
		this.add(anyo_cancion);
		this.add(autor_cancion);
		this.add(duracion_cancion);
		this.add(comentarios_label);
		this.add(scrollPane);
		this.add(imagen_reproduccion);
		this.add(imagen_play);
		this.add(imagen_pause);
	}
	
	 // método para asignar un controlador al botón
	 public void setControlador(ActionListener c) {
		 this.botonIzquierdaArriba.addActionListener(c);
		 this.botonIzquierdaAbajo.addActionListener(c);
	 }
	
}
