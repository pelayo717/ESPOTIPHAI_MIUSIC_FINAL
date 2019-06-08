package ESPOTIPHAI_MIUSIC_FINAL.Grafic;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class PantallaInicio extends PantallaPrincipal {

	private JScrollPane canciones;
	private JScrollPane albumes;
	private JList lista_canciones;
	private JList lista_albumes;
	JButton seleccionarAlbum;
	JButton seleccionarCancion;
	
	public PantallaInicio() {
		
		canciones = new JScrollPane(lista_canciones);
		albumes = new JScrollPane(lista_albumes);
		JLabel susCanciones = new JLabel("Sus canciones",  SwingConstants.CENTER);
		JLabel susAlbumes = new JLabel("Sus albumes",  SwingConstants.CENTER);		
		seleccionarAlbum = new JButton("Elegir album");
		seleccionarCancion = new JButton("Elegir cancion");
		
		
		//Cambio de estilo en los JLabel
		Font susCancionesFont = new Font(susCanciones.getFont().getName(), Font.BOLD, 16);
		Font susAlbumesFont = new Font(susAlbumes.getFont().getName(), Font.BOLD, 16);
		canciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		albumes.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Distribucion en la pantalla
		//Manual Constraints
		//x axis, y axis, width, height 
				
		
		susCanciones.setBounds(screenSize.width/2 - 300, 200, 290, 30);
		susAlbumes.setBounds(screenSize.width/2 +10, 200, 290, 30);
		canciones.setBounds(screenSize.width/2 - 300, 250, 290, 200);
		albumes.setBounds(screenSize.width/2 + 10, 250, 290, 200);
	
		seleccionarCancion.setBounds(screenSize.width/2 - 280, 470, 250, 30);
		seleccionarAlbum.setBounds(screenSize.width/2 + 30, 470, 250, 30);
		
		
		//A�adimos los elementos a la pantalla principal
		this.add(albumes);
		this.add(canciones);
		this.add(susCanciones);
		this.add(susAlbumes);
		this.add(seleccionarAlbum);
		this.add(seleccionarCancion);
	}
		
	
	// m�todo para asignar un controlador al bot�n
	public void setControlador(ActionListener c) {
		this.botonIzquierdaArriba.addActionListener(c);
		this.botonIzquierdaMedio.addActionListener(c);
		this.seleccionarCancion.addActionListener(c);
		this.seleccionarAlbum.addActionListener(c);
	}
		 
	public void setUsuarioRegistrado() {
		this.botonIzquierdaArriba.setText("Ver Perfil");
		this.botonIzquierdaMedio.setVisible(false);
		this.botonIzquierdaAbajo.setVisible(false);
	}
			
	public void setUsuarioNoRegistrado() {
		this.botonIzquierdaArriba.setText("Iniciar Sesion");
		this.botonIzquierdaMedio.setVisible(true);
		this.botonIzquierdaAbajo.setVisible(false);
	}
			
}  
