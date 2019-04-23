package ESPOTIPHAI_MIUSIC_FINAL;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ReproducirCancion extends PantallaPrincipal {

	private String titulo;
	private int anyo;
	private String autor;
	private int duracion;
	
	public ReproducirCancion(String arg1,int arg2,String arg3,int arg4) {
		super();
		
		this.titulo = arg1;
		this.anyo = arg2;
		this.autor = arg3;
		this.duracion = arg4;
		
		//Declaracion
		String direccion = "/ESPOTIPHAI_MIUSIC_SWING/images/reproduccion.jpg";
		String direccion1 = "";
		URL url_corchea = this.getClass().getResource(direccion);
		URL url_reproducir = this.getClass().getResource(direccion1);
		ImageIcon icono_corchea = new ImageIcon("images/reproduccion.jpg");
		ImageIcon icono_reproducir = new ImageIcon();
		ImageIcon icono_parar = new ImageIcon();
		
		JLabel datos_cancion = new JLabel("Datos de la cancion", SwingConstants.CENTER);
		JLabel imagen_reproduccion = new JLabel("icono_repro",icono_corchea,JLabel.CENTER);
		JLabel titulo_cancion = new JLabel("Titulo:\t\t\t\t\t" + this.titulo,SwingConstants.CENTER);
		JLabel anyo_cancion = new JLabel("Año:\t\t\t\t\t" + this.anyo,SwingConstants.CENTER);
		JLabel autor_cancion = new JLabel("Autor:\t\t\t\t\t" + this.autor,SwingConstants.CENTER);
		JLabel duracion_cancion = new JLabel("Duracion:\t\t\t\t\t" + this.duracion + " s",SwingConstants.CENTER);

		
		
		//Style changes
		Font datosFont = new Font(datos_cancion.getFont().getName(),Font.BOLD,datos_cancion.getFont().getSize());
		Font tituloFont = new Font(titulo_cancion.getFont().getName(),Font.BOLD,titulo_cancion.getFont().getSize());
		Font anyoFont = new Font(anyo_cancion.getFont().getName(),Font.BOLD,anyo_cancion.getFont().getSize());
		Font autorFont = new Font(autor_cancion.getFont().getName(),Font.BOLD,autor_cancion.getFont().getSize());
		Font duracionFont = new Font(duracion_cancion.getFont().getName(),Font.BOLD,duracion_cancion.getFont().getSize());

		datos_cancion.setFont(datosFont);
		titulo_cancion.setFont(tituloFont);
		anyo_cancion.setFont(anyoFont);
		autor_cancion.setFont(autorFont);
		duracion_cancion.setFont(duracionFont);
		
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		
		//Distribucion
		datos_cancion.setBounds(500, 190, 150, 50);
		titulo_cancion.setBounds(320, 240, 200, 50);
		anyo_cancion.setBounds(320, 280, 150, 50);
		autor_cancion.setBounds(320,320,150,50);
		duracion_cancion.setBounds(320,360,180,50);
		imagen_reproduccion.setBounds(50, 190, 250, 250);
		
		
		
		//Añadimos
		this.add(datos_cancion);
		this.add(imagen_reproduccion);
		this.add(titulo_cancion);
		this.add(anyo_cancion);
		this.add(autor_cancion);
		this.add(duracion_cancion);

	}
	
}
